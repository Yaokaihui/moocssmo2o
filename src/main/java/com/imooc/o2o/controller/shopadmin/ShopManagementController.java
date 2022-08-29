package com.imooc.o2o.controller.shopadmin;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController { //实现店铺管理相关的逻辑

    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;


    //店铺分类管理相关页面
    @RequestMapping(value = "/getshopmanagementinfo",method = RequestMethod.GET)
    @ResponseBody//因为返回体为Map类型的键值对，所以用 @ResponseBody自动转换成json对象
    private Map<String,Object> getShopManagementInfo(HttpServletRequest request){
        Map<String,Object> modelMap=new HashMap<String ,Object>();
        long shopId=HttpServletRequestUtil.getLong(request,"shopId");//获取店铺Id,做session操作
        if (shopId<=0){
            Object currentShopObj=request.getSession().getAttribute("currentShop");
            if(currentShopObj==null){ //如果还是为空，重定向回之前的前端页面
                modelMap.put("redirect",true);//redirect重定向
                modelMap.put("url","/o2o/shopadmin/shoplist");
            }else {
                Shop currentShop=(Shop) currentShopObj;
                modelMap.put("redirect",false);
                modelMap.put("shopId",currentShop.getShopId());//从currentShop取得shopId
            }
        }else {
            Shop currentShop=new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop",currentShop);
            modelMap.put("redirect",false);
        }
        return modelMap;
    }

    //获取店铺列表信息
    @RequestMapping(value = "/getshoplist",method = RequestMethod.GET)
    @ResponseBody//因为返回体为Map类型的键值对，所以用 @ResponseBody自动转换成json对象
    private Map<String,Object> getShopList(HttpServletRequest request){
        Map<String ,Object> modelMap=new HashMap<String,Object>();//定义变量接收返回值
        PersonInfo user=new PersonInfo();//通过session对象来获取用户信息
        user.setUserId(1l);
        user.setName("test");
        request.getSession().setAttribute("user",user);
        user=(PersonInfo) request.getSession().getAttribute("user");
        try{
            Shop shopCondition=new Shop();
            shopCondition.setOwner(user);
            ShopExecution se=shopService.getShopList(shopCondition,0,100);
            modelMap.put("shopList",se.getShopList());
            // 列出店铺成功之后，将店铺放入session中作为权限验证依据，即该帐号只能操作它自己的店铺
            request.getSession().setAttribute("shopList", se.getShopList());
            modelMap.put("user",user);
            modelMap.put("success",true);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }

    //通过id获取店铺信息
    @RequestMapping(value = "/getshopbyid",method = RequestMethod.GET)
    @ResponseBody//因为返回体为Map类型的键值对，所以用 @ResponseBody自动转换成json对象
    private Map<String,Object> getshopByshopId(HttpServletRequest request){
        Map<String ,Object> modelMap=new HashMap<String,Object>();
        Long shopId=HttpServletRequestUtil.getLong(request,"shopId");
        if (shopId>-1){
            try {
                Shop shop=shopService.getByShopId(shopId);
                List<Area> areaList= areaService.getAreaList();
                modelMap.put("shop",shop);
                modelMap.put("areList",areaList);
                modelMap.put("success",true);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        }
        return modelMap;
    }

    //更改店铺信息
    @RequestMapping(value = "/modifyshop",method = RequestMethod.POST)
    @ResponseBody  //因为返回体为Map类型的键值对，所以用 @ResponseBody自动转换成json对象
    private Map<String ,Object> modifyShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();//先定义返回值变量
        //引入验证码工具类
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }

        //1).接收并转化相应的参数，包括店铺信息以及图片信息
        //a.接收并转换相应参数的逻辑
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");//request接收和前端约定好key值"shopStr"对应店铺所有信息的参数
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null; //实体类对象去接收
        try {
            shop = mapper.readValue(shopStr, Shop.class);// readValue 方法可以将json字符串转换成指定的对象
        } catch (Exception e) {
            modelMap.put("success", false);//初始化为转换失败
            modelMap.put("errMsg", e.getMessage());//打印失败原因
            return modelMap;
        }
        //b.处理图片相关的逻辑(参数)
        CommonsMultipartFile shopImg = null;//接收图片文件流
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());//文件上传解析器去解析request里面的文件信息(从本次会话当中的上下文去获取相关文件上传的内容)
        if (commonsMultipartResolver.isMultipart(request)) {//判断request对象中是否有上传的文件流
            //如果有，就要将request强制转换成MultipartHttpServletRequest对象
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");//提取从前端约定好的shopImg变量传过来的文件里流
        } //图片可上传可不上传（使用原来的图片），非空判断去除
        //2).修改店铺信息
        if (shop != null && shop.getShopId() != null) {  //首先判断shop实例是否为空，shopId是否为空
            //shop表中owner的信息可以通过session会话获取，不需要前端传递过来
            PersonInfo owner=(PersonInfo) request.getSession().getAttribute("user");//获取用户信息//初始化owner(Session TODO)
            shop.setOwner(owner);

            ShopExecution se;//CommonsMultipartFile类型不能强制转换成File类型，会报错，要用CommonsMultipartFile里面的getInputStream()方法绕开。shopImg.getOriginalFilename()方法获取原本的文件名称
            try {
                if (shopImg==null){ //如果上传的图片文件流为空
                    se=shopService.modifyShop(shop,null);
                }else {
                    ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
                    se = shopService.modifyShop(shop, imageHolder);//CommonsMultipartFile类型不能强制转换成File类型，会报错，要用CommonsMultipartFile里面的getInputStream()方法绕开。shopImg.getOriginalFilename()方法获取原本的文件名称
                }
                    //判断返回值的情况
                if (se.getState() == ShopStateEnum.SUCCESS.getState()) {  //a.店铺修改成功的状态
                    modelMap.put("success", true);
                } else {                                               //否则创建失败，并告知失败的原因是什么
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());  //返回状态对应的注释，即失败的原因
                }
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            } catch (ShopOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }

            return modelMap;
        } else {
            modelMap.put("success", false);//初始化为没有上传的文件
            modelMap.put("errMsg", "请输入店铺id");//空值判断
            return modelMap;
        }
    }


    @RequestMapping(value = "/getshopinitinfo",method = RequestMethod.GET)
    @ResponseBody  //返回JSON串
    private Map<String,Object> getShopInitInfo(){
        Map<String ,Object> modelMap=new HashMap<String,Object>();//先定义返回值变量
       List<ShopCategory> shopCategoryList= new ArrayList<ShopCategory>();//然后用shopCategoryList对象接收shopCategory相关的信息
       List<Area> areaList=new ArrayList<Area>();//用areaList对象接收area相关的信息
        try {
            shopCategoryList=shopCategoryService.getShopCategoryList(new ShopCategory());//获取全部的对象(数据库中的全部列表)
            areaList=areaService.getAreaList();
            modelMap.put("shopCategoryList",shopCategoryList);
            modelMap.put("areaList",areaList);
            modelMap.put("success",true);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }

    //1.店铺注册
    @RequestMapping(value = "/registershop",method = RequestMethod.POST)
    @ResponseBody  //因为返回体为Map类型的键值对，所以用 @ResponseBody自动转换成json对象
    private Map<String ,Object> registerShop(HttpServletRequest request){
        Map<String ,Object> modelMap=new HashMap<String,Object>();//先定义返回值变量
        //引入验证码工具类
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","验证码错误");
            return modelMap;
        }

        //1).接收并转化相应的参数，包括店铺信息以及图片信息
          //a.接收并转换相应参数的逻辑
          String shopStr = HttpServletRequestUtil.getString(request, "shopStr");//request接收和前端约定好key值"shopStr"对应店铺所有信息的参数
          ObjectMapper mapper = new ObjectMapper();
          Shop shop=null; //实体类对象去接收
          try{
              shop=mapper.readValue(shopStr,Shop.class);
          } catch (Exception e){
              modelMap.put("success",false);//初始化为转换失败
              modelMap.put("errMsg",e.getMessage());//打印失败原因
              return modelMap;
          }
          //b.处理图片相关的逻辑(参数)
          CommonsMultipartFile shopImg=null;//接收图片
          CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());//文件上传解析器去解析request里面的文件信息(从本次会话当中的上下文去获取相关文件上传的内容)
          if (commonsMultipartResolver.isMultipart(request)){//判断request对象中是否有上传的文件流
              //如果有，就要将request强制转换成MultipartHttpServletRequest对象
              MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
              shopImg=(CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg") ;//提取从前端约定好的shopImg变量传过来的文件里流
          }else {
              modelMap.put("success",false);//初始化为没有上传的文件
              modelMap.put("errMsg","上传图片不能为空");//空值判断
              return modelMap;
          }
        //2).注册店铺
        if (shop!=null&&shopImg!=null){  //首先判断shop实例是否为空，shopImg是否为空
            //shop表中owner的信息可以通过session会话获取，不需要前端传递过来
            PersonInfo owner=(PersonInfo) request.getSession().getAttribute("user");//获取用户信息//初始化owner(Session TODO)
            shop.setOwner(owner);
//            //然后把owner和前端传递过来的店铺信息添加到shop实例里面去
//            File shopImgFile=new File(PathUtil.getImgBasePath()+ ImageUtil.getRandomFileName());
//            try {
//                shopImgFile.createNewFile();//创建出新的文件
//            } catch (IOException e) {
//                modelMap.put("success",false);//初始化为不成功
//                modelMap.put("errMsg",e.getMessage());//文件创建失败
//                return modelMap;
//            }
//            try {
//                inputStreamToFile(shopImg.getInputStream(), shopImgFile);//shopImg是CommonsMultipartFile类型的，所以可以调用getInputStream()方法获取随机的新的文件
//            } catch (IOException e) {
//                modelMap.put("success",false);
//                modelMap.put("errMsg",e.getMessage());
//                return modelMap;
//            }

            ShopExecution se;//CommonsMultipartFile类型不能强制转换成File类型，会报错，要用CommonsMultipartFile里面的getInputStream()方法绕开。shopImg.getOriginalFilename()方法获取原本的文件名称
            try {
                ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
                se = shopService.addShop(shop, imageHolder);//CommonsMultipartFile类型不能强制转换成File类型，会报错，要用CommonsMultipartFile里面的getInputStream()方法绕开。shopImg.getOriginalFilename()方法获取原本的文件名称
                //判断返回值的情况
                if (se.getState()== ShopStateEnum.CHECK.getState()){  //a.店铺创建成功的状态
                    modelMap.put("success",true);
                    List<Shop> shopList=(List<Shop>) request.getSession().getAttribute("shopList");//该用户可以操作的店铺列表（用户和店铺是一对多关系）
                    if(shopList==null||shopList.size()==0){ //判断这是第一个创建的店铺
                        shopList=new ArrayList<Shop>();

                    }
                        shopList.add(se.getShop());
                        request.getSession().setAttribute("shopList",shopList);
                }else {                                               //否则创建失败，并告知失败的原因是什么
                    modelMap.put("success",false);
                    modelMap.put("errMsg",se.getStateInfo());  //返回状态对应的注释，即失败的原因
                }
            } catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }catch (ShopOperationException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }

            return modelMap;
        }else {
            modelMap.put("success",false);//初始化为没有上传的文件
            modelMap.put("errMsg","请输入店铺信息");//空值判断
            return modelMap;
        }

    }

//    private static void inputStreamToFile(InputStream ins, File file){  //负责相应类型的转换，两个参数分别是转换前输入的参数和转换后的参数
//        OutputStream os=null; //将输出流转换成文件
//        try{
//            os=new FileOutputStream(file);
//            int bytesRead=0;
//            byte[] buffer=new byte[1024];//定义buffer数组去读入InputStream里面的内容
//            while ((bytesRead= ins.read(buffer))!=-1){ //将inputStream里面的数据循环写入到buffer中去，读满1024个字节就往输出流中写入一次直到读完为止
//                os.write(buffer,0,bytesRead);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("调用inputStreamToFile产生异常:"+e.getMessage());
//        }finally {   //关闭输入流和输出流
//            try {
//                if(os!=null){  //输出流关闭
//                    os.close();
//                }
//                if(ins!=null){  //输入流关闭
//                    ins.close();
//                }
//            } catch (IOException e) {
//                throw new RuntimeException("输入输出流io关闭产生异常:"+e.getMessage());
//            }
//        }
//        }
}
