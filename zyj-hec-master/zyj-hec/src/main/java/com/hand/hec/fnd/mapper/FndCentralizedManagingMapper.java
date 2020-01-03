package com.hand.hec.fnd.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.FndCentralizedManaging;

/**
 * <p>
 * 归口管理Mapper接口
 * </p>
 *
 * @author YHL 2019/01/21 19:42
 */
public interface FndCentralizedManagingMapper extends Mapper<FndCentralizedManaging> {

    /**
     * 查询归口管理
     *
     * @param fndCentralizedManaging 归口管理
     * @author YHL 2019-01-24 10:40
     * @return java.util.List<com.hand.hec.fnd.dto.FndCentralizedManaging> 归口管理
     */
    List<FndCentralizedManaging> selectCentralizedMagList(FndCentralizedManaging fndCentralizedManaging);

}