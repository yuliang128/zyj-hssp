package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.exp.dto.ExpEmployeeAssign;
import com.hand.hap.exp.service.IExpEmployeeAssignService;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.gld.mapper.GldCurrencyMapper;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.sys.constants.ParameterConstants;
import com.hand.hap.sys.mapper.SysParameterMapper;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtBudgetReserve;
import com.hand.hec.bgt.service.IBgtBudgetItemMappingService;
import com.hand.hec.bgt.service.IBgtBudgetItemService;
import com.hand.hec.bgt.service.IBgtBudgetReserveService;
import com.hand.hec.exp.dto.*;
import com.hand.hec.exp.mapper.ExpMoReqItemMapper;
import com.hand.hec.exp.mapper.ExpRequisitionLineMapper;
import com.hand.hec.exp.mapper.ExpRequisitionObjectMapper;
import com.hand.hec.exp.service.*;
import com.hand.hec.expm.dto.ExpDocumentAuthority;
import com.hand.hec.expm.service.IExpDocumentAuthorityService;
import com.hand.hec.fnd.mapper.FndDimensionValueMapper;
import com.hand.hec.fnd.mapper.GldPeriodMapper;
import com.hand.hec.fnd.service.IGldExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpRequisitionLineServiceImpl extends BaseServiceImpl<ExpRequisitionLine> implements IExpRequisitionLineService {

    @Autowired
    ExpRequisitionLineMapper mapper;
    @Autowired
    IExpRequisitionLineService iExpRequisitionLineService;

    @Autowired
    IExpMoExpPolicyDetailService expMoExpPolicyDetailService;

    @Autowired
    IExpEmployeeAssignService expEmployeeAssignService;

    @Autowired
    IFndManagingOrganizationService fndManagingOrganizationService;

    @Autowired
    GldCurrencyMapper gldCurrencyMapper;

    @Autowired
    IBgtBudgetItemService bgtBudgetItemService;

    @Autowired
    ExpMoReqItemMapper expMoReqItemMapper;

    @Autowired
    FndDimensionValueMapper dimensionValueMapper;

    @Autowired
    IExpRequisitionTravelLineService expRequisitionTravelLineService;

    @Autowired
    GldPeriodMapper gldPeriodMapper;

    @Autowired
    ExpRequisitionObjectMapper expReqObjectMapper;

    @Autowired
    IExpRequisitionHeaderService ExpReqHeaderService;


    @Override
    public List<Map> queryDetailLine(IRequest request, long headId) {
        return mapper.queryDetailLine(headId);
    }

    @Autowired
    IBgtBudgetItemMappingService budgetItemMappingService;


    @Autowired
    SysParameterMapper sysParameterMapper;

    @Autowired
    IGldExchangeRateService gldExchangeRateService;

    @Autowired
    IExpRequisitionDistService expReqDistService;

    @Autowired
    IExpRequisitionObjectService expReqObjectService;

    @Autowired
    private IBgtBudgetReserveService bgtBudgetReserveService;

    @Autowired
    private IExpDocumentAuthorityService authorityService;
    /**
     * 申请单行信息查询
     *
     * @param expRequisitionHeaderId 申请单头id
     * @param request
     * @return List<ExpRequisitionLine>
     * @author jiangxuzheng@hand-china.com 2019-03-28 10:00
     */
    @Override
    public List<ExpRequisitionLine> expReqLineQuery(IRequest request, long expRequisitionHeaderId) {
        List<ExpRequisitionLine> expReqLineList = mapper.expReqLineQuery(expRequisitionHeaderId);
        List<ExpRequisitionLine> aExpReqLineList = new ArrayList<>();
        for (ExpRequisitionLine expReqLine : expReqLineList) {
            ExpEmployeeAssign employeeAssign = expEmployeeAssignService.getEmployeeAssignInfo(request, expReqLine.getCompanyId(), expReqLine.getEmployeeId());
            FndManagingOrganization managingOrganization = fndManagingOrganizationService.defaultManageOrganizationQuery(request, expReqLine.getCompanyId());
            List<ExpMoExpPolicyDetail> policyDetailList = expMoExpPolicyDetailService.getPolicyInfo(request, managingOrganization.getMagOrgId(), expReqLine.getCompanyId(), expReqLine.getAccEntityId(), employeeAssign.getEmployeeJobId(), employeeAssign.getEmployeeLevelsId(), expReqLine.getPlaceId(), expReqLine.getPlaceTypeId(), expReqLine.getPositionId(), expReqLine.getUnitId(), expReqLine.getTransportation(), "EXP_REQUISITION", expReqLine.getMoExpReqTypeId(), expReqLine.getMoExpenseTypeId(), null, expReqLine.getMoReqItemId());
            expReqLine.setChangeableFlag(policyDetailList.get(0).getChangeableFlag());
            expReqLine.setExpenseStandard(policyDetailList.get(0).getExpenseStandard());
            expReqLine.setUpperLimit(policyDetailList.get(0).getUpperLimit());
            expReqLine.setLowerLimit(policyDetailList.get(0).getLowerLimit());
            expReqLine.setCommitFlag(policyDetailList.get(0).getCommitFlag());
            aExpReqLineList.add(expReqLine);
        }

        return aExpReqLineList;
    }

    /**
     * <p>
     * 校验非申请单数量和金额
     * <p/>
     *
     * @param aBigDecimal 行金额/数量
     * @return void
     * @throws RuntimeException exception
     * @author jiangxz 2019/4/3 10:57
     */
    @Override
    public void signCheck(BigDecimal aBigDecimal) throws RuntimeException {
        if (aBigDecimal.compareTo(BigDecimal.ZERO) != 1) {
            throw new RuntimeException("数量和金额必须为正数！");
        }
    }

    /**
     * <p>
     * 根据精度计算金额
     * <p/>
     *
     * @param amount
     * @return 返回计算金额
     * @author jiangxz 2019/4/1 10:54
     */
    private BigDecimal calcCurrTrxAmount(BigDecimal amount, String currencyCode) {
        int precision = gldCurrencyMapper.getPrecision(currencyCode);
        return amount.setScale(precision);
    }

    /**
     * <p>
     * 校验行维值是否存在
     * <p/>
     *
     * @param line 申请单行信息
     * @return void
     * @author jiangxz 2019/4/3 10:02
     */
    private void checkDimValue(ExpRequisitionLine line) {
        List<Long> dimensionList = new ArrayList<>();
        dimensionList.add(line.getDimension1Id());
        dimensionList.add(line.getDimension2Id());
        dimensionList.add(line.getDimension3Id());
        dimensionList.add(line.getDimension4Id());
        dimensionList.add(line.getDimension5Id());
        dimensionList.add(line.getDimension6Id());
        dimensionList.add(line.getDimension7Id());
        dimensionList.add(line.getDimension8Id());
        dimensionList.add(line.getDimension9Id());
        dimensionList.add(line.getDimension10Id());
        dimensionList.add(line.getDimension11Id());
        dimensionList.add(line.getDimension12Id());
        dimensionList.add(line.getDimension13Id());
        dimensionList.add(line.getDimension14Id());
        dimensionList.add(line.getDimension15Id());
        dimensionList.add(line.getDimension16Id());
        dimensionList.add(line.getDimension17Id());
        dimensionList.add(line.getDimension18Id());
        dimensionList.add(line.getDimension19Id());
        dimensionList.add(line.getDimension20Id());
        for (int i = 0; i <= 20; i++) {
            int count = 0;
            count = dimensionValueMapper.checkDimensionValue(Long.valueOf(i), dimensionList.get(i));
            if (count == 0) {
                throw new RuntimeException("维值错误！不属于此维度！");
            }
        }
    }

    /**
     * <p>
     * 计算金额
     * <p/>
     *
     * @param businessPrice          业务单价
     * @param primaryQuantity        业务数量
     * @param businessCurrencyCode   业务币种
     * @param biz2payExchangeRate    业务币种->支付币种汇率
     * @param paymentCurrencyCode    支付币种
     * @param pay2magExchangeRate    支付币种->管理币种汇率
     * @param managementCurrencyCode 管理币种
     * @param pay2funExchangeRate    支付币种->本位币汇率
     * @param funCurrencyCode        本位币币种
     * @return 返回带有各个金额属性的申请单行DTO
     * @author jiangxz 2019/4/3 10:00
     */
    @Override
    public ExpRequisitionLine calcAmount(BigDecimal businessPrice, BigDecimal primaryQuantity, String businessCurrencyCode, BigDecimal biz2payExchangeRate, String paymentCurrencyCode, BigDecimal pay2magExchangeRate, String managementCurrencyCode, BigDecimal pay2funExchangeRate, String funCurrencyCode) {
        ExpRequisitionLine line = new ExpRequisitionLine();
        // 计算业务金额
        BigDecimal businessAmount = calcCurrTrxAmount(businessPrice.multiply(primaryQuantity), businessCurrencyCode);

        // 计算支付单价
        BigDecimal paymentPrice = calcCurrTrxAmount(businessPrice.multiply(biz2payExchangeRate), paymentCurrencyCode);

        // 计算支付金额
        BigDecimal paymentAmount = calcCurrTrxAmount(businessPrice.multiply(biz2payExchangeRate).multiply(primaryQuantity), paymentCurrencyCode);

        // 计算管理单价
        BigDecimal managementPrice = calcCurrTrxAmount(businessPrice.multiply(biz2payExchangeRate).multiply(pay2magExchangeRate), managementCurrencyCode);

        // 计算管理金额
        BigDecimal managementAmount = calcCurrTrxAmount(businessPrice.multiply(biz2payExchangeRate).multiply(pay2magExchangeRate).multiply(primaryQuantity), managementCurrencyCode);

        // 计算核算金额(本位币对应金额)
        BigDecimal functionalAmount = calcCurrTrxAmount(businessPrice.multiply(biz2payExchangeRate).multiply(pay2funExchangeRate).multiply(primaryQuantity), funCurrencyCode);

        line.setBusinessAmount(businessAmount);
        line.setPaymentPrice(paymentPrice);
        line.setPaymentAmount(paymentAmount);
        line.setManagementPrice(managementPrice);
        line.setManagementAmount(managementAmount);
        line.setRequisitionFunctionalAmount(functionalAmount);

        return line;
    }

    /**
     * <p>
     * 申请单行新增
     * <p/>
     *
     * @param request
     * @param expReqLine   需要新增的申请单行信息
     * @param expReqHeader 申请单头信息
     * @param expMoReqType 申请单类型
     * @return 新增后的行信息
     * @throws RuntimeException exception
     * @author jiangxz 2019/4/3 9:58
     */
    @Override
    public ExpRequisitionLine insertExpRequisitionLine(IRequest request, ExpRequisitionLine expReqLine, ExpRequisitionHeader expReqHeader, ExpMoReqType expMoReqType) throws RuntimeException {
        if (ExpRequisitionHeader.FIELD_STATUS_GENERATE.equals(expReqHeader.getRequisitionStatus()) || ExpRequisitionHeader.FIELD_STATUS_REJECTED.equals(expReqHeader.getRequisitionStatus()) || ExpRequisitionHeader.FIELD_STATUS_TAKEN_BACK.equals(expReqHeader.getRequisitionStatus())) {
            //检验单价、金额必须为正数
            signCheck(expReqLine.getBusinessPrice());
            signCheck(new BigDecimal(expReqLine.getPrimaryQuantity()));


            // 获取核算主体本位币
            String functionalCurrencyCode = gldCurrencyMapper.getAccEntityFunCurrCode(expReqLine.getAccEntityId());
            //计算金额
            ExpRequisitionLine line = calcAmount(expReqLine.getBusinessPrice(), new BigDecimal(expReqLine.getPrimaryQuantity()), expReqLine.getBusinessCurrencyCode(), expReqLine.getBiz2payExchangeRate(), expReqLine.getPaymentCurrencyCode(), expReqLine.getPay2magExchangeRate(), expReqLine.getManagementCurrencyCode(), expReqLine.getPay2funExchangeRate(), functionalCurrencyCode);
            // 根据预算项决定规则获取预算项目ID
            Long budgetItemId = budgetItemMappingService.getPrioRityItemId(ExpRequisitionHeader.FIELD_EXP_REQ, expReqLine.getCompanyId(), expMoReqType.getMoExpReqTypeId(), expReqLine.getMoExpenseTypeId(), expReqLine.getMoReqItemId(), null, expReqLine.getUnitId(), expReqLine.getPositionId(), expReqLine.getEmployeeId(), expReqLine.getAccEntityId(), expReqLine.getRespCenterId(), expReqHeader.getPayeeCategory(), expReqHeader.getPayeeId(), expReqLine.getDimension1Id(), expReqLine.getDimension2Id(), expReqLine.getDimension3Id(), expReqLine.getDimension4Id(), expReqLine.getDimension5Id(), expReqLine.getDimension6Id(), expReqLine.getDimension7Id(), expReqLine.getDimension8Id(), expReqLine.getDimension9Id(), expReqLine.getDimension10Id(), expReqLine.getDimension11Id(), expReqLine.getDimension12Id(), expReqLine.getDimension13Id(), expReqLine.getDimension14Id(), expReqLine.getDimension15Id(), expReqLine.getDimension16Id(), expReqLine.getDimension17Id(), expReqLine.getDimension18Id(), expReqLine.getDimension19Id(), expReqLine.getDimension20Id());
            if (budgetItemId.intValue() == -1) {
                ExpMoReqItem expMoReqItem = expMoReqItemMapper.selectByPrimaryKey(expReqLine.getMoReqItemId());
                budgetItemId = expMoReqItem.getBudgetItemId();
            }
            // 校验维值是否存在
            checkDimValue(expReqLine);
            //赋值数据
            expReqLine.setExpRequisitionHeaderId(expReqHeader.getExpRequisitionHeaderId());
            expReqLine.setBudgetItemId(budgetItemId);
            expReqLine.setPaymentPrice(line.getPaymentPrice());
            expReqLine.setManagementPrice(line.getManagementPrice());
            expReqLine.setBusinessAmount(line.getBusinessAmount());
            expReqLine.setPaymentAmount(line.getPaymentAmount());
            expReqLine.setManagementAmount(line.getManagementAmount());
            expReqLine.setRequisitionFunctionalAmount(line.getRequisitionFunctionalAmount());
            expReqLine.setPeriodName(expReqHeader.getPeriodName());
            expReqLine.setPay2funExchangeRate(expReqHeader.getPay2funExchangeRate());
            expReqLine.setPayeeCategory(expReqHeader.getPayeeCategory());
            expReqLine.setPayeeId(expReqHeader.getPayeeId());
            expReqLine.setPaymentFlag("N");
            expReqLine.setRequisitionStatus(expReqHeader.getRequisitionStatus());
            expReqLine.setAuditFlag(expReqHeader.getAuditFlag());
            expReqLine.setCreatedBy(request.getUserId());
            expReqLine.setCreationDate(new Date());
            expReqLine.setLastUpdatedBy(request.getUserId());
            expReqLine.setLastUpdateDate(new Date());
            // 插入数据
            expReqLine = self().insertSelective(request, expReqLine);
            //差旅行程行和机票行则插关联表
            if (ExpRequisitionLine.FIELD_TRAVEL_STAY_LINES.equals(expReqLine.getReqPageElementCode()) || ExpRequisitionLine.FIELD_TICKET_LINES.equals(expReqLine.getReqPageElementCode()) || ExpRequisitionLine.FIELD_TRAVEL_STAY_LINES.equals(expReqLine.getReqPageElementCode())) {
                ExpRequisitionTravelLine expReqTravelLine = new ExpRequisitionTravelLine();
                expReqTravelLine.setExpRequisitionLineId(expReqLine.getExpRequisitionLineId());
                expReqTravelLine.setTravelLineCategory(expReqLine.getReqPageElementCode());
                expReqTravelLine.setSeatClass(expReqLine.getSeatClass());
                expReqTravelLine.setDeparturePlace(expReqLine.getDeparturePlaceName());
                expReqTravelLine.setDeparturePlaceId(expReqLine.getDeparturePlaceId());
                expReqTravelLine.setDepartureDate(expReqLine.getDepartureDate());
                expReqTravelLine.setArrivalPlace(expReqLine.getArrivalPlaceName());
                expReqTravelLine.setArrivalPlaceId(expReqLine.getArrivalPlaceId());
                expReqTravelLine.setArrivalDate(expReqLine.getArrivalDate());
                expReqTravelLine.setAccommodationDateFrom(expReqLine.getAccommodationDateFrom());
                expReqTravelLine.setAccommodationDateTo(expReqLine.getAccommodationDateTo());
                expReqTravelLine.setAccommodationDays(expReqLine.getAccommodationDays());
                expReqTravelLine.setPeerPeople(expReqLine.getPeerPeopleName());
                expReqTravelLine.setMonopolizeFlag(expReqLine.getMonopolizeFlag());
                expReqTravelLine.setMonopolizePlatform(expReqLine.getMonopolizePlatform());
                expReqTravelLine.setCreatedBy(request.getUserId());
                expReqTravelLine.setCreationDate(new Date());
                expReqTravelLine.setLastUpdatedBy(request.getUserId());
                expReqTravelLine.setLastUpdateDate(new Date());
                expReqTravelLine = expRequisitionTravelLineService.insertSelective(request, expReqTravelLine);
            }
            ExpEmployeeAssign employeeAssign = expEmployeeAssignService.getEmployeeAssignInfo(request, expReqLine.getCompanyId(), expReqLine.getEmployeeId());
            if (expReqLine.getPlaceId() != null) {
                // 地点常用表数据插入(未)
            }
            FndManagingOrganization managingOrganization = fndManagingOrganizationService.defaultManageOrganizationQuery(request, expReqLine.getCompanyId());
            // 费用申请政策定义增加超标准事件定义
            List<ExpMoExpPolicyDetail> expPolicyDetails = expMoExpPolicyDetailService.getPolicyInfo(request, managingOrganization.getMagOrgId(), expReqLine.getCompanyId(), expReqLine.getAccEntityId(), employeeAssign.getEmployeeJobId(), employeeAssign.getEmployeeLevelsId(), expReqLine.getPlaceId(), expReqLine.getPlaceTypeId(), expReqLine.getPositionId(), expReqLine.getUnitId(), null, ExpRequisitionHeader.FIELD_EXP_REQ, expMoReqType.getMoExpReqTypeId(), expReqLine.getMoExpenseTypeId(), null, expReqLine.getMoReqItemId());
            ExpMoExpPolicyDetail policyDetail = new ExpMoExpPolicyDetail();
            if (expPolicyDetails != null && !expPolicyDetails.isEmpty()) {
                policyDetail = expPolicyDetails.get(0);
            }

            BigDecimal newExpenseStandard = policyDetail.getExpenseStandard();
            BigDecimal newLowerLimit = policyDetail.getLowerLimit();
            BigDecimal newUpperLimit = policyDetail.getUpperLimit();
            // 判断币种是否相同
            if (!expReqLine.getPaymentCurrencyCode().equals(policyDetail.getCurrencyCode())) {
                // 根据系统参数获取默认汇率类型
                String defaultExchangeRate = String.valueOf(sysParameterMapper.queryParamValueByCode(ParameterConstants.PARAM_DEFAULT_EXCHANGE_RATE_TYPE, null, null, null, null, null, null, null).get(0).getParameterValue());
                // 获取期间
                String periodName = String.valueOf(gldPeriodMapper.getPeriodName(expReqHeader.getRequisitionDate(), expReqLine.getAccEntityId(), null));
                // 获取汇率
                BigDecimal exchangeRate = gldExchangeRateService.getExchangeRate(expReqLine.getAccEntityId(), policyDetail.getCurrencyCode(), line.getPaymentCurrencyCode(), defaultExchangeRate, expReqHeader.getRequisitionDate(), periodName, "Y");
                // 重新设置费用标准/上下限值
                newExpenseStandard = policyDetail.getExpenseStandard().multiply(exchangeRate);
                newLowerLimit = policyDetail.getLowerLimit().multiply(exchangeRate);
                newUpperLimit = policyDetail.getUpperLimit().multiply(exchangeRate);

            }
            // 超上下限
            if ("Y".equals(policyDetail.getCommitFlag()) && expReqLine.getManagementPrice() != null && policyDetail.getEventName() != null) {
                newLowerLimit = newLowerLimit == null ? expReqLine.getManagementPrice().subtract(BigDecimal.ONE) : newLowerLimit;
                newUpperLimit = newUpperLimit == null ? expReqLine.getManagementPrice().add(BigDecimal.ONE) : newUpperLimit;
                if (expReqLine.getManagementPrice().compareTo(newLowerLimit) == -1 || expReqLine.getManagementPrice().compareTo(newUpperLimit) == 1) {
                    //事件
                }
            }
            newExpenseStandard = newExpenseStandard == null ? expReqLine.getBusinessPrice().subtract(BigDecimal.ONE) : newExpenseStandard;
            // 超标准
            if (expReqLine.getManagementPrice().compareTo(newExpenseStandard) == 1 && policyDetail.getUpperStdEventName() != null) {
                //事件
            }
            ExpRequisitionDist expRequisitionDist = new ExpRequisitionDist();
            expRequisitionDist.setExpRequisitionLineId(expReqLine.getExpRequisitionLineId());
            expRequisitionDist.setDescription(expReqLine.getDescription());
            expRequisitionDist.setDateFrom(expReqLine.getDateFrom());
            expRequisitionDist.setDateTo(expReqLine.getDateTo());
            expRequisitionDist.setPeriodName(expReqLine.getPeriodName());
            expRequisitionDist.setBusinessCurrencyCode(expReqLine.getBusinessCurrencyCode());
            expRequisitionDist.setBiz2payExchangeRate(expReqLine.getBiz2payExchangeRate());
            expRequisitionDist.setBiz2payExchangeType(expReqLine.getBiz2payExchangeType());
            expRequisitionDist.setPaymentCurrencyCode(expReqLine.getPaymentCurrencyCode());
            expRequisitionDist.setPay2funExchangeType(expReqLine.getPay2funExchangeType());
            expRequisitionDist.setPay2funExchangeRate(expReqLine.getPay2funExchangeRate());
            expRequisitionDist.setManagementCurrencyCode(expReqLine.getManagementCurrencyCode());
            expRequisitionDist.setPay2funExchangeType(expReqLine.getPay2funExchangeType());
            expRequisitionDist.setPay2funExchangeRate(expReqLine.getPay2funExchangeRate());
            expRequisitionDist.setMoExpenseTypeId(expReqLine.getMoExpenseTypeId());
            expRequisitionDist.setMoReqItemId(expReqLine.getMoReqItemId());
            expRequisitionDist.setBudgetItemId(expReqLine.getBudgetItemId());
            expRequisitionDist.setBusinessPrice(expReqLine.getBusinessPrice());
            expRequisitionDist.setPaymentPrice(expReqLine.getPaymentPrice());
            expRequisitionDist.setPrimaryQuantity(expReqLine.getPrimaryQuantity());
            expRequisitionDist.setPrimaryUom(expReqLine.getPrimaryUom());
            expRequisitionDist.setBusinessAmount(expReqLine.getBusinessAmount());
            expRequisitionDist.setPaymentAmount(expReqLine.getPaymentAmount());
            expRequisitionDist.setManagementAmount(expReqLine.getManagementAmount());
            expRequisitionDist.setRequisitionFunctionalAmount(expReqLine.getRequisitionFunctionalAmount());
            expRequisitionDist.setCompanyId(expReqLine.getCompanyId());
            expRequisitionDist.setUnitId(expReqLine.getUnitId());
            expRequisitionDist.setPositionId(expReqLine.getPositionId());
            expRequisitionDist.setEmployeeId(expReqLine.getEmployeeId());
            expRequisitionDist.setAccEntityId(expReqLine.getAccEntityId());
            expRequisitionDist.setRespCenterId(expReqLine.getRespCenterId());
            expRequisitionDist.setBgtEntityId(expReqLine.getBgtEntityId());
            expRequisitionDist.setBgtCenterId(expReqLine.getBgtCenterId());
            expRequisitionDist.setCloseFlag("N");
            expRequisitionDist.setCloseDate(null);
            expRequisitionDist.setAttachmentNum(expReqLine.getAttachmentNum());
            expRequisitionDist.setExceedBudgetStrategy(null);
            expRequisitionDist.setReleasedAmount(new BigDecimal(0));
            expRequisitionDist.setDimension1Id(expReqLine.getDimension1Id());
            expRequisitionDist.setDimension2Id(expReqLine.getDimension2Id());
            expRequisitionDist.setDimension3Id(expReqLine.getDimension3Id());
            expRequisitionDist.setDimension4Id(expReqLine.getDimension4Id());
            expRequisitionDist.setDimension5Id(expReqLine.getDimension5Id());
            expRequisitionDist.setDimension6Id(expReqLine.getDimension6Id());
            expRequisitionDist.setDimension7Id(expReqLine.getDimension7Id());
            expRequisitionDist.setDimension8Id(expReqLine.getDimension8Id());
            expRequisitionDist.setDimension9Id(expReqLine.getDimension9Id());
            expRequisitionDist.setDimension10Id(expReqLine.getDimension10Id());
            expRequisitionDist.setDimension11Id(expReqLine.getDimension11Id());
            expRequisitionDist.setDimension12Id(expReqLine.getDimension12Id());
            expRequisitionDist.setDimension13Id(expReqLine.getDimension13Id());
            expRequisitionDist.setDimension14Id(expReqLine.getDimension14Id());
            expRequisitionDist.setDimension15Id(expReqLine.getDimension15Id());
            expRequisitionDist.setDimension16Id(expReqLine.getDimension16Id());
            expRequisitionDist.setDimension17Id(expReqLine.getDimension17Id());
            expRequisitionDist.setDimension18Id(expReqLine.getDimension18Id());
            expRequisitionDist.setDimension19Id(expReqLine.getDimension19Id());
            expRequisitionDist.setDimension20Id(expReqLine.getDimension20Id());
            //插入分配行
            expRequisitionDist = expReqDistService.insertDistLine(request, expRequisitionDist, expMoReqType, expReqHeader, expReqLine);

        }
        return null;
    }

    /**
     * <p>
     * 申请单行更新
     * <p/>
     *
     * @param request
     * @param expReqLine   需要更新的申请单行信息
     * @param expReqHeader 申请单头信息
     * @param expMoReqType 申请单类型
     * @return 更新后的行信息
     * @throws RuntimeException exception
     * @author jiangxz 2019/4/3 9:58
     */
    @Override
    public ExpRequisitionLine updateExpRequisitionLine(IRequest request, ExpRequisitionLine expReqLine, ExpRequisitionHeader expReqHeader, ExpMoReqType expMoReqType) throws RuntimeException {
        if (ExpRequisitionHeader.FIELD_STATUS_GENERATE.equals(expReqHeader.getRequisitionStatus()) || ExpRequisitionHeader.FIELD_STATUS_REJECTED.equals(expReqHeader.getRequisitionStatus()) || ExpRequisitionHeader.FIELD_STATUS_TAKEN_BACK.equals(expReqHeader.getRequisitionStatus())) {
            //检验单价、金额必须为正数
            signCheck(expReqLine.getBusinessPrice());
            signCheck(new BigDecimal(expReqLine.getPrimaryQuantity()));


            // 获取核算主体本位币
            String functionalCurrencyCode = gldCurrencyMapper.getAccEntityFunCurrCode(expReqLine.getAccEntityId());
            //计算金额
            ExpRequisitionLine line = calcAmount(expReqLine.getBusinessPrice(), new BigDecimal(expReqLine.getPrimaryQuantity()), expReqLine.getBusinessCurrencyCode(), expReqLine.getBiz2payExchangeRate(), expReqLine.getPaymentCurrencyCode(), expReqLine.getPay2magExchangeRate(), expReqLine.getManagementCurrencyCode(), expReqLine.getPay2funExchangeRate(), functionalCurrencyCode);
            // 根据预算项决定规则获取预算项目ID
            Long budgetItemId = budgetItemMappingService.getPrioRityItemId(ExpRequisitionHeader.FIELD_EXP_REQ, expReqLine.getCompanyId(), expMoReqType.getMoExpReqTypeId(), expReqLine.getMoExpenseTypeId(), expReqLine.getMoReqItemId(), null, expReqLine.getUnitId(), expReqLine.getPositionId(), expReqLine.getEmployeeId(), expReqLine.getAccEntityId(), expReqLine.getRespCenterId(), expReqHeader.getPayeeCategory(), expReqHeader.getPayeeId(), expReqLine.getDimension1Id(), expReqLine.getDimension2Id(), expReqLine.getDimension3Id(), expReqLine.getDimension4Id(), expReqLine.getDimension5Id(), expReqLine.getDimension6Id(), expReqLine.getDimension7Id(), expReqLine.getDimension8Id(), expReqLine.getDimension9Id(), expReqLine.getDimension10Id(), expReqLine.getDimension11Id(), expReqLine.getDimension12Id(), expReqLine.getDimension13Id(), expReqLine.getDimension14Id(), expReqLine.getDimension15Id(), expReqLine.getDimension16Id(), expReqLine.getDimension17Id(), expReqLine.getDimension18Id(), expReqLine.getDimension19Id(), expReqLine.getDimension20Id());
            if (budgetItemId.intValue() == -1) {
                ExpMoReqItem expMoReqItem = expMoReqItemMapper.selectByPrimaryKey(expReqLine.getMoReqItemId());
                budgetItemId = expMoReqItem.getBudgetItemId();
            }
            // 校验维值是否存在
            checkDimValue(expReqLine);
            //赋值数据
            expReqLine.setBudgetItemId(budgetItemId);
            expReqLine.setPaymentPrice(line.getPaymentPrice());
            expReqLine.setManagementPrice(line.getManagementPrice());
            expReqLine.setBusinessAmount(line.getBusinessAmount());
            expReqLine.setPaymentAmount(line.getPaymentAmount());
            expReqLine.setManagementAmount(line.getManagementAmount());
            expReqLine.setRequisitionFunctionalAmount(line.getRequisitionFunctionalAmount());
            expReqLine.setLastUpdatedBy(request.getUserId());
            expReqLine.setLastUpdateDate(new Date());
            //更新dist表
            expReqDistService.resetDistLine(request, expMoReqType, expReqHeader, expReqLine);
            expReqLine = self().updateByPrimaryKey(request, expReqLine);
            //差旅行程行和机票行则插关联表
            if (ExpRequisitionLine.FIELD_TRAVEL_STAY_LINES.equals(expReqLine.getReqPageElementCode()) || ExpRequisitionLine.FIELD_TICKET_LINES.equals(expReqLine.getReqPageElementCode()) || ExpRequisitionLine.FIELD_TRAVEL_STAY_LINES.equals(expReqLine.getReqPageElementCode())) {
                ExpRequisitionTravelLine expReqTravelLine = new ExpRequisitionTravelLine();
                expReqTravelLine.setTravelLineId(expReqLine.getTravelLineId());
                expReqTravelLine = expRequisitionTravelLineService.selectByPrimaryKey(request, expReqTravelLine);
                expReqTravelLine.setExpRequisitionLineId(expReqLine.getExpRequisitionLineId());
                expReqTravelLine.setTravelLineCategory(expReqLine.getReqPageElementCode());
                expReqTravelLine.setSeatClass(expReqLine.getSeatClass());
                expReqTravelLine.setDeparturePlace(expReqLine.getDeparturePlaceName());
                expReqTravelLine.setDeparturePlaceId(expReqLine.getDeparturePlaceId());
                expReqTravelLine.setDepartureDate(expReqLine.getDepartureDate());
                expReqTravelLine.setArrivalPlace(expReqLine.getArrivalPlaceName());
                expReqTravelLine.setArrivalPlaceId(expReqLine.getArrivalPlaceId());
                expReqTravelLine.setArrivalDate(expReqLine.getArrivalDate());
                expReqTravelLine.setAccommodationDateFrom(expReqLine.getAccommodationDateFrom());
                expReqTravelLine.setAccommodationDateTo(expReqLine.getAccommodationDateTo());
                expReqTravelLine.setAccommodationDays(expReqLine.getAccommodationDays());
                expReqTravelLine.setPeerPeople(expReqLine.getPeerPeopleName());
                expReqTravelLine.setMonopolizeFlag(expReqLine.getMonopolizeFlag());
                expReqTravelLine.setMonopolizePlatform(expReqLine.getMonopolizePlatform());
                expReqTravelLine.setLastUpdatedBy(request.getUserId());
                expReqTravelLine.setLastUpdateDate(new Date());
                expReqTravelLine = expRequisitionTravelLineService.updateByPrimaryKey(request, expReqTravelLine);

            }
            ExpEmployeeAssign employeeAssign = expEmployeeAssignService.getEmployeeAssignInfo(request, expReqLine.getCompanyId(), expReqLine.getEmployeeId());
            if (expReqLine.getPlaceId() != null) {
                // 地点常用表数据插入(未)
            }
            FndManagingOrganization managingOrganization = fndManagingOrganizationService.defaultManageOrganizationQuery(request, expReqLine.getCompanyId());
            // 费用申请政策定义增加超标准事件定义
            List<ExpMoExpPolicyDetail> expPolicyDetails = expMoExpPolicyDetailService.getPolicyInfo(request, managingOrganization.getMagOrgId(), expReqLine.getCompanyId(), expReqLine.getAccEntityId(), employeeAssign.getEmployeeJobId(), employeeAssign.getEmployeeLevelsId(), expReqLine.getPlaceId(), expReqLine.getPlaceTypeId(), expReqLine.getPositionId(), expReqLine.getUnitId(), null, ExpRequisitionHeader.FIELD_EXP_REQ, expMoReqType.getMoExpReqTypeId(), expReqLine.getMoExpenseTypeId(), null, expReqLine.getMoReqItemId());
            ExpMoExpPolicyDetail policyDetail = new ExpMoExpPolicyDetail();
            if (expPolicyDetails != null && !expPolicyDetails.isEmpty()) {
                policyDetail = expPolicyDetails.get(0);
            }

            BigDecimal newExpenseStandard = policyDetail.getExpenseStandard();
            BigDecimal newLowerLimit = policyDetail.getLowerLimit();
            BigDecimal newUpperLimit = policyDetail.getUpperLimit();
            // 判断币种是否相同
            if (!expReqLine.getPaymentCurrencyCode().equals(policyDetail.getCurrencyCode())) {
                // 根据系统参数获取默认汇率类型
                String defaultExchangeRate = String.valueOf(sysParameterMapper.queryParamValueByCode(ParameterConstants.PARAM_DEFAULT_EXCHANGE_RATE_TYPE, null, null, null, null, null, null, null).get(0).getParameterValue());
                // 获取期间
                String periodName = String.valueOf(gldPeriodMapper.getPeriodName(expReqHeader.getRequisitionDate(), expReqLine.getAccEntityId(), null));
                // 获取汇率
                BigDecimal exchangeRate = gldExchangeRateService.getExchangeRate(expReqLine.getAccEntityId(), policyDetail.getCurrencyCode(), line.getPaymentCurrencyCode(), defaultExchangeRate, expReqHeader.getRequisitionDate(), periodName, "Y");
                // 重新设置费用标准/上下限值
                newExpenseStandard = policyDetail.getExpenseStandard().multiply(exchangeRate);
                newLowerLimit = policyDetail.getLowerLimit().multiply(exchangeRate);
                newUpperLimit = policyDetail.getUpperLimit().multiply(exchangeRate);

            }
            // 超上下限
            if ("Y".equals(policyDetail.getCommitFlag()) && expReqLine.getManagementPrice() != null && policyDetail.getEventName() != null) {
                newLowerLimit = newLowerLimit == null ? expReqLine.getManagementPrice().subtract(BigDecimal.ONE) : newLowerLimit;
                newUpperLimit = newUpperLimit == null ? expReqLine.getManagementPrice().add(BigDecimal.ONE) : newUpperLimit;
                if (expReqLine.getManagementPrice().compareTo(newLowerLimit) == -1 || expReqLine.getManagementPrice().compareTo(newUpperLimit) == 1) {
                    //事件
                }
            }
            newExpenseStandard = newExpenseStandard == null ? expReqLine.getBusinessPrice().subtract(BigDecimal.ONE) : newExpenseStandard;
            // 超标准
            if (expReqLine.getManagementPrice().compareTo(newExpenseStandard) == 1 && policyDetail.getUpperStdEventName() != null) {
                //事件
            }
        }
        return null;
    }

    /**
     * <p>
     * 申请单行费用对象保存
     * <p/>
     *
     * @param request    请求
     * @param expReqLine 申请单行信息
     * @return 保存后的行费用对象list
     * @throws RuntimeException exception
     * @author jiangxz 2019/3/13 10:41
     */
    @Override
    public List<ExpRequisitionObject> saveLineObject(IRequest request, ExpRequisitionLine expReqLine) throws RuntimeException {
        List<ExpRequisitionObject> expReqObjects = new ArrayList<>();
        List<HashMap<String, Object>> objectList = (List<HashMap<String, Object>>) expReqLine.getInnerMap().get("expenseObjectLines");
        if (objectList != null && !objectList.isEmpty()) {
            for (HashMap<String, Object> map : objectList) {
                ExpRequisitionObject reqObject = new ExpRequisitionObject();
                reqObject.setExpRequisitionHeaderId(expReqLine.getExpRequisitionHeaderId());
                reqObject.setExpRequisitionLineId(expReqLine.getExpRequisitionLineId());
                reqObject.setMoExpObjTypeId(Long.valueOf((String) map.get("moExpObjTypeId")));

                // 查询头费用对象是否存在
                int oldObjectCount = expReqObjectMapper.selectCount(reqObject);

                reqObject.setMoExpenseObjectId(Long.valueOf((String) map.get("moExpenseObjectId")));
                reqObject.setMoExpenseObjectName((String) map.get("moExpenseObjectName"));
                // 存在更新
                if (oldObjectCount != 0) {
                    reqObject.setLastUpdatedBy(request.getUserId());
                    reqObject.setLastUpdateDate(new Date());
                    reqObject = expReqObjectService.updateExpReqObject(reqObject);

                } else {
                    reqObject.setCreatedBy(request.getUserId());
                    reqObject.setCreationDate(new Date());
                    reqObject.setLastUpdatedBy(request.getUserId());
                    reqObject.setLastUpdateDate(new Date());
                    reqObject = expReqObjectService.insertSelective(request, reqObject);
                }
                expReqObjects.add(reqObject);
            }
        }
        return expReqObjects;
    }

    /**
     * <p>
     * 更新行维度
     * <p/>
     *
     * @param request
     * @param header  头信息
     * @return 更新完后的行list
     * @author jiangxz 2019/4/16 9:54
     */

    @Override
    public List<ExpRequisitionLine> updateLineDim(IRequest request, ExpRequisitionHeader header) {
        List<ExpRequisitionLine> lineList = new ArrayList<>();
        if(header!=null){
            lineList=mapper.expReqLineQuery(header.getExpRequisitionHeaderId());
            if(!lineList.isEmpty()){
                for(ExpRequisitionLine line:lineList){
                    line.setDimension1Id(header.getDimension1Id() == null ? line.getDimension1Id()
                            : header.getDimension1Id());
                    line.setDimension2Id(header.getDimension2Id() == null ? line.getDimension2Id()
                            : header.getDimension2Id());
                    line.setDimension3Id(header.getDimension3Id() == null ? line.getDimension3Id()
                            : header.getDimension3Id());
                    line.setDimension4Id(header.getDimension4Id() == null ? line.getDimension4Id()
                            : header.getDimension4Id());
                    line.setDimension5Id(header.getDimension5Id() == null ? line.getDimension5Id()
                            : header.getDimension5Id());
                    line.setDimension6Id(header.getDimension6Id() == null ? line.getDimension6Id()
                            : header.getDimension6Id());
                    line.setDimension7Id(header.getDimension7Id() == null ? line.getDimension7Id()
                            : header.getDimension7Id());
                    line.setDimension8Id(header.getDimension8Id() == null ? line.getDimension8Id()
                            : header.getDimension8Id());
                    line.setDimension9Id(header.getDimension9Id() == null ? line.getDimension9Id()
                            : header.getDimension9Id());
                    line.setDimension10Id(header.getDimension10Id() == null ? line.getDimension10Id()
                            : header.getDimension10Id());
                    line.setDimension11Id(header.getDimension11Id() == null ? line.getDimension11Id()
                            : header.getDimension11Id());
                    line.setDimension12Id(header.getDimension12Id() == null ? line.getDimension12Id()
                            : header.getDimension12Id());
                    line.setDimension13Id(header.getDimension13Id() == null ? line.getDimension13Id()
                            : header.getDimension13Id());
                    line.setDimension14Id(header.getDimension14Id() == null ? line.getDimension14Id()
                            : header.getDimension14Id());
                    line.setDimension15Id(header.getDimension15Id() == null ? line.getDimension15Id()
                            : header.getDimension15Id());
                    line.setDimension16Id(header.getDimension16Id() == null ? line.getDimension16Id()
                            : header.getDimension16Id());
                    line.setDimension17Id(header.getDimension17Id() == null ? line.getDimension17Id()
                            : header.getDimension17Id());
                    line.setDimension18Id(header.getDimension18Id() == null ? line.getDimension18Id()
                            : header.getDimension18Id());
                    line.setDimension19Id(header.getDimension19Id() == null ? line.getDimension19Id()
                            : header.getDimension19Id());
                    line.setDimension20Id(header.getDimension20Id() == null ? line.getDimension20Id()
                            : header.getDimension20Id());
                    line.setLastUpdatedBy(request.getUserId());
                    line.setLastUpdateDate(new Date());
                    self().updateByPrimaryKey(request, line);
                    //更新扩展行
                    expReqDistService.updateDisLineDim(request, line);
                }
            }
            return mapper.expReqLineQuery(header.getExpRequisitionHeaderId());
        }
        return null;
    }
    /**
     * <p>
     * 根据头维度设置行维度
     * <p/>
     *
     * @param header 报销单头信息
     * @param line 报销单行信息
     * @author yang.duan 2019/3/11 17:02
     */
    public ExpRequisitionLine setDimFromHeaderToLine(ExpRequisitionHeader header, ExpRequisitionLine line) {
        line.setDimension1Id(header.getDimension1Id() != null ? header.getDimension1Id() : line.getDimension1Id());
        line.setDimension2Id(header.getDimension2Id() != null ? header.getDimension2Id() : line.getDimension2Id());
        line.setDimension3Id(header.getDimension3Id() != null ? header.getDimension3Id() : line.getDimension3Id());
        line.setDimension4Id(header.getDimension4Id() != null ? header.getDimension4Id() : line.getDimension4Id());
        line.setDimension5Id(header.getDimension5Id() != null ? header.getDimension5Id() : line.getDimension5Id());
        line.setDimension6Id(header.getDimension6Id() != null ? header.getDimension6Id() : line.getDimension6Id());
        line.setDimension7Id(header.getDimension7Id() != null ? header.getDimension7Id() : line.getDimension7Id());
        line.setDimension8Id(header.getDimension8Id() != null ? header.getDimension8Id() : line.getDimension8Id());
        line.setDimension9Id(header.getDimension9Id() != null ? header.getDimension9Id() : line.getDimension9Id());
        line.setDimension10Id(header.getDimension10Id() != null ? header.getDimension10Id() : line.getDimension10Id());
        line.setDimension11Id(header.getDimension11Id() != null ? header.getDimension11Id() : line.getDimension11Id());
        line.setDimension12Id(header.getDimension12Id() != null ? header.getDimension12Id() : line.getDimension12Id());
        line.setDimension13Id(header.getDimension13Id() != null ? header.getDimension13Id() : line.getDimension13Id());
        line.setDimension14Id(header.getDimension14Id() != null ? header.getDimension14Id() : line.getDimension14Id());
        line.setDimension15Id(header.getDimension15Id() != null ? header.getDimension15Id() : line.getDimension15Id());
        line.setDimension16Id(header.getDimension16Id() != null ? header.getDimension16Id() : line.getDimension16Id());
        line.setDimension17Id(header.getDimension17Id() != null ? header.getDimension17Id() : line.getDimension17Id());
        line.setDimension18Id(header.getDimension18Id() != null ? header.getDimension18Id() : line.getDimension18Id());
        line.setDimension19Id(header.getDimension19Id() != null ? header.getDimension19Id() : line.getDimension19Id());
        line.setDimension20Id(header.getDimension20Id() != null ? header.getDimension20Id() : line.getDimension20Id());
        return line;
    }
    /**
     * <p>
     * 标准行保存(包括费用对象保存)
     * <p/>
     *
     * @param request 请求
     * @param header 申请头信息
     * @param reqType 申请单类型
     * @return 保存后的行list
     * @throws RuntimeException exception
     * @author jiangxz 2019/4/16 15:17
     */
    @Override
    public List<ExpRequisitionLine> saveStandardLines(IRequest request, ExpRequisitionHeader header, ExpMoReqType reqType) throws RuntimeException {
        List<ExpRequisitionLine> expReqLineList = new ArrayList<>();
        expReqLineList = header.getStandardLines();
        for (ExpRequisitionLine expReqLine : expReqLineList) {
        //维度
            setDimFromHeaderToLine(header,expReqLine);
            if (DTOStatus.ADD.equals(expReqLine.get__status())) {
                this.insertExpRequisitionLine(request, expReqLine, header, reqType);

            }
            if (DTOStatus.UPDATE.equals(expReqLine.get__status())) {
                this.updateExpRequisitionLine(request, expReqLine, header, reqType);
            }
            //费用对象保存
            this.saveLineObject(request, expReqLine);
        }
        return expReqLineList;
    }

    /**
     * <p>
     * 申请单行删除
     * <p/>
     *
     * @param request
     * @param line
     * @return
     * @author jiangxz 2019/4/16 10:30
     */
    @Override
    public int deleteLine(IRequest request, ExpRequisitionLine line) {
        ExpRequisitionHeader header = new ExpRequisitionHeader();
        header.setExpRequisitionHeaderId(line.getExpRequisitionHeaderId());
        header=ExpReqHeaderService.selectByPrimaryKey(request, header);
        int deleteFlag = -1;
        if (header != null) {
            if (ExpRequisitionHeader.FIELD_STATUS_GENERATE.equals(header.getRequisitionStatus())
                    || ExpRequisitionHeader.FIELD_STATUS_REJECTED.equals(header.getRequisitionStatus())
                    || ExpRequisitionHeader.FIELD_STATUS_TAKEN_BACK.equals(header.getRequisitionStatus())) {
                //删除行费用对象
                ExpRequisitionObject expReqObject = new ExpRequisitionObject();
                expReqObject.setExpRequisitionHeaderId(line.getExpRequisitionHeaderId());
                expReqObject.setExpRequisitionLineId(line.getExpRequisitionLineId());
                List<ExpRequisitionObject> expReqObjects = new ArrayList<>();
                Criteria criteria = new Criteria(expReqObject);
                criteria.where(new WhereField(ExpRequisitionObject.FIELD_EXP_REQUISITION_HEADER_ID, Comparison.EQUAL), new WhereField(ExpRequisitionObject.FIELD_EXP_REQUISITION_LINE_ID, Comparison.EQUAL));
                expReqObjects=expReqObjectService.selectOptions(request, expReqObject, criteria);
                expReqObjectService.batchDelete(expReqObjects);
                //删除行扩展行
                List<ExpRequisitionDist> expReqDistList=new ArrayList<>();
                ExpRequisitionDist expReqDist = new ExpRequisitionDist();
                expReqDist.setExpRequisitionLineId(line.getExpRequisitionLineId());
                Criteria criteriaDist = new Criteria(expReqDist);
                criteriaDist.where(new WhereField(ExpRequisitionDist.FIELD_EXP_REQUISITION_LINE_ID));
                expReqDistList=expReqDistService.selectOptions(request, expReqDist, criteriaDist);
                expReqDistService.batchDelete(expReqDistList);
                // 删除预算保留行
                if (expReqDistList != null && !expReqDistList.isEmpty()) {
                    for (ExpRequisitionDist dist : expReqDistList) {
                        BgtBudgetReserve budgetReserve = new BgtBudgetReserve();
                        budgetReserve.setDocumentId(line.getExpRequisitionHeaderId());
                        budgetReserve.setDocumentLineId(dist.getExpRequisitionDistId());
                        List<BgtBudgetReserve> reserveList =
                                bgtBudgetReserveService.select(request, budgetReserve, 1, 0);
                        bgtBudgetReserveService.batchDelete(reserveList);
                    }
                }
                //删除行
                deleteFlag=self().deleteByPrimaryKey(line);
            }
        }

        return deleteFlag;
    }

    /**
     * <p>报销单关联申请单行查询(头关联)</p>
     *
     * @param reportPageElementCode
     * @param expRequisitionHeaderId
     * @param moExpReportTypeId
     * @param monopolizeFlag
     * @param paymentCurrencyCode
     * @param uncompletelyReleasedFlag
     * @return List<Map>
     * @author yang.duan 2019/4/26 14:34
     **/
    @Override
    public List<Map> expReportFromReqQuery(IRequest request, String reportPageElementCode, Long moExpReportTypeId, Long expRequisitionHeaderId, String uncompletelyReleasedFlag, String paymentCurrencyCode, String monopolizeFlag) {
        List<Map> requisitionLines = mapper.expReportFromReqQuery(reportPageElementCode, moExpReportTypeId, expRequisitionHeaderId, uncompletelyReleasedFlag, paymentCurrencyCode, monopolizeFlag);
        List<Long> employeeIdList = new ArrayList<>();
        List<Map> lineList = new ArrayList<>();
        //获取当前公司下的授权员工
        ExpDocumentAuthority authority = new ExpDocumentAuthority();
        authority.setDocCategory(ExpRequisitionHeader.FIELD_EXP_REQUISITION);
        authority.setEmployeeId(request.getEmployeeId());
        authority.setCompanyId(request.getCompanyId());
        List<ExpEmployee> authEmployeeList = authorityService.getEmpCurCompAuth(request, authority);
        if (authEmployeeList != null && !authEmployeeList.isEmpty()) {
            for (ExpEmployee employee : authEmployeeList) {
                employeeIdList.add(employee.getEmployeeId());
            }
        }
        if (requisitionLines != null && !requisitionLines.isEmpty()) {
            for (Map map : requisitionLines) {
                if(employeeIdList.contains(map.get("employeeId"))){
                    lineList.add(map);
                }
            }
        }
        return lineList;
    }
}