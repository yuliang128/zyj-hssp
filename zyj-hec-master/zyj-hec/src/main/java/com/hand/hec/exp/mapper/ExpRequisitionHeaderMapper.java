package com.hand.hec.exp.mapper;
/**
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/3/19 19:03
 * Description:申请单头表Mapper
 */

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpRequisitionHeader;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ExpRequisitionHeaderMapper extends Mapper<ExpRequisitionHeader> {
    /**
     * 借款单关联申请单查询
     *
     * @param headerId    借款申请单头Id
     * @param closeStatus 关闭状态
     * @return List<Map>
     * @author dingwei.ma@hand-china.com 2019-02-27 15:00
     */
    List<Map> queryByPayReqHeaderId(@Param("headerId") Long headerId, @Param("closeStatus") String closeStatus);

    /**
     * 借款单查询申请单是否部分关闭
     *
     * @param headerId 借款申请单头Id
     * @return String
     * @author dingwei.ma@hand-china.com 2019-02-27 15:00
     */
    String queryPartCloseFlag(@Param("headerId") Long headerId);

    /**
     * 借款单查询申请单是否全部关闭
     *
     * @param headerId 借款申请单头Id
     * @return String
     * @author dingwei.ma@hand-china.com 2019-02-27 15:00
     */
    String queryComCloseFlag(@Param("headerId") Long headerId);


    List<Map> queryExp5360(@Param("expRequisitionHeader") ExpRequisitionHeader expRequisitionHeader);

    List<Map> queryDetailHead(@Param("headId") long expRequisitionHeader);

    boolean closeDetailHead(@Param("headId") List<Long> headId);

    /**
     * 申请单查询
     *
     * @param expRequisitionHeader 申请单信息
     * @return List<ExpRequisitionHeader>
     * @author jiangxuzheng@hand-china.com 2019-03-19 15:00
     */
    List<ExpRequisitionHeader> queryExpRequisitionMain(@Param("expRequisitionHeader") ExpRequisitionHeader expRequisitionHeader);

    BigDecimal getReleaseAmount(@Param("distId") Long distId);

    BigDecimal getReleaseQuantity(@Param("distId") Long distId);

    Long getHeardIdByDistId(@Param("distId") Long distId);

    String getOneOffFlagByDistId(@Param("distId") Long distId);

    Long getbgtOrgId(@Param("distId") Long bgtEntityId);

    /**
     * 申请单创建查询申请单信息
     *
     * @param expRequisitionHeaderId 申请单头id
     * @return List<ExpRequisitionHeader>
     * @author jiangxuzheng@hand-china.com 2019-03-26 15:00
     */
    List<ExpRequisitionHeader> expRequisitionHeaderQuery(@Param("expRequisitionHeaderId") Long expRequisitionHeaderId);

    /**
     * 申请单创建查询申请单信息
     *
     * @param moExpReqTypeId      申请单类型id
     * @param accEntityId         核算主体id
     * @param paymentCurrencyCode 付款币种代码
     * @param employeeId          员工id
     * @return List<ExpRequisitionHeader>
     * @author jiangxuzheng@hand-china.com 2019-03-26 15:00
     */
    List<ExpRequisitionHeader> expReqHeaderCreateQuery(@Param("moExpReqTypeId") Long moExpReqTypeId, @Param("accEntityId") Long accEntityId, @Param("paymentCurrencyCode") String paymentCurrencyCode, @Param("employeeId") Long employeeId);


    /**
     * <p>报销单关联申请单查询(头关联)</p>
     *
     * @param moExpReportTypeId
     * @param expRequisitionNumber
     * @param description
     * @return List<ExpRequisitionHeader>
     * @author yang.duan 2019/4/25 14:50
     **/
    List<ExpRequisitionHeader> expReportFromReqHeaderQuery(@Param("moExpReportTypeId") Long moExpReportTypeId, @Param("expRequisitionNumber") String expRequisitionNumber,@Param("paymentCurrencyCode") String paymentCurrencyCode, @Param("description") String description);

}