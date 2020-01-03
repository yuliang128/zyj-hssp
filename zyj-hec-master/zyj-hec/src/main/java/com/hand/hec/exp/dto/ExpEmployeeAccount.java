package com.hand.hec.exp.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 员工分配银行账户dto
 * </p>
 *
 * @author xiuxian.wu 2019/01/21 10:20
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "exp_employee_account")
public class ExpEmployeeAccount extends BaseDTO {

    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_LINE_NUMBER = "lineNumber";
    public static final String FIELD_BANK_CODE = "bankCode";
    public static final String FIELD_BANK_NAME = "bankName";
    public static final String FIELD_BANK_LOCATION_CODE = "bankLocationCode";
    public static final String FIELD_BANK_LOCATION = "bankLocation";
    public static final String FIELD_PROVINCE_CODE = "provinceCode";
    public static final String FIELD_PROVINCE_NAME = "provinceName";
    public static final String FIELD_CITY_CODE = "cityCode";
    public static final String FIELD_CITY_NAME = "cityName";
    public static final String FIELD_ACCOUNT_NUMBER = "accountNumber";
    public static final String FIELD_ACCOUNT_NAME = "accountName";
    public static final String FIELD_NOTES = "notes";
    public static final String FIELD_PRIMARY_FLAG = "primaryFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    /**
     * 员工ID
     */
    @NotNull
    @Id
    private Long employeeId;

    /**
     * 行号
     */
    @Id
    private Long lineNumber;

    /**
     * 银行代码
     */
    @Length(max = 30)
    private String bankCode;

    /**
     * 银行名称
     */
    @Length(max = 200)
    private String bankName;

    /**
     * 分行代码
     */
    @Length(max = 30)
    private String bankLocationCode;

    /**
     * 分行名称
     */
    @Length(max = 200)
    private String bankLocation;

    /**
     * 分行所在省
     */
    @Length(max = 30)
    private String provinceCode;

    /**
     * 省名称
     */
    @Length(max = 200)
    private String provinceName;

    /**
     * 分行所在城市
     */
    @Length(max = 30)
    private String cityCode;

    /**
     * 市名称
     */
    @Length(max = 200)
    private String cityName;

    /**
     * 银行帐号
     */
    @Length(max = 200)
    private String accountNumber;

    /**
     * 银行户名
     */
    @Length(max = 200)
    private String accountName;

    /**
     * 备注
     */
    @Length(max = 2000)
    private String notes;

    /**
     * 主帐号标志
     */
    @Length(max = 1)
    private String primaryFlag;

    /**
     * 启用标志
     */
    @Length(max = 1)
    private String enabledFlag;


    @Transient
    private Long payeeId;

    @Transient
    private String payeeName;

    @Transient
    private String payeeCode;

    @Transient
    private String payeeCategory;

    @Transient
    private Long accEntityId;

    @Transient
    private Long payeeTypeId;

    @Transient
    private String payeeTypeCode;

    @Transient
    private String payeeTypeName;
}
