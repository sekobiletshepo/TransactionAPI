package domain.repository;

import domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Category save(Category category);

    Optional<Category> findById (Long categoryId);

    /* All categories are visible to the user */
    List<Category> findVisibleToUser(Long userId);

    List<Category> findBySystemDefaultTrue();

    Optional<Category> findByUserIdAndName (Long userId, String name);

    Optional<Category> findSystemDefaultByName (String name);

    /* Hierarchal lookup for the categories */
    List<Category> findByParentCategoryId(Long parentCategoryId);

    boolean existsByUserIdAndName (Long userId, String name);

    void deleteById(Long categoryId);
}
