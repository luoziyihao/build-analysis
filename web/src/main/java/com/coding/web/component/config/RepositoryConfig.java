package com.coding.web.component.config;

import com.coding.common.analysis.repository.MemberAnalysisInfoMemoryRepository;
import com.coding.common.analysis.repository.MemberAnalysisInfoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by luoziyihao on 4/24/17.
 */
@Configuration
public class RepositoryConfig {

    @Bean
    public MemberAnalysisInfoRepository memberAnalysisInfoRepository() {
        return new MemberAnalysisInfoMemoryRepository();
    }
}
