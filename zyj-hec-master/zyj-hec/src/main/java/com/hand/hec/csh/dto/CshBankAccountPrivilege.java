package com.hand.hec.csh.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_bank_account_privilege")
public class CshBankAccountPrivilege extends BaseDTO {

    public static final String FIELD_PRIVILEGE_ID = "privilegeId";
    public static final String FIELD_BANK_ACCOUNT_ID = "bankAccountId";
    public static final String FIELD_GROUP_ID = "groupId";
    public static final String FIELD_START_DATE_ACTIVE = "startDateActive";
    public static final String FIELD_END_DATE_ACTIVE = "endDateActive";

    public static final String FIELD_ACC_ENTITY_ID= "accEntityId";
    public static final String FIELD_ACC_ENTITY_NAME= "accEntityName";
    public static final String FIELD_BANK_CODE = "bankCode";
    public static final String FIELD_BANK_NAME = "bankName";
    public static final String FIELD_BANK_ACCOUNT_TYPE = "bankAccountType";
    public static final String FIELD_BANK_ACCOUNT_NAME = "bankAccountName";
    public static final String FIELD_BANK_ACCOUNT_NUM = "bankAccountNum";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_CURRENCY_NAME = "currencyName";
    public static final String FIELD_BANK_BRANCH_ID = "bankBranchId";
    public static final String FIELD_RESPONSIBILITY_CENTER_NAME = "responsibilityCenterName";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

    public static final String FIELD_GROUP_CODE = "groupCode";
    public static final String FIELD_GROUP_NAME = "groupName";
    public static final String FIELD_MAG_ORG_NAME = "magOrgName";


     @Id
     @GeneratedValue
     private BigDecimal privilegeId;

    /**
     * 银行账户ID
     */
     private BigDecimal bankAccountId;

    /**
     * 付款工作组ID
     */

     private BigDecimal groupId;

    /**
     * 起始日期
     */
    @JsonFormat(pattern="yyyy-MM-dd")
     private Date startDateActive;

    /**
     * 结束日期
     */
    @JsonFormat(pattern="yyyy-MM-dd")
     private Date endDateActive;

     @Transient
     private BigDecimal accEntityId;

     @Transient
     private String accEntityName;

     @Transient
     private String bankCode;

     @Transient
     private String bankName;

     private String bankAccountType;

     private String bankAccountName;

     private String bankAccountNum;

     private String currencyCode;

     private BigDecimal bankBranchId;

     private String responsibilityCenterName;

     @Transient
     private String groupCode;
     @Transient
     private String groupName;
     @Transient
     private String magOrgName;

     private String currencyName;

     private String enabledFlag;

     @Transient
     private String ajustFlag;

}
