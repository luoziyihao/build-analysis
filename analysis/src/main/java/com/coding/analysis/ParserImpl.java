package com.coding.analysis;

import com.coding.analysis.entity.AnalysisInput;
import com.coding.analysis.entity.MemberAnalysisInput;
import com.coding.common.analysis.entity.AnalysisResult;
import com.coding.common.analysis.entity.TestMemberInfo;
import com.google.common.base.Preconditions;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by luoziyihao on 4/12/17.
 */
public class ParserImpl implements Parser {

    @Override
    public AnalysisResult parse(AnalysisInput analysisInput) {
        Preconditions.checkNotNull(analysisInput);
        return new AnalysisResult()
                .setTestMemberInfos(
                        analysisInput.memberBuildInputs().stream()
                                .map(parseMemberAnalysisInput())
                                .collect(Collectors.toMap(
                                        TestMemberInfo::getQq,
                                        testMemberInfo -> testMemberInfo
                                        )
                                )
                );
    }

    private Function<? super MemberAnalysisInput, ? extends TestMemberInfo> parseMemberAnalysisInput() {
        return null;
    }


}
