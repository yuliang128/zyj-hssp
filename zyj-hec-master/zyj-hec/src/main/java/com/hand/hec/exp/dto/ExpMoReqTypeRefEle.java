package com.hand.hec.exp.dto;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.*;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.system.dto.BaseDTO;
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
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/2/20 17:19
 * Description: 申请单类型分配页面元素Dto
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_req_type_ref_ele")
public class ExpMoReqTypeRefEle extends BaseDTO {

    public static final String FIELD_REQ_PAGE_ELE_REF_ID = "reqPageEleRefId";
    public static final String FIELD_MO_EXP_REQ_TYPE_ID = "moExpReqTypeId";
    public static final String FIELD_REQ_PAGE_ELEMENT_ID = "reqPageElementId";
    public static final String FIELD_REQ_PAGE_ELEMENT_CODE = "reqPageElementCode";
    public static final String FIELD_REQ_PAGE_ELEMENT_NAME = "reqPageElementName";
    public static final String FIELD_SEQUENCE = "sequence";
    public static final String FIELD_DOC_TYPE_CODE = "docTypeCode";
    public static final String FIELD_DOC_TYPE_NAME = "docTypeName";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_REPORT_TYPE_FLAG = "reportTypeFlag";
    public static final String FIELD_EXPENSE_OBJECT_FLAG = "expenseObjectFlag";
    public static final String FIELD_DIMENSION_FLAG = "dimensionFlag";


    @Id
    @GeneratedValue
    private Long reqPageEleRefId;

    /**
     * 管理组织级费用申请单类型ID
     */
    @NotNull
    @Where
    private Long moExpReqTypeId;

    /**
     * 费用申请单页面元素ID
     */
    @NotNull
    @JoinTable(name = "ExpReqPageElementJoin", joinMultiLanguageTable = true, target = ExpReqPageElement.class, type = JoinType.LEFT, on = {@JoinOn(joinField = ExpReqPageElement.FIELD_REQ_PAGE_ELEMENT_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long reqPageElementId;

    /**
     * 费用申请单页面元素代码
     */
    @Transient
    @JoinColumn(joinName = "ExpReqPageElementJoin", field = ExpReqPageElement.FIELD_REQ_PAGE_ELEMENT_CODE)
    private String reqPageElementCode;

    /**
     * 费用申请单页面元素名称
     */
    @Transient
    @JoinColumn(joinName = "ExpReqPageElementJoin", field = ExpReqPageElement.FIELD_DESCRIPTION)
    private String reqPageElementName;
    /**
     * 页面排序顺序
     */
    private Long sequence;

    /**
     * 表单类型（SYSCODE：EXP_DOC_PAGE_TYPE）
     */
    @NotEmpty
    @Length(max = 30)
    private String docTypeCode;

    /**
     * 表单类型名称（SYSCODE：EXP_DOC_PAGE_TYPE）
     */
    @Transient
    @JoinCode(code = "EXP_DOC_PAGE_TYPE", joinKey = FIELD_DOC_TYPE_CODE)
    private String docTypeName;
    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 报销类型标识
     */
    @Transient
    @JoinColumn(joinName = "ExpReqPageElementJoin", field = ExpReqPageElement.FIELD_REPORT_TYPE_FLAG)
    private String reportTypeFlag;

    /**
     * 费用对象标识
     */
    @Transient
    @JoinColumn(joinName = "ExpReqPageElementJoin", field = ExpReqPageElement.FIELD_EXPENSE_OBJECT_FLAG)
    private String expenseObjectFlag;

    /**
     * 维度标识
     */
    @Transient
    @JoinColumn(joinName = "ExpReqPageElementJoin", field = ExpReqPageElement.FIELD_DIMENSION_FLAG)
    private String dimensionFlag;


    /**
     * 页面地址
     */
    @Transient
    private String serviceName;


    /**
     * 只读页面地址
     */
    @Transient
    private String readonlyServiceName;
    /**
     * 行DataSet
     */
    @Transient
    private String lineDs;
}
