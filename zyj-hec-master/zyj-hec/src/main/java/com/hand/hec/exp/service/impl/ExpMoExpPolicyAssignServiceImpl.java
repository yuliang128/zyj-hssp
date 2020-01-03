package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoExpPolicyAssignDtl;
import com.hand.hec.exp.mapper.ExpMoExpPolicyAssignDtlMapper;
import com.hand.hec.exp.service.IExpMoExpPolicyAssignDtlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoExpPolicyAssign;
import com.hand.hec.exp.service.IExpMoExpPolicyAssignService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoExpPolicyAssignServiceImpl extends BaseServiceImpl<ExpMoExpPolicyAssign> implements IExpMoExpPolicyAssignService{

    @Autowired
    ExpMoExpPolicyAssignDtlMapper detailmapper;

    @Autowired
    IExpMoExpPolicyAssignDtlService dtlService;

    /**
     * 删除政策标准分配对象之前，先删除政策标准代码
     * @param dto
     * @return
     */
    @Override
    public int deleteByPrimaryKey(ExpMoExpPolicyAssign dto){
        ExpMoExpPolicyAssignDtl expMoExpPolicyAssignDtl = new ExpMoExpPolicyAssignDtl();
        expMoExpPolicyAssignDtl.setAssignId(dto.getAssignId());
        List<ExpMoExpPolicyAssignDtl> dtlList = detailmapper.select(expMoExpPolicyAssignDtl);
        if(dtlList!=null && dtlList.size()!=0){
            for(ExpMoExpPolicyAssignDtl dtl : dtlList){
                dtlService.deleteByPrimaryKey(dtl);
            }
        }
        return super.deleteByPrimaryKey(dto);
    }
}