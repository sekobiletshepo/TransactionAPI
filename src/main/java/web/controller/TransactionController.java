package web.controller;

import application.service.AggregationService;
import domain.repository.TransactionRepository.TransactionQuery;
import application.service.dto.CategoryBreakdownDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.request.AggregateQueryRequest;
import web.response.AggregateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor

public class TransactionController {

    private final AggregationService aggregationService;

    @GetMapping("/aggregates")

    public AggregateResponse aggregates(AggregateQueryRequest req) {
        Long userId = 1L;

        LocalDate from = req.getFrom();
        LocalDate to = req.getTo();

        List<CategoryBreakdownDto> rows =
                aggregationService.breakdownByCategory(userId, req.getAccountId(), from, to);

       BigDecimal grandTotal = rows.stream()
               .map(categoryBreakdownDto::getTotal)
               .reduce(BigDecimal.ZERO, BigDecimal::add);

        return AggregateResponse.builder()
                .from(from)
                .to(to)
                .currency("R")
                grandTotal(grandTotal)
                .categories(rows.stream()
                        .map(r -> AggregateResponse.CategoryRow.builder()
                        .categoryId(r.getCategoryId())
                        .categoryName(r.getCategoryName())
                        .total(r.getTotal())
                        .transactionCount(r.getTransactionCount())
                        .build())
                .toList())
                .build();
    }
}





