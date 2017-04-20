package com.coding.common.analysis.repository;

import com.coding.common.analysis.entity.TestMemberInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by luoziyihao on 4/20/17.
 */
public interface TestMemberInfoRepository {

    TestMemberInfo get(String id);
    List<TestMemberInfo> getAll();
    void save(Map<String, TestMemberInfo> memberInfos);

}
