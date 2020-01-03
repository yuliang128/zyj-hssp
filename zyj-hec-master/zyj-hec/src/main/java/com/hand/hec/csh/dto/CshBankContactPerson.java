package com.hand.hec.csh.dto;

import javax.persistence.*;

import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Table;
import java.util.Date;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 公司付款账户分配联系人Dto
 * </p>
 *
 * @author xiuxian.xu 2019-03-15 9:36
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_bank_contact_person")
public class CshBankContactPerson extends BaseDTO {

    public static final String FIELD_CONTACT_PERSONS_ID = "contactPersonsId";
    public static final String FIELD_BANK_BRANCH_ID = "bankBranchId";
    public static final String FIELD_CONTACT_PERSON_TYPE = "contactPersonType";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_START_DATE_ACTIVE = "startDateActive";
    public static final String FIELD_END_DATE_ACTIVE = "endDateActive";


    @Id
    @GeneratedValue
    @Where
    private Long contactPersonsId;

    /**
     * 分行ID
     */
    @NotNull
    @Where
    private Long bankBranchId;

    /**
     * 联系人类型（CONTACT_PERSON联系人，SIGNATURE_PERSON印签人）
     */
    @NotEmpty
    @Length(max = 30)
    private String contactPersonType;

    /**
     * 员工ID
     */
    @NotNull
    @Where
    @JoinTable(name = "employeeJoin", target = ExpEmployee.class, type = JoinType.LEFT, joinMultiLanguageTable = false, on = {@JoinOn(joinField = ExpEmployee.FIELD_EMPLOYEE_ID)})
    private Long employeeId;
    /**
     * 员工代码
     */
    @Transient
    @JoinColumn(joinName = "employeeJoin", field = ExpEmployee.FIELD_EMPLOYEE_CODE)
    private String employeeCode;
    /**
     * 员工姓名
     */
    @Transient
    @JoinColumn(joinName = "employeeJoin", field = ExpEmployee.FIELD_NAME)
    private String employeeName;

    /**
     * 开始日期
     */
    private Date startDateActive;

    /**
     * 结束日期
     */
    private Date endDateActive;

}
