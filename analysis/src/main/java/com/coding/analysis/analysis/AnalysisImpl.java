package com.coding.analysis.analysis;

import com.coding.analysis.data.TestMemberInfoRepository;
import com.coding.analysis.parser.Parser;
import com.coding.analysis.validator.Validator;
import com.coding.common.analysis.entity.AnalysisResult;
import com.coding.common.build.BuildResult;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by luoziyihao on 4/19/17.
 */
@Slf4j
public class AnalysisImpl implements Analysis {

    private Validator validator;
    private Parser parser;
    private TestMemberInfoRepository testMemberInfoRepository;

    @Override
    public Analysis setValidator(Validator validator) {
        this.validator = validator;
        return this;
    }

    @Override
    public Analysis setParser(Parser parser) {
        this.parser = parser;
        return this;
    }

    @Override
    public Analysis setTestMemberInfoRepository(TestMemberInfoRepository testMemberInfoRepository) {
        this.testMemberInfoRepository = testMemberInfoRepository;
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean analysis(BuildResult buildResult) {
        try {
            AnalysisResult analysisResult = parser.parse(validator.validate(buildResult));
            testMemberInfoRepository.save(analysisResult.getTestMemberInfos());
            log.debug("analysisResult={}", analysisResult);
        } catch (Exception e) {
            log.error("analysis error", e);
            return false;
        }
        return true;
    }
}
