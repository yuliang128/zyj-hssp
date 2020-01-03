package com.hand.hec.wfl.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflInstance;

public interface WflTodoMapper {
    List<Map> getTodoList();
}
