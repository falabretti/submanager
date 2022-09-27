package io.submanager.repository.specification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCriteria<T extends Comparable> {

    private String key;
    private SearchOperation operation;
    private Comparable value;
}
