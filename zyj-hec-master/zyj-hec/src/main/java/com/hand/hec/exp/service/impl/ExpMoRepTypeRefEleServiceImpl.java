package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.*;
import com.hand.hec.exp.exception.ExpReportTypeException;
import com.hand.hec.exp.mapper.ExpMoRepTypeRefEleMapper;
import com.hand.hec.exp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * ExpMoRepTypeRefEleServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 14:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoRepTypeRefEleServiceImpl extends BaseServiceImpl<ExpMoRepTypeRefEle> implements IExpMoRepTypeRefEleService {

    @Autowired
    ExpMoRepTypeRefEleMapper expMoRepTypeRefEleMapper;
    @Autowired
    IExpMoRepEleRefExpTpService eleRefExpTpService;

    @Autowired
    IExpMoRepTypeRefHdDimService repTypeRefHdDimService;

    @Autowired
    IExpMoRepTypeRefHdObjService repTypeRefHdObjService;

    @Autowired
    IExpMoRepEleRefLnDimService eleRefLnDimService;

    @Autowired
    IExpMoRepEleRefLnObjService eleRefLnObjService;

    /**
     * <p>
     * 报销单页面元素批量提交
     * <p/>
     *
     * @param IRequest                 request
     * @param List<ExpMoRepTypeRefEle> expMoRepTypeRefEles
     * @return List<ExpMoRepTypeRefEle>
     * @throws RuntimeException exception description
     * @author yang.duan 2019/3/4 13:33
     */
    @Override
    public List<ExpMoRepTypeRefEle> batchSubmit(IRequest request, List<ExpMoRepTypeRefEle> expMoRepTypeRefEles) throws RuntimeException {
        Set<Long> codeSet = new HashSet<Long>();
        int docCount = 0;
        List<String> list = new ArrayList();
        ExpMoRepTypeRefEle oldDto = new ExpMoRepTypeRefEle();
        if (expMoRepTypeRefEles != null && !expMoRepTypeRefEles.isEmpty()) {
            for (ExpMoRepTypeRefEle ele : expMoRepTypeRefEles) {
                oldDto.setMoExpReportTypeId(ele.getMoExpReportTypeId());
                if (DTOStatus.DELETE.equals(ele.get__status())) {
                    // 级联删除报销类型、费用对象、维度
                    deleteTp(request, ele.getRepPageEleRefId());
                    if ("HEADER".equals(ele.getDocTypeCode())) {
                        deleteHdDim(request, ele.getMoExpReportTypeId());
                        deleteHdObj(request, ele.getMoExpReportTypeId());
                    }
                    if ("LINE".equals(ele.getDocTypeCode())) {
                        deleteLnDim(request, ele.getRepPageEleRefId());
                        deleteLnObj(request, ele.getRepPageEleRefId());
                    }
                } else {
                    codeSet.add(ele.getReportPageElementId());
                    list.add(ele.getDocTypeCode());
                }
            }
            if (!codeSet.isEmpty() && codeSet.size() != expMoRepTypeRefEles.size()) {
                throw new ExpReportTypeException(ExpReportTypeException.EXP_REP_TYPE_ELE_DUP_ERROR, null);
            }
            if (list != null && !list.isEmpty()) {
                for (String docType : list) {
                    if ("HEADER".equals(docType)) {
                        docCount++;
                    }
                }
            }
            List<ExpMoRepTypeRefEle> oldValueList = expMoRepTypeRefEleMapper.select(oldDto);
            if (oldValueList != null && !oldValueList.isEmpty()) {
                for (ExpMoRepTypeRefEle ele : oldValueList) {
                    if ("HEADER".equals(ele.getDocTypeCode())) {
                        docCount++;
                    }
                }
            }
            if (docCount > 1) {
                throw new ExpReportTypeException(ExpReportTypeException.EXP_MO_REP_TYPE_REF_HEADER_DUPLICATE_ERROR, null);
            }
        }
        return this.batchUpdate(request, expMoRepTypeRefEles);
    }

    // 删除报销类型
    private List<ExpMoRepEleRefExpTp> deleteTp(IRequest request, Long repPageEleRefId) {
        ExpMoRepEleRefExpTp dto = new ExpMoRepEleRefExpTp();
        List<ExpMoRepEleRefExpTp> eleRefExpTps = new ArrayList<>();
        dto.setRepPageEleRefId(repPageEleRefId);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoRepEleRefExpTp.FIELD_REP_PAGE_ELE_REF_ID, Comparison.EQUAL));
        eleRefExpTps = eleRefExpTpService.selectOptions(request, dto, criteria);
        if (!eleRefExpTps.isEmpty()) {
            for (ExpMoRepEleRefExpTp tp : eleRefExpTps) {
                tp.set__status(DTOStatus.DELETE);
            }
            eleRefExpTpService.batchSubmit(request, eleRefExpTps);
        }
        return eleRefExpTps;
    }

    // 删除头维度
    private List<ExpMoRepTypeRefHdDim> deleteHdDim(IRequest request, Long expReportTypeId) {
        ExpMoRepTypeRefHdDim dto = new ExpMoRepTypeRefHdDim();
        dto.setMoExpReportTypeId(expReportTypeId);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoRepTypeRefHdDim.FIELD_MO_EXP_REPORT_TYPE_ID, Comparison.EQUAL));
        List<ExpMoRepTypeRefHdDim> typeRefHdDims = new ArrayList<>();
        typeRefHdDims = repTypeRefHdDimService.selectOptions(request, dto, criteria);
        if (!typeRefHdDims.isEmpty()) {
            repTypeRefHdDimService.batchDelete(typeRefHdDims);
        }
        return typeRefHdDims;
    }


    // 删除行维度
    private List<ExpMoRepEleRefLnDim> deleteLnDim(IRequest request, Long repPageEleRefId) {
        ExpMoRepEleRefLnDim dto = new ExpMoRepEleRefLnDim();
        dto.setRepPageEleRefId(repPageEleRefId);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoRepEleRefLnDim.FIELD_REP_PAGE_ELE_REF_ID, Comparison.EQUAL));
        List<ExpMoRepEleRefLnDim> eleRefLnDims = new ArrayList<>();
        eleRefLnDims = eleRefLnDimService.selectOptions(request, dto, criteria);
        if (!eleRefLnDims.isEmpty()) {
            eleRefLnDimService.batchDelete(eleRefLnDims);
        }
        return eleRefLnDims;
    }


    // 删除头费用对象
    private List<ExpMoRepTypeRefHdObj> deleteHdObj(IRequest request, Long moExpReportTypeId) {
        List<ExpMoRepTypeRefHdObj> repTypeRefHdObjs = new ArrayList<>();
        ExpMoRepTypeRefHdObj dto = new ExpMoRepTypeRefHdObj();
        dto.setMoExpReportTypeId(moExpReportTypeId);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoRepTypeRefHdObj.FIELD_MO_EXP_REPORT_TYPE_ID, Comparison.EQUAL));
        repTypeRefHdObjs = repTypeRefHdObjService.selectOptions(request, dto, criteria);
        if (!repTypeRefHdObjs.isEmpty()) {
            repTypeRefHdObjService.batchDelete(repTypeRefHdObjs);
        }
        return repTypeRefHdObjs;
    }

    // 删除行费用对象
    private List<ExpMoRepEleRefLnObj> deleteLnObj(IRequest request, Long repPageEleRefId) {
        List<ExpMoRepEleRefLnObj> eleRefLnObjs = new ArrayList<>();
        ExpMoRepEleRefLnObj dto = new ExpMoRepEleRefLnObj();
        dto.setRepPageEleRefId(repPageEleRefId);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoRepEleRefLnObj.FIELD_REP_PAGE_ELE_REF_ID, Comparison.EQUAL));
        eleRefLnObjs = eleRefLnObjService.selectOptions(request, dto, criteria);
        if (!eleRefLnObjs.isEmpty()) {
            eleRefLnObjService.batchDelete(eleRefLnObjs);
        }
        return eleRefLnObjs;
    }

    /**
     * <p>
     * 查询报销单行页面元素
     * <p/>
     *
     * @param expMoReportTypeId 报销单类型ID
     * @param expReportHeaderId 报销单头ID
     * @return 行页面元素list
     * @author yang.duan 2019/3/26 16:58
     */
    @Override
    public List<ExpMoRepTypeRefEle> queryExpReportLineEle(Long expMoReportTypeId, Long expReportHeaderId) {
        return expMoRepTypeRefEleMapper.queryExpReportLineEle(expMoReportTypeId, expReportHeaderId);
    }


    @Override
    public List<ExpMoRepTypeRefEle> queryInvoiceAndTaxFlag(Long moExpReportTypeId, String reportPageElementCode) {
        return expMoRepTypeRefEleMapper.queryInvoiceAndTaxFlag(moExpReportTypeId, reportPageElementCode);
    }
}
