package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoExpPolicyDetail;

import java.util.List;

/**
 * <p>
 * 政策标准明细定义Service
 * </p>
 *
 * @author xiuxian.wu 2019/01/19 25:25
 */
public interface IExpMoExpPolicyDetailService
                extends IBaseService<ExpMoExpPolicyDetail>, ProxySelf<IExpMoExpPolicyDetailService> {
    /**
     * 根据交通工具查询舱位/席别
     *
     * @param request
     * @param transportation 交通工具代码
     * @return 舱位/席别
     * @author xiuxian.wu 2019/02/18 19:56
     */
    List<CodeValue> querySeatClass(String transportation,IRequest request);

    /**
     * <p>
     * 获取费用政策定义信息(对应原exp_mo_expense_policy_pkg.get_policy_info)
     * <p/>
     * 
     * @param request
     * @param magOrgId 管理组织ID
     * @param companyId 管理公司ID
     * @param vehicleCode 交通工具CODE
     * @param docCategory 单据类别
     * @param docTypeId 单据类型ID
     * @param expenseTypeId 报销类型ID
     * @param expenseItemId 费用项目ID
     * @param reqItemId 申请项目ID
     * @return 符合条件的政策标准明细DTO
     * @author yang.duan 2019/3/12 20:00
     */
    List<ExpMoExpPolicyDetail> getPolicyInfo(IRequest request, Long magOrgId, Long companyId, Long accEntityId,
                                             Long employeeJobId, Long employeeLevelId, Long placeId, Long placeTypeId, Long positionId,
                                             Long unitId, String vehicleCode, String docCategory, Long docTypeId, Long expenseTypeId,
                                             Long expenseItemId, Long reqItemId);


}
