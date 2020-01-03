package com.hand.hec.gld.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * <p>
 * 核算主体级成本中心
 * </p>
 *
 * @author rui.shi@hand-china.com 2019/01/08 13:50
 */

@ExtensionAttribute(disable = true)
@Table(name = "gld_responsibility_center")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@MultiLanguage
public class GldResponsibilityCenter extends BaseDTO {

    public static final String FIELD_RESPONSIBILITY_CENTER_ID = "responsibilityCenterId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_RESPONSIBILITY_CENTER_CODE = "responsibilityCenterCode";
    public static final String FIELD_RESPONSIBILITY_CENTER_NAME = "responsibilityCenterName";
    public static final String FIELD_RESP_CENTER_TYPE_CODE = "respCenterTypeCode";
    public static final String FIELD_START_DATE_ACTIVE = "startDateActive";
    public static final String FIELD_END_DATE_ACTIVE = "endDateActive";
    public static final String FIELD_SUMMARY_FLAG = "summaryFlag";
    public static final String FIELD_PARENT_RESP_CENTER_ID = "parentRespCenterId";
	public static final String FIELD_SUMMARY_TYPE = "summaryType";


    @Id
    @GeneratedValue
    private Long responsibilityCenterId;


    /**
     * 核算主体ID
     */
    // @NotNull
	@Where
    private Long accEntityId;
    /**
     * 核算主体名称
     */
	@Transient
    private String accEntityCode;
    /**
     * 核算主体代码
     */
    @Transient
	private String accEntityName;


    @NotEmpty
    @Length(max = 30)
    @Where
    private String responsibilityCenterCode;

    /**
     * 责任中心名称
     */
    @Length(max = 500)
    @MultiLanguageField
    @Where
    private String responsibilityCenterName;

    /**
     * 责任中心类型
     */
    @Length(max = 30)
    private String respCenterTypeCode;

    /**
     * 有效日期从
     */
    private Date startDateActive;

    /**
     * 有效日期至
     */
    private Date endDateActive;

    /**
     * 汇总标志
     */
    @NotEmpty
    @Length(max = 1)
    private String summaryFlag;

    /**
     * 上级责任中心
     */
    @Where
    private Long parentRespCenterId;


    @Transient
    private String summaryType;

	public GldResponsibilityCenter(Long accEntityId, String responsibilityCenterCode, String responsibilityCenterName, String respCenterTypeCode, Date startDateActive, Date endDateActive, String summaryFlag, Long parentRespCenterId) {
		this.accEntityId = accEntityId;
		this.responsibilityCenterCode = responsibilityCenterCode;
		this.responsibilityCenterName = responsibilityCenterName;
		this.respCenterTypeCode = respCenterTypeCode;
		this.startDateActive = startDateActive;
		this.endDateActive = endDateActive;
		this.summaryFlag = summaryFlag;
		this.parentRespCenterId = parentRespCenterId;
	}
}
