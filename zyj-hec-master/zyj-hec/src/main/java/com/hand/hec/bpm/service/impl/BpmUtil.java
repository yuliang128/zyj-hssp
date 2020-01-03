package com.hand.hec.bpm.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hec.base.service.HecUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * description
 *
 * @author mouse 2019/05/27 19:43
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BpmUtil {


    @Autowired
    HecUtil hecUtil;

    /**
     * 获取默认的公司名称
     *
     * @param request
     * @author mouse 2019-05-27 19:42
     * @return java.lang.String
     */
    public String getDefaultCompanyName(IRequest request, Map param) {
        return hecUtil.getDefaultCompanyName(request);
    }


    /**
     * 获取默认的公司ID
     *
     * @param request
     * @author mouse 2019-05-27 20:02
     * @return java.lang.Long
     */
    public Long getDefaultCompanyId(IRequest request, Map param) {
        return hecUtil.getDefaultCompanyId(request);
    }


    /**
     * 获取默认部门名称
     *
     * @param request
     * @author mouse 2019-05-27 20:05
     * @return java.lang.String
     */
    public String getDefaultUnitName(IRequest request, Map param) {
        return hecUtil.getDefaultUnitName(request);
    }



    /**
     * 获取默认部门ID
     *
     * @param request
     * @author mouse 2019-05-27 20:06
     * @return java.lang.Long
     */
    public Long getDefaultUnitId(IRequest request, Map param) {
        return hecUtil.getDefaultUnitId(request);
    }


    /**
     * 获取默认岗位ID
     *
     * @param request
     * @author mouse 2019-05-27 20:06
     * @return java.lang.String
     */
    public String getDefaultPositionName(IRequest request, Map param) {
        return hecUtil.getDefaultPositionName(request);
    }



    /**
     * 获取默认岗位ID
     *
     * @param request
     * @author mouse 2019-05-27 20:06
     * @return java.lang.Long
     */
    public Long getDefaultPositionId(IRequest request, Map param) {
        return hecUtil.getDefaultPositionId(request);
    }


    /**
     * 获取默认员工姓名
     *
     * @param request
     * @author mouse 2019-05-27 20:06
     * @return java.lang.String
     */
    public String getDefaultEmployeeName(IRequest request, Map param) {
        return hecUtil.getDefaultEmployeeName(request);
    }


    /**
     * 获取默认员工ID
     *
     * @param request
     * @author mouse 2019-05-27 20:06
     * @return java.lang.Long
     */
    public Long getDefaultEmployeeId(IRequest request, Map param) {
        return hecUtil.getDefaultEmployeeId(request);
    }


    /**
     * 获取默认核算主体名称
     *
     * @param request
     * @author mouse 2019-05-27 20:17
     * @return java.lang.String
     */
    public String getDefaultAccEntityName(IRequest request, Map param) {
        return hecUtil.getDefaultAccEntityName(request);
    }


    /**
     * 获取默认核算主体ID
     *
     * @param request
     * @author mouse 2019-05-27 20:17
     * @return java.lang.Long
     */
    public Long getDefaultAccEntityId(IRequest request, Map param) {
        return hecUtil.getDefaultAccEntityId(request);
    }


    /**
     * 获取默认责任中心名称
     *
     * @param request
     * @author mouse 2019-05-27 20:18
     * @return java.lang.String
     */
    public String getDefaultRespCenterName(IRequest request, Map param) {
        return hecUtil.getDefaultRespCenterName(request);
    }

    /**
     * 获取默认责任中心ID
     *
     * @param request
     * @author mouse 2019-05-27 20:18
     * @return java.lang.Long
     */
    public Long getDefaultRespCenterId(IRequest request, Map param) {
        return hecUtil.getDefaultRespCenterId(request);
    }


    /**
     * 获取当天日期
     *
     * @author mouse 2019-05-28 10:23
     * @return java.util.Date
     */
    public Date getCurrentDate(IRequest request, Map param) {
        return hecUtil.getCurrentDate();
    }


    /**
     * 获取当天日期的字符串
     *
     *
     * @author mouse 2019-05-28 10:25
     * @return java.lang.String
     */
    public String getCurrentDateStr(IRequest request, Map param) {
        return hecUtil.getCurrentDateStr();
    }


    /**
     * 获取当前日期时间的字符串
     *
     *
     * @author mouse 2019-05-28 10:26
     * @return java.lang.String
     */
    public String getCurrentDateTimeStr(IRequest request, Map param) {
        return hecUtil.getCurrentDateTimeStr();
    }


    /**
     * 获取当前期间
     *
     * @param request
     * @author mouse 2019-05-28 10:39
     * @return java.lang.String
     */
    public String getCurrentPeriodName(IRequest request, Map param) {
        return hecUtil.getCurrentPeriodName(request);
    }

    /**
     * 获取默认的币种代码
     *
     * @param request
     * @author mouse 2019-05-28 10:50
     * @return java.lang.String
     */
    public String getDefaultCurrencyCode(IRequest request, Map param) {
        return hecUtil.getDefaultCurrencyCode(request);
    }

    /**
     * 获取默认的币种名称
     *
     * @param request
     * @author mouse 2019-05-28 10:50
     * @return java.lang.String
     */
    public String getDefaultCurrencyName(IRequest request, Map param) {
        return hecUtil.getDefaultCurrencyName(request);
    }


    /**
     * 获取默认核算汇率类型
     *
     * @param request
     * @author mouse 2019-05-28 14:10
     * @return java.lang.String
     */
    public String getDftAccExchRateType(IRequest request, Map param) {
        return hecUtil.getDftAccExchRateType(request);
    }

    /**
     * 获取默认核算汇率类型名称
     *
     * @param request
     * @author mouse 2019-05-28 14:10
     * @return java.lang.String
     */
    public String getDftAccExchRateTypeName(IRequest request, Map param) {
        return hecUtil.getDftAccExchRateTypeName(request);
    }


    /**
     * 获取默认管理汇率类型
     *
     * @param request
     * @author mouse 2019-05-28 14:10
     * @return java.lang.String
     */
    public String getDftMagExchRateType(IRequest request, Map param) {
        return hecUtil.getDftMagExchRateType(request);
    }


    /**
     * 获取默认管理汇率类型名称
     *
     * @param request
     * @author mouse 2019-05-28 14:10
     * @return java.lang.String
     */
    public String getDftMagExchRateTypeName(IRequest request, Map param) {
        return hecUtil.getDftMagExchRateTypeName(request);
    }
}
