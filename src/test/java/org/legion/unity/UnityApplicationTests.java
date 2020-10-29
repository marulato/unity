package org.legion.unity;

import org.junit.jupiter.api.Test;
import org.legion.unity.admin.dao.DistrictDAO;
import org.legion.unity.common.utils.SpringUtils;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UnityApplicationTests {

    @Test
    void contextLoads() {
        DistrictDAO districtDAO = SpringUtils.getBean(DistrictDAO.class);
        System.out.println(districtDAO.findById(320623).get().getName());
    }

}
