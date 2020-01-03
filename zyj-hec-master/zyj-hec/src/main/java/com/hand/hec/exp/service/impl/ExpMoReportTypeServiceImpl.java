package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoReportType;
import com.hand.hec.exp.exception.ExpReportTypeException;
import com.hand.hec.exp.mapper.ExpMoReportTypeMapper;
import com.hand.hec.exp.service.IExpMoReportTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * ExpMoReportTypeServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 14:49
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoReportTypeServiceImpl extends BaseServiceImpl<ExpMoReportType> implements IExpMoReportTypeService {

    @Autowired
    ExpMoReportTypeMapper expMoReportTypeMapper;

    /**
     * <p>
     * 报销单类型定义提交
     * <p/>
     *
     * @param request
     * @param reportTypes
     * @return List<ExpMoReportType>
     * @throws RuntimeException exception 运行异常
     * @author yang.duan 2019/2/20 18:56
     */
    @Override
    public List<ExpMoReportType> batchSubmit(IRequest request, List<ExpMoReportType> reportTypes) throws RuntimeException {
        HashSet<String> codeSet = new HashSet<String>();
        List<ExpMoReportType> typeList = new ArrayList<>();
        for (ExpMoReportType type : reportTypes) {
            codeSet.add(type.getMoExpReportTypeCode());
        }
        if (codeSet.size() != reportTypes.size() || checkUnique(reportTypes)) {
            throw new ExpReportTypeException(ExpReportTypeException.EXP_REPORT_TYPE_DUP_ERROR, null);
        } else {
            for (ExpMoReportType type : reportTypes) {
                if (DTOStatus.ADD.equals(type.get__status())) {
                    type.setCreatedBy(request.getUserId());
                    type.setCreationDate(new Date());
                    type.setLastUpdatedBy(request.getUserId());
                    type.setLastUpdateDate(new Date());
                    this.insertSelective(request, type);
                } else if (DTOStatus.UPDATE.equals(type.get__status())) {
                    type.setLastUpdatedBy(request.getUserId());
                    type.setLastUpdateDate(new Date());
                    this.updateByPrimaryKey(request, type);
                } else {
                    this.deleteByPrimaryKey(type);
                }
            }
        }
        return reportTypes;
    }


    /**
     * <p>
     * 校验唯一性
     * <p/>
     *
     * @param dto
     * @return boolean
     * @author yang.duan 2019/2/20 19:06
     */
    private boolean checkUnique(List<ExpMoReportType> dto) {
        boolean flag = false;
        int count = 0;
        ExpMoReportType reportType = new ExpMoReportType();
        for (ExpMoReportType type : dto) {
            if ((type.get__status().equals(DTOStatus.DELETE)) || (type.get__status().equals(DTOStatus.UPDATE))) {
                break;
            } else {
                reportType.setMagOrgId(type.getMagOrgId());
                reportType.setMoExpReportTypeCode(type.getMoExpReportTypeCode());
                count = mapper.selectCount(reportType);
                if (count > 0) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * <p>
     * 根据员工和公司获取报销单类型(对应原exp_mo_report_type_list.bm)
     * <p/>
     *
     * @param request
     * @param employeeId 员工ID
     * @param companyId  公司ID
     * @return 单据类型list
     * @author yang.duan 2019/3/18 18:17
     */
    @Override
    public List<ExpMoReportType> queryExpMoReportTypeList(IRequest request, Long employeeId, Long companyId, int page, int pageSize) {
        return expMoReportTypeMapper.queryExpMoReportTypeList(employeeId, companyId);
    }

    /**
     * <p>
     * 根据公司获取报销单类型
     * <p/>
     *
     * @param request
     * @param companyId
     * @return 单据类型list
     * @author yang.duan 2019/3/19 19:32
     */
    public List<ExpMoReportType> queryExpReportTypeByCom(IRequest request, Long companyId) {
        return expMoReportTypeMapper.queryExpReportTypeByCom(companyId);
    }
}
