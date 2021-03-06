package org.legion.unity.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.legion.unity.admin.entity.User;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserDAO {

    @Select("SELECT * FROM USR_USER WHERE ID = #{id}")
    User findById(String id);
}
