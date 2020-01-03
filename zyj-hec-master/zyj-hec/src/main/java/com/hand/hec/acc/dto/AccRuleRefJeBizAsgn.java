package com.hand.hec.acc.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "acc_rule_ref_je_biz_asgn")
public class AccRuleRefJeBizAsgn extends BaseDTO {

     public static final String FIELD_ASSIGN_ID = "assignId";
     public static final String FIELD_REF_ID = "refId";
     public static final String FIELD_BUSINESS_RULE_ID = "businessRuleId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long assignId;

    /**
     * 关联ID
     */
     @NotNull
     private Long refId;

    /**
     * 权限规则ID
     */
     @NotNull
     private Long businessRuleId;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
