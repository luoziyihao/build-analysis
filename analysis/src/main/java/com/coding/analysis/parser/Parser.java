package com.coding.analysis.parser;

import com.coding.analysis.validator.ResultInput;
import com.coding.common.analysis.entity.MemberAnalysisInfo;

/**
 * Created by luoziyihao on 4/12/17.
 */
public interface Parser {
    MemberAnalysisInfo parse(ResultInput resultInput);
}
