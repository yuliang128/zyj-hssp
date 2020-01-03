package com.hand.hec.fnd.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.GldAePeriodStatus;
import com.hand.hec.fnd.dto.GldPeriod;
import com.hand.hec.fnd.dto.GldPeriodSet;
import com.hand.hec.fnd.dto.GldSobPeriodStatus;
import com.hand.hec.fnd.exception.GldSobPeriodStatusException;
import com.hand.hec.fnd.mapper.FndMagOrgRefGldSobMapper;
import com.hand.hec.fnd.mapper.GldAePeriodStatusMapper;
import com.hand.hec.fnd.mapper.GldSobPeriodStatusMapper;
import com.hand.hec.fnd.service.IGldAePeriodStatusService;
import com.hand.hec.fnd.service.IGldPeriodService;
import com.hand.hec.fnd.service.IGldPeriodSetService;
import com.hand.hec.fnd.service.IGldSobPeriodStatusService;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.mapper.GldAccountingEntityMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldSobPeriodStatusServiceImpl extends BaseServiceImpl<GldSobPeriodStatus> implements
		IGldSobPeriodStatusService {

	@Autowired
	GldSobPeriodStatusMapper gldSobPeriodStatusMapper;

	@Autowired
	FndMagOrgRefGldSobMapper fndMagOrgRefGldSobMapper;

	@Autowired
	IGldPeriodService gldPeriodService;

	@Autowired
	IGldPeriodSetService gldPeriodSetService;

	@Autowired
	GldAccountingEntityMapper gldAccountingEntityMapper;

	@Autowired
	GldAePeriodStatusMapper gldAePeriodStatusMapper;


	@Autowired
	IGldAePeriodStatusService gldAePeriodStatusService;

	@Override
	public List<GldSobPeriodStatus> queryForStatusOpen(IRequest requestContext, GldSobPeriodStatus dto, int page,
			int pageSize) {
		PageHelper.startPage(page, pageSize);
		return gldSobPeriodStatusMapper.queryForStatusOpen(dto);
	}

	@Override
	public List<GldSobPeriodStatus> queryForStatusClosed(IRequest requestContext, GldSobPeriodStatus dto, int page,
			int pageSize) {
		PageHelper.startPage(page, pageSize);
		return gldSobPeriodStatusMapper.queryForStatusClosed(dto);
	}


	@Override
	public boolean initPeriod(IRequest requestContext, GldSobPeriodStatus dto, int page, int pageSize) {

		if(self().canInitPeriod(requestContext, dto.getPeriodSetCode(), dto.getSetOfBooksId())){
			Long internalPeriodNum = gldPeriodService.queryInternalPeriodNum(requestContext,dto.getPeriodName(), dto.getPeriodSetCode());
			if(internalPeriodNum != null){
				dto.setInternalPeriodNum(internalPeriodNum);
				// 执行存储过程 openGldPeriodStatus ，其实就是执行插入
				GldSobPeriodStatus gldSobPeriodStatus = self().openGldPeriodStatus(requestContext, dto);

				if( gldSobPeriodStatus.getPeriodStatusId() != null){
					return true;
				}
			}

		}

		return false;
	}

	@Override
	public boolean openPeriod(IRequest requestContext, GldSobPeriodStatus dto, int page, int pageSize) throws GldSobPeriodStatusException{

		Long internalPeriodNum = gldPeriodService.queryInternalPeriodNum(requestContext,dto.getPeriodName(), dto.getPeriodSetCode());

		// 是否是第一次打开
		GldSobPeriodStatus gldSobPeriodStatusCondition = new GldSobPeriodStatus();
		gldSobPeriodStatusCondition.setSetOfBooksId(dto.getSetOfBooksId());
		gldSobPeriodStatusCondition.setPeriodSetCode(dto.getPeriodSetCode());
		gldSobPeriodStatusCondition.setPeriodName(dto.getPeriodName());
		gldSobPeriodStatusCondition.setInternalPeriodNum(internalPeriodNum);
		List<GldSobPeriodStatus> gldSobPeriodStatusList = gldSobPeriodStatusMapper.queryGldSobPeriodStatus(gldSobPeriodStatusCondition);
		boolean isFirstOpen = false;
		if( gldSobPeriodStatusList == null || gldSobPeriodStatusList.isEmpty() ){
			isFirstOpen = true;
		}


		if(isFirstOpen){
			// 前一个期间必须是打开过的

			GldPeriod gldPeriod = new  GldPeriod();
			gldPeriod.setPeriodSetCode(dto.getPeriodSetCode());
			gldPeriod.setInternalPeriodNum(internalPeriodNum);
			Long lastInternalPeriodNum = gldPeriodService.queryLastInternalPeriodNum(requestContext, gldPeriod);

			GldSobPeriodStatus gldSobPeriodStatusExistCondition = new GldSobPeriodStatus();
			gldSobPeriodStatusExistCondition.setSetOfBooksId(dto.getSetOfBooksId());
			gldSobPeriodStatusExistCondition.setPeriodSetCode(dto.getPeriodSetCode());
			gldSobPeriodStatusExistCondition.setInternalPeriodNum(lastInternalPeriodNum);
			List<GldSobPeriodStatus> gldSobPeriodStatusExistList = gldSobPeriodStatusMapper.queryGldSobPeriodStatus(gldSobPeriodStatusExistCondition);
			if(gldSobPeriodStatusExistList == null || gldSobPeriodStatusExistList.isEmpty()){
				throw new GldSobPeriodStatusException(GldSobPeriodStatusException.GLD_LAST_PERIOD_NOT_CLOSED_ERROR, null);
			}

			// 执行存储过程 openGldPeriodStatus ，其实就是执行插入
			dto.setInternalPeriodNum(internalPeriodNum);
			GldSobPeriodStatus gldSobPeriodStatus = self().openGldPeriodStatus(requestContext, dto);

			return true;
		}else if(!isFirstOpen){
			//  如果不是打开的最后一个期间,下一个期间必须是打开过的
			Long nextInternalPeriodNum = gldPeriodService.queryNextInternalPeriodNum(requestContext,dto.getPeriodSetCode(), internalPeriodNum);
			if(nextInternalPeriodNum !=null){
				GldSobPeriodStatus gldSobPeriodStatusExistCondition = new GldSobPeriodStatus();
				gldSobPeriodStatusExistCondition.setSetOfBooksId(dto.getSetOfBooksId());
				gldSobPeriodStatusExistCondition.setPeriodSetCode(dto.getPeriodSetCode());
				gldSobPeriodStatusExistCondition.setInternalPeriodNum(nextInternalPeriodNum);
				gldSobPeriodStatusExistCondition.setPeriodStatusCode(GldSobPeriodStatus.FIELD_PERIOD_STATUS_TYPE_O);

				List<GldSobPeriodStatus> gldSobPeriodStatusExistList = gldSobPeriodStatusMapper.queryGldSobPeriodStatus(gldSobPeriodStatusExistCondition);

				if(gldSobPeriodStatusExistList == null || gldSobPeriodStatusExistList.isEmpty()){
					throw new GldSobPeriodStatusException(GldSobPeriodStatusException.GLD_NEXT_PERIOD_NOT_OPENED_ERROR, null);
				}

				// 执行存储过程 reopen_gld_period_status
				dto.setInternalPeriodNum(internalPeriodNum);
				self().reopenGldPeriodStatus(requestContext, dto);

				return true;
			}
		}

		return false;
	}


	@Override
	public boolean closePeriod(IRequest requestContext, GldSobPeriodStatus dto, int page, int pageSize) {
		Long internalPeriodNum = gldPeriodService.queryInternalPeriodNum(requestContext, dto.getPeriodName(), dto.getPeriodSetCode());
		//gldSobPeriodStatusMapper.deleteFndSobClosePeriodErr();

		Long checkIsFirstOpen = gldSobPeriodStatusMapper.checkIsFirstOpen(dto);
		boolean isFirstOpen=false;
		if(checkIsFirstOpen == null || checkIsFirstOpen == 0){
			isFirstOpen = true;
		}

		if(isFirstOpen){
			dto.setInternalPeriodNum(internalPeriodNum);
			closeGldPeriodStatus(requestContext, dto);
			return true;
		}else{

			// 前一个期间必须是打开过的
			GldPeriod gldPeriod = new  GldPeriod();
			gldPeriod.setPeriodSetCode(dto.getPeriodSetCode());
			gldPeriod.setInternalPeriodNum(internalPeriodNum);
			Long lastInternalPeriodNum = gldPeriodService.queryLastInternalPeriodNum(requestContext, gldPeriod);

			GldSobPeriodStatus gldSobPeriodStatusExistCondition = new GldSobPeriodStatus();
			gldSobPeriodStatusExistCondition.setSetOfBooksId(dto.getSetOfBooksId());
			gldSobPeriodStatusExistCondition.setPeriodSetCode(dto.getPeriodSetCode());
			gldSobPeriodStatusExistCondition.setInternalPeriodNum(lastInternalPeriodNum);
			gldSobPeriodStatusExistCondition.setPeriodStatusCode(GldSobPeriodStatus.FIELD_PERIOD_STATUS_TYPE_C);
			List<GldSobPeriodStatus> gldSobPeriodStatusExistList = gldSobPeriodStatusMapper.queryGldSobPeriodStatus(gldSobPeriodStatusExistCondition);
			if(gldSobPeriodStatusExistList == null || gldSobPeriodStatusExistList.isEmpty()){
				throw new GldSobPeriodStatusException(GldSobPeriodStatusException.GLD_LAST_PERIOD_NOT_CLOSED_ERROR, null);
			}



			dto.setInternalPeriodNum(internalPeriodNum);
			closeGldPeriodStatus(requestContext, dto);

			return true;
		}

	}

	@Override
	public void closeGldPeriodStatus(IRequest requestContext, GldSobPeriodStatus dto) {

		gldSobPeriodStatusMapper.closeGldSobPeriodStatus(dto);

		// 账套级会计期间关闭，同时关闭以此账套为默认账套的所有核算主体对应的会计期间
		GldPeriodSet gldPeriodSet = gldPeriodSetService.queryByPeriodSetCode(dto.getPeriodSetCode());
		if(gldPeriodSet != null ){
			Long periodSetId = gldPeriodSetService.queryByPeriodSetCode(dto.getPeriodSetCode()).getPeriodSetId();
			List<GldAccountingEntity> gldAccountingEntityList = gldAccountingEntityMapper.queryGldAccountingEntityBySobId(dto.getSetOfBooksId());

			for( GldAccountingEntity gldAccountingEntity : gldAccountingEntityList){
				//  open_period_related_to_acc
				self().closePeriodRelatedToAcc(requestContext,gldAccountingEntity.getAccEntityId(),periodSetId,dto.getPeriodName(),requestContext.getUserId());
			}
		}

	}

	@Override
	public boolean canInitPeriod(IRequest requestContext, String periodSetCode, Long setOfBooksId) {

		if(StringUtils.isNotEmpty(periodSetCode)){
			GldSobPeriodStatus gldSobPeriodStatusCondition = new GldSobPeriodStatus();
			gldSobPeriodStatusCondition.setSetOfBooksId(setOfBooksId);
			gldSobPeriodStatusCondition.setPeriodSetCode(periodSetCode);
			List<GldSobPeriodStatus> gldSobPeriodStatusList = gldSobPeriodStatusMapper.queryGldSobPeriodStatus(gldSobPeriodStatusCondition);
			if( gldSobPeriodStatusList == null || gldSobPeriodStatusList.isEmpty()){
				return true;
			}else{
				return false;
			}
		}
		return true;
	}

	@Override
	public GldSobPeriodStatus openGldPeriodStatus(IRequest requestContext, GldSobPeriodStatus dto) {
		dto.setPeriodStatusCode(GldSobPeriodStatus.FIELD_PERIOD_STATUS_TYPE_O);
		return self().insertSelective(requestContext, dto);
	}

	@Override
	public void reopenGldPeriodStatus(IRequest requestContext, GldSobPeriodStatus dto) throws GldSobPeriodStatusException {
		 dto.setLastUpdatedBy(requestContext.getUserId());
		 gldSobPeriodStatusMapper.reopenGldPeriodStatus(dto);

		// 账套级会计期间打开后，打开以此账套为默认账套的所有核算主体对应的会计期间
		GldPeriodSet gldPeriodSet = gldPeriodSetService.queryByPeriodSetCode(dto.getPeriodSetCode());
		if(gldPeriodSet != null ){
			Long periodSetId = gldPeriodSetService.queryByPeriodSetCode(dto.getPeriodSetCode()).getPeriodSetId();
			List<GldAccountingEntity> gldAccountingEntityList = gldAccountingEntityMapper.queryGldAccountingEntityBySobId(dto.getSetOfBooksId());

			for( GldAccountingEntity gldAccountingEntity : gldAccountingEntityList){
				//  open_period_related_to_acc
				self().openPeriodRelatedToAcc(requestContext,gldAccountingEntity.getAccEntityId(),periodSetId,dto.getPeriodName(),requestContext.getUserId());
			}
		}



	}

	@Override
	public void openPeriodRelatedToAcc(IRequest requestContext, Long accEntityId, Long periodSetId, String periodName, Long userId) {
		GldPeriod gldPeriodCondition = new GldPeriod();
		gldPeriodCondition.setPeriodName(periodName);
		gldPeriodCondition.setPeriodSetId(periodSetId);

		GldPeriod gldPeriod = gldPeriodService.queryGldPeriod(requestContext, gldPeriodCondition).get(0);

		GldAePeriodStatus gldAePeriodStatusCondition = new GldAePeriodStatus();
		gldAePeriodStatusCondition.setAccEntityId(accEntityId);
		gldAePeriodStatusCondition.setPeriodSetCode(gldPeriod.getPeriodSetCode());
		gldAePeriodStatusCondition.setPeriodName(periodName);
		gldAePeriodStatusCondition.setInternalPeriodNum(gldPeriod.getInternalPeriodNum());

		List<GldAePeriodStatus> gldAePeriodStatusList = gldAePeriodStatusMapper.queryGldAePeriodStatus(gldAePeriodStatusCondition);

		boolean isFirstOpen = false;
		if(gldAePeriodStatusList == null || gldAePeriodStatusList.isEmpty()){
			isFirstOpen = true;
		}


		if(isFirstOpen) {
			// 前一个期间必须是打开过的
			GldPeriod gp = new GldPeriod();
			gp.setPeriodSetId(periodSetId);
			gp.setInternalPeriodNum(gldPeriod.getInternalPeriodNum());
			Long lastInternalPeriodNum = gldPeriodService.queryLastInternalPeriodNum(requestContext, gp);


			GldAePeriodStatus gpsCondition = new GldAePeriodStatus();
			gpsCondition.setAccEntityId(accEntityId);
			gpsCondition.setPeriodSetId(periodSetId);
			gpsCondition.setInternalPeriodNum(gldPeriod.getInternalPeriodNum());

			List<GldAePeriodStatus> gpsList = gldAePeriodStatusMapper.queryGldAePeriodStatus(gpsCondition);
			if (gpsList == null || gpsList.isEmpty()) {
				throw new GldSobPeriodStatusException(GldSobPeriodStatusException.GLD_LAST_PERIOD_NOT_CLOSED_ERROR, null);
			}

			//执行存储过程  gld_period_pkg.open_gld_period_status
			gldAePeriodStatusService.openGldPeriodStatus(requestContext, gldAePeriodStatusCondition);

		}else{
            //如果不是打开的最后一个期间,下一个期间必须是打开过的
			GldAePeriodStatus gpsCondition = new GldAePeriodStatus();
			gpsCondition.setAccEntityId(accEntityId);
			gpsCondition.setPeriodSetId(periodSetId);
			gpsCondition.setInternalPeriodNum(gldPeriod.getInternalPeriodNum());
			Long nextInternalPeriodNum = gldAePeriodStatusMapper.queryNextInternalPeriodNum(gpsCondition);

			if(nextInternalPeriodNum != null && nextInternalPeriodNum != 0){
				GldAePeriodStatus gapsCondition= new GldAePeriodStatus();
				gapsCondition.setAccEntityId(accEntityId);
				gapsCondition.setPeriodSetCode(gldPeriod.getPeriodSetCode());
				gapsCondition.setInternalPeriodNum(nextInternalPeriodNum);
				gapsCondition.setPeriodStatusCode("O");
				List<GldAePeriodStatus> aePeriodStatusList = gldAePeriodStatusMapper.queryGldAePeriodStatus(gapsCondition);
				if(aePeriodStatusList ==null ||aePeriodStatusList.isEmpty()){
					throw new GldSobPeriodStatusException(GldSobPeriodStatusException.GLD_NEXT_PERIOD_NOT_OPENED_ERROR, null);
				}

				//执行存储过程  gld_period_pkg.reopen_gld_period_status
				gldAePeriodStatusService.reopenGldPeriodStatus(requestContext, gldAePeriodStatusCondition);

			}

		}
	}


	@Override
	public void closePeriodRelatedToAcc(IRequest requestContext, Long accEntityId, Long periodSetId, String periodName,
			Long userId)  {
		GldPeriod gldPeriodCondition = new GldPeriod();
		gldPeriodCondition.setPeriodName(periodName);
		gldPeriodCondition.setPeriodSetId(periodSetId);

		GldPeriod gldPeriod = gldPeriodService.queryGldPeriod(requestContext, gldPeriodCondition).get(0);

		GldAePeriodStatus gldAePeriodStatusCondition = new GldAePeriodStatus();
		gldAePeriodStatusCondition.setAccEntityId(accEntityId);
		gldAePeriodStatusCondition.setPeriodSetCode(gldPeriod.getPeriodSetCode());
		gldAePeriodStatusCondition.setInternalPeriodNumForClose(gldPeriod.getInternalPeriodNum());

		List<GldAePeriodStatus> gldAePeriodStatusList = gldAePeriodStatusMapper.queryGldAePeriodStatus(gldAePeriodStatusCondition);

		boolean isFirstOpen = false;
		if(gldAePeriodStatusList == null || gldAePeriodStatusList.isEmpty()){
			isFirstOpen = true;
		}


		if(isFirstOpen) {
			// 执行存储过程  gld_period_pkg.close_gld_period_status
			gldAePeriodStatusCondition.setPeriodName(periodName);
			gldAePeriodStatusCondition.setLastUpdatedBy(userId);
			gldAePeriodStatusService.closeGldPeriodStatus(requestContext, gldAePeriodStatusCondition);
		}else{
			// 前一个期间必须是打开过的
			GldPeriod gp = new GldPeriod();
			gp.setPeriodSetId(periodSetId);
			gp.setInternalPeriodNum(gldPeriod.getInternalPeriodNum());
			Long lastInternalPeriodNum = gldPeriodService.queryLastInternalPeriodNum(requestContext, gp);


			GldAePeriodStatus gpsCondition = new GldAePeriodStatus();
			gpsCondition.setAccEntityId(accEntityId);
			gpsCondition.setPeriodSetCode(gldPeriod.getPeriodSetCode());
			gpsCondition.setInternalPeriodNum(lastInternalPeriodNum);
			gpsCondition.setPeriodStatusCode(GldSobPeriodStatus.FIELD_PERIOD_STATUS_TYPE_C);

			List<GldAePeriodStatus> gpsList = gldAePeriodStatusMapper.queryGldAePeriodStatus(gpsCondition);
			if (gpsList == null || gpsList.isEmpty()) {
				throw new GldSobPeriodStatusException(GldSobPeriodStatusException.GLD_LAST_PERIOD_NOT_CLOSED_ERROR, null);
			}

			// 执行存储过程  gld_period_pkg.close_gld_period_status
			gldAePeriodStatusCondition.setPeriodName(periodName);
			gldAePeriodStatusCondition.setLastUpdatedBy(userId);
			gldAePeriodStatusService.closeGldPeriodStatus(requestContext, gldAePeriodStatusCondition);
		}


	}
}