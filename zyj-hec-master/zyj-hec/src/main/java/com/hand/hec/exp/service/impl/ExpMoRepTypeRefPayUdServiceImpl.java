package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoRepTypeRefPayUd;
import com.hand.hec.exp.exception.ExpReportTypeException;
import com.hand.hec.exp.mapper.ExpMoRepTypeRefPayUdMapper;
import com.hand.hec.exp.service.IExpMoRepTypeRefPayUdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * ExpMoRepTypeRefPayUdServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 14:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoRepTypeRefPayUdServiceImpl extends BaseServiceImpl<ExpMoRepTypeRefPayUd>
                implements IExpMoRepTypeRefPayUdService {
    @Autowired
    ExpMoRepTypeRefPayUdMapper expMoRepTypeRefPayUdMapper;

    /**
     * <p>
     * 报销单类型-付款用途批量提交
     * <p/>
     *
     * @param IRequest request
     * @param List<ExpMoRepTypeRefPayUd> payUds
     * @return List<ExpMoRepTypeRefPayUd>
     * @throws RuntimeException 运行时异常
     * @author yang.duan 2019/2/26 14:22
     */
    @Override
    public List<ExpMoRepTypeRefPayUd> batchSubmit(IRequest request, List<ExpMoRepTypeRefPayUd> payUds)
                    throws RuntimeException {
        if (!payUds.isEmpty()) {
            if (!DTOStatus.DELETE.equals(payUds.get(0).get__status())) {
                ExpMoRepTypeRefPayUd dto = new ExpMoRepTypeRefPayUd();
                dto.setMoExpReportTypeId(payUds.get(0).getMoExpReportTypeId());
                dto.setDefaultFlag("Y");
                dto.setEnabledFlag("Y");
                int count = expMoRepTypeRefPayUdMapper.selectCount(dto);
                if (count == 0) {
                    int defCount = 0;
                    for (ExpMoRepTypeRefPayUd ud : payUds) {
                        if ("Y".equals(ud.getDefaultFlag())) {
                            defCount++;
                        }
                    }
                    if (defCount != 1) {
                        throw new ExpReportTypeException(ExpReportTypeException.EXP_REP_TYPE_PAY_USEDE_DEFAULT_ERROR,
                                        null);
                    }

                } else {
                    int defCount = 0;
                    ExpMoRepTypeRefPayUd oldValue = this.select(request, dto, 1, 10).get(0);
                    for (ExpMoRepTypeRefPayUd ud : payUds) {
                        if (!oldValue.getRefId().equals(ud.getRefId()) && "Y".equals(ud.getDefaultFlag())) {
                            defCount++;
                        }
                        if (oldValue.getRefId().equals(ud.getRefId()) && "N".equals(ud.getDefaultFlag())) {
                            defCount--;
                        }
                    }
                    if (defCount != 0) {
                        throw new ExpReportTypeException(ExpReportTypeException.EXP_REP_TYPE_PAY_USEDE_DEFAULT_ERROR,
                                        null);
                    }
                }
            }
        }
        return this.batchUpdate(request, payUds);
    }
}
