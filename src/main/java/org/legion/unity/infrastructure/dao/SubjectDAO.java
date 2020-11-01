package org.legion.unity.infrastructure.dao;

import org.legion.unity.infrastructure.entity.Subject;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubjectDAO extends CrudRepository<Subject, String>, JpaSpecificationExecutor<Subject> {

    @Query("SELECT S FROM Subject S WHERE S.id = S.topId AND S.id = S.subId")
    List<Subject> findCategories();

    @Query("SELECT S FROM Subject  S WHERE S.id = S.subId AND S.topId = ?1")
    List<Subject> findSecondLevelSubjects(String topId);
}
