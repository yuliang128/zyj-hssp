package com.hand.hec.fnd.service.impl;

import java.util.List;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.PurVenderType;
import com.hand.hec.fnd.dto.PurVenderTypeRefAe;
import com.hand.hec.fnd.mapper.PurVenderTypeRefAeMapper;
import com.hand.hec.fnd.service.IPurVenderTypeRefAeService;
import com.hand.hec.gld.dto.GldAccountingEntity;

/**
 * <p>
 * 供应商类型分配核算主体ServiceImpl
 * </p>
 *
 * @author YHL 2019/01/29 12:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PurVenderTypeRefAeServiceImpl extends BaseServiceImpl<PurVenderTypeRefAe>
        implements IPurVenderTypeRefAeService {

    @Autowired
    private PurVenderTypeRefAeMapper mapper;

    @Override
    public List<PurVenderTypeRefAe> getVenderTypeRefAeByVenderTypeId(IRequest request, Long venderTypeId, int pageNum,
            int pageSize) {
        return mapper.getVenderTypeRefAeByVenderTypeId(venderTypeId);
    }

    @Override
    public Boolean multipleAssign(IRequest requestCtx, List<GldAccountingEntity> accountingEntity) {
        IBaseService<PurVenderTypeRefAe> self = ((IBaseService<PurVenderTypeRefAe>) AopContext.currentProxy());
        for (GldAccountingEntity ae : accountingEntity) {
            List<PurVenderType> venderType = ae.getVenderTypeDetail();
            for (PurVenderType vt : venderType) {
                //针对选中的每一供应商类型和每一核算主体一一检查是否建立过分配关系，然后做不同操作
                if (mapper.checkIfAsgn(vt.getVenderTypeId(), ae.getAccEntityId()) == 0) {
                    //未分配过.进行绑定
                    PurVenderTypeRefAe vtra = new PurVenderTypeRefAe();
                    vtra.setAccEntityId(ae.getAccEntityId());
                    vtra.setVenderTypeId(vt.getVenderTypeId());
                    vtra.setEnabledFlag("Y");
                    self.insertSelective(requestCtx, vtra);
                }
            }
        }
        return true;
    }
}