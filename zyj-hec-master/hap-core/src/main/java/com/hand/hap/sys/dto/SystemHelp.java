package com.hand.hap.sys.dto;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.function.dto.Resource;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
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
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "sys_system_help")
@MultiLanguage
public class SystemHelp extends BaseDTO {

    public static final String FIELD_HELP_ID = "helpId";
    public static final String FIELD_FUNCTION_CODE = "functionCode";
    public static final String FIELD_SERIAL_NUMBER = "serialNumber";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_SERVICE_ID = "serviceId";
    public static final String FIELD_ICON = "icon";
    public static final String FIELD_START_DATE_ACTIVE = "startDateActive";
    public static final String FIELD_END_DATE_ACTIVE = "endDateActive";
    public static final String SERVICE_NAME = "serviceName";

    @Id
    @GeneratedValue
    private Long helpId;

    /**
     * 功能代码
     */
    @NotNull
    private String functionCode;

    /**
     * 序号
     */
    @NotNull
    private Long serialNumber;

    /**
     * 描述
     */
    @Where
    @Length(max = 500)
    @MultiLanguageField
    private String description;

    /**
     * 系统帮助SERVICE,多表连接，lov字段查询
     */
    @JoinTable(name = "resourceJoin", joinMultiLanguageTable = true, target = Resource.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = Resource.FIELD_RESOURCE_ID)})
    private Long serviceId;

    /**
     * 图标(路径/CSS类)
     */
    @Length(max = 255)
    private String icon;

    /**
     * 有效期从
     */
    private Date startDateActive;

    /**
     * 有效期到
     */
    private Date endDateActive;

    /**
     * 字段为页面，实际为Resource中的url
     */
    @JoinColumn(joinName = "resourceJoin", field = Resource.FIELD_URL)
    @Transient
    private String serviceName;

}
