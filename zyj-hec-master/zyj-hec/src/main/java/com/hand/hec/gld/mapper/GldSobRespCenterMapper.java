package com.hand.hec.gld.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.dto.GldResponsibilityCenter;
import com.hand.hec.gld.dto.GldSobRespCenter;

import java.util.List;

/**
 * <p>
 * 账套级成本中心mapper
 * </p>
 *
 * @author wuxiuxian 2019-01-10
 */

public interface GldSobRespCenterMapper extends Mapper<GldSobRespCenter> {
    /**
     * 通过父级成本中心查找子级成本中心的核算主体
     *
     * @param condition
     * @return 返回所有自己成本中心对应的核算主体
     * @author xiuxian.wu 2019/2/18 18:43
     */
    List<GldResponsibilityCenter> selectAccountingEntity(GldResponsibilityCenter condition);

    /**
     * 查询剩余的可以关联的核算主体
     *
     * @param center
     * @return 返回可以关联的核算主体
     * @author xiuxian.wu 2019/2/18 18:43
     */
    List<GldAccountingEntity> queryAccountingEntity(GldSobRespCenter center);

    /**
     * 查询对应的账套的账套级成本中心
     *
     * @param center
     * @return 返回账套级成本中心
     * @author xiuxian.wu 2019/2/18 18:43
     */
    List<GldSobRespCenter> query(GldSobRespCenter center);
}