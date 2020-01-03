package com.hand.hec.csh.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
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
import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_payment_batch_tp_rule")
public class CshPaymentBatchTpRule extends BaseDTO {

     public static final String FIELD_TYPE_RULE_ID = "typeRuleId";
     public static final String FIELD_TYPE_ID = "typeId";
     public static final String FIELD_DR_CR_CODE = "drCrCode";
     public static final String FIELD_PARAMETER_TYPE = "parameterType";
     public static final String FIELD_PARAMETER_VALUE = "parameterValue";


     @Id
     @GeneratedValue
     private Long typeRuleId;

    /**
     * 付款批类型定义
     */
     @NotNull
     private Long typeId;

    /**
     * 借/贷方（SYSCODE：DR_CR_FLAG）
     */
     @NotEmpty
     @Length(max = 30)
     private String drCrCode;

    /**
     * 合并参数类型（SYSCODE：CSH_PAYMENT_PARAMETER_TYPE）
     */
     @NotEmpty
     @Length(max = 30)
     private String parameterType;

    /**
     * 参数值
     */
     @NotEmpty
     @Length(max = 30)
     private String parameterValue;

    /**
     * 借/贷方描述
     */
    @Transient
    private String drCrCodeDisplay;

    /**
     * 合并参数类型描述
     */
    @Transient
    private String parameterTypeDisplay;

    /**
     * 参数值描述
     */
    @Transient
    private String parameterValueDisplay;

     }
