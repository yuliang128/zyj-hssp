package com.hand.hec.pur.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.pur.dto.PurVenderTax;
import com.hand.hec.pur.dto.PurVenderTaxRefAe;
import com.hand.hec.pur.exception.PurVenderTaxRefAeException;
import com.hand.hec.pur.mapper.PurVenderTaxMapper;
import com.hand.hec.pur.mapper.PurVenderTaxRefAeMapper;
import com.hand.hec.pur.service.IPurVenderTaxRefAeService;
import com.hand.hec.pur.service.IPurVenderTaxService;

/**
 * <p>
 * 供应商税务信息业务逻辑实现类
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/25 20:03
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PurVenderTaxServiceImpl extends BaseServiceImpl<PurVenderTax> implements IPurVenderTaxService {

    @Autowired
    private PurVenderTaxMapper mapper;

    @Autowired
    private PurVenderTaxRefAeMapper refAeMapper;

    @Autowired
    private IPurVenderTaxRefAeService refAeService;

    @Override
    public List<PurVenderTax> queryByVenderIdAndAccEntityId(IRequest request, Long accEntityId, Long venderId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryByVenderIdAndAccEntityId(accEntityId, venderId);
    }

    @Override
    public List<PurVenderTax> batchUpdateTax(IRequest request, @StdWho List<PurVenderTax> list) throws PurVenderTaxRefAeException {
        for (PurVenderTax t : list) {
            switch (t.get__status()) {
                case DTOStatus.ADD:
                    this.insertTaxAndRefAeTax(request, t);
                    break;
                case DTOStatus.UPDATE:
                    this.updateTaxAndRefAeTax(request, t);
                    break;
                default:
                    break;
            }
        }
        return list;
    }

    private PurVenderTax updateTaxAndRefAeTax(IRequest request, PurVenderTax record) {
        PurVenderTax pvt = super.updateByPrimaryKeySelective(request, record);
        PurVenderTaxRefAe refAe = new PurVenderTaxRefAe();
        BeanUtils.copyProperties(pvt, refAe, PurVenderTaxRefAe.FIELD_ENABLED_FLAG);
        refAe = refAeMapper.selectOne(refAe);
        refAe.setEnabledFlag(record.getEnabledFlag());
        refAeService.updateByPrimaryKeySelective(request, refAe);
        return pvt;
    }

    private PurVenderTax insertTaxAndRefAeTax(IRequest request, PurVenderTax record) throws PurVenderTaxRefAeException {
        PurVenderTax pvt = super.insertSelective(request, record);
        PurVenderTaxRefAe refAe = new PurVenderTaxRefAe();
        BeanUtils.copyProperties(pvt, refAe);
        refAeService.batchInsert(request, refAe);
        return pvt;
    }


}