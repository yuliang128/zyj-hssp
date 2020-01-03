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
import com.hand.hec.exp.mapper.ExpMoRepEleRefLnObjMapper;
import com.hand.hec.exp.mapper.ExpMoReqTypeRefHdObjMapper;
import com.hand.hec.exp.service.IExpMoRepEleRefLnObjService;
import com.hand.hec.exp.service.IExpMoRepTypeRefHdObjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * ExpMoRepEleRefLnObjServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 14:49
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoRepEleRefLnObjServiceImpl extends BaseServiceImpl<ExpMoRepEleRefLnObj>
                implements IExpMoRepEleRefLnObjService {
    @Autowired
    ExpMoRepEleRefLnObjMapper mapper;

    @Autowired
    ExpMoReqTypeRefHdObjMapper reqTypeRefHdObjMapper;

    @Autowired
    IExpMoRepTypeRefHdObjService hdObjService;



    /**
     * <p>
     * 查询报销单类型定义行费用对象
     * <p/>
     *
     * @param  request
     * @param  dto
     * @param  criteria
     * @param  page
     * @param  pageSize
     * @return List<ExpMoRepEleRefLnObj>
     * @author yang.duan 2019/3/1 15:08
     */
    @Override
    public List<ExpMoRepEleRefLnObj> queryAllInfomation(IRequest request, ExpMoRepEleRefLnObj dto, Criteria criteria,
                    int page, int pageSize) {
        List<ExpMoRepEleRefLnObj> oldEleRefLnObjs = new ArrayList<ExpMoRepEleRefLnObj>();
        List<ExpMoRepEleRefLnObj> eleRefLnObjs = new ArrayList<ExpMoRepEleRefLnObj>();
        oldEleRefLnObjs = this.selectOptions(request, dto, criteria, page, pageSize);
        if (oldEleRefLnObjs != null && !oldEleRefLnObjs.isEmpty()) {
            for (ExpMoRepEleRefLnObj lnObj : oldEleRefLnObjs) {
                ExpMoObjectValue objectValue = new ExpMoObjectValue();
                if ("VALUE_LIST".equals(lnObj.getExpenseObjectMethod())) {
                    objectValue = reqTypeRefHdObjMapper.queryObjectValue(lnObj.getMoExpObjTypeId(),
                                    lnObj.getDefaultMoObjectId());
                }
                if (("SQL_TEXT").equals(lnObj.getExpenseObjectMethod())) {
                    objectValue = reqTypeRefHdObjMapper.queryObjectSearch(lnObj.getSqlQuery(),
                                    lnObj.getDefaultMoObjectId());
                }
                if (objectValue != null) {
                    lnObj.setDefaultMoObjectCode(objectValue.getCode());
                    lnObj.setDefaultMoObjectName(objectValue.getName());
                }
                eleRefLnObjs.add(lnObj);
            }
        }
        return eleRefLnObjs;
    }

    /**
     * <p>
     * 校验行费用对象是否存在并启用
     * <p/>
     *
     * @param  dto
     * @return int
     * @author yang.duan 2019/2/27 18:47
     */
    @Override
    public int queryRepEleRefLnObj(ExpMoRepTypeRefHdObj dto) {
        return mapper.queryRepEleRefLnObj(dto);
    }


    /**
     * <p>
     * 报销单类型定义-页面元素-行费用对象批量提交
     * <p/>
     * 
     * @param  request
     * @param  eleRefLnObjs
     * @return List<ExpMoRepEleRefLnObj>
     * @throws RuntimeException exception description
     * @author yang.duan 2019/2/27 18:46
     */
    public List<ExpMoRepEleRefLnObj> batchSubmit(IRequest request, List<ExpMoRepEleRefLnObj> eleRefLnObjs)
                    throws RuntimeException {
        Set<String> codeSet = new HashSet<String>();
        Set<Long> layoutSet = new HashSet<Long>();
        int checkFlag = 0;
        if (!eleRefLnObjs.isEmpty()) {
            for (ExpMoRepEleRefLnObj lnObj : eleRefLnObjs) {
                codeSet.add(lnObj.getMoExpObjTypeCode());
                layoutSet.add(lnObj.getLayoutPriority());
            }
        }
        checkFlag = checkObjAndLay(eleRefLnObjs);
        if (checkHdObj(eleRefLnObjs)) {
            throw new ExpReportTypeException(ExpReportTypeException.EXP_REP_TYPE_HEADER_OBJECT_EXIST_ERROR, null);
        }
        if (checkFlag == 1 || codeSet.size() != eleRefLnObjs.size()) {
            throw new ExpReportTypeException(ExpReportTypeException.EXP_REP_TYPE_OBJECT_DUP_ERROR, null);
        }
        if (checkFlag == 2 || layoutSet.size() != eleRefLnObjs.size()) {
            throw new ExpReportTypeException(ExpReportTypeException.EXP_REP_TYPE_OBJECT_LAY_ERROR, null);
        }
        return this.batchUpdate(request, eleRefLnObjs);
    }

    private int checkObjAndLay(List<ExpMoRepEleRefLnObj> objList) {
        int flag_count = 0;
        int obj_count = 0;
        int lay_count = 0;
        if (!objList.isEmpty()) {
            for (ExpMoRepEleRefLnObj ln : objList) {
                if (ln.get__status().equals(DTOStatus.DELETE)) {
                    break;
                }
                ExpMoRepEleRefLnObj obj_dto = new ExpMoRepEleRefLnObj();
                ExpMoRepEleRefLnObj lay_dto = new ExpMoRepEleRefLnObj();

                obj_dto.setMoExpObjTypeId(ln.getMoExpObjTypeId());
                obj_dto.setRepPageEleRefId(ln.getRepPageEleRefId());

                lay_dto.setLayoutPriority(ln.getLayoutPriority());
                lay_dto.setRepPageEleRefId(ln.getRepPageEleRefId());

                obj_count = mapper.selectCount(obj_dto);
                lay_count = mapper.selectCount(lay_dto);
                if (ln.get__status().equals(DTOStatus.ADD)) {
                    if (obj_count > 0) {
                        flag_count = 1;
                        break;
                    }
                    if (lay_count > 0) {
                        flag_count = 2;
                        break;
                    }
                }
                if (ln.get__status().equals(DTOStatus.UPDATE)) {
                    ExpMoRepEleRefLnObj oldValue = mapper.selectByPrimaryKey(ln.getRefId());
                    if (oldValue.getLayoutPriority().longValue() != ln.getLayoutPriority().longValue()) {
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

    private boolean checkHdObj(List<ExpMoRepEleRefLnObj> lnObjList) {
        boolean hd_obj_flag = false;
        int hd_obj_count = 0;
        if (!lnObjList.isEmpty()) {
            for (ExpMoRepEleRefLnObj l : lnObjList) {
                hd_obj_count = hdObjService.queryExpTypeHdObj(l);
                if (hd_obj_count > 0) {
                    hd_obj_flag = true;
                    break;
                }
            }
        }
        return hd_obj_flag;
    }

    @Override
    public List<Map> queryLnDftObject(IRequest request, Long moExpReportTypeId,
                    String reportPageElementCode) {
        List<ExpMoRepEleRefLnObj> eleRefLnObjs = new ArrayList<ExpMoRepEleRefLnObj>();
        List<Map> objMap = new ArrayList<>();
        eleRefLnObjs = mapper.queryLnDftObject(moExpReportTypeId, reportPageElementCode);
        if (eleRefLnObjs != null && !eleRefLnObjs.isEmpty()) {
            for (ExpMoRepEleRefLnObj lnObj : eleRefLnObjs) {
                ExpMoObjectValue objectValue = new ExpMoObjectValue();
                if ("VALUE_LIST".equals(lnObj.getExpenseObjectMethod())) {
                    objectValue = reqTypeRefHdObjMapper.queryObjectValue(lnObj.getMoExpObjTypeId(),
                                    lnObj.getDefaultMoObjectId());
                }
                if (("SQL_TEXT").equals(lnObj.getExpenseObjectMethod())) {
                    objectValue = reqTypeRefHdObjMapper.queryObjectSearch(lnObj.getSqlQuery(),
                                    lnObj.getDefaultMoObjectId());
                }
                if (objectValue != null) {
                    lnObj.setDefaultMoObjectCode(objectValue.getCode());
                    lnObj.setDefaultMoObjectName(objectValue.getName());
                }
                lnObj.setReturnField("#" + lnObj.getMoExpObjTypeId());
                lnObj.setDisplayField("^#" + lnObj.getMoExpObjTypeId());
                if (BaseConstants.YES.equals(lnObj.getRequiredFlag())) {
                    lnObj.setRequiredFlag("true");
                } else {
                    lnObj.setRequiredFlag("false");
                }
                objMap.add(BeanUtil.convert2Map(lnObj));
            }
        }
        return objMap;
    }
}
