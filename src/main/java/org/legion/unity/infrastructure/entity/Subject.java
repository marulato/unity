package org.legion.unity.infrastructure.entity;

import org.legion.unity.common.base.BasePO;
import org.legion.unity.common.base.Order;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CM_SUBJECT")
public class Subject extends BasePO {
    @Id
    @Order(0)
    private String id;
    private String topId;
    private String subId;
    @Order(1)
    private String name;
    private String description;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopId() {
        return topId;
    }

    public void setTopId(String topId) {
        this.topId = topId;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
