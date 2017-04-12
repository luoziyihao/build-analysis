package com.coding.analysis;

import com.coding.common.build.BuildResult;

public interface Validator {

    AnalysisInput validate(BuildResult buildResult);
}
