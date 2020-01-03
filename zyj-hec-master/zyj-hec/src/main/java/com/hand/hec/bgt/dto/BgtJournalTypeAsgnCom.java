package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 预算日记账类型分配公司
 * </p>
 * 
 * @author mouse 2019/01/07 16:36
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_journal_type_asgn_com")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtJournalTypeAsgnCom extends BaseDTO {

    public static final String FIELD_ASSIGN_COM_ID = "assignComId";
    public static final String FIELD_ASSIGN_MO_ID = "assignMoId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_COMPANY_CODE = "companyCode";
    public static final String FIELD_COMPANY_SHORT_NAME = "companyShortName";


    @Id
    @GeneratedValue
    private Long assignComId;

    /**
     * 预算日记账类型分配管理组织ID
     */
    @Where
    @NotNull
    private Long assignMoId;

    /**
     * 管理公司ID
     */
    @NotNull
    @JoinTable(name = "companyJoin", joinMultiLanguageTable = true, target = FndCompany.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = FndCompany.FIELD_COMPANY_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @JoinTable(name = "companyJoin2", joinMultiLanguageTable = false, target = FndCompany.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = FndCompany.FIELD_COMPANY_ID)})
    private Long companyId;

    @Transient
    @JoinColumn(joinName = "companyJoin2", field = FndCompany.FIELD_COMPANY_CODE)
    private String companyCode;

    @Transient
    @JoinColumn(joinName = "companyJoin", field = FndCompany.FIELD_COMPANY_SHORT_NAME)
    private String companyShortName;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;


}
