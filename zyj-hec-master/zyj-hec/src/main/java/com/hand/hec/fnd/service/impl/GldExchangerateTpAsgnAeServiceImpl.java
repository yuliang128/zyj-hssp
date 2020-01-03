package com.hand.hec.fnd.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.GldExchangerateTpAsgnAe;
import com.hand.hec.fnd.mapper.GldExchangerateTpAsgnAeMapper;
import com.hand.hec.fnd.service.IGldExchangerateTpAsgnAeService;

/**
 * <p>
 * 汇率类型分配核算主体ServiceImpl
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/01/08 16:09
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class GldExchangerateTpAsgnAeServiceImpl extends BaseServiceImpl<GldExchangerateTpAsgnAe> implements IGldExchangerateTpAsgnAeService {

    @Autowired
    private GldExchangerateTpAsgnAeMapper mapper;

    @Override
    public List<GldExchangerateTpAsgnAe> select(IRequest request, GldExchangerateTpAsgnAe condition, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return mapper.selectByTypeId(condition);
    }

    @Override
    public List<GldExchangerateTpAsgnAe> queryUnallocatedAccEntity(IRequest request, Long typeId) {
        return mapper.queryUnallocatedAccEntity(typeId);
    }
}