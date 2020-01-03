package com.hand.hec.ssc.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

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
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "ssc_work_team")
@MultiLanguage
public class SscWorkTeam extends BaseDTO {

    public static final String FIELD_WORK_TEAM_ID = "workTeamId";
    public static final String FIELD_WORK_CENTER_ID = "workCenterId";
    public static final String FIELD_WORK_TEAM_CODE = "workTeamCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_MANAGER_EMPLOYEE_ID = "managerEmployeeId";
    public static final String FIELD_PARENT_WORK_TEAM_ID = "parentWorkTeamId";
    public static final String FIELD_WORK_TEAM_TYPE = "workTeamType";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long workTeamId;

    /**
     * 工作中心ID
     */
    @NotNull
    private Long workCenterId;

    /**
     * 工作组代码
     */
    @NotEmpty
    @Length(max = 30)
    private String workTeamCode;

    /**
     * 描述ID
     */
    @Length(max = 500)
    @MultiLanguageField
    private String description;

    /**
     * 工作组负责人ID
     */
    private Long managerEmployeeId;

    /**
     * 上级工作组
     */
    private Long parentWorkTeamId;

    /**
     * 工作组类型
     */
    @NotEmpty
    @Length(max = 30)
    private String workTeamType;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    @Transient
    private String workTeamTypeName;

    @Transient
    private String managerEmployee;

    @Transient
    private String parentWorkTeam;

    @Transient
    private String workTeamCodeName;

}

