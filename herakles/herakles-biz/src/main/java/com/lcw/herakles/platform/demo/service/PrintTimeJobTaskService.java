package com.lcw.herakles.platform.demo.service;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class PrintTimeJobTaskService {
    
    public void getTime(){
        System.out.println(new Date());
    }

}
