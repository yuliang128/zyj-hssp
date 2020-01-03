package com.hand.hec.bgt.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtBudgetReserve;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 预算占用Mapper
 * </p>
 *
 * @author mouse 2019/01/07 16:47
 */
public interface BgtBudgetReserveMapper extends Mapper<BgtBudgetReserve> {

    /**
     * 更新状态
     *
     * @param businessType 业务类型
     * @param documentId   单据Id
     * @param status       状态
     * @return
     * @author user 2019-03-04 19:32
     */

    void updateForStatus(@Param("businessType") String businessType, @Param("documentId") Long documentId, @Param("status") String status);

    /**
     * 查询拆分税金对应的预算信息
     *
     * @param expReportHeaderId 报销单头Id
     * @param expReportLineId   报销单行Id
     * @param expReportDistId   报销单分配行Id
     * @return List<BgtBudgetReserve>
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/14 10:44
     * @Version 1.0
     **/
    List<BgtBudgetReserve> selectTaxAmountReserveInfo(@Param("expReportHeaderId") Long expReportHeaderId, @Param("expReportLineId") Long expReportLineId, @Param("expReportDistId") Long expReportDistId);

    /**
     * 获取预算占用金额合计
     *
     * @param documentType   单据类型
     * @param documentLineId 单据行Id
     * @return BgtBudgetReserve
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/29 16:48
     * @Version 1.0
     **/
    BgtBudgetReserve getBudgetReserveSum(String documentType, Long documentLineId);

    void updateRequisitionRelease(@Param("status") String status, @Param("userId") Long userId, @Param("expReportHeaderId") Long expReportHeaderId);

    void updateReqAmount(@Param("amount") BigDecimal amount, @Param("functionalAmount") BigDecimal functionalAmount, @Param("userId") Long userId, @Param("expRequisitionHeaderId") Long expRequisitionHeaderId, @Param("expRequisitionDistId") Long expRequisitionDistId, @Param("releaseId") Long releaseId);

    void updateReqQty(@Param("quantity") BigDecimal quantity, @Param("userId") Long userId, @Param("expRequisitionHeaderId") Long expRequisitionHeaderId, @Param("expRequisitionDistId") Long expRequisitionDistId, @Param("releaseId") Long releaseId);


    BgtBudgetReserve getBudgetReservesBalance(@Param("businessType") String businessType, @Param("documentLineId") Long documentLineId);

    /**
     * <p>根据报销单行获取预算占用行</p>
     *
     * @param expReportLineId 报销单行ID
     * @param budgetScheduleFlag 预算进度参数
     * @return List<BgtBudgetReserve>
     * @author yang.duan 2019/5/20 15:15
    **/
    List<BgtBudgetReserve> getBgtReserveByLineId(@Param("expReportLineId") Long expReportLineId,@Param("budgetScheduleFlag") String budgetScheduleFlag);
}
