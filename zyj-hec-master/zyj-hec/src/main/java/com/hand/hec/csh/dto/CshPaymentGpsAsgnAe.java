package com.hand.hec.csh.dto;

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
@Table(name = "csh_payment_gps_asgn_ae")
public class CshPaymentGpsAsgnAe extends BaseDTO {

     public static final String FIELD_ASSIGN_AE_ID = "assignAeId";
     public static final String FIELD_GROUP_ID = "groupId";
     public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
     public static final String FIELD_AUTHORITY_CODE = "authorityCode";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long assignAeId;

    /**
     * 付款工作组ID
     */
     @NotNull
     private Long groupId;

    /**
     * 核算主体ID
     */
     @NotNull
     private Long accEntityId;

    /**
     * 是否分权限支付（SYSCODE:CSH_PAYMENT_GPS_ASGN_AE_AUTHORITY)
     */
     @NotEmpty
     @Length(max = 30)
     private String authorityCode;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     @Transient
     private String accEntityCode;
     @Transient
     private String accEntityName;
     @Transient
     private String authorityFlag;

     }
