package com.coding.analysis;

import com.coding.analysis.entity.AnalysisInput;
import com.coding.analysis.entity.MemberAnalysisInput;
import com.coding.common.analysis.entity.AnalysisResult;
import com.coding.common.analysis.entity.TestMemberInfo;
import com.coding.common.analysis.entity.TestModuleInfo;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by luoziyihao on 4/12/17.
 */

@Slf4j
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
        return memberAnalysisInput -> new TestMemberInfo()
                .setQq(memberAnalysisInput.qq())
                .setTestModuleInfos(
                        memberAnalysisInput.resultInputs()
                                .stream()
                                .filter(resultInput -> {
                                    if (!resultInput.legal()) {
                                        log.error("result illegal, resultInput={}", resultInput);
                                        return false;
                                    }
                                    return resultInput.result().success;
                                })
                                .map(resultInput -> new TestModuleInfo().setResult(resultInput.result()))
                                .map(parserResult())
                                .collect(Collectors.toList())

                );


    }

    private Function<? super TestModuleInfo, ? extends TestModuleInfo> parserResult() {
        return testModuleInfo -> {

            return null;
        };
    }
}

