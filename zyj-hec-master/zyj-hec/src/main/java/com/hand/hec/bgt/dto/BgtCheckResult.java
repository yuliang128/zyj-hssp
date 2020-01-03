package com.hand.hec.bgt.dto;

import lombok.*;

/**
 * description
 *
 * @author mouse 2019/01/08 19:15
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtCheckResult {

    public static final String RESULT_TYPE_PASSED = "PASSED";
    public static final String RESULT_TYPE_WARNING = "WARNING";
    public static final String RESULT_TYPE_BLOCKED = "BLOCKED";

    private Boolean isPassed;
    private String resultType;
    private String message;
}
