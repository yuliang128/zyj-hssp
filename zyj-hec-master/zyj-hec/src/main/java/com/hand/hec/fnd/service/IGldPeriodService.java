package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.GldPeriod;
import com.hand.hec.fnd.exception.GldPeriodException;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * 会计期间Service接口
 * </p>
 *
 * @author $JK.Lu$ 2019/01/14 14:47
 */
public interface IGldPeriodService extends IBaseService<GldPeriod>, ProxySelf<IGldPeriodService> {

    /**
     * 检查期间是否已存在
     *
     * @param request
     * @param dto 会计期间
     * @author LJK 2019-01-22 14:13
     * @return 会计期间list
     */
    List<GldPeriod> checkPeriodExists(IRequest request, GldPeriod dto);

    /**
     * 检查期间是否已被使用
     *
     * @param request
     * @param dto 会计期间
     * @author LJK 2019-01-22 14:13
     * @return 会计期间list
     */
    List<GldPeriod> checkPeriodUsed(IRequest request, GldPeriod dto);


    /**
     * 创建会计期间批量提交
     *
     * @param request
     * @param list
     * @GldPeriodException 会计期间异常
     * @author LJK 2019-01-22 17:34
     * @throws GldPeriodException exception
     * @return List<GldPeriod>
     */
    List<GldPeriod> createPeriod(IRequest request, List<GldPeriod> list) throws GldPeriodException;

    /**
     * 获取会计期间
     *
     * @Author Tagin
     * @Date 2019-02-20 17:20
     * @param iRequest 请求
     * @param date 日期
     * @param accEntityId 核算主体ID
     * @param status 状态 "O" 或者 为空
     * @return java.lang.String
     * @Version 1.0
     **/
    String getPeriodName(IRequest iRequest, Date date, Long accEntityId, String status);



	 /**
	  * 查询期间序号
	  * @param  request request
	  * @param  periodName 期间名称
	  * @param  periodSetCode 会计期代码
	  * @author rui.shi@hand-china.com 2019-03-19 10:33
	  * @return InternalPeriodNum 期间序号
	  */
	Long  queryInternalPeriodNum(IRequest request,String periodName, String periodSetCode);


	 /**
	  * 查询前一个期间序号
	  *
	  * @param  request request
	  * @param  gldPeriod gldPeriod
	  * @author rui.shi@hand-china.com 2019-03-19 10:33
	  * @return lastInternalPeriodNum 期间序号
	  */
	Long  queryLastInternalPeriodNum(IRequest request,GldPeriod gldPeriod);



	/**
	 * 查询下一个期间序号
	 *
	 * @param  request request
	 * @param  periodSetCode 会计期代码
	 * @param  internalPeriodNum 当前期间序号
	 * @author rui.shi@hand-china.com 2019-03-19 10:33
	 * @return nextInternalPeriodNum 下一个期间序号
	 */
	Long  queryNextInternalPeriodNum(IRequest request,String periodSetCode,Long internalPeriodNum);



	/**
	 * 根据条件查询GldPeriod
	 * @param  request request
	 * @param  record 查询条件
	 * @author rui.shi@hand-china.com 2019-03-08 15:17
	 * @return
	 */
	List<GldPeriod> queryGldPeriod(IRequest request, GldPeriod record);

	/**
	 *根据核算主体和期间名称获取期间数据
	 *@Author hui.zhao01@hand-china.com
	 *@Date 2019/5/10 14:21
	 *@param iRequest
	 *@param accEntityId 核算主体Id
	 *@param periodName 期间名称
	 *@return
	 *@Version 1.0
	 **/
    GldPeriod queryPeriodByAccEntityAndPeriodName(IRequest iRequest, Long accEntityId, String periodName);
}
