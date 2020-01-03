package com.hand.hec.expm.mapper;

import com.hand.hap.exp.dto.ExpEmployeeAssign;
import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.expm.dto.ExpReportHeader;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * ExpReportHeaderMapper
 * </p>
 *
 * @author yang.duan 2019/01/10 15:02
 */
public interface ExpReportHeaderMapper extends Mapper<ExpReportHeader>{
/**
     * 获取报销单头信息
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/2/18 11:12
     *@param  expReportHeaderId 报销单头ID
     * @return
     *@Version 1.0
     **/
    ExpReportHeader getExpReportHeader(@Param("expReportHeaderId") Long expReportHeaderId);

    /**
     * <p>我的费用报销主查询<p/>
     *
     * @param dto 查询DTO
     * @return 报销单主页面信息list
     * @author yang.duan 2019/3/12 14:04
     */
    List<ExpReportHeader> queryExpReportMain(ExpReportHeader dto);


    /**
     *报销单审核查询
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/8 10:50
     *@param expReportHeader 查询条件
     *@return List<ExpReportHeader>
     *@Version 1.0
     **/
    List<ExpReportHeader> auditQuery(ExpReportHeader expReportHeader);
    /**
     *报销单审核查询
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/8 10:50
     *@param expReportHeader 查询条件
     *@return List<ExpReportHeader>
     *@Version 1.0
     **/
    List<ExpReportHeader> reverseQuery(ExpReportHeader expReportHeader);

    /**
     * <p>报销单头查询<p/>
     *
     * @param expReportHeaderId 报销单头ID(新建时可以为NULL)
     * @param moExpReportTypeId 报销单类型ID
     * @param accEntityId 核算主体ID
     * @param employeeId 报销人ID
     * @param paymentCurrencyCode 付款币种
     * @return 报销单头list
     * @author yang.duan 2019/3/20 15:09
     */
    List<ExpReportHeader> expReportHeaderQuery(@Param("expReportHeaderId") Long expReportHeaderId,
                                               @Param("moExpReportTypeId") Long moExpReportTypeId, @Param("accEntityId") Long accEntityId,
                                               @Param("employeeId") Long employeeId, @Param("paymentCurrencyCode") String paymentCurrencyCode);


    /**
     * <p>获取分配行个数<p/>
     *
     * @param expReportHeaderId 报销单头ID
     * @return 分配行个数
     * @author yang.duan 2019/4/2 18:57
     */
    Long queryDistCount(@Param("expReportHeaderId") Long expReportHeaderId);

    /**
     * <p>单据行员工选择lov</p>
     *
     * @param companyId 公司ID
     * @param positionId  岗位ID
     * @param primaryPositionFlag 主岗标志
     * @param employeeCode  员工代码
     * @param employeeName  员工名称
     * @return List<ExpEmployeeAssign>
     * @author yang.duan 2019/5/16 16:26
    **/
    List<ExpEmployeeAssign> getEmployeeByCompanyId(@Param("companyId") Long companyId,
            @Param("positionId") Long positionId,@Param("primaryPositionFlag") String primaryPositionFlag,
            @Param("employeeCode") String employeeCode,@Param("employeeName") String employeeName);
}