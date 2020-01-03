package com.hand.hec.vat.service.impl;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hand.hec.base.service.HecUtil;
import com.hand.hec.gld.dto.GldAccEntityTax;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.service.IGldAccEntityTaxService;
import com.hand.hec.gld.service.IGldAccountingEntityService;
import com.hand.hec.vat.dto.VatInvoice;
import com.hand.hec.vat.mapper.VatInvoiceRelationMapper;
import com.hand.hec.vat.service.IVatInvoiceService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class VatInvoiceServiceImpl extends BaseServiceImpl<VatInvoice> implements IVatInvoiceService{
    @Autowired
    private VatInvoiceRelationMapper vatInvoiceRelationMapper;

    @Autowired
    private IGldAccountingEntityService gldAccountingEntityService;

    @Autowired
    private IGldAccEntityTaxService gldAccEntityTaxService;

    /**
     * <p>部分更新增值税发票</p>
     *
     * @param request
     * @param invoice 发票DTO
     * @return VatInvoice
     * @throws RuntimeException exception description
     * @author yang.duan 2019/6/14 15:31
     **/
    @Override
    public VatInvoice updateVatInvoicePart(IRequest request, VatInvoice invoice) throws RuntimeException {
        //1.0 校验状态
        if ("DOC_SUBMITTED".equals(checkInvoiceStatus(request, invoice.getInvoiceId()))) {
            throw new RuntimeException("该发票关联单据已被提交,不允许修改!");
        }
        //更新发票主数据
        invoice = self().updateByPrimaryKey(request, invoice);
        return invoice;
    }


    /**
     * <p>快速生成发票，如果已经存在则直接返回发票ID</p>
     *
     * @param request
     * @param invoice 发票DTO
     * @return VatInvoice
     * @author yang.duan 2019/6/17 10:14
     **/
    @Override
    public VatInvoice generateInvoice(IRequest request, VatInvoice invoice) throws RuntimeException {
        String purchaserName = new String();
        String purchaserTaxNumber = new String();
        String purchaserAddressPhone = new String();
        String purchaserBankAccount = new String();
        //1.0 判断发票是否存在
        //根据发票代码、发票号寻找是否有对应的发票，并对金额和开票日期进行校验
        VatInvoice invoiceDto = new VatInvoice();
        invoiceDto.setInvoiceCode(invoice.getInvoiceCode());
        invoiceDto.setInvoiceNumber(invoice.getInvoiceNumber());
        List<VatInvoice> invoiceList = self().select(request, invoiceDto, 1, 0);
        if (invoiceList != null && !invoiceList.isEmpty()) {
            invoiceDto = invoiceList.get(0);
            //发票日期与发票金额比对，如果存在差异，则抛出发票信息不匹配的异常
            if (invoiceDto != null) {
                if (invoiceDto.getInvoiceDate() != invoice.getInvoiceDate() || invoice.getAmount().compareTo(invoiceDto.getAmount()) != 0) {
                    throw new RuntimeException("VAT_INVOICES.INFO_NOT_SUIT");
                } else {
                    return invoiceDto;
                }
            }
        }
        // 2.0 获取购买方信息
        GldAccountingEntity accountingEntity = new GldAccountingEntity();
        accountingEntity.setAccEntityId(invoice.getAccEntityId());
        accountingEntity = gldAccountingEntityService.selectByPrimaryKey(request, accountingEntity);
        if (accountingEntity != null) {
            accountingEntity.setAddress(accountingEntity.getAddress() + accountingEntity.getPhone());
            purchaserName = accountingEntity.getAccEntityName();
            purchaserTaxNumber = accountingEntity.getTaxpayerNumber();
            purchaserAddressPhone = accountingEntity.getAddress();
            purchaserBankAccount = accountingEntity.getBankAccount();
            if (accountingEntity.getTaxpayerNumber() == null && accountingEntity.getAddress() == null && accountingEntity.getBankAccount() == null) {
                GldAccEntityTax accEntityTax = new GldAccEntityTax();
                accEntityTax.setAccEntityId(invoice.getAccEntityId());
                accEntityTax.setEnabledFlag("Y");
                List<GldAccEntityTax> accEntityTaxList = gldAccEntityTaxService.select(request, accEntityTax, 1, 0);
                if (accEntityTaxList != null && !accEntityTaxList.isEmpty()) {
                    accEntityTax = accEntityTaxList.get(0);
                }
                purchaserTaxNumber = accEntityTax.getTaxpayerNumber();
                purchaserAddressPhone = accEntityTax.getAddress();
                purchaserBankAccount = accEntityTax.getTaxpayerBank();
            }
        }
        //3.0 获取发票类型
        invoice.setInvoiceType(getInvoiceType(request, invoice.getInvoiceCode()));
        //4.0 录入发票信息
        VatInvoice vatInvoice = new VatInvoice();
        vatInvoice.setEmployeeId(invoice.getEmployeeId());
        vatInvoice.setAccEntityId(invoice.getAccEntityId());
        vatInvoice.setInvoiceCode(invoice.getInvoiceCode());
        vatInvoice.setInvoiceNumber(invoice.getInvoiceNumber());
        vatInvoice.setInvoiceDate(invoice.getInvoiceDate());
        vatInvoice.setPurchaserName(purchaserName);
        vatInvoice.setPurchaserTaxNumber(purchaserTaxNumber);
        vatInvoice.setPurchaserAddressPhone(purchaserAddressPhone);
        vatInvoice.setPurchaserBankAccount(purchaserBankAccount);
        vatInvoice.setAmount(invoice.getAmount());
        vatInvoice.setTaxAmount(invoice.getTaxAmount());
        vatInvoice.setWithoutTaxAmount(invoice.getAmount() - (invoice.getTaxAmount() == null ? Long.valueOf(0) : invoice.getTaxAmount()));
        vatInvoice.setInvoiceType(invoice.getInvoiceType());
        vatInvoice.setInvoiceSource("MANUAL");
        vatInvoice.setAuthenticationStatus("N");
        vatInvoice.setInvoiceStatus("N");
        vatInvoice.setInvoiceCategoryId(invoice.getInvoiceCategoryId());
        vatInvoice.setConfirmFlag("N");
        vatInvoice = self().insertVatInvoice(request,vatInvoice);
        return vatInvoice;
    }


    /**
     * <p>插入增值税发票</p>
     *
     * @param request
     * @param invoice 需要插入的invoiceDto
     * @return VatInvoice
     * @throws RuntimeException exception description
     * @author yang.duan 2019/6/17 13:42
     **/
    @Override
    public VatInvoice insertVatInvoice(IRequest request, VatInvoice invoice) throws RuntimeException {
        //1.0 初始化
        String invoiceType = getInvoiceType(request, invoice.getInvoiceCode());

        //2.0 发票类型判断
        if (invoice.getInvoiceType() != null && !invoice.getInvoiceType().equals(invoiceType)) {
            throw new RuntimeException("发票代码与发票类型不匹配!");
        }
        //3.0 赋值
        VatInvoice vatInvoice = new VatInvoice();
        BeanUtils.copyProperties(invoice, vatInvoice);
        vatInvoice.setAmountZhs(new HecUtil().amountConvertToChinese(vatInvoice.getAmount().toString()));
        vatInvoice.setWithoutTaxAmount(vatInvoice.getWithoutTaxAmount() == null ? vatInvoice.getAmount() - vatInvoice.getTaxAmount() : vatInvoice.getWithoutTaxAmount());
        vatInvoice.setInvoiceType(vatInvoice.getInvoiceType() == null ? invoiceType : vatInvoice.getInvoiceType());
        vatInvoice.setInvoiceSource(vatInvoice.getInvoiceSource() == null ? "MANUAL" : vatInvoice.getInvoiceSource());
        vatInvoice.setAuthenticationStatus(vatInvoice.getAuthenticationStatus() == null ? "N" : vatInvoice.getAuthenticationStatus());
        vatInvoice.setInvoiceStatus(vatInvoice.getInvoiceStatus() == null ? "N" : vatInvoice.getInvoiceStatus());
        vatInvoice.setConfirmFlag(vatInvoice.getConfirmFlag() == null ? "N" : vatInvoice.getConfirmFlag());
        //4.0 保存
        vatInvoice = self().insertSelective(request, vatInvoice);
        return vatInvoice;
    }


    /**
     * <p>检查发票状态</p>
     *
     * @param request
     * @param invoiceId 发票ID
     * @return String
     * @author yang.duan 2019/6/14 15:35
     **/
    public String checkInvoiceStatus(IRequest request, Long invoiceId) {
        //--检查是否有已经提交的报销单关联了此发票
        //--普通员工不允许在发票已经被已提交/已审批的报销单关联后再进行修改
        Long relationCount = vatInvoiceRelationMapper.getRelationCountByInvoiceId(invoiceId);
        if (relationCount != null && relationCount.compareTo(Long.valueOf(0)) > 0) {
            return "DOC_SUBMITTED";
        } else {
            return null;
        }
    }

    /**
     * <p>获取发票类型</p>
     *
     * @param request
     * @param invoiceCode 发票代码
     * @return String
     * @author yang.duan 2019/6/17 10:41
     **/
    private String getInvoiceType(IRequest request, String invoiceCode) {
        String invoiceType = "99";
        String tmpStr;
        if (invoiceCode != null && invoiceCode.length() == 12) {
            tmpStr = invoiceCode.substring(7, 8);
            if ("99".equals(invoiceType)) {
                //增加判断，判断是否为新版电子票
                if ("0".equals(invoiceCode.substring(0, 1)) && "11".equals(invoiceCode.substring(10, 12))) {
                    invoiceType = "10";
                }
                if ("0".equals(invoiceCode.substring(0, 1)) && ("06".equals(invoiceCode.substring(10, 12)) || "07".equals(invoiceCode.substring(10, 12)) || "04".equals(invoiceCode.substring(10, 12)))) {
                    //判断是否为卷式发票  第1位为0且第11-12位为06或07
                    invoiceType = "11";
                }
                if ("0".equals(invoiceCode.substring(0, 1)) && "12".equals(invoiceCode.substring(10, 12))) {
                    //判断是否为类型号为14的增值税电子普通发票
                    invoiceType = "14";
                }
            }
            if ("99".equals(invoiceType)) {
                //如果还是99，且第8位是2，则是机动车发票
                if ("2".equals(tmpStr) && !"0".equals(invoiceCode.substring(0, 1))) {
                    invoiceType = "03";
                }
            }
        } else if (invoiceCode != null && invoiceCode.length() == 10) {
            tmpStr = invoiceCode.substring(7, 8);
            if ("1".equals(tmpStr) || "5".equals(tmpStr)) {
                invoiceType = "01";
            } else if ("6".equals(tmpStr) || "3".equals(tmpStr)) {
                invoiceType = "04";
            } else if ("7".equals(tmpStr) || "2".equals(tmpStr)) {
                invoiceType = "02";
            }
        }
        if ("01".equals(invoiceType)) {
            invoiceType = "VAT_SPECIAL";
        } else if ("04".equals(invoiceType) || "11".equals(invoiceType)) {
            invoiceType = "VAT_NORMAL";
        } else if ("10".equals(invoiceType) || "14".equals(invoiceType)) {
            invoiceType = "VAT_ELECTRONIC_NORMAL";
        } else if ("03".equals(invoiceType) || "02".equals(invoiceType)) {
            invoiceType = "OTHER";
        } else {
            invoiceType = "OTHER";
        }
        return invoiceType;
    }
}