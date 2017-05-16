package com.coding.common.analysis.entity;

import com.coding.common.build.Result;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by luoziyihao on 4/12/17.
 */
@Accessors(chain = true)
@Data
public class MemberAnalysisInfo {
    private String id;
    private Result result;
    private List<ModuleAnalysisInfo> moduleAnalysisInfos;
}
