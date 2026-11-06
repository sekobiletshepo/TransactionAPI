package application.services;

import application.service.dto.CategoryDto;
import application.service.dto.TransactionDto;
import domain.exception.DomainException;
import domain.model.Category;
import domain.model.Transaction;
import domain.repository.AccountRepository;
import domain.repository.CategoryRepository;
import domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)


public class CategorizationService {

    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public Long suggestCategoryId(Long userId, Transaction tx) {
        String haystack = (tx.getMerchantName() == null ? "" : tx.getMerchantName())
                + " " + (tx.getRawDescription() == null ? "" : tx.getRawDescription());
        haystack = haystack.toLowerCase(Locale.ROOT);

        List<Category> visible = categoryRepository.findVisibleToUser(userId);

        for (Category category : visible) {
            String name = category.getName();
            if (name != null && haystack.contains(name.toLowerCase(Locale.ROOT))) {
                return category.getCategoryId();
            }
        }
        return null; // no match, it stays uncategorized //
    }

    @Transactional
    public TransactionDto recategorize(Long userId, Long transactionId, Long categoryId) {
        Transaction tx = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new DomainException(
                        "TX_NOT_FOUND", "transaction " + transactionId + " not found"));

        boolean owned = accountRepository.findById(tx.getAccountId())
                .map(a -> userId.equals(a.getUserId()))
                .orElse(false);
        if (!owned) {
            throw new DomainException(
                    "TX_NOT_FOUND", "transaction " + transactionId + " not found");

        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new DomainException(
                        "CATEGORY_NOT_FOUND", "category" + categoryId + " not found"));

        if (category.getUserId() != null && !category.getUserId().equals(userId)) {
            throw new DomainException(
                    "CATEGORY_NOT_FOUND", "category" + categoryId + " not found");
        }

        Transaction updated = tx.categorize(categoryId, Transaction.CategorizationMethod.MANUAL);
        return toDto(transactionRepository.save(updated));
    }

    public List<CategoryDto> listVisible(Long userId) {

        return categoryRepository.findVisibleToUser(userId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public CategoryDto createCategory(Long userId, String name, Long parentCategoryId) {
        if (name == null || name.isBlank()) {
            throw new DomainException(
                    "CATEGORY_NAME_EMPTY", " name is required");
        }
        String trimmed = name.trim();
        if (categoryRepository.existsByUserIdAndName(userId,name)) {
            throw new DomainException(
                    "CATEGORY_ALREADY_EXISTS", "category " + name + " already exists");
        }

        Category category = Category.builder()
                .userId(userId)
                .name(name.trim())
                .parentCategoryId(parentCategoryId)
                .systemDefault(false)
                .build();

        Category saved = categoryRepository.save(category);
        return toDto(saved);
    }

    private CategoryDto toDto(Category c) {
        return CategoryDto.builder()
                .categoryId(c.getCategoryId())
                .name(c.getName())
                .parentCategoryId(c.getParentCategoryId())
                .systemDefault(c.isSystemDefault())
                .build();
    }

    private TransactionDto toDto(Transaction t) {
        return TransactionDto.builder()
                .transactionId(t.getTransactionId())
                .accountId(t.getAccountId())
                .externalRef(t.getExternalRef())
                .amount(t.getAmount())
                .currency(t.getCurrency())
                .direction(t.getDirection())
                .transactionDate(t.getTransactionDate())
                .merchantName(t.getMerchantName())
                .rawDescription(t.getRawDescription())
                .categoryId(t.getCategoryId())
                .categorizationMethod(t.getCategorizationMethod())
                .createdAt(t.getCreatedAt())
                .build();
    }
}

