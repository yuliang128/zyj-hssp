package com.hand.hec.gld.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.lock.components.DatabaseLockProvider;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.GldPeriod;
import com.hand.hec.fnd.service.IGldPeriodService;
import com.hand.hec.gld.dto.GlAccountEntry;
import com.hand.hec.gld.mapper.GlAccountEntryMapper;
import com.hand.hec.gld.service.IGlAccountEntryService;

/**
 * <p>
 * 总账分录Impl
 * </p>
 *
 * @author Tagin 2019/03/29 10:34
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GlAccountEntryServiceImpl extends BaseServiceImpl<GlAccountEntry> implements IGlAccountEntryService {
    @Autowired
    private GlAccountEntryMapper glAccountEntryMapper;

    @Autowired
    private IGldPeriodService gldPeriodService;

    @Autowired
    private DatabaseLockProvider databaseLockProvider;

    public void lockTable(IRequest iRequest, String ruleType, Long headerId) {
        String tableName;
        StringBuilder whereConditionSB = new StringBuilder();
        switch (ruleType) {
            case GlAccountEntry.RULE_TYPE_EXP_REPORT:
                tableName = "exp_report_account era,exp_report_dist d,exp_report_line  l,exp_report_header  h";
                whereConditionSB.append("h.exp_report_header_id = ").append(headerId)
                                .append(" and era.exp_report_header_id = h.exp_report_header_id")
                                .append(" and d.exp_report_line_id = l.exp_report_line_id")
                                .append(" and l.exp_report_header_id = h.exp_report_header_id");
                databaseLockProvider.lock(tableName, whereConditionSB.toString(), null);
                break;
            case GlAccountEntry.RULE_TYPE_CSH_TRANSACTION:
                tableName = "csh_transaction_account cta,csh_transaction_header h,csh_transaction_line l";
                whereConditionSB.append("h.transaction_header_id = ").append(headerId)
                                .append(" and cta.transaction_line_id = l.transaction_line_id")
                                .append(" and l.transaction_header_id = h.transaction_header_id");
                databaseLockProvider.lock(tableName, whereConditionSB.toString(), null);
                break;
            case GlAccountEntry.RULE_TYPE_CSH_WRITE_OFF:
                tableName = "csh_write_off_account  cwo,csh_write_off  o";
                whereConditionSB.append("o.write_off_id = ").append(headerId)
                                .append(" and o.write_off_id = cwo.write_off_id");
                databaseLockProvider.lock(tableName, whereConditionSB.toString(), null);
                break;
            case GlAccountEntry.RULE_TYPE_CSH_PMT_REQ:
                tableName = "csh_payment_requisition_hd h,csh_payment_requisition_ln l,csh_payment_req_account a";
                whereConditionSB.append("h.payment_requisition_header_id = ").append(headerId)
                                .append(" and h.payment_requisition_header_id = l.payment_requisition_header_id")
                                .append(" and l.payment_requisition_line_id = a.payment_requisition_line_id");
                databaseLockProvider.lock(tableName, whereConditionSB.toString(), null);
                break;
            case GlAccountEntry.RULE_TYPE_ACP_REQ:
                tableName = "acp_requisition_hd h,acp_requisition_ln l,acp_requisition_account a";
                whereConditionSB.append("h.requisition_hds_id = ").append(headerId)
                                .append(" and h.requisition_hds_id = l.requisition_hds_id")
                                .append(" and l.requisition_lns_id = a.requisition_lns_id");
                databaseLockProvider.lock(tableName, whereConditionSB.toString(), null);
                break;
            default:
                break;
        }

    }

    public void setPeriod(IRequest iRequest, GlAccountEntry glAccountEntry) {
        // 获取期间
        GldPeriod gldPeriod = new GldPeriod();
        gldPeriod = gldPeriodService.queryPeriodByAccEntityAndPeriodName(iRequest, glAccountEntry.getAccEntityId(),
                        glAccountEntry.getPeriodName());
        glAccountEntry.setPeriodNum(gldPeriod.getPeriodNum());
        glAccountEntry.setPeriodYear(gldPeriod.getPeriodYear());
    }

    public void insertGLAccountEntry(IRequest iRequest, String ruleType, List<GlAccountEntry> glAccountEntries) {
        if (glAccountEntries != null) {
            if (!glAccountEntries.isEmpty()) {
                for (GlAccountEntry glAccountEntry : glAccountEntries) {
                    setPeriod(iRequest, glAccountEntry);
                    // 插入分录表
                    glAccountEntry.setTransactionType(ruleType);
                    self().insertSelective(iRequest, glAccountEntry);
                }
            }
        }
    }

    public void headerCshTransaction(IRequest iRequest, String ruleType, Long headerId) {
        // 查询现金事物凭证行信息
        List<GlAccountEntry> glAccountEntries = new ArrayList<>();
        glAccountEntries = glAccountEntryMapper.queryCshTransactionAccount(headerId);
        insertGLAccountEntry(iRequest, ruleType, glAccountEntries);
    }

    public void headerExpReport(IRequest iRequest, String ruleType, Long headerId) {
        // 查询报销单凭证行信息
        List<GlAccountEntry> glAccountEntries = new ArrayList<>();
        glAccountEntries = glAccountEntryMapper.queryExpReportAccount(headerId);
        insertGLAccountEntry(iRequest, ruleType, glAccountEntries);
    }

    public void headerCshWriteOff(IRequest iRequest, String ruleType, Long headerId) {
        // 查询核销凭证行信息
        List<GlAccountEntry> glAccountEntries = new ArrayList<>();
        glAccountEntries = glAccountEntryMapper.queryCshWriteOffAccount(headerId);
        insertGLAccountEntry(iRequest, ruleType, glAccountEntries);
    }

    private void headerCshPmtReq(IRequest iRequest, String ruleType, Long headerId) {
        // 查询借款单凭证行信息
        List<GlAccountEntry> glAccountEntries = new ArrayList<>();
        glAccountEntries = glAccountEntryMapper.queryCshPmtReqAccount(headerId);
        insertGLAccountEntry(iRequest, ruleType, glAccountEntries);
    }

    private void headerAcpReq(IRequest iRequest, String ruleType, Long headerId) {
        // 查询付款申请单凭证行信息
        List<GlAccountEntry> glAccountEntries = new ArrayList<>();
        glAccountEntries = glAccountEntryMapper.queryAcpReqAccount(headerId);
        insertGLAccountEntry(iRequest, ruleType, glAccountEntries);
    }

    @Override
    public void headerGlAccountEntry(IRequest iRequest, String ruleType, Long headerId) {
        switch (ruleType) {
            case GlAccountEntry.RULE_TYPE_EXP_REPORT:
                // 锁表
                lockTable(iRequest, ruleType, headerId);
                // 插分录表
                headerExpReport(iRequest, ruleType, headerId);
                break;
            case GlAccountEntry.RULE_TYPE_CSH_TRANSACTION:
                // 锁表
                lockTable(iRequest, ruleType, headerId);
                // 插分录表
                headerCshTransaction(iRequest, ruleType, headerId);
                break;
            case GlAccountEntry.RULE_TYPE_CSH_WRITE_OFF:
                // 锁表
                lockTable(iRequest, ruleType, headerId);
                // 插分录表
                headerCshWriteOff(iRequest, ruleType, headerId);
                break;
            case GlAccountEntry.RULE_TYPE_CSH_PMT_REQ:
                // 锁表
                lockTable(iRequest, ruleType, headerId);
                // 插分录表
                headerCshPmtReq(iRequest, ruleType, headerId);
                break;
            case GlAccountEntry.RULE_TYPE_ACP_REQ:
                // 锁表
                lockTable(iRequest, ruleType, headerId);
                // 插分录表
                headerAcpReq(iRequest, ruleType, headerId);
                break;
            default:
                break;
        }
    }

    @Override
    public void updateImportedFlag(String importedFlag, Long transactionHeaderId, String transactionType) {
        glAccountEntryMapper.updateImportedFlag(importedFlag, transactionHeaderId, transactionType);
    }

}
