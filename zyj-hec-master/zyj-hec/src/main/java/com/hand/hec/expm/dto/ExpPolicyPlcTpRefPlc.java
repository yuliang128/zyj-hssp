package com.hand.hec.expm.dto;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 费用政策地点类型分配政策地点Dto
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 15:50
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_policy_plc_tp_ref_plc")
public class ExpPolicyPlcTpRefPlc extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_ASSIGN_ID = "assignId";
    public static final String FIELD_PLACE_ID = "placeId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 费用政策地点类型分配公司ID
     */
    @NotNull
    private Long assignId;

    /**
     * 地点ID
     */
    @NotNull
    private Long placeId;
    /**
     * 地点代码
     */
    @Transient
    private String placeCode;
    /**
     * 地点描述
     */
    @Transient
    private String placeDesc;
    /**
     * 区域
     */
    @Transient
    private String districtDesc;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;
    /**
     * 起头地点ID
     */
    @Transient
    private Long placeFromId;
    @Transient
    /**
     * 结束地点ID
     */
    private Long placeToId;
    @Transient
    /**
     * 起点区域ID
     */
    private Long districtFromId;

    /**
     * 结束区域ID
     */
    @Transient
    private Long districtToId;

}
