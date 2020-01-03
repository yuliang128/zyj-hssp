package com.hand.hec.csh.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.gld.dto.GldCurrency;
import com.hand.hap.gld.service.IGldCurrencyService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshMoPaymentReqType;
import com.hand.hec.csh.mapper.CshMoPaymentReqTypeMapper;
import com.hand.hec.csh.service.ICshMoPaymentReqTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshMoPaymentReqTypeServiceImpl extends BaseServiceImpl<CshMoPaymentReqType> implements ICshMoPaymentReqTypeService {
    @Autowired
    CshMoPaymentReqTypeMapper mapper;
    @Autowired
    ISysParameterService iSysParameterService;
    @Autowired
    IGldCurrencyService iGldCurrencyService;
    @Autowired
    IFndManagingOrganizationService iFndManagingOrganizationService;

    @Override
    public List<FndManagingOrganization> magOrgQuery(IRequest request) {
        //获取参数FND_ALL_ORGANIZATIONAL
        String allOrganizationalFlag = iSysParameterService.queryParamValueByCode("FND_ALL_ORGANIZATIONAL"
                                                                                  ,request.getUserId()
                                                                                  ,request.getRoleId()
                                                                                  ,null
                                                                                  ,null
                                                                                  ,null
                                                                                  ,null
                                                                                  ,null);
        Long magOrgId = request.getMagOrgId();
        return mapper.magOrgQuery(request,magOrgId,allOrganizationalFlag);
    }

    @Override
    public List<GldCurrency> currencyQuery(IRequest request) {
        GldCurrency gldCurrency = new GldCurrency();
        gldCurrency.setEnabledFlag("Y");
        Criteria criteria = new Criteria(gldCurrency);
        criteria.where(GldCurrency.FIELD_ENABLED_FLAG);
        return iGldCurrencyService.selectOptions(request,gldCurrency,criteria);
    }

    @Override
    public List<CshMoPaymentReqType> queryDftPayReqType(IRequest request) {
        return mapper.queryDftPayReqType();
    }

	@Override
	public List<CshMoPaymentReqType> queryTypeForPayReq(IRequest request, Long employeeId, String moPaymentReqTypeCode,
			String description) {
		return mapper.queryTypeForPayReq(employeeId, moPaymentReqTypeCode, description);
	}

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }
}