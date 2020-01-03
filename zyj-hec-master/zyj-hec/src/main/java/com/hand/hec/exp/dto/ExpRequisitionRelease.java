package com.hand.hec.exp.dto;

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
import java.math.BigDecimal;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "exp_requisition_release")
public class ExpRequisitionRelease extends BaseDTO {

     public static final String FIELD_RELEASE_ID = "releaseId";
     public static final String FIELD_EXP_REQUISITION_HEADER_ID = "expRequisitionHeaderId";
     public static final String FIELD_EXP_REQUISITION_LINE_ID = "expRequisitionLineId";
     public static final String FIELD_EXP_REQUISITION_DIST_ID = "expRequisitionDistId";
     public static final String FIELD_COMPANY_ID = "companyId";
     public static final String FIELD_OPERATION_UNIT_ID = "operationUnitId";
     public static final String FIELD_DOCUMENT_TYPE = "documentType";
     public static final String FIELD_DOCUMENT_ID = "documentId";
     public static final String FIELD_DOCUMENT_LINE_ID = "documentLineId";
     public static final String FIELD_DOCUMENT_DIST_ID = "documentDistId";
     public static final String FIELD_REQ_RELEASE_AMOUNT = "reqReleaseAmount";
     public static final String FIELD_DOC_RELEASE_AMOUNT = "docReleaseAmount";
     public static final String FIELD_REQ_RELEASE_QUANTITY = "reqReleaseQuantity";
     public static final String FIELD_REQ_RELEASE_UOM = "reqReleaseUom";
     public static final String FIELD_DOC_RELEASE_QUANTITY = "docReleaseQuantity";
     public static final String FIELD_DOC_RELEASE_UOM = "docReleaseUom";


     @Id
     @GeneratedValue
     private Long releaseId;

    /**
     * 费用申请单头ID
     */
     private Long expRequisitionHeaderId;

    /**
     * 费用申请单行ID
     */
     private Long expRequisitionLineId;

    /**
     * 费用申请单分配行ID
     */
     private Long expRequisitionDistId;

    /**
     * 公司ID
     */
     private Long companyId;

    /**
     * 经营单位ID
     */
     private Long operationUnitId;

    /**
     * 单据类型
     */
     @NotEmpty
     @Length(max = 30)
     private String documentType;

    /**
     * 单据头ID
     */
     private Long documentId;

    /**
     * 单据行ID
     */
     private Long documentLineId;

    /**
     * 单据分配行ID
     */
     private Long documentDistId;

    /**
     * 申请单核销金额
     */
     private BigDecimal reqReleaseAmount;

    /**
     * 单据核销金额
     */
     private BigDecimal docReleaseAmount;

    /**
     * 申请单核销数量
     */
     private BigDecimal reqReleaseQuantity;

    /**
     * 申请单核销单位
     */
     @Length(max = 30)
     private String reqReleaseUom;

    /**
     * 单据核销数量
     */
     private BigDecimal docReleaseQuantity;

    /**
     * 单据核销单位
     */
     @Length(max = 30)
     private String docReleaseUom;

    /**
     * 已报销金额
     */
     @Transient
     private BigDecimal distReleaseAmount;

    /**
     * 已报销数量
     */
    @Transient
    private Long distReleaseQuantity;
     }
