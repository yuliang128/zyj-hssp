package com.hand.hec.csh.dto;

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

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * CshCnaps
 * </p>
 *
 * @author guiyu 2019/01/29 14:22
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_cnaps")
public class CshCnaps extends BaseDTO {

    public static final String FIELD_CNAPS_ID = "cnapsId";
    public static final String FIELD_BANK_ID = "bankId";
    public static final String FIELD_BANK_LOCATION_CODE = "bankLocationCode";
    public static final String FIELD_BANK_LOCATION_NAME = "bankLocationName";
    public static final String FIELD_PROVINCE_CODE = "provinceCode";
    public static final String FIELD_PROVINCE_NAME = "provinceName";
    public static final String FIELD_CITY_CODE = "cityCode";
    public static final String FIELD_CITY_NAME = "cityName";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long cnapsId;

    /**
     * 银行ID
     */
    @NotNull
    private Long bankId;

    /**
     * CNAPS号
     */
    @NotEmpty
    @Length(max = 30)
    private String bankLocationCode;

    /**
     * 支行名称
     */
    @Length(max = 2000)
    private String bankLocationName;

    /**
     * 支行省份代码
     */
    @Length(max = 30)
    private String provinceCode;

    /**
     * 支行省份名称
     */
    @Length(max = 2000)
    private String provinceName;

    /**
     * 支行城市代码
     */
    @Length(max = 30)
    private String cityCode;

    /**
     * 支行城市名称
     */
    @Length(max = 2000)
    private String cityName;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
