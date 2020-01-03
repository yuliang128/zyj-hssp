package com.hand.hec.bgt.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.sys.constants.ParameterConstants;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.ICodeService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.*;
import com.hand.hec.bgt.exception.BgtJournalHeaderException;
import com.hand.hec.bgt.exception.BgtJournalNotExistsException;
import com.hand.hec.bgt.mapper.BgtJournalHeaderMapper;
import com.hand.hec.bgt.service.*;
import com.hand.hec.exp.dto.ExpDocumentHistory;
import com.hand.hec.exp.dto.ExpOrgPosition;
import com.hand.hec.exp.dto.ExpOrgUnit;
import com.hand.hec.exp.service.IExpDocumentHistoryService;
import com.hand.hec.exp.service.IExpOrgPositionService;
import com.hand.hec.exp.service.IExpOrgUnitService;
import com.hand.hec.fnd.dto.FndDimension;
import com.hand.hec.fnd.dto.FndDimensionValue;
import com.hand.hec.fnd.dto.FndDocInfo;
import com.hand.hec.fnd.service.IFndCodingRuleObjectService;
import com.hand.hec.fnd.service.IFndDimensionService;
import com.hand.hec.fnd.service.IFndDimensionValueService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 预算日记账头ServiceImpl
 * </p>
 *
 * @author mouse 2019/01/07 16:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtJournalHeaderServiceImpl extends BaseServiceImpl<BgtJournalHeader> implements IBgtJournalHeaderService {

    @Autowired
    BgtJournalHeaderMapper mapper;

    @Autowired
    IBgtEntityService entityService;

    @Autowired
    IBgtCenterService centerService;

    @Autowired
    IExpOrgUnitService unitService;

    @Autowired
    IFndCompanyService companyService;

    @Autowired
    IBgtJournalTypeService typeService;

    @Autowired
    IBgtStructureService structureService;

    @Autowired
    ICodeService codeService;

    @Autowired
    IExpOrgPositionService positionService;

    @Autowired
    IBgtJournalObjectService objectService;

    @Autowired
    IBgtJournalLineService lineService;

    @Autowired
    IFndCodingRuleObjectService codingRuleObjectService;

    @Autowired
    IExpDocumentHistoryService historyService;

    @Autowired
    IBgtJournalBalanceService journalBalanceService;

    @Autowired
    private ISysParameterService parameterService;

    @Autowired
    private IBgtEntityService bgtEntityService;

    @Autowired
    private IBgtCenterService bgtCenterService;

    @Autowired
    private IBgtBudgetItemService bgtBudgetItemService;

    @Autowired
    private IFndDimensionService fndDimensionService;

    @Autowired
    private IFndDimensionValueService fndDimensionValueService;

    @Autowired
    private IBgtBudgetReserveService budgetReserveService;

    @Autowired
    private IBgtCheckService bgtCheckService;

    @Autowired
    private IBgtJournalTypeService bgtJournalTypeService;

    @Override
    public List<BgtJournalHeader> queryMain(IRequest request, BgtJournalHeader dto, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        return mapper.queryJournalMain(dto);
    }


    @Override
    public BgtJournalHeader queryJournalHeader(IRequest request, BgtJournalHeader dto) {
        BgtJournalHeader header = dto;

        if (header.getJournalHeaderId() == null) {
            // 如果预算日记账头id不存在，按照默认值进行处理
            header.setBgtOrgId(dto.getBgtOrgId());

            BgtJournalType type = typeService.getBgtJournalType(request, dto.getBgtJournalTypeId());
            if (type != null) {
                header.setBgtJournalTypeId(type.getBgtJournalTypeId());
                header.setBgtJournalTypeName(type.getDescription());
                header.setAuthorityType(type.getAuthorityType());
            }

            BgtStructure structure = structureService.getBgtStructure(request, dto.getStructureId());
            if (structure != null) {
                header.setStructureId(structure.getStructureId());
                header.setStructureName(structure.getDescription());
                header.setPeriodStrategy(structure.getPeriodStrategy());
                header.setPeriodStrategyName(codeService.getCodeDescByValue(request, "BUDGET_PERIOD",
                                structure.getPeriodStrategy()));
                header.setEntityFlag(structure.getEntityFlag());
                header.setCenterFlag(structure.getCenterFlag());
            }

            // 默认公司
            FndCompany defaultCompany = companyService.getCompany(request, request.getCompanyId());
            if (defaultCompany != null) {
                header.setCompanyId(defaultCompany.getCompanyId());
                header.setCompanyName(defaultCompany.getCompanyShortName());
            }

            // 默认部门
            ExpOrgUnit defaultUnit =
                            unitService.getDefaultUnit(request, request.getEmployeeId(), request.getCompanyId());
            if (defaultUnit != null) {
                header.setUnitId(defaultUnit.getUnitId());
                header.setUnitName(defaultUnit.getDescription());
            }

            // 默认岗位
            ExpOrgPosition defaultPosition = positionService.getDefaultPosition(request, request.getEmployeeId(),
                            request.getCompanyId());
            if (defaultPosition != null) {
                header.setPositionId(defaultPosition.getPositionId());
                header.setPositionName(defaultPosition.getDescription());
            }

            // 默认预算实体
            BgtEntity defaultEntity = entityService.queryDefaultBgtEntity(request, request.getCompanyId());
            if (defaultEntity != null) {
                header.setBgtEntityId(defaultEntity.getEntityId());
                header.setBgtEntityName(defaultEntity.getDescription());
            }

            // 默认预算中心
            if (defaultUnit != null && defaultEntity != null) {
                BgtCenter defaultCenter = centerService.getDefaultCenterByUnit(request, defaultUnit.getUnitId(),
                                defaultEntity.getEntityId());

                if (defaultCenter != null) {
                    header.setBgtCenterId(defaultCenter.getCenterId());
                    header.setBgtCenterName(defaultCenter.getDescription());
                }
            }

            header.setStatus(BgtJournalHeader.STATUS_NEW);
            header.setStatusName(codeService.getCodeDescByValue(request, "BGT_BUDGET_STATUS", BgtJournalHeader.STATUS_NEW));

            header.setReversedFlag("N");

            header.setEmployeeId(request.getEmployeeId());
            header.setEmployeeName(request.getEmployeeName());

            for (Long i = 1L; i <= 20L; i++) {
                FndDimensionValue value =
                                structureService.getDefaultDimensionValue(request, structure.getStructureId(), i);
                if (value != null) {
                    try {
                        BeanUtils.setProperty(header, "dimension" + i + "Id", value.getDimensionValueId());
                        BeanUtils.setProperty(header, "dimension" + i + "Name", value.getDescription());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException ex) {
                        ex.printStackTrace();
                    }
                }
            }

        } else {
            // 如果预算日记账头存在，获取对应的描述字段
            Criteria criteria = new Criteria(dto);
            List<BgtJournalHeader> headerList = self().selectOptions(request, dto, criteria);
            if (headerList == null || headerList.size() == 0) {
                throw new BgtJournalNotExistsException("BGT", "bgt_journal_header.not_exists", null);
            }

            header = headerList.get(0);
        }

        return header;
    }

    @Override
    public BgtJournalHeader saveJournalHeader(IRequest requestCtx, BgtJournalHeader header) {

        //
        // 如果当前预算日记账的头ID为空，那么设置预算日记账编码
        // ------------------------------------------------------------------------------
        if (header.getJournalHeaderId() == null) {
            FndCompany company = companyService.getCompany(requestCtx, header.getCompanyId());
            header.setBudgetJournalNumber(codingRuleObjectService.getRuleCode(FndDocInfo.DOC_CATEGORY_BGT_JOURNAL,
                            header.getBgtJournalTypeId().toString(), company.getMagOrgId(), header.getCompanyId(),
                            null));
        }


        if (header.getJournalHeaderId() == null) {
            header = self().insertSelective(requestCtx, header);

            //
            // 插入单据历史信息
            // ------------------------------------------------------------------------------
            historyService.insertDocumentHistory(requestCtx, ExpDocumentHistory.DOCUMENT_TYPE_BUDGET_JOURNAL,
                            header.getJournalHeaderId(), ExpDocumentHistory.STATUS_GENERATE, requestCtx.getEmployeeId(),
                            header.getJournalHeaderNotes());
        } else {
            header = self().updateByPrimaryKey(requestCtx, header);
        }

        Map headerMap = header.getInnerMap();

        //
        // 更新费用对象数据
        // ------------------------------------------------------------------------------
        for (Object keyObj : headerMap.keySet()) {
            String key = keyObj.toString();
            if (key.startsWith("^#")) {
                Long objTypeId = Long.parseLong(key.replace("^#", ""));
                String objDesc = headerMap.get("^#" + objTypeId).toString();
                Boolean updateFlag = false;
                Long objId = null;
                if (headerMap.get("#" + objTypeId) != null) {
                    objId = Long.parseLong(headerMap.get("#" + objTypeId).toString());
                }
                BgtJournalObject obj = new BgtJournalObject();
                obj.setJournalHeaderId(header.getJournalHeaderId());
                obj.setExpenseObjectTypeId(objTypeId);

                // 如果本次预算日记账为修改，则需要判断本次费用对象是新增还是修改
                // 根据预算日记账头ID和费用对象类型查询费用对象数据，判断本次为新增还是修改
                // ------------------------------------------------------------------------------
                if (obj.getJournalHeaderId() != null) {
                    List<BgtJournalObject> objList = objectService.select(requestCtx, obj, 0, 0);
                    for (BgtJournalObject resObj : objList) {
                        //
                        // 如果预算日记账行ID为空，说明本次数据是头上的，且为更新
                        // ------------------------------------------------------------------------------
                        if (resObj.getJournalLineId() == null) {
                            updateFlag = true;
                        }

                        obj.setJournalObjectId(resObj.getJournalObjectId());
                    }
                }

                obj.setExpenseObjectDesc(objDesc);
                obj.setExpenseObjectId(objId);

                if (updateFlag) {
                    objectService.updateByPrimaryKey(requestCtx, obj);
                } else {
                    objectService.insert(requestCtx, obj);
                }
            }
        }

        //
        // 更新预算日记账行信息
        // ------------------------------------------------------------------------------
        if (header.getJournalLines() != null) {
            List<BgtJournalLine> journalLines = header.getJournalLines();
            for (BgtJournalLine journalLine : journalLines) {
                journalLine.setJournalHeaderId(header.getJournalHeaderId());
                journalLine.setBgtOrgId(header.getBgtOrgId());
                lineService.saveJournalLine(requestCtx, journalLine);
            }
        }

        return header;
    }


    @Override
    public int deleteByPrimaryKey(BgtJournalHeader dto) {

        //
        // 级联删除行信息和费用对象信息
        // ------------------------------------------------------------------------------
        BgtJournalLine line = new BgtJournalLine();
        line.setJournalHeaderId(dto.getJournalHeaderId());
        List<BgtJournalLine> lineList = lineService.select(getRequest(), line, 0, 0);
        lineService.setRequest(getRequest());
        if (lineList != null && lineList.size() != 0) {
            lineService.batchDelete(lineList);
        }

        BgtJournalObject object = new BgtJournalObject();
        object.setJournalHeaderId(dto.getJournalHeaderId());
        List<BgtJournalObject> objectList = objectService.select(getRequest(), object, 0, 0);

        if (objectList != null && objectList.size() != 0) {
            objectService.batchDelete(objectList);
        }

        return super.deleteByPrimaryKey(dto);
    }

    @Override
    public List<BgtJournalHeader> setBgtJournalStatus(IRequest request, List<BgtJournalHeader> list, String status) {
        for (BgtJournalHeader dto : list) {
            BgtJournalHeader bgtJournalHeader = self().selectByPrimaryKey(request,dto);
            switch (status) {
                case ExpDocumentHistory.STATUS_APPROVE:
                    this.approveBgtJournal(request, bgtJournalHeader);
                    break;
                case ExpDocumentHistory.STATUS_APPROVAL_REJECT:
                    this.rejectBgtJournal(request, bgtJournalHeader);
                    break;
                case ExpDocumentHistory.STATUS_SUBMIT:
                    try {
                        this.submitBgtJournal(request, bgtJournalHeader);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                case ExpDocumentHistory.STATUS_TAKEN_BACK:
                    this.withdrawalBgtJournal(request, bgtJournalHeader);
                    break;
                case ExpDocumentHistory.STATUS_APPROVAL_CANCEL:
                    this.approveCancelBgtJournal(request, bgtJournalHeader);
                    break;
                case ExpDocumentHistory.STATUS_POST:
                    this.postBgtJournal(request, bgtJournalHeader);
                    break;
                case ExpDocumentHistory.STATUS_REVERSE:
                    this.reversedBgtJournal(request, bgtJournalHeader);
                    break;
                default:
                    break;
            }
        }

        return list;
    }

    @Override
    public List<BgtJournalHeader> queryJournalForApprove(IRequest request, BgtJournalHeader header, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryJournalForApprove(header);
    }

    /**
     * 预算日记账提交
     *
     * @param bgtJournalHeader
     * @return
     * @author guiyuting 2019-03-27 19:18
     */
    private BgtJournalHeader submitBgtJournal(IRequest request, BgtJournalHeader bgtJournalHeader)
                    throws NoSuchFieldException, IllegalAccessException {
        Map<String, String> validateResult = this.validateJournalBgtCheck(request, bgtJournalHeader);
        if (validateResult.get("checkResult") == null) {
            throw new BgtJournalHeaderException("条件为：" + validateResult.get("checkCondition") + "的记录出现以下异常:"
                            + validateResult.get("checkMessage"), null);
        }
        if (this.bgtJournalCanModifyChk(request, bgtJournalHeader)) {
            // 调整型的预算日记账总金额需为‘0’
            this.bgtAmountCheck(request,bgtJournalHeader);
            BgtJournalType bgtJournalType = bgtJournalTypeService.queryInfoByBgtJournal(bgtJournalHeader.getJournalHeaderId());
            String status = "",actionStatus = "";
            if(bgtJournalType.getAutoApproveFlag().equals(BaseConstants.YES)){
                status = BgtJournalHeader.STATUS_APPROVED;
                actionStatus = ExpDocumentHistory.STATUS_APPROVE;
            }else{
                status = BgtJournalHeader.STATUS_SUBMITTED;
                actionStatus = ExpDocumentHistory.STATUS_SUBMIT;
            }

            // 更新状态
            bgtJournalHeader.setStatus(status);
            self().updateByPrimaryKey(request, bgtJournalHeader);
            // 插入单据历史
            historyService.insertDocumentHistory(request, ExpDocumentHistory.DOCUMENT_TYPE_BUDGET_JOURNAL,
                    bgtJournalHeader.getJournalHeaderId(), actionStatus,
                    request.getEmployeeId(), bgtJournalHeader.getJournalHeaderNotes());
            if(!bgtJournalType.getAutoApproveFlag().equals(BaseConstants.YES)){
                /**TODO:调用工作流流程
                 *  guiyuting
                 * */
            }
        }

        return bgtJournalHeader;
    }

    /**
     * 预算日记账收回
     *
     * @param bgtJournalHeader
     * @return
     * @author guiyuting 2019-03-27 19:18
     */
    private BgtJournalHeader withdrawalBgtJournal(IRequest request, BgtJournalHeader bgtJournalHeader) {
        int num = mapper.selectCount(BgtJournalHeader.builder().journalHeaderId(bgtJournalHeader.getJournalHeaderId())
                        .status(BgtJournalHeader.STATUS_SUBMITTED).build());

        if (num == 0) {
            throw new BgtJournalHeaderException(BgtJournalHeaderException.WITHDRAWAL_ERROR, null);
        }
        // 更新状态
        bgtJournalHeader.setStatus(BgtJournalHeader.STATUS_WITHDRAWAL);
        self().updateByPrimaryKey(request, bgtJournalHeader);
        return bgtJournalHeader;
    }

    /**
     * 预算日记账审批拒绝
     *
     * @param bgtJournalHeader
     * @return
     * @author guiyuting 2019-03-27 19:18
     */
    private BgtJournalHeader rejectBgtJournal(IRequest request, BgtJournalHeader bgtJournalHeader) {
        int num = mapper.selectCount(BgtJournalHeader.builder().journalHeaderId(bgtJournalHeader.getJournalHeaderId())
                        .status(BgtJournalHeader.STATUS_SUBMITTED).build());

        if (num == 0) {
            throw new BgtJournalHeaderException(BgtJournalHeaderException.REJECTED_ERROR, null);
        }
        // 更新状态
        bgtJournalHeader.setStatus(BgtJournalHeader.STATUS_REJECTED);
        bgtJournalHeader.setApprovedDate(null);
        bgtJournalHeader.setApprovedBy(null);
        self().updateByPrimaryKey(request, bgtJournalHeader);
        // 插入单据历史
        historyService.insertDocumentHistory(request, ExpDocumentHistory.DOCUMENT_TYPE_BUDGET_JOURNAL,
                        bgtJournalHeader.getJournalHeaderId(), ExpDocumentHistory.STATUS_APPROVAL_REJECT,
                        request.getEmployeeId(), bgtJournalHeader.getJournalHeaderNotes());
        return bgtJournalHeader;
    }

    /**
     * 预算日记账审批
     *
     * @param bgtJournalHeader
     * @return
     * @author guiyuting 2019-03-27 19:18
     */
    private BgtJournalHeader approveBgtJournal(IRequest request, BgtJournalHeader bgtJournalHeader) {
        int num = mapper.selectCount(BgtJournalHeader.builder().journalHeaderId(bgtJournalHeader.getJournalHeaderId())
                        .status(BgtJournalHeader.STATUS_SUBMITTED).build());

        if (num == 0) {
            throw new BgtJournalHeaderException(BgtJournalHeaderException.APPROVED_ERROR, null);
        }
        // 更新状态
        bgtJournalHeader.setStatus(BgtJournalHeader.STATUS_APPROVED);
        bgtJournalHeader.setApprovedDate(new Date());
        bgtJournalHeader.setApprovedBy(request.getUserId());
        self().updateByPrimaryKey(request, bgtJournalHeader);
        // 插入单据历史
        historyService.insertDocumentHistory(request, ExpDocumentHistory.DOCUMENT_TYPE_BUDGET_JOURNAL,
                        bgtJournalHeader.getJournalHeaderId(), ExpDocumentHistory.STATUS_APPROVE,
                        request.getEmployeeId(), bgtJournalHeader.getJournalHeaderNotes());
        return bgtJournalHeader;
    }

    /**
     * 预算日记账取消审批
     *
     * @param bgtJournalHeader
     * @return
     * @author guiyuting 2019-03-27 19:18
     */
    private BgtJournalHeader approveCancelBgtJournal(IRequest request, BgtJournalHeader bgtJournalHeader) {
        int num = mapper.selectCount(BgtJournalHeader.builder().journalHeaderId(bgtJournalHeader.getJournalHeaderId())
                        .status(BgtJournalHeader.STATUS_APPROVED).build());

        if (num == 0) {
            throw new BgtJournalHeaderException(BgtJournalHeaderException.APPROVE_CANCEL_ERROR, null);
        }
        // 更新状态
        bgtJournalHeader.setStatus(BgtJournalHeader.STATUS_SUBMITTED);
        bgtJournalHeader.setApprovedDate(null);
        bgtJournalHeader.setApprovedBy(null);
        self().updateByPrimaryKey(request, bgtJournalHeader);
        // 插入单据历史
        historyService.insertDocumentHistory(request, ExpDocumentHistory.DOCUMENT_TYPE_BUDGET_JOURNAL,
                        bgtJournalHeader.getJournalHeaderId(), ExpDocumentHistory.STATUS_APPROVAL_CANCEL,
                        request.getEmployeeId(), bgtJournalHeader.getJournalHeaderNotes());
        return bgtJournalHeader;
    }

    /**
     * 预算日记账过账
     *
     * @param bgtJournalHeader
     * @return
     * @author guiyuting 2019-03-27 19:18
     */
    private BgtJournalHeader postBgtJournal(IRequest request, BgtJournalHeader bgtJournalHeader) {
        int num = mapper.selectCount(BgtJournalHeader.builder().journalHeaderId(bgtJournalHeader.getJournalHeaderId())
                        .status(BgtJournalHeader.STATUS_APPROVED).build());

        if (num == 0) {
            throw new BgtJournalHeaderException(BgtJournalHeaderException.POSTED_ERROR, null);
        }
        // 创建预算BALANCE
        journalBalanceService.createJournalBalance(request, bgtJournalHeader.getJournalHeaderId());
        // 更新状态
        bgtJournalHeader.setStatus(BgtJournalHeader.STATUS_POSTED);
        self().updateByPrimaryKey(request, bgtJournalHeader);
        // 插入单据历史
        historyService.insertDocumentHistory(request, ExpDocumentHistory.DOCUMENT_TYPE_BUDGET_JOURNAL,
                        bgtJournalHeader.getJournalHeaderId(), ExpDocumentHistory.STATUS_POST, request.getEmployeeId(),
                        bgtJournalHeader.getJournalHeaderNotes());
        return bgtJournalHeader;
    }


    /**
     * 预算日记账反冲
     *
     * @param bgtJournalHeader
     * @author guiyuting 2019-04-02 10:56
     * @return
     */
    private BgtJournalHeader reversedBgtJournal(IRequest request, BgtJournalHeader bgtJournalHeader) {
        BgtJournalHeader queryHeader = self().selectByPrimaryKey(request,
                        BgtJournalHeader.builder().journalHeaderId(bgtJournalHeader.getJournalHeaderId()).build());

        boolean existsFlag = queryHeader.getStatus().equals(BgtJournalHeader.STATUS_POSTED)
                        && (queryHeader.getReversedFlag() == null
                                        || queryHeader.getReversedFlag().equals(BaseConstants.NO));
        if (!existsFlag) {
            throw new BgtJournalHeaderException(BgtJournalHeaderException.REVERSED_ERROR, null);
        }
        // 反冲头
        BgtJournalHeader reserveHeader = new BgtJournalHeader();
        org.springframework.beans.BeanUtils.copyProperties(queryHeader, reserveHeader);
        reserveHeader.setJournalHeaderId(null);
        reserveHeader.setReversedFlag("R");
        reserveHeader.setJournalHeaderNotes(bgtJournalHeader.getReason());
        reserveHeader = self().insertSelective(request, reserveHeader);
        // 冲销头费用对象
        List<BgtJournalObject> journalObjectList = objectService.select(request,
                        BgtJournalObject.builder().journalHeaderId(bgtJournalHeader.getJournalHeaderId()).build(), 0,
                        0);
        for (BgtJournalObject journalObject : journalObjectList) {
            journalObject.setJournalHeaderId(reserveHeader.getJournalHeaderId());
            journalObject.setJournalLineId(null);
            journalObject.setJournalObjectId(null);
            objectService.insertSelective(request, journalObject);
        }
        // 反冲行
        List<BgtJournalLine> reserveLines = lineService.select(request,
                        BgtJournalLine.builder().journalHeaderId(bgtJournalHeader.getJournalHeaderId()).build(), 0, 0);
        for (BgtJournalLine bgtJournalLine : reserveLines) {
            bgtJournalLine.setAmount(bgtJournalLine.getAmount().negate());
            bgtJournalLine.setFunctionalAmount(bgtJournalLine.getFunctionalAmount().negate());
            bgtJournalLine.setQuantity(bgtJournalLine.getQuantity().negate());
            bgtJournalLine.setJournalHeaderId(reserveHeader.getJournalHeaderId());
            BgtJournalLine reserveLine = lineService.insertSelective(request, bgtJournalLine);

            // 冲销行费用对象
            List<BgtJournalObject> journalLineObjectList = objectService.select(request,
                            BgtJournalObject.builder().journalHeaderId(bgtJournalHeader.getJournalHeaderId())
                                            .journalLineId(bgtJournalLine.getJournalLineId()).build(),
                            0, 0);
            for (BgtJournalObject lineObject : journalLineObjectList) {
                lineObject.setJournalHeaderId(reserveHeader.getJournalHeaderId());
                lineObject.setJournalLineId(reserveLine.getJournalLineId());
                lineObject.setJournalObjectId(null);
                objectService.insertSelective(request, lineObject);
            }
        }

        // 更新状态
        bgtJournalHeader.setReversedFlag("W");
        bgtJournalHeader.setStatus(BgtJournalHeader.STATUS_REVERSED);
        self().updateByPrimaryKey(request, bgtJournalHeader);

        // 创建预算BALANCE
        journalBalanceService.createJournalBalance(request, reserveHeader.getJournalHeaderId());

        // 插入单据历史
        historyService.insertDocumentHistory(request, ExpDocumentHistory.DOCUMENT_TYPE_BUDGET_JOURNAL,
                        bgtJournalHeader.getJournalHeaderId(), ExpDocumentHistory.STATUS_REVERSE,
                        request.getEmployeeId(), bgtJournalHeader.getJournalHeaderNotes());
        return bgtJournalHeader;
    }

    /**
     * 检查预算日记账单据是否可修改
     *
     * @param bgtJournalHeader
     * @author guiyuting 2019-04-02 19:50
     * @return false不可更改，true可更改
     */
    private boolean bgtJournalCanModifyChk(IRequest request, BgtJournalHeader bgtJournalHeader) {
        boolean flag = false;
        BgtJournalHeader checkHeader = self().selectByPrimaryKey(request,
                        BgtJournalHeader.builder().journalHeaderId(bgtJournalHeader.getJournalHeaderId()).build());
        if (checkHeader.getStatus().equals(BgtJournalHeader.STATUS_NEW)
                        || checkHeader.getStatus().equals(BgtJournalHeader.STATUS_WITHDRAWAL)
                        || checkHeader.getStatus().equals(BgtJournalHeader.STATUS_REJECTED)) {
            flag = true;
        }
        return flag;

    }

    /**
     * 预算日记账调整-预算金额控制校验
     *
     * @param bgtJournalHeader
     * @author guiyuting 2019-04-03 10:32
     * @return
     */
    private Map<String, String> validateJournalBgtCheck(IRequest request, BgtJournalHeader bgtJournalHeader)
                    throws NoSuchFieldException, IllegalAccessException {
        Map<String, String> resultMap = new HashMap();
        String checkResult = "", checkCondition = "", checkMessage = "";
        String vJournalBgtCheckFlag = parameterService.queryParamValueByCode(
                        ParameterConstants.PARAM_BGT_JOURNAL_BGT_CHECK_FLAG, request.getUserId(), request.getRoleId(),
                        bgtJournalHeader.getCompanyId(), null, null, null, null);
        // 需要对预算日记账进行调整
        if (BaseConstants.YES.equals(vJournalBgtCheckFlag)) {
            int vNeedCheckCount = lineService.queryNeedCheckCount(bgtJournalHeader.getJournalHeaderId());
            if (vNeedCheckCount > 0) {
                BgtStructure vPeriodStrategy = structureService.selectByPrimaryKey(request,
                                BgtStructure.builder().structureId(bgtJournalHeader.getStructureId()).build());
                // 金额小于0的日记账行维度组合预算余额金额合计大于0
                List<BgtJournalLine> lineDims = lineService.queryDimByHeaderId(bgtJournalHeader.getJournalHeaderId());
                for (BgtJournalLine bgtJournalLine : lineDims) {
                    Map<String, BigDecimal> sumMap = lineService.querySumAmount(bgtJournalLine);
                    BigDecimal amountSum = sumMap.get("amountSum");
                    BigDecimal quantitySum = sumMap.get("quantitySum");
                    Map<String, BigDecimal> balanceSumMap = lineService.queryBalanceSumAmount(bgtJournalLine);
                    BigDecimal balanceAmountSum = balanceSumMap.get("balanceAmountSum");
                    BigDecimal balanceQuantitySum = balanceSumMap.get("balanceQuantitySum");
                    if (amountSum.add(balanceAmountSum).intValue() < 0) {
                        checkCondition = this.getValidateCondition(request, bgtJournalLine);
                        checkMessage = "金额之和为负数";
                        checkResult = "AMOUNT_NEGATIVE";
                        break;
                    }
                    if (quantitySum.add(balanceQuantitySum).intValue() < 0) {
                        checkCondition = this.getValidateCondition(request, bgtJournalLine);
                        checkMessage = "数量之和为负数";
                        checkResult = "QUANTITY_NEGATIVE";
                        break;
                    }
                }
                // 日记账行维度预算检查校验
                List<BgtJournalLine> bgtJournalLineList = lineService.select(request,
                                BgtJournalLine.builder().journalHeaderId(bgtJournalHeader.getJournalHeaderId()).build(),
                                0, 0);
                bgtJournalLineList.forEach(bgtJournalLine -> {
                    BgtBudgetReserve bgtBudgetReserve = new BgtBudgetReserve();
                    org.springframework.beans.BeanUtils.copyProperties(bgtJournalLine, bgtBudgetReserve);
                    bgtBudgetReserve.setReserveCompanyId(bgtJournalLine.getCompanyId());
                    bgtBudgetReserve.setReserveOperationUnitId(bgtJournalLine.getOperationUnitId());
                    bgtBudgetReserve.setBgtOrgId(bgtJournalHeader.getBgtOrgId());
                    bgtBudgetReserve.setPeriodName(bgtJournalLine.getLinePeriodName());
                    bgtBudgetReserve.setBusinessType(BgtBudgetReserve.BUSINESS_TYPE_BGT_JOURNAL_DATA);
                    bgtBudgetReserve.setReserveFlag("U");
                    bgtBudgetReserve.setStatus("P");
                    bgtBudgetReserve.setDocumentId(bgtJournalHeader.getJournalHeaderId());
                    bgtBudgetReserve.setDocumentLineId(bgtJournalLine.getJournalLineId());
                    bgtBudgetReserve.setAmount(bgtJournalLine.getAmount().negate());
                    bgtBudgetReserve.setFunctionalAmount(bgtJournalLine.getFunctionalAmount().negate());
                    bgtBudgetReserve.setQuantity(bgtJournalLine.getQuantity().negate());
                    bgtBudgetReserve.setReleaseId(null);
                    bgtBudgetReserve.setSourceType(null);
                    budgetReserveService.insertBgtReserve(request, bgtBudgetReserve);

                });

                for (BgtJournalLine bgtJournalLine : bgtJournalLineList) {
                    BgtBudgetReserve bgtBudgetReserve = new BgtBudgetReserve();
                    org.springframework.beans.BeanUtils.copyProperties(bgtJournalLine, bgtBudgetReserve);
                    bgtBudgetReserve.setReserveCompanyId(bgtJournalLine.getCompanyId());
                    bgtBudgetReserve.setReserveOperationUnitId(bgtJournalLine.getOperationUnitId());
                    bgtBudgetReserve.setBgtOrgId(bgtJournalHeader.getBgtOrgId());
                    bgtBudgetReserve.setPeriodName(bgtJournalLine.getLinePeriodName());
                    bgtBudgetReserve.setBusinessType(BgtBudgetReserve.BUSINESS_TYPE_BGT_JOURNAL_CHECK);
                    bgtBudgetReserve.setReserveFlag("U");
                    bgtBudgetReserve.setStatus("N");
                    bgtBudgetReserve.setDocumentId(bgtJournalHeader.getJournalHeaderId());
                    bgtBudgetReserve.setDocumentLineId(bgtJournalLine.getJournalLineId());
                    bgtBudgetReserve.setAmount(new BigDecimal(0));
                    bgtBudgetReserve.setFunctionalAmount(new BigDecimal(0));
                    bgtBudgetReserve.setQuantity(new BigDecimal(0));
                    bgtBudgetReserve.setReleaseId(null);
                    bgtBudgetReserve.setSourceType(null);
                    budgetReserveService.insertBgtReserve(request, bgtBudgetReserve);

                    BgtCheckResult bgtCheckResult = bgtCheckService.check(bgtBudgetReserve, "N", request);
                    if (bgtCheckResult.getResultType().equals(BgtCheckResult.RESULT_TYPE_PASSED)) {
                        checkCondition = this.getValidateCondition(request, bgtJournalLine);
                        checkMessage = bgtCheckResult.getMessage();
                        checkResult = BgtCheckResult.RESULT_TYPE_PASSED;
                        break;
                    } else if (bgtCheckResult.getResultType().equals(BgtCheckResult.RESULT_TYPE_BLOCKED)) {
                        checkCondition = this.getValidateCondition(request, bgtJournalLine);
                        checkMessage = bgtCheckResult.getMessage();
                        checkResult = BgtCheckResult.RESULT_TYPE_BLOCKED;
                        break;
                    }

                }

            }
        }
        resultMap.put("checkCondition", checkCondition);
        resultMap.put("checkMessage", checkMessage);
        resultMap.put("checkResult", checkResult);
        return resultMap;
    }

    /**
     * 根据传入参数返回组合条件描述
     *
     * @param bgtJournalLine
     * @author guiyuting 2019-04-03 16:02
     * @return
     */
    private String getValidateCondition(IRequest request, BgtJournalLine bgtJournalLine)
                    throws NoSuchFieldException, IllegalAccessException {
        String bgtEntityName = "", bgtCenterName = "", companyName = "", bgtItemName = "", dimensionDesc = "";
        if (bgtJournalLine.getBgtEntityId() != null) {
            bgtEntityName = "预算实体为：" + bgtEntityService
                            .selectByPrimaryKey(request,
                                            BgtEntity.builder().entityId(bgtJournalLine.getBgtEntityId()).build())
                            .getDescription();
        }
        if (bgtJournalLine.getBgtCenterId() != null) {
            bgtCenterName = "预算中心为：" + bgtCenterService
                            .selectByPrimaryKey(request,
                                            BgtCenter.builder().centerId(bgtJournalLine.getBgtCenterId()).build())
                            .getDescription();
        }
        if (bgtJournalLine.getCompanyId() != null) {
            FndCompany fndCompany = companyService.selectByPrimaryKey(request,
                            FndCompany.builder().companyId(bgtJournalLine.getCompanyId()).build());
            companyName = "公司为：" + fndCompany.getCompanyCode() + "-" + fndCompany.getCompanyShortName();
        }
        if (bgtJournalLine.getBudgetItemId() != null) {
            BgtBudgetItem bgtBudgetItem = bgtBudgetItemService.selectByPrimaryKey(request,
                            BgtBudgetItem.builder().budgetItemId(bgtJournalLine.getBudgetItemId()).build());
            bgtItemName = "预算项目为：" + bgtBudgetItem.getBudgetItemCode() + "-" + bgtBudgetItem.getDescription();
        }
        // String description = promptService.getPromptDescription(message.getLang(),
        // message.getPromptCode());
        for (int i = 1; i <= 20; i++) {
            String pro = "dimension" + i + "Id";
            Long dimensionValueId = (Long) bgtJournalLine.getClass().getField(pro).get(bgtJournalLine);
            if (dimensionValueId != null) {
                dimensionDesc = dimensionDesc
                                + this.getDimCondition(request, Long.parseLong(String.valueOf(i)), dimensionValueId);
            }
        }

        return bgtEntityName + bgtCenterName + companyName + bgtItemName + dimensionDesc;
    }

    private String getDimCondition(IRequest request, Long sequence, Long dimensionValueId) {
        String condition = "";
        FndDimension fndDimension = new FndDimension();
        fndDimension.setDimensionSequence(sequence);
        List<FndDimension> list = fndDimensionService.select(request, fndDimension, 0, 0);
        if (!list.isEmpty()) {
            fndDimension = list.get(0);
        }
        FndDimensionValue fndDimensionValue = new FndDimensionValue();
        fndDimensionValue.setDimensionValueId(dimensionValueId);
        fndDimensionValue = fndDimensionValueService.selectByPrimaryKey(request, fndDimensionValue);
        condition = fndDimension.getDescription() + "为：" + fndDimensionValue.getDimensionValueCode() + "-"
                        + fndDimensionValue.getDescription();
        return condition;
    }

    /**
     * 预算业务类型为'调整'时，预算日记账行总金额必须为'0'
     *
     * @param bgtJournalHeader
     * @author guiyuting 2019-04-09 15:23
     * @return 
     */
    private void bgtAmountCheck(IRequest request, BgtJournalHeader bgtJournalHeader) {
        BgtJournalType bgtJournalType =
                        bgtJournalTypeService.getBgtJournalType(request, bgtJournalHeader.getBgtJournalTypeId());
        BigDecimal functionAmount = lineService.queryTotalFunctionAmount(bgtJournalHeader.getJournalHeaderId());
        // 调整型的预算日记账总金额需为‘0’
        if (bgtJournalType.getBgtBusinessType().equals(BgtJournalType.BUSINESS_TYPE_ADJUST)
                        && !functionAmount.equals(new BigDecimal(0))) {
            throw new BgtJournalHeaderException(BgtJournalHeaderException.ADJ_AMOUNT_ERROR, null);
        }
    }

}
