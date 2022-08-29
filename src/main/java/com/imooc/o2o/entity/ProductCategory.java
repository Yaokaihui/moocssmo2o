package com.imooc.o2o.entity;

import java.util.Date;

//商品类别实体类
public class ProductCategory {
    private Long productCategoryId;
    private Long shopId;//店铺ID，表明商品类别是哪个店铺下的类别
    private String productCategoryName;//商品类别的名称
    private Integer priority;//权重，越大排列越靠前
    private Date creatTime;

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}
