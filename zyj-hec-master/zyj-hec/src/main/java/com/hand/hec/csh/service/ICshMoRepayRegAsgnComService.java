package com.hand.hec.csh.service;

import java.util.List;
import java.util.Map;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshMoRepayRegAsgnCom;
/**
 * <p>
 *
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/04/25 12:10
 */

public interface ICshMoRepayRegAsgnComService extends IBaseService<CshMoRepayRegAsgnCom>, ProxySelf<ICshMoRepayRegAsgnComService>{

    /**
     * 还款登记单类型定义-批量分配公司基础查询
     *
     * @param request 上下文
     * @param condition condition
     * @param magOrgId 管理组织 ID
     * @author jialin.xing@hand-china.com 2019-04-25 12:10
     * @return java.util.List<com.hand.hap.fnd.dto.FndCompany>
     */
    List<CshMoRepayRegAsgnCom> queryUnallocatedCompanies(IRequest request, CshMoRepayRegAsgnCom condition, Long magOrgId);

    List<CshMoRepayRegAsgnCom> batchAssign(IRequest requestCtx,@StdWho List<CshMoRepayRegAsgnCom> dto);
}