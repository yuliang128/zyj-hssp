package com.hand.hec.expm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.extensible.components.ServiceListenerChainFactory;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.expm.dto.ExpPolicyPlcTpRefPlc;
import com.hand.hec.expm.mapper.ExpPolicyPlcTpAsgnComMapper;
import com.hand.hec.expm.mapper.ExpPolicyPlcTpRefPlcMapper;
import com.hand.hec.expm.service.IExpPolicyPlcTpRefPlcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.expm.dto.ExpPolicyPlcTpAsgnCom;
import com.hand.hec.expm.service.IExpPolicyPlcTpAsgnComService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 费用政策地点类型分配公司ServiceImpl
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 15:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpPolicyPlcTpAsgnComServiceImpl extends BaseServiceImpl<ExpPolicyPlcTpAsgnCom> implements IExpPolicyPlcTpAsgnComService {
    @Autowired
    ExpPolicyPlcTpAsgnComMapper expPolicyPlcTpAsgnComMapper;
    @Autowired
    ExpPolicyPlcTpRefPlcMapper expPolicyPlcTpRefPlcMapper;
    @Autowired
    IExpPolicyPlcTpRefPlcService expPolicyPlcTpRefPlcService;
    @Autowired
    private ServiceListenerChainFactory chainFactory;

    @Override
    public List<ExpPolicyPlcTpAsgnCom> queryRemainExpPolicyPlcTpAsgnCom(IRequest request, ExpPolicyPlcTpAsgnCom dto, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        return expPolicyPlcTpAsgnComMapper.queryRemainExpPolicyPlcTpAsgnCom(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(ExpPolicyPlcTpAsgnCom record) {
        record = (ExpPolicyPlcTpAsgnCom) chainFactory.getChain(this).beforeDelete(null, record);
        ExpPolicyPlcTpRefPlc data = new ExpPolicyPlcTpRefPlc();
        data.setAssignId(record.getAssignId());
        List<ExpPolicyPlcTpRefPlc> datas = expPolicyPlcTpRefPlcMapper.select(data);
        for (ExpPolicyPlcTpRefPlc d : datas) {
            expPolicyPlcTpRefPlcService.deleteByPrimaryKey(d);
        }
        int ret = mapper.deleteByPrimaryKey(record);
        checkOvn(ret, record);
        record = (ExpPolicyPlcTpAsgnCom) chainFactory.getChain(this).afterDelete(null, record);
        return ret;
    }


}