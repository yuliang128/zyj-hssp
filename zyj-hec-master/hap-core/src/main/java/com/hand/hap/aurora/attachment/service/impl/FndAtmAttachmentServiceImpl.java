package com.hand.hap.aurora.attachment.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.attachment.UpConstants;
import com.hand.hap.aurora.attachment.dto.FndAtmAttachment;
import com.hand.hap.aurora.attachment.dto.FndAtmAttachmentRefRecord;
import com.hand.hap.aurora.attachment.exception.FndAtmAttachmentException;
import com.hand.hap.aurora.attachment.mapper.FndAtmAttachmentMapper;
import com.hand.hap.aurora.attachment.mapper.FndAtmAttachmentRefRecordMapper;
import com.hand.hap.aurora.attachment.processor.AttachmentProcessorFactory;
import com.hand.hap.aurora.attachment.processor.IAttachmentProcessor;
import com.hand.hap.aurora.attachment.service.IFndAtmAttachmentService;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.security.TokenUtils;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class FndAtmAttachmentServiceImpl extends BaseServiceImpl<FndAtmAttachment> implements IFndAtmAttachmentService{

	@Autowired
	FndAtmAttachmentMapper fndAtmAttachmentMapper;

	@Autowired
	FndAtmAttachmentRefRecordMapper fndAtmAttachmentRefRecordMapper;


	@Override
	public List<FndAtmAttachment> queryFndAtmAttachment(IRequest request, FndAtmAttachment dto, int pageNum,
			int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		return self().queryFndAtmAttachment(request, dto);
	}


	@Override
	public List<FndAtmAttachment>  queryFndAtmAttachment(IRequest request,  @StdWho FndAtmAttachment dto){

		if(StringUtils.isEmpty(dto.getTableName()) || dto.getTablePkValue() == null){
			throw new FndAtmAttachmentException(FndAtmAttachmentException.ERROR_INVALID_PARAM, new Object[0]);
		}


		FndAtmAttachmentRefRecord fndAtmAttachmentRefRecordCondition = new FndAtmAttachmentRefRecord();
		fndAtmAttachmentRefRecordCondition.setTableName(dto.getTableName());
		fndAtmAttachmentRefRecordCondition.setTablePkValue(dto.getTablePkValue());
		List<FndAtmAttachmentRefRecord> fndAtmAttachmentRefRecordList = fndAtmAttachmentRefRecordMapper.select(fndAtmAttachmentRefRecordCondition);

		List<FndAtmAttachment> fndAtmAttachmentList = new ArrayList<>();
		for( FndAtmAttachmentRefRecord fndAtmAttachmentRefRecord : fndAtmAttachmentRefRecordList){
			FndAtmAttachment fndAtmAttachment = fndAtmAttachmentMapper.selectByPrimaryKey( fndAtmAttachmentRefRecord.getAttachmentId());
			if(fndAtmAttachment != null){
				fndAtmAttachment.setShowDelete(dto.getShowDelete());
				fndAtmAttachmentList.add(fndAtmAttachment);
			}else{
				// invalid data
				fndAtmAttachmentRefRecordMapper.deleteByPrimaryKey(fndAtmAttachmentRefRecord);
			}
		}

		return fndAtmAttachmentList;
	}



	@Override
	public List<FndAtmAttachment> upload(HttpServletRequest request) {

		// 1. save file
		IAttachmentProcessor attachmentProcessor = AttachmentProcessorFactory.createInstance(request.getParameter(FndAtmAttachment.FIELD_SAVE_TYPE));
		List<FndAtmAttachment> attachmentInfoList = attachmentProcessor.upload(request);

		//上传过后 重新获取状态
		String  status = attachmentProcessor.getStatus();
		if (UpConstants.FILE_PROCESS_ERROR.equals(status)) {
			throw new FndAtmAttachmentException(UpConstants.ERROR_UPLOAD_FILE_PROCESS, new Object[0]);
		}

		// 2. save fileInfo to db
		attachmentInfoList.forEach(fndAtmAttachment -> {
			fndAtmAttachmentMapper.insert(fndAtmAttachment);
			TokenUtils.generateAndSetToken(TokenUtils.getSecurityKey(request.getSession(false)), fndAtmAttachment);
			FndAtmAttachmentRefRecord fndAtmAttachmentRefRecord = new FndAtmAttachmentRefRecord();
			fndAtmAttachmentRefRecord.setAttachmentId(fndAtmAttachment.getAttachmentId());
			fndAtmAttachmentRefRecord.setTableName(fndAtmAttachment.getTableName());
			fndAtmAttachmentRefRecord.setTablePkValue(fndAtmAttachment.getTablePkValue());
			fndAtmAttachmentRefRecordMapper.insert(fndAtmAttachmentRefRecord);
		});

		return attachmentInfoList;
	}


	@Override
	public String download(Long attachmentId, HttpServletResponse response) {

		FndAtmAttachment fndAtmAttachment =	fndAtmAttachmentMapper.selectByPrimaryKey(attachmentId);

		if(fndAtmAttachment == null){
			throw new FndAtmAttachmentException(UpConstants.ERROR_DOWNLOAD_FILE_ERROR, new Object[0]);
		}

		IAttachmentProcessor attachmentProcessor = AttachmentProcessorFactory.createInstance(fndAtmAttachment.getSaveType());
		attachmentProcessor.download(fndAtmAttachment, response);

		return attachmentProcessor.getStatus();
	}



	@Override
	public void remove(IRequest request, @StdWho List<FndAtmAttachment> fndAtmAttachmentList) {
		fndAtmAttachmentList.forEach(dto -> {
			// 1. remove file
			IAttachmentProcessor attachmentProcessor = AttachmentProcessorFactory.createInstance(dto.getSaveType());
			attachmentProcessor.remove(dto);

			// 2.delete fileInfo
			FndAtmAttachmentRefRecord fndAtmAttachmentRefRecord = new FndAtmAttachmentRefRecord();
			fndAtmAttachmentRefRecord.setAttachmentId(dto.getAttachmentId());
			fndAtmAttachmentRefRecord.setTableName(dto.getTableName());
			fndAtmAttachmentRefRecord.setTablePkValue(dto.getTablePkValue());
			fndAtmAttachmentRefRecordMapper.delete(fndAtmAttachmentRefRecord);

			fndAtmAttachmentMapper.deleteByPrimaryKey(dto);

		});
	}


	@Override
	public void remove(IRequest request, String tableName, Long tablePkValue) {
		if( StringUtils.isEmpty(tableName) || tablePkValue == null){
			throw new FndAtmAttachmentException(FndAtmAttachmentException.ERROR_INVALID_PARAM, new Object[0]);
		}

		FndAtmAttachment fndAtmAttachment = new FndAtmAttachment();
		fndAtmAttachment.setTableName(tableName);
		fndAtmAttachment.setTablePkValue(tablePkValue);
		List<FndAtmAttachment> fndAtmAttachmentList = queryFndAtmAttachment( request, fndAtmAttachment);

		self().remove( request, fndAtmAttachmentList);

	}
}