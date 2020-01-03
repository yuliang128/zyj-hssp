package com.hand.hec.csh.dto;

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
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 现金事物分类分配公司dto
 * </p>
 *
 * @author LJK 2019/02/19 12:03
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_mo_trx_cls_asgn_com")
public class CshMoTrxClsAsgnCom extends BaseDTO {

	public static final String FIELD_ASSIGN_ID = "assignId";
	public static final String FIELD_MO_CSH_TRX_CLASS_ID = "moCshTrxClassId";
	public static final String FIELD_COMPANY_ID = "companyId";
	public static final String FIELD_ENABLED_FLAG = "enabledFlag";
	public static final String COMPANY_CODE = "companyCode";
	public static final String COMPANY_SHORT_NAME = "companyShortName";

	@Id
	@GeneratedValue
	private Long assignId;

	/**
	 * 管理组织级现金事物分类定义
	 */
	@NotNull
	private Long moCshTrxClassId;

	/**
	 * 公司ID
	 */
	@NotNull
	private Long companyId;

	/**
	 * 启用标志
	 */
	@NotEmpty
	@Length(max = 1)
	private String enabledFlag;

	/**
	 * 公司代码
	 */
	@Transient
	private String companyCode;

	/**
	 * 公司简称
	 */
	@Transient
	private String companyShortName;

}
