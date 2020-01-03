package com.hand.hec.gld.dto;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hec.fnd.dto.GldAccountSet;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;


/**
 * <p>
 * 账套定义dto
 * </p>
 *
 * @author wuxiuxian 2019-01-07
 * update by luhui 2019/01/16 15:58
 */

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "gld_set_of_book")
@MultiLanguage
public class GldSetOfBook extends BaseDTO {

    public static final String FIELD_SET_OF_BOOKS_ID = "setOfBooksId";
    public static final String FIELD_SET_OF_BOOKS_NAME = "setOfBooksName";
    public static final String FIELD_SET_OF_BOOKS_CODE = "setOfBooksCode";
    public static final String FIELD_PERIOD_SET_CODE = "periodSetCode";
    public static final String FIELD_FUNCTIONAL_CURRENCY_CODE = "functionalCurrencyCode";
    public static final String FIELD_ACCOUNT_SET_ID = "accountSetId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_ACCOUNT_SET_NAME = "accountSetName";
    public static final String FIELD_SET_OF_BOOKS_CODE_NAME = "setOfBooksCodeName";
    public static final String FIELD_SOB_ENABLED_FLAG = "sobEnabledFlag";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";


    @Id
    @GeneratedValue
    @Where
    private Long setOfBooksId;

    /**
     * 帐套名称
     */
    @Length(max = 500)
    @MultiLanguageField
    @Where
    private String setOfBooksName;

    /**
     * 帐套代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String setOfBooksCode;

    /**
     * 期间代码
     */
    @NotEmpty
    @Length(max = 30)
    private String periodSetCode;

    /**
     * 本位币
     */
    @NotEmpty
    @Length(max = 10)
    private String functionalCurrencyCode;

    /**
     * 科目表ID
     * add by luhui
     */
    @JoinTable(name = "accSetJoin", joinMultiLanguageTable = true, target = GldAccountSet.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = GldAccountSet.FIELD_ACCOUNT_SET_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @NotNull
    private Long accountSetId;

    /**
     * 科目表名称
     * add by luhui
     */
    @JoinColumn(joinName = "accSetJoin", field = GldAccountSet.FIELD_ACCOUNT_SET_NAME)
    @Where
    @Length(max = 500)
    @Transient
    private String accountSetName;

    /**
     * 启用标志
     */
    @Length(max = 1)
    private String enabledFlag;

    @Transient
    private String setOfBooksCodeName;

    @Transient
    private Long magOrgId;

    @Transient
    private String sobEnabledFlag;

    @Transient
    private String defaultFlag;


    @Transient
	private String periodSetName;


	@Transient
	private String totalPeriodNum;

	@Transient
	private String periodSetNameDisplay;

	@Transient
	private String setOfBooksNameDisplay;
}
