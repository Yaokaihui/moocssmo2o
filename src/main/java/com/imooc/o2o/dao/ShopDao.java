package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {

    /**
     * 分页查询店铺，可输入的条件有：店铺名（模糊），店铺状态，店铺类别，区域Id,owner
     * @param shopCondition
     * @param rowIndex 从第几行开始取数据
     * @param pageSize 返回的条数
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);//rowIndex表示从第几行开始取数据，pageSize表示多少行进行分页

    /**
     * 返回queryShopList总数
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);

    /**
     * 通过shopId查询店铺信息
     * @param shopId
     * @return
     */
     Shop queryByShopId(long shopId);

    /**
     * 新增店铺
     * @param shop
     * @return 影响的行数（）
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺信息
     * @param shop
     * @return
     */
    int updateShop(Shop shop);
}
