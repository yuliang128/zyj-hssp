package com.hand.hec.gld.dto;

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
@Table(name = "gld_sap_posting_key")
public class GldSapPostingKey extends BaseDTO {

     public static final String FIELD_POSTING_KEY_ID = "postingKeyId";
     public static final String FIELD_ACCOUNT_SET_ID = "accountSetId";
     public static final String FIELD_RECONCILIATION_TYPE = "reconciliationType";
     public static final String FIELD_SPECIAL_GL_TYPE = "specialGlType";
     public static final String FIELD_PAYEE_CATEGORY = "payeeCategory";
     public static final String FIELD_DR_CR_TYPE = "drCrType";
     public static final String FIELD_POSTING_KEY = "postingKey";
     public static final String FIELD_SET_OF_BOOKS_ID = "setOfBooksId";
     public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
     public static final String FIELD_RE_POSTING_KEY = "rePostingKey";


     @Id
     @GeneratedValue
     private Long postingKeyId;

    /**
     * 科目表
     */
     @NotNull
     private Long accountSetId;

    /**
     * 统驭类型[是/否]
     */
     @Length(max = 30)
     private String reconciliationType;

    /**
     * 特别总账类型[是/否]
     */
     @Length(max = 30)
     private String specialGlType;

    /**
     * 收款方类型
     */
     @Length(max = 30)
     private String payeeCategory;

    /**
     * 借贷方[DR 借方/CR 贷方]
     */
     @NotEmpty
     @Length(max = 30)
     private String drCrType;

    /**
     * 记账码
     */
     @NotEmpty
     @Length(max = 30)
     private String postingKey;

    /**
     * 账套
     */
     private Long setOfBooksId;

    /**
     * 核算公司
     */
     private Long accEntityId;

    /**
     * 对方记账码
     */
     @Length(max = 30)
     private String rePostingKey;

     }
