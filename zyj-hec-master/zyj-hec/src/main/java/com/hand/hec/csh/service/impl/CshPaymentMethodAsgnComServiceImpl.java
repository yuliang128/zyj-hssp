package com.hand.hec.csh.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.mapper.CshBankMapper;
import com.hand.hec.csh.mapper.CshPaymentMethodAsgnComMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshPaymentMethodAsgnCom;
import com.hand.hec.csh.service.ICshPaymentMethodAsgnComService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * <p>
 * 付款方式分配公司 Service 实现类
 * </p>
 *
 * @author yang.cai@hand-china.com 2019/02/19 15:21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentMethodAsgnComServiceImpl extends BaseServiceImpl<CshPaymentMethodAsgnCom> implements ICshPaymentMethodAsgnComService{

    @Autowired
    private CshPaymentMethodAsgnComMapper mapper;
    @Override
    public List<CshPaymentMethodAsgnCom> queryLov(IRequest request, CshPaymentMethodAsgnCom cshPaymentMethodAsgnCom, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryLov(cshPaymentMethodAsgnCom);
    }

    @Override
    public List<CshPaymentMethodAsgnCom> query(IRequest request, CshPaymentMethodAsgnCom cshPaymentMethodAsgnCom, int page, int pageSize) {
        return mapper.query(cshPaymentMethodAsgnCom);
    }
}