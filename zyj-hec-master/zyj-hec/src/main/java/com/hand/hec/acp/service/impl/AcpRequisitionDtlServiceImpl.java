package com.hand.hec.acp.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.acp.dto.AcpRequisitionLn;
import com.hand.hec.acp.mapper.AcpRequisitionDtlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.acp.dto.AcpRequisitionDtl;
import com.hand.hec.acp.service.IAcpRequisitionDtlService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class AcpRequisitionDtlServiceImpl extends BaseServiceImpl<AcpRequisitionDtl>
                implements IAcpRequisitionDtlService {
    @Autowired
    private AcpRequisitionDtlMapper mapper;

    @Override
    public List<AcpRequisitionDtl> queryByLineId(IRequest request, AcpRequisitionDtl acpRequisitionDtl) {
        return mapper.queryByLineId(acpRequisitionDtl);
    }

    @Override
    public List<AcpRequisitionDtl> batchUpdate(IRequest request, List<AcpRequisitionDtl> list, AcpRequisitionLn line,
                    String refType) {
        for (AcpRequisitionDtl acpRequisitionDtl : list) {
            //acpRequisitionDtl.setRefDocumentType(refType);
            acpRequisitionDtl.setRequisitionLnsId(line.getRequisitionLnsId());
            switch (acpRequisitionDtl.get__status()) {
                case DTOStatus.ADD:
                    self().insertSelective(request, acpRequisitionDtl);
                    break;
                case DTOStatus.UPDATE:
                    self().updateByPrimaryKey(request, acpRequisitionDtl);
                    break;
                case DTOStatus.DELETE:
                    self().deleteByPrimaryKey(acpRequisitionDtl);
                    break;
                default:
                    break;
            }

        }
        return list;
    }

    @Override
    public BigDecimal getTotalAmount(Long requisitionLnsId) {
        return mapper.getTotalAmount(requisitionLnsId);
    }

    @Override
    public List<AcpRequisitionDtl> queryByHeaderId(Long requisitionHdsId) {
        return mapper.queryByHeaderId(requisitionHdsId);
    }

    @Override
    public BigDecimal getOtherAmount(AcpRequisitionDtl acpRequisitionDtl) {
        return mapper.getOtherAmount(acpRequisitionDtl);
    }

    @Override
    public List<AcpRequisitionDtl> queryFromReport(IRequest request, AcpRequisitionDtl acpRequisitionDtl, int page,
                    int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryFromReport(acpRequisitionDtl);
    }

    /**
     * 获取付款申请单明细-根据付款申请单行 ID
     *
     * @author Tagin
     * @date 2019-05-06 15:58
     * @param requisitionLnsId 付款申请单行 ID
     * @return java.util.List<com.hand.hec.acp.dto.AcpRequisitionDtl>
     * @version 1.0
     **/
    @Override
    public List<AcpRequisitionDtl> queryAllByLnsId(Long requisitionLnsId) {
        return mapper.queryAllByLnsId(requisitionLnsId);
    }
}
