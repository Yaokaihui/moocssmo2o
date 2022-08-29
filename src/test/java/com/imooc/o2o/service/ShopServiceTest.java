package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void testGetShopList(){
        Shop shopCondition=new Shop();
        ShopCategory sc=new ShopCategory();
        sc.setShopCategoryId(2l);
        shopCondition.setShopCategory(sc);
        ShopExecution se=shopService.getShopList(shopCondition,2,2);//从第二页开始计数，表示第二页有几条（此时pageSize=2，第二页不超过2条）
        System.out.println("店铺列表数为："+se.getShopList().size());
        System.out.println("店铺总数为："+se.getCount());
    }

    @Test
    @Ignore
    public void testModifyShop()throws ShopOperationException,FileNotFoundException{
        Shop shop=new Shop();
        shop.setShopId(1l);
        shop.setShopName("修改后的店铺名称");
        File shopImg=new File("C:\\Users\\尧凯辉\\Pictures\\Saved Pictures\\保存的图片\\1.jpg");
        InputStream is=new FileInputStream(shopImg);
        ImageHolder imageHolder = new ImageHolder("1.jpg", is);
        ShopExecution shopExecution = shopService.modifyShop(shop, imageHolder);
        System.out.println("新图片的地址："+shopExecution.getShop().getShopImg());
    }

    @Test
    @Ignore
    public void testAddShop() throws ShopOperationException,FileNotFoundException {  //抛出文件不存在的异常
        Shop shop = new Shop();
        PersonInfo owner=new PersonInfo();
        Area area=new Area();
        ShopCategory shopCategory=new ShopCategory();
        owner.setUserId(1);
        area.setAreaId(3);
        shopCategory.setShopCategoryId(1l);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺1");
        shop.setShopDesc("测试描述1");
        shop.setShopAddr("测试地址1");
        shop.setPhone("test1");
        shop.setShopImg(shop.getShopImg());
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());//获取初始化的值
        shop.setAdvice("审核中");
        //初始化文件并转换成inputStream
        File shopImg=new File("C:\\Users\\尧凯辉\\Pictures\\Saved Pictures\\保存的图片\\bz1.jpg");
        InputStream  is= new FileInputStream(shopImg);
        //开始添加shop数据,并返回addShop()方法的ShopExecution(ShopStateEnum.CHECK,shop);//返回shop的状态和shop的信息
        ImageHolder imageHolder = new ImageHolder(shopImg.getName(), is);
        ShopExecution shopExecution = shopService.addShop(shop, imageHolder );
        assertEquals(ShopStateEnum.CHECK.getState(),shopExecution.getState());
    }

}
