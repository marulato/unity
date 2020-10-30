package org.legion.unity;

import org.junit.jupiter.api.Test;
import org.legion.unity.admin.dao.DistrictDAO;
import org.legion.unity.admin.entity.District;
import org.legion.unity.common.utils.SpringUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@SpringBootTest
class UnityApplicationTests {

    @Test
    void contextLoads() {
        DistrictDAO districtDAO = SpringUtils.getBean(DistrictDAO.class);
        System.out.println(districtDAO.findById(320623).get().getName());
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<District> districts = districtDAO.findAll(new Specification<District>() {
            @Override
            public Predicate toPredicate(Root<District> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        }, pageable);
        System.out.println(districts.toList());;
    }

}
