package com.sturdy.springbatch.appleciation.job.writer;

import com.sturdy.springbatch.appleciation.job.reader.jdbc.JpaPagingItemReaderJobConfiguration;
import com.sturdy.springbatch.appleciation.job.reader.jdbc.Pay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JpaBatchItemWriterJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private static final int chunkSize = 10;

    @Bean
    public Job jpaBatchItemWriterJob() {
        return jobBuilderFactory.get("jpaBatchItemWriterJob")
                .start(jpaBatchItemWriterStep())
                .build();
    }

    @Bean
    public Step jpaBatchItemWriterStep() {
        return stepBuilderFactory.get("jpaBatchItemWriterStep")
                .<Pay, Pay2> chunk(chunkSize)
                .reader(jpaBatchItemWriterReader())
                .processor(jpaItemProcessor())
                .writer(jpaBatchItemWriter())
                .build();
    }

    @Bean
    public ItemProcessor<Pay, Pay2> jpaItemProcessor() {
        return pay -> new Pay2(pay.getAmount(), pay.getTxName(), pay.getTxDateTime());
    }

    @Bean
    public JpaPagingItemReader<Pay> jpaBatchItemWriterReader() {
        return new JpaPagingItemReaderBuilder<Pay>()
                .name("jpaBatchItemWriter")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("select p from Pay p order by p.id")
                .build()
                ;
    }

    @Bean
    public JpaItemWriter<Pay2> jpaBatchItemWriter() {
        JpaItemWriter<Pay2> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }
}
