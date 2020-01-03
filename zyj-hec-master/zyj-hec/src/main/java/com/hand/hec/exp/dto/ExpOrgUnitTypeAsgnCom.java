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
 *  ExpOrgUnitTypeAsgnCom
 * </p>
 *
 * @author yang.duan 2019/01/10 11:16
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "exp_org_unit_type_asgn_com")
public class ExpOrgUnitTypeAsgnCom extends BaseDTO {

     public static final String FIELD_ASSIGN_ID = "assignId";
     public static final String FIELD_UNIT_TYPE_ID = "unitTypeId";
     public static final String FIELD_COMPANY_ID = "companyId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";
	public static final String FIELD_COMPANY_CODE = "companyCode";
	public static final String FIELD_COMPANY_SHORT_NAME = "companyShortName";

     @Id
     @GeneratedValue
     private Long assignId;

    /**
     * 部门类型ID
     */
     @Where
     private Long unitTypeId;

    /**
     * 公司ID
     */
	@JoinTable(name = "fndCompanyJoin", joinMultiLanguageTable = false, target = FndCompany.class,
			type = JoinType.LEFT, on = {@JoinOn(joinField = FndCompany.FIELD_COMPANY_ID)})
     private Long companyId;

    /**
     * 启用标志
     */
     @Length(max = 1)
     private String enabledFlag;

	@JoinColumn(joinName = "fndCompanyJoin", field = FndCompany.FIELD_COMPANY_CODE)
	@Transient
	@Length(max = 500)
     private String companyCode;


	@JoinColumn(joinName = "fndCompanyJoin", field = FndCompany.FIELD_COMPANY_SHORT_NAME)
	@Transient
	@Length(max = 500)
     private String companyShortName;

}
