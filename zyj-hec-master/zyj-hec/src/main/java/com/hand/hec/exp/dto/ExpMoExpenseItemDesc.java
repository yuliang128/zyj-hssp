package com.hand.hec.exp.dto;

import javax.persistence.*;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.common.query.Where;
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
 * 费用项目说明定义DTO
 * </p>
 *
 * @author yang.cai 2019/02/28 18:19
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "exp_mo_expense_item_desc")
@MultiLanguage
public class ExpMoExpenseItemDesc extends BaseDTO {

     public static final String FIELD_ITEM_DESC = "itemDesc";
     public static final String FIELD_MAG_ORG_ID = "magOrgId";
     public static final String FIELD_COMPANY_ID = "companyId";
     public static final String FIELD_MO_EXP_REPORT_TYPE_ID = "moExpReportTypeId";
     public static final String FIELD_MO_EXPENSE_TYPE_ID = "moExpenseTypeId";
     public static final String FIELD_MO_EXPENSE_ITEM_ID = "moExpenseItemId";
     public static final String FIELD_DESCRIPTION = "description";


     @Id
     @GeneratedValue
     private Long itemDesc;

    /**
     * 管理组织ID
     */
     @NotNull
     @Where
     private Long magOrgId;

     @Transient
     private String magOrgDisplay;

    /**
     * 管理公司ID
     */
    @Where
     private Long companyId;

     @Transient
     private String companyCode;

     @Transient
     private String companyFullName;

     @Transient
     private String companyName;

    /**
     * 报销单类型ID
     */
    @Where
     private Long moExpReportTypeId;

     @Transient
     private String moExpReportTypeCode;

     @Transient
     private String moExpReportTypeName;

    /**
     * 报销类型ID
     */
    @Where
     private Long moExpenseTypeId;

     @Transient
     private String moExpenseTypeCode;

     @Transient
     private String moExpenseTypeName;

    /**
     * 报销项目ID
     */
     @NotNull
     @Where
     private Long moExpenseItemId;

     @Transient
     private String moExpenseItemCode;

     @Transient
     private String moExpenseItemName;
    /**
     * 说明
     */
     @NotEmpty
     @Length(max = 4000)
     @MultiLanguageField
     private String description;

     }
