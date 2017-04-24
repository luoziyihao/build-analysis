package com.coding.web.component.config;

import com.coding.common.analysis.repository.TestMemberInfoMemoryRepository;
import com.coding.common.analysis.repository.TestMemberInfoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by luoziyihao on 4/24/17.
 */
@Configuration
public class RepositoryConfig {

    @Bean
    public TestMemberInfoRepository testMemberInfoRepository() {
        return new TestMemberInfoMemoryRepository();
    }
}
