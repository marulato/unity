package org.legion.unity.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.legion.unity.admin.entity.UserRole;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RoleDAO {

    @Select("SELECT * FROM USR_ROLE WHERE ID = #{id}")
    UserRole findById(String id);
}
