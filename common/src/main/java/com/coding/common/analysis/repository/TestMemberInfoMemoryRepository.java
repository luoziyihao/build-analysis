package com.coding.common.analysis.repository;

import com.coding.common.analysis.entity.TestMemberInfo;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by luoziyihao on 4/20/17.
 */
public class TestMemberInfoMemoryRepository implements TestMemberInfoRepository {


    private static final Map<String, TestMemberInfo> testMemberInfos = Maps.newConcurrentMap();

    @Override
    public TestMemberInfo get(String id) {
        return testMemberInfos.get(id);
    }

    @Override
    public List<TestMemberInfo> getAll() {
        return new ArrayList<>(testMemberInfos.values());
    }

    @Override
    public void save(Map<String, TestMemberInfo> memberInfos) {
         testMemberInfos.putAll(memberInfos);
    }
}
