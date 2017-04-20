package com.coding.analysis.app;

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

    @SuppressWarnings("unchecked")
    @Override
    public boolean analysis(BuildResult buildResult) {
        try {
            AnalysisResult analysisResult = parser.parse(validator.validate(buildResult));
            log.debug("analysisResult={}", analysisResult);
        } catch (Exception e) {
            log.error("analysis error", e);
            return false;
        }
        return true;
    }
}
