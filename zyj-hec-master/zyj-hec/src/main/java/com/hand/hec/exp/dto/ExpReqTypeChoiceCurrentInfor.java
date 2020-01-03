package com.hand.hec.exp.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * description 申请单创建选择类型当前用户信息DTO
 *
 * @author jiangxz 2019/03/25 16:39
 */
@Getter
@Setter
public class ExpReqTypeChoiceCurrentInfor {
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_COMPANY_CODE = "companyCode";
    public static final String FIELD_COMPANY_SHORT_NAME = "companyShortName";
    public static final String FIELD_PAYMENT_CURRENCY_CODE = "paymentCurrencyCode";
    public static final String FIELD_PAYMENT_CURRENCY_NAME = "paymentCurrencyName";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_ACC_ENTITY_NAME = "accEntityName";
    public static final String FIELD_PAY_CURRENCY_CODE = "payCurrencyCode";
    public static final String FIELD_PAY_CURRENCY_NAME = "payCurrencyName";
    public static final String EMPLOYEE_ID = "employeeId";
    public static final String FIELD_EMPLOYEE_NAME = "employeeName";

    /**
     * 公司id
     */
    private Long companyId;

    /**
     * 公司代码
     */
    private String companyCode;

    /**
     * 公司名称
     */
    private String companyShortName;

    /**
     * 功能付款币种
     */
    private String paymentCurrencyCode;

    /**
     * 功能付款币种名称
     */
    private String paymentCurrencyName;

    /**
     * 核算主体id
     */
    private Long accEntityId;

    /**
     * 核算主体名称
     */
    private String accEntityName;

    /**
     * 付款币种代码
     */
    private String payCurrencyCode;

    /**
     * 付款币种名称
     */
    private String payCurrencyName;

    /**
     * 员工id
     */
    private Long employeeId;

    /**
     * 员工名称
     */
    private String employeeName;
}
