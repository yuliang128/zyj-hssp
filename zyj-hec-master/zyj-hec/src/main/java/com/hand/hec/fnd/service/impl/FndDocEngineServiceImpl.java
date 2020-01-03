package com.hand.hec.fnd.service.impl;

import com.hand.hap.core.exception.BaseRuntimeExcepiton;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.exception.FndDocNotExistsException;
import com.hand.hec.fnd.mapper.FndDocEngineMapper;
import com.hand.hec.fnd.dto.FndDocInfo;
import com.hand.hec.fnd.service.IFndDocEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/3/20 \* Time: 17:44 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class FndDocEngineServiceImpl extends BaseServiceImpl<FndDocInfo> implements IFndDocEngineService {

    public static final String TABLE_LEVEL_HEAD = "HEAD";
    public static final String TABLE_LEVEL_LINE = "LINE";
    public static final String TABLE_LEVEL_DIST = "DIST";

    @Autowired
    FndDocEngineMapper fndDocEngineMapper;

    /**
     * 获取指定单据类别的单据配置信息
     *
     * @param docCategory 单据类别
     * @return 单据配置信息
     */
    @Override
    public FndDocInfo getDocHeadInfo(String docCategory) {
        FndDocInfo docInfo = new FndDocInfo();
        docInfo.setDocCategory(docCategory);
        docInfo.setTableLevelCode("HEAD");
        List<FndDocInfo> docInfoList = fndDocEngineMapper.getDocInfo(docInfo);
        if (docInfoList.size() == 0) {
            throw new FndDocNotExistsException("FND", "fnd_doc_info.doc_not_exists", null);
        }
        if (docInfoList.size() > 1) {
            throw new FndDocNotExistsException("FND", "fnd_doc_info.head_more_than_one", null);
        }
        docInfo = docInfoList.get(0);
        return docInfo;
    }

    @Override
    public List<FndDocInfo> getDocInfo(String docCategory) {
        FndDocInfo docInfo = new FndDocInfo();
        docInfo.setDocCategory(docCategory);
        List<FndDocInfo> docInfoList = fndDocEngineMapper.getDocInfo(docInfo);
        return docInfoList;
    }

    /**
     * 获取对应单据类别和单据ID的单据头数据
     *
     * @param docCategory 单据类别
     * @param docId 单据ID
     * @return 单据头级别的数据
     */
    @Override
    public HashMap getHeadDocData(String docCategory, String docId) {
        FndDocInfo docInfo = self().getDocHeadInfo(docCategory);
        long numberDocId = Long.parseLong(docId);
        List<HashMap> docData = fndDocEngineMapper.getDocData(docInfo, numberDocId);
        if (docData.size() == 0) {
            throw new FndDocNotExistsException("FND", "fnd_doc_info.doc_not_exists", null);
        }
        return docData.get(0);
    }

    @Override
    public FndDocInfo getDocSumInfo(String docCategory) {
        FndDocInfo docInfo = new FndDocInfo();
        docInfo.setDocCategory(docCategory);
        docInfo.setTableLevelCode("SUM");
        List<FndDocInfo> docInfoList = fndDocEngineMapper.getDocInfo(docInfo);
        if (docInfoList.size() == 0) {
            throw new FndDocNotExistsException("FND", "fnd_doc_info.doc_not_exists", null);
        }
        if (docInfoList.size() > 1) {
            throw new FndDocNotExistsException("FND", "fnd_doc_info.sum_more_than_one", null);
        }
        docInfo = docInfoList.get(0);
        return docInfo;
    }

    @Override
    public HashMap getSumDocData(String docCategory, String docId) {
        FndDocInfo docInfo = self().getDocSumInfo(docCategory);
        long numberDocId = Long.parseLong(docId);
        List<HashMap> docData = fndDocEngineMapper.getDocData(docInfo, numberDocId);
        if (docData.size() == 0) {
            throw new FndDocNotExistsException("FND", "fnd_doc_info.doc_not_exists", null);
        }
        return docData.get(0);
    }

    @Override
    public List<HashMap> getDocData(FndDocInfo docInfo, String docId) {
        long numberDocId = Long.parseLong(docId);
        List<HashMap> docData = fndDocEngineMapper.getDocData(docInfo, numberDocId);
        return docData;
    }

    /**
     * 获取对应单据类别和单据ID的单据类型ID
     *
     * @param docCategory 单据类别
     * @param docId 单据ID
     * @return 单据类型ID
     */
    @Override
    public Long getDocTypeId(String docCategory, String docId) {
        FndDocInfo docInfo = self().getDocHeadInfo(docCategory);
        // 如果当前单据类别的类型字段为空，说明不存在类型
        if (docInfo.getTypeFieldName() == null) {
            return null;
        }
        HashMap docData = self().getHeadDocData(docCategory, docId);
        Object docTypeIdData = docData.get(docInfo.getTypeFieldName()) == null ? docData.get(docInfo.getTypeFieldName().toLowerCase()) : docData.get(docInfo.getTypeFieldName());
        if (docTypeIdData != null) {
            Long docTypeId = Long.parseLong(docTypeIdData.toString());
            return docTypeId;
        } else {
            return null;
        }
    }

    /**
     * 获取对应单据类别和单据ID的单据编码
     *
     * @param docCategory 单据类别
     * @param docId 单据ID
     * @return 单据编码
     */
    @Override
    public String getDocNumber(String docCategory, String docId) {
        FndDocInfo docInfo = self().getDocHeadInfo(docCategory);
        // 如果当前单据编码的类型字段为空，说明不存在类型
        if (docInfo.getNumberFieldName() == null) {
            return null;
        }
        HashMap docData = self().getHeadDocData(docCategory, docId);
        String docNumber = (String) (docData.get(docInfo.getNumberFieldName()) == null ? docData.get(docInfo.getNumberFieldName().toLowerCase()) : docData.get(docInfo.getNumberFieldName()));
        return docNumber;
    }

    public FndDocInfo getDocInfoWithAmount(String docCategory, String tableLevel) {
        FndDocInfo docInfo = new FndDocInfo();
        docInfo.setDocCategory(docCategory);
        docInfo.setTableLevelCode(tableLevel);
        List<FndDocInfo> docInfoList = fndDocEngineMapper.getDocInfo(docInfo);
        if (docInfoList.size() == 0) {
            return null;
        }

        for (FndDocInfo currentDocInfo : docInfoList) {
            if (currentDocInfo.getAmountFieldName() != null) {
                return currentDocInfo;
            }
        }

        if (TABLE_LEVEL_HEAD.equals(tableLevel)) {
            return getDocInfoWithAmount(docCategory, TABLE_LEVEL_LINE);
        } else if (TABLE_LEVEL_LINE.equals(tableLevel)) {
            return getDocInfoWithAmount(docCategory, TABLE_LEVEL_DIST);
        } else {
            return null;
        }
    }

    @Override
    public BigDecimal getDocAmount(String docCategory, String docId) {
        FndDocInfo docInfo = getDocInfoWithAmount(docCategory, TABLE_LEVEL_HEAD);

        if (docInfo == null) {
            return null;
        } else {
            BigDecimal docAmount = new BigDecimal(0);
            String amountFieldName = docInfo.getAmountFieldName();
            List<HashMap> resultList = fndDocEngineMapper.getDocData(docInfo, Long.parseLong(docId));
            for (HashMap result : resultList) {
                if (result.get(amountFieldName) != null || result.get(amountFieldName.toLowerCase()) != null) {
                    docAmount = docAmount.add((BigDecimal) (result.get(amountFieldName) == null ? result.get(amountFieldName.toLowerCase()) : result.get(amountFieldName)));
                }
            }
            return docAmount;
        }
    }
}
