package com.hand.hec.pur.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.pur.dto.PurVenderTax;
import com.hand.hec.pur.exception.PurVenderTaxRefAeException;

/**
 * <p>
 * 供应商税务信息service接口
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/25 17:09
 */
public interface IPurVenderTaxService extends IBaseService<PurVenderTax>, ProxySelf<IPurVenderTaxService> {

    /**
     * 供应商税务信息基础查询
     *
     * @param request     request
     * @param accEntityId 核算主体ID
     * @param venderId    供应商ID
     * @param page        页数
     * @param pageSize    分页大小
     * @return java.util.List<com.hand.hec.pur.dto.PurVenderTax>
     * @author jialin.xing@hand-china.com 2019-03-02 21:58
     */
    List<PurVenderTax> queryByVenderIdAndAccEntityId(IRequest request,
                                                     Long accEntityId,
                                                     Long venderId,
                                                     int page,
                                                     int pageSize);

    /**
     * 供应商税务信息批量操作
     *
     * @param request request
     * @param list    税务信息列表
     * @return java.util.List<com.hand.hec.pur.dto.PurVenderTax>
     * @author jialin.xing@hand-china.com 2019-03-02 22:36
     */
    List<PurVenderTax> batchUpdateTax(IRequest request, @StdWho List<PurVenderTax> list) throws PurVenderTaxRefAeException;
}