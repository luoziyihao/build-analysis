package com.coding.analysis;

import com.coding.analysis.entity.AnalysisInput;
import com.coding.common.analysis.entity.TestMemberInfo;
import com.google.common.base.Preconditions;

/**
 * Created by luoziyihao on 4/12/17.
 */
public class ParserImpl implements Parser{

    @Override
    public TestMemberInfo parse(AnalysisInput analysisInput) {
        Preconditions.checkNotNull(analysisInput);

        analysisInput.memberBuildInputs().stream()
                .map(memberAnalysisInput -> parseMemberAnalysisInput())

        return null;
    }

    private <R> R parseMemberAnalysisInput() {
        return null;
    }


}
