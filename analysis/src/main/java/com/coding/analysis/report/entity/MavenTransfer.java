package com.coding.analysis.report.entity;

import com.coding.common.analysis.entity.MavenTransferState;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by luoziyihao on 4/20/17.
 */
@Data
@Accessors(chain = true)
public class MavenTransfer {
    private List<Node> nodes;

    @Data
    @Accessors(chain = true)
    public static final class Node {
        private String id;
        private MavenTransferState mavenTransferState;
    }
}


