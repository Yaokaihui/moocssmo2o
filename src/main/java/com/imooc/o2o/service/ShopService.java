package com.imooc.o2o.service;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.exceptions.ShopOperationException;

import java.io.File;
import java.io.InputStream;

public interface ShopService {

    /**
     * 通过shopCondition分页返回相应店铺列表
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);//返回ShopExecution对象

    /**
     * 通过店铺id查询店铺信息
     * @param shopId
     * @return
     */
    Shop getByShopId(long shopId);

    /**
     * 更新店铺信息，包括对图片的处理
     * @param shop 根据传入的shop实体类进行修改
    // * @param shopImgInputStream 如果有图片流就通过前端传进来并处理保存到一个新的路径下
    // * @param fileName
     * @return
     */
    ShopExecution modifyShop(Shop shop,ImageHolder thumbnail)throws ShopOperationException;

    /**
     * 注册店铺信息，包括对图片的处理
     * @param shop
    // * @param shopImgInputStream
    // * @param fileName
     * @return
     */
    ShopExecution addShop(Shop shop, ImageHolder thumbnail); //InputStream中获取无法文件的名字，所以要有参数fileName获取文件的扩展名
}
