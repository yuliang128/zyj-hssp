package com.hand.hec.fnd.dto;
/**
 * <p>
 * 客户类型定义dto
 * </p>
 *
 * @author xiuxian.wu 2019/02/14 11:07
 */

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.common.query.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "ord_customer_type")
@MultiLanguage
public class FndCustomerType extends BaseDTO {

    public static final String FIELD_CUSTOMER_TYPE_ID = "customerTypeId";
    public static final String FIELD_CUSTOMER_TYPE_CODE = "customerTypeCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_CUSTOMER_TYPE_NAME = "customerTypeName";


    @Id
    @GeneratedValue
    @Where
    private Long customerTypeId;

    /**
     * 客户代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String customerTypeCode;

    /**
     * 客户名称
     */
    @Length(max = 500)
    @Where
    @MultiLanguageField
    private String description;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String enabledFlag;

    @Transient
    private String customerTypeName;

}
