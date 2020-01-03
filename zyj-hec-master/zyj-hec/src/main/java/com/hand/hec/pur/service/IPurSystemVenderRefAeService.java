package com.hand.hec.pur.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.pur.dto.PurSystemVenderRefAe;

/**
 * <p>
 * 供应商分配核算主体Service接口
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/20 15:47
 */
public interface IPurSystemVenderRefAeService extends IBaseService<PurSystemVenderRefAe>, ProxySelf<IPurSystemVenderRefAeService> {

    /**
     * 供应商分配核算主体基础查询
     *
     * @param request  request
     * @param venderId 供应商 ID
     * @param page     页数
     * @param pageSize 页大小
     * @return java.util.List<com.hand.hec.pur.dto.PurSystemVenderRefAe>
     * @author jialin.xing@hand-china.com 2019-02-20 16:38
     */
    List<PurSystemVenderRefAe> queryByVenderId(IRequest request, Long venderId, int page, int pageSize);

    /**
     * 查询所有的核算主体
     *
     * @param request request
     * @return java.util.List<com.hand.hec.gld.dto.GldAccountingEntity>
     * @author jialin.xing@hand-china.com 2019-02-20 17:50
     */
    List<PurSystemVenderRefAe> queryUnallocatedAccEntity(IRequest request,Long venderId);

    /**
     * 为供应商批量分配核算主体
     *
     * @param requestContext request
     * @param dto            dto
     * @return java.util.List<com.hand.hec.pur.dto.PurSystemVenderRefAe>
     * @author jialin.xing@hand-china.com 2019-02-20 18:58
     */
    boolean batchAssign(IRequest requestContext, List<PurSystemVenderRefAe> dto);

    /**
     * 多个供应商批量分配
     *
     * @param request request
     * @param dto     携带供应商ID列表的核算主体列表
     * @return java.util.List<com.hand.hec.pur.dto.PurSystemVenderRefAe>
     * @author jialin.xing@hand-china.com 2019-02-26 19:12
     */
    List<PurSystemVenderRefAe> multiBatchAssign(IRequest request, List<PurSystemVenderRefAe> dto);
}