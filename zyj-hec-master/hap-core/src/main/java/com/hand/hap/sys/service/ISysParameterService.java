package com.hand.hap.sys.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.sys.dto.SysParameter;
import com.hand.hap.sys.dto.SysParameterValue;
import com.hand.hap.sys.exception.ParameterException;
import com.hand.hap.system.service.IBaseService;

/**
 * <p>
 * 参数定义Service接口
 * </p>
 *
 * @author dingwei.ma@hand-china.com
 * @author jialin.xing@hand-china.com 2019/01/17 11:16
 */
public interface ISysParameterService extends IBaseService<SysParameter>, ProxySelf<ISysParameterService> {

    /**
     * 批量保存
     *
     * @param request
     * @param list
     * @return java.util.List<com.hand.hap.sys.dto.SysParameter>
     * @author jialin.xing@hand-china.com 2019-03-01 14:53
     */
    List<SysParameter> batchSave(IRequest request, List<SysParameter> list) throws ParameterException;

    /**
     * 基础查询
     *
     * @param request   request
     * @param condition 模糊查询条件
     * @param pageNum   页数
     * @param pageSize  页大小
     * @return java.util.List<com.hand.hap.sys.dto.SysParameter>
     * @author jialin.xing@hand-china.com 2019-03-01 14:53
     */
    List<SysParameter> queryAll(IRequest request, SysParameter condition, int pageNum, int pageSize);

    /**
     * 根据参数代码查询参数值
     *
     * @param parameterCode 参数代码
     * @param userId        用户ID
     * @param roleId        角色ID
     * @param companyId     公司ID
     * @param accEntityId   核算实体ID
     * @param setOfBooksId  账套ID
     * @param magOrgId      管理组织ID
     * @param bgtOrgId      预算组织ID
     * @return java.lang.String
     * @author jialin.xing@hand-china.com 2019-02-18 15:38
     */
    String queryParamValueByCode(String parameterCode,
                                 Long userId,
                                 Long roleId,
                                 Long companyId,
                                 Long accEntityId,
                                 Long setOfBooksId,
                                 Long magOrgId,
                                 Long bgtOrgId);

    /**
     * 参数指定查询
     *
     * @param request        request
     * @param parameterValue 参数指定
     * @param pageNum        页大小
     * @param pageSize       页数
     * @return 参数列表
     */
    List<SysParameter> queryParameterValue(IRequest request, SysParameterValue parameterValue, int pageNum, int pageSize);
}