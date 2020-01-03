package com.hand.hec.exp.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hec.expm.dto.ExpReportPageElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * ExpMoRepTypeRefEle
 * </p>
 *
 * @author yang.duan 2019/01/10 14:45
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_rep_type_ref_ele")
public class ExpMoRepTypeRefEle extends BaseDTO {

    public static final String FIELD_REP_PAGE_ELE_REF_ID = "repPageEleRefId";
    public static final String FIELD_MO_EXP_REPORT_TYPE_ID = "moExpReportTypeId";
    public static final String FIELD_REPORT_PAGE_ELEMENT_ID = "reportPageElementId";
    public static final String FIELD_SEQUENCE = "sequence";
    public static final String FIELD_DOC_TYPE_CODE = "docTypeCode";
    public static final String FIELD_INVOICE_FLAG = "invoiceFlag";
    public static final String FIELD_TAX_FLAG = "taxFlag";
    public static final String FIELD_MORE_INVOICE_FLAG = "moreInvoiceFlag";
    public static final String FIELD_MORE_TAX_FLAG = "moreTaxFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

    public static final String FIELD_REPORT_PAGE_ELEMENT_NAME = "reportPageElementName";

    @Id
    @GeneratedValue
    private Long repPageEleRefId;

    /**
     * 管理组织级报销单类型ID
     */
    @NotNull
    @Where
    private Long moExpReportTypeId;

    /**
     * 报销单页面元素ID
     */
    @NotNull
    @JoinTable(name = "pageElementJoin", target = ExpReportPageElement.class, joinMultiLanguageTable = true,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = ExpReportPageElement.FIELD_REPORT_PAGE_ELEMENT_ID)})
    private Long reportPageElementId;

    /*
     * 报销单页面元素Name
     */
    @Transient
    @JoinColumn(joinName = "pageElementJoin", field = ExpReportPageElement.FIELD_DESCRIPTION)
    @Where
    private String reportPageElementName;

    /*
     * 报销类型标志
     */
    @Transient
    @JoinColumn(joinName = "pageElementJoin",field = ExpReportPageElement.FIELD_REPORT_TYPE_FLAG)
    private String reportTypeFlag;


    /*
     * 费用对象标志
     */
    @Transient
    @JoinColumn(joinName = "pageElementJoin",field = ExpReportPageElement.FIELD_EXPENSE_OBJECT_FLAG)
    private String expenseObjectFlag;


    /*
     * 维度标志
     */
    @Transient
    @JoinColumn(joinName = "pageElementJoin",field = ExpReportPageElement.FIELD_DIMENSION_FLAG)
    private String dimensionFlag;

    /**
     * 页面元素排序
     */
    @OrderBy
    private Long sequence;

    /**
     * 表单类型（SYSCODE：EXP_DOC_PAGE_TYPE）
     */
    @NotEmpty
    @Length(max = 30)
    private String docTypeCode;

    /**
     * 发票标识
     */
    @NotEmpty
    @Length(max = 1)
    private String invoiceFlag;

    /**
     * 税额标识
     */
    @NotEmpty
    @Length(max = 1)
    private String taxFlag;

    /**
     * 更多发票标识
     */
    @NotEmpty
    @Length(max = 1)
    private String moreInvoiceFlag;

    /**
     * 更多税额标识
     */
    @NotEmpty
    @Length(max = 1)
    private String moreTaxFlag;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    @Transient
    private String reportPageElementCode;

    @Transient
    private String serviceName;

    @Transient
    private String readonlyServiceName;

    @Transient
    private String lineDs;

}
