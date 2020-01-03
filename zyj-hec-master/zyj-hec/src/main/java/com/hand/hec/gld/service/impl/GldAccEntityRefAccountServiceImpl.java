package com.hand.hec.gld.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import com.github.pagehelper.PageHelper;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hand.hec.fnd.dto.GldAccount;
import com.hand.hec.gld.dto.GldAccEntityRefAccount;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.mapper.GldAccEntityRefAccountMapper;
import com.hand.hec.gld.service.IGldAccEntityRefAccountService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * GldAccEntityRefAccountServiceImpl
 * </p>
 * 
 * @author yang.duan 2019/01/10 13:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GldAccEntityRefAccountServiceImpl extends BaseServiceImpl<GldAccEntityRefAccount> implements IGldAccEntityRefAccountService{

    @Autowired
    GldAccEntityRefAccountMapper gldAccEntityRefAccountMapper;

    @Override
    public List<GldAccEntityRefAccount> queryActNotAsgnAccE(Long accEntityId,Long setOfBooksId,IRequest request, Integer page, Integer pageSize) {
        if(page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        return gldAccEntityRefAccountMapper.queryActNotAsgnAccE(accEntityId,setOfBooksId);
    }

    @Override
    public List<GldAccEntityRefAccount> queryAccountBySob(Long setOfBooksId, IRequest request, Integer page, Integer pageSize) {
        if(page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        return gldAccEntityRefAccountMapper.queryAccountBySob(setOfBooksId);
    }

    @Override
    public Boolean batchSubmit(List<GldAccountingEntity> gldAccountingEntityList, IRequest request) {
        IBaseService<GldAccEntityRefAccount> self = ((IBaseService<GldAccEntityRefAccount>) AopContext.currentProxy());
        for(GldAccountingEntity gldAccountingEntity:gldAccountingEntityList){
            List<GldAccount> accountList = gldAccountingEntity.getAccountList();
            Long accEntityId = gldAccountingEntity.getAccEntityId();
            for(GldAccount gldAccount:accountList){
                Long accountId = gldAccount.getAccountId();
                Long setOfBooksId = gldAccountingEntity.getSetOfBooksId();
                GldAccEntityRefAccount gldAccEntityRefAccount = GldAccEntityRefAccount.builder().accEntityId(accEntityId).accountId(accountId).setOfBooksId(setOfBooksId).build();
                List<GldAccEntityRefAccount> list = mapper.select(gldAccEntityRefAccount);
                if(list.isEmpty()){
                    //未分配过
                    gldAccEntityRefAccount.setEnabledFlag("Y");
                    self.insertSelective(request,gldAccEntityRefAccount);
                }
            }
        }
        return true;
    }
}