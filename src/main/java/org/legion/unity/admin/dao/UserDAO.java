package org.legion.unity.admin.dao;

import org.legion.unity.admin.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User, String>, JpaSpecificationExecutor<User> {
}
