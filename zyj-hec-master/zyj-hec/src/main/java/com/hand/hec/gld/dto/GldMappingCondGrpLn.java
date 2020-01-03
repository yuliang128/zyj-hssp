package com.hand.hec.gld.dto;

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
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "gld_mapping_cond_grp_ln")
public class GldMappingCondGrpLn extends BaseDTO {

     public static final String FIELD_COND_GRP_LN_ID = "condGrpLnId";
     public static final String FIELD_GROUP_NAME = "groupName";
     public static final String FIELD_MAPPING_CONDITION_CODE = "mappingConditionCode";
     public static final String FIELD_LAYOUT_PRIORITY = "layoutPriority";

     @Id
     @GeneratedValue
    private Long condGrpLnId;

    /**
     * 匹配组
     */
     @NotEmpty
     @Length(max = 240)
     private String groupName;

    /**
     * 匹配项代码
     */
     @NotEmpty
     @Length(max = 240)
     private String mappingConditionCode;

    /**
     * 匹配项描述
     */
     @Transient
     private String mappingConditionSqlDesc;

    /**
     * 关联字段
     */
     @Transient
     private String refIdField;

    /**
     * 匹配项表主键
     */
     @Transient
     private Long mappingConditionSqlId;

    /**
     * 优先级
     */
     @NotNull
     private Long layoutPriority;

    /**
     * 匹配项对应的LOV
     */
     @Transient
     private String lovStatement;

     }
