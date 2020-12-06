package org.legion.unity.admin.entity;

import org.legion.unity.common.base.BasePO;
import org.legion.unity.common.jpa.annotation.Entity;
import org.legion.unity.common.jpa.annotation.PrimaryKey;

@Entity(tableName = "USR_ROLE")
public class UserRole extends BasePO {

    @PrimaryKey(autoIncrement = false)
    private String id;
    private String name;
    private String type;
    private String profile;
    private String description;
    private String isSystem;
    private String landingPage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLandingPage() {
        return landingPage;
    }

    public void setLandingPage(String landingPage) {
        this.landingPage = landingPage;
    }
}
