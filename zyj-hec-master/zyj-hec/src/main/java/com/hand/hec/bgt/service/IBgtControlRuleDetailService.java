package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtControlRuleDetail;

import java.util.List;

/**
 * <p>
 * 预算控制规则明细Service
 * </p>
 * 
 * @author mouse 2019/01/07 16:56
 */
public interface IBgtControlRuleDetailService
                extends IBaseService<BgtControlRuleDetail>, ProxySelf<IBgtControlRuleDetailService> {

    /**
     * 预算控制规则定义明细信息查询
     *
     * @param bgtControlRuleDetail 明细信息
     * @author guiyuting 2019-03-07 17:16
     * @return 符合条件的明细信息
     */
    List<BgtControlRuleDetail> queryByRuleId(IRequest request, BgtControlRuleDetail bgtControlRuleDetail, int page,
                    int pageSize);

}
