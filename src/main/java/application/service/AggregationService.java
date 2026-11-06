package application.service;

import application.service.dto.CategoryBreakdownDto;
import domain.repository.TransactionRepository;
import domain.repository.TransactionRepository.CategoryAggregate;
import domain.repository.TransactionRepository.TransactionQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class AggregationService {
    private final TransactionRepository transactionRepository;

    public List<CategoryBreakdownDto> breakdownByCategory(
            Long userId,
            Long accountId,
            LocalDate from,
            LocalDate to) {

        TransactionQuery query = new TransactionQuery(
                userId,
                accountId,
                null,
                from == null ? null : from.atStartOfDay(),
                to == null ? null : to.plusDays(1).atStartOfDay());

        List<CategoryAggregate> rows = transactionRepository.aggregateByCategory(query);

        BigDecimal grandTotal = rows.stream()
                .map(CategoryAggregate::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return rows.stream()
                .map(a -> CategoryBreakdownDto.builder()
                        .categoryId(a.categoryId())
                        .categoryName(a.categoryName())
                        .total(a.total())
                        .transactionCount(a.count())
                        .share(shareOf(a.total().abs(), grandTotal))
                        .build())
                .toList();
    }

    private BigDecimal shareOf(BigDecimal part, BigDecimal whole) {
        if (whole == null || whole.signum() == 0) return BigDecimal.ZERO;
        return part.divide(whole, 4, RoundingMode.HALF_UP);
    }
}


