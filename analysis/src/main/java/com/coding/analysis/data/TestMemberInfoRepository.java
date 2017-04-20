package com.coding.analysis.data;

import com.coding.common.analysis.entity.TestMemberInfo;

import java.util.Map;

/**
 * Created by luoziyihao on 4/20/17.
 */
public interface TestMemberInfoRepository {

    TestMemberInfo get(String id);
    void save(Map<String, TestMemberInfo> memberInfos);

}
