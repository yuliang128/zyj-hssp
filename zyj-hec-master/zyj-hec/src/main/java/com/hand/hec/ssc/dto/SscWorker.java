package com.hand.hec.ssc.dto;

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
@Table(name = "ssc_worker")
public class SscWorker extends BaseDTO {

     public static final String FIELD_WORKER_ID = "workerId";
     public static final String FIELD_WORK_TEAM_ID = "workTeamId";
     public static final String FIELD_EMPLOYEE_ID = "employeeId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long workerId;

    /**
     * 工作组ID
     */
     @NotNull
     private Long workTeamId;

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
     private String employeeCode;

     @Transient
     private String employeeName;

     }
