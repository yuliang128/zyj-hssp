package com.hand.hec.csh.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@Table(name = "csh_payment_gps_privilege")
public class CshPaymentGpsPrivilege extends BaseDTO {

     public static final String FIELD_PRIVILEGE_ID = "privilegeId";
     public static final String FIELD_ASSIGN_AE_ID = "assignAeId";
     public static final String FIELD_RULE_ID = "ruleId";

     public static final String FIELD_RULE_CODE = "ruleCode";
     public static final String FIELD_RULE_NAME = "ruleName";


     @Id
     @GeneratedValue
     private Long privilegeId;

    /**
     * 付款工作组分配支付主体ID
     */
     @NotNull
     private Long assignAeId;

    /**
     * 支付权限规则定义ID
     */
     @NotNull
     private Long ruleId;

     @Transient
     private String ruleCode;

     @Transient
     private String ruleName;

     }
