package com.coding.common.analysis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luoziyihao on 4/12/17.
 */
@Data
public class TestCaseInfo {
    private String name;
    private String className;
    private Double time;
    private String systemOut;
    private List<ErrorInfo> errorInfos;

    public void addErrorInfo(ErrorInfo errorInfo) {
        if (this.errorInfos == null) {
            this.errorInfos= new ArrayList<>();
        }
        this.errorInfos.add(errorInfo);;
    }
}
