package web.controller;

import application.service.dto.CategoryBreakdownDto;
import application.services.AggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.request.AggregateQueryRequest;
import web.response.AggregateResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final AggregationService aggregationService;

    @GetMapping("/aggregates")
    public AggregateResponse aggregates(@ModelAttribute AggregateQueryRequest req) {
        Long userId = 1L;

        LocalDate from = req.getFrom();
        LocalDate to = req.getTo();

        List<CategoryBreakdownDto> rows =
                aggregationService.breakdownByCategory(
                        userId, req.getAccountId(), req.getCategoryId(), from, to);

        BigDecimal grandTotal = rows.stream()
                .map(CategoryBreakdownDto::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return AggregateResponse.builder()
                .from(from)
                .to(to)
                .currency("R")
                .grandTotal(grandTotal)
                .categories(rows.stream()
                        .map(row -> AggregateResponse.CategoryRow.builder()
                                .categoryId(row.getCategoryId())
                                .categoryName(row.getCategoryName())
                                .total(row.getTotal())
                                .transactionCount(row.getTransactionCount())
                                .build())
                        .toList())
                .build();
    }
}
