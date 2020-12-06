package org.legion.unity.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.legion.unity.admin.entity.District;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DistrictDAO  {

    @Select("SELECT * FROM MC_DISTRICT WHERE ID = #{id}")
    District findById(Integer id);
}
