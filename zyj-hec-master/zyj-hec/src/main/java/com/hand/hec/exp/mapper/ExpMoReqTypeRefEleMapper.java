package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoReqTypeRefEle;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExpMoReqTypeRefEleMapper extends Mapper<ExpMoReqTypeRefEle>{


    /**
     * 查询当前申请单类型下页面元素信息
     *
     * @param moExpReqTypeId          申请单类型id
     * @param expRequisitionHeaderId  申请单头id
     * @return 返回对象值
     * @author jiangxz 2019/4/18 10:27
     */
    List<ExpMoReqTypeRefEle> expReqEleQuery(@Param("moExpReqTypeId") Long moExpReqTypeId, @Param("expRequisitionHeaderId") Long expRequisitionHeaderId);
}