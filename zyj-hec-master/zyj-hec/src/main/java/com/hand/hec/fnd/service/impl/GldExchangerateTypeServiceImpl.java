package com.hand.hec.fnd.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.GldExchangerateType;
import com.hand.hec.fnd.exception.ExchangerateTypeException;
import com.hand.hec.fnd.mapper.GldExchangerateTpAsgnAeMapper;
import com.hand.hec.fnd.mapper.GldExchangerateTypeMapper;
import com.hand.hec.fnd.service.IGldExchangerateTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 汇率类型 ServiceImpl
 * </p>
 *
 * @author xingjialin 2019/01/07 10:35
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class GldExchangerateTypeServiceImpl extends BaseServiceImpl<GldExchangerateType> implements IGldExchangerateTypeService {

    @Autowired
    private GldExchangerateTpAsgnAeMapper aeMapper;

    @Autowired
    private GldExchangerateTypeMapper mapper;

    @Override
    public int check() throws ExchangerateTypeException {
       int count = this.aeMapper.check();
       if (count > 1) {
           throw new ExchangerateTypeException(ExchangerateTypeException.EXCHANGE_REF_ACC_ERROR,ExchangerateTypeException.EXCHANGE_REF_ACC_ERROR,null);
       }
       return count;
    }

    @Override
    public List<GldExchangerateType> expAuditSelectType(IRequest iRequest){
        return mapper.expAuditSelectType();
    }

    @Override
    public String getRateTypeByBgtOrg(Long bgtOrgId) {
        return mapper.getRateTypeByBgtOrg(bgtOrgId);
    }
}