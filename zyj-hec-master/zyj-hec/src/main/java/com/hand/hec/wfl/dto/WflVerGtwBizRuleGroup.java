package com.hand.hec.wfl.dto;

/** Auto Generated By Hap Code Generator **/

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hand.hap.core.annotation.Children;
import com.hand.hap.mybatis.common.query.Where;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import lombok.*;

import java.util.HashMap;
import java.util.Map;


@MultiLanguage
@ExtensionAttribute(disable = true)
@Table(name = "wfl_ver_gtw_biz_rule_group")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WflVerGtwBizRuleGroup extends BaseDTO {

    public static final String FIELD_VERSION_GROUP_ID = "versionGroupId";
    public static final String FIELD_GROUP_ID = "groupId";
    public static final String FIELD_VERSION = "version";
    public static final String FIELD_GATEWAY_ID = "gatewayId";
    public static final String FIELD_SEQUENCE = "sequence";
    public static final String FIELD_GROUP_CODE = "groupCode";
    public static final String FIELD_GROUP_NAME = "groupName";
    public static final String FIELD_FLOW_ID = "flowId";
    public static final String FIELD_ADJUST_TYPE = "adjustType";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

    public static final String ADJUST_TYPE_ONE = "ONE";
    public static final String ADJUST_TYPE_ALL = "ALL";

    /**
     * ID
     */
    @Id
    @GeneratedValue
    @Where
    private Long versionGroupId;

    @Where
    private Long groupId;

    /**
     * 工作流版本
     */
    @Where
    private Long version;

    /**
     * 接收ID
     */
    @Where
    private Long gatewayId;

    /**
     * 序号
     */
    private Long sequence;

    /**
     * 权限组代码
     */
    @NotEmpty
    @Length(max = 30)
    private String groupCode;

    /**
     * 权限组名称
     */
    @Length(max = 30)
    @MultiLanguageField
    private String groupName;

    /**
     * 流转ID
     */
    private Long flowId;

    /**
     * 权限明细判断类型
     */
    @NotEmpty
    @Length(max = 30)
    private String adjustType;

    /**
     * 启用标志
     */
    @Length(max = 1)
    private String enabledFlag;

    @Transient
    @Children
    private Map<String, WflVerGtwBizRuleDetail> gtwBizRuleDetailMap;

    public WflVerGtwBizRuleDetail getGtwBizRuleDetail(String key) {
        return gtwBizRuleDetailMap == null ? null : gtwBizRuleDetailMap.get(key);
    }

    public void addGtwBizRuleDetail(String key, WflVerGtwBizRuleDetail detail) {
        if (gtwBizRuleDetailMap == null) {
            gtwBizRuleDetailMap = new HashMap<String, WflVerGtwBizRuleDetail>();
        }
        gtwBizRuleDetailMap.put(key, detail);
    }

    public WflVerGtwBizRuleGroup(WflGatewayBizRuleGroup group) {
        this.groupId = group.getGroupId();
        this.gatewayId = group.getGatewayId();
        this.sequence = group.getSequence();
        this.groupCode = group.getGroupCode();
        this.groupName = group.getGroupName();
        this.flowId = group.getFlowId();
        this.adjustType = group.getAdjustType();
        this.enabledFlag = group.getEnabledFlag();
    }

}