package org.legion.unity.admin.entity;


import org.legion.unity.common.base.BasePO;
import org.legion.unity.common.jpa.annotation.Entity;
import java.util.Date;

@Entity(tableName = "USR_LOGIN_HIS")
public class UserLoginHistory extends BasePO {

    private String userId;
    private String acctStatus;
    private Date loginAt;
    private Integer loginStatus;
    private String ipAddress;
    private String browser;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAcctStatus() {
        return acctStatus;
    }

    public void setAcctStatus(String acctStatus) {
        this.acctStatus = acctStatus;
    }

    public Date getLoginAt() {
        return loginAt;
    }

    public void setLoginAt(Date loginAt) {
        this.loginAt = loginAt;
    }

    public Integer getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
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
