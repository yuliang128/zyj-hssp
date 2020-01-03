package com.hand.hec.bgt.dto;

import java.util.List;

import lombok.*;

import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hec.exp.dto.ExpEmployeeJob;
import com.hand.hec.exp.dto.ExpOrgPosition;
import com.hand.hec.exp.dto.ExpOrgUnit;

/**
 * <p>
 * 预算余额查询准备数据
 * </p>
 *
 * @author YHL 2019/03/22 14:30
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtBalanceQueryData {

    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_ENTITY_FLAG = "entityFlag";
    public static final String FIELD_PERIOD_LOWER = "periodLower";
    public static final String FIELD_PERIOD_UPPER = "periodUpper";
    public static final String FIELD_BUDGET_ITEM_LIST = "budgetItemList";
    public static final String FIELD_EMPLOYEE_LIST = "employeeList";
    public static final String FIELD_POSITION_LIST = "positionList";
    public static final String FIELD_EMPLOYEE_JOB_LIST = "employeeJobList";
    public static final String FIELD_UNIT_LIST = "unitList";

    /**
     * 预算组织ID
     */
    private Long bgtOrgId;

    /**
     * 预算表中的预算实体是否勾选
     */
    private String entityFlag;

    /**
     * 预算期间始值
     */
    private BgtPeriod periodLower;

    /**
     * 预算期间止值
     */
    private BgtPeriod periodUpper;

    /**
     * 预算项目
     */
    private List<BgtBudgetItem> budgetItemList;

    /**
     * 员工
     */
    private List<ExpEmployee> employeeList;

    /**
     * 岗位
     */
    private List<ExpOrgPosition> positionList;

    /**
     * 员工职务
     */
    private List<ExpEmployeeJob> employeeJobList;

    /**
     * 部门
     */
    private List<ExpOrgUnit> unitList;

}
