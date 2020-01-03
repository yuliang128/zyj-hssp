package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hec.exp.dto.ExpMoExpPolicyDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 政策标准明细定义Mapper
 * </p>
 *
 * @author xiuxian.wu 2019/01/19 25:25
 */
public interface ExpMoExpPolicyDetailMapper extends Mapper<ExpMoExpPolicyDetail> {
    /**
     * 根据交通工具查询舱位/席别
     *
     * @param transportation 交通工具代码
     * @return 舱位/席别
     * @author xiuxian.wu 2019/02/18 19:56
     */
    List<CodeValue> querySeatClass(@Param("transportation") String transportation);

    /**
     * 查询所有舱位/席别
     * @return 舱位/席别
     */
    List<CodeValue> queryAllSeatClass();


    /**
     * <p>
     * 获取费用政策定义信息(对应原exp_mo_expense_policy_pkg.get_policy_info)
     * <p/>
     *
     * @param magOrgId 管理组织ID
     * @param now 当前时间
     * @param companyId 管理公司ID
     * @param accEntityCode 核算主体CODE
     * @param companyCode 管理公司CODE
     * @param countryCode 国家CODE
     * @param employeeJobCode 员工职务CODE
     * @param employeeLevelCode 员工级别CODE
     * @param placeCode 地点CODE
     * @param placeTypeCode 地点类型CODE
     * @param positionCode 岗位CODE
     * @param unitCode 部门CODE
     * @param vehicleCode 交通工具CODE
     * @param docCategory 单据类别
     * @param docTypeId 单据类型ID
     * @param expenseTypeId 报销类型ID
     * @param expenseItemId 费用项目ID
     * @param reqItemId 申请项目ID
     * @return 符合条件的政策标准明细DTO
     * @author yang.duan 2019/3/12 20:00
     */
    List<ExpMoExpPolicyDetail> getPolicyInfo(@Param("magOrgId") Long magOrgId, @Param("now") Date now,
                                             @Param("companyId") Long companyId, @Param("accEntityCode") String accEntityCode,
                                             @Param("companyCode") String companyCode, @Param("countryCode") String countryCode,
                                             @Param("employeeJobCode") String employeeJobCode,
                                             @Param("employeeLevelCode") String employeeLevelCode, @Param("placeCode") String placeCode,
                                             @Param("placeTypeCode") String placeTypeCode, @Param("positionCode") String positionCode,
                                             @Param("unitCode") String unitCode, @Param("vehicleCode") String vehicleCode,
                                             @Param("docCategory") String docCategory, @Param("docTypeId") Long docTypeId,
                                             @Param("expenseTypeId") Long expenseTypeId, @Param("expenseItemId") Long expenseItemId,
                                             @Param("reqItemId") Long reqItemId);
}