package org.legion.unity.admin.dao;

import org.legion.unity.admin.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictDAO extends JpaRepository<District, Integer> {
}
