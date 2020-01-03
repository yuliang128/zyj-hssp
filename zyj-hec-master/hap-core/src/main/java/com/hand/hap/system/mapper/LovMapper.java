package com.hand.hap.system.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.system.dto.Lov;

/**
 * lov mapper.
 *
 * @author njq.niu@hand-china.com
 * @date 2016/2/1
 */
public interface LovMapper extends Mapper<Lov> {

    /**
     * 根据Lov代码查询lov.
     *
     * @param code
     * @return
     */
    Lov selectByCode(String code);

    /**
     * 条件查询lov.
     *
     * @param lov
     * @return
     */
    List<Lov> selectLovs(Lov lov);

}