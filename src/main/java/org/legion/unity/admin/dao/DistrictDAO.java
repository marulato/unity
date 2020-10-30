package org.legion.unity.admin.dao;

import org.legion.unity.admin.entity.District;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictDAO extends CrudRepository<District, Integer>, JpaSpecificationExecutor<District> {


}
