package com.hand.hec.exp.dto;

import javax.persistence.*;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * <p>
 * 申请项目说明DTO
 * </p>
 *
 * @author yang.cai 2019/02/27 18:43
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "exp_mo_req_item_desc")
@MultiLanguage
public class ExpMoReqItemDesc extends BaseDTO {

     public static final String FIELD_ITEM_DESC = "itemDesc";
     public static final String FIELD_MAG_ORG_ID = "magOrgId";
     public static final String FIELD_COMPANY_ID = "companyId";
     public static final String FIELD_MO_EXP_REQ_TYPE_ID = "moExpReqTypeId";
     public static final String FIELD_MO_EXPENSE_TYPE_ID = "moExpenseTypeId";
     public static final String FIELD_MO_REQ_ITEM_ID = "moReqItemId";
     public static final String FIELD_DESCRIPTION = "description";

    /**
     * 主键（Oracle为Varchar,修改为bigint）
     */
     @Id
     @GeneratedValue
     private Long itemDesc;

    /**
     * 管理组织ID
     */
     @NotNull
     private Long magOrgId;
    /**
     * 管理组织名
     */
     @Transient
     private String magOrgDisplay;

    /**
     * 管理公司ID
     */
     private Long companyId;
    /**
     * 公司代码
     */
     @Transient
     private String companyCode;
    /**
     * 公司简称
     */
     @Transient
     private String companyName;

    /**
     * 申请单类型ID
     */
     private Long moExpReqTypeId;
    /**
     * 申请单类型代码
     */
    @Transient
    private String moExpReqTypeCode;
    /**
     * 申请单类型名称
     */
    @Transient
    private String moExpReqTypeName;



    /**
     * 报销类型ID
     */
     private Long moExpenseTypeId;
    /**
     * 报销类型代码
     */
     @Transient
     private String moExpenseTypeCode;
    /**
     * 报销类型名称
     */
     @Transient
     private String moExpenseTypeName;

    /**
     * 申请项目ID
     */
     @NotNull
     private Long moReqItemId;
    /**
     * 申请项目代码
     */
     @Transient
     private String moReqItemCode;
    /**
     * 申请项目名称
     */
     @Transient
     private String moReqItemName;

    /**
     * 说明
     */
     @NotEmpty
     @Length(max = 4000)
     @MultiLanguageField
     private String description;

     }
