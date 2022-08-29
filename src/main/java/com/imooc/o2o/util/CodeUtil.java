package com.imooc.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {  //判断验证码是否符合预期
    public static boolean checkVerifyCode(HttpServletRequest request){
        String verifyCodeExpected=(String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);//获取会话中的验证码（实际的验证码）
        String verifyCodeActual=HttpServletRequestUtil.getString(request,"verifyCodeActual");//前端输入的验证码
        if(verifyCodeActual==null||!verifyCodeActual.equals(verifyCodeExpected)){ //判断输入验证码为空或者与验证码图片不相等
            return false;
        }
        return true;
    }
}
