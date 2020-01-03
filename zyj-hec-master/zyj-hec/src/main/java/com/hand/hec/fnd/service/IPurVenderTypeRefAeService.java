package com.hand.hec.fnd.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.PurVenderTypeRefAe;
import com.hand.hec.gld.dto.GldAccountingEntity;

/**
 * <p>
 * 供应商类型分配核算主体Service接口
 * </p>
 *
 * @author YHL 2019/01/29 12:47
 */
public interface IPurVenderTypeRefAeService
        extends IBaseService<PurVenderTypeRefAe>, ProxySelf<IPurVenderTypeRefAeService> {

    /**
     * 查询供应商类型中分配的核算主体信息
     *
     * @param request
     * @param venderTypeId 供应商类型ID
     * @param pageNum
     * @param pageSize
     * @author YHL 2019-01-29 14:11
     * @return java.util.List<com.hand.hec.fnd.dto.PurVenderTypeRefAe> 供应商类型中分配的核算主体信息
     */
    List<PurVenderTypeRefAe> getVenderTypeRefAeByVenderTypeId(IRequest request, Long venderTypeId, int pageNum,
            int pageSize);

    /**
     * 供应商类型-核算主体 多对多分配
     *
     * @param requestCtx
     * @param accountingEntity 包含有分配到的供应商类型详细信息的核算主体集合
     * @author YHL 2019-02-01 16:27
     * @return java.lang.Boolean
     */
    public Boolean multipleAssign(IRequest requestCtx, List<GldAccountingEntity> accountingEntity);
}