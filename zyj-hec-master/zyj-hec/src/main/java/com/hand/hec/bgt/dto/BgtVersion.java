package com.hand.hec.bgt.dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 预算版本
 * </p>
 *
 * @author mouse 2019/01/07 16:45
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_version")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@MultiLanguage
public class BgtVersion extends BaseDTO {

    public static final String FIELD_VERSION_ID = "versionId";
    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_VERSION_CODE = "versionCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_VERSION_DATE = "versionDate";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_NOTES = "notes";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_VERSION_CODE_FROM = "versionCodeFrom";
    public static final String FIELD_VERSION_CODE_TO = "versionCodeTo";
    public static final String FIELD_BGT_JOURNAL_TYPE_ID = "bgtJournalTypeId";


    /**
     * 主键，供其他表外键使用
     */
    @Id
    @GeneratedValue
    @Where
    private Long versionId;

    /**
     * 预算组织ID
     */
    @NotNull
    @Where
    private Long bgtOrgId;

    /**
     * 预算版本代码
     */
    @Where(comparison = Comparison.LIKE)
    @OrderBy("ASC")
    @NotEmpty
    @Length(max = 30)
    private String versionCode;

    /**
     * 预算版本描述
     */
    @MultiLanguageField
    @Where(comparison = Comparison.LIKE)
    @NotEmpty
    @Length(max = 500)
    private String description;

    /**
     * 版本日期
     */
    private Date versionDate;

    /**
     * 状态（SYSCODE：BGT_VERSION_STATUS）
     */
    @Where
    @NotEmpty
    @Length(max = 30)
    private String status;

    /**
     * 备注
     */
    @Length(max = 2000)
    private String notes;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String enabledFlag;


    @Transient
    private String versionCodeFrom;

    @Transient
    private String versionCodeTo;

    @Transient
    @Where(expression = " and exists (select 1 from bgt_journal_type_ref_ver rv where rv.version_id = a.version_id and rv.bgt_journal_type_id = #{dto.bgtJournalTypeId})")
    private Long bgtJournalTypeId;
}
