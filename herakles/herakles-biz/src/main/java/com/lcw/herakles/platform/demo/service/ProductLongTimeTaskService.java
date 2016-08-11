package com.lcw.herakles.platform.demo.service;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class ProductLongTimeTaskService {
    
    public void longTimeTask(){
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
        System.out.println(new Date());
    }

}
