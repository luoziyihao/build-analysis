package com.coding.common.analysis.repository;

import com.coding.common.analysis.entity.MemberAnalysisInfo;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by luoziyihao on 4/20/17.
 */
public class MemberAnalysisInfoMemoryRepository implements MemberAnalysisInfoRepository {


    private static final Map<String, MemberAnalysisInfo> testMemberInfos = Maps.newConcurrentMap();

    @Override
    public MemberAnalysisInfo get(String id) {
        return testMemberInfos.get(id);
    }

    @Override
    public List<MemberAnalysisInfo> getAll() {
        return new ArrayList<>(testMemberInfos.values());
    }

    @Override
    public void save(Map<String, MemberAnalysisInfo> memberInfos) {
         testMemberInfos.putAll(memberInfos);
    }
}
