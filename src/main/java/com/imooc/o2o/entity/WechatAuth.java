package com.imooc.o2o.entity;

import java.util.Date;

//微信账户
public class WechatAuth {
    private Long wechatAuthId;
    private String openId;//绑定微信公众号
    private Date createTime;
    private PersonInfo personInfo;//创建用户实体类与本地用户账号表进行关联，直接创建一个用户的实体类

    public Long getWechatAuthId() {
        return wechatAuthId;
    }

    public void setWechatAuthId(Long wechatAuthId) {
        this.wechatAuthId = wechatAuthId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }
}
