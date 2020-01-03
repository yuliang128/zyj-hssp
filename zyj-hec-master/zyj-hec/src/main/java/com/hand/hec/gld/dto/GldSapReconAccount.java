package com.hand.hec.gld.dto;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hec.fnd.dto.GldAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "gld_sap_recon_account")
public class GldSapReconAccount extends BaseDTO {

     public static final String FIELD_SC_GLD_SAP_RECON_ACCOUNTS_ID = "scGldSapReconAccountsId";
     public static final String FIELD_SET_OF_BOOKS_ID = "setOfBooksId";
     public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
     public static final String FIELD_ACCOUNT_ID = "accountId";
     public static final String FIELD_COMMAND_FLAG = "commandFlag";
     public static final String FIELD_SGL_FLAG = "sglFlag";
     public static final String FIELD_SGL = "sgl";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";
     public static final String FIELD_ACCOUNT_SET_ID = "accountSetId";


     @Id
     @GeneratedValue
     private Long scGldSapReconAccountsId;

    /**
     * 账套
     */
    @Where
     private Long setOfBooksId;

    /**
     * 核算公司
     */
    @Where
     private Long accEntityId;

    /**
     * 科目
     */
    @JoinTable(name = "accountJoin",type = JoinType.LEFT,joinMultiLanguageTable = true,target = GldAccount.class,on = {@JoinOn(joinField = GldAccount.FIELD_ACCOUNT_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @JoinTable(name = "accountJoin1",type = JoinType.LEFT,target = GldAccount.class,on = {@JoinOn(joinField = GldAccount.FIELD_ACCOUNT_ID)})
    private Long accountId;

    /**
     * 科目代码
     */
    @JoinColumn(joinName = "accountJoin1",field = GldAccount.FIELD_ACCOUNT_CODE)
    @Transient
    private String accountCode;

    /**
     * 科目描述
     */
    @JoinColumn(joinName = "accountJoin",field = GldAccount.FIELD_DESCRIPTION)
    @Transient
    private String accountName;

    /**
     * SAP统驭标识
     */
     @Length(max = 100)
     private String commandFlag;

    /**
     * 是否特别总账
     */
     @Length(max = 100)
     private String sglFlag;

    /**
     * 特别总账标识
     */
     @Length(max = 100)
     private String sgl;

    /**
     * 启用
     */
     @Length(max = 10)
     private String enabledFlag;

    /**
     * 科目表
     */
    @Where
     private Long accountSetId;

     }
