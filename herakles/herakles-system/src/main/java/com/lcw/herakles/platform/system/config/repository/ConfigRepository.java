package com.lcw.herakles.platform.system.config.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lcw.herakles.platform.system.config.entity.ConfigPo;

/**
 * 参数repository
 * 
 * @author chenwulou
 *
 */
public interface ConfigRepository extends JpaRepository<ConfigPo, String>, JpaSpecificationExecutor<ConfigPo> {

}
