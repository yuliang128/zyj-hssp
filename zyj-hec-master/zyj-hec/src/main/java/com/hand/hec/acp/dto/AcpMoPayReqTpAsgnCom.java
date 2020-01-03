package com.hand.hec.acp.dto;

/**
 * <p>
 * 付款申请单类型关联公司
 * </p>
 * 
 * @author guiyuting 2019/04/25 17:06
 */
import javax.persistence.*;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "acp_mo_pay_req_tp_asgn_com")
public class AcpMoPayReqTpAsgnCom extends BaseDTO {

     public static final String FIELD_ASSIGN_ID = "assignId";
     public static final String FIELD_MO_PAY_REQ_TYPE_ID = "moPayReqTypeId";
     public static final String FIELD_COMPANY_ID = "companyId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long assignId;

    /**
     * 管理组织级付款申请单类型ID
     */
     @NotNull
     @Where
     private Long moPayReqTypeId;

    /**
     * 管理公司ID
     */
     @NotNull
     @JoinTable(name = "companyJoin", joinMultiLanguageTable = true, target = FndCompany.class, type = JoinType.LEFT,
             on = {@JoinOn(joinField = FndCompany.FIELD_COMPANY_ID),
                     @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
     @JoinTable(name = "companyJoin2", joinMultiLanguageTable = false, target = FndCompany.class, type = JoinType.LEFT,
             on = {@JoinOn(joinField = FndCompany.FIELD_COMPANY_ID)})
     private Long companyId;

     @Transient
     @JoinColumn(joinName = "companyJoin2", field = FndCompany.FIELD_COMPANY_CODE)
     private String companyCode;

    @Transient
    @JoinColumn(joinName = "companyJoin", field = FndCompany.FIELD_COMPANY_SHORT_NAME)
    private String companyName;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     }
