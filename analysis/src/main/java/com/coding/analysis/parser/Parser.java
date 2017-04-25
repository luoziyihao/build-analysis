package com.coding.analysis.parser;

import com.coding.analysis.validator.AnalysisInput;
import com.coding.common.analysis.entity.AnalysisResult;

/**
 * Created by luoziyihao on 4/12/17.
 */
public interface Parser {
    AnalysisResult parse(AnalysisInput analysisInput);
}
