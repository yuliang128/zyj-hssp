package com.hand.hec.exp.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;

import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "exp_mo_exp_policy_assign")
public class ExpMoExpPolicyAssign extends BaseDTO {

    public static final String FIELD_ASSIGN_ID = "assignId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_DOC_CATEGORY = "docCategory";
    public static final String FIELD_DOC_TYPE_ID = "docTypeId";
    public static final String FIELD_EXPENSE_TYPE_ID = "expenseTypeId";
    public static final String FIELD_EXP_ITEM_ID = "expItemId";
    public static final String FIELD_EXPENSE_TYPE_CODE = "expenseTypeCode";
    public static final String FIELD_EXPENSE_TYPE_NAME = "expenseTypeName";
    public static final String FIELD_EXP_ITEM_CODE = "expItemCode";
    public static final String FIELD_EXP_ITEM_NAME = "expItemName";
    public static final String FIELD_DOC_TYPE_CODE = "docTypeCode";
    public static final String FIELD_DOC_TYPE_NAME = "docTypeName";




    @Id
    @GeneratedValue
    private Long assignId;

    /**
     * 管理组织ID
     */
    @Where
    private Long magOrgId;

    /**
     * 单据类别
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String docCategory;

    /**
     * 单据类型ID
     */
    private Long docTypeId;

    /**
     * 单据类型代码
     */
    @Transient
    @Length(max = 30)
    private String docTypeCode;

    /**
     * 单据类型描述
     */
    @Transient
    @Length(max = 200)
    private String docTypeName;

    /**
     * 报销类型ID
     */

    private Long expenseTypeId;

    /**
     * 报销类型代码
     */
    @Transient
    @Length(max = 30)
    private String expenseTypeCode;
    /**
     * 报销类型名称
     */
    @Transient
    @Length(max=200)
    private String expenseTypeName;

    /**
     * 申请/费用项目ID
     */
    private Long expItemId;

    /**
     * 申请/费用项目代码
     */
    @Transient
    @Length(max = 30)
    private String expItemCode;

    /**
     * 申请/费用项目名称
     */
    @Transient
    @Length(max=200)
    private String expItemName;
}
