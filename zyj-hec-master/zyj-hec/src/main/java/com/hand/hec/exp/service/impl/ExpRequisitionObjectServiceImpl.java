package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpRequisitionObject;
import com.hand.hec.exp.mapper.ExpRequisitionObjectMapper;
import com.hand.hec.exp.service.IExpRequisitionObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpRequisitionObjectServiceImpl extends BaseServiceImpl<ExpRequisitionObject> implements IExpRequisitionObjectService {

    @Autowired
    ExpRequisitionObjectMapper expReqObjectMapper;

    /**
     * <p>
     * 申请单费用对象更新
     * <p/>
     *
     * @param dto 需要更新的申请单费用对象
     * @return 返回null
     * @author jiangxz 2019/4/4 13:26
     */
    @Override
    public ExpRequisitionObject updateExpReqObject(ExpRequisitionObject dto) {
        expReqObjectMapper.updateExpReqObject(dto);
        return dto;
    }

    /**
     * <p>
     * 查询申请单头对象
     * <p/>
     *
     * @param moExpReqTypeId 申请单类型ID
     * @return 费用头对象的list
     * @author jiangxz 2019/4/4 10:09
     */
    @Override
    public List<ExpRequisitionObject> queryObjectByReqHd(Long moExpReqTypeId) {
        return expReqObjectMapper.queryObjectByReqHd(moExpReqTypeId);

    }

    /**
     * <p>
     * 查询申请单行对象
     * <p/>
     *
     * @param moExpReqTypeId     申请单类型ID
     * @param reqPageElementCode 申请单行页面类型代码
     * @return 费用头对象的list
     * @author jiangxz 2019/4/4 10:09
     */
    @Override
    public List<ExpRequisitionObject> queryObjectByReqLn(Long moExpReqTypeId, Long reqPageElementCode) {
        return expReqObjectMapper.queryObjectByReqLn(moExpReqTypeId, reqPageElementCode);
    }
}