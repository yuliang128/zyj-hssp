package com.hand.hec.bgt.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtOrgRefDetail;
import com.hand.hec.bgt.dto.BgtOrganization;
import com.hand.hec.bgt.mapper.BgtOrganizationMapper;
import com.hand.hec.bgt.service.IBgtOrgRefDetailService;
import com.hand.hec.bgt.service.IBgtOrganizationService;

/**
 * <p>
 * 预算组织ServiceImpl
 * </p>
 *
 * @author mouse 2019/01/07 16:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtOrganizationServiceImpl extends BaseServiceImpl<BgtOrganization> implements IBgtOrganizationService {

    private static final String PARAM_FND_ALL_ORGANIZATIONAL = "FND_ALL_ORGANIZATIONAL";

    @Autowired
    BgtOrganizationMapper bgtOrganizationMapper;

    @Autowired
    IBgtOrgRefDetailService bgtOrgRefService;

    @Autowired
    private ISysParameterService sysParameterService;

    @Override
    public List<BgtOrganization> queryDefaultBgtOrganization(IRequest request) {
        return bgtOrganizationMapper.queryDefaultBgtOrganization(request.getMagOrgId());
    }

    @Override
    public List<BgtOrganization> queryBgtOrganizationOptions(IRequest request) {
        String parameterValue = sysParameterService
                .queryParamValueByCode(PARAM_FND_ALL_ORGANIZATIONAL, request.getUserId(), request.getRoleId(),
                        request.getCompanyId(), request.getMagOrgId(), null, null, null);

        if (null == parameterValue) {
            parameterValue = "N";
        }
        return bgtOrganizationMapper.queryBgtOrganizationOptions(request.getMagOrgId(), parameterValue);
    }

    @Override
    public List<BgtOrganization> queryBgtOrgAll(String bgtOrgCode, String description, IRequest requestContext,Integer page,Integer pageSize) {
        if(page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        return bgtOrganizationMapper.queryBgtOrgAll(bgtOrgCode, description);
    }

    @Override
    public List<BgtOrganization> bgtOrgOption(IRequest request, Long magOrgId) {
        return bgtOrganizationMapper.bgtOrgOption(magOrgId);
    }

    @Override
    public BgtOrganization getBgtOrgByMagOrgId(IRequest request, Long magOrgId) {
        return bgtOrganizationMapper.getBgtOrgByMagOrgId(magOrgId);
    }

    @Override
    public List<BgtOrganization> queryDefaultBudgetOrganizationByMagOrgId(IRequest requestContext) {
        return bgtOrganizationMapper.queryDefaultBudgetOrganizationByMagOrgId();
    }

    @Override
    public List<BgtOrganization> queryAll(IRequest request, BgtOrganization condition) {
        List<BgtOrganization> list = bgtOrganizationMapper.select(condition);
        for (int i = 0; i < list.size(); i++) {
            BgtOrganization t = list.get(i);
            t.setBgtOrgCodeName(t.getBgtOrgCode() + "-" + t.getDescription());
        }
        return list;
    }

    @Override
    public List<BgtOrganization> getBgtOrgOptionForBgtQuery(IRequest request) {
        return bgtOrganizationMapper.getBgtOrgOptionForBgtQuery();
    }

    @Override
    public List<BgtOrganization> queryBgtOrgCurrentMagOrg(IRequest request) {
        return bgtOrganizationMapper.queryBgtOrgCurrentMagOrg();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<BgtOrganization> batchUpdate(IRequest request, @StdWho List<BgtOrganization> list) {
        IBaseService<BgtOrganization> self = ((IBaseService<BgtOrganization>) AopContext.currentProxy());
        for (BgtOrganization bgtOrganization : list) {
            switch ((bgtOrganization).get__status()) {
                case DTOStatus.ADD:
                    self.insertSelective(request, bgtOrganization);
                    break;
                case DTOStatus.UPDATE:
                    // 如果更新的字段包含来源类型，则需要将原先与该预算组织绑定的来源类型切断(只能绑定一方),然后再做更新
                    String newSource = bgtOrganization.getSourceTypeCode();
                    String oldSource = bgtOrganizationMapper.selectByPrimaryKey(bgtOrganization.getBgtOrgId())
                            .getSourceTypeCode();
                    if (!newSource.equals(oldSource)) {
                        // 查询出该预算组织对应的来源类型明细列表List<BgtOrgRefDetail>
                        BgtOrgRefDetail bgtOrgRefDetail = BgtOrgRefDetail.builder()
                                .bgtOrgId(bgtOrganization.getBgtOrgId()).build();
                        Criteria criteria = new Criteria(bgtOrgRefDetail);
                        List<BgtOrgRefDetail> detailList = bgtOrgRefService
                                .selectOptions(request, bgtOrgRefDetail, criteria);
                        // 逐一删除
                        for (BgtOrgRefDetail detail : detailList) {
                            bgtOrgRefService.deleteByPrimaryKey(detail);
                        }
                    }
                    if (useSelectiveUpdate()) {
                        self.updateByPrimaryKeySelective(request, bgtOrganization);
                    } else {
                        self.updateByPrimaryKey(request, bgtOrganization);
                    }
                    break;
                case DTOStatus.DELETE:
                    self.deleteByPrimaryKey(bgtOrganization);
                    break;
                default:
                    break;
            }
        }
        return list;
    }
}
