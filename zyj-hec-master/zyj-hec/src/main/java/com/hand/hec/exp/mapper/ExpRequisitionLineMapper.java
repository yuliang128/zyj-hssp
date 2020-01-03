package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpRequisitionLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ExpRequisitionLineMapper extends Mapper<ExpRequisitionLine> {

    List<Map> queryDetailLine(@Param("headId") long headId);

    /**
     * 申请单行信息查询
     *
     * @param expRequisitionHeaderId 申请单头id
     * @return List<ExpRequisitionLine>
     * @author jiangxuzheng@hand-china.com 2019-03-28 10:00
     */
    List<ExpRequisitionLine> expReqLineQuery(@Param("expRequisitionHeaderId") long expRequisitionHeaderId);

    /**
     * <p>报销单关联申请单行查询(头关联)</p>
     *
     * @param reportPageElementCode
     * @param expRequisitionHeaderId
     * @param moExpReportTypeId
     * @param monopolizeFlag
     * @param paymentCurrencyCode
     * @param uncompletelyReleasedFlag
     * @return List<Map>
     * @author yang.duan 2019/4/26 14:34
     **/
    List<Map> expReportFromReqQuery(@Param("reportPageElementCode") String reportPageElementCode, @Param("moExpReportTypeId") Long moExpReportTypeId, @Param("expRequisitionHeaderId") Long expRequisitionHeaderId, @Param("uncompletelyReleasedFlag") String uncompletelyReleasedFlag, @Param("paymentCurrencyCode") String paymentCurrencyCode, @Param("monopolizeFlag") String monopolizeFlag);
}