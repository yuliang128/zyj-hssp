package com.hand.hec.fnd.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.GldAePeriodStatus;
import com.hand.hec.fnd.exception.GldAePeriodStatusException;
import com.hand.hec.fnd.mapper.GldAePeriodStatusMapper;
import com.hand.hec.fnd.service.IGldAePeriodStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 核算主体级会计期间控制serviceImpl
 * </p>
 *
 * @author $JK.Lu$ 2019/01/22 17:26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GldAePeriodStatusServiceImpl extends BaseServiceImpl<GldAePeriodStatus>
		implements IGldAePeriodStatusService {

	@Autowired
	public GldAePeriodStatusMapper mapper;

	@Override
	public List<GldAePeriodStatus> accEntityListQuery() {
		return mapper.accEntityListQuery();
	}

	@Override
	public List<GldAePeriodStatus> unOpenedPeriodQuery(Long periodSetId, Long accEntityId, int page, int pageSize) {
		PageHelper.startPage(page, pageSize);
		return mapper.unOpenedPeriodQuery(periodSetId, accEntityId);
	}

	@Override
	public List<GldAePeriodStatus> OpenedPeriodQuery(Long periodSetId, Long accEntityId, int page, int pageSize) {
		PageHelper.startPage(page, pageSize);
		return mapper.OpenedPeriodQuery(periodSetId, accEntityId);
	}

	@Override
	public void initPeriod(IRequest request, GldAePeriodStatus periodStatus) {
		if (canInitPeriod(periodStatus.getAccEntityId(), periodStatus.getPeriodSetCode())) {
			this.openGldPeriodStatus(request, periodStatus);
		}
	}

	boolean canInitPeriod(Long accEntityId, String periodSetCode) {
		GldAePeriodStatus periodStatus = new GldAePeriodStatus();
		periodStatus.setAccEntityId(accEntityId);
		periodStatus.setPeriodSetCode(periodSetCode);
		int count = mapper.selectCount(periodStatus);
		if (count == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void openGldPeriodStatus(IRequest request, GldAePeriodStatus periodStatus) {
		periodStatus.setPeriodStatusCode("O");
		self().insertSelective(request, periodStatus);
	}

	@Override
	public void reopenGldPeriodStatus(IRequest request, GldAePeriodStatus periodStatus) {
		periodStatus.setPeriodStatusCode("O");
		self().updateByPrimaryKeySelective(request, periodStatus);
	}

	@Override
	public void closeGldPeriodStatus(IRequest request, GldAePeriodStatus periodStatus) {
		periodStatus.setPeriodStatusCode("C");
		self().updateByPrimaryKeySelective(request, periodStatus);
	}

	@Override
	public void openPeriod(IRequest request, GldAePeriodStatus periodStatus, Long periodSetId)
			throws GldAePeriodStatusException {
		GldAePeriodStatus status = new GldAePeriodStatus();
		status.setAccEntityId(periodStatus.getAccEntityId());
		status.setPeriodSetCode(periodStatus.getPeriodSetCode());
		status.setPeriodName(periodStatus.getPeriodName());
		status.setInternalPeriodNum(periodStatus.getInternalPeriodNum());
		int count = mapper.selectCount(status);
		if (count == 0) {
			Long internalPeriodNum = mapper
					.selectLastPeriod(periodStatus.getAccEntityId(), periodSetId, periodStatus.getInternalPeriodNum());
			if (null != internalPeriodNum) {
				int check = mapper.checkLastPeriod(periodStatus.getAccEntityId(), periodStatus.getPeriodSetCode(),
						internalPeriodNum, null);
				if (check == 0) {
					throw new GldAePeriodStatusException(GldAePeriodStatusException.GLD_LAST_PERIOD_NOT_OPENED_ERROR,
							null);
				}
			}
			this.openGldPeriodStatus(request, periodStatus);
		} else {
			Long internalPeriodNum = mapper
					.selectNextPeriod(periodStatus.getAccEntityId(), periodStatus.getPeriodSetCode(),
							periodStatus.getInternalPeriodNum());
			if (null != internalPeriodNum) {
				int check = mapper.checkNextPeriod(periodStatus.getAccEntityId(), periodStatus.getPeriodSetCode(),
						internalPeriodNum);
				if (check == 0) {
					throw new GldAePeriodStatusException(GldAePeriodStatusException.GLD_NEXT_PERIOD_NOT_OPENED_ERROR,
							null);
				}
			}
			this.reopenGldPeriodStatus(request, periodStatus);
		}
	}

	@Override
	public void closePeriod(IRequest request, GldAePeriodStatus periodStatus, Long periodSetId)
			throws GldAePeriodStatusException {

		int count = mapper.checkIsFirstPeriod(periodStatus.getAccEntityId(), periodStatus.getPeriodSetCode(),
				periodStatus.getInternalPeriodNum());
		if (count == 0) {
			this.closeGldPeriodStatus(request, periodStatus);
		} else {
			Long internalPeriodNum = mapper
					.selectLastPeriod(periodStatus.getAccEntityId(), periodSetId, periodStatus.getInternalPeriodNum());
			if (null != internalPeriodNum) {
				int check = mapper.checkLastPeriod(periodStatus.getAccEntityId(), periodStatus.getPeriodSetCode(),
						internalPeriodNum, "C");
				if (check == 0) {
					throw new GldAePeriodStatusException(GldAePeriodStatusException.GLD_LAST_PERIOD_NOT_CLOSED_ERROR,
							null);
				}
			}
			this.closeGldPeriodStatus(request, periodStatus);
		}
	}
}