package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoReqType;

import java.util.List;

public interface ExpMoReqTypeMapper extends Mapper<ExpMoReqType> {
    /**
     * 查询用户可申请的申请单类型
     *
     * @return 返回申请单类型列表
     * @author jiangxz 2019-02-25 14:43
     */
    List<ExpMoReqType> queryChoiceRequisitionTypeInfor();
}