package com.coding.test;

import com.google.common.collect.ImmutableMap;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by luoziyihao on 4/20/17.
 */
public class MapTest {

    @Test
    public void testMapGet() {
        Map map = ImmutableMap.of("k1", "v1");
        Assert.assertEquals(null, map.get("k2"));
    }
}
