package com.hand.hec.csh.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.mapper.CshMoPaymentUsedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshMoPaymentUsed;
import com.hand.hec.csh.service.ICshMoPaymentUsedService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 付款用途Service实现类
 * </p>
 *
 * @author yang.cai@hand-chian.com 2019/02/26 19:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CshMoPaymentUsedServiceImpl extends BaseServiceImpl<CshMoPaymentUsed> implements ICshMoPaymentUsedService {

    @Autowired
    CshMoPaymentUsedMapper mapper;

    @Override
    public List<CshMoPaymentUsed> queryAll(IRequest request, CshMoPaymentUsed cshMoPaymentUsed, int pageNum,
                    int pageSize) {
        return mapper.queryAll(cshMoPaymentUsed);
    }

    /**
     * @Description 获取公司（管理组织）下启用的付款用途
     *
     * @Author Tagin
     * @Date 2019-03-04 20:50
     * @param iRequest
     * @Return java.util.List<com.hand.hec.csh.dto.CshMoPaymentUsed>
     * @Version 1.0
     **/
    @Override
    public List<CshMoPaymentUsed> queryPaymentUsed(IRequest iRequest) {
        return mapper.queryPaymentUsed();
    }
}
