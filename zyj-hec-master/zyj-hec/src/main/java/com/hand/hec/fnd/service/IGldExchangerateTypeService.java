package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.GldExchangerateType;
import com.hand.hec.fnd.exception.ExchangerateTypeException;

import java.util.List;

/**
 * <p>
 * 汇率类型Service接口
 * </p>
 *
 * @author xingjialin 2019/01/07 10:39
 */
public interface IGldExchangerateTypeService extends IBaseService<GldExchangerateType>, ProxySelf<IGldExchangerateTypeService>{

    /**
     * <p>校验汇率类型分配核算主体是否符合要求</p>
     * <p>同一个核算主体不能再不同的汇率类型下启用</p>
     * @return 核算主体数量
     * @throws ExchangerateTypeException
     */
    int check() throws ExchangerateTypeException;

    /**
     *报销单审核修改汇率获取汇率类型
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/11 13:50
     *@param iRequest 请求上下文
     *@return List<GldExchangerateType>
     *@Version 1.0
     **/
    List<GldExchangerateType> expAuditSelectType(IRequest iRequest);

    /**
     * 获取预算组织的汇率类型
     *
     * @param bgtOrgId 预算组织ID
     * @author guiyuting 2019-03-29 10:39
     * @return 对应预算组织的汇率类型code
     */
    String getRateTypeByBgtOrg(Long bgtOrgId);
}