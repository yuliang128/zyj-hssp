package com.hand.hec.expm.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.common.query.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * <p>
 * 费用政策地点类型Dto
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 15:50
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@MultiLanguage
@Table(name = "exp_policy_place_type")
public class ExpPolicyPlaceType extends BaseDTO {

     public static final String FIELD_PLACE_TYPE_ID = "placeTypeId";
     public static final String FIELD_PLACE_TYPE_CODE = "placeTypeCode";
     public static final String FIELD_MAG_ORG_ID = "magOrgId";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     @Where
     private Long placeTypeId;

    /**
     * 地点类型代码
     */
     @NotEmpty
     @Length(max = 30)
     @Where
     private String placeTypeCode;

    /**
     * 管理组织ID
     */
     @NotNull
     @Where
     private Long magOrgId;

    /**
     * 描述
     */
     @NotEmpty
     @Length(max = 500)
     @Where
     @MultiLanguageField
     private String description;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     @Where
     private String enabledFlag;

     }
