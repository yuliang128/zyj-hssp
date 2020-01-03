package com.hand.hec.csh.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshMoPaymentReqType;
import com.hand.hec.csh.dto.CshPaymentReqAccount;
import com.hand.hec.csh.dto.CshPaymentRequisitionHd;
import com.hand.hec.csh.dto.CshPaymentRequisitionLn;
import com.hand.hec.csh.exception.CshPaymentRequisitionLnException;
import com.hand.hec.csh.mapper.CshPaymentRequisitionHdMapper;
import com.hand.hec.csh.mapper.CshPaymentRequisitionLnMapper;
import com.hand.hec.csh.service.*;
import com.hand.hec.exp.dto.ExpDocumentHistory;
import com.hand.hec.exp.dto.ExpRequisitionHeader;
import com.hand.hec.exp.service.IExpDocumentHistoryService;
import com.hand.hec.exp.service.IExpRequisitionHeaderService;
import com.hand.hec.fnd.dto.FndDocInfo;
import com.hand.hec.fnd.mapper.GldPeriodMapper;
import com.hand.hec.fnd.service.IFndCodingRuleObjectService;
import com.hand.hec.gld.mapper.GldAccountingEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;


@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentRequisitionHdServiceImpl extends BaseServiceImpl<CshPaymentRequisitionHd>
		implements ICshPaymentRequisitionHdService {
	@Autowired
	ICshPaymentRequisitionHdService iPaymentRequisitionHdService;
	@Autowired
	ICshPaymentRequisitionLnService iPaymentRequisitionLnService;
	@Autowired
	IExpDocumentHistoryService iExpDocumentHistoryService;
	@Autowired
	ICshMoPaymentReqTypeService iCshMoPaymentReqTypeService;
	@Autowired
	ICshDocPayAccEntityService iCshDocPayAccEntityService;
	@Autowired
	ICshDocumentFlowService iCshDocumentFlowService;
	@Autowired
	IExpRequisitionHeaderService iExpRequisitionHeaderService;
	@Autowired
	CshPaymentRequisitionHdMapper paymentRequisitionHdMapper;
	@Autowired
	CshPaymentRequisitionLnMapper paymentRequisitionLnMapper;
	@Autowired
	GldPeriodMapper gldPeriodMapper;
	@Autowired
	GldAccountingEntityMapper gldAccountingEntityMapper;
	@Autowired
	ICshPaymentReqAccountService cshPaymentReqAccountService;
	@Autowired
	IFndCodingRuleObjectService codingRuleObjectService;
	@Autowired
	ISysParameterService iSysParameterService;

	@Override
	public List<CshPaymentRequisitionHd> savePaymentRequisition(IRequest request, List<CshPaymentRequisitionHd> dto) {
		CshPaymentRequisitionHd paymentRequisitionHd = dto.get(0);
		paymentRequisitionHd.setTransactionCategory("BUSINESS");
		paymentRequisitionHd.setStatus("GENERATE");
		paymentRequisitionHd.setCompanyId(request.getCompanyId());
		paymentRequisitionHd.setAuditFlag("N");
		paymentRequisitionHd.setReversedFlag("N");
		if (null == paymentRequisitionHd.getCurrencyCode() || paymentRequisitionHd.getCurrencyCode().isEmpty()){
			paymentRequisitionHd.setCurrencyCode("CNY");
		}
		if (null == paymentRequisitionHd.getPaymentRequisitionHeaderId() || paymentRequisitionHd.getPaymentRequisitionHeaderId().toString().isEmpty()) {
			paymentRequisitionHd.setRequisitionNumber(codingRuleObjectService.getRuleCode(FndDocInfo.DOC_CATEGORY_CSH_PAY_REQ,
					paymentRequisitionHd.getPaymentReqTypeId().toString(), null, null,
					paymentRequisitionHd.getAccEntityId()));

			Long requisitionDateL = paymentRequisitionHdMapper.selectForTimestamp(paymentRequisitionHd.getRequisitionDate());
			Timestamp reqTimeStamp = new Timestamp(requisitionDateL);
			paymentRequisitionHd.setRequisitionDateTz(reqTimeStamp);
			paymentRequisitionHd.setRequisitionDateLtz(reqTimeStamp);
			paymentRequisitionHd = iPaymentRequisitionHdService.insert(request, paymentRequisitionHd);

			//将时间戳重置为空  否则第一次提交会出现string无法转化成时间戳的错误
			paymentRequisitionHd.setRequisitionDateTz(null);
			paymentRequisitionHd.setRequisitionDateLtz(null);
			// 保存单据历史
			iExpDocumentHistoryService.insertDocumentHistory(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION,
					paymentRequisitionHd.getPaymentRequisitionHeaderId(), ExpDocumentHistory.STATUS_GENERATE, paymentRequisitionHd.getEmployeeId(),
					paymentRequisitionHd.getDescription());
		} else {
			iPaymentRequisitionHdService.updateByOptions(request, paymentRequisitionHd);
		}

		// 行保存
		for (CshPaymentRequisitionLn ln : paymentRequisitionHd.getPaymentRequisitionLns()) {
			ln.setPaymentRequisitionHeaderId(paymentRequisitionHd.getPaymentRequisitionHeaderId());
		}
		try {
			iPaymentRequisitionLnService.batchSave(request, paymentRequisitionHd.getPaymentRequisitionLns());
		} catch (CshPaymentRequisitionLnException e) {
			e.getMessage();
		}

		return dto;
	}

	@Override
	public void updateByOptions(IRequest request, CshPaymentRequisitionHd dto) {
		paymentRequisitionHdMapper.updateByOptions(dto);
	}

	@Override
	public void submitCheck(IRequest request, CshPaymentRequisitionHd dto) {
		BigDecimal headAmount = dto.getAmount().toString() == null ? new BigDecimal(0) : dto.getAmount();
		BigDecimal lineSumAmount = paymentRequisitionHdMapper
				.selectForLineSumAmount(dto.getPaymentRequisitionHeaderId());
		if (headAmount.compareTo(lineSumAmount) != 0) {
			throw new RuntimeException("借款申请行金额合计不等于借款总金额，不能提交!");
		}
		// 申请行金额校验
		List<Long> requisitionIdList = paymentRequisitionHdMapper
				.selectForDisReqId(dto.getPaymentRequisitionHeaderId());
		for (Long expRequisitionHeaderId : requisitionIdList) {
			// 借款单金额
			BigDecimal payReqSumAmount = paymentRequisitionHdMapper
					.selectPayReqSumAmount(dto.getPaymentRequisitionHeaderId(), expRequisitionHeaderId);
			// 申请单可使用余额
			BigDecimal reqRemainAmount = paymentRequisitionHdMapper.selectRemainAmount(expRequisitionHeaderId);
			if (payReqSumAmount.compareTo(reqRemainAmount) > 0) {
				throw new RuntimeException("借款申请单申请金额不能大于费用申请单的未申请借款金额！");
			}
		}
	}

	@Override
	public void submitPaymentRequisition(IRequest request, Long paymentRequisitionHeaderId) {
		// 判断单据状态
		CshPaymentRequisitionHd cshPaymentRequisitionHd = new CshPaymentRequisitionHd();
		cshPaymentRequisitionHd.setPaymentRequisitionHeaderId(paymentRequisitionHeaderId);
		cshPaymentRequisitionHd = self().selectByPrimaryKey(request, cshPaymentRequisitionHd);
		if (!CshPaymentRequisitionHd.STATUS_GENERATE.equals(cshPaymentRequisitionHd.getStatus())
				&& !CshPaymentRequisitionHd.STATUS_REJECTED.equals(cshPaymentRequisitionHd.getStatus())
				&& !CshPaymentRequisitionHd.STATUS_TAKEN_BACK.equals(cshPaymentRequisitionHd.getStatus())) {
			throw new RuntimeException("借款申请单的状态已经更新, 不能提交!");
		}

		CshMoPaymentReqType cshMoPaymentReqType = new CshMoPaymentReqType();
		cshMoPaymentReqType.setMoPaymentReqTypeId(cshPaymentRequisitionHd.getPaymentReqTypeId());
		cshMoPaymentReqType = iCshMoPaymentReqTypeService.selectByPrimaryKey(request, cshMoPaymentReqType);
		// 获取公司级参数 PAYMENT_REQUISITION_APPROVED_WITH_REQ 暂无
		String companyApproveFlag = iSysParameterService.queryParamValueByCode("PAYMENT_REQUISITION_APPROVED_WITH_REQ",null,null,request.getCompanyId(),null,null,null,null);
		String enabledFlag = "Y";
		String expRequisition = "EXP_REQUISITION";
		if (enabledFlag.equals(companyApproveFlag) && expRequisition.equals(cshPaymentRequisitionHd.getSourceType())) {
			return;
		}
		// 提交校验
		submitCheck(request, cshPaymentRequisitionHd);
		// 事件 跳过

		// 保存单据历史
		iExpDocumentHistoryService.insertDocumentHistory(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION,
				cshPaymentRequisitionHd.getPaymentRequisitionHeaderId(), ExpDocumentHistory.STATUS_SUBMIT,
				cshPaymentRequisitionHd.getEmployeeId(),
				"借款申请单[" + cshPaymentRequisitionHd.getRequisitionNumber() + "]提交");

		// 自审批
		if (enabledFlag.equals(cshMoPaymentReqType.getAutoApproveFlag())) {
			String auditFlag;
			String ebsInterfaceType = "EBS-AP";
			// 参数定义
			String interfaceType = "EBS-AP";
			if (!ebsInterfaceType.equals(interfaceType)) {
				auditFlag = "Y";
			} else if (enabledFlag.equals(cshMoPaymentReqType.getAutoAuditFlag())) {
				auditFlag = "Y";
			} else {
				auditFlag = "N";
			}
			// 更新头状态为审批通过 审核完成
			cshPaymentRequisitionHd.setStatus(CshPaymentRequisitionHd.STATUS_COMPLETELY_APPROVED);
			cshPaymentRequisitionHd.setAuditFlag(auditFlag);
			cshPaymentRequisitionHd.setAuditDate(new Date());
			Criteria criteria = new Criteria(cshPaymentRequisitionHd);
			criteria.update(CshPaymentRequisitionHd.FIELD_STATUS, CshPaymentRequisitionHd.FIELD_AUDIT_FLAG,
					CshPaymentRequisitionHd.FIELD_AUDIT_DATE);
			self().updateByPrimaryKeyOptions(request, cshPaymentRequisitionHd, criteria);
			// 更新行支付状态为未支付
			CshPaymentRequisitionLn cshPaymentRequisitionLn = new CshPaymentRequisitionLn();
			cshPaymentRequisitionLn.setPaymentRequisitionHeaderId(paymentRequisitionHeaderId);
			criteria = new Criteria(cshPaymentRequisitionLn);
			criteria.where(
					new WhereField(CshPaymentRequisitionLn.FIELD_PAYMENT_REQUISITION_HEADER_ID, Comparison.EQUAL));
			List<CshPaymentRequisitionLn> lnList = iPaymentRequisitionLnService
					.selectOptions(request, cshPaymentRequisitionLn, criteria);
			for (CshPaymentRequisitionLn ln : lnList) {
				criteria = new Criteria(ln);
				criteria.update(CshPaymentRequisitionLn.FIELD_PAYMENT_STATUS);
				iPaymentRequisitionLnService.updateByPrimaryKeyOptions(request, ln, criteria);
			}
			// 审批事件 暂无

			// 自审批历史
			// 保存单据历史
			iExpDocumentHistoryService.insertDocumentHistory(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION,
					cshPaymentRequisitionHd.getPaymentRequisitionHeaderId(), ExpDocumentHistory.STATUS_APPROVE,
					cshPaymentRequisitionHd.getEmployeeId(),
					"借款申请单[" + cshPaymentRequisitionHd.getRequisitionNumber() + "]自审批");
		} else {
			// 更新头状态为提交
			cshPaymentRequisitionHd.setStatus(CshPaymentRequisitionHd.STATUS_SUBMITTED);
			Criteria criteria = new Criteria(cshPaymentRequisitionHd);
			criteria.update(CshPaymentRequisitionHd.FIELD_STATUS);
			self().updateByPrimaryKeyOptions(request, cshPaymentRequisitionHd, criteria);
			// 更新行支付状态为未支付
			CshPaymentRequisitionLn cshPaymentRequisitionLn = new CshPaymentRequisitionLn();
			cshPaymentRequisitionLn.setPaymentRequisitionHeaderId(paymentRequisitionHeaderId);
			criteria = new Criteria(cshPaymentRequisitionLn);
			criteria.where(
					new WhereField(CshPaymentRequisitionLn.FIELD_PAYMENT_REQUISITION_HEADER_ID, Comparison.EQUAL));
			List<CshPaymentRequisitionLn> lnList = iPaymentRequisitionLnService
					.selectOptions(request, cshPaymentRequisitionLn, criteria);
			for (CshPaymentRequisitionLn ln : lnList) {
				criteria = new Criteria(ln);
				criteria.update(CshPaymentRequisitionLn.FIELD_PAYMENT_STATUS);
				iPaymentRequisitionLnService.updateByPrimaryKeyOptions(request, ln, criteria);
			}
			// 根据系统参数CSH_PREQ_APPROVE_WORKFLOW_ENABLED 是否启用工作流
			String workflowFlag = iSysParameterService.queryParamValueByCode("CSH_PREQ_APPROVE_WORKFLOW_ENABLED",null,null,null,null,null,null,null);
			if (enabledFlag.equals(workflowFlag)) {
				// 启用工作流 暂无
			}
		}

	}

	@Override
	public void deletePaymentRequisition(IRequest request, Long paymentRequisitionHeaderId) {
		// 状态判断 新建 撤回 审批拒绝可以删除
		CshPaymentRequisitionHd cshPaymentRequisitionHd = new CshPaymentRequisitionHd();
		cshPaymentRequisitionHd.setPaymentRequisitionHeaderId(paymentRequisitionHeaderId);
		cshPaymentRequisitionHd = self().selectByPrimaryKey(request, cshPaymentRequisitionHd);
		if (!CshPaymentRequisitionHd.STATUS_GENERATE.equals(cshPaymentRequisitionHd.getStatus())
				&& !CshPaymentRequisitionHd.STATUS_TAKEN_BACK.equals(cshPaymentRequisitionHd.getStatus())
				&& !CshPaymentRequisitionHd.STATUS_REJECTED.equals(cshPaymentRequisitionHd.getStatus())) {
			throw new RuntimeException("只有新建 收回和拒绝的单据才能删除!");
		}
		// 删除工作流 暂无

		// 删除借款单行
		iPaymentRequisitionLnService.deletePaymentReqLn(request, paymentRequisitionHeaderId);
		// 删除借款单头
		self().deleteByPrimaryKey(cshPaymentRequisitionHd);
		// 删除付款主体信息
		iCshDocPayAccEntityService
				.deletePayAccEntity(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION, paymentRequisitionHeaderId,
						null);
		// 删除合同关联表
		iCshDocumentFlowService
				.deleteDocumentFlow(request, paymentRequisitionHeaderId, "CON_CONTRACT", "CSH_PAYMENT_REQUISITION_LNS");
		// 删除附件 暂无

		// 删除单据历史
		iExpDocumentHistoryService
				.delteDocumentHistory(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION, paymentRequisitionHeaderId);
	}

	@Override
	public void updatePaymentRequisitionStatus(IRequest request, Long paymentRequisitionHeaderId, String status,
			Date approvalDate, Long approvedBy, Date closedDate) {
		CshPaymentRequisitionHd cshPaymentRequisitionHd = new CshPaymentRequisitionHd();
		cshPaymentRequisitionHd.setPaymentRequisitionHeaderId(paymentRequisitionHeaderId);
		cshPaymentRequisitionHd.setStatus(status);
		if (approvalDate != null) {
			cshPaymentRequisitionHd.setApprovalDate(approvalDate);
		}
		if (approvedBy.toString() != null || !approvedBy.toString().isEmpty()) {
			cshPaymentRequisitionHd.setApprovedBy(approvedBy);
		}
		if (closedDate != null) {
			cshPaymentRequisitionHd.setClosedDate(closedDate);
		}
		Criteria criteria = new Criteria(cshPaymentRequisitionHd);
		criteria.update(CshPaymentRequisitionHd.FIELD_STATUS, CshPaymentRequisitionHd.FIELD_APPROVAL_DATE,
				CshPaymentRequisitionHd.FIELD_APPROVED_BY, CshPaymentRequisitionHd.FIELD_CLOSED_DATE);
		self().updateByPrimaryKeyOptions(request, cshPaymentRequisitionHd, criteria);
	}

	@Override
	public void approvePaymentRequisition(IRequest request, List<CshPaymentRequisitionHd> hdList) {
		/*
		 * CshPaymentRequisitionHd cshPaymentRequisitionHd = new CshPaymentRequisitionHd();
		 * cshPaymentRequisitionHd.setPaymentRequisitionHeaderId(paymentRequisitionHeaderId);
		 * cshPaymentRequisitionHd.setStatus(CshPaymentRequisitionHd.STATUS_SUBMITTED); Criteria criteria =
		 * new Criteria(cshPaymentRequisitionHd); criteria.where(new
		 * WhereField(CshPaymentRequisitionHd.FIELD_PAYMENT_REQUISITION_HEADER_ID),Comparison.EQUAL ,new
		 * WhereField(CshPaymentRequisitionHd.FIELD_STATUS),Comparison.EQUAL); List<CshPaymentRequisitionHd>
		 * hdList = self().selectOptions(request,cshPaymentRequisitionHd,criteria);
		 */
		if (hdList.isEmpty()) {
			throw new RuntimeException("未找到该条借款申请单!");
		}
		Long userId = request.getUserId();
		for (CshPaymentRequisitionHd cshPaymentRequisitionHd : hdList) {
			self().updatePaymentRequisitionStatus(request, cshPaymentRequisitionHd.getPaymentRequisitionHeaderId(),
					CshPaymentRequisitionHd.STATUS_COMPLETELY_APPROVED, new Date(), userId, null);

			// 进入派工池事件 暂无
		}
	}

	@Override
	public void reject(IRequest request, List<CshPaymentRequisitionHd> hdList) {
		Long userId = request.getUserId();
		for (CshPaymentRequisitionHd cshPaymentRequisitionHd : hdList) {
			self().updatePaymentRequisitionStatus(request, cshPaymentRequisitionHd.getPaymentRequisitionHeaderId(),
					CshPaymentRequisitionHd.STATUS_REJECTED, new Date(), userId, null);
			// 单据历史
			iExpDocumentHistoryService.insertDocumentHistory(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION,
					cshPaymentRequisitionHd.getPaymentRequisitionHeaderId(), ExpDocumentHistory.STATUS_APPROVAL_REJECT,
					cshPaymentRequisitionHd.getEmployeeId(),
					"借款申请单[" + cshPaymentRequisitionHd.getRequisitionNumber() + "]审批拒绝");
		}
	}

	@Override
	public void submitPayReqWithReq(IRequest request, Long expRequisitionHeaderId, String status) {
		ExpRequisitionHeader expRequisitionHeader = new ExpRequisitionHeader();
		expRequisitionHeader.setExpRequisitionHeaderId(expRequisitionHeaderId);
		expRequisitionHeader = iExpRequisitionHeaderService.selectByPrimaryKey(request, expRequisitionHeader);

		// 判断参数 [若配置 借款申请根据费用申请审批 则提交] 暂无
		String submitFlag = "N";
		String enabledFlag = "Y";

		if (enabledFlag.equals(submitFlag)) {
			List<CshPaymentRequisitionHd> hdList = paymentRequisitionHdMapper
					.selectForPayReqHd(expRequisitionHeaderId, "EXP_REQUISITION");
			for (CshPaymentRequisitionHd hd : hdList) {
				Long userId = request.getUserId();
				Date date = new Date();
				submitCheck(request, hd);
				if (!CshPaymentRequisitionHd.STATUS_COMPLETELY_APPROVED.equals(status)) {
					userId = null;
					date = null;
				}
				hd.setApprovedBy(userId);
				hd.setApprovalDate(date);
				hd.setStatus(status);
				Criteria criteria = new Criteria(hd);
				criteria.update(CshPaymentRequisitionHd.FIELD_STATUS, CshPaymentRequisitionHd.FIELD_APPROVED_BY,
						CshPaymentRequisitionHd.FIELD_APPROVAL_DATE);
				self().updateByPrimaryKeyOptions(request, hd, criteria);

				// 更新行付款状态
				CshPaymentRequisitionLn cshPaymentRequisitionLn = new CshPaymentRequisitionLn();
				cshPaymentRequisitionLn.setPaymentRequisitionHeaderId(hd.getPaymentRequisitionHeaderId());
				criteria = new Criteria(cshPaymentRequisitionLn);
				criteria.where(new WhereField(CshPaymentRequisitionLn.FIELD_PAYMENT_REQUISITION_HEADER_ID),
						Comparison.EQUAL);
				List<CshPaymentRequisitionLn> lnList = iPaymentRequisitionLnService
						.selectOptions(request, cshPaymentRequisitionLn, criteria);
				for (CshPaymentRequisitionLn ln : lnList) {
					criteria = new Criteria(ln);
					criteria.update(CshPaymentRequisitionLn.FIELD_PAYMENT_STATUS);
					iPaymentRequisitionLnService.updateByPrimaryKeyOptions(request, ln, criteria);
				}

				// 单据历史
				iExpDocumentHistoryService.insertDocumentHistory(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION,
						hd.getPaymentRequisitionHeaderId(), ExpDocumentHistory.STATUS_SUBMIT, hd.getEmployeeId(),
						"借款申请单[" + hd.getRequisitionNumber() + "]提交");
			}
		}
	}

	@Override
	public void setPayReqStatusWithReq(IRequest request, Long expRequisitionHeaderId, String status) {
		ExpRequisitionHeader expRequisitionHeader = new ExpRequisitionHeader();
		expRequisitionHeader.setExpRequisitionHeaderId(expRequisitionHeaderId);
		expRequisitionHeader = iExpRequisitionHeaderService.selectByPrimaryKey(request, expRequisitionHeader);

		// 判断参数 [若配置 借款申请根据费用申请审批 则提交] 暂无
		String submitFlag = "N";
		String enabledFlag = "Y";
		String interfaceType = "EBS-AP";
		String ebsType = "EBS-AP";
		String auditFlag;
		Date auditDate;
		if (enabledFlag.equals(submitFlag)) {
			List<CshPaymentRequisitionHd> hdList = paymentRequisitionHdMapper
					.selectForPayReqHd2(expRequisitionHeaderId, "EXP_REQUISITION");
			for (CshPaymentRequisitionHd hd : hdList) {
				if (CshPaymentRequisitionHd.STATUS_COMPLETELY_APPROVED.equals(status)) {
					// 参数 暂无
					if (ebsType.equals(interfaceType)) {
						auditFlag = "Y";
						auditDate = new Date();
					} else {
						auditFlag = "N";
						auditDate = null;
					}

					// 更新单据头
					hd.setAuditFlag(auditFlag);
					hd.setApprovalDate(auditDate);
					hd.setStatus(CshPaymentRequisitionHd.STATUS_COMPLETELY_APPROVED);
					hd.setApprovedBy(request.getUserId());
					hd.setApprovalDate(new Date());
					Criteria criteria = new Criteria(hd);
					criteria.update(CshPaymentRequisitionHd.FIELD_AUDIT_FLAG, CshPaymentRequisitionHd.FIELD_AUDIT_DATE,
							CshPaymentRequisitionHd.FIELD_STATUS, CshPaymentRequisitionHd.FIELD_APPROVED_BY,
							CshPaymentRequisitionHd.FIELD_APPROVAL_DATE);
					self().updateByPrimaryKeyOptions(request, hd, criteria);
					// 保存单据历史
					if (enabledFlag.equals(auditFlag)) {
						iExpDocumentHistoryService
								.insertDocumentHistory(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION,
										hd.getPaymentRequisitionHeaderId(), ExpDocumentHistory.STATUS_AUDIT,
										hd.getEmployeeId(), "借款申请单 [" + hd.getRequisitionNumber() + "] 自动审核");
					}
					// 借款单审批通过事件 暂无
				} else if (CshPaymentRequisitionHd.STATUS_TAKEN_BACK.equals(status)) {
					hd.setStatus(CshPaymentRequisitionHd.STATUS_TAKEN_BACK);
					Criteria criteria = new Criteria(hd);
					criteria.update(CshPaymentRequisitionHd.FIELD_STATUS);
					self().updateByPrimaryKeyOptions(request, hd, criteria);
				} else if (CshPaymentRequisitionHd.STATUS_REJECTED.equals(status)) {
					hd.setStatus(CshPaymentRequisitionHd.STATUS_REJECTED);
					Criteria criteria = new Criteria(hd);
					criteria.update(CshPaymentRequisitionHd.FIELD_STATUS);
					self().updateByPrimaryKeyOptions(request, hd, criteria);
				}
			}
		}
	}

	@Override
	public void updatePayReqLnCash(IRequest request, Long paymentRequisitionHeaderId, Long paymentReqLineId,
			Long cashFlowItemId) {
		CshPaymentRequisitionLn cshPaymentRequisitionLn = new CshPaymentRequisitionLn();
		cshPaymentRequisitionLn.setPaymentRequisitionHeaderId(paymentRequisitionHeaderId);
		cshPaymentRequisitionLn.setPaymentRequisitionLineId(paymentReqLineId);
		cshPaymentRequisitionLn.setCashFlowItemId(cashFlowItemId);
		Criteria criteria = new Criteria(cshPaymentRequisitionLn);
		criteria.update(CshPaymentRequisitionLn.FIELD_CASH_FLOW_ITEM_ID);
		iPaymentRequisitionLnService.updateByPrimaryKeyOptions(request, cshPaymentRequisitionLn, criteria);
	}

	@Override
	public void closePaymentRequisition(IRequest request, List<CshPaymentRequisitionHd> dto) {

		for (CshPaymentRequisitionHd cshPaymentRequisitionHd : dto) {
			if (!CshPaymentRequisitionHd.STATUS_COMPLETELY_APPROVED.equals(cshPaymentRequisitionHd.getStatus())) {
				throw new RuntimeException("非审批完成状态的借款申请单不可关闭, 请确认!");
			}
			Date closedDate = cshPaymentRequisitionHd.getClosedDate();
			cshPaymentRequisitionHd.setStatus(CshPaymentRequisitionHd.STATUS_CLOSED);
			cshPaymentRequisitionHd.setClosedDate(closedDate == null ? new Date() : closedDate);
			Long closedDateTz = paymentRequisitionHdMapper
					.selectForTimestamp(closedDate == null ? new Date() : closedDate);
			Long closedDateLtz = paymentRequisitionHdMapper
					.selectForTimestamp(closedDate == null ? new Date() : closedDate);
			cshPaymentRequisitionHd.setClosedDateTz(new Timestamp(closedDateTz));
			cshPaymentRequisitionHd.setClosedDateLtz(new Timestamp(closedDateLtz));
			Criteria criteria = new Criteria(cshPaymentRequisitionHd);
			criteria.update(CshPaymentRequisitionHd.FIELD_STATUS, CshPaymentRequisitionHd.FIELD_CLOSED_DATE,
					CshPaymentRequisitionHd.FIELD_CLOSED_DATE_TZ, CshPaymentRequisitionHd.FIELD_CLOSED_DATE_LTZ);
			self().updateByPrimaryKeyOptions(request, cshPaymentRequisitionHd, criteria);
			// 单据关闭历史
			iExpDocumentHistoryService.insertDocumentHistory(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION,
					cshPaymentRequisitionHd.getPaymentRequisitionHeaderId(), ExpDocumentHistory.STATUS_CLOSE,
					cshPaymentRequisitionHd.getEmployeeId(),
					"借款申请单 [" + cshPaymentRequisitionHd.getRequisitionNumber() + "] 关闭。");
		}
	}

	@Override
	public List<CshPaymentRequisitionHd> queryPayReq(IRequest request, CshPaymentRequisitionHd dto, int pageNum,
			int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return paymentRequisitionHdMapper.queryPayReq(dto);
	}

	@Override
	public void refundClosePaymentRequisition(IRequest request, Long paymentRequisitionHeaderId,
			BigDecimal unReturnedAmount, BigDecimal returnAmount, String refundType, Date closedDate) {
		String rePayment = "REPAYMENT";
		returnAmount = returnAmount == null ? new BigDecimal(0) : returnAmount;

		if (returnAmount.compareTo(BigDecimal.ZERO) != 0 && !rePayment.equals(refundType)) {
			CshPaymentRequisitionHd cshPaymentRequisitionHd = new CshPaymentRequisitionHd();
			cshPaymentRequisitionHd.setPaymentRequisitionHeaderId(paymentRequisitionHeaderId);
			cshPaymentRequisitionHd = self().selectByPrimaryKey(request, cshPaymentRequisitionHd);

			if (CshPaymentRequisitionHd.STATUS_COMPLETELY_APPROVED.equals(cshPaymentRequisitionHd.getStatus())) {
				cshPaymentRequisitionHd.setStatus(CshPaymentRequisitionHd.STATUS_CLOSED);
				cshPaymentRequisitionHd.setClosedDate(closedDate == null ? new Date() : closedDate);
				Long closedDateTz = paymentRequisitionHdMapper
						.selectForTimestamp(closedDate == null ? new Date() : closedDate);
				Long closedDateLtz = paymentRequisitionHdMapper
						.selectForTimestamp(closedDate == null ? new Date() : closedDate);
				cshPaymentRequisitionHd.setClosedDateTz(new Timestamp(closedDateTz));
				cshPaymentRequisitionHd.setClosedDateLtz(new Timestamp(closedDateLtz));
				Criteria criteria = new Criteria(cshPaymentRequisitionHd);
				criteria.update(CshPaymentRequisitionHd.FIELD_STATUS, CshPaymentRequisitionHd.FIELD_CLOSED_DATE,
						CshPaymentRequisitionHd.FIELD_CLOSED_DATE_TZ, CshPaymentRequisitionHd.FIELD_CLOSED_DATE_LTZ);
				self().updateByPrimaryKeyOptions(request, cshPaymentRequisitionHd, criteria);
				// 单据关闭历史
				iExpDocumentHistoryService.insertDocumentHistory(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION,
						cshPaymentRequisitionHd.getPaymentRequisitionHeaderId(), ExpDocumentHistory.STATUS_CLOSE,
						cshPaymentRequisitionHd.getEmployeeId(),
						"借款申请单 [" + cshPaymentRequisitionHd.getRequisitionNumber() + "] 关闭。");
			}
		}
	}

	@Override
	public void openPaymentRequisition(IRequest request, Long paymentRequisitionHeaderId) {
		CshPaymentRequisitionHd cshPaymentRequisitionHd = new CshPaymentRequisitionHd();
		cshPaymentRequisitionHd.setPaymentRequisitionHeaderId(paymentRequisitionHeaderId);
		cshPaymentRequisitionHd = self().selectByPrimaryKey(request, cshPaymentRequisitionHd);

		if (cshPaymentRequisitionHd.getClosedDate() != null) {
			cshPaymentRequisitionHd.setStatus(CshPaymentRequisitionHd.STATUS_COMPLETELY_APPROVED);
			cshPaymentRequisitionHd.setClosedDate(null);
			cshPaymentRequisitionHd.setClosedDateTz(null);
			cshPaymentRequisitionHd.setClosedDateLtz(null);
			Criteria criteria = new Criteria(cshPaymentRequisitionHd);
			criteria.update(CshPaymentRequisitionHd.FIELD_STATUS, CshPaymentRequisitionHd.FIELD_CLOSED_DATE,
					CshPaymentRequisitionHd.FIELD_CLOSED_DATE_TZ, CshPaymentRequisitionHd.FIELD_CLOSED_DATE_LTZ);
			self().updateByPrimaryKeyOptions(request, cshPaymentRequisitionHd, criteria);
		}
	}

	@Override
	public Long checkRequisitionAmount(IRequest request, String paymentReqLineType, Long expRequisitionHeaderId) {
		Long result = 0L;
		String expRequisition = "EXP_REQUISITION";
		if (expRequisition.equals(paymentReqLineType)) {
			// 申请单金额
			BigDecimal expReqSumAmount = paymentRequisitionHdMapper.selectForReqSumAmount(expRequisitionHeaderId, "Y");
			BigDecimal cshReqSumAmount = paymentRequisitionHdMapper
					.selectForPayReqSumAmount(expRequisitionHeaderId, paymentReqLineType);

			if (cshReqSumAmount.compareTo(expReqSumAmount) > 1) {
				result = 1L;
			}
		}
		return result;
	}

	@Override
	public void updateSscReturnStatus(IRequest request, Long paymentRequisitionHeaderId, String opinion) {
		String sscDocReturn = "RETURN_BACK";
		CshPaymentRequisitionHd cshPaymentRequisitionHd = new CshPaymentRequisitionHd();
		cshPaymentRequisitionHd.setPaymentRequisitionHeaderId(paymentRequisitionHeaderId);
		cshPaymentRequisitionHd = self().selectByPrimaryKey(request, cshPaymentRequisitionHd);

		if (!sscDocReturn.equals(cshPaymentRequisitionHd.getDocStatus())) {
			throw new RuntimeException("单据状态不匹配，请检查单据状态!");
		}
		cshPaymentRequisitionHd.setDocStatus(sscDocReturn);
		Criteria criteria = new Criteria(cshPaymentRequisitionHd);
		criteria.update(CshPaymentRequisitionHd.FIELD_DOC_STATUS);
		self().updateByPrimaryKeyOptions(request, cshPaymentRequisitionHd, criteria);

		// 单据历史
		iExpDocumentHistoryService.insertDocumentHistory(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION,
				cshPaymentRequisitionHd.getPaymentRequisitionHeaderId(), ExpDocumentHistory.STATUS_SSC_RETURN_BACK,
				cshPaymentRequisitionHd.getEmployeeId(), opinion);
	}

	@Override
	public void updateSscCancelReturnStatus(IRequest request, Long paymentRequisitionHeaderId) {
		String sscDocReturn = "RETURN_BACK";
		String sscCancelReturnBack = "CANCEL_RETURN_BACK";
		CshPaymentRequisitionHd cshPaymentRequisitionHd = new CshPaymentRequisitionHd();
		cshPaymentRequisitionHd.setPaymentRequisitionHeaderId(paymentRequisitionHeaderId);
		cshPaymentRequisitionHd = self().selectByPrimaryKey(request, cshPaymentRequisitionHd);

		if (!sscDocReturn.equals(cshPaymentRequisitionHd.getDocStatus())) {
			throw new RuntimeException("单据状态不匹配，请检查单据状态!");
		}


		Criteria criteria = new Criteria(cshPaymentRequisitionHd);
		criteria.update(CshPaymentRequisitionHd.FIELD_DOC_STATUS);
		self().updateByPrimaryKeyOptions(request, cshPaymentRequisitionHd, criteria);

		// 单据历史
		iExpDocumentHistoryService.insertDocumentHistory(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION,
				cshPaymentRequisitionHd.getPaymentRequisitionHeaderId(),
				ExpDocumentHistory.STATUS_SSC_CANCEL_RETURN_BACK, cshPaymentRequisitionHd.getEmployeeId(), "");
	}

	@Override
	public void updatePayReqHdAmount(IRequest request, Long paymentRequisitionHeaderId) {
		BigDecimal lineSumAmount = paymentRequisitionHdMapper.selectForLineSumAmount(paymentRequisitionHeaderId);
		CshPaymentRequisitionHd cshPaymentRequisitionHd = new CshPaymentRequisitionHd();
		cshPaymentRequisitionHd.setAmount(lineSumAmount);
		cshPaymentRequisitionHd.setPaymentRequisitionHeaderId(paymentRequisitionHeaderId);
		Criteria criteria = new Criteria(cshPaymentRequisitionHd);
		criteria.update(CshPaymentRequisitionHd.FIELD_AMOUNT);
		self().updateByPrimaryKeyOptions(request, cshPaymentRequisitionHd, criteria);
	}

	@Override
	public void updatePayReqHdDesc(IRequest request, Long paymentRequisitionHeaderId, String description) {
		String sscDocReturn = "RETURN_BACK";
		CshPaymentRequisitionHd cshPaymentRequisitionHd = new CshPaymentRequisitionHd();
		cshPaymentRequisitionHd.setPaymentRequisitionHeaderId(paymentRequisitionHeaderId);
		cshPaymentRequisitionHd = self().selectByPrimaryKey(request, cshPaymentRequisitionHd);

		if (sscDocReturn.equals(cshPaymentRequisitionHd.getDocStatus())) {
			cshPaymentRequisitionHd.setDescription(description);
			Criteria criteria = new Criteria(cshPaymentRequisitionHd);
			criteria.update(CshPaymentRequisitionHd.FIELD_DESCRIPTION);
			self().updateByPrimaryKeyOptions(request, cshPaymentRequisitionHd, criteria);
		}

	}

	@Override
	public void updatePayReqLnAccountNum(IRequest request, Long paymentRequisitionHeaderId,
			Long paymentRequisitionLineId, String accountNumber, String description) {
		CshPaymentRequisitionLn cshPaymentRequisitionLn = new CshPaymentRequisitionLn();
		cshPaymentRequisitionLn.setPaymentRequisitionLineId(paymentRequisitionLineId);
		cshPaymentRequisitionLn.setAccountNumber(accountNumber);
		cshPaymentRequisitionLn.setDescription(description);

		Criteria criteria = new Criteria(cshPaymentRequisitionLn);
		criteria.update(CshPaymentRequisitionLn.FIELD_ACCOUNT_NUMBER, CshPaymentRequisitionLn.FIELD_DESCRIPTION);
		iPaymentRequisitionLnService.updateByPrimaryKeyOptions(request, cshPaymentRequisitionLn, criteria);
	}

	@Override
	public void cancelDocReturn(IRequest request, Long paymentRequisitionHeaderId) {
		CshPaymentRequisitionHd cshPaymentRequisitionHd = new CshPaymentRequisitionHd();
		cshPaymentRequisitionHd.setPaymentRequisitionHeaderId(paymentRequisitionHeaderId);
		cshPaymentRequisitionHd = self().selectByPrimaryKey(request, cshPaymentRequisitionHd);

		if (CshPaymentRequisitionHd.STATUS_RETURN_BACK.equals(cshPaymentRequisitionHd.getDocStatus())) {
			cshPaymentRequisitionHd.setDocStatus(CshPaymentRequisitionHd.STATUS_CANCEL_RETURN_BACK);
			Criteria criteria = new Criteria(cshPaymentRequisitionHd);
			criteria.update(CshPaymentRequisitionHd.FIELD_DOC_STATUS);
			self().updateByPrimaryKeyOptions(request, cshPaymentRequisitionHd, criteria);
			// ssc表未迁移 暂无
		} else if (CshPaymentRequisitionHd.STATUS_PAY_BACK.equals(cshPaymentRequisitionHd.getDocStatus())) {
			cshPaymentRequisitionHd.setDocStatus(CshPaymentRequisitionHd.STATUS_CANCEL_PAY_BACK);
			Criteria criteria = new Criteria(cshPaymentRequisitionHd);
			criteria.update(CshPaymentRequisitionHd.FIELD_DOC_STATUS);
			self().updateByPrimaryKeyOptions(request, cshPaymentRequisitionHd, criteria);
		} else {
			throw new RuntimeException("单据状态不匹配，请检查单据状态!");
		}

		// 单据历史
		iExpDocumentHistoryService.insertDocumentHistory(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION,
				cshPaymentRequisitionHd.getPaymentRequisitionHeaderId(), "CSH_DOC_BACK",
				cshPaymentRequisitionHd.getEmployeeId(), "");
	}

	@Override
	public List<Map> selectDetailByHdId(IRequest request, Long paymentRequisitionHeaderId) {
		return paymentRequisitionHdMapper.selectDetailByHdId(paymentRequisitionHeaderId);
	}

	@Override
	public List<Map> queryForFinance(IRequest request, String allCompanyFlag, String requisitionNumber, Long employeeId,
			String description, Date requisitionDateFrom, Date requisitionDateTo, BigDecimal amountFrom,
			BigDecimal amountTo, String payeeCategory, Long payeeId, String currencyCode, String status,
			Long paymentMethodId, Long cshPaymentRequisitionTypeId, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return paymentRequisitionHdMapper
				.queryForFinance(allCompanyFlag, requisitionNumber, employeeId, description, requisitionDateFrom,
						requisitionDateTo, amountFrom, amountTo, payeeCategory, payeeId, currencyCode, status,
						paymentMethodId, cshPaymentRequisitionTypeId);
	}

	@Override
	public List<Map> queryForAudit(IRequest request, Long accEntityId, Long employeeId, String requisitionNumber,
			Date requisitionDateFrom, Date requisitionDateTo, String currencyCode, String payeeCategory, Long payeeId,
			int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return paymentRequisitionHdMapper
				.queryForAudit(accEntityId, employeeId, requisitionNumber, requisitionDateFrom, requisitionDateTo,
						currencyCode, payeeCategory, payeeId);
	}

	@Override
	public List<Map> getPeriodName(IRequest request, Date date) {
		Long accEntityId = gldAccountingEntityMapper.getDefaultAccEntity(request.getCompanyId()).getAccEntityId();
		// gldAccountingEntityMapper.getDefaultAccEntityId(request.getCompanyId());
		List<Map> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("periodName", gldPeriodMapper.getPeriodName(date, accEntityId, null));
		list.add(map);
		return list;
	}

	@Override
	public List<CshPaymentRequisitionHd> reversePayReq(IRequest request, List<CshPaymentRequisitionHd> records) {
		for (CshPaymentRequisitionHd record : records) {
			if ("R".equals(record.getReversedFlag()) || "W".equals(record.getReversedFlag())) {
				throw new RuntimeException("借款申请单已经被反冲！");
			}
			if (!"Y".equals(record.getAuditFlag())) {
				throw new RuntimeException("借款申请单还未审核！");
			}
			String periodName = gldPeriodMapper.getPeriodName(record.getReverseDate(), record.getAccEntityId(), null);
			if (null == periodName) {
				throw new RuntimeException("反冲日期必须在打开的会计期间内！");
			}
			Long oldHeaderId = record.getPaymentRequisitionHeaderId();
			// Long magOrgId =
			// fndManagingOrganizationMapper.defaultManageOrganizationQuery(record.getCompanyId()).getMagOrgId();
			// 待完成 编码规则取单据编号 CSH5010_PAYMENT_REQ_CODING_RULE_ERROR
			String requisitionNumber =codingRuleObjectService.getRuleCode(FndDocInfo.DOC_CATEGORY_CSH_PAY_REQ,
					record.getPaymentReqTypeId().toString(), null, null,
					record.getAccEntityId());
			// 反冲头
			Long newHeaderId = reverseCshPaymentReqHd(request, record, requisitionNumber);
			// 反冲行
			reverseCshPaymentReqLine(request, oldHeaderId, newHeaderId, periodName, record.getReverseDate());
			// 插入反冲借款申请单的历史
			iExpDocumentHistoryService
					.insertDocumentHistory(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION, newHeaderId,
							"GENERATE", request.getEmployeeId(), "");

			iExpDocumentHistoryService
					.insertDocumentHistory(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION, newHeaderId, "AUDIT",
							request.getEmployeeId(), "");
			// 插入被反冲借款申请单的历史
			iExpDocumentHistoryService
					.insertDocumentHistory(request, CshPaymentRequisitionHd.PAYMENT_REQUISITION, oldHeaderId,
							"REVERSED", request.getEmployeeId(), "");

			// 待完成 工作流部分
		}
		return records;
	}


	/**
	 * 反冲借款申请单头
	 *
	 * @param record
	 * @author LJK 2019-03-11 15:43
	 * @return
	 */
	public Long reverseCshPaymentReqHd(IRequest request, CshPaymentRequisitionHd record, String requisitionNumber) {
		Long headerId = record.getPaymentRequisitionHeaderId();
		record.setCreationDate(null);
		record.setCreatedBy(null);
		record.setRequisitionNumber(requisitionNumber);
		record.setRequisitionDate(record.getReverseDate());
		record.setAmount(record.getAmount().multiply(BigDecimal.valueOf(-1)));
		record.setRequisitionPaymentDate(null);
		record.setReversedFlag("R");
		record.setSourcePmtReqHeaderId(headerId);
		record.setAuditDate(record.getReverseDate());
		record.setPaymentRequisitionHeaderId(null);
		// 插入反冲单据
		CshPaymentRequisitionHd newRecord = self().insertSelective(request, record);
		Long newHeaderId = newRecord.getPaymentRequisitionHeaderId();

		// 更新原单据
		CshPaymentRequisitionHd dto = new CshPaymentRequisitionHd();
		dto.setPaymentRequisitionHeaderId(headerId);
		dto.setReversedFlag("W");
		/*
		 * Criteria criteria = new Criteria(record);
		 * criteria.update(CshPaymentRequisitionHd.FIELD_REVERSED_FLAG);
		 * self().updateByPrimaryKeyOptions(request, dto, criteria);
		 */
		self().updateByPrimaryKeySelective(request, dto);

		return newHeaderId;
	}

	/**
	 * 反冲借款申请单行
	 *
	 * @param headerId
	 * @author LJK 2019-03-11 15:43
	 * @return
	 */
	public void reverseCshPaymentReqLine(IRequest request, Long headerId, Long newHeaderId, String periodName,
			Date reverseDate) {
		CshPaymentRequisitionLn cshPaymentRequisitionLn = new CshPaymentRequisitionLn();
		cshPaymentRequisitionLn.setPaymentRequisitionHeaderId(headerId);
		Criteria criteria = new Criteria(cshPaymentRequisitionLn);
		criteria.where(new WhereField(CshPaymentRequisitionLn.FIELD_PAYMENT_REQUISITION_HEADER_ID, Comparison.EQUAL));
		List<CshPaymentRequisitionLn> lines = iPaymentRequisitionLnService
				.selectOptions(request, cshPaymentRequisitionLn, criteria);
		for (CshPaymentRequisitionLn line : lines) {
			Long lineId = line.getPaymentRequisitionLineId();
			line.setPaymentRequisitionHeaderId(newHeaderId);
			line.setPaymentRequisitionLineId(null);
			line.setAmount(line.getAmount().multiply(BigDecimal.valueOf(-1)));
			line.setCreationDate(null);
			line.setCreatedBy(null);
			line.setPaymentCompletedDate(null);
			CshPaymentRequisitionLn newLine = iPaymentRequisitionLnService.insertSelective(request, line);
			Long newLineId = newLine.getPaymentRequisitionLineId();
			reverseCshPmtReqAccount(request, lineId, newLineId, periodName, reverseDate);
		}
	}

	/**
	 * 反冲凭证
	 *
	 * @param lineId
	 * @author LJK 2019-03-11 15:43
	 * @return
	 */
	public void reverseCshPmtReqAccount(IRequest request, Long lineId, Long newLineId, String periodName,
			Date reverseDate) {
		CshPaymentReqAccount cshPaymentReqAccount = new CshPaymentReqAccount();
		cshPaymentReqAccount.setPaymentRequisitionLineId(lineId);
		Criteria criteria = new Criteria(cshPaymentReqAccount);
		criteria.where(new WhereField(CshPaymentReqAccount.FIELD_PAYMENT_REQUISITION_LINE_ID, Comparison.EQUAL));
		List<CshPaymentReqAccount> accountList = cshPaymentReqAccountService
				.selectOptions(request, cshPaymentReqAccount, criteria);
		for (CshPaymentReqAccount account : accountList) {
			account.setPaymentRequisitionLineId(newLineId);
			account.setCreatedBy(null);
			account.setCreationDate(null);
			account.setJournalDate(reverseDate);
			account.setPeriodName(periodName);
			if(null != account.getEnteredAmountCr()){
				account.setEnteredAmountCr(BigDecimal.valueOf(-1).multiply(account.getEnteredAmountCr()));
			}
			if(null != account.getEnteredAmountDr()){
				account.setEnteredAmountDr(BigDecimal.valueOf(-1).multiply(account.getEnteredAmountDr()));
			}
			if(null != account.getFunctionalAmountCr()){
				account.setFunctionalAmountCr(BigDecimal.valueOf(-1).multiply(account.getFunctionalAmountCr()));
			}
			if(null != account.getFunctionalAmountDr()){
				account.setFunctionalAmountDr(BigDecimal.valueOf(-1).multiply(account.getFunctionalAmountDr()));
			}
			account.setGldInterfaceFlag("N");
			cshPaymentReqAccountService.insertSelective(request, account);
			// 待处理 触发事件 evt_event_core_pkg.fire_event
		}
	}


	/**
	 * 费用申请财务关闭单查询   关闭、审批时调用(dto中需状态参数)
	 *
	 * @param request  IRequest
	 * @param dto  借款申请单表头实体
	 * @param pageNum 页码
	 * @param pageSize 页数
	 * @author weikun.wang2019-03-25 14:04
	 * @return List<CshPaymentRequisitionHd>
	 */
	@Override
	public List<CshPaymentRequisitionHd> queryForRequisitionReverse(IRequest request, CshPaymentRequisitionHd dto,
			int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return paymentRequisitionHdMapper.queryForRequisitionReverse(dto);
	}

	@Override
	public List<Map> queryPayRequisitionMain(IRequest request, CshPaymentRequisitionHd dto, int pageNum, int pageSize) {
		if (dto.getStatus() != null){
			dto.setStatus("'" + dto.getStatus().replace(";","','") + "'");
		}

		PageHelper.startPage(pageNum, pageSize);
		return paymentRequisitionHdMapper.queryPayRequisitionMain(dto);
	}

	@Override
	public List<CshPaymentRequisitionHd> queryPaymentRequisitionHeader(IRequest request,
			Long paymentRequisitionHeaderId, Long paymentReqTypeId, Long employeeId, Long accEntityId) {
		return paymentRequisitionHdMapper
				.queryPaymentRequisitionHeader(paymentRequisitionHeaderId, paymentReqTypeId, employeeId, accEntityId);
	}

	@Override
	public List<Map> selectPayReqRefReport(IRequest request, Long paymentRequisitionHeaderId) {
		return paymentRequisitionHdMapper.selectPayReqRefReport(paymentRequisitionHeaderId);
	}

	@Override
	public List<Map> selectPayReqRefRegister(IRequest request, Long paymentRequisitionHeaderId) {
		return paymentRequisitionHdMapper.selectPayReqRefRegister(paymentRequisitionHeaderId);
	}
}
