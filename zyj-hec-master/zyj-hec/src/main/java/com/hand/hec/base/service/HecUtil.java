package com.hand.hec.base.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.exp.mapper.ExpEmployeeMapper;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hap.gld.dto.GldCurrency;
import com.hand.hap.gld.service.IGldCurrencyService;
import com.hand.hap.sys.constants.ParameterConstants;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hec.exp.dto.ExpOrgPosition;
import com.hand.hec.exp.dto.ExpOrgUnit;
import com.hand.hec.exp.service.IExpOrgPositionService;
import com.hand.hec.exp.service.IExpOrgUnitService;
import com.hand.hec.fnd.dto.GldAePeriodStatus;
import com.hand.hec.fnd.dto.GldExchangeRate;
import com.hand.hec.fnd.dto.GldExchangerateType;
import com.hand.hec.fnd.dto.OrdSystemCustomer;
import com.hand.hec.fnd.mapper.GldPeriodMapper;
import com.hand.hec.fnd.mapper.OrdSystemCustomerMapper;
import com.hand.hec.fnd.service.*;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.dto.GldResponsibilityCenter;
import com.hand.hec.gld.dto.GldSetOfBook;
import com.hand.hec.gld.mapper.GldSetOfBookMapper;
import com.hand.hec.gld.service.IGldAccountingEntityService;
import com.hand.hec.gld.service.IGldResponsibilityCenterService;
import com.hand.hec.pur.dto.PurSystemVender;
import com.hand.hec.pur.mapper.PurSystemVenderMapper;
import com.hand.hec.pur.service.IPurSystemVenderService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mouse 2019/05/06 14:26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class HecUtil {

    public static final String PAYEE_CATEGORY_EMPLOYEE = "EMPLOYEE";
    public static final String PAYEE_CATEGORY_VENDER = "VENDER";
    public static final String PAYEE_CATEGORY_CUSTOMER = "CUSTOMER";

    @Autowired
    private ExpEmployeeMapper employeeMapper;

    @Autowired
    private PurSystemVenderMapper venderMapper;

    @Autowired
    private IPurSystemVenderService venderService;

    @Autowired
    private OrdSystemCustomerMapper customerMapper;

    @Autowired
    private IOrdSystemCustomerService customerService;

    @Autowired
    IGldExchangeRateService exchangeRateService;

    @Autowired
    IFndCompanyService companyService;

    @Autowired
    IGldAccountingEntityService entityService;

    @Autowired
    GldSetOfBookMapper setOfBookMapper;

    @Autowired
    GldPeriodMapper gldPeriodMapper;

    @Autowired
    IGldAePeriodStatusService gldAePeriodStatusService;

    @Autowired
    IExpOrgPositionService positionService;

    @Autowired
    IExpOrgUnitService unitService;

    @Autowired
    IGldResponsibilityCenterService respCenterService;

    @Autowired
    IGldPeriodService periodService;

    @Autowired
    IGldCurrencyService currencyService;

    @Autowired
    ISysParameterService parameterService;

    @Autowired
    IGldExchangerateTypeService exchangerateTypeService;

    /**
     * 获取收款方类型ID
     *
     * @return java.lang.Long
     * @author mouse 2019-05-06 14:26
     */
    public Long getPayeeTypeId(String payeeCategory, Long payeeId) {

        switch (payeeCategory) {
            case PAYEE_CATEGORY_EMPLOYEE:
                ExpEmployee e = employeeMapper.selectByPrimaryKey(payeeId);
                if (e != null) {
                    return e.getEmployeeTypeId();
                }
                break;
            case PAYEE_CATEGORY_VENDER:
                PurSystemVender v = venderMapper.selectByPrimaryKey(payeeId);
                if (v != null) {
                    return v.getVenderTypeId();
                }
                break;
            case PAYEE_CATEGORY_CUSTOMER:
                OrdSystemCustomer c = customerMapper.selectByPrimaryKey(payeeId);
                if (c != null) {
                    return c.getCustomerTypeId();
                }
                break;
            default:
                break;
        }
        return null;
    }

    /**
     * 获取收款方代码-名称
     *
     * @return java.lang.String
     * @author jialin.xing@hand-china.com 2019-05-10 00:50
     */
    public String getPayeeCode(String payeeCategory, Long payeeId) {
        switch (payeeCategory) {
            case PAYEE_CATEGORY_EMPLOYEE:
                ExpEmployee e = employeeMapper.selectByPrimaryKey(payeeId);
                if (e != null) {
                    return e.getEmployeeCode() + '-' + e.getName();
                }
                break;
            case PAYEE_CATEGORY_VENDER:
                PurSystemVender v = venderMapper.selectByPrimaryKey(payeeId);
                if (v != null) {
                    return v.getVenderCode();
                }
                break;
            case PAYEE_CATEGORY_CUSTOMER:
                OrdSystemCustomer c = customerMapper.selectByPrimaryKey(payeeId);
                if (c != null) {
                    return c.getCustomerCode();
                }
                break;
            default:
                break;
        }
        return null;
    }

    /**
     * 获取收款方名称
     *
     * @return java.lang.String
     * @author jialin.xing@hand-china.com 2019-05-10 00:26
     */
    public String getPayeeName(IRequest request, String payeeCategory, Long payeeId) {
        switch (payeeCategory) {
            case PAYEE_CATEGORY_EMPLOYEE:
                ExpEmployee e = employeeMapper.selectByPrimaryKey(payeeId);
                if (e != null) {
                    return e.getName();
                }
                break;
            case PAYEE_CATEGORY_VENDER:
                PurSystemVender v = venderService.selectByPrimaryKey(request,
                                PurSystemVender.builder().venderId(payeeId).build());
                if (v != null) {
                    return v.getDescription();
                }
                break;
            case PAYEE_CATEGORY_CUSTOMER:
                OrdSystemCustomer c = customerService.selectByPrimaryKey(request,
                                OrdSystemCustomer.builder().customerId(payeeId).build());
                if (c != null) {
                    return c.getDescription();
                }
                break;
            default:
                break;
        }
        return null;
    }

    /**
     * 获取收款方代码-名称
     *
     * @return java.lang.String
     * @author jialin.xing@hand-china.com 2019-05-10 00:50
     */
    public String getPayeeCodeName(IRequest request, String payeeCategory, Long payeeId) {
        switch (payeeCategory) {
            case PAYEE_CATEGORY_EMPLOYEE:
                ExpEmployee e = employeeMapper.selectByPrimaryKey(payeeId);
                if (e != null) {
                    return e.getEmployeeCode() + '-' + e.getName();
                }
                break;
            case PAYEE_CATEGORY_VENDER:
                PurSystemVender v = venderService.selectByPrimaryKey(request,
                                PurSystemVender.builder().venderId(payeeId).build());
                if (v != null) {
                    return v.getVenderCode() + '-' + v.getDescription();
                }
                break;
            case PAYEE_CATEGORY_CUSTOMER:
                OrdSystemCustomer c = customerService.selectByPrimaryKey(request,
                                OrdSystemCustomer.builder().customerId(payeeId).build());
                if (c != null) {
                    return c.getCustomerCode() + '-' + c.getDescription();
                }
                break;
            default:
                break;
        }
        return null;
    }

    /**
     * 获取员工类型id
     *
     * @param employeeId
     * @return java.lang.Long
     * @author mouse 2019-05-06 14:41
     */
    public Long getEmployeeTypeId(Long employeeId) {
        ExpEmployee employee = employeeMapper.selectByPrimaryKey(employeeId);
        if (employee != null) {
            return employee.getEmployeeTypeId();
        }
        return null;

    }

    /**
     * 获取汇率
     *
     * @param accEntityId
     * @param fromCurrencyCode
     * @param toCurrencyCode
     * @param exchangeRateType
     * @param exchangeDate
     * @param periodName
     * @return java.math.BigDecimal
     * @author mouse 2019-05-06 16:38
     */
    public BigDecimal getExchangeRate(Long accEntityId, String fromCurrencyCode, String toCurrencyCode,
                    String exchangeRateType, Date exchangeDate, String periodName) {
        return exchangeRateService.getExchangeRate(accEntityId, fromCurrencyCode, toCurrencyCode, exchangeRateType,
                        exchangeDate, periodName, null);
    }

    /**
     * 根据公司ID获取管理组织ID
     *
     * @param request
     * @param companyId
     * @return java.lang.Long
     * @author mouse 2019-05-06 18:52
     */
    public Long getMagOrgId(IRequest request, Long companyId) {
        FndCompany company = new FndCompany();
        company.setCompanyId(companyId);
        company = companyService.selectByPrimaryKey(request, company);
        return company.getMagOrgId();
    }

    /**
     * 获取核算主体的默认账套
     *
     * @param request
     * @param accEntityId
     * @return java.lang.Long
     * @author mouse 2019-05-06 19:23
     */
    public Long getSetOfBooksId(IRequest request, Long accEntityId) {
        List<GldSetOfBook> setOfBookList = setOfBookMapper.queryDftSetOffBookByAe(accEntityId);
        return setOfBookList.get(0).getSetOfBooksId();
    }

    /**
     * 校验
     *
     * @param request
     * @param periodName
     * @param accEntityId
     * @return java.lang.Boolean
     * @author mouse 2019-05-07 16:12
     */
    public Boolean isPeriodOpen(IRequest request, String periodName, Long accEntityId) {
        GldAePeriodStatus status = new GldAePeriodStatus();
        status.setPeriodName(periodName);
        status.setAccEntityId(accEntityId);
        List<GldAePeriodStatus> statusList = gldAePeriodStatusService.select(request, status, 0, 0);
        if (statusList == null || statusList.size() == 0 || "C".equals(statusList.get(0).getPeriodStatusCode())) {
            return false;
        } else {
            return true;
        }
    }

    public String getPeriodNameByCompany(IRequest request, Date date, Long companyId, String status) {
        Long accEntityId = entityService.queryDefaultAccEntity(request, companyId).getAccEntityId();
        return gldPeriodMapper.getPeriodName(date, accEntityId, status);
    }

    /**
     * 获取默认的公司名称
     *
     * @param request
     * @author mouse 2019-05-27 19:42
     * @return java.lang.String
     */
    public String getDefaultCompanyName(IRequest request) {
        FndCompany company = new FndCompany();
        company.setCompanyId(request.getCompanyId());
        company = companyService.selectByPrimaryKey(request, company);
        return company.getCompanyShortName();
    }

    /**
     * 获取默认的公司ID
     *
     * @param request
     * @author mouse 2019-05-27 20:02
     * @return java.lang.Long
     */
    public Long getDefaultCompanyId(IRequest request) {
        return request.getCompanyId();
    }

    /**
     * 获取默认部门名称
     *
     * @param request
     * @author mouse 2019-05-27 20:05
     * @return java.lang.String
     */
    public String getDefaultUnitName(IRequest request) {
        ExpOrgUnit unit = new ExpOrgUnit();
        unit.setUnitId(this.getDefaultUnitId(request));
        unit = unitService.selectByPrimaryKey(request, unit);
        if (unit != null) {
            return unit.getDescription();
        } else {
            return null;
        }
    }

    /**
     * 获取默认部门ID
     *
     * @param request
     * @author mouse 2019-05-27 20:06
     * @return java.lang.Long
     */
    public Long getDefaultUnitId(IRequest request) {
        ExpOrgPosition position =
                        positionService.getDefaultPosition(request, request.getEmployeeId(), request.getCompanyId());
        if (position != null) {
            return position.getUnitId();
        } else {
            return null;
        }
    }

    /**
     * 获取默认岗位ID
     *
     * @param request
     * @author mouse 2019-05-27 20:06
     * @return java.lang.String
     */
    public String getDefaultPositionName(IRequest request) {
        ExpOrgPosition position =
                        positionService.getDefaultPosition(request, request.getEmployeeId(), request.getCompanyId());
        if (position != null) {
            return position.getDescription();
        } else {
            return null;
        }
    }

    /**
     * 获取默认岗位ID
     *
     * @param request
     * @author mouse 2019-05-27 20:06
     * @return java.lang.Long
     */
    public Long getDefaultPositionId(IRequest request) {
        ExpOrgPosition position =
                        positionService.getDefaultPosition(request, request.getEmployeeId(), request.getCompanyId());
        if (position != null) {
            return position.getPositionId();
        } else {
            return null;
        }
    }

    /**
     * 获取默认员工姓名
     *
     * @param request
     * @author mouse 2019-05-27 20:06
     * @return java.lang.String
     */
    public String getDefaultEmployeeName(IRequest request) {
        return request.getEmployeeName();
    }

    /**
     * 获取默认员工ID
     *
     * @param request
     * @author mouse 2019-05-27 20:06
     * @return java.lang.Long
     */
    public Long getDefaultEmployeeId(IRequest request) {
        return request.getEmployeeId();
    }

    /**
     * 获取默认核算主体名称
     *
     * @param request
     * @author mouse 2019-05-27 20:17
     * @return java.lang.String
     */
    public String getDefaultAccEntityName(IRequest request) {
        Long accEntityId = this.getDefaultAccEntityId(request);
        GldAccountingEntity accEntity = new GldAccountingEntity();
        accEntity.setAccEntityId(accEntityId);
        accEntity = entityService.selectByPrimaryKey(request, accEntity);
        if (accEntity != null) {
            return accEntity.getAccEntityName();
        } else {
            return null;
        }
    }

    /**
     * 获取默认核算主体ID
     *
     * @param request
     * @author mouse 2019-05-27 20:17
     * @return java.lang.Long
     */
    public Long getDefaultAccEntityId(IRequest request) {
        GldAccountingEntity accEntity = entityService.queryDefaultAccEntity(request, request.getCompanyId());
        if (accEntity != null) {
            return accEntity.getAccEntityId();
        } else {
            return null;
        }
    }

    /**
     * 获取默认责任中心名称
     *
     * @param request
     * @author mouse 2019-05-27 20:18
     * @return java.lang.String
     */
    public String getDefaultRespCenterName(IRequest request) {
        Long respCenterId = this.getDefaultRespCenterId(request);
        GldResponsibilityCenter respCenter = new GldResponsibilityCenter();
        respCenter.setResponsibilityCenterId(respCenterId);
        respCenter = respCenterService.selectByPrimaryKey(request, respCenter);
        if (respCenter != null) {
            return respCenter.getResponsibilityCenterName();
        } else {
            return null;
        }
    }

    /**
     * 获取默认责任中心ID
     *
     * @param request
     * @author mouse 2019-05-27 20:18
     * @return java.lang.Long
     */
    public Long getDefaultRespCenterId(IRequest request) {
        Long accEntityId = this.getDefaultAccEntityId(request);
        Long unitId = this.getDefaultUnitId(request);
        if (accEntityId != null && unitId != null) {
            GldResponsibilityCenter respCenter = respCenterService.getDefaultRespCenter(unitId, accEntityId);
            if (respCenter != null) {
                return respCenter.getResponsibilityCenterId();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 获取当天日期
     *
     * @author mouse 2019-05-28 10:23
     * @return java.util.Date
     */
    public Date getCurrentDate() {
        return new Date();
    }

    /**
     * 获取当天日期的字符串
     *
     *
     * @author mouse 2019-05-28 10:25
     * @return java.lang.String
     */
    public String getCurrentDateStr() {
        Date date = this.getCurrentDate();
        return DateUtils.date2Str(date, "yyyy-MM-dd");
    }

    /**
     * 获取当前日期时间的字符串
     *
     *
     * @author mouse 2019-05-28 10:26
     * @return java.lang.String
     */
    public String getCurrentDateTimeStr() {
        Date date = this.getCurrentDate();
        return DateUtils.date2Str(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     *
     *
     * @param request
     * @author mouse 2019-05-28 10:39
     * @return java.lang.String
     */
    public String getCurrentPeriodName(IRequest request) {
        return periodService.getPeriodName(request, this.getCurrentDate(), this.getDefaultAccEntityId(request), null);
    }

    /**
     * 获取默认的币种,当前核算主体的本位币
     * 
     * @param request
     * @return
     */
    public GldCurrency getDefaultCurrency(IRequest request) {
        String currencyCode = this.getDefaultCurrencyCode(request);
        GldCurrency currency = new GldCurrency();
        currency.setCurrencyCode(currencyCode);
        List<GldCurrency> currencyList = currencyService.select(request, currency, 0, 0);
        if (currencyList != null && currencyList.size() != 0) {
            return currencyList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 获取默认的币种代码
     *
     * @param request
     * @author mouse 2019-05-28 10:50
     * @return java.lang.String
     */
    public String getDefaultCurrencyCode(IRequest request) {
        return currencyService.getAccEntityFunCurrCode(this.getDefaultAccEntityId(request));
    }


    /**
     * 获取默认的币种名称
     *
     * @param request
     * @author mouse 2019-05-28 10:50
     * @return java.lang.String
     */
    public String getDefaultCurrencyName(IRequest request) {
        GldCurrency currency = this.getDefaultCurrency(request);
        if (currency != null) {
            return currency.getCurrencyName();
        } else {
            return null;
        }
    }

    /**
     * 获取默认核算汇率类型
     *
     * @param request
     * @author mouse 2019-05-28 14:10
     * @return java.lang.String
     */
    public String getDftAccExchRateType(IRequest request) {
        String dftAccExchRateType = parameterService.queryParamValueByCode(
                        ParameterConstants.PARAM_DEFAULT_EXCHANGE_RATE_TYPE, request.getUserId(), request.getRoleId(),
                        request.getCompanyId(), this.getDefaultAccEntityId(request), null, request.getMagOrgId(), null);
        return dftAccExchRateType;
    }

    /**
     * 获取默认核算汇率类型名称
     *
     * @param request
     * @author mouse 2019-05-28 14:10
     * @return java.lang.String
     */
    public String getDftAccExchRateTypeName(IRequest request) {
        String dftAccExchRateType = this.getDftAccExchRateType(request);
        if (dftAccExchRateType != null) {
            GldExchangerateType type = new GldExchangerateType();
            type.setTypeCode(dftAccExchRateType);
            List<GldExchangerateType> exchangerateTypeList = exchangerateTypeService.select(request, type, 0, 0);
            if (exchangerateTypeList != null && exchangerateTypeList.size() != 0) {
                return exchangerateTypeList.get(0).getDescription();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    /**
     * 获取默认管理汇率类型
     *
     * @param request
     * @author mouse 2019-05-28 14:10
     * @return java.lang.String
     */
    public String getDftMagExchRateType(IRequest request) {
        String dftMagExchRateType = parameterService.queryParamValueByCode(
                        ParameterConstants.PARAM_DEFAULT_MAG_EXCH_RATE_TYPE, request.getUserId(), request.getRoleId(),
                        request.getCompanyId(), this.getDefaultAccEntityId(request), null, request.getMagOrgId(), null);

        return dftMagExchRateType;
    }

    /**
     * 获取默认管理汇率类型名称
     *
     * @param request
     * @author mouse 2019-05-28 14:10
     * @return java.lang.String
     */
    public String getDftMagExchRateTypeName(IRequest request) {
        String dftMagExchRateType = this.getDftMagExchRateType(request);
        if (dftMagExchRateType != null) {
            GldExchangerateType type = new GldExchangerateType();
            type.setTypeCode(dftMagExchRateType);
            List<GldExchangerateType> exchangerateTypeList = exchangerateTypeService.select(request, type, 0, 0);
            if (exchangerateTypeList != null && exchangerateTypeList.size() != 0) {
                return exchangerateTypeList.get(0).getDescription();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    /**
     * <p>数字金额-中文金额转化</p>
     *
     * @param num 数字金额字符串
     * @return String
     * @author yang.duan 2019/6/14 17:10
     **/
    public String amountConvertToChinese(String num) throws RuntimeException{
        String integer;
        String small;
        boolean zeroFlag = false;
        boolean omitFlag = false;//省略标志，比如100万后面的0
        boolean hasSmall = false;//是否有小数
        String[] unit = {"圆", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟"};
        String[] numberWord = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String[] smallUnit = {"角", "分"};
        StringBuilder result = new StringBuilder();
        StringBuilder tmpSb = new StringBuilder();
        String number;
        char tmpChar;
        if(num.indexOf(",")>0){
            for(int i = 0; i < num.length(); i++){
                tmpChar = num.charAt(i);
                if(tmpChar != ','){
                    tmpSb.append(tmpChar);
                }
            }
            number = tmpSb.toString();
        }else{
            number = num;
        }
        if(number.indexOf(".")>0){
            String[] twoParts = number.split("\\.");
            integer = twoParts[0];
            small = twoParts[1];
        }else{
            integer = number;
            small = "";
        }
        if(integer.length()>12){
            throw new RuntimeException("本程序只能处理千亿以内的数字!");
        }
        String tmp;
        int tmpInt;
        int integerLengh = integer.length() > unit.length ? unit.length : integer.length();
        for(int i = 0; i < integerLengh; i++){
            tmp = String.valueOf(integer.charAt(i));
            tmpInt = Integer.valueOf(tmp).intValue();
            if(tmpInt == 0){
                if(integerLengh-1-i==4 || integerLengh-1-i==8){
                    //如果这位是万、亿时
                    if(omitFlag){
                        continue;
                    }else{
                        omitFlag = true;
                        result.append(unit[integerLengh-1-i]);
                    }
                }else if(integerLengh-1-i==0){
                    //这位是元，拼单位
                    result.append(unit[integerLengh-1-i]);
                }else{
                    //其他位，遇到0，更改标志，继续
                    zeroFlag = true;
                    continue;
                }

            }else{
                //非0，并且之前是0
                if(zeroFlag){
                    //如果这位是亿、万、元时，先填零，再拼数字
                    result.append(numberWord[0]);
                }else{
                    //之前也不是0，正常
                    //如果是亿位
                    if(integerLengh-1-i==8){
                        omitFlag = true;
                    }
                }
                zeroFlag = false;
                result.append(numberWord[tmpInt]).append(unit[integerLengh-1-i]);
            }

        }
        if(StringUtils.isNotBlank(small)){
            for(int i=0; i < smallUnit.length; i++){
                tmp = String.valueOf(small.charAt(i));
                tmpInt = Integer.valueOf(tmp).intValue();
                if(tmpInt == 0){
                    continue;
                }
                result.append(numberWord[tmpInt]).append(smallUnit[i]);
                hasSmall = true;
            }
        }else{
            //没有小数
        }
        if(!hasSmall){
            //没有小数或者小数都是0
            result.append("整");
        }
        //删除多余的"零"
        if(numberWord[0].equals(String.valueOf(result.toString().charAt(result.indexOf(unit[8])+1))) && !result.toString().contains(unit[4])){
            result.deleteCharAt(result.indexOf(unit[8])+1);
        }
        return result.toString();
    }
}
