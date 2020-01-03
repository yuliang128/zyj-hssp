package com.hand.hap.aurora.attachment.service;

import com.hand.hap.aurora.attachment.dto.FndAtmAttachment;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IFndAtmAttachmentService extends IBaseService<FndAtmAttachment>, ProxySelf<IFndAtmAttachmentService>{

	List<FndAtmAttachment> queryFndAtmAttachment(IRequest request, FndAtmAttachment dto,  int pageNum, int pageSize);

	List<FndAtmAttachment>  queryFndAtmAttachment(IRequest request,  @StdWho FndAtmAttachment dto);

	/**
	 *  upload attachment：
	 *  	process:
	 *  		1. save file to fileServer
	 *  		2. save fileInfo to db
	 * @param request request
	 * @return list of attachment info
	 */
	List<FndAtmAttachment> upload(HttpServletRequest request);

	/**
	 * download by attachmentId
	 * @param attachmentId attachmentId
	 * @param response response
	 */
	String download(Long attachmentId, HttpServletResponse response);

	/**
	 * remove by attachmentId
	 * 		process:
	 * 			1. remove file which is in the fileServer
	 * 			2. delete fileInfo in the db
	 * @param fndAtmAttachmentList fndAtmAttachmentList
	 */
	void remove(IRequest request, @StdWho List<FndAtmAttachment> fndAtmAttachmentList);



	/**
	 *  remove by  tableName and tablePkValue
	 * @param request
	 * @param tableName
	 * @param tablePkValue
	 */
	void remove(IRequest request, String tableName, Long tablePkValue);


}