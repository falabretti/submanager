package io.submanager.repository.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

@Data
@AllArgsConstructor
public class GenericSpecification<T> implements Specification<T> {

    private SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        SearchOperation operation = searchCriteria.getOperation();

        Path<String> field = root.get(searchCriteria.getKey());
        Object value = searchCriteria.getValue();

        switch (operation) {
            case EQUALS:
                return criteriaBuilder.equal(field, value);
            default:
                return null;
        }
    }
}
