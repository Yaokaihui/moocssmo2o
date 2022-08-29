package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;

    @Test
    @Ignore  //用来不再执行这个程序
    public void testInsertShop(){
        Shop shop = new Shop();
        PersonInfo owner=new PersonInfo();
        Area area=new Area();
        ShopCategory shopCategory=new ShopCategory();
        owner.setUserId(1);
        area.setAreaId(1);
        shopCategory.setShopCategoryId(1l);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(0);
        shop.setAdvice("审核中");
        int effectedNum= shopDao.insertShop(shop);//影响的行数
        assertEquals(1,effectedNum);
    }

    @Test
    @Ignore  //用来表示不再执行这个程序
    public void testUpdateShop(){
        Shop shop = new Shop();
        shop.setShopId(1l);//指定修改的是哪个店铺
        PersonInfo owner=new PersonInfo();
        Area area=new Area();
        ShopCategory shopCategory=new ShopCategory();
        owner.setUserId(1);
        area.setAreaId(1);
        shopCategory.setShopCategoryId(1l);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺");
        shop.setShopDesc("测试描述");
        shop.setShopAddr("测试地址");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(0);
        shop.setAdvice("审核中");
        int effectedNum= shopDao.updateShop(shop);//影响的行数
        assertEquals(1,effectedNum);
    }

    @Test
    @Ignore
    public void testQueryByShopId(){
        long shopId=1;
        Shop shop=shopDao.queryByShopId(shopId);
        System.out.println("areaId:"+shop.getArea().getAreaId());
        System.out.println("areaName:"+shop.getArea().getAreaName());
    }

    @Test
    @Ignore
    public void testQueryShopList(){
        Shop shopCondition=new Shop();
        PersonInfo owner=new PersonInfo();
        owner.setUserId(1l);
        shopCondition.setOwner(owner);
        List<Shop> shopList=shopDao.queryShopList(shopCondition,0,2);
        System.out.println("店铺列表的大小"+shopList.size());
    }

    @Test

    public void testQueryShopListAndCount(){
        Shop shopCondition=new Shop();
        ShopCategory childCategory=new ShopCategory();
        ShopCategory parentCategory=new ShopCategory();
        parentCategory.setShopCategoryId(12l);//数据库中的一级列表的美食饮品
        childCategory.setParent(parentCategory);//作为一级列表美食饮品的下一级列表
        shopCondition.setShopCategory(childCategory);//通过childCategory来设置二级店铺有哪些
        List<Shop> shopList=shopDao.queryShopList(shopCondition,0,6);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("店铺列表的大小"+shopList.size());
        System.out.println("店铺总数："+count);
//        ShopCategory sc=new ShopCategory();
//        sc.setShopCategoryId(2L);//设置shopCategoryId为2的条件来查询有多少店铺
//        shopCondition.setShopCategory(sc);
//        shopList=shopDao.queryShopList(shopCondition,0,2);
//        System.out.println("分类新店铺列表的大小"+shopList.size());
//        count= shopDao.queryShopCount(shopCondition);
//        System.out.println("分类新店铺总数："+count);
    }
    }

