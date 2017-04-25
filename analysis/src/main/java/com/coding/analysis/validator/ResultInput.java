package com.coding.analysis.validator;

import com.coding.common.build.Result;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by luoziyihao on 4/12/17.
 */
@Data
@Accessors(fluent = true)
public class ResultInput {
    private Result result;
    private boolean legal = false;
    private ResultIllegalReason resultIllegalReason;
    private String  msg = "";
}
