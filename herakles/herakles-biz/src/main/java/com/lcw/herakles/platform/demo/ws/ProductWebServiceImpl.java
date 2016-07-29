
package com.lcw.herakles.platform.demo.ws;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.lcw.herakles.platform.demo.entity.ProductPo;
import com.lcw.herakles.platform.demo.service.ProductService;

/**
 * Class Name: CategoryServiceImpl Description: Sample implementation
 * 
 * @author chenwulou
 * 
 */

@WebService(endpointInterface = "com.lcw.herakles.platform.demo.ws.ProductWebService")
public class ProductWebServiceImpl implements ProductWebService {
    
    @Autowired
    ProductService productService;

    public ProductPo getProductById(String productId) {
        return productService.findProductById(productId);
    }

}
