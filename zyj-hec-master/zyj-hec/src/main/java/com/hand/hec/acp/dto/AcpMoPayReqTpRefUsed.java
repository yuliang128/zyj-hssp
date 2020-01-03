package com.hand.hec.acp.dto;

import javax.persistence.*;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hec.csh.dto.CshMoPaymentUsed;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * <p>
 * 付款申请单类型定义关联付款用途实体类
 * </p>
 * 
 * @author guiyuting 2019/04/25 16:49
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "acp_mo_pay_req_tp_ref_used")
public class AcpMoPayReqTpRefUsed extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_MO_PAY_REQ_TYPE_ID = "moPayReqTypeId";
    public static final String FIELD_PAYMENT_USEDE_ID = "paymentUsedeId";
    public static final String FIELD_DEFAULT_FLAG = "defaultFlag";
    public static final String FIELD_PAYMENT_USEDE_CODE = "paymentUsedeCode";
    public static final String FIELD_PAYMENT_USEDE_NAME = "paymentUsedeName";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 管理组织级付款申请单类型ID
     */
    @NotNull
    @Where
    private Long moPayReqTypeId;

    /**
     * 管理组织级付款用途ID
     */
    @NotNull
    @JoinTable(name = "usedJoin", joinMultiLanguageTable = true, target = CshMoPaymentUsed.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = CshMoPaymentUsed.FIELD_PAYMENT_USEDE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @JoinTable(name = "usedJoin2", joinMultiLanguageTable = false, target = CshMoPaymentUsed.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = CshMoPaymentUsed.FIELD_PAYMENT_USEDE_ID)})
    private Long paymentUsedeId;

    @Transient
    @JoinColumn(joinName = "usedJoin2", field = CshMoPaymentUsed.FIELD_PAYMENT_USEDE_CODE)
    private String paymentUsedeCode;

    @Transient
    @JoinColumn(joinName = "usedJoin", field = CshMoPaymentUsed.FIELD_DESCRIPTION)
    private String paymentUsedeName;

    /**
     * 默认标志
     */
    @NotEmpty
    @Length(max = 1)
    private String defaultFlag;

}
