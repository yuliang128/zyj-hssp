package com.hand.hec.gld.service.impl;


import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.gld.dto.GldSetOfBook;
import com.hand.hec.gld.exception.GldSetOfBookException;
import com.hand.hec.gld.mapper.GldSetOfBookMapper;
import com.hand.hec.gld.service.IGldSetOfBookService;
import com.hand.hap.sys.dto.SysParameter;
import com.hand.hap.sys.service.ISysParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 账套定义serviceImpl
 * </p>
 *
 * @author wuxiuxian 2019-01-07
 * @author jialin.xing 2019-01-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GldSetOfBookServiceImpl extends BaseServiceImpl<GldSetOfBook> implements IGldSetOfBookService {

    @Autowired
    private GldSetOfBookMapper mapper;
    @Autowired
    private IGldSetOfBookService service;
    @Autowired
    private ISysParameterService parameterService;

    @Override
    public Boolean judgeUsedByAccounting(Long setBooksId) {
        Long data = mapper.judgeUsedByAccounting(setBooksId);
        if (data > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean judgeUsedByOrganization(Long setBooksId) {
        Long data = mapper.judgeUsedByOrganization(setBooksId);
        if (data > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<GldSetOfBook> querySetOfBookByParam(IRequest request) {
        String paramValue = this.parameterService.queryParamValueByCode(SysParameter.PARAM_FND_ALL_ORGANIZATIONAL, null,
                        null, null, null, null, null, null);
        return this.mapper.querySetOfBookByParam(request.getMagOrgId(), paramValue);
    }


    @Override
    public List<GldSetOfBook> selectDefaultSobByMagOrgId(IRequest request) {
        return mapper.selectDefaultSobByMagOrgId();
    }


    @Override
    public List<GldSetOfBook> querySetOfBookOptionsByParamValue(IRequest request, String paramValue) {
        String parameterValue = parameterService.queryParamValueByCode(paramValue, request.getUserId(),
                        request.getRoleId(), request.getCompanyId(), request.getMagOrgId(), null, null, null);

        if (null == parameterValue) {
            parameterValue = "N";
        }
        return mapper.querySetOfBookOptionsByParamValue(request.getMagOrgId(), parameterValue);

    }


    @Override
    public List<GldSetOfBook> queryForPeriod(IRequest requestContext, GldSetOfBook dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);

        return mapper.queryForPeriod(dto);
    }

    @Override
    public List<GldSetOfBook> batchSubmitGldSetOfBook(IRequest requestContext, List<GldSetOfBook> dto)
                    throws GldSetOfBookException {
        for (GldSetOfBook gldSetOfBook : dto) {
            if ("N".equals(gldSetOfBook.getEnabledFlag()) && "update".equals(gldSetOfBook.get__status())) {
                if (service.judgeUsedByAccounting(gldSetOfBook.getSetOfBooksId())) {
                    throw new GldSetOfBookException(GldSetOfBookException.FAILURE_ACCOUNT_SET,
                                    GldSetOfBookException.FAILURE_ACCOUNT_SET, null);
                } else if (service.judgeUsedByOrganization(gldSetOfBook.getSetOfBooksId())) {
                    throw new GldSetOfBookException(GldSetOfBookException.CANNOT_BE_INVALIDATED,
                                    GldSetOfBookException.CANNOT_BE_INVALIDATED, null);
                }
            }
        }
        return service.batchUpdate(requestContext, dto);
    }

    @Override
    public List<GldSetOfBook> queryByMagId(IRequest iRequest, Long magOrgId) {
        return mapper.queryByMagId(magOrgId);
    }

    /**
     * 获取核算主体默认账套
     *
     * @author Tagin
     * @date 2019-03-27 19:43
     * @param accEntityId 核算主体
     * @return com.hand.hec.gld.dto.GldSetOfBook
     * @version 1.0
     **/
    @Override
    public GldSetOfBook queryDftSetOffBookByAe(IRequest iRequest, Long accEntityId) {
        return mapper.queryDftSetOffBookByAe(accEntityId).get(0);
    }
}
