package com.coding.analysis.validator;

import com.coding.common.build.Result;

public interface Validator {

    ResultInput validate(Result result);
}
