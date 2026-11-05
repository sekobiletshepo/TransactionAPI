package domain;

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
    private Boolean systemDefault;

    public Category () {
        if (categoryId != null && categoryId.equals(parentCategoryId))
            throw new IllegalArgumentException("categoryId and parentCategoryId can't be the same");

        if (systemDefault && userId != null)
            throw new IllegalArgumentException("systemDefault and userId cant have a userId");

        if (!systemDefault && userId == null)
            throw new IllegalArgumentException("custom categories must have a userId");

        if (name == null || name.isBlank())
            throw new IllegalArgumentException("name is required");
    }

}
