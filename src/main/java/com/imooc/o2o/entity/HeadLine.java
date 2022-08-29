package com.imooc.o2o.entity;

import java.util.Date;

//头条实体类
public class HeadLine {
    private Long lineID;
    private String lineName;
    private String lineLink;//头条链接
    private String lineImg;
    private Integer priority;//权重，就是一个优先级
    private Integer enableStatus;//0:不可用；1：可用（状态）
    private Date createTime;
    private Date lastEditTime;

    public Long getLineID() {
        return lineID;
    }

    public void setLineID(Long lineID) {
        this.lineID = lineID;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineLink() {
        return lineLink;
    }

    public void setLineLink(String lineLink) {
        this.lineLink = lineLink;
    }

    public String getLineImg() {
        return lineImg;
    }

    public void setLineImg(String lineImg) {
        this.lineImg = lineImg;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}
