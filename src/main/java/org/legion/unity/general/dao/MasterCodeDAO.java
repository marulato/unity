package org.legion.unity.general.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.legion.unity.general.entity.MasterCode;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MasterCodeDAO {

    @Select("SELECT * FROM ")
    MasterCode findByTypeAndAndCode(String type, String code);
}
