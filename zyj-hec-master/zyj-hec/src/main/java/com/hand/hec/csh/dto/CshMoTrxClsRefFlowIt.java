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
 * 现金事物关联现金流量项dto
 * </p>
 *
 * @author LJK 2019/02/19 12:03
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_mo_trx_cls_ref_flow_it")
public class CshMoTrxClsRefFlowIt extends BaseDTO {

	public static final String FIELD_REF_ID = "refId";
	public static final String FIELD_MO_CSH_TRX_CLASS_ID = "moCshTrxClassId";
	public static final String FIELD_CASH_FLOW_ITEM_ID = "cashFlowItemId";
	public static final String FIELD_DEFAULT_FLAG = "defaultFlag";
	public static final String FIELD_ENABLED_FLAG = "enabledFlag";
	public static final String FIELD_MO_CSH_TRX_CLASS_CODE = "moCshTrxClassCode";
	public static final String FIELD_SET_OF_BOOKS_NAME = "setOfBooksName";
	public static final String FIELD_CASH_FLOW_ITEM_DESC = "cashFlowItemDesc";


	@Id
	@GeneratedValue
	private Long refId;

	/**
	 * 管理组织级现金事物分类ID
	 */
	@NotNull
	private Long moCshTrxClassId;

	/**
	 * 现金流量项ID
	 */
	@NotNull
	private Long cashFlowItemId;

	/**
	 * 默认现金流量项标志
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
	 * 现金事务分类分类代码
	 */
	@Transient
	private String moCshTrxClassCode;

	/**
	 * 账套名称
	 */
	@Transient
	private String setOfBooksName;

	/**
	 * 现金流量项
	 */
	@Transient
	private String cashFlowItemDesc;
}
