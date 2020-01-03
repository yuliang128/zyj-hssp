package com.hand.hec.fnd.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.GldAccount;
import com.hand.hec.fnd.dto.GldAccountHierarchyDetail;
import com.hand.hec.fnd.dto.GldAccountHierarchyTree;
import com.hand.hec.fnd.dto.GldAccountHierarchy;
import com.hand.hec.fnd.mapper.GldAccountHierarchyMapper;
import com.hand.hec.fnd.service.IGldAccountHierarchyDetailService;
import com.hand.hec.fnd.service.IGldAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.service.IGldAccountHierarchyService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * GldAccountHierarchyServiceImpl
 * </p>
 * 
 * @author guiyu 2019/01/08 15:37
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GldAccountHierarchyServiceImpl extends BaseServiceImpl<GldAccountHierarchy>
                implements IGldAccountHierarchyService {

    @Autowired
    public GldAccountHierarchyMapper gldAccountHierarchyMapper;

    @Autowired
    public IGldAccountService accountService;

    @Autowired
    public IGldAccountHierarchyDetailService accountHierarchyDetailService;

    @Override
    public List<GldAccountHierarchyTree> accountHierarchyTreeQuery(Long accountSetId) {
        return gldAccountHierarchyMapper.accountHierarchyTreeQuery(accountSetId);
    }

    @Override
    public void refreshAccountHierarchy(IRequest request, Long accountSetId) {
        // 查询需汇总的科目
        GldAccount gldAccount = new GldAccount();
        gldAccount.setAccountSetId(accountSetId);
        gldAccount.setSummaryFlag("Y");
        Criteria accountCri = new Criteria(gldAccount);
        accountCri.where(new WhereField(GldAccount.FIELD_ACCOUNT_SET_ID, Comparison.EQUAL));
        List<GldAccount> gldAccountList = accountService.selectOptions(request, gldAccount, accountCri);
        // 删除科目层次
        accountHierarchyDetailService.deleteByAccountSetId(accountSetId);
        for (GldAccount acc : gldAccountList) {
            // 创建科目层次
            GldAccountHierarchy gldAccountHierarchy = new GldAccountHierarchy();
            gldAccountHierarchy.setAccountSetId(accountSetId);
            gldAccountHierarchy.setParentAccountId(acc.getAccountId());
            Criteria hierarchyCri = new Criteria(gldAccountHierarchy);
            hierarchyCri.where(new WhereField(GldAccountHierarchy.FIELD_ACCOUNT_SET_ID, Comparison.EQUAL),
                            new WhereField(GldAccountHierarchy.FIELD_PARENT_ACCOUNT_ID, Comparison.EQUAL));
            List<GldAccountHierarchy> gldAccountHierarchyList =
                            gldAccountHierarchyMapper.selectOptions(gldAccountHierarchy, hierarchyCri);
            for (GldAccountHierarchy hierarchy : gldAccountHierarchyList) {
                Map<String, Object> map = new HashMap<>();
                map.put("accountSetId", accountSetId);
                map.put("parentAccountId", acc.getAccountId());
                map.put("fromAccountCode", hierarchy.getFromAccountCode());
                map.put("toAccountCode", hierarchy.getToAccountCode());
                List<GldAccount> gldAccounts = accountService.selectAccountId(map);
                List<GldAccountHierarchyDetail> newAccountDetail = new ArrayList<>();
                for (GldAccount queryGldAccount : gldAccounts) {
                    GldAccountHierarchyDetail gldAccountHierarchyDetail = new GldAccountHierarchyDetail();
                    gldAccountHierarchyDetail.setAccountSetId(accountSetId);
                    gldAccountHierarchyDetail.setAccountId(queryGldAccount.getAccountId());
                    gldAccountHierarchyDetail.setParentAccountId(acc.getAccountId());
                    gldAccountHierarchyDetail.set__status(DTOStatus.ADD);
                    newAccountDetail.add(gldAccountHierarchyDetail);
                }
                accountHierarchyDetailService.batchUpdate(request, newAccountDetail);
            }
        }
        // 插入科目层次顶层
        List<GldAccount> topGldAccounts = accountService.selectByAccountSetId(accountSetId);
        List<GldAccountHierarchyDetail> topDetails = new ArrayList<>();
        for (GldAccount gldAccount1 : topGldAccounts) {
            GldAccountHierarchyDetail topDetail = new GldAccountHierarchyDetail();
            topDetail.setAccountSetId(accountSetId);
            topDetail.setAccountId(gldAccount1.getAccountId());
            topDetail.set__status(DTOStatus.ADD);
            topDetails.add(topDetail);
        }
        accountHierarchyDetailService.batchUpdate(request, topDetails);

    }
}
