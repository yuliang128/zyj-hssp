package com.hand.hap.function.service;

import java.util.List;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.function.dto.ResourceCustomization;
import com.hand.hap.system.service.IBaseService;

/**
 * 资源合并配置服务接口.
 *
 * @author zhizheng.yang@hand-china.com
 */
public interface IResourceCustomizationService extends IBaseService<ResourceCustomization>, ProxySelf<IResourceCustomizationService> {

    /**
     * 根据资源Id查询资源合并配置项.
     *
     * @param resourceId 资源Id
     * @return 资源合并配置项集合
     */
    List<ResourceCustomization> selectResourceCustomizationsByResourceId(Long resourceId);

    /**
     * 根据资源Id删除资源合并配置项.
     *
     * @param resourceId 资源Id
     * @return int
     */
    int deleteByResourceId(Long resourceId);
}
