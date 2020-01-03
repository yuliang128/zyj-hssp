package com.hand.hec.gld.dto;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.*;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hec.exp.dto.ExpMoExpenseItem;
import com.hand.hec.exp.dto.ExpMoReportType;
import com.hand.hec.fnd.dto.GldAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "gld_transit_account")
public class GldTransitAccount extends BaseDTO {

     public static final String FIELD_TRANSIT_ACCOUNT_ID = "transitAccountId";
     public static final String FIELD_MAG_ORG_ID = "magOrgId";
     public static final String FIELD_SET_OF_BOOKS_ID = "setOfBooksId";
     public static final String FIELD_MO_EXPENSE_ITEM_ID = "moExpenseItemId";
     public static final String FIELD_MO_EXP_REPORT_TYPE_ID = "moExpReportTypeId";
     public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
     public static final String FIELD_DR_ACCOUNT_ID = "drAccountId";
     public static final String FIELD_CR_ACCOUNT_ID = "crAccountId";


     @Id
     @GeneratedValue
     private Long transitAccountId;

    /**
     * 管理组织
     */
     @NotNull
     @Where(comparison = Comparison.EQUAL)
     private Long magOrgId;

    /**
     * 账套
     */
     @NotNull
     @Where(comparison = Comparison.EQUAL)
     private Long setOfBooksId;

    /**
     * 费用项目
     */
     @NotNull
     @Where(comparison = Comparison.EQUAL)
     @JoinTable(name = "moExpenseItemJoin",target = ExpMoExpenseItem.class,type = JoinType.LEFT,joinMultiLanguageTable = true,on = {@JoinOn(joinField = ExpMoExpenseItem.FIELD_MO_EXPENSE_ITEM_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
     @JoinTable(name = "moExpenseItemJoin1",target = ExpMoExpenseItem.class,type = JoinType.LEFT,on = {@JoinOn(joinField = ExpMoExpenseItem.FIELD_MO_EXPENSE_ITEM_ID)})
     private Long moExpenseItemId;

    /**
     * 费用项目名称
     */
    @Transient
    @JoinColumn(joinName = "moExpenseItemJoin",field = ExpMoExpenseItem.FIELD_DESCRIPTION)
    private String moExpenseItemName;

    /**
     * 费用项目编码
     */
    @Transient
    @JoinColumn(joinName = "moExpenseItemJoin1",field = ExpMoExpenseItem.FIELD_MO_EXPENSE_ITEM_CODE)
    private String moExpenseItemCode;

    /**
     * 报销单类型
     */
    @JoinTable(name = "moExpReportTypeJoin",target = ExpMoReportType.class,type = JoinType.LEFT,joinMultiLanguageTable = true,on = {@JoinOn(joinField = ExpMoReportType.FIELD_MO_EXP_REPORT_TYPE_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @JoinTable(name = "moExpReportTypeJoin1",target = ExpMoReportType.class,type = JoinType.LEFT,on = {@JoinOn(joinField = ExpMoReportType.FIELD_MO_EXP_REPORT_TYPE_ID)})
    private Long moExpReportTypeId;

    /**
     * 报销单类型名称
     */
    @Transient
    @JoinColumn(joinName = "moExpReportTypeJoin",field = ExpMoReportType.FIELD_DESCRIPTION)
    private String moExpReportTypeName;

    /**
     * 报销单类型编码
     */
    @Transient
    @JoinColumn(joinName = "moExpReportTypeJoin1",field = ExpMoReportType.FIELD_MO_EXP_REPORT_TYPE_CODE)
    private String moExpReportTypeCode;

    /**
     * 核算主体
     */
    @JoinTable(name = "accEntityJoin",target = GldAccountingEntity.class,type = JoinType.LEFT,joinMultiLanguageTable = true,on = {@JoinOn(joinField = GldAccountingEntity.FIELD_ACC_ENTITY_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @JoinTable(name = "accEntityJoin1",target = GldAccountingEntity.class,type = JoinType.LEFT,on = {@JoinOn(joinField = GldAccountingEntity.FIELD_ACC_ENTITY_ID)})
    private Long accEntityId;

    /**
     * 核算主体名称
     */
    @Transient
    @JoinColumn(joinName = "accEntityJoin1",field = GldAccountingEntity.FIELD_ACC_ENTITY_NAME)
    private String accEntityName;

    /**
     * 核算主体编码
     */
    @Transient
    @JoinColumn(joinName = "accEntityJoin",field = GldAccountingEntity.FIELD_ACC_ENTITY_CODE)
    private String accEntityCode;

    /**
     * 借方科目
     */
     @NotNull
     @Where(comparison = Comparison.EQUAL)
     @JoinTable(name = "drAccountJoin",target = GldAccount.class,type = JoinType.LEFT,joinMultiLanguageTable = true,on = {@JoinOn(joinField = GldAccount.FIELD_ACCOUNT_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
     @JoinTable(name = "drAccountJoin1",target = GldAccount.class,type = JoinType.LEFT,on = {@JoinOn(joinField = GldAccount.FIELD_ACCOUNT_ID)})
     private Long drAccountId;

    /**
     * 借方科目名称
     */
    @Transient
    @JoinColumn(joinName = "drAccountJoin",field = GldAccount.FIELD_DESCRIPTION)
    private String drAccountName;

    /**
     * 借方科目编码
     */
    @Transient
    @JoinColumn(joinName = "drAccountJoin1",field = GldAccount.FIELD_ACCOUNT_CODE)
    private String drAccountCode;

    /**
     * 贷方科目
     */
     @NotNull
     @Where(comparison = Comparison.EQUAL)
     @JoinTable(name = "crAccountJoin",target = GldAccount.class,type = JoinType.LEFT,joinMultiLanguageTable = true,on = {@JoinOn(joinField = GldAccount.FIELD_ACCOUNT_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
     @JoinTable(name = "crAccountJoin1",target = GldAccount.class,type = JoinType.LEFT,on = {@JoinOn(joinField = GldAccount.FIELD_ACCOUNT_ID)})
     private Long crAccountId;

    /**
     * 贷方科目名称
     */
    @Transient
    @JoinColumn(joinName = "crAccountJoin",field = GldAccount.FIELD_DESCRIPTION)
    private String crAccountName;

    /**
     * 贷方科目编码
     */
    @Transient
    @JoinColumn(joinName = "crAccountJoin1",field = GldAccount.FIELD_ACCOUNT_CODE)
    private String crAccountCode;

     }
