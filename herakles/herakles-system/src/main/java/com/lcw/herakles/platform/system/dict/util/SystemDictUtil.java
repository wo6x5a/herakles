package com.lcw.herakles.platform.system.dict.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.lcw.herakles.platform.common.enums.EFlagType;
import com.lcw.herakles.platform.system.dict.dto.DynamicOption;
import com.lcw.herakles.platform.system.dict.entity.DictPo;
import com.lcw.herakles.platform.system.dict.enums.EDictCategory;
import com.lcw.herakles.platform.system.dict.repository.DictRepository;

public final class SystemDictUtil {

    private static DictRepository dictRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemDictUtil.class);

    private static List<String> UNMEANING_PARENT_CODE = Lists.newArrayList();;
    private static Map<String, DynamicOption> SYS_DIC_MAP = new ConcurrentHashMap<String, DynamicOption>();
    private static Map<String, Map<String, DynamicOption>> SYS_DIC_CODE_MAP = new HashMap<String, Map<String,DynamicOption>>();
    private static Map<String, List<DynamicOption>> ROOT_DICT_MAP = new ConcurrentHashMap<String, List<DynamicOption>>();
    private static Map<String, Map<String, List<DynamicOption>>> CHILDREN_DIC_MAP = new ConcurrentHashMap<String, Map<String, List<DynamicOption>>>();

    static {
        UNMEANING_PARENT_CODE.add("0000");
    }

    private SystemDictUtil(){
      
    }
    
    private static DynamicOption packItems(DictPo dict){
        DynamicOption option = new DynamicOption();
        option.setId(dict.getId());
        option.setCategory(dict.getCategory().getCode());
        option.setCode(dict.getDictCode());
        option.setText(dict.getDictText());
        option.setLongText(dict.getDictFullText());
        option.setEnabled(dict.getEnableFLag().getCode());
        option.setParentCode(dict.getParentDictCode());
        option.setLeaf(dict.getLeafFlag().getCode());
        option.setOrder(dict.getDictOrder());
        return option;
    }
    
    /**
     * Description: Should be called only when Spring start or synchronous data from database.
     * 
     */
    public static void initAndRefresh() {
        int count = 0;
        synchronized (SYS_DIC_MAP) {
            SYS_DIC_MAP.clear();
            SYS_DIC_CODE_MAP.clear();
            ROOT_DICT_MAP.clear();
            CHILDREN_DIC_MAP.clear();
            List<DictPo> list = dictRepository.findByEnableFLag(EFlagType.YES);
            for (DictPo po : list) {
                DynamicOption sysDict = packItems(po);
                SYS_DIC_MAP.put(sysDict.getId(), sysDict);
  
                String category = sysDict.getCategory();
                if (!SYS_DIC_CODE_MAP.containsKey(category)) {
                    SYS_DIC_CODE_MAP.put(category, new ConcurrentHashMap<String, DynamicOption>());
                }
                SYS_DIC_CODE_MAP.get(category).put(sysDict.getCode(), sysDict);
  
                String parentCode = sysDict.getParentCode();
                if (isParentCodeBlank(parentCode)) {
                    if (!ROOT_DICT_MAP.containsKey(category)) {
                        ROOT_DICT_MAP.put(category, Lists.newArrayList());
                    }
                    ROOT_DICT_MAP.get(category).add(sysDict);
                } else {
                    if (!CHILDREN_DIC_MAP.containsKey(category)) {
                        CHILDREN_DIC_MAP.put(category, new ConcurrentHashMap<String, List<DynamicOption>>());
                    }
                    if (!CHILDREN_DIC_MAP.get(category).containsKey(parentCode)) {
                        CHILDREN_DIC_MAP.get(category).put(parentCode, new LinkedList<DynamicOption>());
                    }
                    CHILDREN_DIC_MAP.get(category).get(parentCode).add(sysDict);
                }
                count++;
            }
        }
        LOGGER.info("INFO: Reload " + count + " records from SYS_DICT table!");
    }
    
    /**
     * Description:获取指定category（类别）的所有父类节点
     * @param category
     * @param hasEmpty
     * @return
     */
    public static List<DynamicOption> getRootDictList(EDictCategory category, boolean hasEmpty){
        List<DynamicOption> list = new LinkedList<DynamicOption>();
        if(hasEmpty){
            list.add(new DynamicOption("","",""));
        }
        List<DynamicOption> rootList = ROOT_DICT_MAP.get(category.getCode());
        if(rootList != null){
            list.addAll(rootList);
        }
        return list;
    }
    
   /**
    * Description:根据category、code查找对应的选项
    * @param category
    * @param code
    * @return
    */
    public static DynamicOption getDictByCategoryAndCode(EDictCategory category, String code){
        Map<String, DynamicOption> dictMap = SYS_DIC_CODE_MAP.get(category.getCode());
        if(dictMap != null && StringUtils.isNotBlank(code)){
            return dictMap.get(code);
        }
        return null;
    }
    
    /**
     * Description:根据子选项查找对应的父类选项
     * @param option
     * @return
     */
    public static DynamicOption getParentDict(DynamicOption option){
        Map<String, DynamicOption> dictMap = SYS_DIC_CODE_MAP.get(option.getCategory());
        String parentCode = option.getParentCode();
        if(dictMap != null && StringUtils.isNotBlank(parentCode)){
            return dictMap.get(parentCode);
        }
        return null;
    }
    
    /**
     * Description:根据categroy、parentCode查找父类下面的所有子类选项
     * @param category
     * @param parentCode
     * @param hasEmpty
     * @return
     */
    public static List<DynamicOption> getChildrenDictByParentCode(EDictCategory category, String parentCode, boolean hasEmpty){
        List<DynamicOption> list = new LinkedList<DynamicOption>();
        if(hasEmpty){
            list.add(new DynamicOption("","",""));
        }
        Map<String, List<DynamicOption>> dictMap = CHILDREN_DIC_MAP.get(category.getCode());
        if(dictMap != null){
            List<DynamicOption> dictList = dictMap.get(parentCode);
            if(dictList != null){
                list.addAll(dictList);
            }
        }
        return list;
    }
    
    /**
     * Description:获取选项描述
     * @param option
     * @return
     */
    public static String getFullTextWithParent(DynamicOption option) {
        StringBuilder sb = new StringBuilder();
        String parentCode = option.getParentCode();
        if (StringUtils.isNotBlank(parentCode) && !UNMEANING_PARENT_CODE.contains(parentCode)) {
            DynamicOption parent = SYS_DIC_CODE_MAP.get(option.getCategory()).get(parentCode);
            if (parent != null) {
                sb.append(getFullTextWithParent(parent));
            }
        }
        sb.append(option.getLongText());
        return sb.toString();
    }
    
    public static void setSysDictRepository(DictRepository dictRepository) {
        SystemDictUtil.dictRepository = dictRepository;
    }

    private static boolean isParentCodeBlank(String parentCode) {
        return StringUtils.isBlank(parentCode) || UNMEANING_PARENT_CODE.contains(parentCode);
    }
}
