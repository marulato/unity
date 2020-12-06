package org.legion.unity.common.jpa;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.legion.unity.common.base.BasePO;
import org.legion.unity.common.cache.CachePool;
import org.legion.unity.common.cache.ORMCache;
import org.legion.unity.common.utils.StringUtils;

public class SimpleSQLGenerator extends AbstractSQLGenerator {

    private final ORMCache ormCache = CachePool.getCache(ORMCache.KEY, ORMCache.class);
    private static final Cache<String, String> sqlCache = CacheBuilder.newBuilder().build();

    @Override
    public String insert(BasePO entity) {
        ORMEntity ormEntity = ormCache.get(entity.getClass());
        String sql = sqlCache.getIfPresent(ormEntity.getTableName() + ":INSERT");
        if (StringUtils.isBlank(sql)) {
            String insert = "INSERT INTO " + ormEntity.getTableName() +
                    insertClause(ormEntity);
            sqlCache.put(ormEntity.getTableName() + ":INSERT", insert);
            return insert;
        }
        return sql;
    }

    @Override
    public String update(BasePO entity) {
        ORMEntity ormEntity = ormCache.get(entity.getClass());
        String sql = sqlCache.getIfPresent(ormEntity.getTableName() + ":UPDATE");
        if (StringUtils.isBlank(sql)) {
            String update = "UPDATE " + ormEntity.getTableName() +
                    updateClause(ormEntity);
            sqlCache.put(ormEntity.getTableName() + ":UPDATE", update);
            return update;
        }
        return sql;
    }

    @Override
    public String delete(BasePO entity) {
        ORMEntity ormEntity = ormCache.get(entity.getClass());
        String sql = sqlCache.getIfPresent(ormEntity.getTableName() + ":DELETE");
        if (StringUtils.isBlank(sql)) {
            String delete = "DELETE FROM " + ormEntity.getTableName() +
                    whereClause(ormEntity);
            sqlCache.put(ormEntity.getTableName() + ":DELETE", delete);
            return delete;
        }
        return sql;
    }
}
