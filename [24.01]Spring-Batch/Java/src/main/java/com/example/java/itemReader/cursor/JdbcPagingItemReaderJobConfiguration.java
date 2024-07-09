package com.example.java.itemReader.cursor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class JdbcPagingItemReaderJobConfiguration {

    private ItemWriter<Pay> jdbcPagingItemWriter() {
        return list -> {
            for (Pay pay :
                    list) {
                log.info("Current Pay={}", pay);
            }
        };
    }

    private PagingQueryProvider createQueryProvider(DataSource dataSource) throws Exception{
        SqlPagingQueryProviderFactoryBean queryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
        queryProviderFactoryBean.setDataSource(dataSource);
        queryProviderFactoryBean.setSelectClause("id, amount, tx_name, tx_date_time");
        queryProviderFactoryBean.setFromClause("FROM pay");
        queryProviderFactoryBean.setWhereClause("WHERE amount >= :amount");

        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);

        queryProviderFactoryBean.setSortKeys(sortKeys);

        return queryProviderFactoryBean.getObject();
    }

    @Bean
    public JdbcPagingItemReader<Pay> jdbcPagingItemReader(DataSource dataSource) throws Exception {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("amount", 2000);

        return new JdbcPagingItemReaderBuilder<Pay>()
                .name("jdbcPagingItemReader")
                .pageSize(10) // 쿼리당 요청할 레코드 수
                .dataSource(dataSource) // 접근할 DB 객체
                .beanRowMapper(Pay.class) // 쿼리 결과 데이터 매핑
                .parameterValues(parameterValues) // 쿼리 파라미터 설정
                .queryProvider(createQueryProvider(dataSource)) // DB 페이징 전략에 따른 설정
//                .selectClause(String selectClause) // select 절 설정 (PagingQueryProvider 에서 설정 가능)
//                .fromClause(String fromClause) // from 절 설정 (PagingQueryProvider 에서 설정 가능)
//                .whereClause(String whereClause) // where 절 설정 (PagingQueryProvider 에서 설정 가능)
//                .groupClause(String groupClause) // group 절 설정 (PagingQueryProvider 에서 설정 가능)
//                .sortKeys(Map<String, Order> sortKeys) // 정렬을 위한 유니크 키 설정
//                .maxItemCount(int count) // 조회 할 최대 item 수
//                .currentItemCount(int count) // 조회 Item의 시작지점
                .build();
    }


    @Bean
    public Step jdbcPagingItemReaderStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, JdbcPagingItemReader<Pay> jdbcPagingItemReader) {
        log.info(">>>> jdbcPagingItemReaderStep");
        return new StepBuilder("jdbcPagingItemReaderStep", jobRepository)
                .<Pay, Pay>chunk(10, platformTransactionManager)
                .reader(jdbcPagingItemReader)
//                .processor(processor())
                .writer(jdbcPagingItemWriter())
                .build();
    }

    @Bean
    public Job jdbcPagingItemReaderJob(JobRepository jobRepository, Step jdbcPagingItemReaderStep) {
        log.info(">>>> jdbcPagingItemReaderJob");
        return new JobBuilder("jdbcPagingItemReaderJob", jobRepository)
                .start(jdbcPagingItemReaderStep)
                .build();
    }
}
