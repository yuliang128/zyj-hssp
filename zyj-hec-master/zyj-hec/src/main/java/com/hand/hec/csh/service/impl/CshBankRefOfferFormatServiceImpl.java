package com.hand.hec.csh.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshBankRefOfferFormat;
import com.hand.hec.csh.service.ICshBankRefOfferFormatService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 公司付款账户分配报盘格式ServiceImpl
 * </p>
 *
 * @author xiuxian.xu 2019-03-15 9:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CshBankRefOfferFormatServiceImpl extends BaseServiceImpl<CshBankRefOfferFormat> implements ICshBankRefOfferFormatService {
    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }
}