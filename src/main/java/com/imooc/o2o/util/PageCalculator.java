package com.imooc.o2o.util;

public class PageCalculator {
    //函数与页数的转换
    public static int calculateRowIndex(int pageIndex, int pageSize) {
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }
}