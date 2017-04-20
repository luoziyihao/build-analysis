package com.coding.analysis.validator;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by luoziyihao on 4/12/17.
 */
@Data
@Accessors(fluent = true)
public class MemberAnalysisInput {
    private String id;
    private List<ResultInput> resultInputs;
}
