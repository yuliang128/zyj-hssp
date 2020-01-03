package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;

import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mouse 2019/03/08 18:09
 */
public interface IWflTodoService {

    /**
     * 获取当前员工的待办事项
     *
     *
     * @author mouse 2019-04-04 13:48
     * @return java.util.List<java.util.Map>
     */
    List<Map> getTodoList(IRequest request, Integer pageNum, Integer pageSize);
}
