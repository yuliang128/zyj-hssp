package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoRepTypeRefEmpGp;

import java.util.List;

/**
 * <p>
 * IExpMoRepTypeRefEmpGpService
 * </p>
 *
 * @author yang.duan 2019/01/10 14:56
 */
public interface IExpMoRepTypeRefEmpGpService
                extends IBaseService<ExpMoRepTypeRefEmpGp>, ProxySelf<IExpMoRepTypeRefEmpGpService> {
    /**
     * <p>报销单类型-分配员工组批量提交<p/>
     *
     * @param IRequest request
     * @param List<ExpMoRepTypeRefEmpGp> empGpList
     * @return List<ExpMoRepTypeRefEmpGp>
     * @throws RuntimeException exception description
     * @author yang.duan 2019/3/4 11:09
     */
    List<ExpMoRepTypeRefEmpGp> batchSumbit(IRequest request, List<ExpMoRepTypeRefEmpGp> empGpList)
                    throws RuntimeException;
}
