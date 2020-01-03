package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
/**
 * <p>
 * 预算日记账类型
 * </p>
 * 
 * @author mouse 2019/01/07 16:36
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_journal_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@MultiLanguage
public class BgtJournalType extends BaseDTO {

    public static final String FIELD_BGT_JOURNAL_TYPE_ID = "bgtJournalTypeId";
    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_BGT_JOURNAL_TYPE_CODE = "bgtJournalTypeCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_BGT_BUSINESS_TYPE = "bgtBusinessType";
    public static final String FIELD_AUTHORITY_TYPE = "authorityType";
    public static final String FIELD_AUTO_APPROVE_FLAG = "autoApproveFlag";
    public static final String FIELD_IMPORT_FLAG = "importFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_DOCUMENT_PAGE_TYPE = "documentPageType";

    public static final String BUSINESS_TYPE_ADJUST = "ADJUST";


    /**
     * 预算日记账类型ID
     */
    @Id
    @GeneratedValue
    @Where
    private Long bgtJournalTypeId;

    /**
     * 预算组织ID
     */
    @NotNull
    @Where
    private Long bgtOrgId;

    /**
     * 预算日记账类型CODE
     */
    @NotEmpty
    @Length(max = 30)
    @Where(comparison = Comparison.LIKE)
    private String bgtJournalTypeCode;

    /**
     * 描述ID
     */
    @NotEmpty
    @Length(max = 500)
    @MultiLanguageField
    @Where(comparison = Comparison.LIKE)
    private String description;

    /**
     * 预算业务类型syscode
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String bgtBusinessType;

    /**
     * 权限类型
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String authorityType;

    /**
     * 自审批
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String autoApproveFlag;

    /**
     * 只可导入
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String importFlag;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String enabledFlag;

    /**
     * 单据页面类型
     */
    @Length(max = 30)
    @Where
    private String documentPageType;


}
