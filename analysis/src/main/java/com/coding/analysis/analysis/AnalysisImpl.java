package com.coding.analysis.analysis;

import com.coding.common.analysis.entity.MemberAnalysisInfo;
import com.coding.common.analysis.entity.ModuleAnalysisInfo;
import com.coding.common.analysis.repository.MemberAnalysisInfoRepository;
import com.coding.analysis.parser.Parser;
import com.coding.analysis.validator.Validator;
import com.coding.common.analysis.entity.AnalysisResult;
import com.coding.common.build.BuildResult;
import com.coding.common.build.Result;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by luoziyihao on 4/19/17.
 */
@Slf4j
public class AnalysisImpl implements Analysis {

    private Validator validator;
    private Parser parser;
    private MemberAnalysisInfoRepository memberAnalysisInfoRepository;

    @Override
    public Analysis setValidator(Validator validator) {
        this.validator = validator;
        return this;
    }

    @Override
    public Analysis setParser(Parser parser) {
        this.parser = parser;
        return this;
    }

    @Override
    public Analysis setMemberAnalysisInfoRepository(MemberAnalysisInfoRepository memberAnalysisInfoRepository) {
        this.memberAnalysisInfoRepository = memberAnalysisInfoRepository;
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public MemberAnalysisInfo analysis(Result result) {
        try {
           return parser.parse(validator.validate(result));
        } catch (Exception e) {
            log.error("analysis error", e);

            throw new IllegalStateException(e);
        }
    }
}
