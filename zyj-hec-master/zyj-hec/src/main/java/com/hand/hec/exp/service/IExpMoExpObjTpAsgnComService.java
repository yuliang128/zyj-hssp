package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoExpObjTpAsgnCom;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/2/26 16:48
 * Description:费用对象分配公司Service
 */
public interface IExpMoExpObjTpAsgnComService extends IBaseService<ExpMoExpObjTpAsgnCom>, ProxySelf<IExpMoExpObjTpAsgnComService> {
    /**
     * 查询当前未分配的公司列表
     *
     * @param magOrgId       关联组织
     * @param moExpObjTypeId 费用对象类型id
     * @param page           页码
     * @param pageSize       数量
     * @return 返回公司信息
     * @author jiangxz 2019/2/21 14:27
     */
    List<ExpMoExpObjTpAsgnCom> queryFndCompanyTypeId(Long magOrgId, Long moExpObjTypeId, int page, int pageSize,IRequest request);

    /**
     * 批量分配公司
     *
     * @param jsonObject 组合数据
     * @param request    请求数据
     * @return 返回公司信息
     * @author jiangxz 2019/2/21 14:27
     */
    List<ExpMoExpObjTpAsgnCom> batchAssignCompany(JSONObject jsonObject, IRequest request);
}