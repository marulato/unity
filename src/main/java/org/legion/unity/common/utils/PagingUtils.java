package org.legion.unity.common.utils;

import org.legion.unity.common.base.SearchParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PagingUtils {

    public static Pageable defaultPaging(int pageNo, int pageSize) {
        return PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, "id"));
    }

    public static Pageable reversePaging(int pageNo, int pageSize) {
        return PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
    }

    public static Pageable paging(int pageNo, int pageSize, String order, String property) {
        if ("ASC".equalsIgnoreCase(order)) {
            return PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, property));
        } else {
            return PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, property));
        }
    }

    public static Pageable paging(SearchParam param) {
        if (param != null) {
            return paging(param.getPageNo(), param.getPageSize(), param.getOrder(), param.getOrderProperty());
        }
        return null;
    }
}
