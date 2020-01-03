package com.hand.hec.fnd.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
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
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "fnd_tax_type_code")
@MultiLanguage
public class FndTaxTypeCode extends BaseDTO {

    public static final String FIELD_TAX_TYPE_ID = "taxTypeId";
    public static final String FIELD_COUNTRY_CODE = "countryCode";
    public static final String FIELD_TAX_TYPE_CODE = "taxTypeCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_TAX_CATEGORY = "taxCategory";
    public static final String FIELD_TAX_TYPE_RATE = "taxTypeRate";
    public static final String FIELD_AUTO_CALCULATION_FLAG = "autoCalculationFlag";
    public static final String FIELD_WITHHOLDING_FLAG = "withholdingFlag";
    public static final String FIELD_SALE_TAX_FLAG = "saleTaxFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long taxTypeId;

    /**
     * 国家代码
     */


    @NotEmpty
    @Length(max = 30)
    @JoinTable(name = "countryCodeJoin",joinMultiLanguageTable = false,target = com.hand.hec.fnd.dto.FndCountryCode.class,
            type = JoinType.LEFT,on = {@JoinOn(joinField = FndCountryCode.FIELD_COUNTRY_CODE)})
    private String countryCode;

    @Transient
    @JoinColumn(joinName = "countryCodeJoin", field = FndCountryCode.FIELD_DESCRIPTION )
    private String countryName;

    /**
     * 税种代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String taxTypeCode;

    /**
     * 税种名称
     */
    @Length(max = 500)
    @MultiLanguageField
    @Where
    private String description;

    /**
     * 税率类别（SYSCODE：FND_TAX_CATEGORY  价内/价外税）
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String taxCategory;

    /**
     * 税率
     */
    @Where
    private BigDecimal taxTypeRate;

    /**
     * 自动计算标志
     */
    @NotEmpty
    @Length(max = 1)
    private String autoCalculationFlag;

    /**
     * 代扣缴标志
     */
    @NotEmpty
    @Length(max = 1)
    private String withholdingFlag;

    /**
     * 销项税率标志
     */
    @NotEmpty
    @Length(max = 1)
    private String saleTaxFlag;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
