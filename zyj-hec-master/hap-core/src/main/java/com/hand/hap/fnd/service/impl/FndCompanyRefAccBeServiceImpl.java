package com.hand.hap.fnd.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndCompanyRefAccBe;
import com.hand.hap.fnd.mapper.FndCompanyRefAccBeMapper;
import com.hand.hap.fnd.service.IFndCompanyRefAccBeService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 管理公司关联核算主体关联预算实体ServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 11:08
 * @author xiuxian.wu 2019/01/25 14:57
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FndCompanyRefAccBeServiceImpl extends BaseServiceImpl<FndCompanyRefAccBe> implements IFndCompanyRefAccBeService {
    @Autowired
    FndCompanyRefAccBeMapper mapper;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public List<FndCompanyRefAccBe> queryBgtEntity(FndCompanyRefAccBe dto, IRequest request, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryBgtEntity(dto);
    }

    @Override
    public FndCompanyRefAccBe queryFndCompanyRefAccBe(FndCompanyRefAccBe dto) {
        return mapper.queryFndCompanyRefAccBe(dto);
    }

    @Override
    public Long setDefaultFlagN(Long accRefId) {
        return mapper.setDefaultFlagN(accRefId);
    }

    @Override
    public List<FndCompanyRefAccBe> queryCompanyAccRefBgtByAccRefId(IRequest request, FndCompanyRefAccBe condition, int page, int pageSize) {
        PageHelper.startPage(page,pageSize );
        return mapper.queryCompanyAccRefBgtByAccRefId(condition);
    }

    /**
     * <p>
     * 根据公司+核算主体获取默认预算实体
     * <p/>
     *
     * @param request
     * @param companyId 公司ID
     * @param accEntityId 核算主体ID
     * @return 预算实体信息
     * @author yang.duan 2019/3/21 10:51
     */
    @Override
    public FndCompanyRefAccBe getBgtEntityByComAndAcc(IRequest request, Long companyId, Long accEntityId) {
        return mapper.getBgtEntityByComAndAcc(companyId, accEntityId);
    }
}