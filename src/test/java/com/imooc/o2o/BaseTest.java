package com.imooc.o2o;

import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器
 */

@RunWith(SpringJUnit4ClassRunner.class)//用什么类跑单元测试（这里是SpringJunit）
@WebAppConfiguration
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml","classpath:spring/spring-web.xml"})//告诉junit,spring配置文件的位置
public class BaseTest {
}
