package org.legion.unity.common.base;


import org.legion.unity.common.utils.DateUtils;
import org.legion.unity.common.utils.Reflections;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

public class BaseVO implements Serializable {

    private String createdAt;
    private String createdBy;
    private String updatedAt;
    private String updatedBy;

    public BaseVO() {}

    public BaseVO(BasePO po) {
        if (po != null) {
            Class<?> poClass = po.getClass();
            Class<?> voClass = this.getClass();
            Class<?> basePOClass = poClass.getSuperclass();
            Class<?> baseVOClass = voClass.getSuperclass();
            Field[] poFields = poClass.getDeclaredFields();
            for (Field poField : poFields) {
                try {
                    Field voField = voClass.getDeclaredField(poField.getName());
                    Reflections.setValue(voField, voClass, this, Reflections.getValue(poField, poClass, po));
                } catch (Exception ignored) {

                }
            }
            Field[] auditFields = basePOClass.getDeclaredFields();
            for (Field auditField : auditFields) {
                try {
                    Field voAuditField = baseVOClass.getDeclaredField(auditField.getName());
                    if (auditField.getType() == Date.class) {
                        Reflections.setValue(voAuditField, baseVOClass, this,
                                DateUtils.getDateString((Date) Reflections.getValue(auditField, basePOClass, po), "yyyy/MM/dd"));
                    } else {
                        Reflections.setValue(voAuditField, baseVOClass, this, Reflections.getValue(auditField, basePOClass, po));
                    }
                } catch (Exception ignored) {

                }
            }
        }
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
