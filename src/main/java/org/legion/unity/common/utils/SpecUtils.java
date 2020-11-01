package org.legion.unity.common.utils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.criteria.Predicate;

public class SpecUtils {

    public static List<Predicate> allEqual(Root<?> root, CriteriaBuilder cb, Map<String, Object> params) {
        List<Predicate> predicateList = new ArrayList<>();
        if (root != null && cb != null && params != null) {
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                if (params.get(key) != null) {
                    predicateList.add(cb.equal(root.get(key).as(params.get(key).getClass()), params.get(key)));
                }
            }
        }
        return predicateList;
    }
}
