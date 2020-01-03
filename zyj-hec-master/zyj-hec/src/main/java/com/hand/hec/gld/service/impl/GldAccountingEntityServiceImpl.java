package com.hand.hec.gld.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.mapper.GldAccountingEntityMapper;
import com.hand.hec.gld.service.IGldAccountingEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * * 核算主体定义
 * </p>
 *
 * @author ngls.luhui 2019/01/08 13:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GldAccountingEntityServiceImpl extends BaseServiceImpl<GldAccountingEntity>
                implements IGldAccountingEntityService {

    @Autowired
    private GldAccountingEntityMapper mapper;

    @Autowired
    private ISysParameterService parameterService;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public List<GldAccountingEntity> selectMoreAccEntity(GldAccountingEntity accountingEntity, IRequest request,
                    Integer page, Integer pageSize) {

        if (page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        return mapper.selectMore(accountingEntity);
    }

    @Override
    public GldAccountingEntity queryDefaultAccEntity(IRequest request, Long companyId) {
        return mapper.queryDefaultAccEntity(companyId);
    }

    @Override
    public List<GldAccountingEntity> getAccEntityByVenderTypeRefAe(IRequest request,

                    GldAccountingEntity accountingEntity, Long venderTypeId, Long magOrgId, int pageNum, int pageSize) {

        return mapper.getAccEntityByVenderTypeRefAe(accountingEntity, venderTypeId, magOrgId);
    }

    @Override
    public List<GldAccountingEntity> getAccEntityByVenderTypeRefAeMore(IRequest request,

                    GldAccountingEntity accountingEntity, Long magOrgId, int pageNum, int pageSize) {


        return mapper.getAccEntityByVenderTypeRefAeMore(accountingEntity, magOrgId);
    }


    @Override
    public List<GldAccountingEntity> queryByTypeId(IRequest request, Long typeId) {
        return this.mapper.queryAccEntityByTypeId(typeId);
    }

    @Override
    public List<GldAccountingEntity> selectAccEntityName(IRequest iRequest) {
        return mapper.selectAccEntityName();
    }

    @Override
    public List<GldAccountingEntity> queryAccEntityByCompany(IRequest request, Long companyId) {
        return mapper.queryAccEntityByCompany(companyId);
    }

    @Override
    public List<GldAccountingEntity> ordSystemCustomerQuery(IRequest request, Long customerId, Long accountId,

                    Long taxId, Long pCustomerId) {

        return mapper.ordSystemCustomerQuery(customerId, accountId, taxId, pCustomerId);

    }

    @Override
    public Boolean checkInTime(Date date, Long accEntityId) {
        Date startDateActive = mapper.select(GldAccountingEntity.builder().accEntityId(accEntityId).build()).get(0)
                        .getStartDateActive();
        Date endDateActive = mapper.select(GldAccountingEntity.builder().accEntityId(accEntityId).build()).get(0)
                        .getEndDateActive();
        if (date.after(startDateActive) && date.before(endDateActive)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<GldAccountingEntity> queryByBgtOrg(IRequest request, Long bgtOrgId) {
        return mapper.queryByBgtOrg(bgtOrgId);
    }

    @Override
    public List<GldAccountingEntity> taxTypeQuery(IRequest request, Long taxTypeId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return mapper.taxTypeQuery(taxTypeId);
    }

    @Override
    public List<Map<String, Object>> queryByMagOrg(IRequest request, Long companyId, Long magOrgId) {
        return mapper.queryByMagOrg(companyId, magOrgId);
    }

    @Override
    public List<Map> queryForDftAccEntity(IRequest request) {
        return mapper.queryForDftAccEntity();
    }

    @Override
    public List<Map> queryByCompanyId(Long companyId, IRequest request) {
        return mapper.queryByCompanyId(companyId, "");
    }


    /**
     * <p>
     * 获取符合要求的付款核算主体(对应原hec_util_pkg.get_pay_acc_entity_id)
     * <p/>
     *
     * @param magOrgId 管理组织ID
     * @param companyId 管理公司ID
     * @param documentCategory 单据类别
     * @param documentTypeId 单据类型ID
     * @param payeeCategory 收款方类型
     * @param paymentMethodId 收款方式ID
     * @return 符合条件的核算主体ID
     * @author yang.duan 2019/3/13 19:22
     */
    @Override
    public Long getPayAccEntityId(Long magOrgId, Long companyId, String documentCategory, Long documentTypeId,
                    Long paymentMethodId, String payeeCategory) {
        return mapper.getPayAccEntityId(magOrgId, companyId, documentCategory, documentTypeId, paymentMethodId,
                        payeeCategory);
    }

    @Override
    public List<GldAccountingEntity> queryAccEntityBySessionCompanyId(GldAccountingEntity dto, IRequest request,
                    int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryAccEntityBySessionCompanyId(dto);
    }

}
