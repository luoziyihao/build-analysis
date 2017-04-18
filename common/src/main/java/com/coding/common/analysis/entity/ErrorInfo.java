package com.coding.common.analysis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by luoziyihao on 4/12/17.
 */
@Data
public class ErrorInfo {
    private String type;
    private String text;
    private String message;
}
