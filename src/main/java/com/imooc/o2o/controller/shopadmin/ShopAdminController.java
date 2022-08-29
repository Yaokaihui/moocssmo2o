package com.imooc.o2o.controller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopadmin",method = RequestMethod.GET)//店铺管理系统的根路由

//通过后台路由导入WEB-INF下的jsp/html文件
public class ShopAdminController {
    //添加店铺页面
    @RequestMapping(value = "/shopoperation",method =RequestMethod.GET )
    public String shopOperation(){  //用来做转接（转发）
        return "shop/shopoperation"; //返回的是WEB-INF下的shop包的shopoperation.html文件
    }

    //店铺列表管理页面
    @RequestMapping(value = "/shoplist")
    public String shopList(){  //用来做转接（转发）
        return "shop/shoplist"; //返回的是WEB-INF下的shop包的shoplist.html文件
    }

    //点击“进入”后显示的具体哪个店铺的管理页面
    @RequestMapping(value = "/shopmanagement")
    public String shopManagement(){  //用来做转接（转发）
        return "shop/shopmanagement"; //返回的是WEB-INF下的shop包的shopmanagement.html文件
    }

    //点击“商品分类”后跳转到分类商品列表页面
    @RequestMapping(value = "/productcategorymanagement",method = RequestMethod.GET)
    public String productCategoryManage(){  //用来做转接（转发）
        return "shop/productcategorymanagement"; //返回的是WEB-INF下的shop包的shopmanagement.html文件
    }

    //点击“商品添加”后跳转到商品添加页面进行添加操作
    @RequestMapping(value = "/productoperation",method = RequestMethod.GET)
    public String productOperation(){  //用来做转接（转发）
        return "shop/productoperation"; //返回的是WEB-INF下的shop包的productoperation.html文件
    }

    //点击“商品管理”后跳转到商品管理页面进行操作
    @RequestMapping(value = "/productmanagement",method = RequestMethod.GET)
    public String productManagement(){  //用来做转接（转发）
        return "shop/productmanagement"; //返回的是WEB-INF下的shop包的productmanagement.html文件
    }
}
