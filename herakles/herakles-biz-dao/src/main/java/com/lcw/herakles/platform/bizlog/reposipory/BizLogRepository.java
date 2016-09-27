package com.lcw.herakles.platform.bizlog.reposipory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lcw.herakles.platform.bizlog.entity.BizLogPo;

/**
 * @author chenwulou
 *
 */
public interface BizLogRepository
        extends JpaRepository<BizLogPo, String>, JpaSpecificationExecutor<BizLogPo> {
}
