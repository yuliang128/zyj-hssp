package com.hand.hec.csh.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hec.fnd.dto.GldCoaStructure;
import com.hand.hec.gld.dto.GldAccountingEntity;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_payment_trx_rule_asgn_ae")
public class CshPaymentTrxRuleAsgnAe extends BaseDTO {

    public static final String FIELD_ASSIGN_ID = "assignId";
    public static final String FIELD_RULE_ID = "ruleId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long assignId;

    /**
     * 付款事务生成规则ID
     */
    @NotNull
    @Where
    private Long ruleId;

    /**
     * 核算主体ID
     */
    @JoinTable(name = "accJoin", joinMultiLanguageTable = true, target = GldAccountingEntity.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = GldAccountingEntity.FIELD_ACC_ENTITY_ID)})
    @NotNull
    private Long accEntityId;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    @JoinColumn(joinName = "accJoin", field = GldAccountingEntity.FIELD_ACC_ENTITY_CODE)
    @Transient
    private String accEntityCode;

    @JoinColumn(joinName = "accJoin", field = GldAccountingEntity.FIELD_ACC_ENTITY_NAME)
    @Transient
    private String accEntityName;

    @Transient
    private Long magOrgId;
}
