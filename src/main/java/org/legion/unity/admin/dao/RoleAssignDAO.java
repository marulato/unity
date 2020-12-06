package org.legion.unity.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.legion.unity.admin.entity.UserRoleAssign;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RoleAssignDAO {

    @Select("SELECT * FROM USR_ROLE_ASSIGN WHERE USER_ID = #{userId}")
    UserRoleAssign findByUserId(String userId);
}
