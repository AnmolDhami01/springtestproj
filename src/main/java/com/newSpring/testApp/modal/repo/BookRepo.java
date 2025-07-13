package com.newSpring.testApp.modal.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newSpring.testApp.modal.BookModal;

public interface BookRepo extends JpaRepository<BookModal, Integer> {
    // You can add custom query methods here if needed
    BookModal findByName(String name);

    @Override
    public List<BookModal> filterBooksNative(String name, Long price, Long authorId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        StringBuilder sql = new StringBuilder("SELECT * FROM books WHERE 1=1 ");
        Map<String, Object> params = new HashMap<>();

        // Define conditions and values
        List<FilterCondition> filters = List.of(
                new FilterCondition("LOWER(name) LIKE :name", name != null && !name.isBlank(), "name", name != null ? "%" + name.toLowerCase() + "%" : null),
                new FilterCondition("price = :price", price != null, "price", price),
                new FilterCondition("author_id = :authorId", authorId != null, "authorId", authorId),
                new FilterCondition("created_at >= :startDate", startDate != null, "startDate", startDate),
                new FilterCondition("created_at <= :endDate", endDate != null, "endDate", endDate)
        );

        // Build query
        for (FilterCondition filter : filters) {
            if (filter.shouldApply) {
                sql.append("AND ").append(filter.sqlSnippet).append(" ");
                params.put(filter.paramName, filter.paramValue);
            }
        }

        Query query = entityManager.createNativeQuery(sql.toString(), BookModal.class);
        params.forEach(query::setParameter);

        return query.getResultList();
    }
}