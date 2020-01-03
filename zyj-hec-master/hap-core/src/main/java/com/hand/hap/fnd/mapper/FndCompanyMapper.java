package com.hand.hap.fnd.mapper;

import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.mybatis.common.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理公司Mapper
 * </p>
 *
 * @author yang.duan 2019/01/10 11:06
 * @author xiuxian.wu 2019/01/25 15:16
 */
public interface FndCompanyMapper extends Mapper<FndCompany> {

    /**
     * 预算检查的公司符合判断
     *
     * @param filtrateMethod         取值方式
     * @param controlType            过滤类型，明细、汇总、全部
     * @param companyId              维度值ID
     * @param controlCompanyCodeFrom 控制规则维度值代码从
     * @param controlCompanyCodeTo   控制规则维度值代码到
     * @return 符合的公司
     * @author mouse 2019-01-10 15:50
     */
    List<FndCompany> checkFndCompany(@Param("filtrateMethod") String filtrateMethod,
            @Param("controlType") String controlType, @Param("companyId") Long companyId,
            @Param("controlCompanyCodeFrom") String controlCompanyCodeFrom,
            @Param("controlCompanyCodeTo") String controlCompanyCodeTo);

    /**
     * 查询符合条件的管理公司
     *
     * @param dto 各种查询条件
     * @return 返回管理公司
     * @author xiuxian.wu 2019/1/25 15:15
     */
    List<FndCompany> queryFndCompany(FndCompany dto);

    /**
     * 修改页面中查询父级公司且不包含自己的公司列表下拉框
     *
     * @param dto 管理组织ID及自己公司ID信息
     * @return 不含自己的公司下拉框数据
     * @author xiuxian.wu 2019/1/25 15:15
     */
    List<FndCompany> queryFndCompanyBox(FndCompany dto);

    /**
     * 当前组织下-公司lov-不限制已分配的公司
     *
     * @param dto
     * @return 分配公司信息
     * @author zhongyu 2019-2-26 16:36
     */
    List<FndCompany> queryBatchCompany(FndCompany dto);

    /**
     * 查询员工公司分配数量
     *
     * @param employeeId 员工ID
     * @param companyId 公司ID
     * @author jialin.xing@hand-china.com 2019-03-06 16:14
     * @return int
     */
    int selectEmployeeCompanyCount(Long employeeId, Long companyId);

    /**
     * 预算实体同步，根据预算组织ID获取公司信息
     *
     * @param bgtOrgId 预算组织ID
     * @return
     * @author guiyuting 2019-02-25 17:03
     */
    List<FndCompany> queryByBgtOrgId(@Param("bgtOrgId") Long bgtOrgId);

    /**
     * 根据公司ID和管理组织ID 查询默认公司信息
     *
     * @param company
     * @return 公司信息
     * @author guiyuting 2019-02-28 15:59
     */
    Map<String,Object> queryDefaultCompany(FndCompany company);

    /**
     * 查询当前预算表未被分配的公司
     *
     * @param magOrgId 管理组织ID
     * @param  assignMoId 预算表分配公司ID
     * @author guiyuting 2019-03-06 10:54
     * @return
     */
    List<FndCompany> queryForBgtStructureAssign(@Param("magOrgId") Long magOrgId,@Param("assignMoId") Long assignMoId);

    /**
     * 查询当前预算表未被分配的公司
     *
     * @param magOrgId 管理组织ID
     * @param assignMoId 预算表分配管理组织ID
     * @author guiyuting 2019-03-06 10:54
     * @return
     */
    List<FndCompany> bgtItemAssignCompany(@Param("magOrgId") Long magOrgId,@Param("assignMoId") Long assignMoId);

    /**
     * 查询当前部门类型未被分配的公司
     *
     * @param magOrgId 管理组织ID
     * @param unitTypeId 部门类型ID
     * @author guiyuting 2019-03-06 10:54
     * @return
     */
    List<FndCompany> unitTypeAssignCompany(@Param("magOrgId") Long magOrgId,@Param("unitTypeId") Long unitTypeId);
    /**
     * 查询当前工作中心定义-分配组织范围-明细未被分配的公司
     *
     * @param magOrgId 管理组织ID  scopeId 共享作业中心业务范围ID
     * @author bo.zhang 2019-03-18 10:54
     * @return
     */
    List<FndCompany> workCenterDocTypeFndCompany(@Param("magOrgId") Long magOrgId,@Param("scopeId") Long scopeId);

    /**
     * 预算日记账类型定义查询未被分配的公司信息
     *
     * @param fndCompany
     * @param assignMoId 预算日记账类型分配管理组织ID
     * @author guiyuting 2019-03-21 17:09
     * @return 符合条件的公司
     */
    List<FndCompany> bgtJournalBatchAssign(@Param("magOrgId") Long magOrgId,@Param("assignMoId") Long assignMoId);

    /**
     * 查询当前公司相关信息 (单据页面)
     *
     * @param null
     * @author LJK 2019-03-28 14:22
     * @return List<FndCompany>
     */
	List<FndCompany> queryCurrentCompany();

    /**
     * 计量单位查询公司(分配/未分配)
     * @param dto
     * @author zhongyu 2019-4-25
     * @return
     */
	List<FndCompany> queryCompanyForUoms(FndCompany dto);

	List<FndCompany> queryNoAssignedCompanyForUoms(@Param("assignMoId")Long assignMoId,@Param("magOrgId")Long magOrgId);
}
