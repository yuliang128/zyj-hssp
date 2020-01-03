package com.hand.hec.fnd.dto;
/**
 * <p>
 * 客户类型关联核算主体dto
 * </p>
 *
 * @author xiuxian.wu 2019/02/14 11:07
 */

import javax.persistence.*;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hec.gld.dto.GldAccountingEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Table;
import javax.persistence.criteria.JoinType;

import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "ord_customer_type_ref_ae")
public class FndCustomerTypeRefAe extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_CUSTOMER_TYPE_ID = "customerTypeId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    @Where
    private Long refId;

    /**
     * 客户类型ID
     */
    @Where
    private Long customerTypeId;

    /**
     * 核算主体ID
     */
    @Where
    @JoinTable(name = "accEntityJoin", joinMultiLanguageTable = true, target = GldAccountingEntity.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = GldAccountingEntity.FIELD_ACC_ENTITY_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long accEntityId;

    /**
     * 核算主体名称
     */
    @Transient
    @JoinColumn(joinName = "accEntityJoin", field = GldAccountingEntity.FIELD_ACC_ENTITY_NAME)
    private String accEntityName;

    /**
     * 核算主体代码
     */
    @JoinColumn(joinName = "accEntityJoin", field = GldAccountingEntity.FIELD_ACC_ENTITY_CODE)
    @Transient
    private String accEntityCode;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String enabledFlag;
    /**
     * 核算实体代码从
     */
    @Transient
    private String accEntityCodeFrom;
    /**
     * 核算实体代码到
     */
    @Transient
    private String accEntityCodeTo;

}
