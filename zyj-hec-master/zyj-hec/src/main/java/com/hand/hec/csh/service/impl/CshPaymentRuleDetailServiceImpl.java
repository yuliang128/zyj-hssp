package com.hand.hec.csh.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshPaymentRuleDetail;
import com.hand.hec.csh.mapper.CshPaymentRuleDetailMapper;
import com.hand.hec.csh.service.ICshPaymentRuleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentRuleDetailServiceImpl extends BaseServiceImpl<CshPaymentRuleDetail> implements ICshPaymentRuleDetailService{

    @Autowired
    private CshPaymentRuleDetailMapper cshPaymentRuleDetailMapper;


    /**
     *
     * 校验函数
     *
     * 检验其中的左右括号和and or是否匹配，匹配则返回true 不匹配返回false
     */
    @Override
    public int check(IRequest request,String str) {
        return cshPaymentRuleDetailMapper.check(str);
    }
}