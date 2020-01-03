package com.hand.hec.pur.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.mapper.GldAccountingEntityMapper;
import com.hand.hec.pur.dto.PurVenderAccountRefAe;
import com.hand.hec.pur.exception.PurVenderAccountRefAeException;
import com.hand.hec.pur.mapper.PurVenderAccountMapper;
import com.hand.hec.pur.mapper.PurVenderAccountRefAeMapper;
import com.hand.hec.pur.service.IPurVenderAccountRefAeService;

/**
 * <p>
 * 供应商银行账户核算主体分配逻辑实现类
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/21 14:25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PurVenderAccountRefAeServiceImpl extends BaseServiceImpl<PurVenderAccountRefAe> implements IPurVenderAccountRefAeService {

    @Autowired
    private PurVenderAccountRefAeMapper mapper;

    @Autowired
    private GldAccountingEntityMapper accEntityMapper;

    @Override
    public List<PurVenderAccountRefAe> queryByAccountId(IRequest request, Long accountId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return this.mapper.queryByAccountId(accountId);
    }

    @Override
    public List<PurVenderAccountRefAe> queryAccEntityByVenderIdAndAccountId(IRequest request, Long accountId, Long venderId) {
        return mapper.queryAccEntityByVenderIdAndAccountId(accountId, venderId);
    }

    @Override
    public List<PurVenderAccountRefAe> assignVendorAccountAccEntity(IRequest request, @StdWho List<PurVenderAccountRefAe> list) throws PurVenderAccountRefAeException {
        for (PurVenderAccountRefAe t : list) {
            switch (t.get__status()) {
                case DTOStatus.ADD:
                    this.batchInsert(request, t);
                    break;
                case DTOStatus.UPDATE:
                    this.updateVendorAccountAccEntity(request, t);
                    break;
                case DTOStatus.DELETE:
                    super.deleteByPrimaryKey(t);
                    break;
                default:
                    break;
            }
        }
        return list;
    }

    @Override
    public PurVenderAccountRefAe batchInsert(IRequest request, PurVenderAccountRefAe dto) throws PurVenderAccountRefAeException {
        checkAccEntityHasBeenAssigned(dto);
        checkPrimaryFlag(dto);
        return insertSelective(request, dto);
    }

    @Override
    public PurVenderAccountRefAe updateVendorAccountAccEntity(IRequest requestCtx, PurVenderAccountRefAe dto) throws PurVenderAccountRefAeException {
        checkPrimaryFlag(dto);
        return self().updateByPrimaryKeySelective(requestCtx, dto);
    }

    /**
     * 判断主账号唯一性
     *
     * @param dto dto
     * @return void
     * @author jialin.xing@hand-china.com 2019-03-02 19:47
     */
    private void checkPrimaryFlag(PurVenderAccountRefAe dto) throws PurVenderAccountRefAeException {
        Long count = this.mapper.checkMainAccount(dto);
        if (count > 0) {
            GldAccountingEntity accEntity = accEntityMapper.selectByPrimaryKey(dto.getAccEntityId());
            String accEntityName = accEntity.getAccEntityName().isEmpty() ? StringUtils.EMPTY : accEntity.getAccEntityName();
            throw new PurVenderAccountRefAeException(PurVenderAccountRefAeException.PRIMARY_FLAG_CHECK_ERROR
                    , PurVenderAccountRefAeException.PRIMARY_FLAG_CHECK_ERROR
                    , new String[]{accEntityName});
        }
    }

    /**
     * 校验当前银行账号是否已分配该核算主体
     *
     * @param dto
     * @return void
     * @author jialin.xing@hand-china.com 2019-03-02 21:41
     */
    private void checkAccEntityHasBeenAssigned(PurVenderAccountRefAe dto) throws PurVenderAccountRefAeException {
        int count = mapper.selectCount(dto);
        if (count > 0) {
            throw new PurVenderAccountRefAeException(PurVenderAccountRefAeException.PUR_VEN_ACCOUNT_REF_AE_CODE_DUPLICATE
                    , PurVenderAccountRefAeException.PRIMARY_FLAG_CHECK_ERROR, null);
        }

    }
}