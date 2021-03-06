package com.hand.hec.wfl.dto;

/**
 * Auto Generated By Hap Code Generator
 **/

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.JoinCode;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import javax.persistence.Transient;

import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.core.annotation.MultiLanguage;

import java.util.Map;


@MultiLanguage
@ExtensionAttribute(disable = true)
@Table(name = "wfl_gateway")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WflGateway extends BaseDTO {

    public static final String FIELD_GATEWAY_ID = "gatewayId";
    public static final String FIELD_PROCESS_ID = "processId";
    public static final String FIELD_GATEWAY_CODE = "gatewayCode";
    public static final String FIELD_GATEWAY_NAME = "gatewayName";
    public static final String FIELD_GATEWAY_TYPE = "gatewayType";


    public static final String ELEMENT_GATEWAY = "GATEWAY";

    public static final String GATEWAY_TYPE_EXCLUSIVE = "EXCLUSIVE";
    public static final String GATEWAY_TYPE_PARALLEL = "PARALLEL";


    /**
     * 网关ID
     */
    @Id
    @GeneratedValue
    @Where
    private Long gatewayId;

    /**
     * 工作流ID
     */
    @Where
    private Long processId;

    /**
     * 流转代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where(comparison = Comparison.LIKE)
    private String gatewayCode;

    /**
     * 网关名称
     */
    @Length(max = 240)
    @MultiLanguageField
    @Where(comparison = Comparison.LIKE)
    private String gatewayName;

    /**
     * 网关类型
     */
    @NotEmpty
    @Length(max = 30)
    private String gatewayType;

    @Transient
    @JoinCode(code = "WFL.GATEWAY_TYPE", joinKey = FIELD_GATEWAY_TYPE)
    private String gatewayTypeName;

    public WflGateway(Map map) {
        this.setGatewayId(Long.parseLong(map.get("element_id").toString()));
        this.setGatewayCode(map.get("element_code").toString());
        this.setGatewayName(map.get("element_name").toString());
    }
}
