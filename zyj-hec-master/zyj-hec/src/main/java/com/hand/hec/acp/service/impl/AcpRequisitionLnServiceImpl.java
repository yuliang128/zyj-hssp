package com.hand.hec.acp.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.acp.dto.AcpRequisitionDtl;
import com.hand.hec.acp.dto.AcpRequisitionHd;
import com.hand.hec.acp.mapper.AcpRequisitionLnMapper;
import com.hand.hec.acp.service.IAcpRequisitionDtlService;
import com.hand.hec.acp.service.IAcpRequisitionHdService;
import com.hand.hec.csh.service.ICshDocPayAccEntityService;
import com.hand.hec.csh.service.ICshMoPayUsdRefFlowItService;
import com.hand.hec.gld.service.IGldAccountingEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.acp.dto.AcpRequisitionLn;
import com.hand.hec.acp.service.IAcpRequisitionLnService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class AcpRequisitionLnServiceImpl extends BaseServiceImpl<AcpRequisitionLn> implements IAcpRequisitionLnService {

    @Autowired
    private AcpRequisitionLnMapper mapper;

    @Autowired
    private ICshMoPayUsdRefFlowItService flowItService;

    @Autowired
    private IGldAccountingEntityService gldAccountingEntityService;

    @Autowired
    private IAcpRequisitionHdService headerService;

    @Autowired
    private ICshDocPayAccEntityService cshDocPayAccEntityService;

    @Autowired
    private IAcpRequisitionDtlService acpRequisitionDtlService;

    @Override
    public List<AcpRequisitionLn> queryByHeader(IRequest request, Long requisitionHdsId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryByHeader(requisitionHdsId);
    }

    @Override
    public List<AcpRequisitionLn> batchUpdate(IRequest request, List<AcpRequisitionLn> list,
                    AcpRequisitionHd acpRequisitionHd) {
        for (AcpRequisitionLn acpRequisitionLn : list) {
            switch (acpRequisitionLn.get__status()) {
                case DTOStatus.ADD:
                    acpRequisitionLn = this.insertAcpRequisitionLns(request, acpRequisitionLn, acpRequisitionHd);
                    break;
                case DTOStatus.UPDATE:
                    acpRequisitionLn = this.updateAcpRequisitionLns(request, acpRequisitionLn, acpRequisitionHd);
                    break;
                case DTOStatus.DELETE:
                    self().deleteByPrimaryKey(acpRequisitionLn);
                    break;
                default:
                    break;
            }

            List<AcpRequisitionDtl> reports = acpRequisitionLn.getReport();
            if (reports != null) {
                acpRequisitionDtlService.batchUpdate(request, reports, acpRequisitionLn,
                                AcpRequisitionLn.REF_DOCUMENT_TYPE_REPORT);
            }else{
                List<AcpRequisitionDtl> contracts = acpRequisitionLn.getContract();
                if (contracts != null) {
                    acpRequisitionDtlService.batchUpdate(request, reports, acpRequisitionLn,
                            AcpRequisitionLn.REF_DOCUMENT_TYPE_CONTRACT);
                }
            }

        }
        return list;
    }

    @Override
    public void updateStatusByHeaderId(IRequest request, AcpRequisitionLn acpRequisitionLn) {
        mapper.updateStatusByHeaderId(acpRequisitionLn);
    }

    @Override
    public BigDecimal getTotalAmount(Long requisitionHdsId) {
        return mapper.getTotalAmount(requisitionHdsId);
    }

    @Override
    public void deleteByHeaderId(Long requisitionHdsId) {
        mapper.deleteByHeaderId(requisitionHdsId);
    }

    @Override
    public List<AcpRequisitionLn> queryForCreateAccount(Long requisitionHdsId) {
        return mapper.queryForCreateAccount(requisitionHdsId);
    }

    private AcpRequisitionLn insertAcpRequisitionLns(IRequest request, AcpRequisitionLn acpRequisitionLn,
                    AcpRequisitionHd acpRequisitionHd) {
        // 1.0 赋值
        Long cashFlowItemId = flowItService.getCashFlowItemId(acpRequisitionLn.getPaymentUsedeId(),
                        acpRequisitionLn.getAccEntityId());

        // 2.0 录入
        acpRequisitionLn.setCashFlowItemId(cashFlowItemId);
        acpRequisitionLn.setRequisitionHdsId(acpRequisitionHd.getRequisitionHdsId());
        acpRequisitionLn.setCompanyId(acpRequisitionHd.getCompanyId());
        acpRequisitionLn.setAccEntityId(acpRequisitionHd.getAccEntityId());
        acpRequisitionLn = self().insertSelective(request, acpRequisitionLn);

        // 获取当前单据的付款核算主体ID
        Long payAccEntityId = gldAccountingEntityService.getPayAccEntityId(request.getMagOrgId(),
                        acpRequisitionLn.getCompanyId(), AcpRequisitionHd.ACP_REQUISITION,
                        acpRequisitionHd.getMoPayReqTypeId(), acpRequisitionLn.getPaymentMethodId(),
                        acpRequisitionLn.getPayeeCategory());
        payAccEntityId = payAccEntityId == null ? acpRequisitionLn.getAccEntityId() : payAccEntityId;
        // 3.0 录入付款主体信息
        cshDocPayAccEntityService.createPayAccEntity(request, AcpRequisitionHd.ACP_REQUISITION,
                        acpRequisitionHd.getCompanyId(), acpRequisitionHd.getMoPayReqTypeId(),
                        acpRequisitionHd.getRequisitionHdsId(), acpRequisitionLn.getRequisitionLnsId(), payAccEntityId,
                        acpRequisitionLn.getPaymentMethodId(), acpRequisitionLn.getPayeeCategory(), BaseConstants.YES,
                        BaseConstants.NO);



        return acpRequisitionLn;
    }

    private AcpRequisitionLn updateAcpRequisitionLns(IRequest request, AcpRequisitionLn acpRequisitionLn,
                    AcpRequisitionHd acpRequisitionHd) {

        if (headerService.updateStatusCheck(request, acpRequisitionHd.getRequisitionHdsId())
                        .equals(BaseConstants.YES)) {
            Long cashFlowItemId = flowItService.getCashFlowItemId(acpRequisitionLn.getPaymentUsedeId(),
                            acpRequisitionLn.getAccEntityId());

            acpRequisitionLn.setCashFlowItemId(cashFlowItemId);
            acpRequisitionLn.setRequisitionHdsId(acpRequisitionHd.getRequisitionHdsId());
            acpRequisitionLn.setCompanyId(acpRequisitionHd.getCompanyId());
            acpRequisitionLn.setAccEntityId(acpRequisitionHd.getAccEntityId());
            acpRequisitionLn = self().updateByPrimaryKey(request, acpRequisitionLn);

            cshDocPayAccEntityService.deletePayAccEntity(request, AcpRequisitionHd.ACP_REQUISITION,
                            acpRequisitionHd.getRequisitionHdsId(), acpRequisitionLn.getRequisitionLnsId());

            // 获取当前单据的付款核算主体ID
            Long payAccEntityId = gldAccountingEntityService.getPayAccEntityId(request.getMagOrgId(),
                            acpRequisitionLn.getCompanyId(), AcpRequisitionHd.ACP_REQUISITION,
                            acpRequisitionHd.getMoPayReqTypeId(), acpRequisitionLn.getPaymentMethodId(),
                            acpRequisitionLn.getPayeeCategory());

            payAccEntityId = payAccEntityId == null ? acpRequisitionLn.getAccEntityId() : payAccEntityId;
            // 3.0 录入付款主体信息
            cshDocPayAccEntityService.createPayAccEntity(request, AcpRequisitionHd.ACP_REQUISITION,
                            acpRequisitionHd.getCompanyId(), acpRequisitionHd.getMoPayReqTypeId(),
                            acpRequisitionHd.getRequisitionHdsId(), acpRequisitionLn.getRequisitionLnsId(),
                            payAccEntityId, acpRequisitionLn.getPaymentMethodId(), acpRequisitionLn.getPayeeCategory(),
                            BaseConstants.YES, BaseConstants.NO);
        }
        return acpRequisitionLn;

    }
}
