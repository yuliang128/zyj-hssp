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
import com.hand.hec.pur.dto.PurVenderAccount;
import com.hand.hec.pur.dto.PurVenderAccountRefAe;
import com.hand.hec.pur.exception.PurVenderAccountRefAeException;
import com.hand.hec.pur.mapper.PurVenderAccountMapper;
import com.hand.hec.pur.mapper.PurVenderAccountRefAeMapper;
import com.hand.hec.pur.service.IPurVenderAccountRefAeService;
import com.hand.hec.pur.service.IPurVenderAccountService;

/**
 * <p>
 * 供应商银行账户逻辑实现类
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/21 14:25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PurVenderAccountServiceImpl extends BaseServiceImpl<PurVenderAccount> implements IPurVenderAccountService {

    @Autowired
    private PurVenderAccountMapper mapper;

    @Autowired
    private PurVenderAccountRefAeMapper refAeMapper;

    @Autowired
    private IPurVenderAccountRefAeService refAeService;

    @Override
    public List<PurVenderAccount> queryByVenderId(IRequest request, Long venderId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return this.mapper.queryByVenderId(venderId);
    }

    @Override
    public List<PurVenderAccount> queryByVenderIdAndAccEntityId(IRequest request, Long venderId, Long accEntityId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return this.mapper.queryByVenderIdAndAccEntityId(venderId, accEntityId);
    }

    private void insertAccountAndAeAccount(IRequest request, PurVenderAccount record) throws PurVenderAccountRefAeException {
        PurVenderAccount pva = super.insertSelective(request, record);
        PurVenderAccountRefAe refAe = new PurVenderAccountRefAe();
        BeanUtils.copyProperties(pva, refAe);
        refAeService.batchInsert(request, refAe);

    }

    private void updateAccountAndRefAe(IRequest request, PurVenderAccount record) throws PurVenderAccountRefAeException {
        PurVenderAccount pva = super.updateByPrimaryKeySelective(request, record);
        PurVenderAccountRefAe refAe = new PurVenderAccountRefAe();
        //忽略修改，通过核算主体ID与账户ID查询对应的分配主体
        BeanUtils.copyProperties(pva, refAe, PurVenderAccountRefAe.FIELD_PRIMARY_FLAG, PurVenderAccountRefAe.FIELD_ENABLED_FLAG);
        refAe = refAeMapper.selectOne(refAe);
        //同步更新
        BeanUtils.copyProperties(pva, refAe, PurVenderAccountRefAe.FIELD_REF_ID);
        refAeService.updateVendorAccountAccEntity(request, refAe);
    }

    @Override
    public List<PurVenderAccount> updateAeAccount(IRequest request, @StdWho List<PurVenderAccount> list) throws PurVenderAccountRefAeException {
        for (PurVenderAccount t : list) {
            switch (t.get__status()) {
                case DTOStatus.ADD:
                    this.insertAccountAndAeAccount(request, t);
                    break;
                case DTOStatus.UPDATE:
                    this.updateAccountAndRefAe(request, t);
                    break;
                default:
                    break;
            }
        }
        return list;
    }
}