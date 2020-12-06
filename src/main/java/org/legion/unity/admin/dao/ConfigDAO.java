package org.legion.unity.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.legion.unity.admin.entity.Config;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ConfigDAO  {

    @Select("SELECT * FROM SYS_CONFIG ORDER BY CONFIG_KEY")
    List<Config> findAll();

    @Select("SELECT * FROM SYS_CONFIG WHERE CONFIG_KEY = #{configKey}")
    Config findByConfigKey(String configKey);
}
