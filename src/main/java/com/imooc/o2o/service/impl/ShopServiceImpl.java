package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.SocketHandler;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;

    @Override  //显示店铺列表
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex= PageCalculator.calculateRowIndex(pageIndex,pageSize);//将前端页码转换成后端函数，用到工具类 PageCalculator
        List<Shop> shopList= shopDao.queryShopList(shopCondition,rowIndex,pageSize);//用来返回需要的店铺列表
        int count=shopDao.queryShopCount(shopCondition);//获取总数
        ShopExecution se=new ShopExecution();
        if (shopList!=null){
            se.setShopList(shopList);
            se.setCount(count);
        }else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    @Override//通过店铺Id查询店铺
    @Transactional//事务注解
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override //店铺修改
    @Transactional //添加事务注解
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException{
        if(shop==null||shop.getShopId()==null){
            return new ShopExecution(ShopStateEnum.NULL_SHOPID);//返回店铺状态
        }else {
            try {
                //1.判断是否需要处理图片并且fileName不为空
                if (thumbnail.getImage() != null && thumbnail.getImageName()!=null) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId()); //先获取shop之前的地址？
                    if (tempShop.getShopImg() != null) { //如果之前的地址不为空，删除之前的地址
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop, thumbnail);//然后生成新的shopImg
                }
                //2.更新店铺信息
                shop.setLastEditTime(new Date());//获取最新的更改时间
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);//修改失败
                } else {
                    shop = shopDao.queryByShopId(shop.getShopId());//修改成功返回店铺详细信息
                    System.out.println(shop);//打印返回的店铺详细信息
                    return new ShopExecution(ShopStateEnum.SUCCESS,shop);//返回最新的店铺状态(之前这里少写了一个shop)
                }
            } catch (Exception e) {
                throw new ShopOperationException("modifyShop error:"+e.getMessage());//打印异常信息
            }
        }
    }

    @Override //店铺注册
    @Transactional //添加事务的注解
    public ShopExecution addShop(Shop shop,ImageHolder thumbnail) throws ShopOperationException{   //1.往数据库中添加店铺信息；2.添加店铺信息成功就会处理上传的图片；3.处理上传图片成功后就会把图片上传的地址更新到这个新增店铺信息中去
        //首先检查传入的参数是否合法(空值判断)
        if(shop==null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try{
            //初始化一些店铺信息的参数
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            
            //添加店铺信息
            int i = shopDao.insertShop(shop);
            //获取店铺的id
            if(i<=0){ //如果店铺获取失败
                throw new ShopOperationException("店铺创建失败");
            }else { //如果店铺获取成功
                if(thumbnail !=null){ //判断店铺图片是否为空
                    //存储店铺的图片地址
                    try {
                        addShopImg(shop, thumbnail);
                    }catch (Exception e){
                        throw new ShopOperationException("addShopImg error:"+e.getMessage());
                    }
                    //更新数据库中店铺的图片地址
                    int i1 = shopDao.updateShop(shop);
                    if(i1<=0){
                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }
        }catch(Exception e){ //将获取到的异常交给Controller层处理
            throw new ShopOperationException("addShop error"+e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);//返回shop的状态和shop的信息
    }

    private void addShopImg(Shop shop, ImageHolder thumbnail) {
        //1.通过店铺id获取shop图片目录的相对值路径
        String dest= PathUtil.getShopImagePath(shop.getShopId());
        //2.调用之前创建的ImageUtil的generateThumbnail（）方法存储图片并返回相应的相对值路径
        String shopImgAddr= ImageUtil.generateThumbnail(thumbnail,dest);
        shop.setShopImg(shopImgAddr);
    }
}
