package web.response;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Value
@Builder

public class AggregateResponse {

    LocalDate from;
    LocalDate to;
    String currency;
    BigDecimal grandTotal;
    List<categoryRow> categories;

    @Value
    @Builder
    public static class CategoryRow {

        Long categoryId;
        String categoryName;
        BigDecimal total;
        long transactionCount;
    }
}
