package com.hand.hec.exp.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
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
/**
 * <p>
 * ExpMoReqItemsAsgnCom
 * </p>
 *
 * @author yang.duan 2019/02/19 10:01
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable=true)
@Table(name = "exp_mo_req_items_asgn_com")
public class ExpMoReqItemsAsgnCom extends BaseDTO {

     public static final String FIELD_ASSIGN_ID = "assignId";
     public static final String FIELD_MO_REQ_ITEM_ID = "moReqItemId";
     public static final String FIELD_COMPANY_ID = "companyId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";

     public static final String FIELD_COMPANY_CODE = "companyCode";
     public static final String FIELD_COMPANY_SHORT_NAME = "companyShortName";

     @Id
     @GeneratedValue
     private Long assignId;

    /**
     * 申请项目ID
     */
     @NotNull
     private Long moReqItemId;

    /**
     * 公司ID
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


     /*
     * 公司Code
     * */
     @Transient
     private String companyCode;

     /*
     * 公司简称
     * */
     @Transient
     private String companyShortName;

     @Transient
     private Long magOrgId;

     }
