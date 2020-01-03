package com.hand.hec.fnd.dto;

import javax.persistence.*;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.persistence.criteria.JoinType;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * <p>
 * 系统级客户主数据表
 * </p>
 * 
 * @author guiyuting 2019/02/20 15:43
 */

@Getter
@Setter
@Builder
@ToString
@MultiLanguage
@NoArgsConstructor
@AllArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "ord_system_customer")
public class OrdSystemCustomer extends BaseDTO {

    public static final String FIELD_CUSTOMER_ID = "customerId";
    public static final String FIELD_CUSTOMER_CODE = "customerCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_CUSTOMER_TYPE_ID = "customerTypeId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_CUSTOMER_TYPE_CODE = "customerTypeCode";
    public static final String FIELD_CUSTOMER_TYPE_NAME = "customerTypeName";


    @Id
    @GeneratedValue
    private Long customerId;

    /**
     * 客户编码
     */
    @Where
    @NotEmpty
    @Length(max = 30)
    private String customerCode;

    /**
     * 客户描述
     */
    @Where
    @MultiLanguageField
    @NotEmpty
    @Length(max = 500)
    private String description;

    /**
     * 客户类型ID
     */
    @Where
    @JoinTable(name = "customerTypeJoin", joinMultiLanguageTable = true, target = FndCustomerType.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = FndCustomerType.FIELD_CUSTOMER_TYPE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @JoinTable(name = "customerTypeJoin2", joinMultiLanguageTable = false, target = FndCustomerType.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = FndCustomerType.FIELD_CUSTOMER_TYPE_ID)})
    private Long customerTypeId;


    @JoinColumn(joinName = "customerTypeJoin", field = FndCustomerType.FIELD_DESCRIPTION)
    @Transient
    private String customerTypeName;

    @JoinColumn(joinName = "customerTypeJoin2", field = FndCustomerType.FIELD_CUSTOMER_TYPE_CODE)
    @Transient
    private String customerTypeCode;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    @Transient
    private Long accEntityId;

    @Transient
    private List<OrdSystemCustomerRefAe> accEntityAssign;

}
