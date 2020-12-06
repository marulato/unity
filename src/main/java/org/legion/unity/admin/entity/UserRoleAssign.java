package org.legion.unity.admin.entity;

import org.legion.unity.common.base.BasePO;
import org.legion.unity.common.jpa.annotation.Entity;
import org.legion.unity.common.jpa.annotation.PrimaryKey;

@Entity(tableName = "USR_ROLE_ASSIGN")
public class UserRoleAssign extends BasePO {

    @PrimaryKey(autoIncrement = true)
    private Long id;
    private String userId;
    private String roleId;
    private String approvedBy;
    private String remarks;

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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
