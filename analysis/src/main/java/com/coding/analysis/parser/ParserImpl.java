package com.coding.analysis.parser;

import com.coding.analysis.validator.AnalysisInput;
import com.coding.analysis.validator.MemberAnalysisInput;
import com.coding.common.analysis.entity.*;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import strman.Strman;

import java.io.File;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.coding.common.util.FileUtils.legalDirectory;

/**
 * Created by luoziyihao on 4/12/17.
 */

@Slf4j
public class ParserImpl implements Parser {

    private TestsuiteParser testSuiteInfoParser = new TestsuiteParserImpl();

    @Override
    public AnalysisResult parse(AnalysisInput analysisInput) {
        Preconditions.checkNotNull(analysisInput);
        return new AnalysisResult()
                .setTestMemberInfos(
                        analysisInput.memberBuildInputs().stream()
                                .map(parseMemberAnalysisInput())
                                .collect(Collectors.toMap(
                                        MemberAnalysisInfo::getId,
                                        testMemberInfo -> testMemberInfo
                                        )
                                )
                );
    }

    private Function<? super MemberAnalysisInput, ? extends MemberAnalysisInfo> parseMemberAnalysisInput() {
        return memberAnalysisInput -> new MemberAnalysisInfo()
                .setId(memberAnalysisInput.id())
                .setModuleAnalysisInfos(
                        memberAnalysisInput.resultInputs()
                                .stream()
                                .filter(resultInput -> {
                                    if (!resultInput.legal()) {
                                        log.error("result illegal, resultInput={}", resultInput);
                                        return false;
                                    }
                                    return resultInput.result().success();
                                })
                                .map(resultInput -> new ModuleAnalysisInfo()
                                        .setResult(resultInput.result())
                                        .setMavenTransferState(
                                                resultInput.result().specificReason() != null ?
                                                        MavenTransferState.MAVEN_UNUSED : MavenTransferState.MAVEN_USED
                                        )
                                        .setMavenBuildState(
                                                resultInput.result().success() ?
                                                        MavenBuildState.SUCCESS : MavenBuildState.FAILED
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


    private Function<? super ModuleAnalysisInfo, ? extends ModuleAnalysisInfo> parserResult() {
        return moduleAnalysisInfo -> {
            final String surefireReportsPath = Strman.append(moduleAnalysisInfo.getResult().path(), SUREFIRE_REPORTS_PRE);
            File surefireReportsDir = new File(surefireReportsPath);
            if (!legalDirectory(surefireReportsDir)) {
                return moduleAnalysisInfo;
            }
            moduleAnalysisInfo.setMavenTransferState(MavenTransferState.SUREFIRE_REPORTS_PLUGIN_USED);
            return moduleAnalysisInfo.setSurefireReports(parserSurefireReports(surefireReportsDir));
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

        final List<TestSuiteInfo> testSuiteInfos = Stream.of(files)
                .filter(file -> {
                    final String fileName = file.getName();
                    return fileName.startsWith(TEST_PREFIX) && fileName.endsWith(TEST_SUFFIX);
                })
                .map(file -> testSuiteInfoParser.parse(file))
                .collect(Collectors.toList());

        return new SurefireReports()
                .setTestSuiteInfos(testSuiteInfos)
                .setSurefireReportsCountInfo(testSuiteInfos.stream()
                        .map(TestSuiteInfo::surefireReportsCountInfo)
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

