package com.hand.hap.sys.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.sys.dto.SysParameter;
import com.hand.hap.sys.dto.SysParameterValue;
import com.hand.hap.sys.exception.ParameterException;
import com.hand.hap.sys.mapper.SysParameterMapper;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.service.impl.BaseServiceImpl;

/**
 * <p>
 * 参数定义ServiceImpl
 * </p>
 *
 * @author dingwei.ma@hand-china.com
 * @author jialin.xing@hand-china.com 2019/01/17 11:16
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysParameterServiceImpl extends BaseServiceImpl<SysParameter> implements ISysParameterService {

    @Autowired
    private SysParameterMapper mapper;

    @Override
    public List<SysParameter> queryAll(IRequest request, SysParameter condition, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return mapper.queryAll(condition);
    }

    @Override
    public String queryParamValueByCode(String parameterCode,
                                        Long userId,
                                        Long roleId,
                                        Long companyId,
                                        Long accEntityId,
                                        Long setOfBooksId,
                                        Long magOrgId,
                                        Long bgtOrgId) {
        List<SysParameter> listValue = this.mapper.queryParamValueByCode(parameterCode, userId, roleId, companyId, accEntityId, setOfBooksId, magOrgId, bgtOrgId);
        if (listValue.isEmpty()) {
            return BaseConstants.NO;
        }
        SysParameter parameter = listValue.get(0);
        String paramValue = parameter.getParameterValue();
        if (BaseConstants.YES.equals(parameter.getEncryptValueFlag())) {
            return new String(Hex.encode(Utf8.encode(paramValue)));
        }
        return paramValue;
    }

    @Override
    public List<SysParameter> batchSave(IRequest request, List<SysParameter> list) throws ParameterException {
        validateParameter(request, list);
        return super.batchUpdate(request, list);
    }

    @Override
    public List<SysParameter> queryParameterValue(IRequest request, SysParameterValue parameterValue, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return mapper.queryParameterValue(parameterValue);
    }

    /**
     * 参数校验
     *
     * @param request request
     * @param list    参数列表
     * @throws ParameterException
     */
    private void validateParameter(IRequest request, List<SysParameter> list) throws ParameterException {
        for (SysParameter parameter : list) {
            //日期判断
            if (parameter.getEndDateActive() != null) {
                if (parameter.getEndDateActive().before(parameter.getStartDateActive())) {
                    throw new ParameterException(ParameterException.END_DATE_MORE_START_DATE, ParameterException.END_DATE_MORE_START_DATE, null);
                }
            }
            //标志判断
            if ("N".equals(parameter.getSystemEnabledFlag()) && "N".equals(parameter.getRoleEnabledFlag()) && "N".equals(parameter.getUserEnabledFlag())
                    && "N".equals(parameter.getCompanyEnabledFlag()) && "N".equals(parameter.getAccEntityEnabledFlag())) {
                throw new ParameterException(ParameterException.AT_LEAST_CHOOSE_ONE_LEVEL, ParameterException.AT_LEAST_CHOOSE_ONE_LEVEL, null);
            }
        }
    }
}