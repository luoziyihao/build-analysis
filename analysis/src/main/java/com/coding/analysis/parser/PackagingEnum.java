package com.coding.analysis.parser;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Created by luoziyihao on 5/16/17.
 */
public class PackagingEnum {
    private static final String JAR = "jar";
    private static final String POM = "pom";
    private static final String WAR = "war";
    private static final List packagingEnum = ImmutableList.of(
            JAR
            , POM
            , WAR
    );

    public static boolean legal(String packaging) {
        return packagingEnum.contains(packaging);
    }
}
