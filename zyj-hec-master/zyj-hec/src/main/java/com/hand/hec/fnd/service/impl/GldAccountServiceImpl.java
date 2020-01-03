package com.hand.hec.fnd.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.GldAccount;
import com.hand.hec.fnd.mapper.GldAccountMapper;
import com.hand.hec.fnd.service.IGldAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * GldAccountServiceImpl
 * </p>
 * 
 * @author guiyu 2019/01/08 15:38
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GldAccountServiceImpl extends BaseServiceImpl<GldAccount> implements IGldAccountService {
    @Autowired
    private GldAccountMapper gldAccountMapper;

    @Override
    public Map<String, Object> accountSetRefrenceQuery(Long accountId, Long accountSetId) {
        return gldAccountMapper.accountSetRefrenceQuery(accountId, accountSetId);
    }

    @Override
    public List<GldAccount> selectAccountId(Map<String, Object> accountMap) {
        return gldAccountMapper.selectAccountId(accountMap);
    }

    @Override
    public List<GldAccount> selectByAccountSetId(Long accountSetId) {
        return gldAccountMapper.selectByAccountSetId(accountSetId);
    }

    @Override
    public List<GldAccount> hierarchyTreeQuery(IRequest request, Long accountSetId) {
        return gldAccountMapper.hierarchyTreeQuery(accountSetId);
    }


}
