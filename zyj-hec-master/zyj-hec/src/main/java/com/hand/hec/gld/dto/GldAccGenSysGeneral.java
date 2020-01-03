package com.hand.hec.gld.dto;

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

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "gld_acc_gen_sys_general")
public class GldAccGenSysGeneral extends BaseDTO {

    public static final String FIELD_GENERAL_ID = "generalId";
    public static final String FIELD_USAGE_CODE = "usageCode";
    public static final String FIELD_ACCOUNT_ID = "accountId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_SET_OF_BOOKS_ID = "setOfBooksId";


    @Id
    @GeneratedValue
    private Long generalId;

    /**
     * 用途代码
     */
    @NotEmpty
    @Length(max = 30)
    private String usageCode;

    /**
     * 科目ID
     */
    private Long accountId;

    @Transient
    private String accountCode;

    @Transient
    private String accountDescription;

    /**
     * 管理组织ID
     */
    private Long magOrgId;

    /**
     * 管理组织名称
     */
    @Transient
    private String magOrgName;

    /**
     * 账套ID
     */
    private Long setOfBooksId;

    /**
     * 账套名称
     */
    @Transient
    private String setOfBooksName;

    /**
     * 用途代码描述
     */
    @Transient
    private String usageCodeDesc;

    /**
     * 科目表Id
     */
    @Transient
    private Long accountSetId;
}
