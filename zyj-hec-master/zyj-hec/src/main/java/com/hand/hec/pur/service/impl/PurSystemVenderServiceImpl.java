package com.hand.hec.pur.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.pur.dto.PurSystemVender;
import com.hand.hec.pur.dto.PurSystemVenderRefAe;
import com.hand.hec.pur.mapper.PurSystemVenderMapper;
import com.hand.hec.pur.mapper.PurSystemVenderRefAeMapper;
import com.hand.hec.pur.service.IPurSystemVenderRefAeService;
import com.hand.hec.pur.service.IPurSystemVenderService;

/**
 * <p>
 * 系统级供应商主数据逻辑实现
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/18 14:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PurSystemVenderServiceImpl extends BaseServiceImpl<PurSystemVender> implements IPurSystemVenderService {

    @Autowired
    private PurSystemVenderMapper mapper;

    @Autowired
    private PurSystemVenderRefAeMapper venderRefAeMapper;

    @Autowired
    private IPurSystemVenderRefAeService venderRefAeService;

    @Override
    public List<PurSystemVender> selectForSysLevel(IRequest request, PurSystemVender dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return this.mapper.queryForSystemLevel(dto);
    }

    @Override
    public List<PurSystemVender> selectForNonSysLevel(IRequest request, PurSystemVender dto, Long accEntityId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryByNonSysLevel(dto, accEntityId);
    }

    @Override
    public List<PurSystemVender> batchUpdateVenders(IRequest request, @StdWho List<PurSystemVender> list, boolean systemFlag) {
        for (PurSystemVender t : list) {
            switch (t.get__status()) {
                case DTOStatus.ADD:
                    this.insertVenderSelective(request, t,systemFlag);
                    break;
                case DTOStatus.UPDATE:
                    this.updateVenderByPrimaryKeySelective(request, t,systemFlag);
                    break;
                default:
                    break;
            }
        }
        return list;
    }


    private PurSystemVender insertVenderSelective(IRequest request, PurSystemVender record,boolean systemFlag) {
        PurSystemVender psv = super.insertSelective(request, record);
        if (!systemFlag){
            PurSystemVenderRefAe refAe = new PurSystemVenderRefAe();
            refAe.setAccEntityId(psv.getAccEntityId());
            refAe.setVenderId(psv.getVenderId());
            refAe.setEnabledFlag(psv.getEnabledFlag());
            venderRefAeService.insertSelective(request, refAe);
        }
        return psv;
    }

    private PurSystemVender updateVenderByPrimaryKeySelective(IRequest request, PurSystemVender record,boolean systemFlag) {
        PurSystemVender psv = super.updateByPrimaryKey(request, record);
        if (!systemFlag) {
            PurSystemVenderRefAe refAe = new PurSystemVenderRefAe();
            refAe.setVenderId(psv.getVenderId());
            refAe.setAccEntityId(psv.getAccEntityId());
            refAe = venderRefAeMapper.selectOne(refAe);
            refAe.setEnabledFlag(psv.getEnabledFlag());
            venderRefAeService.updateByPrimaryKey(request, refAe);
        }
        return psv;
    }
}