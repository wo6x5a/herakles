
package com.lcw.herakles.platform.demo.ws;

import javax.jws.WebService;

import com.lcw.herakles.platform.demo.entity.ProductPo;

/**
 * Class Name: CategoryService Description: Just a sample
 * 
 * @author chenwulou
 * 
 */

@WebService
public interface ProductWebService {

    ProductPo getProductById(String productId);

}
