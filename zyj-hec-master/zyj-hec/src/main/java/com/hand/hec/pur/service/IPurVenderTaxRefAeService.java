package com.hand.hec.pur.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.pur.dto.PurVenderTaxRefAe;
import com.hand.hec.pur.exception.PurVenderTaxRefAeException;

/**
 * <p>
 * 供应商税务信息分配核算主体service接口
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/26 10:31
 */
public interface IPurVenderTaxRefAeService extends IBaseService<PurVenderTaxRefAe>, ProxySelf<IPurVenderTaxRefAeService> {

    /**
     * 系统级供应商管理税务信息核算主体分配基础查询
     *
     * @param request  request
     * @param taxId    税务信息Id
     * @param page     页数
     * @param pageSize 页大小
     * @return java.util.List<com.hand.hec.pur.dto.PurVenderTaxRefAe>
     * @author jialin.xing@hand-china.com 2019-02-26 10:24
     */
    List<PurVenderTaxRefAe> queryByTaxId(IRequest request, Long taxId, int page, int pageSize);

    /**
     * 根绝供应商Id和税务信息Id查询未分配的核算主体
     *
     * @param venderId 供应商Id
     * @param taxId    税务信息Id
     * @return java.util.List<com.hand.hec.pur.dto.PurVenderTaxRefAe>
     * @author jialin.xing@hand-china.com 2019-02-26 11:12
     */
    List<PurVenderTaxRefAe> queryAccEntityByVenderIdAndTaxId(IRequest request, Long venderId, Long taxId);

    /**
     * 系统级供应商税务信息核算主体批量分配
     *
     * @param request request
     * @param dto     核算主体列表
     * @return java.util.List<com.hand.hec.pur.dto.PurVenderTaxRefAe>
     * @author jialin.xing@hand-china.com 2019-02-26 14:12
     */
    List<PurVenderTaxRefAe> batchAssign(IRequest request, @StdWho List<PurVenderTaxRefAe> dto) throws PurVenderTaxRefAeException;

    /**
     * 税务信息核算主体分配插入
     *
     * @param request request
     * @param refAe   dto
     * @return com.hand.hec.pur.dto.PurVenderTaxRefAe
     * @throws PurVenderTaxRefAeException
     * @author jialin.xing@hand-china.com 2019-03-02 22:23
     */
    PurVenderTaxRefAe batchInsert(IRequest request, PurVenderTaxRefAe refAe) throws PurVenderTaxRefAeException;
}