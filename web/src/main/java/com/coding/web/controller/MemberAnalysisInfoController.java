package com.coding.web.controller;

import com.coding.common.analysis.entity.MemberAnalysisInfo;
import com.coding.common.analysis.repository.MemberAnalysisInfoRepository;
import com.coding.web.component.Ret;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import strman.Strman;

/**
 * Created by luoziyihao on 4/20/17.
 */
@RestController
@RequestMapping(value = "/memberAnalysisInfo")
public class MemberAnalysisInfoController {

    @Autowired
    private MemberAnalysisInfoRepository memberAnalysisInfoRepository;

    @RequestMapping(value = "/{id}")
    public Object findOne(@PathVariable(value = "id") String id) {
        Preconditions.checkNotNull(id);
        MemberAnalysisInfo testMemberInfo = memberAnalysisInfoRepository.get(id);
        if (testMemberInfo == null) {
            return Ret.fail(Strman.append("memberAnalysisInfo is not exist for id=", id));
        } else {
            return Ret.success(testMemberInfo);
        }
    }
}
