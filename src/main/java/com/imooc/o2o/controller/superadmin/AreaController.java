package com.imooc.o2o.controller.superadmin;

import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/superadmin")
public class AreaController {

    Logger logger= LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;
    @RequestMapping(value = "/listarea",method = RequestMethod.GET)
    @ResponseBody//将返回值自动转换成json对象返回到前端
    private Map<String,Object> listArea(){
        logger.info("===start===");
        long startTime=System.currentTimeMillis();//记录方法的执行时间(获取当前时间的毫秒数)

        Map<String, Object> modelMap = new HashMap<String,Object>();//HaspMap:key值不能重复
        List<Area> list=new ArrayList<Area>();
        try{
            list=areaService.getAreaList();
            modelMap.put("rows",list);//返回集合
            modelMap.put("total",list.size());//返回的总数
        }catch (Exception e){
            e.printStackTrace();
            modelMap.put("success",false);
            modelMap.put("errMap",e.toString());
        }
        logger.error("test error!");

        long endTime=System.currentTimeMillis();//记录方法的结束时间(获取当前时间的毫秒数)
        logger.debug("costTime:[{}ms]",endTime-startTime);
        logger.info("===end===");
        return modelMap;
    }
}
