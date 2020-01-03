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
 * 预算日记账类型指定预算实体范围
 * </p>
 * 
 * @author mouse 2019/01/07 16:35
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_journal_tp_range_et")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtJournalTpRangeEt extends BaseDTO {

    public static final String FIELD_RANGE_ETS_ID = "rangeEtsId";
    public static final String FIELD_RANGE_ID = "rangeId";
    public static final String FIELD_ENTITY_ID = "entityId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_ENTITY_CODE = "entityCode";
    public static final String FIELD_ENTITY_NAME = "entityName";


    /**
     * 主键，供其他表外键使用
     */
    @Id
    @GeneratedValue
    private Long rangeEtsId;

    /**
     * 预算日记账类型-指定范围ID
     */
    @Where
    @NotNull
    private Long rangeId;

    /**
     * 预算实体ID
     */
    @NotNull
    @JoinTable(name = "entityJoin", joinMultiLanguageTable = true, target = BgtEntity.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = BgtEntity.FIELD_ENTITY_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @JoinTable(name = "entityJoin2", joinMultiLanguageTable = false, target = BgtEntity.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = BgtEntity.FIELD_ENTITY_ID)})
    private Long entityId;

    @Transient
    @JoinColumn(joinName = "entityJoin2", field = BgtEntity.FIELD_ENTITY_CODE)
    private String entityCode;

    @Transient
    @JoinColumn(joinName = "entityJoin", field = BgtEntity.FIELD_DESCRIPTION)
    private String entityName;


    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;


}
