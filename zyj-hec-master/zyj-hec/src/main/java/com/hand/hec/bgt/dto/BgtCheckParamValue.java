package com.hand.hec.bgt.dto;

import lombok.*;

/**
 * description
 *
 * @author mouse 2019/01/08 16:02
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtCheckParamValue {

    public static final String PARAM_VALUE_TYPE_DETAIL = "DETAIL";
    public static final String PARAM_VALUE_TYPE_SUMMARY = "SUMMARY";
    public static final String PARAM_VALUE_TYPE_ALL = "ALL";

    private String paramValueType;
    private Long paramValueId;
    private String paramValueCode;
    private String str1;
    private String str2;
    private String str3;
    private String str4;
    private String str5;
    private Long long1;
    private Long long2;

    public BgtCheckParamValue(String paramValueType, Long paramValueId, String paramValueCode) {
        this.paramValueType = paramValueType;
        this.paramValueId = paramValueId;
        this.paramValueCode = paramValueCode;
    }
}
