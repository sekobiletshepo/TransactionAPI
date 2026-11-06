package org.example.transactionsapi;

import infrastructure.adapter.TransactionRepositoryAdapter;
import infrastructure.entity.AccountJpaEntity;
import infrastructure.entity.CategoryJpaEntity;
import infrastructure.entity.TransactionJpaEntity;
import infrastructure.mapper.TransactionMapper;
import infrastructure.repository.AccountJpaRepository;
import infrastructure.repository.TransactionJpaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import application.services.AggregationService;
import web.controller.TransactionController;

@SpringBootApplication
@EntityScan(basePackageClasses = {
        AccountJpaEntity.class,
        CategoryJpaEntity.class,
        TransactionJpaEntity.class
})
@EnableJpaRepositories(basePackageClasses = {
        AccountJpaRepository.class,
        TransactionJpaRepository.class
})
@Import({
        AggregationService.class,
        TransactionController.class,
        TransactionMapper.class,
        TransactionRepositoryAdapter.class
})
public class TransactionsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionsApiApplication.class, args);
    }

}
