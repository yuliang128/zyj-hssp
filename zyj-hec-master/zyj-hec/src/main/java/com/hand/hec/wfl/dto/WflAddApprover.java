package com.hand.hec.wfl.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.Table;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/3/19 \* Time: 20:25 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */


@Component
@ExtensionAttribute(disable = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
/**
 * <p>
 *   工作流添加审批者
 * </p>
 *
 * @author mouse 2019/02/18 19:17
 */
public class WflAddApprover extends BaseDTO {

    public static final String ADD_ORDER_BEFORE = "BEFORE";
    public static final String ADD_ORDER_AFTER = "AFTER";
    public static final String ADD_ORDER_PARALLEL = "PARALLEL";

    private String order;
    private Long userId;
}
