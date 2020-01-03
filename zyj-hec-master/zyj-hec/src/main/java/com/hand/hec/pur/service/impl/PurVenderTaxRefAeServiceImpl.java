package com.hand.hec.pur.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.pur.dto.PurVenderTaxRefAe;
import com.hand.hec.pur.exception.PurVenderTaxRefAeException;
import com.hand.hec.pur.mapper.PurVenderTaxRefAeMapper;
import com.hand.hec.pur.service.IPurVenderTaxRefAeService;

/**
 * <p>
 *
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/26 14:27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PurVenderTaxRefAeServiceImpl extends BaseServiceImpl<PurVenderTaxRefAe> implements IPurVenderTaxRefAeService {

    @Autowired
    private PurVenderTaxRefAeMapper mapper;

    @Override
    public List<PurVenderTaxRefAe> queryByTaxId(IRequest request, Long taxId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryByTaxId(taxId);
    }

    @Override
    public List<PurVenderTaxRefAe> queryAccEntityByVenderIdAndTaxId(IRequest request, Long venderId, Long taxId) {
        return mapper.queryAccEntityByVenderIdAndTaxId(venderId, taxId);
    }

    @Override
    public List<PurVenderTaxRefAe> batchAssign(IRequest request, @StdWho List<PurVenderTaxRefAe> list) throws PurVenderTaxRefAeException {
        for (PurVenderTaxRefAe t : list) {
            this.batchInsert(request, t);
        }
        return list;
    }

    @Override
    public PurVenderTaxRefAe batchInsert(IRequest request, PurVenderTaxRefAe dto) throws PurVenderTaxRefAeException {
        checkAccEntityHasBeenAssigned(dto);
        return self().insertSelective(request, dto);
    }

    /**
     * 校验当前纳税人类型是否已分配该核算主体
     *
     * @param dto dto
     * @return void
     * @throws PurVenderTaxRefAeException
     * @author jialin.xing@hand-china.com 2019-03-02 22:21
     */
    private void checkAccEntityHasBeenAssigned(PurVenderTaxRefAe dto) throws PurVenderTaxRefAeException {
        int count = mapper.selectCount(dto);
        if (count > 0) {
            throw new PurVenderTaxRefAeException(PurVenderTaxRefAeException.PUR_VENDER_TAX_REF_AE_CODE_DUPLICATE,
                    PurVenderTaxRefAeException.PUR_VENDER_TAX_REF_AE_CODE_DUPLICATE, null);
        }
    }


}