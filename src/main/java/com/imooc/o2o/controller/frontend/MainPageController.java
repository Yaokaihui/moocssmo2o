package com.imooc.o2o.controller.frontend;

import com.imooc.o2o.entity.HeadLine;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.service.HeadLineService;
import com.imooc.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")//前端展示系统（顾客）的根路由
public class MainPageController {
	@Autowired
	private ShopCategoryService shopCategoryService;  //用来处理前端的店铺类别（不是商品类别）
	@Autowired
	private HeadLineService headLineService;

	/**
	 * 初始化前端展示系统的主页信息，包括获取一级店铺类别列表以及头条列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listmainpageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listMainPageInfo() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();//用来接收一级店铺分类列表
		try {
			// 获取一级店铺类别列表(即parentId为空的ShopCategory就是最高级店铺列表)
			shopCategoryList = shopCategoryService.getShopCategoryList(null);
			modelMap.put("shopCategoryList", shopCategoryList);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		List<HeadLine> headLineList = new ArrayList<HeadLine>();//获取头条信息
		try {
			// 获取状态为可用(1)的头条列表
			HeadLine headLineCondition = new HeadLine();
			headLineCondition.setEnableStatus(1);//为1表示头条状态为可用
			headLineList = headLineService.getHeadLineList(headLineCondition);
			modelMap.put("headLineList", headLineList);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		modelMap.put("success", true);
		return modelMap;
	}

}
