package com.lcw.herakles.platform.system.init;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.lcw.herakles.platform.common.util.AppConfigUtil;
import com.lcw.herakles.platform.common.util.MessageUtil;
import com.lcw.herakles.platform.system.dict.repository.DictRepository;
import com.lcw.herakles.platform.system.dict.util.SystemDictUtil;

import lombok.extern.slf4j.Slf4j;


/**
* Class Name: UtilityPreparationPostProcessor
* Description: TODO
* @author chenwulou
*
*/
@Component
@Slf4j
public class UtilityPreparationPostProcessor {

    @Autowired
    @Qualifier("appConfig")
    private MessageSource appConfig;
    
    @Autowired
    @Qualifier("messageSource")
    private MessageSource messageSource;

    @Autowired
    private DictRepository dictRepository; 

    @PostConstruct
    public void postProcessAfterInitialization() throws BeansException {
        log.info("postProcessAfterInitialization() invoked");
        
        MessageUtil.setMessageSource(messageSource);
        AppConfigUtil.setMessageSource(appConfig);
        
        SystemDictUtil.setSysDictRepository(dictRepository);
        SystemDictUtil.initAndRefresh();
    }

}
