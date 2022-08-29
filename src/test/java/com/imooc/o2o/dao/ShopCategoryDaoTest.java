package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ShopCategory;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopCategoryDaoTest extends BaseTest {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    @Ignore
    public void testQueryShopCategory(){
        List<ShopCategory> shopCategoryList=shopCategoryDao.queryShopCategory(new ShopCategory());
        assertEquals(2,shopCategoryList.size());
        ShopCategory testCategory = new ShopCategory();
        ShopCategory parentCategory = new ShopCategory();
        parentCategory.setShopCategoryId(1l);
        testCategory.setParent(parentCategory);
        shopCategoryList=shopCategoryDao.queryShopCategory(testCategory);
        assertEquals(1,shopCategoryList.size());//选出parent_id=1l的所有数据
        System.out.println(shopCategoryList.get(0).getShopCategoryName());
    }

    @Test
    public void testQueryShopCategory1() {
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(null);
        System.out.println(shopCategoryList.size());
    }
}
