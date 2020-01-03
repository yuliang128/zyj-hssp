package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoReqTypeAsgnCom;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/2/21 13:39
 * Description:申请单类型分配公司Service
 */
public interface IExpMoReqTypeAsgnComService extends IBaseService<ExpMoReqTypeAsgnCom>, ProxySelf<IExpMoReqTypeAsgnComService> {

    /**
     * 查询当前未分配的公司列表
     *
     * @param magOrgId       关联组织
     * @param moExpReqTypeId 申请单类型
     * @param page           页码
     * @param pageSize       数量
     * @param request        请求参数
     * @return 返回公司信息
     * @author jiangxz 2019/2/21 14:27
     */
    List<ExpMoReqTypeAsgnCom> queryFndCompanyTypeId(IRequest request, Long magOrgId, Long moExpReqTypeId, int page, int pageSize);

    /**
     * 批量分配公司
     *
     * @param jsonObject 组合数据
     * @param request    请求数据
     * @return 返回公司信息
     * @author jiangxz 2019/2/21 14:27
     */
    List<ExpMoReqTypeAsgnCom> batchAssignCompany(JSONObject jsonObject, IRequest request);
}