package com.hand.hap.fnd.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndCompanyRefAccBe;
import com.hand.hap.fnd.dto.FndCompanyRefAccEntity;
import com.hand.hap.fnd.service.IFndCompanyRefAccBeService;
import com.hand.hap.fnd.mapper.FndCompanyRefAccEntityMapper;
import com.hand.hap.fnd.service.IFndCompanyRefAccEntityService;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;


/**
 * <p>
 * 管理公司关联核算主体ServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 11:08
 * @author xiuxian.wu 2019/01/25 15:08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FndCompanyRefAccEntityServiceImpl extends BaseServiceImpl<FndCompanyRefAccEntity> implements IFndCompanyRefAccEntityService {
    @Autowired
    FndCompanyRefAccEntityMapper mapper;
    @Autowired
    private IFndCompanyRefAccBeService companyRefAccBeService;

    @Override
    public FndCompanyRefAccEntity queryFndCompanyRefAccEntity(FndCompanyRefAccEntity dto) {
        return mapper.queryFndCompanyRefAccEntity(dto);
    }

    @Override
    public Long setDefaultFlagN(Long companyId) {
        return mapper.setDefaultFlagN(companyId);
    }

    @Override
    public List<FndCompanyRefAccEntity> queryAccEntity(FndCompanyRefAccEntity dto, IRequest request, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryAccEntity(dto);
    }

    @Override
    public List<FndCompanyRefAccEntity> queryAllFndCompanyRefAccEntity(IRequest requestContext, FndCompanyRefAccEntity dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryAllFndCompanyRefAccEntity(dto);
    }

    @Override
    public List<FndCompanyRefAccEntity> submitCompanyRefAccEntity(IRequest requestContext, List<FndCompanyRefAccEntity> dto) {
        List<FndCompanyRefAccBe> bes = new ArrayList<>();
        List<FndCompanyRefAccEntity> companyRefAccEntities = batchUpdate(requestContext, dto);
        for (FndCompanyRefAccEntity companyRefAccEntity : companyRefAccEntities) {
            if (companyRefAccEntity.getBgtEntityId() != null) {
                FndCompanyRefAccBe be = new FndCompanyRefAccBe(companyRefAccEntity.getRefId(), companyRefAccEntity.getBgtEntityId(), "Y", "Y");
                be.set__id(companyRefAccEntity.get__id());
                be.set_token(companyRefAccEntity.get_token());
                be.set__status(DTOStatus.ADD);
                if (DTOStatus.UPDATE.equals(companyRefAccEntity.get__status())) {
                    FndCompanyRefAccBe b = companyRefAccBeService.queryFndCompanyRefAccBe(be);
                    if (b != null) {
                        be.setRefId(b.getRefId());
                        be.set__status(companyRefAccEntity.get__status());
                    }
                    companyRefAccBeService.setDefaultFlagN(companyRefAccEntity.getRefId());
                }
                bes.add(be);
            }
        }
        companyRefAccBeService.batchUpdate(requestContext, bes);
        return companyRefAccEntities;
    }


    /**
     * 公司Id查询默认核算主体
     *
     * @return 核算主体信息
     * @author dingwei.ma@hand-china.com 2019/1/25 14:42
     */
    @Override
    public Long queryDftAccByComId(IRequest request) {
        return mapper.queryDftAccByComId();
    }
}