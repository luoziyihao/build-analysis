package com.coding.web.controller;

import com.coding.common.analysis.entity.TestMemberInfo;
import com.coding.common.analysis.repository.TestMemberInfoRepository;
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
@RequestMapping(value = "/testMemberInfo")
public class TestMemberInfoController {

    @Autowired
    private TestMemberInfoRepository testMemberInfoRepository;

    @RequestMapping(value = "/{id}")
    public Object findOne(@PathVariable(value = "id") String id) {
        Preconditions.checkNotNull(id);
        TestMemberInfo testMemberInfo = testMemberInfoRepository.get(id);
        if (testMemberInfo == null) {
            return Ret.fail(Strman.append("testMemberInfo is not exist for id=", id));
        } else {
            return Ret.success(testMemberInfo);
        }
    }
}
