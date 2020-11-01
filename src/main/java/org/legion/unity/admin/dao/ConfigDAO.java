package org.legion.unity.admin.dao;

import org.legion.unity.admin.entity.Config;
import org.legion.unity.admin.pk.ConfigPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigDAO extends CrudRepository<Config, ConfigPK>, JpaSpecificationExecutor<Config> {

    Config findByConfigKey(String configKey);
}
