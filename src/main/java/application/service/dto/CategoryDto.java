package application.service.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value

public class CategoryDto {

    Long categoryId;
    String name;
    Long parentCategoryId;
    boolean systemDefault;
}
