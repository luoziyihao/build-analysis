package com.coding.analysis.validator;

import com.coding.common.build.BuildResult;

public interface Validator {

    AnalysisInput validate(BuildResult buildResult);
}
