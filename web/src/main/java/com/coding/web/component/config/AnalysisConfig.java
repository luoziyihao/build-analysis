package com.coding.web.component.config;

import com.coding.analysis.analysis.Analysis;
import com.coding.analysis.analysis.AnalysisImpl;
import com.coding.analysis.parser.Parser;
import com.coding.analysis.parser.ParserImpl;
import com.coding.analysis.validator.Validator;
import com.coding.analysis.validator.ValidatorImpl;
import com.coding.common.analysis.repository.MemberAnalysisInfoRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by luoziyihao on 4/24/17.
 */
@Configuration
public class AnalysisConfig {

    @Bean(name = "analysisParser")
    public Parser parser() {
        return new ParserImpl();
    }

    @Bean(name = "analysisValidator")
    public Validator validator() {
        return new ValidatorImpl();
    }

    @Bean
    public Analysis analysis(@Qualifier(value = "analysisParser") Parser parser
            , @Qualifier(value = "analysisValidator") Validator validator
            , MemberAnalysisInfoRepository memberAnalysisInfoRepository) {

        return new AnalysisImpl()
                .setParser(parser)
                .setValidator(validator)
                .setMemberAnalysisInfoRepository(memberAnalysisInfoRepository);
    }
}
