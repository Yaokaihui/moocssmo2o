package com.imooc.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {
    public static int getInt(HttpServletRequest request,String key){ //从request参数提取key，将key值转换成整型
        try{
            return Integer.decode(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }

    public static Long getLong(HttpServletRequest request, String key){ //从request参数提取key，将key值转换成整型
        try{
            return Long.valueOf(request.getParameter(key));
        }catch (Exception e){
            return -1l;
        }
    }

    public static Double getDouble(HttpServletRequest request, String key){ //从request参数提取key，将key值转换成整型
        try{
            return Double.valueOf(request.getParameter(key));
        }catch (Exception e){
            return -1d;
        }
    }

    public static boolean getBoolean(HttpServletRequest request, String key){ //从request参数提取key，将key值转换成整型
        try{
            return Boolean.valueOf(request.getParameter(key));
        }catch (Exception e){
            return false;
        }
    }

    public static String getString(HttpServletRequest request,String key){
        try{
            String result= request.getParameter(key);//获取key值
            if (result!=null){
                result=result.trim();//去掉key左右两侧的空格
            }
            if ("".equals(result)){ //如果key为空
                result=null;
            }
            return result;//返回最终的结果
        }catch (Exception e){
            return null;
        }
    }
}
