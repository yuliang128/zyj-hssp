package com.hand.hec.expm.service.impl;

import com.hand.hap.extensible.components.ServiceListenerChainFactory;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.expm.dto.ExpPolicyPlcTpAsgnCom;
import com.hand.hec.expm.mapper.ExpPolicyPlcTpAsgnComMapper;
import com.hand.hec.expm.service.IExpPolicyPlcTpAsgnComService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.expm.dto.ExpPolicyPlaceType;
import com.hand.hec.expm.service.IExpPolicyPlaceTypeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 费用政策地点类型ServiceImpl
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 15:50
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpPolicyPlaceTypeServiceImpl extends BaseServiceImpl<ExpPolicyPlaceType> implements IExpPolicyPlaceTypeService {
    @Autowired
    private ServiceListenerChainFactory chainFactory;
    @Autowired
    ExpPolicyPlcTpAsgnComMapper expPolicyPlcTpAsgnComMapper;
    @Autowired
    IExpPolicyPlcTpAsgnComService expPolicyPlcTpAsgnComService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(ExpPolicyPlaceType record) {
        record = (ExpPolicyPlaceType) chainFactory.getChain(this).beforeDelete(null, record);
        ExpPolicyPlcTpAsgnCom data = new ExpPolicyPlcTpAsgnCom();
        data.setPlaceTypeId(record.getPlaceTypeId());
        List<ExpPolicyPlcTpAsgnCom> datas = expPolicyPlcTpAsgnComMapper.select(data);
        for (ExpPolicyPlcTpAsgnCom d : datas) {
            expPolicyPlcTpAsgnComService.deleteByPrimaryKey(d);
        }
        int ret = mapper.deleteByPrimaryKey(record);
        checkOvn(ret, record);
        record = (ExpPolicyPlaceType) chainFactory.getChain(this).afterDelete(null, record);
        return ret;
    }
}