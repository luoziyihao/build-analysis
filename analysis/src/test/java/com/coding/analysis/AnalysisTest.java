package com.coding.analysis;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by luoziyihao on 4/12/17.
 */
public class AnalysisTest {
    @Test
    public void testBuildResult() throws Exception {

        System.out.println("hello, world");
    }

    @Test
    public void testException1(){
        throw new IllegalStateException("1#");

    }
    @Test
    public void testException2(){
        throw new IllegalStateException("2#");

    }

}