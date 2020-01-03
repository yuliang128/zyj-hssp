package com.hand.hec.pur.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.pur.dto.PurVenderAccount;
import com.hand.hec.pur.exception.PurVenderAccountRefAeException;

/**
 * <p>
 * 供应商银行账户Service接口
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/21 14:24
 */
public interface IPurVenderAccountService extends IBaseService<PurVenderAccount>, ProxySelf<IPurVenderAccountService> {

    /**
     * 系统级供应商主文件定义银行账户页面基础查询
     *
     * @param request  request
     * @param venderId 供应商ID
     * @return java.util.List<com.hand.hec.pur.dto.PurVenderAccount>
     * @author jialin.xing@hand-china.com 2019-02-21 14:33
     */
    List<PurVenderAccount> queryByVenderId(IRequest request, Long venderId, int page, int pageSize);

    /**
     * 供应商银行账户页面基础查询
     *
     * @param request
     * @param venderId    供应商ID
     * @param accEntityId 核算主体ID
     * @param page        页数
     * @param pageSize    页大小
     * @return java.util.List<com.hand.hec.pur.dto.PurVenderAccount>
     * @author jialin.xing@hand-china.com 2019-03-02 19:27
     */
    List<PurVenderAccount> queryByVenderIdAndAccEntityId(IRequest request,
                                                         Long venderId,
                                                         Long accEntityId,
                                                         int page,
                                                         int pageSize);

    /**
     * 供应商银行账户页面批处理
     *
     * @param request request
     * @param list    供应商银行账户列表
     * @return java.util.List<com.hand.hec.pur.dto.PurVenderAccount>
     * @throws PurVenderAccountRefAeException
     * @author jialin.xing@hand-china.com 2019-03-02 20:22
     */
    List<PurVenderAccount> updateAeAccount(IRequest request, @StdWho List<PurVenderAccount> list) throws PurVenderAccountRefAeException;

}