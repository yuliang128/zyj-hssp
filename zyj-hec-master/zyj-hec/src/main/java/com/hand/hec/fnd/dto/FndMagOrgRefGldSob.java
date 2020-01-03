package com.hand.hec.fnd.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hec.bgt.dto.BgtEntity;
import com.hand.hec.gld.dto.GldSetOfBook;

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

/**
 * <p>
 * FndMagOrgRefGldSob
 * </p>
 *
 * @author yang.duan 2019/01/10 11:06
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "fnd_mag_org_ref_gld_sob")
public class FndMagOrgRefGldSob extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_SET_OF_BOOKS_ID = "setOfBooksId";
    public static final String FIELD_DEFAULT_FLAG = "defaultFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 管理组织ID
     */
    @NotNull
    @Where
    private Long magOrgId;

    /**
     * 账套ID
     */
    @NotNull
    @JoinTable(name = "sobJoin", joinMultiLanguageTable = false, target = GldSetOfBook.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = GldSetOfBook.FIELD_SET_OF_BOOKS_ID)})
    private Long setOfBooksId;

    /**
     * 账套code
     */
    @JoinColumn(joinName = "sobJoin", field = GldSetOfBook.FIELD_SET_OF_BOOKS_CODE)
    @Transient
    private String setOfBooksCode;

    /**
     * 账套名称
     */
    @JoinColumn(joinName = "sobJoin", field = GldSetOfBook.FIELD_SET_OF_BOOKS_NAME)
    @Transient
    private String setOfBooksName;

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

}
