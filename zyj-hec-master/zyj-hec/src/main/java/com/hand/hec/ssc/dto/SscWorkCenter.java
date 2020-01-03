package com.hand.hec.ssc.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "ssc_work_center")
@MultiLanguage
public class SscWorkCenter extends BaseDTO {

    public static final String FIELD_WORK_CENTER_ID = "workCenterId";
    public static final String FIELD_WORK_CENTER_CODE = "workCenterCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_MANAGER_TYPE = "managerType";
    public static final String FIELD_MANAGER_ID = "managerId";
    public static final String FIELD_PROCESS_MODEL = "processModel";
    public static final String FIELD_AUDIT_TYPE = "auditType";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long workCenterId;

    /**
     * 工作中心代码
     */
    @Length(max = 30)
    private String workCenterCode;

    /**
     * 描述ID
     */
    @Length(max = 500)
    @MultiLanguageField
    private String description;

    /**
     * 负责人类型
     */
    @Length(max = 30)
    private String managerType;

    /**
     * 负责人ID
     */
    private Long managerId;

    /**
     * 处理模式：SSC_PROCESS_MODEL
     */
    @Length(max = 30)
    private String processModel;

    /**
     * 审核
     */
    @Length(max = 30)
    private String auditType;

    /**
     * 启用标志
     */
    @Length(max = 1)
    private String enabledFlag;

    @Transient
    private String workCenterCodeName;

    @Transient
    private String managerName;

    @Transient
    private String workTeamName;

    @Transient
    private String workTeamCodeName;

    @Transient
    private String employeeCodeName;

    @Transient
    private String name;

    @Transient
    private String employeeCode;

    @Transient
    private Long employeeId;

}
