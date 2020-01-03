package com.hand.hap.exp.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 员工定义Dto
 * </p>
 *
 * @author xiuxian.wu 2019/01/25 14:48
 */

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_employee")
public class ExpEmployee extends BaseDTO {

    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_EMPLOYEE_CODE = "employeeCode";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_MOBIL = "mobil";
    public static final String FIELD_PHONE = "phone";
    public static final String FIELD_BANK_OF_DEPOSIT = "bankOfDeposit";
    public static final String FIELD_BANK_ACCOUNT = "bankAccount";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_EMPLOYEE_TYPE_ID = "employeeTypeId";
    public static final String FIELD_ID_TYPE = "idType";
    public static final String FIELD_ID_CODE = "idCode";
    public static final String FIELD_NOTES = "notes";
    public static final String FIELD_NATIONAL_IDENTIFIER = "nationalIdentifier";
    public static final String FIELD_HMAP_SYNC_FLAG = "hmapSyncFlag";
    public static final String FIELD_HMAP_SYNC_DATE = "hmapSyncDate";
    public static final String FIELD_PLACE_ID = "placeId";
    public static final String FIELD_CODE_NAME = "codeName";


    @Id
    @GeneratedValue
    @Where
    private Long employeeId;

    /**
     * 员工代码
     */
    @NotEmpty
    @Where
    @Length(max = 30)
    private String employeeCode;

    /**
     * 姓名
     */
    @NotEmpty
    @Length(max = 50)
    @Where
    private String name;

    /**
     * E-mail
     */
    @Length(max = 50)
    private String email;

    /**
     * 移动电话
     */
    @Length(max = 50)
    private String mobil;

    /**
     * 固定电话
     */
    @Length(max = 50)
    private String phone;

    /**
     * 开户行
     */
    @Length(max = 200)
    private String bankOfDeposit;

    /**
     * 银行帐户
     */
    @Length(max = 200)
    private String bankAccount;

    /**
     * 启用标志
     */
    @NotEmpty
    @Where
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 员工类型ID
     */
    private Long employeeTypeId;

    /**
     * 证件类型
     */
    @Length(max = 50)
    private String idType;

    /**
     * 证件编码
     */
    @Length(max = 200)
    private String idCode;

    /**
     * 备注
     */
    @Length(max = 200)
    private String notes;

    /**
     * 证件编号
     */
    @Length(max = 200)
    private String nationalIdentifier;

    /**
     * HMAP同步标志，Y/N
     */
    @Length(max = 1)
    private String hmapSyncFlag;

    /**
     * HMAP同步日期
     */
    private Date hmapSyncDate;

    /**
     * 费用政策地点id
     */
    @Where
    private Long placeId;

    /**
     * 费用政策地点名称
     */
    @Transient
    private String placeName;

    /**
     * 员工代码+名称
     */
    @Transient
    private String codeName;



}
