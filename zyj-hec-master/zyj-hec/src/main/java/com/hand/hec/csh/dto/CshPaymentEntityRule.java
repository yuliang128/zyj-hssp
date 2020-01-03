package com.hand.hec.csh.dto;

import com.hand.hap.fnd.dto.FndCompany;
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
@ExtensionAttribute(disable = true)
@Table(name = "csh_payment_entity_rule")
public class CshPaymentEntityRule extends BaseDTO {

    public static final String FIELD_ENTITY_RULE_ID = "entityRuleId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_DOCUMENT_CATEGORY = "documentCategory";
    public static final String FIELD_PRIORITY = "priority";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_DOCUMENT_TYPE_ID = "documentTypeId";
    public static final String FIELD_PAYMENT_METHOD_ID = "paymentMethodId";
    public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
    public static final String FIELD_ACC_ENTITY_NAME = "accEntityName";
    public static final String FIELD_COMPANY_NAME = "companyName";
    public static final String FIELD_PAYMENT_METHOD_DESC = "paymentMethodDesc";


    /**
     * 三种业务类型: 费用报销单/借款申请单/付款申请单
     */
    public static final String DOCUMENT_CATEGORY_EXP_REPORT = "EXP_REPORT";
    public static final String DOCUMENT_CATEGORY_PAYMENT_REQUISITION = "PAYMENT_REQUISITION";
    public static final String DOCUMENT_CATEGORY_ACP_REQUISITION = "ACP_REQUISITION";



    @Id
    @GeneratedValue
    private Long entityRuleId;

    /**
     * 管理组织ID
     */
    @NotNull
	@Where
    private Long magOrgId;

    /**
     * 业务类型（SYSCODE：CSH_PAYMENT_DOCUMENT_CATEGORY）
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String documentCategory;

    /**
     * 优先级
     */
    @NotNull
    private Long priority;

    /**
     * 核算主体ID
     */
    @NotNull
    @JoinTable(name = "accEntityJoin", joinMultiLanguageTable = true, target = GldAccountingEntity.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = GldAccountingEntity.FIELD_ACC_ENTITY_ID)})
    private Long accEntityId;

    /**
     * 管理公司ID
     */
    @JoinTable(name = "companyJoin", joinMultiLanguageTable = true, target = FndCompany.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = FndCompany.FIELD_COMPANY_ID)})
    private Long companyId;

    /**
     * 单据类型ID（费用报销单、借款申请单、付款申请单）
     */
    private Long documentTypeId;

    /**
     * 付款方式ID
     */
    @JoinTable(name = "cshPaymentMethodJoin", joinMultiLanguageTable = true, target = CshPaymentMethod.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = CshPaymentMethod.FIELD_PAYMENT_METHOD_ID)})
    private Long paymentMethodId;

    /**
     * 收款方类别
     */
    @Length(max = 30)
    private String payeeCategory;


    @Transient
    @Where
    @JoinColumn(joinName = "accEntityJoin", field = GldAccountingEntity.FIELD_ACC_ENTITY_NAME)
    private String accEntityName;

    @Transient
    @JoinColumn(joinName = "companyJoin", field = FndCompany.FIELD_COMPANY_SHORT_NAME)
    private String companyName;

    @Transient
    private String documentTypeDesc;

    @Transient
    @JoinColumn(joinName = "cshPaymentMethodJoin", field = CshPaymentMethod.FIELD_DESCRIPTION)
    private String paymentMethodDesc;
}
