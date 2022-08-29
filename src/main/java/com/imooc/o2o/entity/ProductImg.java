package com.imooc.o2o.entity;

import java.util.Date;

//商品图片实体类
public class ProductImg {
    private Long productImgId;
    private String imgAddr;//图片地址
    private String imgDesc;//图片说明（描述）
    private Integer priority;
    private Date createTime;
    private Long productId;//表示属于哪个商品的详情图片（多对一的关系，多张图片表示一个商品）

    public Long getProductImgId() {
        return productImgId;
    }

    public void setProductImgId(Long productImgId) {
        this.productImgId = productImgId;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
