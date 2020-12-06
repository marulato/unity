package org.legion.unity.admin.entity;

import org.legion.unity.common.jpa.annotation.Entity;
import org.legion.unity.common.jpa.annotation.PrimaryKey;

@Entity(tableName = "MC_DISTRICT")
public class District {

    @PrimaryKey(autoIncrement = false)
    private Integer id;
    private Integer parentId;
    private String name;
    private String shortName;
    private Double longitude;
    private Double latitude;
    private Integer level;
    private Integer sort;
    private Integer status;

    @Override
    public String toString() {
        return "District{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", level=" + level +
                ", sort=" + sort +
                ", status=" + status +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
