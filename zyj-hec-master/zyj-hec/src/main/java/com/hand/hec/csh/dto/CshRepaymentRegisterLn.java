package com.hand.hec.csh.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_repayment_register_ln")
public class CshRepaymentRegisterLn extends BaseDTO {

    public static final String FIELD_REGISTER_LNS_ID = "registerLnsId";
    public static final String FIELD_REGISTER_HDS_ID = "registerHdsId";
    public static final String FIELD_LINE_NUMBER = "lineNumber";
    public static final String FIELD_PAYMENT_REQUISITION_LINE_ID = "paymentRequisitionLineId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_ACCOUNT_NAME = "accountName";
    public static final String FIELD_ACCOUNT_NUMBER = "accountNumber";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
    public static final String FIELD_PAYEE_ID = "payeeId";
    public static final String FIELD_CASH_FLOW_ITEM_ID = "cashFlowItemId";


    @Id
    @GeneratedValue
    private Long registerLnsId;

    /**
     * 还款登记单头ID
     */
    private Long registerHdsId;

    /**
     * 行号
     */
    @NotNull
    private Long lineNumber;

    /**
     * 借款申请单行ID
     */
    @NotNull
    private Long paymentRequisitionLineId;

    /**
     * 管理公司ID
     */
    @NotNull
    private Long companyId;

    /**
     * 核算主体ID
     */
    @NotNull
    private Long accEntityId;

    @Transient
    private String accEntityName;

    /**
     * 还款人户名
     */
    @Length(max = 200)
    private String accountName;

    /**
     * 还款人账号
     */
    @Length(max = 200)
    private String accountNumber;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 说明
     */
    @Length(max = 2000)
    private String description;

    /**
     * 还款人类别
     */
    @Length(max = 30)
    private String payeeCategory;

    /**
     * 还款人ID
     */
    private Long payeeId;

    /**
     * 现金流量项ID
     */
    private Long cashFlowItemId;

    @Transient
    private String cashFlowItemName;

    @Transient
    private String requisitionNumber;

    @Transient
    private String requisitionDate;

    @Transient
    private String currencyName;

    @Transient
    private String payeeName;

    @Transient
    private Long transactionLineId;

    @Transient
    private Long paymentRequisitionHeaderId;

}
