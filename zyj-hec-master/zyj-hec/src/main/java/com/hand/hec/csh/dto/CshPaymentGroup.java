package com.hand.hec.csh.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
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
import javax.validation.constraints.NotNull;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@MultiLanguage
@Table(name = "csh_payment_group")
public class CshPaymentGroup extends BaseDTO {

     public static final String FIELD_GROUP_ID = "groupId";
     public static final String FIELD_MAG_ORG_ID = "magOrgId";
     public static final String FIELD_GROUP_CODE = "groupCode";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";
     public static final String FIELD_DESCRIPTION = "description";

     @Id
     @GeneratedValue
     private Long groupId;

    /**
     * 管理组织ID
     */
     @NotNull
     private Long magOrgId;

    /**
     * 付款组代码
     */
     @NotEmpty
     @Length(max = 30)
     private String groupCode;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     @MultiLanguageField
     private String description;
     }


