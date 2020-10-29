package org.legion.unity.admin.entity;


import org.legion.unity.common.base.BasePO;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "UserRoleAssign")
public class UserRoleAssign extends BasePO {
    @Id
    private Long id;
    private String userId;
    private String roleId;
    private Date activatedAt;
    private Date deactivatedAt;
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

    public Date getActivatedAt() {
        return activatedAt;
    }

    public void setActivatedAt(Date activatedAt) {
        this.activatedAt = activatedAt;
    }

    public Date getDeactivatedAt() {
        return deactivatedAt;
    }

    public void setDeactivatedAt(Date deactivatedAt) {
        this.deactivatedAt = deactivatedAt;
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
