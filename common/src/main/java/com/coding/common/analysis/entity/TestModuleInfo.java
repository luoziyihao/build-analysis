package com.coding.common.analysis.entity;

import com.coding.common.build.Result;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by luoziyihao on 4/12/17.
 */
@Data
@Accessors(chain = true)
public class TestModuleInfo {

    private SurefireReports surefireReports;
    private Result result;
    private MavenTransferState mavenState;

}
