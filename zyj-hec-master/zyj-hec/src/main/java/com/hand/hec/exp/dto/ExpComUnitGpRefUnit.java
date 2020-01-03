package com.hand.hec.exp.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.Transient;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_com_unit_gp_ref_unit")
public class ExpComUnitGpRefUnit extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_UNIT_GROUP_COM_ASSIGN_ID = "unitGroupComAssignId";
    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_UNIT_NAME = "unitName";
    public static final String FIELD_UNIT_CODE = "unitCode";
    public static final String FIELD_COMPANY_NAME = "companyName";
    public static final String FIELD_COMPANY_CODE = "companyCode";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 管理组织级部门组公司分配ID
     */
    @NotNull
    private Long unitGroupComAssignId;

    /**
     * 部门ID
     */
    @NotNull
    private Long unitId;

    @Transient
    private String unitName;

    @Transient
    private String unitCode;

    @Transient
    private String companyCode;

    @Transient
    private String companyName;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
