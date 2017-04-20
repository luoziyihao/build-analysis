package com.coding.analysis.report;

import com.coding.common.analysis.entity.MavenBuildState;
import com.coding.common.analysis.entity.MavenTransferState;
import com.coding.common.analysis.entity.TestMemberInfo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by luoziyihao on 4/20/17.
 */
@Data
@Accessors(chain = true)
public class MavenBuild {

    private List<Node> nodes;

    @Data
    @Accessors(chain = true)
    public final static class Node {
        private String id;
        private MavenBuildState mavenBuildState;
        private TestMemberInfo testMemberInfo;
    }
}
