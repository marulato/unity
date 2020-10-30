package org.legion.unity.general.dao;

import org.legion.unity.general.entity.MasterCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterCodeDAO extends CrudRepository<MasterCode, Integer> {

    MasterCode findByTypeAndAndCode(String type, String code);
}
