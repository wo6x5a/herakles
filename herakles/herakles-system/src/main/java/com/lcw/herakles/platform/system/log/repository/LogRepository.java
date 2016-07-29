package com.lcw.herakles.platform.system.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lcw.herakles.platform.system.log.entity.LogPo;

/**
 * @author chenwulou
 *
 */
public interface LogRepository
        extends JpaRepository<LogPo, String>, JpaSpecificationExecutor<LogPo> {
}
