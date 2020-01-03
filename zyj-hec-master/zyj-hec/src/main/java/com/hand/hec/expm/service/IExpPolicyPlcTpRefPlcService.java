package com.hand.hec.expm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.expm.dto.ExpPolicyPlcTpRefPlc;

import java.util.List;

/**
 * <p>
 * 费用政策地点类型分配政策地点Service
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 15:50
 */
public interface IExpPolicyPlcTpRefPlcService extends IBaseService<ExpPolicyPlcTpRefPlc>, ProxySelf<IExpPolicyPlcTpRefPlcService> {

    /**
     * 查找所有已分配给费用政策类型的地点
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return 返回地点
     * @author xiuxian.wu 2019-02-27
     */
    List<ExpPolicyPlcTpRefPlc> queryAll(IRequest requestContext, ExpPolicyPlcTpRefPlc dto, Integer page, Integer pageSize);

    /**
     * 查询剩余费用政策类型的地点
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return 费用政策类型的地点
     * @author xiuxian.wu 2019-02-27
     */
    List<ExpPolicyPlcTpRefPlc> queryRemainPolicyPlace(IRequest requestContext, ExpPolicyPlcTpRefPlc dto, Integer page, Integer pageSize);
}