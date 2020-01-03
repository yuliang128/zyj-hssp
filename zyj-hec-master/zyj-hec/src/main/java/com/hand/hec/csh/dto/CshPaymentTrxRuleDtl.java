package com.hand.hec.csh.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_payment_trx_rule_dtl")
public class CshPaymentTrxRuleDtl extends BaseDTO {

    public static final String FIELD_RULE_DTL_ID = "ruleDtlId";
    public static final String FIELD_RULE_ID = "ruleId";
    public static final String FIELD_GROUP_CONDITION = "groupCondition";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_SYSTEM_FLAG = "systemFlag";

    /**
     * 增加分组条件
     *
     * @GROUP_CONDITION_ACCOUNT_NAME 银行账户名
     * @GROUP_CONDITION_ACCOUNT_NUMBER 银行账号
     * @GROUP_CONDITION_ACC_ENTITY_ID 单据核算主体
     * @GROUP_CONDITION_CASH_FLOW_ITEM_ID 现金流量项
     * @GROUP_CONDITION_CURRENCY_CODE 币种
     * @GROUP_CONDITION_DOCUMENT_NUMBER 单据编号
     * @GROUP_CONDITION_DOCUMENT_TYPE 单据类型
     * @GROUP_CONDITION_PAYEE_CATEGORY 收款方类型
     * @GROUP_CONDITION_PAYEE_ID 收款方
     * @GROUP_CONDITION_PAYMENT_METHOD_ID 付款方式
     * @GROUP_CONDITION_PAYMENT_USEDE_ID 付款用途
     */
    public static final String GROUP_CONDITION_ACCOUNT_NAME = "ACCOUNT_NAME";
    public static final String GROUP_CONDITION_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
    public static final String GROUP_CONDITION_ACC_ENTITY_ID = "ACC_ENTITY_ID";
    public static final String GROUP_CONDITION_CASH_FLOW_ITEM_ID = "CASH_FLOW_ITEM_ID";
    public static final String GROUP_CONDITION_CURRENCY_CODE = "CURRENCY_CODE";
    public static final String GROUP_CONDITION_DOCUMENT_NUMBER = "DOCUMENT_NUMBER";
    public static final String GROUP_CONDITION_DOCUMENT_TYPE = "DOCUMENT_TYPE";
    public static final String GROUP_CONDITION_PAYEE_CATEGORY = "PAYEE_CATEGORY";
    public static final String GROUP_CONDITION_PAYEE_ID = "PAYEE_ID";
    public static final String GROUP_CONDITION_PAYMENT_METHOD_ID = "PAYMENT_METHOD_ID";
    public static final String GROUP_CONDITION_PAYMENT_USEDE_ID = "PAYMENT_USEDE_ID";


    @Id
    @GeneratedValue
    @Where
    private Long ruleDtlId;

    /**
     * 付款事物生成规则ID
     */

    @NotNull
    @Where
    private Long ruleId;

    /**
     * 分组条件（SYSCODE：CSH_PAYMENT_TRX_GROUP_CONDITION）
     */
    @NotEmpty
    @Length(max = 30)
    private String groupCondition;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String enabledFlag;

    /**
     * 预置标志
     */
    @NotEmpty
    @Length(max = 1)
    private String systemFlag;

}
