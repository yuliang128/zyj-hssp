package com.hand.hec.gld.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hec.fnd.dto.GldAccount;
/**
 * <p>
 * GldAccEntityRefAccount
 * </p>
 * 
 * @author yang.duan 2019/01/10 13:46
 * update by luhui 2019/01/17 13:58
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "gld_acc_entity_ref_account")
public class GldAccEntityRefAccount extends BaseDTO {

     public static final String FIELD_REF_ID = "refId";
     public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
     public static final String FIELD_SET_OF_BOOKS_ID = "setOfBooksId";
     public static final String FIELD_ACCOUNT_ID = "accountId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long refId;

    /**
     * 核算主体ID
     */
     @NotNull
     @Where
     private Long accEntityId;

    /**
     * 帐套ID
     */
     @NotNull
     @Where
     private Long setOfBooksId;

    /**
     * 科目ID
     */
    @JoinTable(name = "accJoin",joinMultiLanguageTable = false,target = GldAccount.class,
            type = JoinType.LEFT,on = {@JoinOn(joinField = GldAccount.FIELD_ACCOUNT_ID)})
     @NotNull
     private Long accountId;

    /**
     * 科目代码
     */
    @JoinColumn(joinName = "accJoin", field = GldAccount.FIELD_ACCOUNT_CODE)
    @Transient
    @OrderBy("ASC")
    @Where
    @NotEmpty
    @Length(max = 100)
    private String accountCode;

    /**
     * 描述
     */
    @JoinColumn(joinName = "accJoin", field = GldAccount.FIELD_DESCRIPTION)
    @Transient
    @Where
    @MultiLanguageField
    @Length(max = 1000)
    private String description;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     @Transient
     private String accountCodeName;

     }
