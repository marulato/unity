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

    public BaseVO() {
    }

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
                    Reflections.setValue(voField, this, Reflections.getValue(poField, po));
                } catch (Exception ignored) {

                }
            }
            Field[] auditFields = basePOClass.getDeclaredFields();
            for (Field auditField : auditFields) {
                try {
                    Field voAuditField = baseVOClass.getDeclaredField(auditField.getName());
                    if (auditField.getType() == Date.class) {
                        Reflections.setValue(voAuditField, this,
                                DateUtils.getDateString((Date) Reflections.getValue(auditField, po), "yyyy/MM/dd"));
                    } else {
                        Reflections.setValue(voAuditField, this, Reflections.getValue(auditField, po));
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
