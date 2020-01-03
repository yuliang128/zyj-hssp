package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoRepTypeRefEmpGp;
import com.hand.hec.exp.exception.ExpReportTypeException;
import com.hand.hec.exp.service.IExpMoRepTypeRefEmpGpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * ExpMoRepTypeRefEmpGpServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 14:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoRepTypeRefEmpGpServiceImpl extends BaseServiceImpl<ExpMoRepTypeRefEmpGp>
                implements IExpMoRepTypeRefEmpGpService {
    /**
     * <p>
     * 报销单类型-分配员工组批量提交
     * <p/>
     *
     * @param IRequest request
     * @param List<ExpMoRepTypeRefEmpGp> empGpList
     * @return List<ExpMoRepTypeRefEmpGp>
     * @throws RuntimeException exception description
     * @author yang.duan 2019/3/4 11:09
     */
    @Override
    public List<ExpMoRepTypeRefEmpGp> batchSumbit(IRequest request, List<ExpMoRepTypeRefEmpGp> empGpList)
                    throws RuntimeException {
        Set<String> codeSet = new HashSet<String>();
        if (empGpList != null && !empGpList.isEmpty()) {
            for (ExpMoRepTypeRefEmpGp egp : empGpList) {
                codeSet.add(egp.getMoEmpGroupCode());
            }
            if (codeSet.size() != empGpList.size()) {
                throw new ExpReportTypeException(ExpReportTypeException.EXP_REP_TYPE_EMP_GP_DUP_ERROR, null);
            }
        }
        return this.batchUpdate(request, empGpList);
    }
}
