package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.exception.UnitAccOrBgtDfMultiException;
import com.hand.hec.exp.mapper.ExpOrgUnitRefAccOrgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpOrgUnitRefAccOrg;
import com.hand.hec.exp.service.IExpOrgUnitRefAccOrgService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * ExpOrgUnitRefAccOrgServiceImpl
 * </p>
 * 
 * @author yang.duan 2019/01/10 11:18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpOrgUnitRefAccOrgServiceImpl extends BaseServiceImpl<ExpOrgUnitRefAccOrg>
                implements IExpOrgUnitRefAccOrgService {
    @Autowired
    private ExpOrgUnitRefAccOrgMapper mapper;

    @Override
    public List<ExpOrgUnitRefAccOrg> batchSubmit(IRequest request, List<ExpOrgUnitRefAccOrg> list)
                    throws UnitAccOrBgtDfMultiException {
        List<ExpOrgUnitRefAccOrg> refAccOrgList = new ArrayList<>();
        for (ExpOrgUnitRefAccOrg refAccOrg : list) {
            if (refAccOrg.getDefaultFlag() == "Y" && checkUnitAccDefault(refAccOrg) > 0) {
                throw new UnitAccOrBgtDfMultiException("EXP",
                                UnitAccOrBgtDfMultiException.ERROR_UNIT_ACC_DEFAULT_MULTI);
            }
            ExpOrgUnitRefAccOrg expOrgUnitRefAccOrg = new ExpOrgUnitRefAccOrg();
            switch (refAccOrg.get__status()) {
                case DTOStatus.ADD:
                    expOrgUnitRefAccOrg = self().insertSelective(request, refAccOrg);
                    break;
                case DTOStatus.UPDATE:
                    expOrgUnitRefAccOrg = self().updateByPrimaryKey(request, refAccOrg);
                    break;
                case DTOStatus.DELETE:
                    self().deleteByPrimaryKey(refAccOrg);
                    break;
                default:
                    break;

            }
            refAccOrgList.add(expOrgUnitRefAccOrg);
        }
        return refAccOrgList;
    }

    @Override
    public int checkUnitAccDefault(ExpOrgUnitRefAccOrg refAccOrg) {
        ExpOrgUnitRefAccOrg expOrgUnitRefAccOrg = new ExpOrgUnitRefAccOrg();
        expOrgUnitRefAccOrg.setUnitId(refAccOrg.getUnitId());
        expOrgUnitRefAccOrg.setAccEntityId(refAccOrg.getAccEntityId());
        expOrgUnitRefAccOrg.setDefaultFlag("Y");
        return mapper.selectCount(expOrgUnitRefAccOrg);
    }
}
