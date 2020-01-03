package com.hand.hec.pur.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.pur.dto.PurVenderAccountRefAe;
import com.hand.hec.pur.exception.PurVenderAccountRefAeException;

/**
 * <p>
 * 供应商银行账户分配核算主体逻辑实现类
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/21 14:25
 */
public interface IPurVenderAccountRefAeService extends IBaseService<PurVenderAccountRefAe>, ProxySelf<IPurVenderAccountRefAeService> {

    /**
     * 供应商印上账户核算主体分配基础查询
     *
     * @param request   request
     * @param accountId 核算主体ID
     * @param page      页数
     * @param pageSize  分页大小
     * @return java.util.List<com.hand.hec.pur.dto.PurVenderAccountRefAe>
     * @author jialin.xing@hand-china.com 2019-03-02 19:58
     */
    List<PurVenderAccountRefAe> queryByAccountId(IRequest request, Long accountId, int page, int pageSize);

    /**
     * 根绝账户ID与供应商ID查询银行账户下未分配的核算主体
     *
     * @param request   request
     * @param accountId 账户ID
     * @param venderId  供应商ID
     * @return java.util.List<com.hand.hec.pur.dto.PurVenderAccountRefAe>
     * @author jialin.xing@hand-china.com 2019-02-22 14:05
     */
    List<PurVenderAccountRefAe> queryAccEntityByVenderIdAndAccountId(IRequest request, Long accountId, Long venderId);

    /**
     * asdasd
     *
     * @param requestCtx request
     * @param dto        dto
     * @return java.util.List<com.hand.hec.pur.dto.PurVenderAccountRefAe>
     * @throws PurVenderAccountRefAeException
     * @author jialin.xing@hand-china.com 2019-03-02 19:51
     */
    List<PurVenderAccountRefAe> assignVendorAccountAccEntity(IRequest requestCtx, @StdWho List<PurVenderAccountRefAe> dto) throws PurVenderAccountRefAeException;

    /**
     * 批量分配插入
     *
     * @param request request
     * @param dto     dto
     * @return com.hand.hec.pur.dto.PurVenderAccountRefAe
     * @throws PurVenderAccountRefAeException
     * @author jialin.xing@hand-china.com 2019-03-02 19:56
     */
    PurVenderAccountRefAe batchInsert(IRequest request, PurVenderAccountRefAe dto) throws PurVenderAccountRefAeException;

    /**
     * 批量分配更新
     *
     * @param requestCtx request
     * @param dto        dto
     * @return com.hand.hec.pur.dto.PurVenderAccountRefAe
     * @throws PurVenderAccountRefAeException
     * @author jialin.xing@hand-china.com 2019-03-02 19:57
     */
    PurVenderAccountRefAe updateVendorAccountAccEntity(IRequest requestCtx, PurVenderAccountRefAe dto) throws PurVenderAccountRefAeException;
}