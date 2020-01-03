package com.hand.hec.bgt.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.mapper.BgtControlRuleDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtControlRuleDetail;
import com.hand.hec.bgt.service.IBgtControlRuleDetailService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 预算控制规则明细ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtControlRuleDetailServiceImpl extends BaseServiceImpl<BgtControlRuleDetail>
                implements IBgtControlRuleDetailService {
    @Autowired
    private BgtControlRuleDetailMapper controlRuleDetailMapper;

    @Override
    public List<BgtControlRuleDetail> queryByRuleId(IRequest request, BgtControlRuleDetail bgtControlRuleDetail,
                    int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return controlRuleDetailMapper.queryByRuleId(bgtControlRuleDetail);
    }

    protected boolean useSelectiveUpdate() {
        return false;
    }
}
