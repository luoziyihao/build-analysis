package com.coding.common.analysis.entity;

import com.coding.common.analysis.entity.surefire.SurefireReports;
import com.coding.common.build.PomInfo;
import com.coding.common.build.Result;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by luoziyihao on 4/12/17.
 */
@Data
@Accessors(chain = true)
public class ModuleAnalysisInfo {

    private SurefireReports surefireReports;

    private PomInfo pomInfo;
}
