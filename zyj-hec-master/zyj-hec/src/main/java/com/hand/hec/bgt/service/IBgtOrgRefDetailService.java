package com.hand.hec.bgt.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtOrgRefDetail;

/**
 * <p>
 * 预算组织来源明细Service
 * </p>
 *
 * @author mouse 2019/01/07 17:02
 */
public interface IBgtOrgRefDetailService extends IBaseService<BgtOrgRefDetail>, ProxySelf<IBgtOrgRefDetailService> {


    /**
     * 根据来源类型代码来查询预算来源明细
     *
     * @param sourceTypeCode 来源明细代码
     * @param bgtOrgId       预算组织代码
     * @return
     * @author ngls.luhui 2019-01-29 15:00
     */
    public List<BgtOrgRefDetail> selectDetailBySourceType(String sourceTypeCode, Long bgtOrgId);

    /**
     * 根据来源类型代码来查询预算来源下拉框
     *
     * @param sourceTypeCode 来源明细代码
     * @author ngls.luhui 2019-01-29 15:34
     * @return 
     */
    public List<BgtOrgRefDetail> selectCombox(String sourceTypeCode, IRequest request);

}