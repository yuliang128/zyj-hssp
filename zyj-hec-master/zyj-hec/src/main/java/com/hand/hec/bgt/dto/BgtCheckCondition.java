package com.hand.hec.bgt.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.*;

/**
 * description
 *
 * @author mouse 2019/01/08 16:01
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtCheckCondition {
    private Map<String, BgtCheckParamValue> checkParamValueMap = new HashMap<String, BgtCheckParamValue>();

    private String[] dimensionNameList = new String[] {"DIMENSION_1", "DIMENSION_2", "DIMENSION_3", "DIMENSION_4",
            "DIMENSION_5", "DIMENSION_6", "DIMENSION_7", "DIMENSION_8", "DIMENSION_9", "DIMENSION_10", "DIMENSION_11",
            "DIMENSION_12", "DIMENSION_13", "DIMENSION_14", "DIMENSION_15", "DIMENSION_16", "DIMENSION_17",
            "DIMENSION_18", "DIMENSION_19", "DIMENSION_20"};
}
