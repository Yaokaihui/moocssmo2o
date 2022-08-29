package com.imooc.o2o.exceptions;

public class ShopOperationException extends RuntimeException{  //抛出异常
    /**
     * 生成随机序列化ID
     */
    private static final long serialVersionUID = 2361446884822298905L;

    public ShopOperationException(String msg){
        super(msg);
    }
}
