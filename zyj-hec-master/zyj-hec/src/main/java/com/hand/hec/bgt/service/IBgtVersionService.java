package com.hand.hec.bgt.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtVersion;

/**
 * <p>
 * 预算版本ServiceImpl
 * </p>
 *
 * @author mouse 2019/01/07 17:05
 */
public interface IBgtVersionService extends IBaseService<BgtVersion>, ProxySelf<IBgtVersionService> {

    /**
     * 预算检查的预算版本符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param filtrateMethod 控制规则取值方式
     * @param versionCodeFrom 控制规则预算版本代码从
     * @param versionCodeTo 控制规则预算版本代码到
     * @author YHL 2019-03-05 18:54
     * @return java.util.List<com.hand.hec.bgt.dto.BgtVersion> 符合的预算版本
     */
    List<BgtVersion> checkBgtVersion(String controlType, String filtrateMethod, String versionCodeFrom,
            String versionCodeTo);

    /**
     * 根据预算组织ID查询当前预算版本
     *
     * @param request
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-14 16:41
     * @return com.hand.hec.bgt.dto.BgtVersion 该预算组织的当前预算版本
     */
    BgtVersion getCurrentBgtVersionByBgtOrgId(IRequest request, Long bgtOrgId);

    /**
     * 根据预算组织ID获取预算版本下拉框
     *
     * @param request
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-14 16:06
     * @return java.util.List<com.hand.hec.bgt.dto.BgtVersion> 该预算组织的预算版本
     */
    List<BgtVersion> getBgtVersionOption(IRequest request, Long bgtOrgId);


}