package com.coding.common.analysis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by luoziyihao on 4/12/17.
 */
@Data
@Accessors(chain = true)
public class PropertyInfo {
    private String name;
    private String value;
}
