package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hec.bgt.dto.BgtBudgetReserve;
import com.hand.hec.bgt.dto.BgtCheckResult;
import org.springframework.stereotype.Service;

/**
 * description
 *
 * @author mouse 2019/01/08 18:49
 */
public interface IBgtCheckService {

    /**
     * 针对某个预算占用进行预算检查
     *
     * @param reserve 预算占用
     * @param ignoreWarning 是否忽略警告
     * @param request 请求
     * @author mouse 2019-01-30 15:34
     * @return com.hand.hec.bgt.dto.BgtCheckResult
     */
    BgtCheckResult check(BgtBudgetReserve reserve, String ignoreWarning, IRequest request);

}
