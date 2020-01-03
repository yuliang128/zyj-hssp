<?xml version="1.0" encoding="UTF-8"?>
<a:screen xmlns:c="aurora.application.action" xmlns:p="uncertain.proc"
          xmlns:a="http://www.aurora-framework.org/application" xmlns:s="aurora.plugin.script" trace="true">
    <a:view>
        <a:link id="EXP5110_exp_report_maintain_standard_lines_extend_link"
                url="$[/request/@context_path]/expm/EXP5110/exp_report_maintain_standard_lines_view_extend.screen"/>
        <a:link id="EXP5110_exp_report_maintain_more_invoice_standard_link"
                url="$[/request/@context_path]/expm/EXP5110/exp_report_view_more_invoice.screen"/>
        <a:link id="EXP5110_exp_report_maintain_vat_invoice_more_tax_link"
                url="$[/request/@context_path]/expm/EXP5110/exp_report_view_vat_invoice_more_tax.screen"/>
        <a:link id="EXP5110_exp_report_maintain_cancel_report_realation_link"
                model="db.vat_invoice_relation_pkg.cancel_report_realation" modelaction="update"/>
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
                        data['biz2payExchangeTypeName'] = headerRecord.get('pay2funExchangeTypeName');
                        data['biz2pay_exchange_type'] = headerRecord.get('pay2funExchangeType');
                        data['biz2payExchangeRate'] = 1; // Modify Tagin/2018.04.02 默认业务币种等于支付币种所以汇率为1
                        data['paymentCurrencyName'] = headerRecord.get('paymentCurrencyName');
                        data['paymentCurrencyCode'] = headerRecord.get('paymentCurrencyCode');
                        data['pay2funExchangeTypeName'] = headerRecord.get('pay2funExchangeTypeName');
                        data['pay2funExchangeType'] = headerRecord.get('pay2funExchangeType');
                        data['pay2funExchangeRate'] = headerRecord.get('pay2funExchangeRate');
                        data['managementCurrencyName'] = headerRecord.get('managementCurrencyName');
                        data['managementCurrencyCode'] = headerRecord.get('managementCurrencyCode');
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
                        //modified by ll 20180305
                        // $au('EXP5110_standard_expense_type_default_ds').setQueryParameter('companyId', headerRecord.get('companyId'));
                        // $au('EXP5110_standard_expense_type_default_ds').query();
                        var expenseTypeDefaultDs = $au('EXP5110_standard_expense_type_default_ds').getAt(0);
                        if (expenseTypeDefaultDs) {
                            data['moExpenseTypeId'] = expenseTypeDefaultDs.get('moExpenseTypeId');
                            data['moExpenseTypeName'] = expenseTypeDefaultDs.get('moExpenseTypeName');
                        }
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
                        record.getField('splittedTaxAmount').setRequired(true); //发票金额
                    } else if (value == 'VAT_NORMAL' || value == 'VAT_ELECTRONIC_NORMAL') {
                        record.getField('invoiceCode').setRequired(true);
                        record.getField('invoiceNumber').setRequired(true);
                        record.getField('invoiceDate').setRequired(true);
                        record.getField('invoiceAmount').setRequired(true);
                    } else if (value == 'OTHER' || value == undefined || value == '') {
                        //Modifiied by caoke/2018-05-30 其他发票也比输
                        // record.getField('invoiceCode').setRequired(false);
                        // record.getField('invoiceNumber').setRequired(false);
                        record.getField('invoiceCode').setRequired(true);
                        record.getField('invoiceNumber').setRequired(true);
                        record.getField('invoiceDate').setRequired(false);
                        record.getField('invoiceAmount').setRequired(true);
                        record.getField('taxTypeName').setRequired(false);
                        record.getField('taxAmount').setRequired(false); //发票金额
                        record.getField('splittedTaxAmount').setRequired(false); //发票金额
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
                        record.set('invoiceNumber', '');
                        record.set('invoiceDate', '');
                        record.set('invoiceAmount', '');
                        record.set('taxTypeName', '');
                        record.set('taxTypeId', '');
                        record.set('taxTypeRate', '');
                        record.set('taxAmount', '');
                        record.set('splittedTaxAmount', '');
                        record.set('relationFlag', '');
                    } else if (value == '' || value == undefined) {
                        record.set('invoiceId', '');
                        record.set('invoiceLineId', '');
                        record.set('invoiceCategoryName', '');
                        record.set('invoiceCategoryId', '');
                        record.set('invoiceCode', '');
                        record.set('invoiceNumber', '');
                        record.set('invoiceDate', '');
                        record.set('invoiceAmount', '');
                        record.set('taxTypeName', '');
                        record.set('taxTypeId', '');
                        record.set('taxTypeRate', '');
                        record.set('taxAmount', '');
                        record.set('splittedTaxAmount', '');
                        record.set('invoiceType', '');
                        record.set('invoiceSource', '');
                        record.set('relationFlag', '');
                    }
                };

                // 3.0 增值税发票相关字段显示/隐藏
                this.show = function (value, grid) {
                    if (value == 'Y') {
                        // Modify Tagin/2018.01.22 增加判断 若页面元素中更多发票/更多税额标识存在为Y则启用更多关联链接
                        if ('$[/model/standardLineElement/record/@moreInvoiceFlag]' == 'Y' || '$[/model/standardLineElement/record/@moreTaxFlag]' == 'Y') {
                            grid.showColumn('invoiceInfo');
                        } else {
                            grid.hideColumn('invoiceInfo');
                        }
                        if ('$[/model/standardLineElement/record/@invoiceFlag]' == 'N') {
                            grid.hideColumn('invoiceCategoryName');
                            grid.hideColumn('invoiceCode');
                            grid.hideColumn('invoiceNumber');
                            grid.hideColumn('invoiceDate');
                            grid.hideColumn('invoiceAmount');
                            grid.hideColumn('cancelInvoice');
                        } else {
                            grid.showColumn('invoiceCategoryName');
                            grid.showColumn('invoiceCode');
                            grid.showColumn('invoiceNumber');
                            grid.showColumn('invoiceDate');
                            grid.showColumn('invoiceAmount');
                            grid.showColumn('cancelInvoice');
                        }
                        if ('$[/model/standardLineElement/record/@taxFlag]' == 'N') {
                            grid.hideColumn('taxTypeName');
                            grid.hideColumn('taxAmount');
                            grid.hideColumn('splittedTaxAmount');
                        } else {
                            grid.showColumn('taxTypeName');
                            grid.showColumn('taxAmount');
                            grid.showColumn('splittedTaxAmount');
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
                        grid.hideColumn('splittedTaxAmount');
                        grid.hideColumn('cancelInvoice');
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
                            }
                            return 'EXP5110_standard_nf_2';
                        }
                    } else if (name == 'splittedTaxAmount') {
                        // Modify Tagin/2018.04.17 存在只录税率及税额（只拆分税金不关联发票）
                        if ((invoiceRow <= 1 && invoiceSource == 'MANUAL') || (!record.get('invoiceCode') && invoiceRow <= 1)) {
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
                } else if (name == 'accEntityId') {
                    //核算实体边更清除责任中心
                    record.set('respCenterName', '');
                    record.set('respCenterId', '');
                } else if (name == 'primaryQuantity') {
                    record.set('businessAmount', hecUtil.calAmount(record.get('businessPrice'), record.get('primaryQuantity'), record.get('businessCurrencyPrecision')));
                    record.set('paymentAmount', hecUtil.calAmount(record.get('paymentPrice'), record.get('primaryQuantity'), record.get('paymentCurrencyPrecision')));
                    record.set('managementAmount', hecUtil.calAmount(record.get('managementPrice'), record.get('primaryQuantity'), record.get('managementCurrencyPrecision')));
                    record.set('reportFunctionalAmount', hecUtil.calExchangeAmount(record.get('paymentAmount'), record.get('pay2funExchangeRate'), record.get('functionalCurrencyPrecision')));
                } else if (name == 'businessPrice') {
                    record.set('businessAmount', hecUtil.calAmount(record.get('businessPrice'), record.get('primaryQuantity'), record.get('businessCurrencyPrecision')));
                    record.set('paymentPrice', hecUtil.calExchangeAmount(record.get('businessPrice'), record.get('biz2payExchangeRate'), record.get('paymentCurrencyPrecision')));
                } else if (name == 'paymentPrice') {
                    record.set('paymentAmount', hecUtil.calAmount(record.get('paymentPrice'), record.get('primaryQuantity'), record.get('paymentCurrencyPrecision')));
                    record.set('managementPrice', hecUtil.calExchangeAmount(record.get('paymentPrice'), record.get('pay2magExchangeRate'), record.get('managementCurrencyPrecision')));
                } else if (name == 'managementPrice') {
                    record.set('managementAmount', hecUtil.calAmount(record.get('managementPrice'), record.get('primaryQuantity'), record.get('managementCurrencyPrecision')));
                } else if (name == 'paymentAmount') {
                    record.set('reportFunctionalAmount', hecUtil.calExchangeAmount(record.get('paymentAmount'), record.get('pay2funExchangeRate'), record.get('functionalCurrencyPrecision')));
                } else if (name == 'moExpenseTypeId') {
                    record.set('moExpenseItemName', '');
                    record.set('moExpenseItemId', '');
                } else if (name == 'moExpenseItemId') {
                    record.set('budgetItemId', '');
                    record.set('budgetItemName', '');
                } else if (name == 'biz2payExchangeRate') {
                    // Modify Tagin/2018.05.08 当业务币种-支付币种 汇率变化时调整支付单价
                    record.set('paymentPrice', hecUtil.calExchangeAmount(record.get('businessPrice'), value, record.get('paymentCurrencyPrecision')));
                } else if (name == 'biz2payExchangeRate') {

                    // Modify Tagin/2018.05.08 当支付币种-本位币 汇率变化时调整本位币金额
                    record.set('reportFunctionalAmount', hecUtil.calExchangeAmount(record.get('paymentAmount'), value, record.get('functionalCurrencyPrecision')));

                } else if (name == 'invoiceCategoryId') {
                    //add by caoke/2018-05-30 取消关联
                    if (record.get('invoiceId')) {
                        EXP5110_cancelLineRefInvoice(index);
                    }

                } else if (name == 'taxAmount') {
                    //税金更新后，自动调整拆分税金金额。同时设置manual_splitted_tax_flag为N
                    record.set('splittedTaxAmount', record.get('taxAmount'));
                    record.set('reportAmount', record.get('taxAmount'));
                    record.set('manualSplittedTaxFlag', 'N');
                } else if (name == 'splittedTaxAmount') {
                    //拆分税金更新后，设置manual_splitted_tax_flag
                    record.set('manualSplittedTaxFlag', 'Y');
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
                } else if (name == 'businessCurrencyCode') {
                    // Modify Tagin/2018.04.02 增加业务币种-支付币种、支付币种-本位币获取汇率逻辑
                    var dataSet = $au('EXP5110_standardDocExchangeRate_ds');
                    dataSet.setQueryParameter('accEntityId', record.get('accEntityId'));
                    dataSet.setQueryParameter('businessCurrencyCode', record.get('businessCurrencyCode'));
                    dataSet.setQueryParameter('paymentCurrencyCode', record.get('paymentCurrencyCode'));
                    dataSet.query();
                } else if (name == 'invoiceAmount') {
                    // Modify Tagin/2018.05.22 发票价税合计变更时自动计算税额/实际报销金额
                    if (record.get('invoiceSource') == 'MANUAL') {
                        if (record.get('taxTypeRate') && value) {
                            //Modified by caoke/2018-05-30 计算价内税和价外税
                            // record.set('taxAmount', hecUtil.calAmount(mul(record.get('taxTypeRate'), value), 1, record.get('businessCurrencyPrecision')));
                            if (record.get('autoCalculationFlag') == 'Y') {
                                record.set('taxAmount', hecUtil.calTaxAmount(record.get('invoiceAmount'), record.get('taxTypeRate'), record.get('taxCategory'), record.get('businessCurrencyPrecision')));
                            } else {
                                record.set('taxAmount', '');
                            }
                        }
                        record.set('reportAmount', record.get('taxAmount') ? record.get('taxAmount') : value);
                    }
                } else if (name == 'taxTypeRate') {
                    // Modify Tagin/2018.05.22 发票税率变更时自动计算税额/实际报销金额
                    if (record.get('invoiceSource') == 'MANUAL') {
                        if (record.get('invoiceAmount') && value) {
                            //价税合计金额算税额方案为 ： 金额  * (税率 / (1 + 税率))
                            //Modified by caoke/2018-05-30 计算价内税和价外税
                            // record.set('taxAmount', hecUtil.calAmount(mul(value, record.get('invoiceAmount')), 1, record.get('businessCurrencyPrecision')));
                            if (record.get('autoCalculationFlag') == 'Y') {
                                record.set('taxAmount', hecUtil.calTaxAmount(record.get('invoiceAmount'), record.get('taxTypeRate'), record.get('taxCategory'), record.get('businessCurrencyPrecision')));
                            } else {
                                record.set('taxAmount', '');
                            }
                        }
                        record.set('reportAmount', record.get('taxAmount') ? record.get('taxAmount') : record.get('invoiceAmount'));
                    }
                }
                //数据变更获取费用政策
                if (name == 'accEntityId' || name == 'companyId' || name == 'employeeId' || name == 'moExpenseItemId' || name == 'placeTypeId' || name == 'placeId' || name == 'businessCurrencyCode' || name == 'positionId') {
                    EXP5110_onStandardLinePolicyInit(record);
                }
                //ADD CHQ 更新头上总金额
                if (name == 'businessAmount') {
                    EXP5110_onHeadAmountChanged();
                }

            }

            function EXPT5110_onStandardLineLoadFun(dataSet) {
                var records = dataSet.getAll();
                for (var i = 0; i < records.length; i++) {
                    EXP5110_onStandardLinePolicyInit(records[i]);
                }
            }

            function EXP5110_onStandardLinePolicyInit(record) {
                var headerRecord = $au('EXP5110_exp_report_header_ds').getAt(0);
                Aurora.request({
                    url: $au('EXP5110_get_policy_info_link').getUrl(),
                    para: {
                        companyId: record.get('companyId'),
                        employeeId: record.get('employeeId'),
                        accEntityId: record.get('accEntityId'),
                        placeId: record.get('placeId'),
                        placeTypeId: record.get('placeTypeId'),
                        positionId: record.get('positionId'),
                        unitId: record.get('unitId'),
                        vehicleCode: null,
                        reportDate: headerRecord.get('reportDate'),
                        docCategory: 'EXP_REP',
                        docTypeId: headerRecord.get('moExpReportTypeId'),
                        moExpenseTypeId: record.get('moExpenseTypeId'),
                        moExpenseItemId: record.get('moExpenseItemId')
                    },
                    success: function (resp) {
                        if (resp && resp.result) {
                            var policyData = resp.result;
                            if (policyData.count == 1) {
                                if (policyData.defaultFlag == 'Y' && record.isNew) {
                                    //默认标志，自动带出金额
                                    var hecUtil = new HecUtil();
                                    var expenseStandard = policyData.expenseStandard;
                                    //自动倒算当前业务币种对应的金额，计算方式为：
                                    //管理币种金额  * (1 / (付款币种 -》 管理币种汇率)) =》 付款币种金额
                                    //付款币种金额  * (1 / (业务币种 -》 付款币种汇率)) =》 业务币种金额
                                    var paymentExpenseStandard = hecUtil.calExchangeAmount(expenseStandard, 1 / record.get('pay2magExchangeRate'), record.get('paymentCurrencyPrecision'));
                                    var businessExpenseStandard = hecUtil.calExchangeAmount(paymentExpenseStandard, 1 / record.get('biz2payExchangeRate'), record.get('businessCurrencyPrecision'));
                                    record.set('businessPrice', businessExpenseStandard);
                                }
                                //设置管理币种的标准、上下限信息、是否可修改、是否可提交信息
                                record.set('upperLimit', policyData.upperLimit);
                                record.set('lowerLimit', policyData.lowerLimit);
                                record.set('expenseStandard', policyData.expenseStandard);
                                record.set('changeableFlag', policyData.changeableFlag);
                                record.set('commitFlag', policyData.commitFlag);
                            } else {
                                //没有读取到则清空数据add by duanjian2017.12.21
                                //record.set('businessPrice', '');
                                record.set('changeableFlag', 'Y');
                            }
                        }
                    }
                });
            }

            function EXP5110_onStandardLineCellClickFun(grid, row, name, record) {
                if (name == 'unitName') {
                    record.getField('unitName').setLovPara('companyId', record.get('companyId'));
                    // 新增核算主体Id查询条件 caoke 2018-1-25 14:38:04
                    record.getField('unitName').setLovPara('accEntityId', record.get('accEntityId'));
                } else if (name == 'positionName') {
                    record.getField('positionName').setLovPara('companyId', record.get('companyId'));
                    record.getField('positionName').setLovPara('employeeId', record.get('employeeId'));
                } else if (name == 'employeeName') {
                    record.getField('employeeName').setLovPara('companyId', record.get('companyId'));
                    //新增核算主体查询条件
                    record.getField('employeeName').setLovPara('accEntityId', record.get('accEntityId'));
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
                     1.动态获取mo_expense_item_name字段的options
                     */
                    $au('EXP5110_standard_items_ds').setQueryParameter('moExpenseTypeId', record.get('moExpenseTypeId'));
                    $au('EXP5110_standard_items_ds').setQueryParameter('companyId', record.get('companyId'));
                    $au('EXP5110_standard_items_ds').setQueryParameter('moExpReportTypeId', '$[/model/headerInfo/record/@moExpReportTypeId]');
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
                if (/dimension/.test(name)) {
                    // Modify Tagin/2018.01.22 增加维度设置查询条件
                    var id = '';
                    var level = record.get('companyLevel');
                    if (level == 'BUDGET') {
                        id = record.get('bgtEntityId');
                    } else if (level == 'ACCOUNTING') {
                        id = record.get('accEntityId');
                    } else if (level == 'MANAGEMENT') {
                        id = '$[/session/@companyId]';
                    }
                    record.getField(name).setLovPara('companyId', id);
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
                    url: $au('EXP5110_exp_report_maintain_create_from_req_link').getUrl() + '?moExpReportTypeId=$[/model/headerInfo/record/@moExpReportTypeId]&reportPageElementCode=STANDARD&paymentCurrencyCode=' + $au('EXP5110_exp_report_header_ds').getAt(0).get('paymentCurrencyCode') + '&monopolizeFlag=' + monopolizeFlag,
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
                var vatCount = !record.get('vatcount') ? 0 : record.get('vatcount');
                var taxCount = !record.get('taxcount') ? 0 : record.get('taxcount');
                var index = $au('EXP5110_exp_report_standard_line_ds').indexOf(record);
                var vatContent = '';
                var taxContent = '';
                if ('$[/model/standardLineElement/record/@moreInvoiceFlag]' == 'Y') {
                    if (vatCount <= 1) {
                        if (!record.isNew) {
                            vatContent = '<a href="javascript:EXP5110_openMoreInvoiceWindow(\'' + index + '\')">$[l:exp_mo_req_types.invoice_flag]</a>';
                        }
                    } else {
                        vatContent = '<a href="javascript:EXP5110_openMoreInvoiceWindow(\'' + index + '\')"><font color="green">$[l:exp_mo_req_types.invoice_flag]</font></a>';
                    }
                }
                if ('$[/model/standardLineElement/record/@moreTaxFlag]' == 'Y') {
                    if (record.get('relationId')) { // Modify Tagin/2018.05.22 若不存在发票关联ID，则为关联多个发票税额不显示【进入更多发票查看税额】
                        if (taxCount <= 1) {
                            if (!record.isNew) {
                                taxContent = '<a href="javascript:EXP5110_standardMoreTaxFunc(' + index + ')">$[l:vat_invoice_lines.tax_rate]</a>';
                            }
                        } else {
                            taxContent = '<a href="javascript:EXP5110_standardMoreTaxFunc(' + index + ')"><font color="green">$[l:vat_invoice_lines.tax_rate]</font></a>';
                        }
                    }
                }
                return !vatContent ? (!taxContent ? '' : taxContent) : (!taxContent ? vatContent : vatContent + ' / ' + taxContent);
            }

            function EXP5110_openMoreInvoiceWindow(index) {
                var record = $au('EXP5110_exp_report_standard_line_ds').getAt(index);
                var headerId = record.get('expReportHeaderId') || '';
                var lineId = record.get('expReportLineId') || '';
                var distId = record.get('expReportDistId') || '';
                new Aurora.Window({
                    url: $au('EXP5110_exp_report_maintain_more_invoice_standard_link').getUrl() + '?expReportHeaderId=' + headerId + '&expReportLineId=' + lineId + '&expReportDistId=' + distId,
                    id: 'EXP5110_exp_report_maintain_more_invoice_standard_window',
                    title: '$[l:more_invoice]',
                    side: 'right',
                    width: 1020
                }).on('close', function () {
                    $au('EXP5110_exp_report_standard_line_ds').query();
                });
            }

            function EXP5110_standardMoreTaxFunc(index) {
                var record = $au('EXP5110_exp_report_standard_line_ds').getAt(index);
                var invoiceLineId = record.get('invoiceLineId') || '';
                var invoiceId = record.get('invoiceId') || '';
                var relationId = record.get('relationId') || '';
                var lineId = record.get('expReportLineId') || '';
                new Aurora.Window({
                    id: 'EXP5110_exp_report_maintain_vat_invoice_more_tax_window',
                    url: $au('EXP5110_exp_report_maintain_vat_invoice_more_tax_link').getUrl() + '?invoiceLineId=' + invoiceLineId + '&invoiceId=' + invoiceId + '&relationId=' + relationId + '&expReportLineId=' + lineId,
                    side: 'right',
                    width: 1020
                }).on('close', function () {
                    $au('EXP5110_exp_report_standard_line_ds').query();
                });
            }

            function EXP5110_invoiceItemValidator(record, name, value) {
                if (record.get('invoiceLineId') && !value) {
                    //发票项目允许为空，财务人员在审核时选择
                    //return '发票项目必输!';
                }
                return true;
            }

            function EXP5110_invoiceUsedeValidator(record, name, value) {
                if (record.get('invoiceLineId') && !value) {
                    //发票用途允许为空，财务人员在审核时选择
                    //return '发票用途必输!';
                }
                return true;
            }

            function EXP5110_standardMoreRenderer(value, record, name) {
                if (name == 'more' && record.get('expReportLineId')) {
                    var content = '>>';
                    return '<a href="javascript:EXP5110_openStandardMoreWindow(\'' + record.get('expReportHeaderId') + '\',\'' + record.get('expReportLineId') + '\')">' + content + '</a>';
                }
            }

            function EXP5110_openStandardMoreWindow(headerId, lineId) {
                var typeId = '$[/model/header_info/records/record/@moExpReportTypeId]';
                var invoiceFlag = '$[/model/standardLineElement/records/record/@invoiceFlag]';
                var taxFlag = '$[/model/standardLineElement/records/record/@taxFlag]';
                var companyId = '$[/model/header_info/records/record/@companyId]';
                var bgtEntityId = '$[/model/header_info/records/record/@bgtEntityId]';
                var accEntityId = '$[/model/header_info/records/record/@accEntityId]';
                new Aurora.Window({
                    title: '$[l:conventional_expense_line_details]',
                    id: 'EXP5110_exp_report_maintain_standard_lines_extend_window',
                    url: $au('EXP5110_exp_report_maintain_standard_lines_extend_link').getUrl() + '?moExpReportTypeId=' + typeId + '&expReportHeaderId=' + headerId + '&expReportLineId=' + lineId + '&invoiceFlag=' + invoiceFlag + '&taxFlag=' + taxFlag + '&companyId=' + companyId + '&bgtEntityId=' + bgtEntityId + '&accEntityId=' + accEntityId,
                    side: 'right',
                    width: .8
                }).on('close', function () {
                    $au('EXP5110_exp_report_standard_line_ds').query();
                });
            }

            function EXP5110_standardTaxEditorFun(record, name) {
                // Modify Tagin/2018.04.17 调整
                return new EXP5110_maintainInvoice(record).edit(name);
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

            //重新计算头上总金额

            function EXP5110_onHeadAmountChanged() {
                var lineDs = $au('EXP5110_exp_report_standard_line_ds').getAll();
                var lineTotalAmount = 0;
                for (var i = 0; i < lineDs.length; i++) {
                    lineTotalAmount = plus(lineDs[i].get('businessAmount'), lineTotalAmount);
                }
                $au('EXP5110_exp_report_header_ds').getAt(0).set('repTotalAmount', lineTotalAmount);
            }

            function EXP5110_standardCancelVatRenderer(value, record, name) {
                var index = $au('EXP5110_exp_report_standard_line_ds').indexOf(record);
                return '<a href="javascript:EXP5110_cancelLineRefInvoice(' + index + ');">$[l:cancel_invoice]</a>';
            }

            function EXP5110_cancelLineRefInvoice(index) {
                var record = $au('EXP5110_exp_report_standard_line_ds').getAt(index);
                var headerId = record.get('expReportHeaderId') || '';
                var lineId = record.get('expReportLineId') || '';
                if (record.get('vatcount') > 0) {
                    // Modify Tagin/2018.04.16 取消关联增值税发票信息
                    new EXP5110_maintainInvoice().cancel(headerId, lineId, $au('EXP5110_exp_report_standard_line_ds'));
                }
                // Modify Tagin/2018.04.16 清空增值税发票信息
                new EXP5110_maintainInvoice(record).clear();
            }

            function EXP5110_standardInvoiceTypeLoadFunc(dataSet) {
                // Modify Tagin/2018.04.10 增加根据发票代码获取发票类型加载时动态设置单据行的发票类型字段
                var record = dataSet.getAt(0);
                var reportRd = $au('EXP5110_exp_report_standard_line_ds').getCurrentRecord();
                var invoiceType = record.get('invoiceType');
                if (reportRd.get('invoiceCode')) {
                    reportRd.set('invoiceSource', !reportRd.get('invoiceSource') ? 'MANUAL' : reportRd.get('invoiceSource'));
                    reportRd.set('invoiceType', invoiceType);
                } else {
                    reportRd.set('invoiceType', '');
                }
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
                record.set('reportAmount', record.get('reportAmount') ? record.get('reportAmount') : record.get('taxAmount'));
                record.set('splittedTaxAmount', record.get('splittedTaxAmount') ? record.get('splittedTaxAmount') : record.get('taxAmount'));
            }

            //若单据行未关联发票或者关联发票不可抵扣，则无需选择发票用途  Y.duan 2018-8-23 09:25:34

            function EXP5110_InvoiceUsedeFun(record, name) {
                if (name == 'invoiceUsedeName') {
                    var invoiceId = record.get('invoiceId');
                    var invoiceLineId = record.get('invoiceLineId');
                    var invoiceAttributes = record.get('invoiceAttributes');
                    if ((!Ext.isEmpty(invoiceId)) && (invoiceAttributes == 'VAT-SPECIAL')) {
                        return 'EXP5110_standard_lov';
                    } else {
                        record.getMeta().getField('invoiceUsedeName').setRequired(false);
                        return '';
                    }
                }
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
                                var idValue = records[i].get(field);
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
                       queryDataSet="EXP5110_exp_report_header_ds"
                       queryUrl="$[/request/@context_path]/exp/report-line/reportLineQuery?reportPageElementCode=STANDARD&amp;_ban_remind=Y"
                       selectable="false">
                <a:fields>
                    <a:field name="expReportHeaderId"/>
                    <a:field name="expReportLineId"/>
                    <a:field name="lineNumber" prompt="con_contract_element.line_number" readOnly="true"/>
                    <a:field name="reportPageElementCode" prompt="exp_req_page_element.req_page_element_code"
                             readOnly="true"/>
                    <a:field name="description" readOnly="true"/>
                    <a:field name="invoiceAttributes"/>
                    <a:field name="companyName" lovCode="FND_COMPANY_VL_LOV" prompt="bgt_budget_item_mapping.company_id"
                             readOnly="true">
                        <a:mapping>
                            <a:map from="companyName" to="companyName"/>
                            <a:map from="companyId" to="companyId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="companyId" prompt="bgt_budget_item_mapping.company_id" readOnly="true"/>
                    <a:field name="accEntityName" lovCode="GLD_ACCOUNTING_ENTITY_VL_LOV"
                             prompt="csh_offer_format.acc_entity" readOnly="true">
                        <a:mapping>
                            <a:map from="accEntityName" to="accEntityName"/>
                            <a:map from="accEntityId" to="accEntityId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="accEntityId" prompt="csh_offer_format.acc_entity" readOnly="true"/>
                    <a:field name="unitName" lovCode="EXP_ORG_UNITS_VL_LOV" prompt="acp_acp_requisition_hds.unit_id"
                             readOnly="true">
                        <!--添加默认核算主体，默认责任中心，默认预算主体，默认预算中心映射   Y.duan 2017-8-31 19:37:31-->
                        <a:mapping>
                            <a:map from="unitName" to="unitName"/>
                            <a:map from="unitId" to="unitId"/>
                            <a:map from="defaultAccEntityId" to="accEntityId"/>
                            <a:map from="defaultResCenterId" to="respCenterId"/>
                            <a:map from="defaultBgtEntityId" to="bgtEntityId"/>
                            <a:map from="defaultBgtCenterId" to="bgtCenterId"/>
                            <a:map from="defaultAccEntityDisplay" to="accEntityName"/>
                            <a:map from="defaultResCentersDisplay" to="respCenterName"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="unitId" prompt="acp_acp_requisition_hds.unit_id" readOnly="true"/>
                    <a:field name="positionName" lovCode="EXP_ORG_POSITION_VL_LOV"
                             prompt="acp_acp_requisition_hds.position_id" readOnly="true">
                        <a:mapping>
                            <a:map from="positionName" to="positionName"/>
                            <a:map from="positionId" to="positionId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="positionId" prompt="acp_acp_requisition_hds.position_id" readOnly="true"/>
                    <a:field name="employeeName" lovCode="EXP_EMPLOYEE_LOV" prompt="acp_acp_requisition_hds.employee_id"
                             readOnly="true">
                        <a:mapping>
                            <a:map from="name" to="employeeName"/>
                            <a:map from="employeeId" to="employeeId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="employeeId" prompt="acp_acp_requisition_hds.employee_id" readOnly="true"/>
                    <a:field name="respCenterName" lovCode="GLD_RESPONSIBILITY_CENTERS_VL_LOV"
                             prompt="fnd_responsibility_centers.responsibility_center_name" readOnly="true">
                        <a:mapping>
                            <a:map from="responsibilityCenterName" to="respCenterName"/>
                            <a:map from="responsibilityCenterId" to="respCenterId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="respCenterId" prompt="acp_invoice_line.responsibility_center_id" readOnly="true"/>
                    <a:field name="bgtEntityId" prompt="bgt_entities.bgt_entity_name" readOnly="true"/>
                    <a:field name="bgtCenterId" prompt="bgt_centers.bgt_center_name" readOnly="true"/>
                    <a:field name="placeTypeName" prompt="exp_report_lines.place_type_id" readOnly="true"/>
                    <a:field name="placeTypeId"/>
                    <!--重新指定地点查询的lovService 地点类型直接自动带出   Y.duan 2017-8-14 17:13:08-->
                    <a:field name="placeName" lovCode="EXP_POLICY_PLACES_AND_TYPES_VL_LOV" readOnly="true">
                        <a:mapping>
                            <a:map from="placeName" to="placeName"/>
                            <a:map from="placeId" to="placeId"/>
                            <a:map from="placeTypeId" to="placeTypeId"/>
                            <a:map from="placeTypeName" to="placeTypeName"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="placeId"/>
                    <a:field name="budgetItemName"/>
                    <a:field name="budgetItemId"/>
                    <a:field name="dateFrom" prompt="exp_report_lines.date_from" readOnly="true"
                             validator="EXP5110_standardDateValidator"/>
                    <a:field name="dateTo" prompt="exp_report_lines.date_to" readOnly="true"
                             validator="EXP5110_standardDateValidator"/>
                    <a:field name="periodName" lovCode="BGT_PERIODS_LOV" readOnly="true">
                        <a:mapping>
                            <a:map from="periodName" to="periodName"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="moExpenseTypeName" displayField="moExpenseTypeName"
                             options="EXP5110_standard_expense_type_ds" prompt="exp_report_lines.expense_type_id"
                             readOnly="true" returnField="moExpenseTypeId" valueField="moExpenseTypeId"/>
                    <a:field name="moExpenseTypeId"/>
                    <!-- Modified by Arthur.Chen Date:2017-09-20 19:42:31 1.修改mo_expense_item_name字段的编辑器为combobox模式,修改item的options为EXP5110_streamlined_items_ds -->
                    <a:field name="moExpenseItemName" displayField="moExpenseItemName"
                             options="EXP5110_standard_items_ds" prompt="exp_report_lines.expense_item_id"
                             readOnly="true">
                        <a:mapping>
                            <a:map from="moExpenseItemName" to="moExpenseItemName"/>
                            <a:map from="moExpenseItemId" to="moExpenseItemId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="moExpenseItemId"/>
                    <a:field name="primaryQuantity" prompt="acp_invoice_line.quantity" readOnly="true"/>
                    <a:field name="businessCurrencyName" displayField="currency_name" options="EXP5110_currency_ds"
                             prompt="business_currency_name" readOnly="true">
                        <a:mapping>
                            <a:map from="currencyCode" to="businessCurrencyCode"/>
                            <a:map from="precision" to="businessCurrencyPrecision"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="businessCurrencyCode" prompt="exp_report.business_currency_code" readOnly="true"/>
                    <a:field name="biz2payExchangeTypeName"/>
                    <a:field name="biz2payExchangeRate" prompt="exp_report.biz2pay_exchange_rate" readOnly="true"/>
                    <a:field name="businessPrice" prompt="business_price" readOnly="true"/>
                    <a:field name="businessAmount" prompt="business_amount" readOnly="true"/>
                    <a:field name="paymentCurrencyName" prompt="gld_accounting_entities.pay_currency_code"
                             readOnly="true"/>
                    <a:field name="paymentCurrencyCode" prompt="exp_report.payment_currency_code" readOnly="true"/>
                    <a:field name="pay2funExchangeTypeName"/>
                    <a:field name="pay2funExchangeRate" prompt="exp_report.pay2fun_exchange_rate" readOnly="true"/>
                    <a:field name="paymentPrice" prompt="payment_price"/>
                    <a:field name="paymentAmount" prompt="payment_amount"/>
                    <a:field name="managementCurrencyName" prompt="exp_report_headers.currency_code" readOnly="true"/>
                    <a:field name="managementCurrencyCode" prompt="exp_report.management_currency_code"
                             readOnly="true"/>
                    <a:field name="pay2magExchangeTypeName"/>
                    <a:field name="pay2magExchangeRate" prompt="pay2mag_exchange_rate" readOnly="true"/>
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
                    <a:field name="invoiceInfo" prompt="hap_more"/>
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
                            <a:map from="precision" to="precision"/>
                            <a:map from="autoCalculationFlag" to="autoCalculationFlag"/>
                            <a:map from="saleTaxFlag" to="saleTaxFlag"/>
                            <a:map from="taxTypeRate" to="taxTypeRate"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="taxTypeId"/>
                    <a:field name="taxTypeRate"/>
                    <a:field name="manualSplittedTaxFlag" defaultValue="N"/>
                    <a:field name="splittedTaxAmount" prompt="split_tax" validator="EXP5110_standardInvoiceValidator"/>
                    <a:field name="reportAmount"/>
                    <a:field name="more" prompt="&gt;&gt;"/>
                    <!--                    &lt;!&ndash;【报销单审核】新增发票用途字段  Y.duan 2018-8-22 09:06:23&ndash;&gt;-->
                    <!--                    <a:placeHolder id="EXP5110_standardLineAuditFields"/>-->
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
            </a:dataSet>
            <!-- Modify Tagin/2018.05.18 增加报销单行关联发票行逻辑 【发票行可选择性被报销单关联】-->
            <a:dataSet id="EXP5110_standardInvoiceLineDs" autoCreate="true" bindName="invoiceLines"
                       bindTarget="EXP5110_exp_report_standard_line_ds">
                <a:fields>
                    <a:field name="relationId"/>
                    <a:field name="documentCategory" defaultValue="EXP_REPORT"/>
                    <a:field name="invoiceId"/>
                    <a:field name="invoiceLineId"/>
                    <a:field name="documentId"/>
                    <a:field name="documentLineId"/>
                    <a:field name="documentDistId"/>
                    <a:field name="taxAmount"/>
                    <a:field name="splittedTaxAmount"/>
                    <a:field name="reportAmount"/>
                    <a:field name="manualSplittedTaxFlag"/>
                    <a:field name="taxTypeId"/>
                </a:fields>
            </a:dataSet>
        </a:dataSets>
        <a:form marginWidth="3" padding="0" showmargin="0" style="margin-top:5px;"
                title="$[/model/standardLineDescription/records/record/@description]">
            <a:grid id="EXP5110_standard_lines_grid" autoAdjustHeight="true"
                    bindTarget="EXP5110_exp_report_standard_line_ds" height="600" marginWidth="12" navBar="true"
                    showRowNumber="false">
                <a:toolBar>
                    <a:placeHolder id="EXP5110_standardLineAddBtnPlace"/>
                    <a:placeHolder id="EXP5110_standardLineFromReqBtnPlace"/>
                </a:toolBar>
                <a:columns>
                    <a:column name="more" align="center" lock="true" renderer="EXP5110_standardMoreRenderer"
                              width="50"/>
                    <a:column name="lineNumber" align="center" lock="true" prompt="con_contract_element.line_number"
                              width="50"/>
                    <a:column name="description" prompt="exp_report_headers.description" width="150"/>
                    <!-- Modify Tagin/2018.04.12 隐藏公司/岗位员工/地点类型  项目上可选择性启用
                    <a:column name="companyName"  prompt="EXP_REPORT_LINES.COMPANY_ID" width="120"/>
                    -->
                    <!--
                    <a:column name="positionName"  prompt="ACP_ACP_REQUISITION_HDS.POSITION_ID" width="120"/>
                    <a:column name="employeeName"  prompt="ACP_ACP_REQUISITION_HDS.EMPLOYEE_ID" width="120"/>
                    -->
                    <a:column name="moExpenseTypeName" align="center" prompt="exp_report_lines.expense_type_id"
                              width="120"/>
                    <!-- Modified by Arthur.Chen Date:2017-09-20 19:42:31 1.修改mo_expense_item_name字段的编辑器为combobox模式   -->
                    <a:column name="moExpenseItemName" align="center" prompt="exp_report_lines.expense_item_id"
                              width="120"/>
                    <!--
                    <a:column name="placeTypeName" prompt="EXP_REPORT_LINES.PLACE_TYPE_ID" width="120"/>
                    -->
                    <a:column name="placeName" align="center" prompt="exp_report_lines.place_id" width="100"/>
                    <a:column name="dateFrom" align="center" prompt="exp_report_lines.date_from"
                              renderer="Aurora.formatDate" width="100"/>
                    <a:column name="dateTo" align="center" prompt="exp_report_lines.date_to"
                              renderer="Aurora.formatDate" width="100"/>
                    <a:column name="businessCurrencyName" align="center" prompt="business_currency_name" width="100"/>
                    <a:column name="businessPrice" align="right" renderer="Aurora.formatMoney" width="100"/>
                    <a:column name="primaryQuantity" align="right" prompt="acp_invoice_line.quantity" width="100"/>
                    <!--
                    <a:column name="biz2payExchangeTypeName" prompt="BIZ2PAY_EXCHANGE_TYPE_NAME" width="100"/>
                    <a:column name="biz2payExchangeRate" prompt="BIZ2PAY_EXCHANGE_RATE" width="80"/>
                    <a:column name="paymentCurrencyName" prompt="EXP_REQUISITION_HEADERS.PAYMENT_CURRENCY_NAME" width="80"/>
                    <a:column name="pay2funExchangeTypeName" prompt="PAY2FUN_EXCHANGE_TYPE_NAME" width="100"/>
                    <a:column name="pay2funExchangeRate" prompt="PAY2FUN_EXCHANGE_RATE" width="80"/>
                    <a:column name="paymentPrice" prompt="PAYMENT_PRICE" renderer="Aurora.formatMoney" width="80"/>
                    <a:column name="managementCurrencyName" prompt="EXP_REPORT_HEADERS.CURRENCY_CODE" width="100"/>
                    <a:column name="pay2magExchangeTypeName" prompt="PAY2MAG_EXCHANGE_TYPE_NAME" width="100"/>
                    <a:column name="pay2magExchangeRate" prompt="PAY2MAG_EXCHANGE_RATE" width="80"/>
                    <a:column name="managementPrice" prompt="MANAGEMENT_PRICE" renderer="Aurora.formatMoney" width="80"/>
                    -->
                    <a:column name="businessAmount" align="right" prompt="business_amount" renderer="Aurora.formatMoney"
                              width="100"/>
                    <!--
                    <a:column name="paymentAmount" prompt="PAYMENT_AMOUNT" renderer="Aurora.formatMoney" width="80"/>
                    <a:column name="managementAmount" prompt="MANAGEMENT_AMOUNT" renderer="Aurora.formatMoney" width="80"/>
                    -->
                    <a:column name="reportFunctionalAmount" align="right"
                              prompt="exp_report_lines.requisition_functional_amount" renderer="Aurora.formatMoney"
                              width="100"/>
                    <a:column name="periodName" align="center" prompt="exp_report_lines.period_name" width="100"/>
                    <a:column name="unitName" align="center" prompt="acp_acp_requisition_hds.unit_id" width="120"/>
                    <a:column name="accEntityName" align="center" prompt="csh_offer_format.acc_entity" width="120"/>
                    <a:column name="respCenterName" align="center" prompt="acp_invoice_line.responsibility_center_id"
                              width="120"/>
                    <a:column name="invoiceCategoryName" align="center" width="100"/>
                    <a:column name="invoiceCode" align="center" width="100"/>
                    <a:column name="invoiceNumber" align="center" width="100"/>
                    <a:column name="invoiceDate" align="center" renderer="Aurora.formatDate" width="100"/>
                    <a:column name="invoiceAmount" align="right" renderer="Aurora.formatMoney" width="100"/>
                    <a:column name="taxTypeName" align="center" width="100"/>
                    <a:column name="taxAmount" align="right" renderer="Aurora.formatMoney" width="100"/>
                    <!--
                    <a:column name="splittedTaxAmount" align="right"  renderer="Aurora.formatMoney"/>
                    -->
                    <!--                    <a:placeHolder id="EXP5110_standardLineAuditColumns"/>-->
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
                    <a:numberField id="EXP5110_standard_nf_1" allowDecimals="true" allowNegative="false"/>
                    <a:numberField id="EXP5110_standard_nf_2" allowDecimals="true" allowNegative="false"/>
                    <a:numberField id="EXP5110_standard_nf_3" allowDecimals="true" allowNegative="false"/>
                </a:editors>
            </a:grid>
        </a:form>
    </a:view>
</a:screen>
