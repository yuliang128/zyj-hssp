package com.hand.hec.fnd.service.impl;

import java.util.Collections;
import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.FndTaxTypeCode;
import com.hand.hec.fnd.mapper.FndTaxTypeCodeMapper;
import com.hand.hec.fnd.service.IFndTaxTypeCodeService;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.mapper.GldAccountingEntityMapper;

import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 税率定义逻辑实现类
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/28 14:46
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FndTaxTypeCodeServiceImpl extends BaseServiceImpl<FndTaxTypeCode> implements IFndTaxTypeCodeService{

    @Autowired
    private GldAccountingEntityMapper accEntityMapper;

    @Autowired
    private FndTaxTypeCodeMapper mapper;

    @Override
    public List<FndTaxTypeCode> queryByAccEntityId(IRequest request,Long companyId) {
        GldAccountingEntity defaultAccEntityId = accEntityMapper.getDefaultAccEntity(companyId);
        if (defaultAccEntityId == null) {
            return Collections.emptyList();
        }
        return mapper.queryByAccEntityId(defaultAccEntityId.getAccEntityId());
    }

    @Override
    /**
     * <p>获取税率信息下拉框</p>
     *
     * @return List<FndTaxTypeCode>
     * @throws RuntimeException exception description
     * @author yang.duan 2019/4/18 9:59
     **/
    public List<FndTaxTypeCode> queryTaxTypeWithholding(IRequest request){
        return mapper.queryTaxTypeWithholding();
    }
}