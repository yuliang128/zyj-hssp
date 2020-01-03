package com.hand.hec.exp.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoExpObjTpAsgnCom;
import com.hand.hec.exp.dto.ExpMoExpenseObjectType;
import com.hand.hec.exp.mapper.ExpMoExpObjTpAsgnComMapper;
import com.hand.hec.exp.service.IExpMoExpObjTpAsgnComService;
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
 * @date 2019/2/26 18:30
 * Description:费用对象分配公司ServiceImpl
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoExpObjTpAsgnComServiceImpl extends BaseServiceImpl<ExpMoExpObjTpAsgnCom> implements IExpMoExpObjTpAsgnComService {
    @Autowired
    ExpMoExpObjTpAsgnComMapper asgnComMapper;

    @Override
    public List<ExpMoExpObjTpAsgnCom> queryFndCompanyTypeId(Long magOrgId, Long moExpObjTypeId, int page, int pageSize, IRequest request) {
        PageHelper.startPage(page, pageSize);
        return asgnComMapper.queryFndCompanyTypeId(magOrgId, moExpObjTypeId);
    }

    @Override
    public List<ExpMoExpObjTpAsgnCom> batchAssignCompany(JSONObject jsonObject, IRequest request) {
        List<ExpMoExpObjTpAsgnCom> list = new ArrayList<>();
        List<ExpMoExpenseObjectType> objectTypeList = new ArrayList<>();
        List<FndCompany> fndCompanyList = new ArrayList<>();
        JSONArray typeJsonArray = jsonObject.getJSONArray("objRecords");
        for (int i = 0; i < typeJsonArray.size(); i++) {
            ExpMoExpenseObjectType aExpMoExpenseObjectType = (ExpMoExpenseObjectType) JSONObject.toBean(JSONObject.fromObject(typeJsonArray.get(i)), ExpMoExpenseObjectType.class);
            objectTypeList.add(aExpMoExpenseObjectType);
        }
        JSONArray comJsonArray = jsonObject.getJSONArray("assignComRecords");
        for (int i = 0; i < comJsonArray.size(); i++) {
            FndCompany aFndCompany = (FndCompany) JSONObject.toBean(JSONObject.fromObject(comJsonArray.get(i)), FndCompany.class);
            fndCompanyList.add(aFndCompany);
        }
        for (ExpMoExpenseObjectType expMoExpenseObjectType : objectTypeList) {
            for (FndCompany fndCompany : fndCompanyList) {
                ExpMoExpObjTpAsgnCom expMoExpObjTpAsgnCom = new ExpMoExpObjTpAsgnCom();
                expMoExpObjTpAsgnCom.setMoExpObjTypeId(expMoExpenseObjectType.getMoExpObjTypeId());
                expMoExpObjTpAsgnCom.setCompanyId(fndCompany.getCompanyId());
                int count = mapper.selectCount(expMoExpObjTpAsgnCom);
                expMoExpObjTpAsgnCom.setEnabledFlag("Y");
                if (count == 1) {
                    Criteria criteria = new Criteria(expMoExpObjTpAsgnCom);
                    criteria.where(new WhereField(ExpMoExpObjTpAsgnCom.FIELD_MO_EXP_OBJ_TYPE_ID, Comparison.EQUAL), new WhereField(ExpMoExpObjTpAsgnCom.FIELD_COMPANY_ID, Comparison.EQUAL));
                    List<ExpMoExpObjTpAsgnCom> expMoExpObjTpAsgnComList = mapper.selectOptions(expMoExpObjTpAsgnCom, criteria);
                    if (!expMoExpObjTpAsgnComList.isEmpty()) {
                        ExpMoExpObjTpAsgnCom aExpMoExpObjTpAsgnCom = expMoExpObjTpAsgnComList.get(0);
                        expMoExpObjTpAsgnCom.setAssignId(aExpMoExpObjTpAsgnCom.getAssignId());
                        expMoExpObjTpAsgnCom = self().updateByPrimaryKey(request, expMoExpObjTpAsgnCom);
                    }
                } else {
                    expMoExpObjTpAsgnCom = self().insert(request, expMoExpObjTpAsgnCom);
                }
                list.add(expMoExpObjTpAsgnCom);
            }
        }
        return list;
    }
}