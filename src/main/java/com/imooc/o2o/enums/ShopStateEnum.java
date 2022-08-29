package com.imooc.o2o.enums;



public enum ShopStateEnum {  //enum枚举类
    CHECK(0,"审核中"),OFFLINE(-1,"非法店铺"),SUCCESS(1,"操作成功"),PASS(2,"通过认证"),INNER_ERROR(-1001,"内部系统错误"),NULL_SHOPID(-1002,"ShopId为空"),NULL_SHOP(-1003,"shop为空");

    private int state;
    private String stateInfo;

    private ShopStateEnum(int state,String stateInfo){
        this.state=state;//获取状态值
        this.stateInfo=stateInfo;//获取对状态值的说明
    }

    /**
     * 依据传入的state值返回相应的enum值
     */
    public static ShopStateEnum stateOf(int state){
        for (ShopStateEnum stateEnum: ShopStateEnum.values()){
            if (stateEnum.getState()==state){
                return stateEnum;
            }
        }
        return null;
    }




    //生成get()和set()方法（不希望程序外面调用set()方法）

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

}
