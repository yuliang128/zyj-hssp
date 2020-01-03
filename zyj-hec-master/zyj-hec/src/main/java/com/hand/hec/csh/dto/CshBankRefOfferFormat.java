package com.hand.hec.csh.dto;

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
import java.util.Date;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
/**
 * <p>
 * 公司付款账户分配报盘格式dto
 * </p>
 *
 * @author xiuxian.xu 2019-03-15 9:36
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_bank_ref_offer_format")
public class CshBankRefOfferFormat extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_REF_TYPE = "refType";
    public static final String FIELD_REF_LINE_ID = "refLineId";
    public static final String FIELD_FORMAT_HDS_ID = "formatHdsId";
    public static final String FIELD_START_DATE = "startDate";
    public static final String FIELD_END_DATE = "endDate";


    @Id
    @GeneratedValue
    @Where
    private Long refId;

    /**
     * 关联类型（BANKS/银行，BRANCH/分行，ACCOUNT/账户）
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String refType;

    /**
     * 关联ID（银行ID/分行ID/账户ID）
     */
    @NotNull
    @Where
    private Long refLineId;

    /**
     * 报盘格式头ID
     */
    @NotNull
    @Where
    @JoinTable(name = "formatHdsJoin",type = JoinType.LEFT,target = CshOfferFormatHds.class,joinMultiLanguageTable = true,on = {@JoinOn(joinField = CshOfferFormatHds.FIELD_FORMAT_HDS_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE) })
    private Long formatHdsId;
    /**
     * 报盘格式代码
     */
    @Transient
    @JoinColumn(joinName = "formatHdsJoin",field = CshOfferFormatHds.FIELD_FORMAT_CODE)
    private String formatCode;
    /**
     * 报盘格式名称
     */
    @Transient
    @JoinColumn(joinName = "formatHdsJoin",field = CshOfferFormatHds.FIELD_DESCRIPTION)
    private String formatName;
    /**
     * 报盘格式类型
     */
    @Transient
    @JoinColumn(joinName = "formatHdsJoin",field = CshOfferFormatHds.FIELD_EXPORT_TYPE_CODE)
    private String exportTypeCode;


    /**
     * 有效期从
     */
    private Date startDate;

    /**
     * 有效期至
     */
    private Date endDate;

}
