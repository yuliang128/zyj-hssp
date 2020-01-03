package com.hand.hec.gld.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.gld.dto.GldMappingConditionSql;

import java.util.List;

public interface IGldMappingConditionSqlService
                extends IBaseService<GldMappingConditionSql>, ProxySelf<IGldMappingConditionSqlService> {

    /**
     * 创建匹配组时匹配项grid数据
     * 
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/4/11 16:14
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     * @Version 1.0
     **/
    List<GldMappingConditionSql> selectGridData(IRequest requestContext,  String usageCode, int page,
                    int pageSize);
}
