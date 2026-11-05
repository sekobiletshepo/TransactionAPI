package application.service.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder

public class CategoryBreakdownDto {

        Long categoryId;
        String categoryName;
        BigDecimal total;
        long transactionCount;
}
