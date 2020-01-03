package com.hand.hap.sys.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.sys.dto.SysParameter;
import com.hand.hap.sys.dto.SysParameterValue;
import com.hand.hap.sys.mapper.SysParameterValueMapper;
import com.hand.hap.sys.service.ISysParameterValueService;
import com.hand.hap.system.service.impl.BaseServiceImpl;


/**
 * <p>
 * 参数指定 serviceImpl
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/01/22 11:27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysParameterValueServiceImpl extends BaseServiceImpl<SysParameterValue> implements ISysParameterValueService {

    @Autowired
    private SysParameterValueMapper mapper;

    /**
     * <p>
     * 前台参数展示主数据结构为sys_parameter  所以不能依据status来判断
     * </p>
     *
     * @author user 2019/01/14 15:04
     */
    @Override
    public List<SysParameter> batchSave(IRequest request, @StdWho List<SysParameter> list) {

        for (SysParameter parameter : list) {
            if ("Y".equals(parameter.getEncryptValueFlag())) {
                parameter.setParameterValue(desEncrypt(parameter.getParameterValue()));
            }
            SysParameterValue parameterValue = new SysParameterValue();
            BeanUtils.copyProperties(parameter, parameterValue);
            if (parameterValue.getParameterValueId() == null) {
                parameterValue.setCreationDate(new Date());
                parameterValue.setCreatedBy(request.getUserId());
                mapper.insert(parameterValue);
            } else {
                Criteria criteria = new Criteria(parameterValue.getParameterValue());
                criteria.where(new WhereField(SysParameterValue.FIELD_PARAMETER_ID, Comparison.EQUAL)
                        , new WhereField(SysParameterValue.FIELD_LEVEL_ID, Comparison.EQUAL)
                        , new WhereField(SysParameterValue.FIELD_LEVEL_VALUE, Comparison.EQUAL));
                mapper.updateByPrimaryKeyOptions(parameterValue, criteria);
            }
        }

        return list;
    }

    /**
     * <p>
     * 参数指定加密   暂时不做
     * </p>
     *
     * @author dingwei.ma@hand-china.com 2019/01/14 16:48
     */
    public String desEncrypt(String parameterValue) {
        return parameterValue;
    }
}