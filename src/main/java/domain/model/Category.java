package domain.model;

import domain.exception.DomainException;
import lombok.Builder;
import lombok.Getter;
import lombok.With;

@Getter
@Builder
@With

public class Category {

    private Long categoryId;
    private Long userId;
    private Long parentCategoryId;
    private String name;
    private boolean systemDefault;

    public Category () {
        if (categoryId != null && categoryId.equals(parentCategoryId))
            throw new DomainException("CATEGORY_ID_NOT_FOUND_OR_EXISTS", null + " Category duplicates/Does not exist");

        if (systemDefault && userId != null)
            throw new DomainException("USER_ID_NOT_FOUND", null + " UserId is not found");

        if (!systemDefault && userId == null)
            throw new DomainException("USER_ID_ALREADY_EXISTS", null + " UserId is null");

        if (name == null || name.isBlank())
            throw new DomainException("CATEGORY_NAME_BLANK", null + " Name is required");
    }

}
