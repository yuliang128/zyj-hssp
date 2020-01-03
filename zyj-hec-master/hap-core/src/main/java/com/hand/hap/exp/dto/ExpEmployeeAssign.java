package com.hand.hap.exp.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

import com.hand.hap.mybatis.common.query.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 员工分配信息Dto
 * </p>
 *
 * @author xiuxian.wu 2019/01/25 14:48
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_employee_assign")
public class ExpEmployeeAssign extends BaseDTO {

    public static final String FIELD_EMPLOYEES_ASSIGN_ID = "employeesAssignId";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_POSITION_ID = "positionId";
    public static final String FIELD_EMPLOYEE_JOB_ID = "employeeJobId";
    public static final String FIELD_EMPLOYEE_LEVELS_ID = "employeeLevelsId";
    public static final String FIELD_DATE_FROM = "dateFrom";
    public static final String FIELD_DATE_TO = "dateTo";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_RESPONSIBILITY_CENTER_ID = "responsibilityCenterId";
    public static final String FIELD_ENTITY_ID = "entityId";
    public static final String FIELD_CENTER_ID = "centerId";
    public static final String FIELD_PRIMARY_POSITION_FLAG = "primaryPositionFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    @Where
    private Long employeesAssignId;

    /**
     * 员工ID
     */
    @NotNull
    @Where
    private Long employeeId;

    /**
     * 公司ID
     */
    @NotNull
    @Where
    private Long companyId;

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
    /**
     * 管理组织民名称
     */
    @Transient
    private String companyOrgDescribe;


    /**
     * 岗位ID
     */
    @Where
    private Long positionId;
    /**
     * 岗位代码
     */
    @Transient
    private String positionCode;
    /**
     * 岗位名称
     */
    @Transient
    private String positionName;

    /**
     * 员工职务ID
     */
    @Where
    private Long employeeJobId;
    /**
     * 员工职务代码
     */
    @Transient
    private String employeeJobCode;
    /**
     * 员工职务描述
     */
    @Transient
    private String employeeJobName;
    /**
     * 员工职务级别
     */
    @Transient
    private Long levelSeriesId;

    /**
     * 员工级别ID
     */
    @Where
    private Long employeeLevelsId;
    /**
     * 员工级别代码
     */
    @Transient
    private String employeeLevelsCode;
    /**
     * 员工级别描述
     */
    @Transient
    private String employeeLevelsDescription;


    /**
     * 员工分配开始日期
     */
    private Date dateFrom;

    /**
     * 员工分配结束日期
     */
    private Date dateTo;

    /**
     * 核算主体ID
     */
    @Where
    private Long accEntityId;
    /**
     * 核算主体名称
     */
    @Transient
    private String accEntityName;

    /**
     * 责任中心ID
     */
    @Where
    private Long responsibilityCenterId;
    /**
     * 责任中心名称
     */
    @Transient
    private String responsibilityCenterName;

    /**
     * 预算实体ID
     */
    @Where
    private Long entityId;
    /**
     * 预算实体名称
     */
    @Transient
    private String entityName;

    /**
     * 预算中心ID
     */
    @Where
    private Long centerId;
    /**
     * 预算中心名称
     */
    @Transient
    private String centerName;

    /**
     * 主岗
     */
    @Length(max = 1)
    @Where
    private String primaryPositionFlag;

    /**
     * 启用标志
     */
    @Length(max = 1)
    @Where
    private String enabledFlag;

    @Transient
    private String employeeCode;

    @Transient
    private String employeeName;

    @Transient
    private Long unitId;

}
