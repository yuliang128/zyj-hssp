package com.hand.hec.expm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.expm.dto.ExpDocumentAuthority;

import java.util.List;

public interface IExpDocumentAuthorityService
                extends IBaseService<ExpDocumentAuthority>, ProxySelf<IExpDocumentAuthorityService> {



    /**
     * 查询授权员工
     *
     * @param request
     * @param dto
     * @author mouse 2019-03-12 16:57
     * @return java.util.List<com.hand.hap.exp.dto.ExpEmployee>
     */
    List<ExpEmployee> queryDocAuthEmployee(IRequest request, ExpDocumentAuthority dto);


    /**
     * <p>查询当前公司下的授权员工</p>
     *
     * @param dto
     * @return java.util.List<com.hand.hap.exp.dto.ExpEmployee>
     * @author yang.duan 2019/4/25 11:25
     **/
    List<ExpEmployee> getEmpCurCompAuth(IRequest request, ExpDocumentAuthority dto);

}
