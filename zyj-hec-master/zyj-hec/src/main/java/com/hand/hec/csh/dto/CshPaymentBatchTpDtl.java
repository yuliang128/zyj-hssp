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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_payment_batch_tp_dtl")
public class CshPaymentBatchTpDtl extends BaseDTO {

    public static final String FIELD_TYPE_DTL_ID = "typeDtlId";
    public static final String FIELD_TYPE_ID = "typeId";
    public static final String FIELD_GROUP_CONDITION = "groupCondition";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

    /**
     * 增加分组条件
     *
     * @GROUP_CONDITION_DOCUMENT_CATEGORY 单据类别
     * @GROUP_CONDITION_DOCUMENT_TYPE 单据类型
     * @GROUP_CONDITION_PAYEE_CATEGORY 收款方类型
     * @GROUP_CONDITION_PAYEE_ID 收款方
     * @GROUP_CONDITION_PAYMENT_METHOD_ID 付款方式
     * @GROUP_CONDITION_PAYMENT_USEDE_ID 付款用途
     * @GROUP_CONDITION_TRANSACTION_NUM 付款事物编号
     */
    public static final String GROUP_CONDITION_DOCUMENT_CATEGORY = "DOCUMENT_CATEGORY";
    public static final String GROUP_CONDITION_DOCUMENT_TYPE = "DOCUMENT_TYPE";
    public static final String GROUP_CONDITION_PAYEE_CATEGORY = "PAYEE_CATEGORY";
    public static final String GROUP_CONDITION_PAYEE_ID = "PAYEE_ID";
    public static final String GROUP_CONDITION_PAYMENT_METHOD_ID = "PAYMENT_METHOD_ID";
    public static final String GROUP_CONDITION_PAYMENT_USEDE_ID = "PAYMENT_USEDE_ID";
    public static final String GROUP_CONDITION_TRANSACTION_NUM = "TRANSACTION_NUM";


    @Id
    @GeneratedValue
    @Where
    private Long typeDtlId;

    /**
     * 付款批类型定义
     */
    @NotNull
    @Where
    private Long typeId;

    /**
     * 分组条件（SYSCODE：CSH_PAYMENT_TRX_TP_GROUP_CONDITION）
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

}
