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
@Table(name = "csh_payment_gps_ref_emp")
public class CshPaymentGpsRefEmp extends BaseDTO {

     public static final String FIELD_REF_ID = "refId";
     public static final String FIELD_GROUP_ID = "groupId";
     public static final String FIELD_EMPLOYEE_ID = "employeeId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long refId;

    /**
     * 付款工作组ID
     */
     @NotNull
     private Long groupId;

    /**
     * 员工ID
     */
     @NotNull
     private Long employeeId;

    /**
     * 启用标志
     */
     @NotEmpty
     @Length(max = 1)
     private String enabledFlag;

     @Transient
     private String employeeName;

     }
