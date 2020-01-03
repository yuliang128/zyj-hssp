package com.hand.hec.exp.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hap.fnd.dto.FndCompany;
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

/**
 * <p>
 * ExpMoReportTypeAsgnCom
 * </p>
 *
 * @author yang.duan 2019/01/10 14:44
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_mo_report_type_asgn_com")
public class ExpMoReportTypeAsgnCom extends BaseDTO {

    public static final String FIELD_ASSIGN_ID = "assignId";
    public static final String FIELD_MO_EXP_REPORT_TYPE_ID = "moExpReportTypeId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

     public static final String FIELD_COMPANY_CODE = "companyCode";
     public static final String FIELD_COMPANY_SHORT_NAME = "companyShortName";


    @Id
    @GeneratedValue
    private Long assignId;

    @Where
    private Long moExpReportTypeId;

    @JoinTable(name = "companyJoin", target = FndCompany.class, type = JoinType.LEFT, joinMultiLanguageTable = true,
                    on = {@JoinOn(joinField = FndCompany.FIELD_COMPANY_ID)})
    @Where
    private Long companyId;

    @Transient
    @JoinColumn(joinName = "companyJoin",field = FndCompany.FIELD_COMPANY_CODE)
    @Where
    private String companyCode;

    @Transient
    @JoinColumn(joinName = "companyJoin",field = FndCompany.FIELD_COMPANY_SHORT_NAME)
    @Where
    private String companyShortName;

    @Length(max = 1)
    private String enabledFlag;

}
