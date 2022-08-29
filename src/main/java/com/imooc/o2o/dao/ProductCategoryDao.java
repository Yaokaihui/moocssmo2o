package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao {
    /**
     * 通过shopId查询店铺商品类别
     * @param shopId
     * @return
     */
    List<com.imooc.o2o.entity.ProductCategory> queryProductCategoryList(long shopId);

    /**
     * 批量新增商品类别
     *
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    /**
     * 删除指定商品类别
     *
     * @param productCategoryId
     * @param shopId
     * @return effectedNum
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);//因为有两个参数，用@Param注解
}
