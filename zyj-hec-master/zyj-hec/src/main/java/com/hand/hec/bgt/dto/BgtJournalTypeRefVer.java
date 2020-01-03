package com.hand.hec.bgt.dto;

/**
 * <p>
 * 预算日记账类型关联预算版本
 * </p>
 *
 * @author guiyuting 2019/03/20 16:39
 */
import javax.persistence.*;

import com.hand.hap.core.BaseConstants;
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
import javax.persistence.Table;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "bgt_journal_type_ref_ver")
public class BgtJournalTypeRefVer extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_BGT_JOURNAL_TYPE_ID = "bgtJournalTypeId";
    public static final String FIELD_VERSION_ID = "versionId";
    public static final String FIELD_VERSION_CODE = "versionCode";
    public static final String FIELD_VERSION_NAME = "versionName";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 预算日记账类型ID
     */
    @Where
    @NotNull
    private Long bgtJournalTypeId;

    /**
     * 预算版本ID
     */
    @NotNull
    @JoinTable(name = "versionJoin", joinMultiLanguageTable = true, target = BgtVersion.class, type = JoinType.LEFT,
            on = {@JoinOn(joinField = BgtVersion.FIELD_VERSION_ID),
                    @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @JoinTable(name = "versionJoin2", joinMultiLanguageTable = false, target = BgtVersion.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = BgtVersion.FIELD_VERSION_ID)})
    private Long versionId;

    @Transient
    @JoinColumn(joinName = "versionJoin2", field = BgtVersion.FIELD_VERSION_CODE)
    private String versionCode;

    @Transient
    @JoinColumn(joinName = "versionJoin", field = BgtVersion.FIELD_DESCRIPTION)
    private String versionName;

}
