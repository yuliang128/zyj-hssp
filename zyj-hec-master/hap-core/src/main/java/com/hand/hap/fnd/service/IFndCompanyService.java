package com.hand.hap.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.exception.FndCompanyException;
import com.hand.hap.system.service.IBaseService;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理公司service
 * </p>
 *
 * @author yang.duan 2019/01/10 11:10
 * @author xiuxian.wu 2019/01/25 15:11
 */
public interface IFndCompanyService extends IBaseService<FndCompany>, ProxySelf<IFndCompanyService> {

    /**
     * 预算检查的公司符合判断
     *
     * @param filtrateMethod 取值方式
     * @param controlType 过滤类型，明细、汇总、全部
     * @param companyId 维度值ID
     * @param controlCompanyCodeFrom 控制规则维度值代码从
     * @param controlCompanyCodeTo 控制规则维度值代码到
     * @return 符合的公司
     * @author mouse 2019-01-10 15:50
     */
    List<FndCompany> checkFndCompany(String filtrateMethod, String controlType, Long companyId,
                    String controlCompanyCodeFrom, String controlCompanyCodeTo);


    /**
     * 查询符合条件的管理公司
     *
     * @param dto 各种查询条件
     * @param page
     * @param pageSize
     * @param requestContext
     * @return 返回管理公司
     * @author xiuxian.wu
     */
    List<FndCompany> queryFndCompany(IRequest requestContext, FndCompany dto, int page, int pageSize);

    /**
     * 新增或修改公司信息及公司所关联的默认核算主体及预算实体
     *
     * @param requestContext
     * @param dto 公司信息
     * @return 返回新增或修改的公司信息
     * @author xiuxian.wu 2019/1/25 14:27
     */
    List<FndCompany> submitFndCompany(IRequest requestContext, List<FndCompany> dto);

    /**
     * 修改页面中查询父级公司且不包含自己的公司列表下拉框
     *
     * @param requestContext
     * @param dto 管理组织ID及自己公司ID信息
     * @return 不含自己的公司下拉框数据
     * @author xiuxian.wu 2019/1/25
     */
    List<FndCompany> queryFndCompanyBox(FndCompany dto, IRequest requestContext);

    Boolean checkInTime(Date date, Long companyId);

    /**
     * 判断公司员工分配是否存在
     *
     * @param employeeId 员工ID
     * @param companyId 公司ID
     * @return void
     * @author jialin.xing@hand-china.com 2019-03-06 16:06
     */
    void checkCompanyExists(Long employeeId, Long companyId) throws FndCompanyException;

    /**
     * 预算实体同步，根据预算组织ID获取公司信息
     *
     * @param bgtOrgId 预算组织ID
     * @return
     * @author guiyuting 2019-02-25 17:03
     */
    List<FndCompany> queryByBgtOrgId(IRequest request, Long bgtOrgId);

    /**
     * 根据公司ID和管理组织ID 查询默认公司信息
     *
     * @param company
     * @return 公司信息
     * @author guiyuting 2019-02-28 15:59
     */
    Map<String, Object> queryDefaultCompany(IRequest request, FndCompany company);

    /**
     * 查询当前预算表未被分配的公司
     *
     * @param magOrgId 管理组织ID
     * @param assignMoId 预算表分配管理组织ID
     * @return
     * @author guiyuting 2019-03-06 10:54
     */
    List<FndCompany> queryForBgtStructureAssign(IRequest request, Long magOrgId, Long assignMoId);

    /**
     * 查询当前预算表未被分配的公司
     *
     * @param magOrgId 管理组织ID
     * @param assignMoId 预算表分配管理组织ID
     * @return
     * @author guiyuting 2019-03-06 10:54
     */
    List<FndCompany> bgtItemAssignCompany(IRequest request, Long magOrgId, Long assignMoId);

    /**
     * 查询当前部门类型未被分配的公司
     *
     * @param magOrgId 管理组织ID
     * @param unitTypeId 部门类型ID
     * @return
     * @author guiyuting 2019-03-06 10:54
     */
    List<FndCompany> unitTypeAssignCompany(IRequest request, Long magOrgId, Long unitTypeId);

    /**
     * 根据CompanyId获取对应的公司对象
     *
     * @param request
     * @param companyId
     * @return com.hand.hap.fnd.dto.FndCompany
     * @author mouse 2019-03-14 17:23
     */
    FndCompany getCompany(IRequest request, Long companyId);

    /**
     * 查询当前工作中心定义-分配组织范围-明细未被分配的公司
     *
     * @param magOrgId 管理组织ID scopeId 共享作业中心业务范围ID
     * @return
     * @author bo.zhang 2019-03-18 10:54
     */
    List<FndCompany> workCenterDocTypeFndCompany(IRequest iRequest, @Param("magOrgId") Long magOrgId,
                    @Param("scopeId") Long scopeId);

    /**
     * 预算日记账类型定义查询未被分配的公司信息
     *
     * @param fndCompany
     * @param assignMoId 预算日记账类型分配管理组织ID
     * @author guiyuting 2019-03-21 17:09
     * @return 符合条件的公司
     */
    List<FndCompany> bgtJournalBatchAssign(IRequest iRequest, Long magOrgId, Long assignMoId, int page,
                    int pageSize);

	/**
	 * 查询当前公司相关信息 (单据页面)
	 *
	 * @param iRequest
	 * @author LJK 2019-03-28 14:22
	 * @return List<FndCompany>
	 */
	List<FndCompany> queryCurrentCompany(IRequest iRequest);

    /**
     * 查询没有分配计量单位的公司
     * @param iRequest
     * @param dto
     * @param page
     * @param pageSize
     * @author zhongyu 2019-4-25
     * @return
     */
	List<FndCompany> queryCompanyForUoms(IRequest iRequest,FndCompany dto, int page,int pageSize);

    /**
     * 按照管理组织ID和计量单位ID查询未分配公司。
     * @param iRequest
     * @param assignMoId
     * @param magOrgId
     * @param page
     * @param pageSize
     * @author zhongyu 2019-4-26
     * @return
     */
    List<FndCompany> queryNoAssignedCompanyForUoms(IRequest iRequest,Long assignMoId,Long magOrgId,int page ,int pageSize);


    /**
     * 当前组织下-公司lov-不限制已分配的公司
     * @param request
     * @param dto
     * @param page
     * @param pageSize
     * @return
     * @author zhongyu 2019-2-26 16:36
     */
    List<FndCompany> queryBatchCompany (IRequest request, FndCompany dto, int page, int pageSize);


}
