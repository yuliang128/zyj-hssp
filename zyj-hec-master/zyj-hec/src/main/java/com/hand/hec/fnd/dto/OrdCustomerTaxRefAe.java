package com.hand.hec.fnd.dto;

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
import java.math.BigDecimal;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 客户税务信息分配核算主体表
 * </p>
 * 
 * @author guiyuting 2019/02/22 16:31
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "ord_customer_tax_ref_ae")
public class OrdCustomerTaxRefAe extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_TAX_ID = "taxId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 客户税务信息ID
     */
    @Where
    @NotNull
    private Long taxId;

    /**
     * 核算主体ID
     */
    @JoinTable(name = "accEntityJoin", joinMultiLanguageTable = true, target = GldAccountingEntity.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = GldAccountingEntity.FIELD_ACC_ENTITY_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @JoinTable(name = "accEntityJoin2", joinMultiLanguageTable = false, target = GldAccountingEntity.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = GldAccountingEntity.FIELD_ACC_ENTITY_ID)})
    private Long accEntityId;

    @Transient
    @com.hand.hap.mybatis.common.query.JoinColumn(joinName = "accEntityJoin2",
                    field = GldAccountingEntity.FIELD_ACC_ENTITY_CODE)
    private String accEntityCode;

    @Transient
    @JoinColumn(joinName = "accEntityJoin", field = GldAccountingEntity.FIELD_ACC_ENTITY_NAME)
    private String accEntityName;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
