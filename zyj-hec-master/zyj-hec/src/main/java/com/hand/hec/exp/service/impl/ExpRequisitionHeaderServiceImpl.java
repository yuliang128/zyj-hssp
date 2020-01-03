package com.hand.hec.exp.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.sys.constants.ParameterConstants;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtBudgetReserve;
import com.hand.hec.bgt.mapper.BgtBudgetReserveMapper;
import com.hand.hec.bgt.service.IBgtBudgetReserveService;
import com.hand.hec.exp.dto.*;
import com.hand.hec.exp.exception.ExpRequisitionException;
import com.hand.hec.exp.mapper.*;
import com.hand.hec.exp.service.*;
import com.hand.hec.expm.dto.ExpDocumentAuthority;
import com.hand.hec.expm.dto.ExpReportHeader;
import com.hand.hec.expm.service.IExpDocumentAuthorityService;
import com.hand.hec.fnd.dto.FndDocInfo;
import com.hand.hec.fnd.service.IFndCodingRuleObjectService;
import com.hand.hec.fnd.service.IGldPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpRequisitionHeaderServiceImpl extends BaseServiceImpl<ExpRequisitionHeader> implements IExpRequisitionHeaderService {

    public static final String PROGRESS_STATUS_GENERATE = "GENERATE";
    public static final String PROGRESS_STATUS_TAKEN_BACK = "TAKEN_BACK";
    public static final String PROGRESS_STATUS_REJECTED = "REJECTED";
    public static final String PROGRESS_STATUS_SUBMITTED = "SUBMITTED";
    public static final String PROGRESS_STATUS_COMPLETELY_APPROVED = "COMPLETELY_APPROVED";
    public static final String PROGRESS_STATUS_CLOSED = "CLOSED";

    @Autowired
    private ExpRequisitionObjectMapper ExpReqObjectMapper;

    @Autowired
    private ExpRequisitionHeaderMapper mapper;

    @Autowired
    private BgtBudgetReserveMapper bgtBudgetReserveMapper;

    @Autowired
    private ExpRequisitionLineMapper lineMapper;

    @Autowired
    private ExpRequisitionDistMapper distMapper;

    @Autowired
    private ExpRequisitionReleaseMapper releaseMapper;

    @Autowired
    private IExpRequisitionLineService iExpRequisitionLineService;

    @Autowired
    private IExpDocumentHistoryService iExpDocumentHistoryService;

    @Autowired
    private IGldPeriodService gldPeriodService;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private IExpRequisitionDistService expRequisitionDistService;

    @Autowired
    private IBgtBudgetReserveService bgtBudgetReserveService;

    @Autowired
    private IExpRequisitionLineService expReqLineService;

    @Autowired
    private IExpRequisitionDistService expReqDistService;

    @Autowired
    IFndCodingRuleObjectService codingRuleObjectService;

    @Autowired
    private IFndCompanyService fndCompanyService;

    // 单据历史
    @Autowired
    IExpDocumentHistoryService expDocumentHistoryService;

    @Autowired
    private IExpRequisitionObjectService expReqObjectService;

    @Autowired
    private IExpMoReqTypeService expMoReqTypeService;

    @Autowired
    private IExpDocumentAuthorityService authorityService;


    @Override
    public List<Map> queryByPayReqHeaderId(IRequest request, Long headerId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        // 获取关闭状态
        String closeStatus;
        String closePartStatus = mapper.queryPartCloseFlag(headerId);
        String closeComStatus = mapper.queryComCloseFlag(headerId);
        if (closePartStatus == null || closePartStatus.isEmpty()) {
            closeStatus = "N";
        } else if (closeComStatus == null || closeComStatus.isEmpty()) {
            closeStatus = "C";
        } else {
            closeStatus = closeComStatus;
        }

        return mapper.queryByPayReqHeaderId(headerId, closeStatus);
    }

    @Override
    public List<Map> queryExp5360(IRequest request, ExpRequisitionHeader dto, int pageNum, int pageSize) {
        return mapper.queryExp5360(dto);
    }

    @Override
    public List<Map> queryDetailHead(IRequest request, long headId) {
        return mapper.queryDetailHead(headId);
    }

    // 重置分配行预算占用额
    public void setBgtBalanceReverse(IRequest iRequest, ExpRequisitionDist dist, String sourceType) {
        /*
         * Mantis:0030639:【费用申请单关闭】为手工关闭时，当参数为N， 则应释放在界面上所选的【关闭期间】，而不应当去判断最大打开期间。
         * 当一次性申请在报销单提交时由系统自动关闭时，才会取最大打开期间。
         */
        BgtBudgetReserve bgtBudgetReserve = new BgtBudgetReserve();
        bgtBudgetReserve = bgtBudgetReserveService.getBudgetReserveSum("EXP_REQUISITION", dist.getExpRequisitionDistId());
        BigDecimal releaseAmount = bgtBudgetReserve.getAmountSum();
        BigDecimal releaseQuantity = bgtBudgetReserve.getQuantitySum();
        String periodName = null;
        BigDecimal zero = new BigDecimal(0);
        if (releaseAmount.compareTo(zero) == 0 && releaseQuantity.longValue() == 0) {
            // 通过系统参数去控制报销单反冲时影响预算的期间
            String bgtReverseOriginalPeriod = null;
            bgtReverseOriginalPeriod = sysParameterService.queryParamValueByCode(ParameterConstants.PARAM_BGT_REVERSE_ORIGINAL_PERIOD, null, null, dist.getCompanyId(), null, null, null, null);
            if ("Y".equals(bgtReverseOriginalPeriod)) {
                periodName = dist.getPeriodName();
            } else {
                if ("Y".equals(dist.getOneOffFlag())) {
                    periodName = gldPeriodService.getPeriodName(iRequest, DateUtils.getCurrentDate(), dist.getAccEntityId(), "O");
                } else {
                    periodName = dist.getPeriodName();
                }
            }
            if (periodName != null && !"".equals(periodName)) {
                long bgtOrgId = mapper.getbgtOrgId(dist.getBgtEntityId());
                bgtBudgetReserve.setReserveCompanyId(dist.getCompanyId());
                bgtBudgetReserve.setBgtCenterId(bgtOrgId);
                bgtBudgetReserve.setPeriodName(periodName);
                bgtBudgetReserve.setBusinessType("EXP_REQUISITION");
                bgtBudgetReserve.setReserveFlag("R");
                bgtBudgetReserve.setStatus("P");
                bgtBudgetReserve.setDocumentId(dist.getExpRequisitionLineId());
                bgtBudgetReserve.setDocumentLineId(dist.getExpRequisitionLineId());
                bgtBudgetReserve.setCurrency(dist.getPaymentCurrencyCode());
                bgtBudgetReserve.setBudgetItemId(dist.getBudgetItemId());
                bgtBudgetReserve.setResponsibilityCenterId(dist.getRespCenterId());
                bgtBudgetReserve.setExchangeRateType(dist.getPay2funExchangeType());
                bgtBudgetReserve.setExchangeRate(dist.getPay2funExchangeRate());
                bgtBudgetReserve.setAmount(releaseAmount.multiply(new BigDecimal(-1)));
                bgtBudgetReserve.setFunctionalAmount((releaseAmount.multiply(new BigDecimal(-1))));
                bgtBudgetReserve.setQuantity(releaseQuantity.multiply(new BigDecimal(-1)));
                bgtBudgetReserve.setCompanyId(dist.getCompanyId());
                bgtBudgetReserve.setUnitId(dist.getUnitId());
                bgtBudgetReserve.setPositionId(dist.getPositionId());
                bgtBudgetReserve.setEmployeeId(dist.getEmployeeId());
                bgtBudgetReserve.setDimension1Id(dist.getDimension1Id());
                bgtBudgetReserve.setDimension2Id(dist.getDimension2Id());
                bgtBudgetReserve.setDimension3Id(dist.getDimension3Id());
                bgtBudgetReserve.setDimension4Id(dist.getDimension4Id());
                bgtBudgetReserve.setDimension5Id(dist.getDimension5Id());
                bgtBudgetReserve.setDimension6Id(dist.getDimension6Id());
                bgtBudgetReserve.setDimension7Id(dist.getDimension7Id());
                bgtBudgetReserve.setDimension8Id(dist.getDimension8Id());
                bgtBudgetReserve.setDimension9Id(dist.getDimension9Id());
                bgtBudgetReserve.setDimension10Id(dist.getDimension10Id());
                bgtBudgetReserve.setDimension11Id(dist.getDimension11Id());
                bgtBudgetReserve.setDimension12Id(dist.getDimension12Id());
                bgtBudgetReserve.setDimension13Id(dist.getDimension13Id());
                bgtBudgetReserve.setDimension14Id(dist.getDimension14Id());
                bgtBudgetReserve.setDimension15Id(dist.getDimension15Id());
                bgtBudgetReserve.setDimension16Id(dist.getDimension16Id());
                bgtBudgetReserve.setDimension17Id(dist.getDimension17Id());
                bgtBudgetReserve.setDimension18Id(dist.getDimension18Id());
                bgtBudgetReserve.setDimension19Id(dist.getDimension19Id());
                bgtBudgetReserve.setDimension20Id(dist.getDimension20Id());
                bgtBudgetReserve.setSourceType(sourceType);
                // 插入记录
                bgtBudgetReserveMapper.insertSelective(bgtBudgetReserve);
            }
        }
    }

    @Override
    public void closeDetailHead(IRequest iRequest, List<ExpRequisitionHeader> expRequisitionHeaders) {
        if (!expRequisitionHeaders.isEmpty() && expRequisitionHeaders != null) {
            for (ExpRequisitionHeader expRequisitionHeader : expRequisitionHeaders) {
                List<ExpRequisitionDist> expRequisitionDists = new ArrayList<>();
                expRequisitionDists = expRequisitionDistService.getAllExpRequisitionDist(iRequest, expRequisitionHeader.getExpRequisitionHeaderId());
                if (!expRequisitionDists.isEmpty() && expRequisitionDists != null) {
                    for (ExpRequisitionDist expRequisitionDist : expRequisitionDists) {
                        //校验是否存在未审核的关联的报销单
                        int count = 0;
                        count = expRequisitionDistService.countUnAuditExpReport(iRequest, expRequisitionHeader.getExpRequisitionHeaderId(), expRequisitionDist.getExpRequisitionLineId(), expRequisitionDist.getExpRequisitionDistId());
                        if (count == 0) {
                            expRequisitionDist.setCloseFlag("Y");
                            expRequisitionDist.setCloseDate(DateUtils.getCurrentDate());
                            expRequisitionDistService.updateByPrimaryKeySelective(iRequest, expRequisitionDist);
                            setBgtBalanceReverse(iRequest, expRequisitionDist, "REQ_CLOSE");
                        } else {
                            throw new ExpRequisitionException(ExpRequisitionException.EXP_REQ_CLOSED_CHECK_EXP_REPORT_STATUS_ERROR, ExpRequisitionException.EXP_REQ_CLOSED_CHECK_EXP_REPORT_STATUS_ERROR, null);
                        }
                    }
                }
            }
        }
    }


    /**
     * 申请单查询
     *
     * @param request  上下文
     * @param dto      申请单信息
     * @param pageNum  页码
     * @param pageSize 页数
     * @return List<ExpRequisitionHeader>
     * @author jiangxz 2019-03-19 15:00
     */
    @Override
    public List<ExpRequisitionHeader> queryExpRequisitionMain(IRequest request, ExpRequisitionHeader dto, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ExpRequisitionHeader> expReqHdList = mapper.queryExpRequisitionMain(dto);
        for (int i = 0; i < expReqHdList.size(); i++) {
            expReqHdList.get(i).setProgressStatus(this.getReqProgressStatus(request, expReqHdList.get(i)));
            expReqHdList.get(i).setProgressCount(this.getProgressCount(expReqHdList.get(i).getProgressStatus()));
        }
        return expReqHdList;
    }

    /**
     * 申请单维护查询
     *
     * @param request                上下文
     * @param employeeId             员工id
     * @param expRequisitionHeaderId 申请单头id
     * @param moExpReqTypeId         申请单类型id
     * @param accEntityId            核算主体id
     * @param paymentCurrencyCode    付款币种代码
     * @param pageNum                页码
     * @param pageSize               页数
     * @return List<ExpRequisitionHeader>
     * @author jiangxz 2019-03-26 15:00
     */
    @Override
    public List<ExpRequisitionHeader> expRequisitionHeaderQuery(IRequest request, Long expRequisitionHeaderId, Long moExpReqTypeId, Long accEntityId, String paymentCurrencyCode, Long employeeId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (expRequisitionHeaderId.intValue() == -1) {
            return mapper.expReqHeaderCreateQuery(moExpReqTypeId, accEntityId, paymentCurrencyCode, employeeId);
        } else {
            return mapper.expRequisitionHeaderQuery(expRequisitionHeaderId);
        }
    }

    //获取进度条
    private Long getProgressCount(String expProgressStatus) {
        switch (expProgressStatus) {
            case PROGRESS_STATUS_SUBMITTED:
                return Long.valueOf(50);
            case PROGRESS_STATUS_COMPLETELY_APPROVED:
                return Long.valueOf(75);
            case PROGRESS_STATUS_CLOSED:
                return Long.valueOf(100);
            default:
                return Long.valueOf(25);
        }
    }

    //获取单据状态字段
    private String getReqProgressStatus(IRequest request, ExpRequisitionHeader dto) {
        boolean aBoolean = true;
        boolean bBoolean = true;
        if (PROGRESS_STATUS_COMPLETELY_APPROVED.equals(dto.getRequisitionStatus())) {
            //申请单是否关闭
            //判断分配扩展行关闭状态
            List<ExpRequisitionLine> expReqLineList = expReqLineService.expReqLineQuery(request, dto.getExpRequisitionHeaderId());
            for (ExpRequisitionLine expReqLine : expReqLineList) {
                ExpRequisitionDist expReqDist = new ExpRequisitionDist();
                expReqDist.setExpRequisitionLineId(expReqLine.getExpRequisitionLineId());
                Criteria criteria = new Criteria(expReqDist);
                criteria.where(new WhereField(ExpRequisitionDist.FIELD_EXP_REQUISITION_LINE_ID, Comparison.EQUAL));
                List<ExpRequisitionDist> expReqDistList = expReqDistService.selectOptions(request, expReqDist, criteria);
                for (ExpRequisitionDist expReqDistDto : expReqDistList) {
                    if ("Y".equals(expReqDistDto.getCloseFlag())) {
                        aBoolean = false;
                    } else {
                        bBoolean = false;
                    }
                }
            }
            if (!aBoolean && bBoolean) {
                //行全部关闭
                return PROGRESS_STATUS_CLOSED;
            }
        }
        return dto.getRequisitionStatus();
    }
    //保存头信息

    /**
     * <p>
     * 头保存(包含头费用对象保存)
     * <p/>
     *
     * @param request
     * @param header
     * @param reqType
     * @return ExpRequisitionHeader
     * @throws RuntimeException exception description
     * @author jiangxz 2019/4/16 11:17
     */
    private ExpRequisitionHeader saveHeader(IRequest request, ExpRequisitionHeader header, ExpMoReqType reqType) throws RuntimeException {
        if (header.getExpRequisitionHeaderId().intValue() == -1) {
            ExpRequisitionHeader oldHeader = new ExpRequisitionHeader();
            oldHeader = self().selectByPrimaryKey(request, header);
            if (ExpRequisitionHeader.FIELD_STATUS_GENERATE.equals(header.getRequisitionStatus()) || ExpRequisitionHeader.FIELD_STATUS_REJECTED.equals(header.getRequisitionStatus()) || ExpRequisitionHeader.FIELD_STATUS_TAKEN_BACK.equals(header.getRequisitionStatus())) {
                if ("Y".equals(checkDim(header, oldHeader))) {
                    // 头维度如果发生变化,则更新行/分配行维度
                    expReqLineService.updateLineDim(request, header);
                }
                // 更新报销单头
                header.setLastUpdatedBy(request.getUserId());
                header.setLastUpdateDate(new Date());
                header = self().updateByPrimaryKeySelective(request, header);
            }
        } else {
            FndCompany fndCompany = new FndCompany();
            fndCompany.setCompanyId(header.getCompanyId());
            fndCompany = fndCompanyService.selectByPrimaryKey(request, fndCompany);
            header.setExpRequisitionNumber(codingRuleObjectService.getRuleCode(FndDocInfo.DOC_CATEGORY_EXP_REQUISITION, header.getMoExpReqTypeId().toString(), fndCompany.getMagOrgId(), header.getCompanyId(), null));
            //插入数据
            header.setAuditFlag(reqType.getAutoAuditFlag());
            //先取申请日期
            header.setRequisitionDateLtz(header.getRequisitionDate());
            header.setRequisitionDateTz(header.getRequisitionDate());
            header.setGldInterfaceFlag("N");
            header.setReleasedStatus("N");
            header.setReversedFlag("N");
            header.setCreatedBy(request.getUserId());
            header.setCreationDate(new Date());
            header.setLastUpdatedBy(request.getUserId());
            header.setLastUpdateDate(new Date());
            header = self().insertSelective(request, header);
            // 插入单据历史
            expDocumentHistoryService.insertDocumentHistory(request, ExpRequisitionHeader.FIELD_EXP_REQUISITION, header.getExpRequisitionHeaderId(), ExpDocumentHistory.STATUS_GENERATE, request.getEmployeeId(), header.getDescription());
            //借款申请处理（暂无）
        }

        return header;
    }

    // 校验维度是否发生改变
    private String checkDim(ExpRequisitionHeader header, ExpRequisitionHeader oldHeader) {
        List<Long> dimList = new ArrayList<>();
        List<Long> oldDimList = new ArrayList<>();
        String flag = "N";
        dimList.add(header.getDimension1Id() != null ? header.getDimension1Id() : Long.valueOf(0));
        dimList.add(header.getDimension2Id() != null ? header.getDimension2Id() : Long.valueOf(0));
        dimList.add(header.getDimension3Id() != null ? header.getDimension3Id() : Long.valueOf(0));
        dimList.add(header.getDimension4Id() != null ? header.getDimension4Id() : Long.valueOf(0));
        dimList.add(header.getDimension5Id() != null ? header.getDimension5Id() : Long.valueOf(0));
        dimList.add(header.getDimension6Id() != null ? header.getDimension6Id() : Long.valueOf(0));
        dimList.add(header.getDimension7Id() != null ? header.getDimension7Id() : Long.valueOf(0));
        dimList.add(header.getDimension8Id() != null ? header.getDimension8Id() : Long.valueOf(0));
        dimList.add(header.getDimension9Id() != null ? header.getDimension9Id() : Long.valueOf(0));
        dimList.add(header.getDimension10Id() != null ? header.getDimension10Id() : Long.valueOf(0));
        dimList.add(header.getDimension11Id() != null ? header.getDimension11Id() : Long.valueOf(0));
        dimList.add(header.getDimension12Id() != null ? header.getDimension12Id() : Long.valueOf(0));
        dimList.add(header.getDimension13Id() != null ? header.getDimension13Id() : Long.valueOf(0));
        dimList.add(header.getDimension14Id() != null ? header.getDimension14Id() : Long.valueOf(0));
        dimList.add(header.getDimension15Id() != null ? header.getDimension15Id() : Long.valueOf(0));
        dimList.add(header.getDimension16Id() != null ? header.getDimension16Id() : Long.valueOf(0));
        dimList.add(header.getDimension17Id() != null ? header.getDimension17Id() : Long.valueOf(0));
        dimList.add(header.getDimension18Id() != null ? header.getDimension18Id() : Long.valueOf(0));
        dimList.add(header.getDimension19Id() != null ? header.getDimension19Id() : Long.valueOf(0));
        dimList.add(header.getDimension20Id() != null ? header.getDimension20Id() : Long.valueOf(0));


        oldDimList.add(oldHeader.getDimension1Id() != null ? oldHeader.getDimension1Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension2Id() != null ? oldHeader.getDimension2Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension3Id() != null ? oldHeader.getDimension3Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension4Id() != null ? oldHeader.getDimension4Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension5Id() != null ? oldHeader.getDimension5Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension6Id() != null ? oldHeader.getDimension6Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension7Id() != null ? oldHeader.getDimension7Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension8Id() != null ? oldHeader.getDimension8Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension9Id() != null ? oldHeader.getDimension9Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension10Id() != null ? oldHeader.getDimension10Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension11Id() != null ? oldHeader.getDimension11Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension12Id() != null ? oldHeader.getDimension12Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension13Id() != null ? oldHeader.getDimension13Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension14Id() != null ? oldHeader.getDimension14Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension15Id() != null ? oldHeader.getDimension15Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension16Id() != null ? oldHeader.getDimension16Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension17Id() != null ? oldHeader.getDimension17Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension18Id() != null ? oldHeader.getDimension18Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension19Id() != null ? oldHeader.getDimension19Id() : Long.valueOf(0));
        oldDimList.add(oldHeader.getDimension20Id() != null ? oldHeader.getDimension20Id() : Long.valueOf(0));

        for (int i = 0; i < dimList.size(); i++) {
            if (dimList.get(i).longValue() != oldDimList.get(i).longValue()) {
                flag = "Y";
                break;
            }
        }
        return flag;
    }

    /**
     * <p>
     * 头费用对象保存
     * <p/>
     *
     * @param request 请求
     * @param header  申请单头信息
     * @return 保存后的头费用对象list
     * @throws RuntimeException exception
     * @author jiangxz 2019/4/16 10:08
     */
    private List<ExpRequisitionObject> saveHeaderObject(IRequest request, ExpRequisitionHeader header) throws RuntimeException {
        List<ExpRequisitionObject> reportObjects = new ArrayList<>();
        List<HashMap<String, Object>> objectList = (List<HashMap<String, Object>>) header.getInnerMap().get("expenseObjectHeaders");
        if (objectList != null && !objectList.isEmpty()) {
            for (HashMap<String, Object> map : objectList) {
                ExpRequisitionObject expReqObject = new ExpRequisitionObject();
                expReqObject.setExpRequisitionHeaderId(header.getExpRequisitionHeaderId());
                expReqObject.setMoExpObjTypeId(Long.valueOf((String) map.get("moExpObjTypeId")));
                //查询费用对象是否存在
                int objectCount=ExpReqObjectMapper.selectCount(expReqObject);
                expReqObject.setMoExpenseObjectId(Long.valueOf((Integer) map.get("moExpenseObjectId")));
                expReqObject.setMoExpenseObjectName((String) map.get("moExpenseObjectName"));
                if(objectCount!=0){
                    expReqObject.setLastUpdateDate(new Date());
                    expReqObject.setLastUpdatedBy(request.getUserId());
                    expReqObject=expReqObjectService.updateExpReqObject(expReqObject);
                }else{
                    expReqObject.setCreatedBy(request.getUserId());
                    expReqObject.setCreationDate(new Date());
                    expReqObject.setLastUpdatedBy(request.getUserId());
                    expReqObject.setLastUpdateDate(new Date());
                    expReqObject = expReqObjectService.insertSelective(request, expReqObject);
                }
                reportObjects.add(expReqObject);
            }
        }
        return reportObjects;
    }
    /**
     * <p>
     * 报销单头保存(页面保存)
     * <p/>
     *
     * @param request
     * @param headers 头信息
     * @return 返回保存后的头信息
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/7 14:38
     */
    @Override
    public List<ExpRequisitionHeader> saveReportHeader(IRequest request, List<ExpRequisitionHeader> headers) throws RuntimeException {
        if (headers != null && !headers.isEmpty()) {
            for (ExpRequisitionHeader header : headers) {
                ExpMoReqType expMoReqType = new ExpMoReqType();
                expMoReqType.setMoExpReqTypeId(header.getMoExpReqTypeId());
                expMoReqType = expMoReqTypeService.selectByPrimaryKey(request, expMoReqType);
                //头信息保存
                this.saveHeader(request, header, expMoReqType);
                //标准行保存
                expReqLineService.saveStandardLines(request, header, expMoReqType);
                //
            }
        }
        return headers;
    }
    /**
     * <p>
     * 申请单整单删除
     * <p/>
     *
     * @param request
     * @param header
     * @return
     * @author jiangxz 2019/4/16 10:30
     */
    public int deleteHeader(IRequest request, ExpRequisitionHeader header) {
        //查询头信息
        header = self().selectByPrimaryKey(request, header);
            int deleteFlag = -1;
        if (header != null) {
            if (ExpRequisitionHeader.FIELD_STATUS_GENERATE.equals(header.getRequisitionStatus()) || ExpRequisitionHeader.FIELD_STATUS_REJECTED.equals(header.getRequisitionStatus()) || ExpRequisitionHeader.FIELD_STATUS_TAKEN_BACK.equals(header.getRequisitionStatus())) {
                //删除申请行
                List<ExpRequisitionLine> expReqLineList = new ArrayList<>();
                expReqLineList = expReqLineService.expReqLineQuery(request, header.getExpRequisitionHeaderId());
                for (ExpRequisitionLine expReqLine : expReqLineList) {
                    expReqLineService.deleteLine(request, expReqLine);
                }
                //删除单据历史
                deleteFlag = self().deleteByPrimaryKey(header);
            }
        }
        return deleteFlag;
    }

    /**
     * <p>报销单关联申请单查询(头关联)</p>
     *
     * @param request
     * @param moExpReportTypeId
     * @param expRequisitionNumber
     * @param description
     * @return List<ExpRequisitionHeader>
     * @author yang.duan 2019/4/25 14:50
     **/
    @Override
    public List<ExpRequisitionHeader> expReportFromReqHeaderQuery(IRequest request, Long moExpReportTypeId, String expRequisitionNumber, String paymentCurrencyCode, String description) {
        List<ExpRequisitionHeader> requisitionHeaders = mapper.expReportFromReqHeaderQuery(moExpReportTypeId, expRequisitionNumber, paymentCurrencyCode, description);
        List<Long> employeeIdList = new ArrayList<>();
        List<ExpRequisitionHeader> headerList = new ArrayList<>();
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

        if (requisitionHeaders != null && !requisitionHeaders.isEmpty()) {
            for (ExpRequisitionHeader header : requisitionHeaders) {
                if (employeeIdList.contains(header.getEmployeeId())) {
                    headerList.add(header);
                }
            }
        }

        return headerList;
    }
}
