package com.hand.hec.fnd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.GldExchangerateTpAsgnAe;

/**
 * <p>
 * 汇率类型分配核算主体Mapper接口
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/01/08 16:09
 */

public interface GldExchangerateTpAsgnAeMapper extends Mapper<GldExchangerateTpAsgnAe> {
    
    /**
     * <p>校验汇率类型分配核算主体是否符合要求</p>
     * <p>同一个核算主体不能再不同的汇率类型下启用</p>
     * @param null
     * @author jialin.xing@hand-china.com 2019-01-14 15:54
     * @return 核算主体数量
     */
    int check();

    /**
     * 根据typeId查找分配的核算主体
     * @param condition 传入条件
     * @author jialin.xing@hand-china.com 2019-01-29 15:08
     * @return 分配的核算主体列表
     */
    List<GldExchangerateTpAsgnAe> selectByTypeId(GldExchangerateTpAsgnAe condition);

    /**
     * 查询指定汇率类型未被分配的核算实体
     *
     * @param typeId
     * @author jialin.xing@hand-china.com 2019-03-25 17:04
     * @return java.util.List<com.hand.hec.fnd.dto.GldExchangerateTpAsgnAe>
     */
    List<GldExchangerateTpAsgnAe> queryUnallocatedAccEntity(@Param("typeId")Long typeId);

}