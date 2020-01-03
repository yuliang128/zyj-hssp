package com.hand.hap.fnd.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndCompanyRefBgtEntity;
import com.hand.hap.fnd.mapper.FndCompanyRefBgtEntityMapper;
import com.hand.hap.fnd.service.IFndCompanyRefBgtEntityService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 管理公司关联预算实体ServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 11:08
 * @author xiuxian.wu 2019/01/25 15:08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FndCompanyRefBgtEntityServiceImpl extends BaseServiceImpl<FndCompanyRefBgtEntity> implements IFndCompanyRefBgtEntityService {
    @Autowired
    FndCompanyRefBgtEntityMapper mapper;

    @Override
    public FndCompanyRefBgtEntity queryFndCompanyRefBgtEntity(FndCompanyRefBgtEntity dto) {
        return mapper.queryFndCompanyRefBgtEntity(dto);
    }

    @Override
    public Long setDefaultFlagN(Long companyId) {
        return mapper.setDefaultFlagN(companyId);
    }

    @Override
    public List<FndCompanyRefBgtEntity> queryBgtEntity(FndCompanyRefBgtEntity dto, IRequest request, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryBgtEntity(dto);
    }

    @Override
    public List<FndCompanyRefBgtEntity> queryCompanyRefBgtEntityByCompanyId(IRequest request, FndCompanyRefBgtEntity condition, int page, int pageSize) {
        PageHelper.startPage(page,pageSize );
        return mapper.queryCompanyRefBgtEntityByCompanyId(condition);
    }

    /**
     * <p>获取公司默认的预算实体<p/>
     * @param request
     * @param companyId 公司ID
     * @return 预算实体信息
     * @author yang.duan 2019/3/21 11:24
     */
    @Override
    public FndCompanyRefBgtEntity getDftBgtEntity(IRequest request,Long companyId){
        return mapper.getDftBgtEntity(companyId);
    }
}