package com.hand.hec.wfl.service.impl;

import com.hand.hec.fnd.dto.FndDocInfo;
import com.hand.hec.fnd.service.IFndDocEngineService;
import com.hand.hec.wfl.dto.WflInstance;
import com.hand.hec.wfl.exception.WflReceiverException;
import com.hand.hec.wfl.service.IWflReceiverUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * description
 *
 * @author mouse 2019/03/06 15:25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflReceiverUtilServiceImpl implements IWflReceiverUtilService {

    @Autowired
    IFndDocEngineService docEngineService;

    @Override
    public FndDocInfo getHeadDocInfo(WflInstance instance) {
        return docEngineService.getDocHeadInfo(instance.getDocCategory());
    }

    @Override
    public FndDocInfo getSumDocInfo(WflInstance instance) {
        return docEngineService.getDocSumInfo(instance.getDocCategory());
    }

    @Override
    public List<FndDocInfo> getLineDocInfo(WflInstance instance) {
        List<FndDocInfo> docInfoList = docEngineService.getDocInfo(instance.getDocCategory());
        List<FndDocInfo> lineDocInfoList = new ArrayList<FndDocInfo>();
        docInfoList.forEach(docInfo -> {
            if (FndDocInfo.TABLE_LEVEL_LINE.equals(docInfo.getTableLevelCode())) {
                lineDocInfoList.add(docInfo);
            }
        });
        return lineDocInfoList;
    }

    @Override
    public List<FndDocInfo> getDistDocInfo(WflInstance instance, FndDocInfo lineDocInfo) {

        List<FndDocInfo> docInfoList = docEngineService.getDocInfo(instance.getDocCategory());
        List<FndDocInfo> distDocInfoList = new ArrayList<FndDocInfo>();
        docInfoList.forEach(docInfo -> {
            // 单据信息的表级别为DIST，且父单据信息ID等于传入的行单据信息ID
            if (FndDocInfo.TABLE_LEVEL_DIST.equals(docInfo.getTableLevelCode())
                            && lineDocInfo.getDocInfoId().equals(docInfo.getParentDocInfoId())) {
                distDocInfoList.add(docInfo);
            }
        });
        return distDocInfoList;
    }

    @Override
    public HashMap getHeadData(WflInstance instance) {
        List<HashMap> docDataList =
                        docEngineService.getDocData(getHeadDocInfo(instance), instance.getDocId().toString());
        if (docDataList != null && docDataList.size() == 1) {
            return docDataList.get(0);
        } else if (docDataList != null && docDataList.size() > 1) {
            throw new WflReceiverException("WFL", "wfl_receiver.doc_head_more_than_one", null);
        } else {
            throw new WflReceiverException("WFL", "wfl_receiver.doc_head_not_exists", null);
        }
    }

    @Override
    public List<HashMap> getSumData(WflInstance instance) {
        List<HashMap> docDataList =
                        docEngineService.getDocData(getSumDocInfo(instance), instance.getDocId().toString());
        if (docDataList != null && docDataList.size() > 0) {
            return docDataList;
        } else {
            throw new WflReceiverException("WFL", "wfl_receiver.doc_sum_not_exists", null);
        }
    }

    @Override
    public List<HashMap> getData(WflInstance instance, FndDocInfo docInfo, String docId) {
        List<HashMap> docDataList = docEngineService.getDocData(docInfo, docId);
        return docDataList;
    }
}
