package com.hand.hec.acp.dto;

/**
 * <p>
 * 付款申请单明细表
 * </p>
 *
 * @author guiyuting 2019/04/30 11:08
 */
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "acp_requisition_dtl")
public class AcpRequisitionDtl extends BaseDTO {

    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_REQUISITION_DTL_ID = "requisitionDtlId";
    public static final String FIELD_REQUISITION_LNS_ID = "requisitionLnsId";
    public static final String FIELD_LINE_NUMBER = "lineNumber";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_REF_DOCUMENT_TYPE = "refDocumentType";
    public static final String FIELD_REF_DOCUMENT_ID = "refDocumentId";
    public static final String FIELD_REF_DOCUMENT_LINE_ID = "refDocumentLineId";
    public static final String FIELD_PAID_AMOUNT = "payedAmount";

    /**
     * 单据类型
     *
     * @DOC_TYPE_REPORT 报销单
     * @DOC_TYPE_CONTRACT 合同
     */
    public static final String DOC_TYPE_REPORT = "REPORT";
    public static final String DOC_TYPE_CONTRACT = "CONTRACT";

    /**
     * 金额
     */
    private BigDecimal amount;

    @Id
    @GeneratedValue
    private Long requisitionDtlId;

    /**
     * 付款申请单行表ID
     */
    @NotNull
    private Long requisitionLnsId;

    /**
     * 行号
     */
    @NotNull
    private Long lineNumber;

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

    /**
     * 单据类型（REPORT/报销单、CONTRACT/合同）
     */
    @NotEmpty
    @Length(max = 30)
    private String refDocumentType;

    /**
     * 单据头ID（报销单头ID、合同头ID）
     */
    @NotNull
    private Long refDocumentId;

    /**
     * 单据付款行ID（报销单结算行ID、合同结算行ID）
     */
    @NotNull
    private Long refDocumentLineId;

    @Transient
    private Long requisitionHdsId;

    @Transient
    private Long employeeId;

    @Transient
    private Long moPayReqTypeId;

    @Transient
    private String currencyCode;

    @Transient
    private String documentNumber;

    @Transient
    private Long documentLineNumber;

    @Transient
    private String payeeCategory;

    @Transient
    private String payeeCategoryName;

    @Transient
    private Long payeeId;

    @Transient
    private String payeeName;

    @Transient
    private BigDecimal totalAmount;

    /**
     * 已付金额
     */
    @Transient
    private BigDecimal payedAmount;

    @Transient
    private BigDecimal appliedAmount;

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    private Date scheduleDueDate;

    @Transient
    private String paymentCurrencyCode;

    @Transient
    private Long expReportHeaderId;

}
