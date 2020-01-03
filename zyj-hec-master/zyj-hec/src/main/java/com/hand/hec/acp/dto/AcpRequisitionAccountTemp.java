package com.hand.hec.acp.dto;

import lombok.*;

import java.util.Date;

/**
 * 选择单据临时表
 *
 * @author guiyuting 2019/05/08 10:35
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcpRequisitionAccountTemp {

    private Long requisitionHdsId;

    private Date creationDate;

}
