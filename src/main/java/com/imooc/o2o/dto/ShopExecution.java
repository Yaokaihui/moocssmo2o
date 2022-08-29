package com.imooc.o2o.dto;

import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;

import java.util.List;

public class ShopExecution { //存储店铺信息，存储店铺状态值
    //结果状态
    private int state;

    //状态标识
    private String stateInfo;

    //店铺数量
    private int count;

    //操作的shop(增删改店铺的时候用到)
    private Shop shop;

    //shop列表（查询店铺列表的时候使用）
    private List<Shop> shopList;

    public ShopExecution(){ //默认的构造函数

    }

    //针对店铺操作失败时的构造器
    public ShopExecution(ShopStateEnum stateEnum){ //构造器带枚举类型的参数,存储状态值（这个枚举类型需要创建出来）
        this.state=stateEnum.getState();//获取状态值
        this.stateInfo=stateEnum.getStateInfo();//获取对状态值的说明
    }
    //针对店铺操作成功时的构造器(返回单个shop)
    public ShopExecution(ShopStateEnum stateEnum,Shop shop){ //构造器带枚举类型的参数,存储状态值（这个枚举类型需要创建出来）
        this.state=stateEnum.getState();//获取状态值
        this.stateInfo=stateEnum.getStateInfo();//获取对状态值的说明
        this.shop=shop;
    }
    //针对店铺操作成功时的构造器（返回shop列表）
    public ShopExecution(ShopStateEnum stateEnum,List<Shop> shopList){ //构造器带枚举类型的参数,存储状态值（这个枚举类型需要创建出来）
        this.state=stateEnum.getState();//获取状态值
        this.stateInfo=stateEnum.getStateInfo();//获取对状态值的说明
        this.shopList=shopList;
    }

    //添加get()和set()方法

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}



