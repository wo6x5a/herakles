package com.lcw.herakles.platform.log.reposipory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lcw.herakles.platform.log.entity.LogPo;

/**
 * @author chenwulou
 *
 */
public interface LogRepository
        extends JpaRepository<LogPo, String>, JpaSpecificationExecutor<LogPo> {
}
