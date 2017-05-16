package com.coding.analysis.parser;

import com.coding.analysis.validator.ResultInput;
import com.coding.common.analysis.entity.MemberAnalysisInfo;
import com.coding.common.analysis.entity.ModuleAnalysisInfo;
import com.coding.common.analysis.entity.surefire.SurefireReports;
import com.coding.common.analysis.entity.surefire.SurefireReportsCountInfo;
import com.coding.common.analysis.entity.surefire.TestSuiteInfo;
import com.coding.common.build.PomInfo;
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
    public MemberAnalysisInfo parse(ResultInput resultInput) {
        Preconditions.checkNotNull(resultInput);
        MemberAnalysisInfo memberAnalysisInfo = new MemberAnalysisInfo();
        if (!resultInput.legal()) {
            log.error("result illegal, resultInput={}", resultInput);
            throw new IllegalStateException("result illegal");
        }
        return memberAnalysisInfo
                .setResult(resultInput.result())
                .setModuleAnalysisInfos(
                        resultInput.result().pomInfos()
                                .stream()
                                .filter(pomInfo -> PackagingEnum.legal(pomInfo.packaging()))
                                .map(parserPomInfo())
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


    private Function<? super PomInfo, ? extends ModuleAnalysisInfo> parserPomInfo() {
        return pomInfo -> {
            ModuleAnalysisInfo moduleAnalysisInfo = new ModuleAnalysisInfo();
            final String surefireReportsPath = Strman.append(pomInfo.path(), SUREFIRE_REPORTS_PRE);
            File surefireReportsDir = new File(surefireReportsPath);
            if (!legalDirectory(surefireReportsDir)) {
                log.error("surefireReportsDir illegal, surefireReportsDir={}, pomInfo=", surefireReportsDir, pomInfo);
                throw new IllegalStateException("surefireReportsDir illegal");
            }
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

