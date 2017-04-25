package com.coding.common.analysis.repository;

import com.coding.common.analysis.entity.MemberAnalysisInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by luoziyihao on 4/20/17.
 */
public interface MemberAnalysisInfoRepository {

    MemberAnalysisInfo get(String id);
    List<MemberAnalysisInfo> getAll();
    void save(Map<String, MemberAnalysisInfo> memberInfos);

}
