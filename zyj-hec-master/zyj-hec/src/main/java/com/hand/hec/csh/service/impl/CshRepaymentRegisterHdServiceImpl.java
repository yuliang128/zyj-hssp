package com.hand.hec.csh.service.impl;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.aurora.attachment.service.IFndAtmAttachmentService;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.base.service.HecUtil;
import com.hand.hec.csh.dto.CshPaymentRequisitionLn;
import com.hand.hec.csh.dto.CshRepaymentRegisterHd;
import com.hand.hec.csh.dto.CshRepaymentRegisterLn;
import com.hand.hec.csh.exception.CshRepaymentRegisterException;
import com.hand.hec.csh.mapper.CshRepaymentRegisterDistMapper;
import com.hand.hec.csh.mapper.CshRepaymentRegisterHdMapper;
import com.hand.hec.csh.mapper.CshRepaymentRegisterLnMapper;
import com.hand.hec.csh.service.ICshRepaymentRegisterHdService;
import com.hand.hec.csh.service.ICshRepaymentRegisterLnService;
import com.hand.hec.csh.service.ICshRepaymentRegisterService;
import com.hand.hec.exp.dto.ExpDocumentHistory;
import com.hand.hec.exp.service.IExpDocumentHistoryService;
import com.hand.hec.exp.service.IExpOrgPositionService;
import com.hand.hec.exp.service.IExpOrgUnitService;
import com.hand.hec.fnd.dto.FndDocInfo;
import com.hand.hec.fnd.service.IFndCodingRuleObjectService;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshRepaymentRegisterHdServiceImpl extends BaseServiceImpl<CshRepaymentRegisterHd> implements ICshRepaymentRegisterHdService {

    @Autowired
    private HecUtil hecUtil;

    @Autowired
    private CshRepaymentRegisterHdMapper mapper;

    @Autowired
    private CshRepaymentRegisterDistMapper distMapper;

    @Autowired
    private CshRepaymentRegisterLnMapper lineMapper;

    @Autowired
    private IExpOrgUnitService unitService;

    @Autowired
    private IExpOrgPositionService positionService;

    @Autowired
    private IFndCodingRuleObjectService codeRuleService;

    @Autowired
    private IExpDocumentHistoryService documentHistoryService;

    @Autowired
    private ICshRepaymentRegisterLnService repaymentLineService;

    @Autowired
    private ICshRepaymentRegisterService registerService;

    @Autowired
    private IFndAtmAttachmentService attachmentService;

    @Override
    public List<CshRepaymentRegisterHd> baseSelect(IRequest request, Map<String, Object> params, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        params.computeIfPresent("repaymentStatus", (k, v) -> "'" + StringUtils.join(Arrays.asList(v.toString().split(";")), "','") + "'");
        params.computeIfPresent("cshRepaymentDateScope",(k,v)->DateUtils.getDateScopeFrom((String) v));
        return mapper.baseSelect(params);
    }

    @Override
    public CshRepaymentRegisterHd queryRepaymentHd(IRequest request, Long registerHdsId) {
        return mapper.queryRepaymentHd(registerHdsId);
    }

    @Override
    public CshRepaymentRegisterHd queryHdWithIdIsNull(IRequest requestCxt, Long moRepaymentRegTypeId, Long employeeId, Long accEntityId) {
        Long positionId = positionService.getDefaultPosition(requestCxt, employeeId, requestCxt.getCompanyId()).getPositionId();
        Long unitId = unitService.getDefaultUnit(requestCxt, employeeId, requestCxt.getCompanyId()).getUnitId();
        return this.mapper.queryHdWithIdIsNull(positionId, unitId, employeeId, accEntityId, moRepaymentRegTypeId);
    }

    @Override
    public List<CshRepaymentRegisterHd> queryRepaymentAccounting(IRequest requestContext, Map<String, Object> params, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryRepaymentAccounting(params);
    }

    @Override
    public List<CshRepaymentRegisterHd> queryRepaymentCashier(IRequest request, Map condition, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryRepaymentCashier(condition);
    }

    @Override
    public Map<String, String> queryNumberAndType(IRequest request, Long registerHdsId) {
        return mapper.queryNumberAndType(registerHdsId);
    }

    @Override
    public List<CshRepaymentRegisterHd> batchUpdate(IRequest request, @StdWho List<CshRepaymentRegisterHd> list) {

        if (list.isEmpty()) {
            return Collections.emptyList();
        }

        //获取还款单据头信息
        CshRepaymentRegisterHd dto = list.get(0);
        if (dto.getRegisterHdsId() == null) {
            //第一次头保存
            checkAmount(dto);

            String registerNumber = codeRuleService.getRuleCode(FndDocInfo.DOC_CATEGORY_REPAYMENT_REGISTER
                    , dto.getMoRepaymentRegTypeId().toString()
                    , request.getMagOrgId()
                    , dto.getCompanyId()
                    , dto.getAccEntityId());
            dto.setRegisterNumber(registerNumber);

            Long timeMillis = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(timeMillis);
            dto.setRepaymentDateTz(timestamp);
            dto.setRepaymentDateLtz(timestamp);
            dto = this.insertSelective(request, dto);

            documentHistoryService.insertDocumentHistory(request, CshRepaymentRegisterHd.CSH_REPAYMENT_REGISTER
                    , dto.getRegisterHdsId()
                    , ExpDocumentHistory.STATUS_GENERATE
                    , dto.getEmployeeId()
                    , "还款申请单[" + dto.getRegisterNumber() + "]创建");
        } else {
            //修改头保存
            this.updateByPrimaryKeySelective(request, dto);
        }

        //行保存
        List<CshRepaymentRegisterLn> lines = dto.getLines();

        if (lines.isEmpty()) {
            return list;
        }

        if (lines.get(0).getRegisterHdsId() == null) {
            //回写头ID
            CshRepaymentRegisterHd header = dto;
            lines.forEach(r -> r.setRegisterHdsId(header.getRegisterHdsId()));
        }
        dto.setLines(repaymentLineService.batchUpdate(request, lines));
        return list;
    }

    @Override
    public CshRepaymentRegisterHd updateByPrimaryKeySelective(IRequest request, CshRepaymentRegisterHd record) {
        checkAmount(record);
        return super.updateByPrimaryKeySelective(request, record);
    }

    @Override
    public List<CshRepaymentRegisterHd> batchSubmit(IRequest request, Long registerHdsId) {
        //获取还款单据头信息
        CshRepaymentRegisterHd dto = mapper.selectByPrimaryKey(registerHdsId);
        registerService.lockRegister(registerHdsId);
        registerService.submitCheck(request, dto);
        dto.setRepaymentStatus(CshRepaymentRegisterHd.STATUS_SUBMITTED);
        dto = updateByPrimaryKeySelective(request, dto);
        documentHistoryService.insertDocumentHistory(request, CshRepaymentRegisterHd.CSH_REPAYMENT_REGISTER
                , dto.getRegisterHdsId()
                , ExpDocumentHistory.STATUS_SUBMIT
                , dto.getEmployeeId()
                , StringUtils.EMPTY);
        return Collections.singletonList(dto);
    }

    @Override
    public int delete(IRequest request, CshRepaymentRegisterHd header) {
        //删除分配行
        distMapper.deleteDist(header.getRegisterHdsId());
        //删除行
        lineMapper.delete(CshRepaymentRegisterLn.builder().registerHdsId(header.getRegisterHdsId()).build());
        //删除头
        int count = super.deleteByPrimaryKey(header);
        //删除附件
        attachmentService.remove(request, CshRepaymentRegisterHd.class.getAnnotation(Table.class).name(), header.getRegisterHdsId());
        //删除单据历史
        documentHistoryService.delteDocumentHistory(request, CshRepaymentRegisterHd.CSH_REPAYMENT_REGISTER, header.getRegisterHdsId());
        return count;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map> selectRelatedPaymentRequisition(IRequest request, Long registerHdsId) {
        List<Map> requisitions = mapper.selectRelatedPaymentRequisition(registerHdsId);
        return requisitions.parallelStream().peek(requisition -> {
            String codeName = hecUtil.getPayeeCodeName(request
                    , (String) requisition.get(CshPaymentRequisitionLn.FIELD_PAYEE_CATEGORY)
                    , (Long) requisition.get(CshPaymentRequisitionLn.FIELD_PAYEE_ID));
            requisition.put(CshPaymentRequisitionLn.FIELD_PAYEE_CODE, codeName);
        }).collect(Collectors.toList());
    }

    private void checkAmount(CshRepaymentRegisterHd dto) {
        if (dto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CshRepaymentRegisterException(CshRepaymentRegisterException.HD_NEGATIVE_AMOUNT_ERROR
                    , CshRepaymentRegisterException.HD_NEGATIVE_AMOUNT_ERROR
                    , null);
        }
    }

    @Override
    public List<CshRepaymentRegisterHd> queryForFinance(IRequest request, CshRepaymentRegisterHd dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryForFinance(dto);
    }

}