package com.lcw.herakles.platform.demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.lcw.herakles.platform.common.validation.BaseValidator;
import com.lcw.herakles.platform.demo.entity.ProductPo;
import com.lcw.herakles.platform.demo.service.ProductService;


/**
* Class Name: ProductExistenceValidator
* Description: Customized validator.
* @author chenwulou
*
*/
public class ProductExistenceValidator extends BaseValidator implements
        ConstraintValidator<ProductExistenceCheck, String> {

    @Autowired
    private ProductService productService;

    @Override
    public void initialize(ProductExistenceCheck check) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        ProductPo product = productService.findProductById(value);
        return product == null;
    }

}
