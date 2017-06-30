package com.lcw.herakles.platform.demo.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lcw.herakles.platform.demo.service.PrintTimeJobTaskService;

/**
 * 定时任务demo
 * 
 * @author chenwulou
 *
 */
@Component
public class PrintTimeJobTask {

    @Autowired
    private PrintTimeJobTaskService printTimeJobTaskService;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void getTime() {
        printTimeJobTaskService.getTime();
        // System.out.println("任务进行中。。。每分钟触发一次");
    }

}

// cron ="秒  分  时  日  月  年"

//- 区间  
//* 通配符  
//? 你不想设置那个字段

//字段      允许值     允许的特殊字符
//秒       0-59        , - * /
//分       0-59        , - * /
//小时      0-23        , - * /
//日期      1-31        , - * ? / L W C
//月份      1-12 或者 JAN-DEC         , - * /
//星期      1-7 或者 SUN-SAT      , - * ? / L C #
//年（可选）       留空, 1970-2099       , - * /


//具体可以参考  http://docs.spring.io/spring/docs/current/spring-framework-reference/html/scheduling.html