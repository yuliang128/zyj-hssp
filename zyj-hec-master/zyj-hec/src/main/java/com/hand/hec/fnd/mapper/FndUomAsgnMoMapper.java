package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.FndUomAsgnMo;

import java.util.List;

public interface FndUomAsgnMoMapper extends Mapper<FndUomAsgnMo>{

    @Override
     List<FndUomAsgnMo> select(FndUomAsgnMo dto);

    /**
     * 分配时，查询该记录是否存在
     * @param dto
     * @author zhongyu 2019-4-26
     * @return
     */
    List<FndUomAsgnMo> checkExists(FndUomAsgnMo dto);

}