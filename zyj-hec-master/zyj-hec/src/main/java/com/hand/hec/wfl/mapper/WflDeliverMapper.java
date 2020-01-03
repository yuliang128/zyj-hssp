package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflDeliver;

import java.util.List;

public interface WflDeliverMapper extends Mapper<WflDeliver>{
    List<WflDeliver> selectWflDeliver(WflDeliver wflDeliver);

    List<WflDeliver> selectWflDeliverLov(WflDeliver wflDeliver);

    List<WflDeliver> getMatchedDeliver(WflDeliver wflDeliver);
}