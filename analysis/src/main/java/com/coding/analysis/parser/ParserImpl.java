package com.coding.analysis.parser;

import com.coding.analysis.entity.AnalysisInput;
import com.coding.analysis.entity.MemberAnalysisInput;
import com.coding.common.analysis.entity.*;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import strman.Strman;

import java.io.File;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by luoziyihao on 4/12/17.
 */

@Slf4j
public class ParserImpl implements Parser{

    private TestsuiteParser testSuiteInfoParser = new TestsuiteParserImpl();

    @Override
    public AnalysisResult parse(AnalysisInput analysisInput) {
        Preconditions.checkNotNull(analysisInput);
        return new AnalysisResult()
                .setTestMemberInfos(
                        analysisInput.memberBuildInputs().stream()
                                .map(parseMemberAnalysisInput())
                                .collect(Collectors.toMap(
                                        TestMemberInfo::getId,
                                        testMemberInfo -> testMemberInfo
                                        )
                                )
                );
    }

    private Function<? super MemberAnalysisInput, ? extends TestMemberInfo> parseMemberAnalysisInput() {
        return memberAnalysisInput -> new TestMemberInfo()
                .setId(memberAnalysisInput.id())
                .setTestModuleInfos(
                        memberAnalysisInput.resultInputs()
                                .stream()
                                .filter(resultInput -> {
                                    if (!resultInput.legal()) {
                                        log.error("result illegal, resultInput={}", resultInput);
                                        return false;
                                    }
                                    return resultInput.result().success();
                                })
                                .filter(resultInput -> {
                                    File file = new File(resultInput.result().path());
                                    if (file.exists() && file.isDirectory()) {
                                        log.error("result path illegal, resultInput={}", resultInput);
                                        return false;
                                    }
                                    return resultInput.result().success();
                                })
                                .map(resultInput -> new TestModuleInfo()
                                        .setResult(resultInput.result())
                                        .setMavenState(
                                                resultInput.result().specificReason() != null ?
                                                        MavenTransferState.MAVEN_UNUSED : MavenTransferState.MAVEN_USED
                                        )
                                )
                                .map(parserResult())
                                .collect(Collectors.toList())

                );


    }

    private static final String SUREFIRE_REPORTS_PRE = Strman.append(
            File.separator
            , "target"
            , File.separator
            , "surefire-reports"
            , File.separator
    );


    private Function<? super TestModuleInfo, ? extends TestModuleInfo> parserResult() {
        return testModuleInfo -> {
            final String surefireReportsPath = Strman.append(testModuleInfo.getResult().path(), SUREFIRE_REPORTS_PRE);
            File surefireReportsDir = new File(surefireReportsPath);
            if (!(surefireReportsDir.exists() && surefireReportsDir.isDirectory())) {
                return testModuleInfo;
            }
            testModuleInfo.setMavenState(MavenTransferState.SUREFIRE_REPORTS_PLUGIN_USED);
            return testModuleInfo.setSurefireReports(parserSurefireReports(surefireReportsDir));
        };
    }

    private static final String TEST_PREFIX = "TEST-";
    private static final String TEST_SUFFIX = "Test.xml";

    private SurefireReports parserSurefireReports(File surefireReportsDir) {
        File[] files = surefireReportsDir.listFiles();
        if (files == null) {
            log.error("files is null in surefireReports Directory={}", surefireReportsDir);
            return null;
        }
        Stream<TestSuiteInfo> stream = Stream.of(files)
                .filter(file -> {
                    final String fileName = file.getName();
                    return fileName.startsWith(TEST_PREFIX) && fileName.endsWith(TEST_SUFFIX);
                })
                .map(file -> testSuiteInfoParser.parse(file));

        return new SurefireReports()
                .setTestSuiteInfos(stream.collect(Collectors.toList()))
                .setSurefireReportsCountInfo(
                        stream.map(TestSuiteInfo::surefireReportsCountInfo)
                                .reduce(new SurefireReportsCountInfo(), accumulatorSurefireReportsCountInfo())
                );

    }

    private BinaryOperator<SurefireReportsCountInfo> accumulatorSurefireReportsCountInfo() {
        return (surefireReportsCountInfo, testSuiteInfo) ->
                surefireReportsCountInfo
                        .setErrors(surefireReportsCountInfo.getErrors() + testSuiteInfo.getErrors())
                        .setFailures(surefireReportsCountInfo.getFailures() + testSuiteInfo.getFailures())
                        .setSkipped(surefireReportsCountInfo.getSkipped() + testSuiteInfo.getSkipped())
                        .setTests(surefireReportsCountInfo.getTests() + testSuiteInfo.getTests())
                        .setTime(surefireReportsCountInfo.getTime() + testSuiteInfo.getTime())
                ;
    }

}

