package org.legion.unity.common.jpa.exec;

import org.legion.unity.common.base.AppContext;
import org.legion.unity.common.base.BasePO;
import org.legion.unity.common.utils.Reflections;
import org.legion.unity.common.utils.SpringUtils;
import org.legion.unity.common.utils.StringUtils;
import java.lang.reflect.Field;
import java.util.Date;

public class DAO {

    private static final IExecutor executor = SpringUtils.getBean(IExecutor.class);

    public static void save(BasePO entity) {
        if (entity != null) {
            Date now = new Date();
            AppContext appContext = AppContext.getFromWebThread();
            entity.setCreatedAt(now);
            entity.setUpdatedAt(now);
            entity.setCreatedBy(appContext.getUserId());
            entity.setUpdatedBy(appContext.getUserId());
            formatEmptyString(entity);
            executor.insert(entity);
        }
    }

    public static void update(BasePO entity) {
        if (entity != null) {
            Date now = new Date();
            AppContext appContext = AppContext.getFromWebThread();
            entity.setUpdatedAt(now);
            entity.setUpdatedBy(appContext.getUserId());
            formatEmptyString(entity);
            executor.update(entity);
        }
    }

    public static void delete(BasePO entity) {
        if (entity != null) {
            executor.delete(entity);
        }
    }

    private static void formatEmptyString(BasePO po) {
        Class<?> type = po.getClass();
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType() == String.class) {
                try {
                    String value = (String) Reflections.getValue(field, po);
                    if (StringUtils.isEmpty(value)) {
                        field.set(po, null);
                    }
                } catch (Exception ignored) {

                }
            }
        }
    }
}
