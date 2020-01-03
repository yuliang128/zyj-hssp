package com.hand.hec.exp.service.impl;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.BeanUtil;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoObjectValue;
import com.hand.hec.exp.dto.ExpMoRepEleRefLnObj;
import com.hand.hec.exp.dto.ExpMoRepTypeRefHdObj;
import com.hand.hec.exp.exception.ExpReportTypeException;
import com.hand.hec.exp.mapper.ExpMoRepTypeRefHdObjMapper;
import com.hand.hec.exp.mapper.ExpMoReqTypeRefHdObjMapper;
import com.hand.hec.exp.service.IExpMoRepEleRefLnObjService;
import com.hand.hec.exp.service.IExpMoRepTypeRefHdObjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * ExpMoRepTypeRefHdObjServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 14:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoRepTypeRefHdObjServiceImpl extends BaseServiceImpl<ExpMoRepTypeRefHdObj> implements IExpMoRepTypeRefHdObjService {
    @Autowired
    ExpMoRepTypeRefHdObjMapper mapper;

    @Autowired
    ExpMoReqTypeRefHdObjMapper moReqTypeRefHdObjMapper;

    @Autowired
    IExpMoRepEleRefLnObjService lnObjService;


    /**
     * <p>
     * 查询报销单类型定义头费用对象
     * <p/>
     *
     * @param IRequest             request
     * @param Criteria             criteria
     * @param ExpMoRepTypeRefHdObj dto
     * @param int                  page
     * @param int                  pageSize
     * @return List<ExpMoRepTypeRefHdObj>
     * @author yang.duan 2019/3/1 14:51
     */
    @Override
    public List<ExpMoRepTypeRefHdObj> queryAllInfomation(IRequest request, ExpMoRepTypeRefHdObj dto, Criteria criteria, int page, int pageSize) {
        List<ExpMoRepTypeRefHdObj> oldRepTypeRefHdObjs = new ArrayList<ExpMoRepTypeRefHdObj>();
        List<ExpMoRepTypeRefHdObj> expMoRepTypeRefHdObjs = new ArrayList<ExpMoRepTypeRefHdObj>();
        oldRepTypeRefHdObjs = this.selectOptions(request, dto, criteria, page, pageSize);
        if (oldRepTypeRefHdObjs != null && !oldRepTypeRefHdObjs.isEmpty()) {
            for (ExpMoRepTypeRefHdObj hdObj : oldRepTypeRefHdObjs) {
                ExpMoObjectValue objectValue = new ExpMoObjectValue();
                if (("VALUE_LIST").equals(hdObj.getExpenseObjectMethod())) {
                    objectValue = moReqTypeRefHdObjMapper.queryObjectValue(hdObj.getMoExpObjTypeId(), hdObj.getDefaultMoObjectId());
                }
                if (("SQL_TEXT").equals(hdObj.getExpenseObjectMethod())) {
                    objectValue = moReqTypeRefHdObjMapper.queryObjectSearch(hdObj.getSqlQuery(), hdObj.getDefaultMoObjectId());
                }
                if (objectValue != null) {
                    hdObj.setDefaultMoObjectCode(objectValue.getCode());
                    hdObj.setDefaultMoObjectName(objectValue.getName());
                }
                expMoRepTypeRefHdObjs.add(hdObj);
            }
        }
        return expMoRepTypeRefHdObjs;
    }

    @Override
    public Map getHeaderObjInfo(IRequest request, ExpMoRepTypeRefHdObj dto, Criteria criteria) {
        List<ExpMoRepTypeRefHdObj> oldRepTypeRefHdObjs = new ArrayList<ExpMoRepTypeRefHdObj>();
        List<Map> fieldMap = new ArrayList<>();
        Map valueMap = new HashMap();
        Map objMap = new HashMap();
        oldRepTypeRefHdObjs = this.selectOptions(request, dto, criteria);
        if (oldRepTypeRefHdObjs != null && !oldRepTypeRefHdObjs.isEmpty()) {
            for (ExpMoRepTypeRefHdObj hdObj : oldRepTypeRefHdObjs) {
                if (BaseConstants.YES.equals(hdObj.getEnabledFlag())) {
                    ExpMoObjectValue objectValue = new ExpMoObjectValue();
                    if (("VALUE_LIST").equals(hdObj.getExpenseObjectMethod())) {
                        objectValue = moReqTypeRefHdObjMapper.queryObjectValue(hdObj.getMoExpObjTypeId(), hdObj.getDefaultMoObjectId());
                    }
                    if (("SQL_TEXT").equals(hdObj.getExpenseObjectMethod())) {
                        objectValue = moReqTypeRefHdObjMapper.queryObjectSearch(hdObj.getSqlQuery(), hdObj.getDefaultMoObjectId());
                    }
                    if (objectValue != null) {
                        hdObj.setDefaultMoObjectCode(objectValue.getCode());
                        hdObj.setDefaultMoObjectName(objectValue.getName());
                    }
                    hdObj.setReturnField("#" + hdObj.getMoExpObjTypeId());
                    hdObj.setDisplayField("^#" + hdObj.getMoExpObjTypeId());
                    if (BaseConstants.YES.equals(hdObj.getRequiredFlag())) {
                        hdObj.setRequiredFlag("true");
                    } else {
                        hdObj.setRequiredFlag("false");
                    }
                    fieldMap.add(BeanUtil.convert2Map(hdObj));
                    valueMap.put(hdObj.getReturnField(), hdObj.getDefaultMoObjectId());
                    valueMap.put(hdObj.getDisplayField(), hdObj.getDefaultMoObjectName());
                }
            }
        }
        objMap.put("fieldMap",fieldMap);
        objMap.put("valueMap",valueMap);
        return objMap;
    }

    /**
     * <p>
     * 校验头费用对象是否存在并启用
     * <p/>
     *
     * @param ExpMoRepEleRefLnObj dto
     * @return int
     * @author yang.duan 2019/2/27 18:49
     */
    @Override
    public int queryExpTypeHdObj(ExpMoRepEleRefLnObj dto) {
        return mapper.queryExpTypeHdObj(dto);
    }


    /**
     * <p>
     * 报销单类型定义-页面元素-头费用对象批量提交
     * <p/>
     *
     * @param List<ExpMoRepTypeRefHdObj> typeRefHdObjs
     * @param IRequest                   request
     * @return List<ExpMoRepTypeRefHdObj>
     * @throws RuntimeException exception description
     * @author yang.duan 2019/2/27 18:30
     */
    @Override
    public List<ExpMoRepTypeRefHdObj> batchSubmit(IRequest request, List<ExpMoRepTypeRefHdObj> typeRefHdObjs) throws RuntimeException {
        Set<String> codeSet = new HashSet<String>();
        Set<Long> layoutSet = new HashSet<Long>();
        int checkFlag = 0;
        if (!typeRefHdObjs.isEmpty()) {
            for (ExpMoRepTypeRefHdObj hdObj : typeRefHdObjs) {
                codeSet.add(hdObj.getMoExpObjTypeCode());
                layoutSet.add(hdObj.getLayoutPriority());
            }
        }
        checkFlag = checkObjAndLay(typeRefHdObjs);
        if (checkLnObj(typeRefHdObjs)) {
            throw new ExpReportTypeException(ExpReportTypeException.EXP_REP_TYPE_LINE_OBJECT_EXIST_ERROR, null);
        }
        if (checkFlag == 1 || codeSet.size() != typeRefHdObjs.size()) {
            throw new ExpReportTypeException(ExpReportTypeException.EXP_REP_TYPE_OBJECT_DUP_ERROR, null);
        }
        if (checkFlag == 2 || layoutSet.size() != typeRefHdObjs.size()) {
            throw new ExpReportTypeException(ExpReportTypeException.EXP_REP_TYPE_OBJECT_LAY_ERROR, null);
        }
        return this.batchUpdate(request, typeRefHdObjs);
    }

    private int checkObjAndLay(List<ExpMoRepTypeRefHdObj> objList) {
        int flag_count = 0;
        int obj_count = 0;
        int lay_count = 0;
        if (!objList.isEmpty()) {
            for (ExpMoRepTypeRefHdObj hd : objList) {
                if (hd.get__status().equals(DTOStatus.DELETE)) {
                    break;
                }
                ExpMoRepTypeRefHdObj obj_dto = new ExpMoRepTypeRefHdObj();
                ExpMoRepTypeRefHdObj lay_dto = new ExpMoRepTypeRefHdObj();

                obj_dto.setMoExpObjTypeId(hd.getMoExpObjTypeId());
                obj_dto.setMoExpReportTypeId(hd.getMoExpReportTypeId());

                lay_dto.setLayoutPriority(hd.getLayoutPriority());
                lay_dto.setMoExpReportTypeId(hd.getLayoutPriority());

                obj_count = mapper.selectCount(obj_dto);
                lay_count = mapper.selectCount(lay_dto);
                if (hd.get__status().equals(DTOStatus.ADD)) {
                    if (obj_count > 0) {
                        flag_count = 1;
                        break;
                    }
                    if (lay_count > 0) {
                        flag_count = 2;
                        break;
                    }
                }
                if (hd.get__status().equals(DTOStatus.UPDATE)) {
                    ExpMoRepTypeRefHdObj oldValue = mapper.selectByPrimaryKey(hd.getRefId());
                    if (oldValue.getLayoutPriority().longValue() != hd.getLayoutPriority().longValue()) {
                        if (lay_count > 0) {
                            flag_count = 2;
                            break;
                        }
                    }
                }
            }
        }
        return flag_count;
    }

    private boolean checkLnObj(List<ExpMoRepTypeRefHdObj> typeRefHdObjList) {
        boolean ln_obj_flag = false;
        int ld_obj_count = 0;
        if (!typeRefHdObjList.isEmpty()) {
            for (ExpMoRepTypeRefHdObj hd : typeRefHdObjList) {
                ld_obj_count = lnObjService.queryRepEleRefLnObj(hd);
                if (ld_obj_count > 0) {
                    ln_obj_flag = true;
                    break;
                }
            }
        }
        return ln_obj_flag;
    }


}
