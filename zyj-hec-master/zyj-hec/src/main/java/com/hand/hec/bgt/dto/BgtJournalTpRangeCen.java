package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 预算日记账类型指定预算中心范围
 * </p>
 * 
 * @author mouse 2019/01/07 16:34
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_journal_tp_range_cen")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtJournalTpRangeCen extends BaseDTO {

    public static final String FIELD_RANGE_CEN_ID = "rangeCenId";
    public static final String FIELD_RANGE_ETS_ID = "rangeEtsId";
    public static final String FIELD_CENTER_ID = "centerId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_CENTER_CODE = "centerCode";
    public static final String FIELD_CENTER_NAME = "centerName";


    @Id
    @GeneratedValue
    private Long rangeCenId;

    /**
     * 预算日记账类型-指定范围预算实体ID
     */
    @NotNull
    @Where
    private Long rangeEtsId;

    /**
     * 预算中心ID
     */
    @NotNull
    @JoinTable(name = "centerJoin", joinMultiLanguageTable = true, target = BgtCenter.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = BgtCenter.FIELD_CENTER_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @JoinTable(name = "centerJoin2", joinMultiLanguageTable = false, target = BgtCenter.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = BgtCenter.FIELD_CENTER_ID)})
    private Long centerId;

    @Transient
    @JoinColumn(joinName = "centerJoin2", field = BgtCenter.FIELD_CENTER_CODE)
    private String centerCode;

    @Transient
    @JoinColumn(joinName = "centerJoin", field = BgtCenter.FIELD_DESCRIPTION)
    private String centerName;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;


}
