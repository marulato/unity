package org.legion.unity.admin.entity;

import org.legion.unity.common.base.BasePO;
import org.legion.unity.common.jpa.annotation.Entity;
import org.legion.unity.common.jpa.annotation.PrimaryKey;
import java.util.Date;

@Entity(tableName = "USR_UN_LOGIN_HIS")
public class UnknownLoginHistory extends BasePO {

    @PrimaryKey(autoIncrement = true)
    private Long id;
    private String userId;
    private Date loginAt;
    private String ipAddress;
    private String browser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getLoginAt() {
        return loginAt;
    }

    public void setLoginAt(Date loginAt) {
        this.loginAt = loginAt;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }
}
