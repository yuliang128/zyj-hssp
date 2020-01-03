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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * GldAccEntityRefSob
 * </p>
 * 
 * @author yang.duan 2019/01/10 13:44
 * update by luhui 2019/01/16 15:58
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "gld_acc_entity_ref_sob")
public class GldAccEntityRefSob extends BaseDTO {

     public static final String FIELD_REF_ID = "refId";
     public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
     public static final String FIELD_SET_OF_BOOKS_ID = "setOfBooksId";
     public static final String FIELD_DEFAULT_FLAG = "defaultFlag";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long refId;

     /**
      * 核算主体ID
      */
     @JoinTable(name = "accEntityJoin",joinMultiLanguageTable = false,target = GldAccountingEntity.class,
            type = JoinType.LEFT,on = {@JoinOn(joinField = GldAccountingEntity.FIELD_ACC_ENTITY_ID)})
     @NotNull
     @Where
     private Long accEntityId;

    /**
     * 帐套ID
     */
     @JoinTable(name = "sobJoin",joinMultiLanguageTable = false,target = GldSetOfBook.class,
            type = JoinType.LEFT,on = {@JoinOn(joinField = GldSetOfBook.FIELD_SET_OF_BOOKS_ID)})
     @NotNull
     @Where
     private Long setOfBooksId;

    /**
     * 默认标志
     */
     @NotEmpty
     @Length(max = 1)
     private String defaultFlag;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

    /**
     * 帐套名称
     */
    @JoinColumn(joinName = "sobJoin", field = GldSetOfBook.FIELD_SET_OF_BOOKS_NAME)
    @Transient
    private String setOfBooksName;

    /**
     * 帐套代码
     */
    @JoinColumn(joinName = "sobJoin", field = GldSetOfBook.FIELD_SET_OF_BOOKS_CODE)
    @Transient
    private String setOfBooksCode;

    /**
     * 科目表ID
     */
    @JoinColumn(joinName = "sobJoin", field = GldSetOfBook.FIELD_ACCOUNT_SET_ID)
    @Transient
    private Long accountSetId;

    /**
     * 科目表名称
     * add by luhui
     */
    @Transient
    private String accountSetName;

     }
