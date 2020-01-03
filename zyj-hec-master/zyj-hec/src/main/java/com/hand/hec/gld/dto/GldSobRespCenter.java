package com.hand.hec.gld.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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

import java.util.Date;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 账套级成本中心定义dto
 * </p>
 *
 * @author wuxiuxian 2019-01-10
 */
@Getter
@Setter
@ToString
@MultiLanguage
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "gld_sob_resp_center")
public class GldSobRespCenter extends BaseDTO {

    public static final String FIELD_RESPONSIBILITY_CENTER_ID = "responsibilityCenterId";
    public static final String FIELD_SET_OF_BOOKS_ID = "setOfBooksId";
    public static final String FIELD_RESPONSIBILITY_CENTER_CODE = "responsibilityCenterCode";
    public static final String FIELD_RESPONSIBILITY_CENTER_NAME = "responsibilityCenterName";
    public static final String FIELD_RESP_CENTER_TYPE_CODE = "respCenterTypeCode";
    public static final String FIELD_START_DATE_ACTIVE = "startDateActive";
    public static final String FIELD_END_DATE_ACTIVE = "endDateActive";
    public static final String FIELD_SUMMARY_FLAG = "summaryFlag";


    @Id
    @GeneratedValue
    @Where
    private Long responsibilityCenterId;

    /**
     * 帐套ID
     */
    @NotNull
    @Where
    private Long setOfBooksId;

    /**
     * 责任中心代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String responsibilityCenterCode;

    /**
     * 责任中心名称
     */
    @Length(max = 500)
    @Where
    @MultiLanguageField
    private String responsibilityCenterName;

    /**
     * 责任中心类型
     */
    @Length(max = 30)
    private String respCenterTypeCode;

    /**
     * 有效日期从
     */
    private Date startDateActive;

    /**
     * 有效日期至
     */
    private Date endDateActive;

    /**
     * 汇总标志
     */
    @NotEmpty
    @Length(max = 1)
    private String summaryFlag;

}
