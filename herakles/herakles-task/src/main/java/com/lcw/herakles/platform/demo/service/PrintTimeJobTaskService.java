package com.lcw.herakles.platform.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author chenwulou
 *
 */
@Service
public class PrintTimeJobTaskService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrintTimeJobTaskService.class);

	// @Autowired
	// private ProductRepository productRepository;

	public void getTime() {
		LOGGER.debug("this is task demo");
		// System.out.println(productRepository.findAll().size());
		// System.out.println(new Date());
	}

}
