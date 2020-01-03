package com.hand.hec.csh.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
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
/**
 * <p>
 * 现金事务分类定义dto
 * </p>
 *
 * @author LJK 2019/02/19 12:02
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "csh_mo_transaction_class")
@MultiLanguage
public class CshMoTransactionClass extends BaseDTO {

     public static final String FIELD_MO_CSH_TRX_CLASS_ID = "moCshTrxClassId";
     public static final String FIELD_MAG_ORG_ID = "magOrgId";
     public static final String FIELD_MO_CSH_TRX_CLASS_CODE = "moCshTrxClassCode";
     public static final String FIELD_CSH_TRANSACTION_TYPE_CODE = "cshTransactionTypeCode";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_ENABLED_WRITE_OFF_FLAG = "enabledWriteOffFlag";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long moCshTrxClassId;

    /**
     * 管理组织ID
     */
     @NotNull
	 @Where
     private Long magOrgId;

    /**
     * 现金事物分类代码
     */
     @NotEmpty
     @Length(max = 30)
	 @Where
     private String moCshTrxClassCode;

    /**
     * 现金事物类型代码（PAYMENT，PREPAYMENT）
     */
     @NotEmpty
     @Length(max = 30)
	 @Where
     private String cshTransactionTypeCode;

    /**
     * 描述ID
     */
     @NotEmpty
     @Length(max = 500)
     @MultiLanguageField
	 @Where
     private String description;

    /**
     * 是否启用核销
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledWriteOffFlag;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
