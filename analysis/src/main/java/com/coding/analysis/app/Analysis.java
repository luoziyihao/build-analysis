package com.coding.analysis.app;

import com.coding.analysis.parser.Parser;
import com.coding.analysis.validator.Validator;
import com.coding.common.build.BuildResult;

/**
 * Created by luoziyihao on 4/12/17.
 */

public interface Analysis {

    Analysis setValidator(Validator validator);

    Analysis setParser(Parser parser);

    /**
     * 拿到所有的 buildResult, 校验准确性
     * 开始解析 buildResult , 生成 TestMemberInfo
     * 数据持久化(内存, mongo)
     * 各种报表
     * @param buildResult
     */
    boolean analysis(BuildResult buildResult);
}
