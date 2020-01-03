package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.exception.UnitAccOrBgtDfMultiException;
import com.hand.hec.exp.mapper.ExpOrgUnitRefBgtOrgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpOrgUnitRefBgtOrg;
import com.hand.hec.exp.service.IExpOrgUnitRefBgtOrgService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * ExpOrgUnitRefBgtOrgServiceImpl
 * </p>
 * 
 * @author yang.duan 2019/01/10 11:18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpOrgUnitRefBgtOrgServiceImpl extends BaseServiceImpl<ExpOrgUnitRefBgtOrg>
                implements IExpOrgUnitRefBgtOrgService {
    @Autowired
    private ExpOrgUnitRefBgtOrgMapper mapper;

    @Override
    public List<ExpOrgUnitRefBgtOrg> batchSubmit(IRequest request, List<ExpOrgUnitRefBgtOrg> list)
                    throws UnitAccOrBgtDfMultiException {
        List<ExpOrgUnitRefBgtOrg> refBgtOrgList = new ArrayList<>();
        for (ExpOrgUnitRefBgtOrg refBgtOrg : list) {
            if (refBgtOrg.getDefaultFlag() == "Y" && checkUnitBgtDefault(refBgtOrg) >= 1) {
                throw new UnitAccOrBgtDfMultiException("EXP",
                                UnitAccOrBgtDfMultiException.ERROR_UNIT_BGT_DEFAULT_MULTI);
            }
            ExpOrgUnitRefBgtOrg expOrgUnitRefBgtOrg = new ExpOrgUnitRefBgtOrg();
            switch (refBgtOrg.get__status()) {
                case DTOStatus.ADD:
                    expOrgUnitRefBgtOrg = self().insertSelective(request, refBgtOrg);
                    break;
                case DTOStatus.UPDATE:
                    expOrgUnitRefBgtOrg = self().updateByPrimaryKey(request, refBgtOrg);
                    break;
                case DTOStatus.DELETE:
                    self().deleteByPrimaryKey(refBgtOrg);
                    break;
                default:
                    break;
            }
            refBgtOrgList.add(expOrgUnitRefBgtOrg);
        }
        return refBgtOrgList;
    }

    @Override
    public int checkUnitBgtDefault(ExpOrgUnitRefBgtOrg refBgtOrg) {
        ExpOrgUnitRefBgtOrg expOrgUnitRefBgtOrg = new ExpOrgUnitRefBgtOrg();
        expOrgUnitRefBgtOrg.setUnitId(refBgtOrg.getUnitId());
        expOrgUnitRefBgtOrg.setBgtEntityId(refBgtOrg.getBgtEntityId());
        expOrgUnitRefBgtOrg.setDefaultFlag("Y");
        return mapper.selectCount(expOrgUnitRefBgtOrg);
    }

    @Override
    public ExpOrgUnitRefBgtOrg getDefaultRef(IRequest request, Long unitId, Long bgtEntityId) {
        ExpOrgUnitRefBgtOrg ref = new ExpOrgUnitRefBgtOrg();
        ref.setUnitId(unitId);
        ref.setBgtEntityId(bgtEntityId);
        ref.setEnabledFlag("Y");
        ref.setDefaultFlag("Y");
        List<ExpOrgUnitRefBgtOrg> refList = select(request, ref, 0, 0);
        if (refList != null && refList.size() != 0) {
            return refList.get(0);
        } else {
            return null;
        }
    }
}
