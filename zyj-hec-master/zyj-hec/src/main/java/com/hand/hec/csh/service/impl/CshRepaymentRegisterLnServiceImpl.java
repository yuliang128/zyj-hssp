package com.hand.hec.csh.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.base.service.HecUtil;
import com.hand.hec.csh.dto.CshRepaymentRegisterHd;
import com.hand.hec.csh.dto.CshRepaymentRegisterLn;
import com.hand.hec.csh.exception.CshRepaymentRegisterException;
import com.hand.hec.csh.mapper.CshRepaymentRegisterHdMapper;
import com.hand.hec.csh.mapper.CshRepaymentRegisterLnMapper;
import com.hand.hec.csh.service.ICshRepaymentRegisterHdService;
import com.hand.hec.csh.service.ICshRepaymentRegisterLnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshRepaymentRegisterLnServiceImpl extends BaseServiceImpl<CshRepaymentRegisterLn> implements ICshRepaymentRegisterLnService {

    @Autowired
    private HecUtil hecUtil;

    @Autowired
    private CshRepaymentRegisterLnMapper mapper;

    @Autowired
    private CshRepaymentRegisterHdMapper headerMapper;

    @Autowired
    private ICshRepaymentRegisterHdService headerService;

    @Override
    public List<CshRepaymentRegisterLn> queryPaymentRequisition(IRequest request, String currencyCode, Long employeeId,String requisitionNumber) {
        return mapper.queryPaymentRequisition(currencyCode, employeeId,requisitionNumber);
    }

    @Override
    public List<CshRepaymentRegisterLn> queryLinesByHeaderId(IRequest request, Long registerHdsId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<CshRepaymentRegisterLn> lines = mapper.queryLinesByHeaderId(registerHdsId);
        return lines.parallelStream()
                .peek(line -> line.setPayeeName(hecUtil.getPayeeName(request, line.getPayeeCategory(), line.getPayeeId())))
                .collect(Collectors.toList());
    }

    /**
     * this method assume the object in list is BaseDTO.
     *
     * @param request requestContext
     * @param list    dto list
     * @return the list
     */
    @Override
    public List<CshRepaymentRegisterLn> batchUpdate(IRequest request, @StdWho List<CshRepaymentRegisterLn> list) {
        for (CshRepaymentRegisterLn t : list) {
            switch (t.get__status()) {
                case DTOStatus.ADD:
                    insertSelective(request, t);
                    break;
                case DTOStatus.UPDATE:
                    if (useSelectiveUpdate()) {
                        self().updateByPrimaryKeySelective(request, t);
                    } else {
                        self().updateByPrimaryKey(request, t);
                    }
                    break;
                case DTOStatus.DELETE:
                    self().deleteByPrimaryKey(t);
                    break;
                default:
                    break;
            }
        }
        return list;
    }

    @Override
    public CshRepaymentRegisterLn insertSelective(IRequest request, CshRepaymentRegisterLn record) {
        checkAmount(record);
        return super.insertSelective(request, record);
    }

    @Override
    public CshRepaymentRegisterLn updateByPrimaryKeySelective(IRequest request, CshRepaymentRegisterLn record) {
        checkAmount(record);
        record = super.updateByPrimaryKeySelective(request, record);
        rewriteAmount(record);
        return record;
    }

    @Override
    public int deleteByPrimaryKey(CshRepaymentRegisterLn record) {
        int count = super.deleteByPrimaryKey(record);
        rewriteAmount(record);
        return count;
    }

    private void rewriteAmount(CshRepaymentRegisterLn record) {
        CshRepaymentRegisterHd header = headerMapper.selectByPrimaryKey(record.getRegisterHdsId());
        BigDecimal amount = mapper.select(CshRepaymentRegisterLn.builder().registerHdsId(record.getRegisterHdsId()).build())
                .parallelStream()
                .map(CshRepaymentRegisterLn::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        header.setAmount(amount);
        headerMapper.updateByPrimaryKeySelective(header);
    }

    private void checkAmount(CshRepaymentRegisterLn record) {
        if (record.getAmount() == null || record.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CshRepaymentRegisterException(CshRepaymentRegisterException.LN_NEGATIVE_AMOUNT_ERROR
                    , CshRepaymentRegisterException.LN_NEGATIVE_AMOUNT_ERROR, new Long[]{record.getLineNumber()});
        }
    }
}