package com.hand.hec.fnd.dto;

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
@Table(name = "fnd_tax_type_code_ref_ae")
public class FndTaxTypeCodeRefAe extends BaseDTO {

     public static final String FIELD_REF_ID = "refId";
     public static final String FIELD_TAX_TYPE_ID = "taxTypeId";
     public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long refId;

    /**
     * 税率定义ID
     */
     @NotNull
     @Where
     private Long taxTypeId;

    /**
     * 核算主体ID
     */
     @NotNull
     @JoinTable(name = "accEntityIdJoin",joinMultiLanguageTable = false,target = com.hand.hec.gld.dto.GldAccountingEntity.class,
             type = JoinType.LEFT,on = {@JoinOn(joinField = GldAccountingEntity.FIELD_ACC_ENTITY_ID)})
     private Long accEntityId;

     @Transient
     @JoinColumn(joinName = "accEntityIdJoin", field = GldAccountingEntity.FIELD_ACC_ENTITY_CODE)
     private String accEntityCode;

    @Transient
    @JoinColumn(joinName = "accEntityIdJoin", field = GldAccountingEntity.FIELD_ACC_ENTITY_NAME)
    private String accEntityName;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;



     }
