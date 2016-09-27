package com.lcw.herakles.platform.demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lcw.herakles.platform.common.validation.BaseValidator;
import com.lcw.herakles.platform.demo.service.ProductService;


/**
 * 
 * @author chenwulou
 *
 */
@Component
public class ProductExistenceValidator extends BaseValidator
        implements ConstraintValidator<ProductExistenceCheck, String> {

    @Autowired
    private ProductService productService;

    @Override
    public void initialize(ProductExistenceCheck check) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Integer size = productService.findProductByName(value).size();
        return 0 == size;
    }

}
