package com.imooc.o2o.util;

import java.util.Locale;

//进行地址的处理，提供两类路径
public class PathUtil {
    private static String seperator=System.getProperty("file.separator");
    public static String getImgBasePath(){
        String os=System.getProperty("os.name");
        String basePath="";
        if (os.toLowerCase(Locale.ROOT).startsWith("win")){
            basePath="D:\\photo"; //图片处理后保存的文件路径，本身计算机中有的文件夹路径
        }else {
            basePath="/Users/baidu/work/image";
        }
        //统一替换为"/"
        basePath=basePath.replace("/",seperator);
        return basePath;
    }

    //在ImagBasePath图片路径的基础上还有一层店铺图片的相对子路径
    public static String getShopImagePath(long shopId){
        //通过shopId将不同的图片传递到不同的店铺图片路径下保存
        String imagePath="/upload/item/shop/"+shopId+"/";
        return imagePath.replace("/",seperator); //替换成系统的分隔符返回
    }
}
