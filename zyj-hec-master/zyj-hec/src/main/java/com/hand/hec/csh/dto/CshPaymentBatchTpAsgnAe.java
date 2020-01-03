package com.hand.hec.csh.dto;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hec.gld.dto.GldAccountingEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_payment_batch_tp_asgn_ae")
public class CshPaymentBatchTpAsgnAe extends BaseDTO {

     public static final String FIELD_ASSIGN_ID = "assignId";
     public static final String FIELD_TYPE_ID = "typeId";
     public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long assignId;

    /**
     * 付款批类型
     */
     @NotNull
     @Where
     private Long typeId;

    /**
     * 核算主体
     */
     @NotNull
     @JoinTable(name="accEntityJoin",target = GldAccountingEntity.class,joinMultiLanguageTable = true,type = JoinType.LEFT,on = {@JoinOn(joinField = GldAccountingEntity.FIELD_ACC_ENTITY_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
     @JoinTable(name="accEntityJoin1",target = GldAccountingEntity.class,type = JoinType.LEFT,on = {@JoinOn(joinField = GldAccountingEntity.FIELD_ACC_ENTITY_ID)})
     private Long accEntityId;

    /**
     * 核算主体编码
     */
     @JoinColumn(joinName = "accEntityJoin1",field = GldAccountingEntity.FIELD_ACC_ENTITY_CODE)
     @Transient
     private String accEntityCode;

    /**
     * 核算主体名称
     */
     @JoinColumn(joinName = "accEntityJoin",field = GldAccountingEntity.FIELD_ACC_ENTITY_NAME)
     @Transient
     private String accEntityName;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
