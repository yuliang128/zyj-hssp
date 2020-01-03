<?xml version="1.0" encoding="UTF-8"?>
<a:screen xmlns:c="aurora.application.action" xmlns:p="uncertain.proc"
          xmlns:a="http://www.aurora-framework.org/application" xmlns:s="aurora.plugin.script" trace="true">
    <a:view>
        <a:link id="EXP5110_exp_report_maintain_standard_lines_extend_link"
                url="$[/request/@context_path]/expm/EXP5110/exp_report_maintain_standard_lines_extend.screen"/>
        <a:link id="EXP5110_exp_report_unit_lov_link"
                url="$[/request/@context_path]/expm/EXP5110/exp_report_line_unit_lov.screen"/>
        <!--<a:link id="EXP5110_exp_report_maintain_more_invoice_standard_link" url="$[/request/@context_path]/expm/EXP5110/exp_report_maintain_more_invoice.screen"/>-->
        <!--<a:link id="EXP5110_exp_report_maintain_vat_invoice_more_tax_link" url="$[/request/@context_path]/expm/EXP5110/exp_report_maintain_vat_invoice_more_tax.screen"/>-->
        <!--<a:link id="EXP5110_exp_report_maintain_cancel_report_realation_link" model="db.vat_invoice_relation_pkg.cancel_report_realation" modelaction="update"/>-->
        <script><![CDATA[
            function EXP5110_standardGetNextLineNumber() {
                var nextLineNumber = 10;
                var allPmtRecords = $au('EXP5110_exp_report_standard_line_ds').getAll();
                Ext.each(allPmtRecords, function (allPmtRecord) {
                    if (allPmtRecord.get('lineNumber') >= nextLineNumber) {
                        nextLineNumber = allPmtRecord.get('lineNumber') + 10;
                    }
                }, this);
                return nextLineNumber;
            }

            function EXP5110_standardLineAdd(reqDatas) {
                if (reqDatas && reqDatas.length && reqDatas.length != 0) {
                    var headerRecord = $au('EXP5110_exp_report_header_ds').getAt(0);
                    //从申请单创建
                    Ext.each(reqDatas, function (reqData) {
                        var data = {};
                        for (var fieldName in reqDatas['fields']) {
                            data[fieldName] = reqData[fieldName];
                        }
                        data['reportPageElementCode'] = 'STANDARD';
                        data['expReportHeaderId'] = headerRecord.get('expReportHeaderId');
                        data['lineNumber'] = EXP5110_standardGetNextLineNumber();
                        $au('EXP5110_exp_report_standard_line_ds').create(data);
                        //Modify Tagin/2018.01.22 行新增时重算头金额
                        EXP5110_onHeadAmountChanged();
                    }, this);
                } else {
                    var selectedRecords = $au('EXP5110_exp_report_standard_line_ds').getSelected();
                    if (selectedRecords && selectedRecords.length) {
                        Ext.each(selectedRecords, function (selectedRecord) {
                            var data = {};
                            for (var field in $au('EXP5110_exp_report_standard_line_ds').fields) {
                                data[field] = selectedRecord.get(field);
                            }
                            data['expReportLineId'] = null;
                            data['lineNumber'] = EXP5110_standardGetNextLineNumber();
                            $au('EXP5110_exp_report_standard_line_ds').create(data);
                            // Modify Tagin/2018.01.22 行新增时重算头金额
                            EXP5110_onHeadAmountChanged();
                        });
                    } else {
                        var data = {};
                        var lineDs = $au('EXP5110_exp_report_standard_line_ds');
                        headerRecord = $au('EXP5110_exp_report_header_ds').getAt(0);
                        data['reportPageElementCode'] = 'STANDARD';
                        data['lineNumber'] = EXP5110_standardGetNextLineNumber();
                        data['expReportHeaderId'] = headerRecord.get('expReportHeaderId');
                        data['companyName'] = headerRecord.get('companyName');
                        data['companyId'] = headerRecord.get('companyId');
                        data['accEntityName'] = headerRecord.get('accEntityName');
                        data['accEntityId'] = headerRecord.get('accEntityId');
                        data['unitName'] = headerRecord.get('unitName');
                        data['unitId'] = headerRecord.get('unitId');
                        data['positionName'] = headerRecord.get('positionName');
                        data['positionId'] = headerRecord.get('positionId');
                        data['employeeName'] = headerRecord.get('employeeName');
                        data['employeeId'] = headerRecord.get('employeeId');
                        data['respCenterName'] = headerRecord.get('respCenterName');
                        data['respCenterId'] = headerRecord.get('respCenterId');
                        data['primaryQuantity'] = 1;
                        data['businessCurrencyName'] = headerRecord.get('paymentCurrencyName');
                        data['businessCurrencyCode'] = headerRecord.get('paymentCurrencyCode');
                        data['businessCurrencyPrecision'] = headerRecord.get('paymentCurrencyPrecision');
                        data['biz2payExchangeTypeName'] = headerRecord.get('pay2funExchangeTypeName');
                        data['biz2payExchangeType'] = headerRecord.get('pay2funExchangeType');
                        data['biz2payExchangeRate'] = 1; // Modify Tagin/2018.04.02 默认业务币种等于支付币种所以汇率为1
                        data['paymentCurrencyName'] = headerRecord.get('paymentCurrencyName');
                        data['paymentCurrencyCode'] = headerRecord.get('paymentCurrencyCode');
                        data['paymentCurrencyPrecision'] = headerRecord.get('paymentCurrencyPrecision');
                        data['pay2funExchangeTypeName'] = headerRecord.get('pay2funExchangeTypeName');
                        data['pay2funExchangeType'] = headerRecord.get('pay2funExchangeType');
                        data['pay2funExchangeRate'] = headerRecord.get('pay2funExchangeRate');
                        data['managementCurrencyName'] = headerRecord.get('managementCurrencyName');
                        data['managementCurrencyCode'] = headerRecord.get('managementCurrencyCode');
                        data['managementCurrencyPrecision'] = headerRecord.get('managementCurrencyPrecision');
                        data['pay2magExchangeTypeName'] = headerRecord.get('pay2magExchangeTypeName');
                        data['pay2magExchangeType'] = headerRecord.get('pay2magExchangeType');
                        data['pay2magExchangeRate'] = headerRecord.get('pay2magExchangeRate');
                        data['accEntityId'] = headerRecord.get('accEntityId');
                        data['accEntityName'] = headerRecord.get('accEntityName');
                        data['respCenterId'] = headerRecord.get('respCenterId');
                        data['respCenterName'] = headerRecord.get('respCenterName');
                        data['bgtEntityId'] = headerRecord.get('bgtEntityId');
                        data['bgtEntityName'] = headerRecord.get('bgtEntityName');
                        data['bgtCenterId'] = headerRecord.get('bgtCenterId');
                        data['bgtCenterName'] = headerRecord.get('bgtCenterName');
                        data['periodName'] = headerRecord.get('periodName');
                        data['dateFrom'] = headerRecord.get('reportDate');
                        data['dateTo'] = headerRecord.get('reportDate');
                        for (var i = 1; i <= 20; i++) {
                            data['dimension' + i + 'Id'] = lineDs.fields['dimension' + i + 'Id'] ? lineDs.fields['dimension' + i + 'Id'].pro.defaultvalue : '';
                            data['dimension' + i + 'Name'] = lineDs.fields['dimension' + i + 'Name'] ? lineDs.fields['dimension' + i + 'Name'].pro.defaultvalue : '';
                        }
                        //新增行的时候将默认报销类型自动带出
                        //modified by liliang 2018/11/22
                        //begin
                        var expenseTypeDefaultDs = $au('EXP5110_standard_expense_type_default_ds').getAt(0);
                        data['moExpenseTypeId'] = expenseTypeDefaultDs == undefined ? '' : expenseTypeDefaultDs.get('moExpenseTypeId');
                        data['moExpenseTypeName'] = expenseTypeDefaultDs == undefined ? '' : expenseTypeDefaultDs.get('description');
                        //end
                        $au('EXP5110_exp_report_standard_line_ds').create(data);
                        //Modify Tagin/2018.01.22 行新增时重算头金额
                        EXP5110_onHeadAmountChanged();
                    }
                }
            }

            // Modified by Arthur.Chen Date: 2017 - 09 - 18 15: 15: 12
            // 1.若报销单类型中必须申请字段为Y，那么修改新增按钮为复制按钮。下为复制按钮的function

            function EXP5110_standardLineCopy() {
                var selectedRecords = $au('EXP5110_exp_report_standard_line_ds').getSelected();
                if (selectedRecords && selectedRecords.length) {
                    Ext.each(selectedRecords, function (selectedRecord) {
                        var data = {};
                        for (var field in $au('EXP5110_exp_report_standard_line_ds').fields) {
                            data[field] = selectedRecord.get(field);
                        }
                        data['expReportLineId'] = null;
                        data['lineNumber'] = EXP5110_standardGetNextLineNumber();
                        $au('EXP5110_exp_report_standard_line_ds').create(data);
                    });
                } else {
                    new Aurora.showMessage('$[l:prompt]', '$[l:select_record]', null, 300, 100);
                }
            }

            // Modify Tagin/2018.04.16 调整发票处理的逻辑（整理所有的函数）
            var EXP5110_maintainInvoice = (function (record) {
                // 1.0 增值税发票字段必输/非必输
                this.required = function (value) {
                    if (value == 'VAT_SPECIAL') {
                        record.getField('invoiceCode').setRequired(true);
                        record.getField('invoiceNumber').setRequired(true);
                        record.getField('invoiceDate').setRequired(true);
                        record.getField('invoiceAmount').setRequired(true);
                        record.getField('taxTypeName').setRequired(true);
                        record.getField('taxAmount').setRequired(true); //发票金额
                    } else if (value == 'VAT_NORMAL' || value == 'VAT_ELECTRONIC_NORMAL') {
                        record.getField('invoiceCode').setRequired(true);
                        record.getField('invoiceNumber').setRequired(true);
                        record.getField('invoiceDate').setRequired(true);
                        record.getField('invoiceAmount').setRequired(true);
                    } else if (value == 'OTHER') {
                        //Modifiied by caoke/2018-05-30 其他发票也比输
                        // record.getField('invoiceCode').setRequired(false);
                        // record.getField('invoiceNumber').setRequired(false);
                        record.getField('invoiceCode').setRequired(true);
                        record.getField('invoiceNumber').setRequired(true);
                        record.getField('invoiceDate').setRequired(false);
                        record.getField('invoiceAmount').setRequired(true);
                        record.getField('taxTypeName').setRequired(false);
                        record.getField('taxAmount').setRequired(false); //发票金额
                    } else {
                        record.getField('invoiceCode').setRequired(false);
                        record.getField('invoiceNumber').setRequired(false);
                        record.getField('invoiceDate').setRequired(false);
                        record.getField('invoiceAmount').setRequired(false);
                        record.getField('taxTypeName').setRequired(false);
                        record.getField('taxAmount').setRequired(false); //发票金额
                    }
                };

                // 2.0 增值税发票字段清空
                this.clear = function (value) {
                    if (value == 'VAT_SPECIAL') {
                        //
                    } else if (value == 'VAT_NORMAL' || value == 'VAT_ELECTRONIC_NORMAL') {
                        record.set('taxTypeName', '');
                        record.set('taxTypeId', '');
                        record.set('taxTypeRate', '');
                        record.set('relationFlag', '');
                    } else if (value == 'OTHER') {
                        //record.set('invoiceCode', '');
                        //Modiied by caoke/2018-06-04 其他类型发票，可以有发票税等信息
                        // record.set('invoiceNumber', '');
                        // record.set('invoiceDate', '');
                        // record.set('invoiceAmount', '');
                        // record.set('taxTypeName', '');
                        // record.set('taxTypeId', '');
                        // record.set('taxTypeRate', '');
                        // record.set('taxAmount', '');
                        // record.set('relation_flag', '');
                    } else if (value == '' || value == undefined) {
                        record.set('invoiceId', '');
                        record.set('invoiceLineId', '');
                        // record.set('invoiceCategoryName', '');
                        // record.set('invoiceCategoryId', '');
                        record.set('invoiceCode', '');
                        record.set('invoiceNumber', '');
                        record.set('invoiceDate', '');
                        record.set('invoiceAmount', '');
                        record.set('taxTypeName', '');
                        record.set('taxTypeId', '');
                        record.set('taxTypeRate', '');
                        record.set('taxAmount', '');
                        record.set('invoiceType', '');
                        record.set('invoiceSource', '');
                        record.set('relationFlag', '');
                    }
                };

                // 3.0 增值税发票相关字段显示/隐藏
                this.show = function (value, grid) {
                    if (value == 'Y') {
                        // Modify Tagin/2018.01.22 增加判断 若页面元素中更多发票/更多税额标识存在为Y则启用更多关联链接
                        if ('$[/model/standardLineElement/records/record/@moreInvoiceFlag]' == 'Y' || '$[/model/standardLineElement/records/record/@moreTaxFlag]' == 'Y') {
                            grid.showColumn('invoiceInfo');
                        } else {
                            grid.hideColumn('invoiceInfo');
                        }
                        if ('$[/model/standardLineElement/records/record/@invoiceFlag]' == 'N') {
                            grid.hideColumn('invoiceCategoryName');
                            grid.hideColumn('invoiceCode');
                            grid.hideColumn('invoiceNumber');
                            grid.hideColumn('invoiceDate');
                            grid.hideColumn('invoiceAmount');
                        } else {
                            grid.showColumn('invoiceCategoryName');
                            grid.showColumn('invoiceCode');
                            grid.showColumn('invoiceNumber');
                            grid.showColumn('invoiceDate');
                            grid.showColumn('invoiceAmount');
                        }
                        if ('$[/model/standardLineElement/records/record/@taxFlag]' == 'N') {
                            grid.hideColumn('taxTypeName');
                            grid.hideColumn('taxAmount');
                        } else {
                            grid.showColumn('taxTypeName');
                            grid.showColumn('taxAmount');
                        }
                    } else {
                        grid.hideColumn('invoiceInfo');
                        grid.hideColumn('invoiceCategoryName');
                        grid.hideColumn('invoiceCode');
                        grid.hideColumn('invoiceNumber');
                        grid.hideColumn('invoiceDate');
                        grid.hideColumn('invoiceAmount');
                        grid.hideColumn('taxTypeName');
                        grid.hideColumn('taxAmount');
                    }
                    //add by caoke/2018-05-02 报销单头关联申请单，隐藏行上的关联申请单号
                    if ($au('EXP5110_exp_report_header_ds').getAt(0).get('relationMethodCode') == 'HEAD') {
                        grid.hideColumn('expRequisitionNumber');
                    }
                };

                // 4.0 增值税发票相关字段是否可输入
                this.edit = function (name) {
                    var invoiceRow = !record.get('vatcount') ? 0 : record.get('vatcount');
                    var invoiceSource = record.get('invoiceSource');
                    var invoiceType = record.get('invoiceType');
                    if (name == 'invoiceCategoryName') {
                        // add caoke/2018.05-30 设置增值税相关字段的必输性
                        if (record.get('invoiceSource') == 'MANUAL' && invoiceRow <= 1) {
                            new EXP5110_maintainInvoice(record).required(invoiceType);
                        }
                        //end
                        if (invoiceRow <= 1) {
                            return 'EXP5110_standard_cb';
                        }
                    } else if (name == 'invoiceCode') {
                        if (invoiceRow <= 1 && record.get('invoiceCategoryName')) {
                            return 'EXP5110_standard_lov';
                        }
                    } else if (name == 'invoiceNumber') {
                        if (invoiceRow <= 1 && invoiceSource == 'MANUAL') {
                            return 'EXP5110_standard_tf';
                        }
                    } else if (name == 'invoiceDate') {
                        if (invoiceRow <= 1 && invoiceSource == 'MANUAL') {
                            return 'EXP5110_standard_dp';
                        }
                    } else if (name == 'invoiceAmount') {
                        if (invoiceRow <= 1 && invoiceSource == 'MANUAL') {
                            return 'EXP5110_standard_nf_2';
                        }
                    } else if (name == 'taxTypeName') {
                        // Modify Tagin/2018.04.17 存在只录税率及税额（只拆分税金不关联发票）
                        if ((invoiceRow <= 1 && invoiceSource == 'MANUAL') || (!record.get('invoiceCode') && invoiceRow <= 1)) {
                            return 'EXP5110_standard_cb';
                        }
                    } else if (name == 'taxAmount') {
                        // Modify Tagin/2018.04.17 存在只录税率及税额（只拆分税金不关联发票）
                        if ((invoiceRow <= 1 && invoiceSource == 'MANUAL') || (!record.get('invoiceCode') && invoiceRow <= 1)) {
                            //add by caoke/2018-05-30 设置比输
                            if (record.get('taxTypeRate')) {
                                record.getField('taxAmount').setRequired(true);
                            } else {
                                record.getField('taxAmount').setRequired(false);
                            }
                            return 'EXP5110_standard_nf_2';
                        }
                    }
                    return '';
                };

                // 5.0 增值税发票取消关联
                this.cancel = function (headerId, lineId, dataSet) {
                    if (headerId && lineId) {
                        Aurora.request({
                            url: $au('EXP5110_exp_report_maintain_cancel_report_realation_link').getUrl(),
                            para: {
                                expReportHeaderId: headerId,
                                expReportLineId: lineId
                            },
                            success: function (resp) {
                                dataSet.query();
                            }
                        });
                    }
                };
            });

            function EXPT5110_onStandardLineUpdateFun(ds, record, name, value, oldValue) {
                var index = ds.indexOf(record);
                var hecUtil = new HecUtil();
                if (name == 'companyId') {
                    //公司变更清除部门、岗位、员工
                    record.set('unitName', '');
                    record.set('unitId', '');
                    record.set('positionName', '');
                    record.set('positionId', '');
                    //添加公司变更清除地点和地点类型   Y.duan 2017-8-14 17:12:09
                    record.set('placeTypeName', '');
                    record.set('placeTypeId', '');
                    record.set('placeId', '');
                    record.set('placeName', '');

                    record.set('employeeId', '');
                    record.set('employeeName', '');
                    record.set('accEntityId', '');
                    record.set('accEntityName', '');
                    record.set('respCenterId', '');
                    record.set('respCenterName', '');
                    record.set('bgtEntityId', '');
                    record.set('bgtCenterId', '');
                    record.set('employeeId', '');
                    record.set('employeeName', '');
                    record.set('accEntityId', '');
                    record.set('accEntityName', '');
                    record.set('respCenterId', '');
                    record.set('respCenterName', '');
                    record.set('bgtEntityId', '');
                    record.set('bgtCenterId', '');
                } else if (name == 'accEntityId') {
                    //核算实体边更清除责任中心
                    record.set('respCenterName', '');
                    record.set('respCenterId', '');
                } else if (name == 'primaryQuantity') {
                    record.set('businessAmount', hecUtil.calAmount(record.get('businessPrice'), record.get('primaryQuantity'), record.get('businessCurrencyPrecision')));
                    record.set('paymentAmount', hecUtil.calAmount(mul(record.get('businessPrice'), record.get('biz2payExchangeRate')), record.get('primaryQuantity'), record.get('paymentCurrencyPrecision')));
                    record.set('managementAmount', hecUtil.calAmount(mul(mul(record.get('businessPrice'), record.get('biz2payExchangeRate')), record.get('pay2magExchangeRate')), record.get('primaryQuantity'), record.get('managementCurrencyPrecision')));
                    record.set('reportFunctionalAmount', hecUtil.calExchangeAmount(mul(mul(record.get('businessPrice'), record.get('biz2payExchangeRate')), record.get('primaryQuantity')), record.get('pay2magExchangeRate')), record.get('pay2funExchangeRate'), record.get('functionalCurrencyPrecision'));
                    record.set('functionalAmount', hecUtil.calExchangeAmount(mul(mul(record.get('businessPrice'), record.get('biz2payExchangeRate')), record.get('primaryQuantity')), record.get('pay2magExchangeRate')), record.get('pay2funExchangeRate'), record.get('functionalCurrencyPrecision'));
                } else if (name == 'businessPrice') {
                    record.set('businessAmount', hecUtil.calAmount(record.get('businessPrice'), record.get('primaryQuantity'), record.get('businessCurrencyPrecision')));
                    record.set('paymentPrice', hecUtil.calExchangeAmount(record.get('businessPrice'), record.get('biz2payExchangeRate'), record.get('paymentCurrencyPrecision')));
                    record.set('paymentAmount', hecUtil.calAmount(mul(record.get('businessPrice'), record.get('biz2payExchangeRate')), record.get('primaryQuantity'), record.get('paymentCurrencyPrecision')));
                    record.set('managementPrice', hecUtil.calExchangeAmount(mul(record.get('businessPrice'), record.get('biz2payExchangeRate')), record.get('pay2magExchangeRate'), record.get('managementCurrencyPrecision')));
                    record.set('managementAmount', hecUtil.calAmount(mul(mul(record.get('businessPrice'), record.get('biz2payExchangeRate')), record.get('pay2magExchangeRate')), record.get('primaryQuantity'), record.get('managementCurrencyPrecision')));
                    record.set('reportFunctionalAmount', hecUtil.calExchangeAmount(mul(mul(record.get('businessPrice'), record.get('biz2payExchangeRate')), record.get('primaryQuantity')), record.get('pay2magExchangeRate')), record.get('pay2funExchangeRate'), record.get('functionalCurrencyPrecision'));
                    record.set('functionalAmount', hecUtil.calExchangeAmount(mul(mul(record.get('businessPrice'), record.get('biz2payExchangeRate')), record.get('primaryQuantity')), record.get('pay2magExchangeRate')), record.get('pay2funExchangeRate'), record.get('functionalCurrencyPrecision'));
                } else if (name == 'pay2funExchangeRate') {
                    record.set('reportFunctionalAmount', hecUtil.calExchangeAmount(record.get('businessAmount') * record.get('biz2payExchangeRate'), record.get('pay2funExchangeRate'), record.get('functionalCurrencyPrecision')));
                    record.set('functionalAmount', hecUtil.calExchangeAmount(record.get('businessAmount') * record.get('biz2payExchangeRate'), record.get('pay2funExchangeRate'), record.get('functionalCurrencyPrecision')));
                } else if (name == 'paymentAmount') {
                    EXP5110_onHeadAmountChanged();
                    // } else if (name == 'primaryQuantity') {
                    // record.set('businessAmount', hecUtil.calAmount(record.get('businessPrice'), record.get('primaryQuantity'), record.get('businessCurrencyPrecision')));
                    // record.set('paymentAmount', hecUtil.calAmount(record.get('paymentPrice'), record.get('primaryQuantity'), record.get('paymentCurrencyPrecision')));
                    // record.set('managementAmount', hecUtil.calAmount(record.get('managementPrice'), record.get('primaryQuantity'), record.get('managementCurrencyPrecision')));
                    // record.set('reportFunctionalAmount', hecUtil.calExchangeAmount(record.get('businessAmount') * record.get('biz2payExchangeRate'), record.get('pay2funExchangeRate'), record.get('functionalCurrencyPrecision')));
                    // record.set('functionalAmount', hecUtil.calExchangeAmount(record.get('businessAmount') * record.get('biz2payExchangeRate'), record.get('pay2funExchangeRate'), record.get('functionalCurrencyPrecision')));
                    // } else if (name == 'businessPrice') {
                    // record.set('businessAmount', hecUtil.calAmount(record.get('businessPrice'), record.get('primaryQuantity'), record.get('businessCurrencyPrecision')));
                    // record.set('paymentPrice', hecUtil.calExchangeAmount(record.get('businessPrice'), record.get('biz2payExchangeRate'), record.get('paymentCurrencyPrecision')));
                    // } else if (name == 'paymentPrice') {
                    // record.set('paymentAmount', hecUtil.calAmount(record.get('paymentPrice'), record.get('primaryQuantity'), record.get('paymentCurrencyPrecision')));
                    // // Tagin/2018.07.19 为避免尾差处理直接用 业务单价 * 业务币种->支付币种汇率 = 支付单价
                    // record.set('managementPrice', hecUtil.calExchangeAmount(record.get('businessPrice') * record.get('biz2payExchangeRate'), record.get('pay2magExchangeRate'), record.get('managementCurrencyPrecision')));
                    // } else if (name == 'managementPrice') {
                    // record.set('managementAmount', hecUtil.calAmount(record.get('managementPrice'), record.get('primaryQuantity'), record.get('managementCurrencyPrecision')));
                    // } else if (name == 'paymentAmount') {
                    // // Tagin/2018.07.19 为避免尾差处理直接用 业务币种 * 业务币种->支付币种汇率 = 支付金额
                    // record.set('reportFunctionalAmount', hecUtil.calExchangeAmount(record.get('businessAmount') * record.get('biz2payExchangeRate'), record.get('pay2funExchangeRate'), record.get('functionalCurrencyPrecision')));
                    // record.set('functionalAmount', hecUtil.calExchangeAmount(record.get('businessAmount') * record.get('biz2payExchangeRate'), record.get('pay2funExchangeRate'), record.get('functionalCurrencyPrecision')));
                    // // Tagin/2018.07.19 重算计算单据头金额
                    // EXP5110_onHeadAmountChanged();
                    // } else if (name == 'pay2funExchangeRate') {
                    // // Tagin/2018.07.19 为避免尾差处理直接用 业务币种 * 业务币种->支付币种汇率 = 支付金额
                    // record.set('reportFunctionalAmount', hecUtil.calExchangeAmount(record.get('businessAmount') * record.get('biz2payExchangeRate'), value, record.get('functionalCurrencyPrecision')));
                    // record.set('functionalAmount', hecUtil.calExchangeAmount(record.get('businessAmount') * record.get('biz2payExchangeRate'), value, record.get('functionalCurrencyPrecision')));
                } else if (name == 'moExpenseTypeId') {
                    record.set('moExpenseItemName', '');
                    record.set('moExpenseItemId', '');
                } else if (name == 'moExpenseItemId') {
                    record.set('budgetItemId', '');
                    record.set('budgetItemName', '');
                } else if (name == 'biz2payExchangeRate') {
                    // Modify Tagin/2018.05.08 当业务币种-支付币种 汇率变化时调整支付单价
                    record.set('paymentPrice', hecUtil.calExchangeAmount(record.get('businessPrice'), value, record.get('paymentCurrencyPrecision')));
                } else if (name == 'businessCurrencyCode') {
                    // Modify Tagin/2018.04.02 增加业务币种-支付币种、支付币种-本位币获取汇率逻辑
                    var dataSet = $au('EXP5110_standardDocExchangeRate_ds');
                    dataSet.setQueryParameter('accEntityId', record.get('accEntityId'));
                    dataSet.setQueryParameter('businessCurrencyCode', record.get('businessCurrencyCode'));
                    dataSet.setQueryParameter('paymentCurrencyCode', record.get('paymentCurrencyCode'));
                    dataSet.query();
                } else if (name == 'invoiceCategoryId') {
                    //add by caoke/2018-05-30 取消关联
                    if (record.get('invoiceId')) {
                        EXP5110_cancelLineRefInvoice(index);
                    } else {
                        new EXP5110_maintainInvoice(record).clear(null);
                    }
                } else if (name == 'invoiceCode') {
                    // Modify Tagin/2018.04.10 根据发票代码获取发票类型
                    var dataSet = $au('EXP5110_standardInvoiceTypeDs');
                    dataSet.setQueryParameter('invoiceCode', value);
                    dataSet.query();
                } else if (name == 'invoiceType') {
                    if (record.get('invoiceSource') == 'MANUAL') {
                        // Modify Tagin/2018.04.16 设置增值税相关字段的必输性
                        new EXP5110_maintainInvoice(record).required(value);
                    }
                    // Modify Tagin/2018.04.16 设置增值税发票清空逻辑
                    new EXP5110_maintainInvoice(record).clear(value);
                } else if (name == 'invoiceAmount') {
                    // Modify Tagin/2018.05.22 发票价税合计变更时自动计算税额/实际报销金额
                    if (record.get('invoiceSource') == 'MANUAL') {
                        if (record.get('taxTypeRate') && value) {
                            //Modified by caoke/2018-05-30 计算价内税和价外税
                            if (record.get('autoCalculationFlag') == 'Y') {
                                record.set('taxAmount', hecUtil.calTaxAmount(record.get('invoiceAmount'), record.get('taxTypeRate'), record.get('taxCategory'), record.get('businessCurrencyPrecision')));
                            } else {
                                record.set('taxAmount', '');
                            }
                        }
                    }
                } else if (name == 'taxTypeRate') {
                    // Modify Tagin/2018.05.22 发票税率变更时自动计算税额/实际报销金额
                    if (record.get('invoiceSource') == 'MANUAL') {
                        if (record.get('invoiceAmount') && value) {
                            //价税合计金额算税额方案为 ： 金额  * (税率 / (1 + 税率))
                            //Modified by caoke/2018-05-30 计算价内税和价外税
                            if (record.get('autoCalculationFlag') == 'Y') {
                                record.set('taxAmount', hecUtil.calTaxAmount(record.get('invoiceAmount'), record.get('taxTypeRate'), record.get('taxCategory'), record.get('businessCurrencyPrecision')));
                            } else {
                                record.set('taxAmount', '');
                            }
                        }
                    }
                }
                //数据变更获取费用政策
                if (name == 'accEntityId' || name == 'companyId' || name == 'employeeId' || name == 'moExpenseItemId' || name == 'placeTypeId' || name == 'placeId' || name == 'businessCurrencyCode' || name == 'positionId') {
                    // 调用头页面元素的JS,动态获取并处理当前行费用政策相关数据,在行数据更新、新增的时候触发。
                    EXP5110_onLinePolicyInit(record, name);
                }
            }

            // function EXPT5110_onStandardLineLoadFun(dataSet) {
            // var records = dataSet.getAll();
            // }

            function EXP5110_onStandardLineCellClickFun(grid, row, name, record) {
                var positionId = record.get('positionId');
                if (name == 'unitName') {
                    var employeeId = record.get('employeeId');
                    if (!Ext.isEmpty(employeeId)) {
                        record.getField('unitName').setLovPara('employeeId', record.get('employeeId'));
                        record.getField('unitName').setLovPara('companyId', record.get('companyId'));
                        record.getField('unitName').setLovCode('EXP_REP_EM_UNIT_LOV');
                    } else {
                        record.getField('unitName').setLovPara('companyId', record.get('companyId'));
                        // 新增核算主体Id查询条件 caoke 2018-1-25 14:38:04
                        record.getField('unitName').setLovPara('accEntityId', record.get('accEntityId'));
                        record.getField('unitName').setLovCode('EXP_ORG_UNITS_VL_LOV');
                    }
                } else if (name == 'positionName') {
                    record.getField('positionName').setLovPara('companyId', record.get('companyId'));
                    record.getField('positionName').setLovPara('employeeId', record.get('employeeId'));
                    record.getField('positionName').setLovPara('unitId', record.get('unitId'));
                } else if (name == 'employeeName') {
                    record.getField('employeeName').setLovPara('companyId', record.get('companyId'));
                    if (!Ext.isEmpty(positionId)) {
                        record.getField('employeeName').setLovPara('positionId', record.get('positionId'));
                        record.getField('employeeName').setLovPara('primaryPositionFlag', null);
                    } else {
                        record.getField('employeeName').setLovPara('positionId', null);
                        record.getField('employeeName').setLovPara('primaryPositionFlag', 'Y');
                    }
                } else if (name == 'accEntityName') {
                    record.getField('accEntityName').setLovPara('companyId', record.get('companyId'));
                } else if (name == 'respCenterName') {
                    record.getField('respCenterName').setLovPara('accEntityId', record.get('accEntityId'));
                    record.getField('respCenterName').setLovPara('summaryFlag', 'N');
                    //add by caoke/20180-01-25 增加部门限制
                    record.getField('respCenterName').setLovPara('unitId', record.get('unitId'));
                } else if (name == 'placeName') {
                    record.getField('placeName').setLovPara('companyId', record.get('companyId'));
                } else if (name == 'moExpenseTypeName') {
                    $au('EXP5110_standard_expense_type_ds').setQueryParameter('companyId', record.get('companyId'));
                    $au('EXP5110_standard_expense_type_ds').query();
                } else if (name == 'moExpenseItemName') {
                    /*Modified by Arthur.Chen Date:2017-09-20 19:42:31
                     1.动态获取moExpenseItemName字段的options
                     */
                    $au('EXP5110_standard_items_ds').setQueryParameter('moExpenseTypeId', record.get('moExpenseTypeId'));
                    $au('EXP5110_standard_items_ds').setQueryParameter('companyId', record.get('companyId'));
                    $au('EXP5110_standard_items_ds').setQueryParameter('moExpReportTypeId', '$[/model/header_info/records/record/@moExpReportTypeId]');
                    $au('EXP5110_standard_items_ds').setQueryParameter('pageElementCode', 'STANDARD');
                    $au('EXP5110_standard_items_ds').query();
                } else if (name == 'moExpenseTypeName') {
                    $au('EXP5110_standard_expense_type_ds').setQueryParameter('companyId', record.get('companyId'));
                } else if (name == 'invoiceCode') {
                    // Modify Tagin/2018.04.28 设置LovUrl及参数
                    record.getField(name).setLovPara('sourceDataset', 'EXP5110_exp_report_standard_line_ds');
                    record.getField(name).setLovPara('sourceLineDataset', 'EXP5110_standardInvoiceLineDs');
                    record.getField(name).setLovPara('relationId', record.get('relationId') || '');
                    record.getField(name).setLovPara('invoiceId', record.get('invoiceId') || '');
                    record.getField(name).setLovPara('invoiceCategoryId', record.get('invoiceCategoryId') || '');
                    record.getField(name).setLovPara('invoiceCode', record.get('invoiceCode') || '');
                    record.getField(name).setLovPara('invoiceNumber', record.get('invoiceNumber') || '');
                    record.getField(name).setLovPara('invoiceDate', record.get('invoiceDate') || '');
                    record.getField(name).setLovPara('amount', record.get('amount') || '');
                    record.getField(name).setLovPara('taxAmount', record.get('taxAmount') || '');
                    record.getField(name).setLovPara('expReportLineId', record.get('expReportLineId') || '');
                    record.getField(name).setLovWidth(parseInt(Aurora.getCookie('vw')) + 20);
                    record.getField(name).setLovHeight(parseInt(Aurora.getCookie('vh')));
                    record.getField(name).setTitle('$[l:invoice_select]');
                }
                if (/dimension/.test(name)) { // 设置维度查询条件【Tips：若涉及多维度则需要判断维度对应的级别，不同级别对应的组织架构不同】
                    var id = '';
                    var fieldName = name.replace('Name', 'Level');
                    var level = record.get(fieldName);
                    if (level == 'BUDGET') {
                        id = record.get('bgtEntityId');
                    } else if (level == 'ACCOUNTING') {
                        id = record.get('accEntityId');
                    } else if (level == 'MANAGEMENT') {
                        id = '$[/session/@companyId]';
                    }
                    record.getField(name).setLovPara('companyId', id);
                }
                if (name == 'periodName') {
                    record.getField(name).setLovPara("bgtEntityId", record.get('bgtEntityId'));
                }
            }

            function EXP5110_standardBizPriceEditorFun(record, name) {
                if (name == 'businessPrice') {
                    if (record.get('changeableFlag') == 'N') {
                        record.getMeta().getField('businessPrice').setReadOnly(true);
                        return '';
                    } else {
                        var precision = record.get('businessCurrencyPrecision');
                        record.getMeta().getField('businessPrice').setReadOnly(false);
                        if (!precision) {
                            return 'EXP5110_standard_nf_2';
                        } else {
                            return 'EXP5110_standard_nf_' + precision;
                        }
                    }
                }
            }

            function EXP5110_standardDateValidator(record, name, value) {
                if (record.get('dateFrom') && record.get('dateTo')) {
                    if (record.get('dateFrom') > record.get('dateTo')) {
                        return '$[l:start_date_not_later_ending_date]';
                    }
                }
                return true;
            }

            function EXP5110_standardCreateFromReq() {
                //add lcy 2018/5/29 22:33:34 怎加是否为统购标识
                var monopolizeFlag = 'N';
                new Aurora.Window({
                    url: $au('EXP5110_exp_report_maintain_create_from_req_link').getUrl() + '?moExpReportTypeId=$[/model/header_info/records/record/@moExpReportTypeId]&reportPageElementCode=STANDARD&paymentCurrencyCode=' + $au('EXP5110_exp_report_header_ds').getAt(0).get('paymentCurrencyCode') + '&monopolizeFlag=' + monopolizeFlag,
                    id: 'EXP5110_exp_report_maintain_create_from_req_window',
                    title: '$[l:exp_report_create.fromreq]',
                    fullScreen: true
                });
            }

            function EXP5110_standardLineInitVatColumn() {
                var headerRd = $au('EXP5110_exp_report_header_ds').getAt(0);
                var lineGrid = $au('EXP5110_standard_lines_grid');
                // Modify Tagin/2018.04.16 调用增值税发票行信息显示方法
                new EXP5110_maintainInvoice().show(headerRd.get('vatInvoiceFlag'), lineGrid);
            }

            function EXP5110_standardLineInvoiceInfoRenderer(value, record, name) {
                // var vatCount = !record.get('vatcount') ? 0 : record.get('vatcount');
                // var taxCount = !record.get('taxcount') ? 0 : record.get('taxcount');
                // var index = $au('EXP5110_exp_report_standard_line_ds').indexOf(record);
                // var vatContent = '';
                // var taxContent = '';
                // var cancelContent = '';
                // if ('$[/model/standardLineElement/record/@moreInvoiceFlag]' == 'Y') {
                //     if (vatCount <= 1) {
                //         if (!record.isNew) {
                //             vatContent = '<a href="javascript:EXP5110_openMoreInvoiceWindow(\'' + index + '\')">$[l:exp_mo_req_types.invoice_flag]</a>';
                //         }
                //     } else {
                //         vatContent = '<a href="javascript:EXP5110_openMoreInvoiceWindow(\'' + index + '\')"><font color="green">$[l:exp_mo_req_types.invoice_flag]</font></a>';
                //     }
                // }
                // if ('$[/model/standardLineElement/record/@moreTaxFlag]' == 'Y') {
                //     if (record.get('relationId')) { // Modify Tagin/2018.05.22 若不存在发票关联ID，则为关联多个发票税额不显示【进入更多发票查看税额】
                //         if (taxCount <= 1) {
                //             if (!record.isNew) {
                //                 taxContent = '<a href="javascript:EXP5110_standardMoreTaxFunc(' + index + ')">$[l:vat_invoice_lines.tax_rate]</a>';
                //             }
                //         } else {
                //             taxContent = '<a href="javascript:EXP5110_standardMoreTaxFunc(' + index + ')"><font color="green">$[l:vat_invoice_lines.tax_rate]</font></a>';
                //         }
                //     }
                // }
                // //return !vatContent ? (!taxContent ? '' : taxContent) : (!taxContent ? vatContent : vatContent + ' / ' + taxContent);
                // cancelContent = '<a href="javascript:EXP5110_cancelLineRefInvoice(' + index + ');">$[l:hap_cancel]</a>';
                // return (!vatContent ? '<font color="gray">$[l:exp_mo_req_types.invoice_flag]</font>' : vatContent) + ' / ' + (!taxContent ? '<font color="gray">$[l:vat_invoice_lines.tax_rate]</font>' : taxContent) + ' / ' + cancelContent;
            }

            function EXP5110_openMoreInvoiceWindow(index) {
                // var record = $au('EXP5110_exp_report_standard_line_ds').getAt(index);
                // var headerId = record.get('expReportHeaderId') || '';
                // var lineId = record.get('expReportLineId') || '';
                // var distId = record.get('expReportDistId') || '';
                // new Aurora.Window({
                //     url: $au('EXP5110_exp_report_maintain_more_invoice_standard_link').getUrl() + '?expReportHeaderId=' + headerId + '&expReportLineId=' + lineId + '&expReportDistId=' + distId + '&sourceDataset=' + 'EXP5110_exp_report_standard_line_ds',
                //     id: 'EXP5110_exp_report_maintain_more_invoice_standard_window',
                //     title: '$[l:more_invoice]',
                //     side: 'right',
                //     width: .8
                // }).on('close', function() {
                //     $au('EXP5110_exp_report_standard_line_ds').query();
                // });
            }

            function EXP5110_standardMoreTaxFunc(index) {
                // var record = $au('EXP5110_exp_report_standard_line_ds').getAt(index);
                // var invoiceLineId = record.get('invoiceLineId') || '';
                // var invoiceId = record.get('invoiceId') || '';
                // var relationId = record.get('relationId') || '';
                // var lineId = record.get('expReportLineId') || '';
                // new Aurora.Window({
                //     id: 'EXP5110_exp_report_maintain_vat_invoice_more_tax_window',
                //     url: $au('EXP5110_exp_report_maintain_vat_invoice_more_tax_link').getUrl() + '?invoiceLineId=' + invoiceLineId + '&invoiceId=' + invoiceId + '&relationId=' + relationId + '&expReportLineId=' + lineId,
                //     side: 'right',
                //     width: 1020
                // }).on('close', function() {
                //     $au('EXP5110_exp_report_standard_line_ds').query();
                // });
            }

            function EXP5110_invoiceItemValidator(record, name, value) {
                // if (record.get('invoiceLineId') && !value) {
                //     //发票项目允许为空，财务人员在审核时选择
                //     //return '发票项目必输!';
                // }
                // return true;
            }

            function EXP5110_invoiceUsedeValidator(record, name, value) {
                // if (record.get('invoiceLineId') && !value) {
                //     //发票用途允许为空，财务人员在审核时选择
                //     //return '发票用途必输!';
                // }
                // return true;
            }

            function EXP5110_standardMoreRenderer(value, record, name) {
                if (name == 'more' && record.get('expReportLineId')) {
                    var content = '>>';
                    return '<a href="javascript:EXP5110_openStandardMoreWindow(\'' + record.get('expReportHeaderId') + '\',\'' + record.get('expReportLineId') + '\')">' + content + '</a>';
                }
            }

            function EXP5110_openStandardMoreWindow(headerId, lineId, companyId, bgtEntityId, accEntityId) {
                var typeId = '$[/model/header_info/records/record/@moExpReportTypeId]';
                var invoiceFlag = '$[/model/standardLineElement/records/record/@invoiceFlag]';
                var taxFlag = '$[/model/standardLineElement/records/record/@taxFlag]';
                var companyId = '$[/model/header_info/records/record/@companyId]';
                var bgtEntityId = '$[/model/header_info/records/record/@bgtEntityId]';
                var accEntityId = '$[/model/header_info/records/record/@accEntityId]';
                new Aurora.Window({
                    title: '$[l:conventional_expense_line_detail]',
                    id: 'EXP5110_exp_report_maintain_standard_lines_extend_window',
                    url: $au('EXP5110_exp_report_maintain_standard_lines_extend_link').getUrl() + '?moExpReportTypeId=' + typeId + '&expReportHeaderId=' + headerId + '&expReportLineId=' + lineId + '&invoiceFlag=' + invoiceFlag + '&taxFlag=' + taxFlag + '&companyId=' + companyId + '&bgtEntityId=' + bgtEntityId + '&accEntityId=' + accEntityId,
                    side: 'right',
                    width: .8
                }).on('close', function () {
                    $au('EXP5110_exp_report_standard_line_ds').query();
                });
            }

            function EXP5110_standardTaxEditorFun(record, name) {
                // // Modify Tagin/2018.04.17 调整
                // return new EXP5110_maintainInvoice(record).edit(name);
            }

            function EXP5110_standardInvoiceValidator(record, name, value) {
                if (!record.dirty && !record.isNew) {
                    return true;
                }
                // 1.0 判断税额
                if (name == 'taxAmount') {
                    if (value < 0) {
                        return '$[l:vat_invoice_tax_amount_positive]';
                    } else {
                        if (record.get('invoiceAmount') && value) {
                            if (value > record.get('invoiceAmount')) {
                                return '$[l:vat_invoice_tax_less_total]';
                            }
                        }
                    }
                    return true;
                }
                // 2.0 判断价税合计
                if (name == 'invoiceAmount') {
                    if (value < 0) {
                        return '$[l:vat_invoice_total_amount_positive]';
                    } else {
                        if (record.get('taxAmount') && value) {
                            if (value < record.get('taxAmount')) {
                                return '$[l:vat_invoice_total_more_tax]';
                            }
                        }
                    }
                    return true;
                }
                return true;
            }

            function EXP5110_cancelLineRefInvoice(index) {
                // var record = $au('EXP5110_exp_report_standard_line_ds').getAt(index);
                // var headerId = record.get('expReportHeaderId') || '';
                // var lineId = record.get('expReportLineId') || '';
                // if (record.get('vatcount') > 0) {
                //     // Modify Tagin/2018.04.16 取消关联增值税发票信息
                //     new EXP5110_maintainInvoice().cancel(headerId, lineId, $au('EXP5110_exp_report_standard_line_ds'));
                // }
                // // Modify Tagin/2018.04.16 清空增值税发票信息
                // new EXP5110_maintainInvoice(record).clear();
            }

            function EXP5110_standardInvoiceTypeLoadFunc(dataSet) {
                // Modify Tagin/2018.04.10 增加根据发票代码获取发票类型加载时动态设置单据行的发票类型字段
                // var record = dataSet.getAt(0);
                // var reportRd = $au('EXP5110_exp_report_standard_line_ds').getCurrentRecord();
                // var invoiceType = record.get('invoiceType');
                // if (reportRd.get('invoiceCode')) {
                //     reportRd.set('invoiceSource', !reportRd.get('invoiceSource') ? 'MANUAL' : reportRd.get('invoiceSource'));
                //     reportRd.set('invoiceType', invoiceType);
                // } else {
                //     reportRd.set('invoiceType', '');
                // }
            }

            function EXP5110_standardDocExchangeRateFunc(dataSet) {
                // Modify Tagin/2018.05.08 将获取的汇率设置到单据行
                var record = dataSet.getAt(0);
                var reportRd = $au('EXP5110_exp_report_standard_line_ds').getCurrentRecord();
                reportRd.set('biz2payExchangeRate', record.get('biz2payExchangeRate'));
                reportRd.set('pay2funExchangeRate', record.get('pay2funExchangeRate'));
            }

            function EXP5110_standardInvoiceLineAddFunc(dataSet, record, index) {
                // Modify Tagin/2018.05.21 若发票拆分税金/实际报销金额为空则赋值为税额
                // Modify Tagin/2018.07.13 设置汇率相关字段
                // var lRecord = $au('EXP5110_exp_report_standard_line_ds').getCurrentRecord();
                // record.set('biz2payExchangeRate', lRecord.get('biz2payExchangeRate'));
                // record.set('pay2funExchangeRate', lRecord.get('pay2funExchangeRate'));
            }

            // Modify Tagin/2018.04.16 页面加载时，添加单据头相关事件
            $au('EXP5110_exp_report_header_ds')['on']('beforesubmit', function () {
                var standardLineDs = $au('EXP5110_exp_report_standard_line_ds');
                if (!standardLineDs.validate()) {
                    return false;
                } else {
                    var records = standardLineDs.getAll();
                    for (var i = 0; i < records.length; i++) {
                        var expObjDatas = [];
                        for (field in standardLineDs.fields) {
                            if (/^#/.test(field)) {
                                //id字段
                                var expObjTypeId = field.replace(/^#/, '');
                                var idValue = String(records[i].get(field));
                                var nameValue = records[i].get('^' + field);
                                var expObjData = {
                                    moExpObjTypeId: expObjTypeId,
                                    moExpenseObjectId: idValue,
                                    moExpenseObjectName: nameValue
                                };
                                expObjDatas.push(expObjData);
                            }
                        }
                        records[i]['data']['expenseObjectLines'] = expObjDatas;
                    }
                }
            });
            $au('EXP5110_exp_report_header_ds')['on']('update', function (dataSet, record, name, value, oldValue) {
                // Modify Tagin/2018.04.16 单据头发票信息变更时，调用增值税发票行信息显示方法
                if (name == 'vatInvoiceFlag') {
                    new EXP5110_maintainInvoice().show(value, $au('EXP5110_standard_lines_grid'));
                }
            });
            $au('EXP5110_exp_report_header_ds')['on']('submitsuccess', function () {
                //如果当前拓展页面打开，则进行dists的查询，如果没有打开，则进行lines的查询
                if ($A.CmpManager.get('EXP5110_exp_report_maintain_standard_lines_extend_window')) {
                    //$au('EXP5110_exp_report_standard_dist_ds').query();
                    null;
                } else {
                    $au('EXP5110_exp_report_standard_line_ds').query();
                }
            });

            Aurora.onReady(EXP5110_standardLineInitVatColumn);
            ]]></script>
        <a:dataSets>
            <a:dataSet id="EXP5110_standard_expense_type_default_ds" autoQuery="true" loadData="true" fetchAll="true"
                       queryUrl="$[/request/@context_path]/exp/report-line/queryExpenseTypeDefault?pageElementCode=STANDARD&amp;moExpReportTypeId=$[/model/header_info/records/record/@moExpReportTypeId]"/>
            <a:dataSet id="EXP5110_standard_expense_type_ds" autoQuery="false" fetchAll="true"
                       queryUrl="$[/request/@context_path]/exp/report-line/queryExpenseType?pageElementCode=STANDARD&amp;moExpReportTypeId=$[/model/header_info/records/record/@moExpReportTypeId]"/>
            <!--Modified by Arthur.Chen Date:2017-09-20 19:42:31 1.修改moExpenseItemName字段的编辑器为combobox模式，设置item的option的ds -->
            <a:dataSet id="EXP5110_standard_items_ds" autoQuery="true" fetchAll="true"
                       queryUrl="$[/request/@context_path]/exp/report-line/queryExpenseItem"/>
            <a:dataSet id="EXP5110_standard_tax_rate_type_ds" autoQuery="false"
                       queryUrl="$[/request/@context_path]/exp/report-line/queryTaxTypeCode?withholdingFlag=N"/>
            <a:dataSet id="EXP5110_standardInvoiceCategory_ds"/>
            <!-- Modify Tagin/2018.05.08 
            		增加会单据业务币种-支付币种汇率、支付币种-本位币汇率获取逻辑   
            -->
            <a:dataSet id="EXP5110_standardDocExchangeRate_ds">
                <a:events>
                    <a:event name="load" handler="EXP5110_standardDocExchangeRateFunc"/>
                </a:events>
            </a:dataSet>
            <a:dataSet id="EXP5110_standardInvoiceTypeDs">
                <a:events>
                    <a:event name="load" handler="EXP5110_standardInvoiceTypeLoadFunc"/>
                </a:events>
            </a:dataSet>
            <a:dataSet id="EXP5110_exp_report_standard_line_ds" autoQuery="true" bindName="standardLines"
                       bindTarget="EXP5110_exp_report_header_ds" prompt="exp_requisition_standard_line"
                       queryDataSet="EXP5110_exp_report_header_ds" selectable="true"
                       queryUrl="$[/request/@context_path]/exp/report-line/reportLineQuery?reportPageElementCode=STANDARD&amp;_ban_remind=Y"
                       submitUrl="$[/request/@context_path]/exp/report-line/reportLineDelete">
                <a:fields>
                    <a:field name="expReportHeaderId"/>
                    <a:field name="expReportLineId"/>
                    <a:field name="lineNumber" prompt="con_contract_element.line_number" required="true"/>
                    <a:field name="reportPageElementCode" prompt="exp_req_page_element.req_page_element_code"
                             required="true"/>
                    <a:field name="description"/>
                    <a:field name="companyName" lovCode="FND_COMPANIES_VL_LOV"
                             prompt="bgt_budget_item_mapping.company_id"
                             required="true">
                        <a:mapping>
                            <a:map from="companyName" to="companyName"/>
                            <a:map from="companyId" to="companyId"/>
                            <a:map from="accEntityId" to="accEntityId"/>
                            <a:map from="accEntityName" to="accEntityName"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="companyId" prompt="bgt_budget_item_mapping.company_id" required="true"/>
                    <a:field name="accEntityName" lovCode="LOV_ACC_ENTITY_COMPANY"
                             prompt="csh_offer_format.acc_entity" required="true">
                        <a:mapping>
                            <a:map from="accEntityName" to="accEntityName"/>
                            <a:map from="accEntityId" to="accEntityId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="accEntityId" prompt="csh_offer_format.acc_entity" required="true"/>
                    <a:field name="unitName" prompt="acp_acp_requisition_hds.unit_id"
                             required="true">
                        <a:mapping>
                            <a:map from="unitId" to="unitId"/>
                            <a:map from="unitName" to="unitName"/>
                            <a:map from="positionId" to="positionId"/>
                            <a:map from="positionName" to="positionName"/>
                            <a:map from="defaultAccEntityId" to="accEntityId"/>
                            <a:map from="defaultAccEntityDisplay" to="accEntityName"/>
                            <a:map from="defaultResCenterId" to="respCenterId"/>
                            <a:map from="defaultResCenterDisplay" to="respCenterName"/>
                            <a:map from="defaultBgtEntityId" to="bgtEntityId"/>
                            <a:map from="defaultBgtCenterId" to="bgtCenterId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="unitId" prompt="acp_acp_requisition_hds.unit_id" required="true"/>
                    <a:field name="positionName" lovCode="EXP_ORG_POSITION_VL_LOV"
                             prompt="acp_acp_requisition_hds.position_id" required="true">
                        <a:mapping>
                            <a:map from="positionName" to="positionName"/>
                            <a:map from="positionId" to="positionId"/>
                            <a:map from="accEntityId" to="accEntityId"/>
                            <a:map from="accEntityName" to="accEntityName"/>
                            <a:map from="respCenterId" to="respCenterId"/>
                            <a:map from="respCenterName" to="respCenterName"/>
                            <a:map from="bgtEntityId" to="bgtEntityId"/>
                            <a:map from="bgtCenterId" to="bgtCenterId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="positionId" prompt="acp_acp_requisition_hds.position_id" required="true"/>
                    <a:field name="employeeName" lovCode="EXP_EMPLOYEE_DOC_LOV" prompt="acp_acp_requisition_hds.employee_id"
                             required="true">
                        <a:mapping>
                            <a:map from="employeeName" to="employeeName"/>
                            <a:map from="employeeId" to="employeeId"/>
                            <a:map from="accEntityId" to="accEntityId"/>
                            <a:map from="accEntityName" to="accEntityName"/>
                            <a:map from="responsibilityCenterId" to="respCenterId"/>
                            <a:map from="responsibilityCenterName" to="respCenterName"/>
                            <a:map from="entityId" to="bgtEntityId"/>
                            <a:map from="centerId" to="bgtCenterId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="employeeId" prompt="acp_acp_requisition_hds.employee_id" required="true"/>
                    <a:field name="respCenterName" lovCode="GLD_RESPONSIBILITY_CENTERS_VL_LOV"
                             prompt="fnd_responsibility_centers.responsibility_center_name" required="true">
                        <a:mapping>
                            <a:map from="responsibilityCenterName" to="respCenterName"/>
                            <a:map from="responsibilityCenterId" to="respCenterId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="respCenterId" prompt="acp_invoice_line.responsibility_center_id" required="true"/>
                    <a:field name="bgtEntityId" prompt="bgt_entities.bgt_entity_name" required="true"/>
                    <a:field name="bgtCenterId" prompt="bgt_centers.bgt_center_name" required="true"/>
                    <a:field name="placeTypeName" prompt="exp_report_line.place_type_id" readOnly="true"/>
                    <a:field name="placeTypeId"/>
                    <!--重新指定地点查询的lovService 地点类型直接自动带出   Y.duan 2017-8-14 17:13:08-->
                    <a:field name="placeName" autoComplete="true" autoCompleteField="placeName" lovAutoQuery="true"
                             lovCode="EXP_POLICY_PLACES_AND_TYPES_VL_LOV">
                        <a:mapping>
                            <a:map from="placeName" to="placeName"/>
                            <a:map from="placeId" to="placeId"/>
                            <a:map from="placeTypeId" to="placeTypeId"/>
                            <a:map from="placeTypeName" to="placeTypeName"/>
                            <a:map from="placeCode" to="placeCode"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="placeId"/>
                    <a:field name="budgetItemName"/>
                    <a:field name="budgetItemId"/>
                    <a:field name="dateFrom" prompt="exp_report_lines.date_from" required="true"
                             validator="EXP5110_standardDateValidator"/>
                    <a:field name="dateTo" prompt="exp_report_lines.date_to" required="true"
                             validator="EXP5110_standardDateValidator"/>
                    <a:field name="periodName" lovCode="BGT_PERIODS_LOV">
                        <a:mapping>
                            <a:map from="periodName" to="periodName"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="moExpenseTypeName" displayField="description"
                             options="EXP5110_standard_expense_type_ds" prompt="exp_report_lines.expense_type_id"
                             required="true" returnField="moExpenseTypeId" valueField="moExpenseTypeId"/>
                    <a:field name="moExpenseTypeId"/>
                    <!-- Modified by Arthur.Chen Date:2017-09-20 19:42:31 1.修改moExpenseItemName字段的编辑器为combobox模式,修改item的options为EXP5110_streamlined_items_ds -->
                    <a:field name="moExpenseItemName" displayField="description"
                             options="EXP5110_standard_items_ds" prompt="exp_report_lines.expense_item_id"
                             required="true" tipField="tip">
                        <a:mapping>
                            <a:map from="description" to="moExpenseItemName"/>
                            <a:map from="moExpenseItemId" to="moExpenseItemId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="moExpenseItemId"/>
                    <a:field name="primaryQuantity" prompt="acp_invoice_line.quantity" required="true"/>
                    <a:field name="businessCurrencyName" displayField="currencyName" options="EXP5110_currencyDs"
                             prompt="business_currency_name" required="true">
                        <a:mapping>
                            <a:map from="currencyCode" to="businessCurrencyCode"/>
                            <a:map from="precision" to="businessCurrencyPrecision"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="businessCurrencyCode" prompt="exp_report.business_currency_code" required="true"/>
                    <a:field name="biz2payExchangeType"/>
                    <a:field name="biz2payExchangeTypeName"/>
                    <a:field name="biz2payExchangeRate" prompt="exp_report.biz2pay_exchange_rate" required="true"/>
                    <a:field name="businessPrice" prompt="business_price" required="true"
                             validator="EXP5110_policyValidator"/>
                    <a:field name="businessAmount" prompt="business_amount" required="true"/>
                    <a:field name="paymentCurrencyName" prompt="gld_accounting_entities.pay_currency_code"
                             required="true"/>
                    <a:field name="paymentCurrencyCode" prompt="exp_report.payment_currency_code" required="true"/>
                    <a:field name="pay2funExchangeType"/>
                    <a:field name="pay2funExchangeTypeName"/>
                    <a:field name="pay2funExchangeRate" prompt="exp_report.pay2fun_exchange_rate" required="true"/>
                    <a:field name="paymentPrice" prompt="payment_price"/>
                    <a:field name="paymentAmount" prompt="payment_amount"/>
                    <a:field name="managementCurrencyName" prompt="exp_report_headers.currency_code" required="true"/>
                    <a:field name="managementCurrencyCode" prompt="exp_report.management_currency_code"
                             required="true"/>
                    <a:field name="pay2magExchangeType"/>
                    <a:field name="pay2magExchangeTypeName"/>
                    <a:field name="pay2magExchangeRate" prompt="pay2mag_exchange_rate" required="true"/>
                    <a:field name="managementPrice" prompt="management_price"/>
                    <a:field name="managementAmount" prompt="management_amount"/>
                    <a:field name="reportFunctionalAmount" prompt="exp_report_lines.requisition_functional_amount"/>
                    <!-- Modify Tagin/2018.04.12 将发票类型调整为从发票代码自动获取 / 增加发票种类字段 -->
                    <a:field name="invoiceCategoryName" displayField="invoiceCategoryName" fetchRemote="false"
                             options="EXP5110_standardInvoiceCategory_ds" prompt="vat_invoice_items.invoice_category"
                             returnField="invoiceCategoryId" valueField="invoiceCategoryId"/>
                    <a:field name="invoiceCategoryId"/>
                    <a:field name="invoiceType"/>
                    <a:field name="invoiceId"/>
                    <a:field name="invoiceLineId"/>
                    <a:field name="invoiceInfo"/>
                    <a:field name="invoiceCode" fetchRemote="false"
                             lovUrl="$[/request/@context_path]/modules/expm/EXP5110/exp_report_maintain_select_invoice.screen"
                             prompt="vat_invoices.invoice_code"/>
                    <a:field name="invoiceNumber" prompt="vat_invoices.invoice_number"/>
                    <a:field name="invoiceDate" prompt="vat_invoices.invoice_date"/>
                    <a:field name="invoiceAmount" prompt="total_ad_valorem_tax_invoice"
                             validator="EXP5110_standardInvoiceValidator"/>
                    <a:field name="taxAmount" prompt="vat_invoice_lines.tax_amount"
                             validator="EXP5110_standardInvoiceValidator"/>
                    <a:field name="taxTypeName" displayField="taxTypeName" options="EXP5110_standard_tax_rate_type_ds"
                             prompt="exp_report_lines.tax_rate_type" validator="EXP5110_standardInvoiceValidator">
                        <a:mapping>
                            <a:map from="taxTypeId" to="taxTypeId"/>
                            <a:map from="taxCategory" to="taxCategory"/>
                            <a:map from="autoCalculationFlag" to="autoCalculationFlag"/>
                            <a:map from="taxTypeRate" to="taxTypeRate"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="taxTypeId"/>
                    <a:field name="taxTypeRate"/>
                    <a:field name="more" prompt=">>"/>
                    <#list standard_line_dimensions as line_dimension>
                        <#if line_dimension??>
                            <a:field name="${line_dimension.displayField}"
                                     defaultValue="${line_dimension.defaultDimValueName}" required="true"
                                     prompt="${line_dimension.dimensionName}" title="${line_dimension.dimensionName}"
                                     lovCode="EXP_DIMENSION_VALUE_LOV?companyLevel=${line_dimension.companyLevel}&amp;dimensionId=${line_dimension.dimensionId}">
                                <a:mapping>
                                    <a:map from="dimensionValueId" to="${line_dimension.returnField}"/>
                                    <a:map from="dimensionValueName" to="${line_dimension.displayField}"/>
                                </a:mapping>
                            </a:field>
                            <a:field name="${line_dimension.returnField}"
                                     defaultValue="${line_dimension.defaultDimValueId}"/>
                            <a:field name="${line_dimension.levelField}" defaultValue="${line_dimension.companyLevel}"/>
                        </#if>
                    </#list>
                    <#list standard_line_objects as line_object>
                        <#if line_object??>
                            <#if line_object.expenseObjectMethod == "VALUE_LIST">
                                <a:field name="${line_object.displayField}"
                                         lovCode="LOV_OBJECT_VALUE?moExpObjTypeId=${line_object.moExpObjTypeId}"
                                         defaultValue="${line_object.defaultMoObjectName}"
                                         prompt="${line_object.moExpObjTypeName}"
                                         title="${line_object.moExpObjTypeName}"
                                         required="${line_object.requiredFlag}">
                                    <a:mapping>
                                        <a:map from="id" to="${line_object.returnField}"/>
                                        <a:map from="name" to="${line_object.displayField}"/>
                                    </a:mapping>
                                </a:field>
                            </#if>
                            <#if line_object.expenseObjectMethod == "SQL_TEXT">
                                <a:field name="${line_object.displayField}"
                                         lovCode="LOV_OBJECT_SQL_VALUE?sqlQuery=${line_object.sqlQuery}"
                                         defaultValue="${line_object.defaultMoObjectName}"
                                         prompt="${line_object.moExpObjTypeName}"
                                         title="${line_object.moExpObjTypeName}"
                                         required="${line_object.requiredFlag}">
                                    <a:mapping>
                                        <a:map from="id" to="${line_object.returnField}"/>
                                        <a:map from="name" to="${line_object.displayField}"/>
                                    </a:mapping>
                                </a:field>
                            </#if>
                            <a:field name="${line_object.returnField}" defaultValue="${line_object.defaultMoObjectId}"/>
                        </#if>
                    </#list>
                </a:fields>
                <a:events>
                    <a:event name="update" handler="EXPT5110_onStandardLineUpdateFun"/>
                    <a:event name="remove" handler="EXP5110_onHeadAmountChanged"/>
                </a:events>
            </a:dataSet>
            <!-- Modify Tagin/2018.05.18 增加报销单行关联发票行逻辑 【发票行可选择性被报销单关联】-->
            <a:dataSet id="EXP5110_standardInvoiceLineDs" autoCreate="false" bindName="invoiceLines"
                       bindTarget="EXP5110_exp_report_standard_line_ds">
                <a:fields>
                    <a:field name="relationId"/>
                    <a:field name="documentCategory" defaultValue="expReport"/>
                    <a:field name="invoiceId"/>
                    <a:field name="invoiceLineId"/>
                    <a:field name="documentId"/>
                    <a:field name="documentLineId"/>
                    <a:field name="documentDistId"/>
                    <a:field name="taxAmount"/>
                    <a:field name="businessAmount"/>
                    <a:field name="biz2payExchangeRate"/>
                    <a:field name="paymentAmount"/>
                    <a:field name="pay2funExchangeRate"/>
                    <a:field name="functionalAmount"/>
                    <a:field name="businessCurrencyPrecision"/>
                    <a:field name="paymentCurrencyPrecision"/>
                    <a:field name="functionalCurrencyPrecision"/>
                    <a:field name="autoCalculationFlag"/>
                    <a:field name="taxTypeId"/>
                </a:fields>
                <a:events>
                    <a:event name="add" handler="EXP5110_standardInvoiceLineAddFunc"/>
                </a:events>
            </a:dataSet>
        </a:dataSets>
        <a:form padding="0" showmargin="0" style="margin-top:5px;"
                title="$[/model/standardLineDescription/records/record/@description]">
            <a:grid id="EXP5110_standard_lines_grid" autoAdjustHeight="true"
                    bindTarget="EXP5110_exp_report_standard_line_ds" gridskin="gridskin" height="600" marginWidth="3"
                    navBar="true" showRowNumber="false">
                <a:toolBar>
                    <#list header_info as head>
                        <#if head.reqRequiredFlag == 'Y'>
                            <a:button click="EXP5110_standardLineCopy"
                                      icon="$[/request/@context_path]/resources/images/aurora/hap/copy.png"
                                      skin="custom" text="fnd_calendar.copy"/>
                        <#else>
                            <a:button click="EXP5110_standardLineAdd" type="add"/>
                        </#if>
                    </#list>
                    <#list header_info as head>
                        <#if head.relationMethodCode == 'LINE'>
                            <a:button click="EXP5110_standardCreateFromReq"
                                      icon="$[/request/@context_path]/resources/images/aurora/hap/source-doc.png"
                                      skin="custom" text="exp_report_create.fromreq"/>
                        </#if>
                    </#list>
                    <a:button type="delete"/>
                </a:toolBar>
                <a:columns>
                    <a:column name="more" align="center" lock="true" renderer="EXP5110_standardMoreRenderer"
                              width="50"/>
                    <a:column name="lineNumber" align="center" lock="true" prompt="con_contract_element.line_number"
                              width="50"/>
                    <a:column name="description" editor="EXP5110_standard_ta" prompt="exp_report_headers.description"
                              width="150"/>
                    <a:column name="moExpenseTypeName" align="center" editor="EXP5110_standard_cb"
                              prompt="exp_report_lines.expense_type_id" width="120"/>
                    <!-- Modified by Arthur.Chen Date:2017-09-20 19:42:31 1.修改moExpenseItemName字段的编辑器为combobox模式   -->
                    <a:column name="moExpenseItemName" align="center" editor="EXP5110_standard_cb"
                              prompt="exp_report_lines.expense_item_id" width="120"/>
                    <a:column name="placeName" align="center" editor="EXP5110_standard_lov"
                              prompt="exp_report_lines.place_id" width="100"/>
                    <a:column name="dateFrom" align="center" editor="EXP5110_standard_dp"
                              prompt="exp_report_lines.date_from" renderer="Aurora.formatDate" width="100"/>
                    <a:column name="dateTo" align="center" editor="EXP5110_standard_dp"
                              prompt="exp_report_lines.date_to" renderer="Aurora.formatDate" width="100"/>
                    <a:column name="businessCurrencyName" align="center" editor="EXP5110_standard_cb"
                              prompt="business_currency_name" width="100"/>
                    <a:column name="businessPrice" align="right" editorFunction="EXP5110_standardBizPriceEditorFun"
                              renderer="Aurora.formatMoney" width="100"/>
                    <a:column name="primaryQuantity" align="right" editor="EXP5110_standard_nf_0"
                              prompt="acp_invoice_line.quantity" width="100"/>
                    <a:column name="businessAmount" align="right" prompt="business_amount" renderer="Aurora.formatMoney"
                              width="100"/>
                    <a:column name="reportFunctionalAmount" align="right"
                              prompt="exp_report_lines.requisition_functional_amount" renderer="Aurora.formatMoney"
                              width="100"/>
                    <a:column name="periodName" align="center" editor="EXP5110_standard_lov"
                              prompt="exp_report_lines.period_name" width="100"/>
                    <a:column name="unitName" align="center" editor="EXP5110_standard_lov"
                              prompt="acp_acp_requisition_hds.unit_id" width="120"/>
                    <a:column name="accEntityName" align="center" editor="EXP5110_standard_lov"
                              prompt="csh_offer_format.acc_entity" width="120"/>
                    <a:column name="respCenterName" align="center" editor="EXP5110_standard_lov"
                              prompt="acp_invoice_line.responsibility_center_id" width="120"/>
                    <a:column name="invoiceCategoryName" align="center" editorFunction="EXP5110_standardTaxEditorFun"
                              width="100"/>
                    <a:column name="invoiceCode" align="center" editorFunction="EXP5110_standardTaxEditorFun"
                              width="100"/>
                    <a:column name="invoiceNumber" align="center" editorFunction="EXP5110_standardTaxEditorFun"
                              width="100"/>
                    <a:column name="invoiceDate" align="center" editorFunction="EXP5110_standardTaxEditorFun"
                              renderer="Aurora.formatDate" width="100"/>
                    <a:column name="invoiceAmount" align="right" editorFunction="EXP5110_standardTaxEditorFun"
                              renderer="Aurora.formatMoney" width="100"/>
                    <a:column name="taxTypeName" align="center" editorFunction="EXP5110_standardTaxEditorFun"
                              width="100"/>
                    <a:column name="taxAmount" align="right" editorFunction="EXP5110_standardTaxEditorFun"
                              renderer="Aurora.formatMoney" width="100"/>
                    <a:column name="invoiceInfo" align="center" renderer="EXP5110_standardLineInvoiceInfoRenderer"
                              width="120"/>
                    <#list standard_line_dimensions as line_dimension>
                        <#if line_dimension??>
                            <a:column name="${line_dimension.displayField}" editor="EXP5110_standard_lov" align="center"
                                      width="120" prompt="${line_dimension.dimensionName}"/>
                        </#if>
                    </#list>
                    <#list standard_line_objects as line_object>
                        <#if line_object??>
                            <a:column name="${line_object.displayField}" editor="EXP5110_standard_lov" align="center"
                                      width="120" prompt="${line_object.moExpObjTypeName}"/>
                        </#if>
                    </#list>
                </a:columns>
                <a:editors>
                    <a:numberField id="EXP5110_standard_nf"/>
                    <a:textField id="EXP5110_standard_tf"/>
                    <a:textArea id="EXP5110_standard_ta"/>
                    <a:lov id="EXP5110_standard_lov"/>
                    <a:comboBox id="EXP5110_standard_cb"/>
                    <a:datePicker id="EXP5110_standard_dp"/>
                    <a:numberField id="EXP5110_standard_nf_0" allowDecimals="false" allowNegative="false"/>
                    <a:numberField id="EXP5110_standard_nf_1" allowDecimals="true" decimalPrecision="1"/>
                    <a:numberField id="EXP5110_standard_nf_2" allowDecimals="true" decimalPrecision="2"/>
                    <a:numberField id="EXP5110_standard_nf_3" allowDecimals="true" decimalPrecision="3"/>
                    <a:numberField id="EXP5110_standard_nf_4" allowDecimals="true" decimalPrecision="4"/>
                    <a:numberField id="EXP5110_standard_nf_5" allowDecimals="true" decimalPrecision="5"/>
                    <a:numberField id="EXP5110_standard_nf_6" allowDecimals="true" decimalPrecision="6"/>
                </a:editors>
                <a:events>
                    <a:event name="cellclick" handler="EXP5110_onStandardLineCellClickFun"/>
                </a:events>
            </a:grid>
        </a:form>
    </a:view>
</a:screen>
