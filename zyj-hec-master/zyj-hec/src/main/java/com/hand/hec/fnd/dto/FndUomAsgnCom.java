package com.hand.hec.fnd.dto;

import javax.persistence.*;

import com.hand.hap.mybatis.common.query.Where;
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
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "fnd_uom_asgn_com")
public class FndUomAsgnCom extends BaseDTO {

     public static final String FIELD_ASSIGN_COM_ID = "assignComId";
     public static final String FIELD_ASSIGN_MO_ID = "assignMoId";
     public static final String FIELD_COMPANY_ID = "companyId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";
     public static final String FIELD_COMPANY_CODE = "companyCode";
     public static final String FIELD_COMPANY_NAME = "companyName";


     @Id
     @GeneratedValue
     private Long assignComId;

    /**
     * 计量单位分配管理组织ID
     */
     @NotNull
     @Where
     private Long assignMoId;

    /**
     * 管理公司ID
     */
     @NotNull
     @Where
     private Long companyId;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

    /**
     * 公司代码
     */
    @Transient
     private String companyCode;

    /**
     * 公司名称
     */
    @Transient
    private String companyName;

     }
