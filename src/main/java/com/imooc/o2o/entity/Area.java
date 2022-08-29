package com.imooc.o2o.entity;

import java.util.Date;
//区域实体类
public class Area {

    private Integer areaId;//ID
    private String areaName;//名称
    private Integer priority;//权重，用来做排序
    private Date cerattime;//创建时间
    private  Date lastEditTime;//更新时间

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCerattime() {
        return cerattime;
    }

    public void setCerattime(Date cerattime) {
        this.cerattime = cerattime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}
