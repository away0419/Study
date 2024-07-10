package com.example.kotlin.itemReader.cursor

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JdbcPagingItemReader
import org.springframework.batch.item.database.Order
import org.springframework.batch.item.database.PagingQueryProvider
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class JdbcPagingItemReaderConfiguration {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    private fun createQueryProvider(dataSource: DataSource): PagingQueryProvider {
        val queryProviderFactoryBean = SqlPagingQueryProviderFactoryBean()
        val map = HashMap<String, Order>()
        map["id"] = Order.ASCENDING

        queryProviderFactoryBean.setDataSource(dataSource)
        queryProviderFactoryBean.setSelectClause("id, amount, tx_name, tx_date_time")
        queryProviderFactoryBean.setFromClause("pay")
        queryProviderFactoryBean.setWhereClause("amount >= :amount")
        queryProviderFactoryBean.setSortKeys(map)

        return queryProviderFactoryBean.`object`
    }

    @Bean
    fun jdbcPagingItemReader(dataSource: DataSource): JdbcPagingItemReader<Pay> {
        val parameterValues = HashMap<String, Any>()
        parameterValues["amount"] = 2000

        return JdbcPagingItemReaderBuilder<Pay>()
            .name("jdbcPagingItemReader")
            .pageSize(10)
            .dataSource(dataSource)
            .rowMapper(BeanPropertyRowMapper(Pay::class.java))
            .parameterValues(parameterValues)
            .queryProvider(createQueryProvider(dataSource))
            .build()
    }

    @Bean
    fun jdbcPagingItemWriter(): ItemWriter<Pay> =
        ItemWriter { items -> items.forEach { log.info("Current Pay={}", it.toString()) } }

    @Bean
    fun jdbcPagingItemReaderStep(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager,
        jdbcPagingItemReader: JdbcPagingItemReader<Pay>
    ): Step {
        log.info(">>>> jdbcPagingItemReaderStep")
        return StepBuilder("jdbcPagingItemReaderStep", jobRepository)
            .chunk<Pay, Pay>(10, platformTransactionManager)
            .reader(jdbcPagingItemReader)
            .writer(jdbcPagingItemWriter())
            .build()
    }

    @Bean
    fun jdbcPagingItemReaderJob(jobRepository: JobRepository, jdbcPagingItemReaderStep: Step): Job {
        log.info(">>>> jdbcPagingItemReaderJob")
        return JobBuilder("jdbcPagingItemReaderJob", jobRepository)
            .start(jdbcPagingItemReaderStep)
            .build()
    }
}