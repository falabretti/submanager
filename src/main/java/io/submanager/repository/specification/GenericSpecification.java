package io.submanager.repository.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class GenericSpecification<T> implements Specification<T> {

    private SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        SearchOperation operation = searchCriteria.getOperation();

        Path<Comparable> field = root.get(searchCriteria.getKey());
        Comparable value = searchCriteria.getValue();

        switch (operation) {
            case EQUALS:
                return criteriaBuilder.equal(field, value);
            case LESS_OR_EQUAL:
                return criteriaBuilder.lessThanOrEqualTo(field, value);
            default:
                return null;
        }
    }
}
