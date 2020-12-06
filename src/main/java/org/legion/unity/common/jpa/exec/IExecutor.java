package org.legion.unity.common.jpa.exec;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.UpdateProvider;
import org.legion.unity.common.base.BasePO;
import org.legion.unity.common.jpa.SimpleSQLGenerator;


@Mapper
public interface IExecutor {

    @InsertProvider(type = SimpleSQLGenerator.class, method = "insert")
    void insert(BasePO po);

    @UpdateProvider(type = SimpleSQLGenerator.class, method = "update")
    void update(BasePO po);

    @DeleteProvider(type = SimpleSQLGenerator.class, method = "delete")
    void delete(BasePO po);

}
