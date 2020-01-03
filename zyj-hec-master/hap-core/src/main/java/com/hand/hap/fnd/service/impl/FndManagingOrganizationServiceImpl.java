package com.hand.hap.fnd.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.mapper.FndManagingOrganizationMapper;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.sys.dto.SysParameter;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.service.impl.BaseServiceImpl;

/**
 * <p>
 * 管理组织ServiceImpl
 * </p>
 *
 * @author MouseZhou 2019/01/07 10:33
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FndManagingOrganizationServiceImpl extends BaseServiceImpl<FndManagingOrganization>
        implements IFndManagingOrganizationService {

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Autowired
    private FndManagingOrganizationMapper mapper;

    @Autowired
    private ISysParameterService sysParameterService;

    private static final String PARAM_FND_ALL_ORGANIZATIONAL = "FND_ALL_ORGANIZATIONAL";

    @Override
    public List<FndManagingOrganization> magOrgOption(IRequest request) {
        return mapper.magOrgOption();
    }

    @Override
    public FndManagingOrganization queryDefaultMagOrg(IRequest request) {
        return mapper.queryDefaultMagOrg();
    }

    @Override
    public List<FndManagingOrganization> queryMain(FndManagingOrganization fndManagingOrganization) {
        return mapper.queryMain(fndManagingOrganization);
    }

    @Override
    public List<FndManagingOrganization> selectMagOrg(IRequest request) {
        return mapper.selectMagOrg();
    }

    @Override
    public List<FndManagingOrganization> queryMain(FndManagingOrganization fndManagingOrganization, IRequest request, Integer page, Integer pageSize) {
        if (page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        return mapper.queryMain(fndManagingOrganization);
    }

    @Override
    public FndManagingOrganization defaultManageOrganizationQuery(IRequest request, Long companyId) {
        return mapper.defaultManageOrganizationQuery(companyId);
    }

    @Override
    public List<FndManagingOrganization> queryMagOrg(IRequest request) {
        String paramValue = sysParameterService.queryParamValueByCode(SysParameter.PARAM_FND_ALL_ORGANIZATIONAL, request.getUserId(),
                request.getRoleId(), null, null, null, null, null);
        return mapper.queryMagOrg(paramValue);
    }


    @Override
    public List<FndManagingOrganization> queryFndMagOra(IRequest request) {
        String parameterValue = sysParameterService
                .queryParamValueByCode(PARAM_FND_ALL_ORGANIZATIONAL, request.getUserId(), request.getRoleId(),
                        null, null, null, null, null);
        return mapper.queryFndManOrg(parameterValue, request.getMagOrgId());
    }

    @Override
    public List<FndManagingOrganization> queryByBgtOrgId(IRequest request, Long bgtOrgId) {
        return mapper.queryByBgtOrgId(bgtOrgId);
    }

    @Override
    public List<FndManagingOrganization> queryNoneByFndUomId(IRequest request,Long uomId,FndManagingOrganization dto,int page,int pageSize){
        PageHelper.startPage(page, pageSize);
        return mapper.queryNoneByFndUomId(uomId,dto);
    }

}
