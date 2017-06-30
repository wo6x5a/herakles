package com.lcw.herakles.platform.demo.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenwulou
 *
 */
@Slf4j
@Service
public class PrintTimeJobTaskService {

    // private static final Logger LOGGER = LoggerFactory.getLogger(PrintTimeJobTaskService.class);

    // @Autowired
    // private ProductRepository productRepository;

    public void getTime() {
        log.debug("this is task demo");
        // System.out.println(productRepository.findAll().size());
//         System.out.println(new Date());
    }

}
