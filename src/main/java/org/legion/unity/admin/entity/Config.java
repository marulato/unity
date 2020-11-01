package org.legion.unity.admin.entity;


import org.legion.unity.admin.pk.ConfigPK;
import org.legion.unity.common.base.BasePO;

import javax.persistence.*;

@Entity
@Table(name = "SYS_CONFIG")
@IdClass(ConfigPK.class)
public class Config extends BasePO {

    @Id
    private String configKey;
    @Id
    private String configValue;
    private String type;
    private String profile;
    private String description;
    private String isSystem;
    private String isEditable;
    private String isNeedRestart;

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(String isSystem) {
        this.isSystem = isSystem;
    }

    public String getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(String isEditable) {
        this.isEditable = isEditable;
    }

    public String getIsNeedRestart() {
        return isNeedRestart;
    }

    public void setIsNeedRestart(String isNeedRestart) {
        this.isNeedRestart = isNeedRestart;
    }
}
