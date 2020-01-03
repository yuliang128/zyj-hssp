package com.hand.hec.fnd.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.FndDocInfo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/3/20 \* Time: 17:40 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */

public interface IFndDocEngineService extends IBaseService<FndDocInfo>, ProxySelf<IFndDocEngineService> {

    public List<FndDocInfo> getDocInfo(String docCategory);

    public FndDocInfo getDocHeadInfo(String docCategory);

    public HashMap getHeadDocData(String docCategory, String docId);

    public FndDocInfo getDocSumInfo(String docCategory);

    public HashMap getSumDocData(String docCategory, String docId);

    public List<HashMap> getDocData(FndDocInfo docInfo, String docId);

    public Long getDocTypeId(String docCategory, String docId);

    public String getDocNumber(String docCategory, String docId);

    public BigDecimal getDocAmount(String docCategory, String docId);
}
