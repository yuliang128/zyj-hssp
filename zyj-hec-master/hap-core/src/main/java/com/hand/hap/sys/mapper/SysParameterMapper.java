package com.hand.hap.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.sys.dto.SysParameter;
import com.hand.hap.sys.dto.SysParameterValue;

/**
 * <p>
 * 参数定义Mapper接口
 * </p>
 *
 * @author dingwei.ma@hand-china.com
 * @author jialin.xing@hand-china.com 2019/01/17 11:16
 */
public interface SysParameterMapper extends Mapper<SysParameter> {

    List<SysParameter> queryAll(SysParameter record);

    /**
     * 获取参数值
     *
     * @param paramCode    参数代码
     * @param accEntityId  核算主体ID
     * @param setOfBooksId 账套ID
     * @param bgtOrgId     预算组织ID
     * @return 参数值列表
     * @author jialin.xing@hand-china.com 2019-01-17 10:00
     * @author hui.zhao@hand-china.com
     */
    List<SysParameter> queryParamValueByCode(@Param("parameterCode") String paramCode
            , @Param("userId") Long userId
            , @Param("roleId") Long roleId
            , @Param("companyId") Long companyId
            , @Param("accEntityId") Long accEntityId
            , @Param("setOfBooksId") Long setOfBooksId
            , @Param("magOrgId") Long magOrgId
            , @Param("bgtOrgId") Long bgtOrgId);

    /**
     * 参数指定查询
     *
     * @param parameterValue 参数指定dto
     * @return 参数列表
     * @author jialin.xing@hand-china.com 2019-01-24 14:01
     */

    List<SysParameter> queryParameterValue(SysParameterValue parameterValue);


}