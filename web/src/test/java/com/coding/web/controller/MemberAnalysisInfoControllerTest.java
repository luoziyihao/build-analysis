package com.coding.web.controller;

import com.coding.analysis.analysis.Analysis;
import com.coding.common.build.BuildResult;
import com.coding.common.build.PomInfo;
import com.coding.web.component.Ret;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import strman.Strman;

import java.io.IOException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by luoziyihao on 4/24/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberAnalysisInfoControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private Analysis analysis;

    @Before
    public void before() throws IOException {
        BuildResult buildResult = new BuildResult();
        String qq = "1204187480";
        buildResult.addResultEntry(
                qq
                , new ClassPathResource("mock/test-project").getFile().getAbsolutePath()
                , true
                , null
                , new Date()
                , new PomInfo().artifactId("analysis").groupId("com.coding").version("1.0-SNAPSHOT")
        );
        analysis.analysis(buildResult);
    }

    @Test
    public void findOne() throws Exception {
        String id = "12041874801";
        Ret ret = restTemplate.getForObject(Strman.append("/memberAnalysisInfo/", id), Ret.class);
        assertThat(ret.toString()).isEqualTo(Ret.fail(Strman.append("memberAnalysisInfo is not exist for id=", id)).toString());
    }

}