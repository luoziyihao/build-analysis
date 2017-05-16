package com.coding.analysis.analysis;

import com.coding.common.analysis.entity.MemberAnalysisInfo;
import com.coding.common.analysis.repository.MemberAnalysisInfoRepository;
import com.coding.analysis.parser.Parser;
import com.coding.analysis.validator.Validator;
import com.coding.common.build.BuildResult;
import com.coding.common.build.Result;

/**
 * Created by luoziyihao on 4/12/17.
 */

public interface Analysis {

    Analysis setValidator(Validator validator);

    Analysis setParser(Parser parser);

    /**
     * 分析单个 result
     * @param result
     * @return
     */
    MemberAnalysisInfo analysis(Result result);

    Analysis setMemberAnalysisInfoRepository(MemberAnalysisInfoRepository memberAnalysisInfoRepository);

}
