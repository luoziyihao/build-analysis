package com.coding.web.controller;

import com.alibaba.fastjson.JSON;
import com.coding.analysis.analysis.Analysis;
import com.coding.common.analysis.entity.MemberAnalysisInfo;
import com.coding.common.analysis.repository.MemberAnalysisInfoRepository;
import com.coding.common.build.BuildResult;
import com.coding.common.build.PomInfo;
import com.coding.web.component.Ret;
import com.google.common.base.Preconditions;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import strman.Strman;

import java.io.IOException;
import java.util.Date;

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
