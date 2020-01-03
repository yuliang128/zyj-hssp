package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpRequisitionDist;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ExpRequisitionDistMapper extends Mapper<ExpRequisitionDist>{

   List<Long> queryDistByLineId(@Param("headId") long headId);

    /**
     *根据申请单头Id获取所有未关闭的申请单分配行数据
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/29 15:11
     *@param expRequisitionHeaderId 申请单头Id
     *@return List<ExpRequisitionDist>
     *@Version 1.0
     **/
    List<ExpRequisitionDist> getAllExpRequisitionDist(Long expRequisitionHeaderId);

    /**
     *根据申请单头Id,行Id,分配行Id获取所有未审核的报销单数据
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/29 15:11
     *@param expRequisitionHeaderId 申请单头Id
     *@param expRequisitionLineId 申请单行Id
     *@param expRequisitionDistId 申请单分配行Id
     *@return List<ExpRequisitionDist>
     *@Version 1.0
     **/
    int countUnAuditExpReport(Long expRequisitionHeaderId, Long expRequisitionLineId, Long expRequisitionDistId);

    /**
     * <p>获取申请单分配行总金额</p>
     *
     * @param expRequisitionHeaderId 申请单头ID
     * @param expReqDistsId 申请单分配行ID
     * @return the return
     * @author yang.duan 2019/5/5 14:50
    **/
    BigDecimal getTotalPaymentAmount(@Param("expRequisitionHeaderId") Long expRequisitionHeaderId,@Param("expReqDistsId") Long expReqDistsId);
}