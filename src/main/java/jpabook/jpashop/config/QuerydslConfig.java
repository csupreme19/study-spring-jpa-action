package jpabook.jpashop.config;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class QuerydslConfig {

    @Bean
    JPAQuery jpaQuery(EntityManager em) {
        return new JPAQuery(em);
    }

}
