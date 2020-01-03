package com.hand.hec.exp.service.impl;
/**
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/3/25 14:07
 * Description:申请单类型ServiceImpl
 */

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoReqType;
import com.hand.hec.exp.dto.ExpReqTypeChoiceCurrentInfor;
import com.hand.hec.exp.mapper.ExpMoReqTypeMapper;
import com.hand.hec.exp.mapper.ExpReqTypeChoiceCurrentInforMapper;
import com.hand.hec.exp.service.IExpMoReqTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoReqTypeServiceImpl extends BaseServiceImpl<ExpMoReqType> implements IExpMoReqTypeService {
    @Autowired
    ExpMoReqTypeMapper expMoReqTypeMapper;

    @Autowired
    ExpReqTypeChoiceCurrentInforMapper expReqTypeChoiceCurrentInforMapper;

    /**
     * 查询用户可申请的申请单类型
     *
     * @param request
     * @return 返回申请单类型列表
     * @author jiangxz 2019-02-25 14:43
     */
    @Override
    public List<ExpMoReqType> queryChoiceRequisitionTypeInfor(IRequest request) {
        return expMoReqTypeMapper.queryChoiceRequisitionTypeInfor();

    }

    /**
     * 查询选择类型当前用户信息
     *
     * @param request
     * @param employeeId 员工id
     * @return 返回当前用户信息列表
     * @author jiangxz 2019-02-25 14:43
     */
    @Override
    public List<ExpReqTypeChoiceCurrentInfor> queryExpReqTypeChoiceCurrentInfor(Long employeeId, IRequest request) {
        return expReqTypeChoiceCurrentInforMapper.queryExpReqTypeChoiceCurrentInfor(employeeId);

    }

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}