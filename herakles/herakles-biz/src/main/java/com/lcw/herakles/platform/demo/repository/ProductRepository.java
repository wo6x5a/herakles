package com.lcw.herakles.platform.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lcw.herakles.platform.demo.entity.ProductPo;


/**
* Class Name: ProductRepository
* Description: TODO
* @author chenwulou
*
*/
public interface ProductRepository extends JpaRepository<ProductPo, String>,
		JpaSpecificationExecutor<ProductPo> {

    public  ProductPo findByName(String name);
}