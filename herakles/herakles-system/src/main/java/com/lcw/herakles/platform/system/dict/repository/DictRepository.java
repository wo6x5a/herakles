package com.lcw.herakles.platform.system.dict.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.lcw.herakles.platform.common.enumeration.EFlagType;
import com.lcw.herakles.platform.system.dict.entity.DictPo;
import com.lcw.herakles.platform.system.dict.enumeration.EDictCategory;

public interface DictRepository extends PagingAndSortingRepository<DictPo, String> {

    List<DictPo> findByEnableFLag(EFlagType enabled);
    
    List<DictPo> findByCategory(EDictCategory category);
  
    @Query("select d from DictPo d where d.dictFullText like ?1")
    DictPo getCityByCityName(String cityName);
}
