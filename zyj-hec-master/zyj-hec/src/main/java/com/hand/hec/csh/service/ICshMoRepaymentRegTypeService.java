package com.hand.hec.csh.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshMoRepaymentRegType;

public interface ICshMoRepaymentRegTypeService extends IBaseService<CshMoRepaymentRegType>, ProxySelf<ICshMoRepaymentRegTypeService>{

    /**
     *
     * 我的借款申请-借款类型查询
     *
     * @author jialin.xing@hand-china.com 2019-04-26 13:58
     * @return java.util.List<com.hand.hec.csh.dto.CshMoRepaymentRegType>
     */
    List<CshMoRepaymentRegType> queryByCompanyId(IRequest request);

}