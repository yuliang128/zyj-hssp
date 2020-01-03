package com.hand.hap.sys.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "sys_standard_timezone")
public class SysStandardTimezone extends BaseDTO {

    public static final String FIELD_STANDARD_TIMEZONE_ID = "standardTimezoneId";
    public static final String FIELD_STANDARD_TIMEZONE_CODE = "standardTimezoneCode";
    public static final String FIELD_DESCRIPTION = "description";


    @Id
    @GeneratedValue
    private Long standardTimezoneId;

    /**
     * 标准时区代码
     */
    @Length(max = 30)
    private String standardTimezoneCode;

    /**
     * 描述
     */
    @NotEmpty
    @Length(max = 500)
    private String description;

}
