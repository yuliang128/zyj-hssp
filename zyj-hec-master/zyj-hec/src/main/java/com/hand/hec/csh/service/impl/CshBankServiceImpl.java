package com.hand.hec.csh.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshCnaps;
import com.hand.hec.csh.mapper.CshBankMapper;
import com.hand.hec.csh.service.ICshCnapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshBank;
import com.hand.hec.csh.service.ICshBankService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * CshBankServiceImpl
 * </p>
 * 
 * @author guiyu 2019/01/29 14:21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CshBankServiceImpl extends BaseServiceImpl<CshBank> implements ICshBankService {
    @Autowired
    private CshBankMapper mapper;

    @Autowired
    private ICshCnapsService cshCnapsService;

    @Override
    public List<CshBank> queryAll(IRequest request, CshBank cshBank, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryAll(cshBank);
    }

    @Override
    public List<CshBank> batchSubmit(IRequest request, List<CshBank> cshBanks) {
        List<CshBank> banks = self().batchUpdate(request, cshBanks);
        for (CshBank cshBank : banks) {
            List<CshCnaps> cshCnapss = cshBank.getCnaps();
            if (cshCnapss != null && cshCnapss.size() > 0) {
                if (DTOStatus.ADD.equals(cshBank.get__status())) {
                    for (CshCnaps cshCnaps : cshCnapss) {
                        cshCnaps.setBankId(cshBank.getBankId());

                    }
                }

                cshCnapss = cshCnapsService.batchUpdate(request, cshCnapss);
            }
        }

        return banks;
    };
}
