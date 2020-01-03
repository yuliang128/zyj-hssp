package com.hand.hec.vat.dto;

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
import java.math.BigDecimal;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "vat_tax_transfer_ratio")
public class VatTaxTransferRatio extends BaseDTO {

     public static final String FIELD_RATIO_ID = "ratioId";
     public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
     public static final String FIELD_PERIOD_NAME = "periodName";
     public static final String FIELD_VAT_TRANSFER_TYPE = "vatTransferType";
     public static final String FIELD_TRANSFER_RATIO = "transferRatio";


     @Id
     @GeneratedValue
     private Long ratioId;

    /**
     * 核算主体
     */
     @NotNull
     @Where
     @JoinTable(name = "accEntityJoin",target = GldAccountingEntity.class,type = JoinType.LEFT,joinMultiLanguageTable = true,on = {@JoinOn(joinField = GldAccountingEntity.FIELD_ACC_ENTITY_ID),
             @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
     private Long accEntityId;

     @Transient
     @JoinColumn(joinName = "accEntityJoin",field = GldAccountingEntity.FIELD_ACC_ENTITY_NAME)
     private String accEntityName;

    /**
     * 期间
     */
     @NotEmpty
     @Length(max = 30)
     @Where
     private String periodName;

    /**
     * 转出方式
     */
     @NotEmpty
     @Length(max = 30)
     private String vatTransferType;

    /**
     * 转出比例
     */
    @Where
     private BigDecimal transferRatio;

     }
