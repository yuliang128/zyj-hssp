package com.hand.hap.aurora.attachment.mapper;

import com.hand.hap.aurora.attachment.dto.FndAtmAttachment;
import com.hand.hap.mybatis.common.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FndAtmAttachmentMapper extends Mapper<FndAtmAttachment>{
	/**
	 * 查詢附件信息
	 * @return
	 */
	List<FndAtmAttachment> queryFndAtmAttachment(@Param("attachmentId") Long attachmentId, @Param("tableName") String tableName, @Param("tablePkValue") Long tablePkValue);
}