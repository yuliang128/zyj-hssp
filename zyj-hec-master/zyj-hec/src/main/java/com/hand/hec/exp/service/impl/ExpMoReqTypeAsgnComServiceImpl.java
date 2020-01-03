package com.hand.hec.exp.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoReqType;
import com.hand.hec.exp.dto.ExpMoReqTypeAsgnCom;
import com.hand.hec.exp.mapper.ExpMoReqTypeAsgnComMapper;
import com.hand.hec.exp.service.IExpMoReqTypeAsgnComService;
import com.hand.hap.fnd.dto.FndCompany;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/2/21 13:45
 * Description:申请单类型分配公司ServiceImpl
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoReqTypeAsgnComServiceImpl extends BaseServiceImpl<ExpMoReqTypeAsgnCom> implements IExpMoReqTypeAsgnComService {
    @Autowired
    ExpMoReqTypeAsgnComMapper asgnComMapper;

    @Override
    public List<ExpMoReqTypeAsgnCom> queryFndCompanyTypeId(IRequest request, Long magOrgId, Long moExpReqTypeId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return asgnComMapper.queryFndCompanyTypeId(magOrgId, moExpReqTypeId);
    }

    @Override
    public List<ExpMoReqTypeAsgnCom> batchAssignCompany(JSONObject jsonObject, IRequest request) {
        List<ExpMoReqTypeAsgnCom> list = new ArrayList<>();
        List<ExpMoReqType> reqTypeList = new ArrayList<>();
        List<FndCompany> fndCompanyList = new ArrayList<>();
        JSONArray typeJsonArray = jsonObject.getJSONArray("typeRecords");
        for (int i = 0; i < typeJsonArray.size(); i++) {
            ExpMoReqType aExpMoReqType = (ExpMoReqType) JSONObject.toBean(JSONObject.fromObject(typeJsonArray.get(i)), ExpMoReqType.class);
            reqTypeList.add(aExpMoReqType);
        }
        JSONArray comJsonArray = jsonObject.getJSONArray("assignComRecords");
        for (int i = 0; i < comJsonArray.size(); i++) {
            FndCompany aFndCompany = (FndCompany) JSONObject.toBean(JSONObject.fromObject(comJsonArray.get(i)), FndCompany.class);
            fndCompanyList.add(aFndCompany);
        }
        for (ExpMoReqType expMoReqType : reqTypeList) {
            for (FndCompany fndCompany : fndCompanyList) {
                ExpMoReqTypeAsgnCom expMoReqTypeAsgnCom = new ExpMoReqTypeAsgnCom();
                expMoReqTypeAsgnCom.setMoExpReqTypeId(expMoReqType.getMoExpReqTypeId());
                expMoReqTypeAsgnCom.setCompanyId(fndCompany.getCompanyId());
                int count = mapper.selectCount(expMoReqTypeAsgnCom);
                expMoReqTypeAsgnCom.setEnabledFlag("Y");
                if (count == 1) {
                    Criteria criteria = new Criteria(expMoReqTypeAsgnCom);
                    criteria.where(new WhereField(ExpMoReqTypeAsgnCom.FIELD_MO_EXP_REQ_TYPE_ID, Comparison.EQUAL), new WhereField(ExpMoReqTypeAsgnCom.FIELD_COMPANY_ID, Comparison.EQUAL));
                    List<ExpMoReqTypeAsgnCom> expMoReqTypeAsgnComList = mapper.selectOptions(expMoReqTypeAsgnCom, criteria);
                    if (!expMoReqTypeAsgnComList.isEmpty()) {
                        ExpMoReqTypeAsgnCom expMoReqTypeAsgn = expMoReqTypeAsgnComList.get(0);
                        expMoReqTypeAsgnCom.setAssignId(expMoReqTypeAsgn.getAssignId());
                        expMoReqTypeAsgnCom = self().updateByPrimaryKey(request, expMoReqTypeAsgnCom);
                    }
                } else {
                    expMoReqTypeAsgnCom = self().insert(request, expMoReqTypeAsgnCom);
                }
                list.add(expMoReqTypeAsgnCom);
            }
        }
        return list;
    }
}