package com.imooc.o2o.entity;

import java.util.Date;
import java.util.List;

//商品实体类
public class Product {
    private Long productId;
    private String productName;
    private String productDesc;//商品描述
    private String imgAddr;//商品缩略图的地址
    private String normalPrice;//商品的原价
    private String promotionPrice;//商品的折扣价
    private Integer point;//商品积分
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    private Integer enableStatus;//商品的状态（ 0：下架; 1:在前端展示系统展示）

    private List<ProductImg>productImgList;//List(商品详情图片列表)
    private ProductCategory productCategory;//商品类别实体类，表示商品是属于哪一个商品类别的
    private Shop shop;//店铺类，表示商品是属于哪一个店铺


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String produceName) {
        this.productName = produceName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String produceDesc) {
        this.productDesc = produceDesc;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
    }

    public String getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(String normalPrice) {
        this.normalPrice = normalPrice;
    }

    public String getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(String promotionPrice) {
        this.promotionPrice = promotionPrice;
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

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public List<ProductImg> getProductImgList() {
        return productImgList;
    }

    public void setProductImgList(List<ProductImg> productImgList) {
        this.productImgList = productImgList;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
