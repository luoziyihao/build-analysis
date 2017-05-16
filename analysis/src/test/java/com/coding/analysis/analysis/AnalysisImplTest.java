package com.coding.analysis.analysis;

import com.coding.analysis.parser.ParserImpl;
import com.coding.analysis.validator.ValidatorImpl;
import com.coding.common.analysis.entity.MemberAnalysisInfo;
import com.coding.common.analysis.repository.MemberAnalysisInfoMemoryRepository;
import com.coding.common.analysis.repository.MemberAnalysisInfoRepository;
import com.coding.common.build.BuildResult;
import com.coding.common.build.PomInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * Created by luoziyihao on 4/23/17.
 */
@Slf4j
public class AnalysisImplTest {

    @Test
    public void analysis() throws Exception {
//        MemberAnalysisInfoRepository memberAnalysisInfoRepository = new MemberAnalysisInfoMemoryRepository();
//        BuildResult buildResult = new BuildResult();
//        String qq = "1204187480";
//        buildResult.addResultEntry(
//                qq
//                , new ClassPathResource("mock/test-project").getFile().getAbsolutePath()
//                , true
//                , null
//                , new Date()
//                , new PomInfo().artifactId("analysis").groupId("com.coding").version("1.0-SNAPSHOT")
//        );
//
//        new AnalysisImpl()
//                .setParser(new ParserImpl())
//                .setValidator(new ValidatorImpl())
//                .setMemberAnalysisInfoRepository(memberAnalysisInfoRepository)
//                .analysis(buildResult);
//        MemberAnalysisInfo testMemberInfo = memberAnalysisInfoRepository.get(qq);
//        log.debug("testMemberInfo={}", testMemberInfo);
//        Assert.notNull(testMemberInfo, "null");

    }

}