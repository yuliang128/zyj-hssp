<?xml version="1.0" encoding="UTF-8"?>
<a:screen xmlns:c="aurora.application.action" xmlns:p="uncertain.proc"
          xmlns:a="http://www.aurora-framework.org/application" xmlns:s="aurora.plugin.script" trace="true">
    <a:init-procedure>
        <p:echo/>
    </a:init-procedure>
    <a:view>
        <a:link id="EXP5110_submit_exp_report_link" url="$[/request/@context_path]/exp/report-header/submitReport"/>
        <a:link id="EXP5110_exp_report_main_link" url="$[/request/@context_path]/expm/EXP5110/exp_report_main.screen"/>
        <!--<a:link id="EXP5110_inv_exp_report_main_link" url="$[/request/@context_path]/inv/INV5110/inv_exp_report_main.screen"/>-->
        <a:link id="EXP5110_exp_report_maintain_create_from_req_link" url="$[/request/@context_path]/expm/EXP5110/exp_report_maintain_create_from_req.screen"/>
        <!--&lt;!&ndash;add by caoke/2018-04-24 从费用池创建&ndash;&gt;-->
        <!--<a:link id="EXP5110_exp_report_maintain_create_from_expense_pools_link" url="$[/request/@context_path]/expm/EXP5110/exp_report_maintain_create_from_expense_pools.screen"/>-->
        <!--&lt;!&ndash;added by zealot at 20181121 工作流提交前预览&ndash;&gt;-->
        <!--<a:link id="EXP5110_workflow_preview_before_submit_link" url="$[/request/@context_path]/wfl/WFL1001/wfl_workflow_preview_before_submit.screen"/>-->
        <a:link id="EXP5110_uploadFile_link" url="$[/request/@context_path]/app/APP2030/app_uploadFile.screen"/>
        <a:link id="EXP5110_document_history_view_link" url="$[/request/@context_path]/expm/public/exp_document_history_query.screen"/>
        <!--&lt;!&ndash;  <a:link id="EXP5110_exp_report_from_req_query_link" model="expm.EXP5110.exp_report_from_req_query" modelaction="query"/> &ndash;&gt;-->
        <a:link id="EXP5110_get_policy_info_link" url="$[/request/@context_path]/exp/report-header/getPolicyInfo"/>
        <a:link id="EXP4010_getPeriodName" url="$[/request/@context_path]/exp/report-header/getPeriodName"/>
        <!--<a:link id="EXP5100_exp_report_screen_editor" url="$[/request/@context_path]/engine/screen_parser/complex_screen_editor.screen"/>-->
        <style><![CDATA[
            .reqnumberlov {
                color: #005a78;
                text-decoration: none;
                cursor: pointer;
            }

            .reqnumberlov:hover {
                color: #ff9900;
                text-decoration: none;
            }

            .reqnumberlov input {
                cursor: pointer;
            }

            #report_placeholder {
                height: 45px;
            }

            ]]></style>
        <script><![CDATA[
            var headerRecord;
            var submitFlag = false;
            var bgtIgnoreWarningFlag;
            var vatIgnoreAccEntityFlag;
            var vatIgnoreDateFlag;
            var closeable = false;

            function EXP5110_onMaintainReady() {
                headerRecord = $au('EXP5110_exp_report_header_ds').getAt(0);
                //增加按钮隐藏逻辑
                var expHeaderNumber = headerRecord.get('expReportNumber');
                //var saveDiv = Ext.get('saveExpDiv');
                if (expHeaderNumber == undefined) {
                    document.getElementById("saveExpDiv").style.display = "none";
                    //saveDiv.parent().setStyle('display', 'none');
                } else {
                    //saveDiv.parent().setStyle('display', 'table-cell');
                    document.getElementById("saveExpDiv").style.display = "block"; //显示
                }
                $au('EXP5110_exp_report_header_ds')['on']('beforesubmit', EXP5110_headerBeforeSubmit);
                //对头上的申请单编号进行处理,申请单编号的输入框点击时，打开申请单的只读页面
                if ($A.CmpManager.get('EXP5110_expReqNumberLov')) {
                    //如果存在申请单编号的lov，说明当前单据是头关联申请的，将对应的编辑器设置css，并设置点击事件
                    EXP5110_setExpReqNumberLink();
                }

            }

            function EXP5110_setExpReqNumberLink() {
                var lovDom = Ext.get('EXP5110_expReqNumberLov');
                lovDom['on']('click', function(evt) {
                    var headerRecord = $au('EXP5110_exp_report_header_ds').getAt(0);
                    var moExpReportTypeId = '$[/model/header_info/record/@moExpReportTypeId]';
                    //Modify lcy 增加expRequisitionHeaderId参数
                    var expRequisitionHeaderId = headerRecord.get('expRequisitionHeaderId');
                    var paymentCurrencyCode = $au('EXP5110_exp_report_header_ds').getAt(0).get('paymentCurrencyCode');
                    if (headerRecord.get('expRequisitionHeaderId')) {
                        new Aurora.Window({
                            url: $au('EXP5110_exp_report_maintain_create_from_req_link').getUrl() + '?moExpReportTypeId=' + moExpReportTypeId + '&expRequisitionHeaderId=' + expRequisitionHeaderId + '&paymentCurrencyCode=' + paymentCurrencyCode,
                            id: 'EXP5110_exp_report_maintain_create_from_req_window',
                            title: '$[l:EXP_REPORT_CREATE.FROMREQ]',
                            fullScreen: true
                        });
                    }
                });
            }

            function EXP5110_onPayCurrFocusFun() {
                var payCurrNameField = headerRecord.getField('paymentCurrencyName');
                payCurrNameField.setOptions('EXP5110_currencyDs');
            }

            function EXP5110_saveReport() {
                var headerDs = $au('EXP5110_exp_report_header_ds');
                if (!headerDs.isModified()) {
                    headerDs.data[0].dirty = true;
                }
                $au('EXP5110_exp_report_header_ds').submit();
            }

            function EXP5110_manualSaveReport() {
                submitFlag = false;
                EXP5110_saveReport();
            }

            function EXP5110_manualSubmitReport() {
                submitFlag = true;
                EXP5110_saveReport();
            }

            function EXP5110_submitReport() {
                Aurora.request({
                    lockMessage: '$[l:EXP_REPORT.SUBMITTING]',
                    url: $au('EXP5110_submit_exp_report_link').getUrl(),
                    para: {
                        expReportHeaderId: $au('EXP5110_exp_report_header_ds').getAt(0).get('expReportHeaderId'),
                        bgtIgnoreWarningFlag: (typeof bgtIgnoreWarningFlag) == 'string' ? bgtIgnoreWarningFlag : 'N',
                        vatIgnoreAccEntityFlag: (typeof vatIgnoreAccEntityFlag) == 'string' ? vatIgnoreAccEntityFlag : 'N',
                        vatIgnoreDateFlag: (typeof vatIgnoreDateFlag) == 'string' ? vatIgnoreDateFlag : 'N'
                    },
                    success: function(resp) {
                        if (resp && resp.row) {
                            if (resp.row.resultType) {
                                var errorLevelCode = resp.result.resultType;
                                var errorMessage = resp.result.message;
                                if (errorLevelCode == 'ALLOWED') {
                                    Aurora.showConfirm('$[l:PROMPT]', errorMessage, function() {
                                        bgtIgnoreWarningFlag = 'Y';
                                        EXP5110_submitReport();
                                    }, null);
                                } else if (errorLevelCode == 'BLOCK') {
                                    Aurora.showWarningMessage('$[l:BGT_CHECK_FORBID]', errorMessage, null);
                                }
                            } else if (resp.row.vat_other_acc_entity_code) {
                                var vatOtherAccEntityMsg = resp.row.vat_other_acc_entity_msg;
                                Aurora.showConfirm('$[l:PROMPT]', vatOtherAccEntityMsg, function() {
                                    vatIgnoreAccEntityFlag = 'Y';
                                    EXP5110_submitReport();
                                }, null);
                            } else if (resp.row.vat_over_date_code) {
                                var vatOverDateMsg = resp.row.vat_over_date_msg;
                                Aurora.showConfirm('$[l:PROMPT]', vatOverDateMsg, function() {
                                    vatIgnoreDateFlag = 'Y';
                                    EXP5110_submitReport();
                                }, null);
                            } else {
                                EXP5110_jumpMain();
                            }
                        }
                        EXP5110_jumpMain();
                    }
                });
            }

            function EXP5110_onHeaderSubmitSuccess() {
                //$au('EXP5110_exp_report_header_ds').query();
                var model = '$[/model/header_info/records/record/@expReportHeaderId]';
                console.log(model);
                if (submitFlag) {
                    // 从提交按钮触发的保存
                    EXP5110_submitReport();
                } else {
                    // 从保存按钮触发的保存
                    // NOOP
                }

                var dsHeader = $au('EXP5110_exp_report_header_ds').getAt(0);
                if (dsHeader.get('expReportNumber') != undefined) {
                    //Ext.get('saveExpDiv').parent().setStyle('display', 'table-cell');
                    document.getElementById("saveExpDiv").style.display = "block";
                }
            }

            function EXP5110_jumpMain() {
                if ($au('EXP5110_exp_report_standard_line_ds')) {
                    var expStandardLine = $au('EXP5110_exp_report_standard_line_ds').getAt(0);
                    if (expStandardLine.get('reportPageElementCode') == 'STOCK_LINE' || expStandardLine.get('reportPageElementCode') == 'STOCK_PS_LINE' || expStandardLine.get('reportPageElementCode') == 'STOCK_CR_LINE') {
                        window.location.href = $au('EXP5110_inv_exp_report_main_link').getUrl();
                    } else {
                        window.location.href = $au('EXP5110_exp_report_main_link').getUrl();
                    }
                } else {
                    window.location.href = $au('EXP5110_exp_report_main_link').getUrl();
                }
            }

            function EXP5110_headerBeforeSubmit() {
                var headerDs = $au('EXP5110_exp_report_header_ds');
                if (!headerDs.validate()) {
                    return false;
                } else {
                    var record = headerDs.getAll()[0];
                    var expObjDatas = [];
                    for (field in headerDs.fields) {
                        if (/^#/.test(field)) {
                            //id值
                            var expObjTypeId = field.replace(/^#/, '');
                            var idValue = record.get(field);
                            var nameValue = record.get('^' + field);
                            var expObjData = {
                                moExpObjTypeId: expObjTypeId,
                                moExpenseObjectId: idValue,
                                moExpenseObjectName: nameValue
                            };
                            expObjDatas.push(expObjData);
                        }
                    }
                    record['data']['expenseObjectLines'] = expObjDatas;
                }
            }

            function EXP5110_onReqNumberFocusFun() {
                 $au('EXP5110_exp_report_header_ds').getAt(0).getField('expRequisitionNumber').setLovPara('paymentCurrencyCode', $au('EXP5110_exp_report_header_ds').getAt(0).get('paymentCurrencyCode'));
            }

            function EXP5110_onHeaderUpdateFun(ds, record, name, value, oldValue) {
                //收款对象变化,清空收款方
                if (name == 'payeeCategory') {
                    record.set('payeeId', '');
                    record.set('payeeName', '');
                }
                if (name == 'expRequisitionNumber' && value == "") {
                    record.set('expRequisitionHeaderId', '');
                }
                else if (name == 'reportDate') {
                    var headerRecord = $au('EXP5110_exp_report_header_ds').getAt(0);
                    Aurora.request({
                        lockMessage: '$[l:HAP_WAITING]',
                        url: $au('EXP4010_getPeriodName').getUrl(),
                        para: {
                            accEntityId: headerRecord.get('accEntityId'),
                            paymentCurrencyCode: headerRecord.get('paymentCurrencyCode'),
                            date: headerRecord.get('reportDate')
                        },
                        success: function(res) {
                            var record = res.rows[0];
                            if (record.periodName) {
                                headerRecord.set('periodName', record.periodName);
                                headerRecord.set('pay2funExchangeRate', record.pay2funExchangeRate);
                                headerRecord.set('pay2magExchangeRate', record.pay2magExchangeRate);

                                if (Aurora.CmpManager.get('EXP5110_expReportStandardLineDs')) {
                                    var standard_lineRecord = $au('EXP5110_expReportStandardLineDs').getAll();
                                    for (var i = 0;i <= standard_lineRecord.length - 1;i++) {
                                        standard_lineRecord[i].set('periodName', record.periodName);
                                    }
                                }
                            } else {
                                Aurora.showMessage('$[l:sys_not_null]', '$[l:csh.period_name_of_this_date_not_exists]', null, 300, 100);
                            }
                        },
                        scope: this
                    });
                }
            }

            //Modified lcy 2018/5/9 12:31:12 翻译多语言，报错信息换行显示

            function EXP5110_policyValidator(record, name, value) {
                //Modified lcy 2018/6/6 11:10:12 非调整类报销单不允许输入负数单价
                var adjustmentFlag = '$[/model/header_info/records/record/@adjustmentFlag]';
                var businessPrice = record.get('businessPrice');
                if (adjustmentFlag == 'N' && businessPrice < 0) {
                    return '$[l:EXP_REPORT_HEADER.ADJUSTMENT_REPORT_CHECK]';
                }
                //超政策之后不允许保存
                if (record.get('commitFlag') == 'N') {
                    if (record.get('managementPrice') < (record.get('lowerLimit') ? record.get('lowerLimit') : record.get('managementPrice'))) {
                        return '$[l:current_business_price_lower_limit_value]' + record.get('managementCurrencyName') + record.get('lowerLimit') + '<br>' + '$[l:wrong_field_is]';
                    } else if (record.get('managementPrice') > (record.get('upperLimit') ? record.get('upperLimit') : record.get('managementPrice'))) {
                        return '$[l:current_business_price_upper_limit_value]' + record.get('managementCurrencyName') + record.get('upperLimit') + '<br>' + '$[l:wrong_field_is]';
                    }
                }
                return true;
            }

            function EXP5110_pay2funExchangeRateValidator(record, name, value) {
                //外币支付必须存在对头核算主体本位币的汇率
                if (!value) {
                    var paymentCurrencyName = record.get('paymentCurrencyName');
                    return "$[l:PAY2FUN_EXCHANGE_RATE_NOT_FOUND]" + paymentCurrencyName;
                }
                return true;
            }

            function EXP5110_pay2magExchangeRateValidator(record, name, value) {
                //外币支付必须存在对管理组织本位币的汇率
                if (!value) {
                    var paymentCurrencyName = record.get('paymentCurrencyName');
                    return "$[l:PAY2MAG_EXCHANGE_RATE_NOT_FOUND]" + paymentCurrencyName;
                }
                return true;
            }

            function EXP5110_onLinePolicyInit(record, name) {
                //动态获取行费用数据,在行数据更新、新增的时候触发。
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
                        vehicleCode: record.get('transportation'),
                        reportDate: headerRecord.get('reportDate'),
                        docCategory: 'EXP_REP',
                        docTypeId: headerRecord.get('moExpReportTypeId'),
                        moExpenseTypeId: record.get('moExpenseTypeId'),
                        moExpenseItemId: record.get('moExpenseItemId')
                    },
                    success: function(resp) {
                        if (resp && resp.result) {
                            var policyData = resp.result;
                            if (policyData.count == 1) {
                                //需要进行数学运算的时候需要使用hecUtil中的函数
                                var hecUtil = new HecUtil();
                                //默认标志为Y
                                if (policyData.defaultFlag == 'Y') {
                                    //当行为新增，且默认标志位Y的时候，则自动带出金额
                                    if (record.isNew) {
                                        //获取费用标准
                                        var expenseStandard = policyData.expenseStandard;
                                        //自动倒算当前业务币种对应的金额，计算方式为：
                                        //管理币种金额  * (1 / (付款币种 -》 管理币种汇率)) =》 付款币种金额
                                        //付款币种金额  * (1 / (业务币种 -》 付款币种汇率)) =》 业务币种金额
                                        var paymentExpenseStandard = hecUtil.calExchangeAmount(expenseStandard, 1 / record.get('pay2magExchangeRate'), record.get('paymentCurrencyPrecision'));
                                        var businessExpenseStandard = hecUtil.calExchangeAmount(paymentExpenseStandard, 1 / record.get('biz2payExchangeRate'), record.get('businessCurrencyPrecision'));
                                        //设置金额
                                        record.set('businessPrice', businessExpenseStandard);
                                    }
                                    //当默认标志位Y,且舱位有正常值的时候，用标准带出交通工具和舱位
                                    if (policyData.seatClass != '' && policyData.seatClass != null && policyData.seatClass != 'undefined') {
                                        if (name != 'transportationName' && name != 'transportation') {
                                            record.set('transportation', policyData.transportation);
                                            record.set('transportationName', policyData.transportation_desc);
                                        }
                                        record.set('seatClass', policyData.seatClass);
                                        record.set('seatClassName', policyData.seatClass_desc);
                                    }
                                }
                                //获取到正常的费用数据，则设置管理币种的标准、上下限信息、是否可修改、是否可提交信息。
                                record.set('upperLimit', policyData.upperLimit);
                                record.set('lowerLimit', policyData.lowerLimit);
                                record.set('expenseStandard', policyData.expenseStandard);
                                record.set('changeableFlag', policyData.changeableFlag);
                                record.set('commitFlag', policyData.commitFlag);
                            } else {
                                //没有获取到正常的费用数据则清空数据
                                record.set('upperLimit', '');
                                record.set('lowerLimit', '');
                                record.set('expenseStandard', '');
                                record.set('changeableFlag', '');
                                record.set('commitFlag', '');
                                if (name != 'transportation') {
                                    record.set('transportation', '');
                                    record.set('transportationName', '');
                                }
                                record.set('seatClass', '');
                                record.set('seatClassName', '');
                            }
                        }
                    }
                });
            }


            function EXP5110_return() {
                if (Aurora.CmpManager.get('exp_report_maintain_main_screen')) {
                    $au('exp_report_maintain_main_screen').close();
                }
            }

            function EXP5100_onFocus() {
                var record = $au('EXP5110_exp_report_header_ds').getCurrentRecord();
                var payeeCategory = record.get('payeeCategory');
                if (payeeCategory != null && payeeCategory != '') {
                    var accEntityId = record.get('accEntityId');
                    record.getField('payeeName').setLovPara('payeeCategory', payeeCategory);
                    record.getField('payeeName').setLovPara('accEntityId', accEntityId);
                } else {
                    new Aurora.showMessage('$[l:PROMPT]', '$[l:PLEASE_SELECT_BENEFICIARY]');
                }
            }

            function EXP5110_deleteReport() {
                Aurora.showConfirm('$[l:hap.prompt]', '$[l:exp5110_report_delete]', function(resp) {
                    Aurora.request({
                        url: $au('EXP5110_exp_report_deleteHeader_link').getUrl(),
                        para: {
                            'expReportHeaderId': $au('EXP5110_exp_report_header_ds').getAt(0).get('expReportHeaderId')
                        },
                        success: function() {
                            EXP5110_return();
                            EXP5110_query();
                        },
                        scope: this
                    });
                    resp.close();
                });
            }

            function EXP5110_attachment() {
                var record = $au('EXP5110_exp_report_header_ds').getAt(0);
                new Aurora.Window({
                    url: $au('EXP5110_uploadFile_link').getUrl() + '?tableName=EXP_REPORT_HEADER&tablePkValue=' + record.get('expReportHeaderId')+"&showDelete=true",
                    title: '$[l:atm.upload_attachment]',
                    id: 'check_upload_file_screen',
                    width: 600,
                    height: 400
                });
            }

            function EXP5110_trackReport() {
                var record = $au('EXP5110_exp_report_header_ds').getAt(0);
                new Aurora.Window({
                    url: $au('EXP5110_document_history_view_link').getUrl() + '?documentType=EXP_REPORT&documentId=' + record.get('expReportHeaderId'),
                    id: 'CSH5010_document_history_view_window',
                    side: 'right',
                    width: 1020
                });
            }

            function EXP5110_headerDimensionFunc() {
                // Modify Tagin/2018.01.19 增加维度设置查询条件 this.para.companyLevel
                var record = $au('EXP5110_exp_report_header_ds').getAt(0);
                var level = this.para.companyLevel;
                if (level == 'BUDGET') {
                    id = record.get('bgtEntityId');
                } else if (level == 'ACCOUNTING') {
                    id = record.get('accEntityId');
                } else if (level == 'MANAGEMENT') {
                    id = '$[/session/@companyId]';
                }
                record.getField(this.binder.name).setLovPara('companyId', id);
            }

            //add by caoke/2018-04-24 从费用池创建

            function EXP5110_standardCreateFromExpensePools() {
                // var headerRecord = $au('EXP5110_exp_report_header_ds').getAt(0);
                // var expRequisitionHeaderId = headerRecord.get('expRequisitionHeaderId');
                // if (!expRequisitionHeaderId) {
                //     expRequisitionHeaderId = '';
                // }
                // new Aurora.Window({
                //     url: $au('EXP5110_exp_report_maintain_create_from_expense_pools_link').getUrl() + '?moExpReportTypeId=$[/model/header_info/record/@moExpReportTypeId]&paymentCurrencyCode=' + $au('EXP5110_exp_report_header_ds').getAt(0).get('paymentCurrencyCode') + '&expRequisitionHeaderId=' + expRequisitionHeaderId,
                //     id: 'EXP5110_exp_report_create_from_expense_pools_window',
                //     title: '$[l:EXP_REPORT_CREATE.FROM_EXPENSE_POOLS]',
                //     fullScreen: true
                // });
            }

            //added by zealot at 20181121 单据提交前工作流预览

            function EXP5110_wflPreview() {
                // var headerRecord = $au('EXP5110_exp_report_header_ds').getAt(0);
                // var transactionTypeId = headerRecord.get('moExpReportTypeId');
                // var companyId = headerRecord.get('companyId');
                // var instanceParam = headerRecord.get('expReportHeaderId');
                // if (instanceParam) {
                //     new Aurora.Window({
                //         url: $au('EXP5110_workflow_preview_before_submit_link').getUrl() + '?transactionCategory=EXP_REPORT&transactionTypeId=' + transactionTypeId + '&companyId=' + companyId + '&instanceParam=' + instanceParam,
                //         id: 'EXP5110_workflow_preview_before_submit_window',
                //         title: '$[l:WFL.PREVIEW]',
                //         fullScreen: true
                //     });
                // }
            }

            function EXP5110_calHeadAmount(lineDs, lineTotalAmount) {
                var lineRd = lineDs.getAll();
                for (var i = 0;i < lineRd.length;i++) {
                    var paymentAmount = lineRd[i].get('paymentAmount');
                    paymentAmount = (paymentAmount) ? paymentAmount : 0;
                    lineTotalAmount = plus(paymentAmount, lineTotalAmount);
                }
                return lineTotalAmount;
            }

            //add by caoke/2018-06-01 重新计算头上总金额

            function EXP5110_onHeadAmountChanged() {
                var headerDs = $au('EXP5110_exp_report_header_ds');
                var elementDs = $au('EXP5110_reportPageElementDs');
                var headerRd = headerDs.getAt(0);
                var elementRd = elementDs.getAll();
                var lineTotalAmount = 0;
                for (var i = 0;i < elementRd.length;i++) {
                    var lineDs = elementRd[i].get('lineDs');
                    if (lineDs) {
                        lineTotalAmount = EXP5110_calHeadAmount($au(lineDs), lineTotalAmount);
                    }
                }
                headerRd.set('repTotalAmount', lineTotalAmount);
            }

            Aurora.onReady(EXP5110_onMaintainReady);

            function EXP5110_tagBoxRendererFunc(value, record, displayfield) {
                return '<span><p style="color:red">超预算 80%， 禁止提交预算。</p></span>';
            }

            function EXP5110_tagBoxContentrendererFunc(value, record) {
                html = '<table border="1" bordercolor="#a0c6e5" style="width:100%;border-collapse:collapse;">' + '<tr>' + '<td style="width:20%;align:center"> 政策代码' + '</td>' + '<td style="width:45%;align:center"> 政策名称' + '</td>' + '<td style="width:35%;align:center"> 标准/上限/金额' + '</td>' + '</tr>' + '<tr>' + '<td> PD-1024 ' + '</td>' + '<td> 差旅行程标准 ' + '</td>' + '<td> 500.00 / 1,500.00 / 700.00 ' + '</td>' + '</tr>' + '<tr>' + '<td> PD-1025' + '</td>' + '<td> 住宿标准 ' + '</td>' + '<td> 300.00 / 500.00 / 400.00 ' + '</td>' + '</tr>' + '</table>';
                return html;
            }

            ]]></script>
        <a:dataSets>
            <a:dataSet id="EXP5110_paymentObjectDs" autoQuery="true" loadData="true" queryUrl="$[/request/@context_path]/common/auroraCode/PAYMENT_OBJECT"/>
            <a:dataSet id="EXP5110_currencyDs" autoQuery="true" loadData="true" queryUrl="$[/request/@context_path]/gld-currency/getGldCurrencyOption"/>
            <a:dataSet id="EXP5110_reportPageElementDs">
                <a:datas dataSource="/model/elements/records"/>
            </a:dataSet>
            <a:dataSet id="EXP5110_exp_report_header_ds" autoCreate="true"
                       submitUrl="$[/request/@context_path]/exp/report-header/saveHeader">
                <a:datas dataSource="/model/header_info/records"/>
                <a:fields>
                    <a:field name="expReportHeaderId"/>
                    <a:field name="employeeName" prompt="exp_report_header.employee_name" readOnly="true"
                             required="true"/>
                    <a:field name="employeeId"/>
                    <a:field name="payeeId"/>
                    <a:field name="payeeName" autoComplete="true" lovCode="LOV_GLD_PAYEE" lovAutoQuery="true"
                             prompt="exp_report_header.payee_id" required="true" title="exp_report_header.payee_id">
                        <a:mapping>
                            <a:map from="payeeName" to="payeeName"/>
                            <a:map from="payeeId" to="payeeId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="payeeCategory"/>
                    <a:field name="payeeCategoryName" displayField="code_value_name" options="EXP5110_paymentObjectDs"
                             prompt="exp_report_header.partner_category" returnField="payeeCategory"
                             valueField="code_value"/>
                    <a:field name="expReportNumber" prompt="exp_report_header.exp_report_number" readOnly="true"/>
                    <a:field name="moExpReportTypeName" prompt="exp_report_header.exp_report_type" readOnly="true"
                             required="true"/>
                    <a:field name="moExpReportTypeId"/>
                    <a:field name="reportDate" prompt="exp_report_header.requisition_date" required="true" datatype="date"/>
                    <a:field name="createdByName" prompt="exp_report_header.created_by_name" readOnly="true"
                             required="true"/>
                    <a:field name="paymentCurrencyName" displayField="currencyName"
                             prompt="exp_report_header.payment_currency_name" readOnly="true" required="true"
                             returnField="paymentCurrencyCode" valueField="currencyCode"/>
                    <a:field name="paymentCurrencyCode"/>
                    <a:field name="repTotalAmount" prompt="exp_report_header.sum_amount" readOnly="true"/>
                    <a:field name="unitId"/>
                    <a:field name="unitName"
                             lovCode="EXP_UNIT_AND_POSITION_LOV?employeeId=$[/model/header_info/records/record/@employeeId]&amp;accEntityId=$[/model/header_info/records/record/@accEntityId]"
                             prompt="exp_report_header.unit_id" required="true" title="exp_report_header.unit_id">
                        <a:mapping>
                            <a:map from="unitId" to="unitId"/>
                            <a:map from="unitName" to="unitName"/>
                            <a:map from="positionId" to="positionId"/>
                            <a:map from="defaultBgtEntityId" to="bgtEntityId"/>
                            <a:map from="defaultBgtCenterId" to="bgtCenterId"/>
                            <a:map from="defaultRespCenterId" to="respCenterId"/>
                            <a:map from="defaultAccEntityId" to="accEntityId"/>
                            <a:map from="defaultAccEntityName" to="accEntityName"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="positionName" prompt="exp_report_header.position_name"/>
                    <a:field name="pay2funExchangeTypeName" prompt="exp_report_header.pay2fun_exch_rate_name"/>
                    <a:field name="pay2funExchangeType"/>
                    <a:field name="pay2funExchangeRate" prompt="exp_report_header.pay2fun_exch_rate"
                             validator="EXP5110_pay2funExchangeRateValidator"/>
                    <a:field name="pay2magExchangeTypeName" prompt="exp_report_header.pay2mag_exch_rate_name"/>
                    <a:field name="pay2magExchangeType"/>
                    <a:field name="pay2magExchangeRate" prompt="exp_report_header.pay2mag_exch_rate"
                             validator="EXP5110_pay2magExchangeRateValidator"/>
                    <a:field name="reportStatusName" prompt="exp_report_header.report_status_name" readOnly="true"
                             required="true"/>
                    <a:field name="attachmentNum" prompt="exp_report_header.attachment_num"/>
                    <a:field name="description" prompt="exp_report_header.description"/>
                    <a:field name="accEntityId"/>
                    <a:field name="accEntityName" prompt="exp_report_header.acc_entity_name" readOnly="true"/>
                    <a:field name="accCenterId"/>
                    <a:field name="accCenterName"/>
                    <a:field name="bgtEntityId"/>
                    <a:field name="bgtEntityName"/>
                    <a:field name="bgtCenterId"/>
                    <a:field name="bgtCenterName"/>
                    <a:field name="periodName"/>
                    <a:field name="paymentMethodId"/>
                    <a:field name="paymentMethodName"/>
                    <a:field name="vatInvoiceFlag" checkedValue="Y" defaultValue="Y"
                             prompt="exp_report_header.vat_invoice_flag" uncheckedValue="N"/>
                    <#list header_info as header>
                        <#if header.relationMethodCode == 'HEAD'>
                            <a:field name="expRequisitionNumber" lovUrl="$[/request/@context_path]/expm/EXP5110/exp_report_from_req_header_lov.screen?moExpReportTypeId=${header.moExpReportTypeId}" lovWidth="880" prompt="exp_report_hd.exp_requisition" required="${header.reqRequired}" title="requisition_enquiry">
                                <a:mapping>
                                    <a:map from="expRequisitionNumber" to="expRequisitionNumber"/>
                                    <a:map from="expRequisitionHeaderId" to="expRequisitionHeaderId"/>
                                </a:mapping>
                            </a:field>
                            <a:field name="expRequisitionHeaderId"/>
                        </#if>
                    </#list>
                    <#list headerDimensionFields as DimensionField>
                        <#if DimensionField ??>
                            <a:field name="${DimensionField.returnField}"
                                     defaultValue="${DimensionField.defaultDimValueId}"/>
                            <a:field name="${DimensionField.displayField}"
                                     defaultValue="${DimensionField.defaultDimValueName}"
                                     prompt="${DimensionField.dimensionName}" title="${DimensionField.dimensionName}"
                                     required="true" lovCode="EXP_DIMENSION_VALUE_LOV?companyLevel=${DimensionField.companyLevel}&amp;dimensionId=${DimensionField.dimensionId}">
                                <a:mapping>
                                    <a:map from="dimensionValueId" to="${DimensionField.returnField}"/>
                                    <a:map from="dimensionValueName" to="${DimensionField.displayField}"/>
                                </a:mapping>
                            </a:field>
                            <a:field name="companyLevel" defaultValue="${DimensionField.companyLevel}"/>
                        </#if>
                    </#list>
                    <#list defaultObjectFields as ObjectField>
                        <a:field name="${ObjectField.returnField}" defaultValue="${ObjectField.defaultMoObjectId}"/>
                        <#if ObjectField.expenseObjectMethod == "VALUE_LIST">
                            <a:field name="${ObjectField.displayField}"
                                     lovCode="LOV_OBJECT_VALUE?moExpObjTypeId=${ObjectField.moExpObjTypeId}"
                                     defaultValue="${ObjectField.defaultMoObjectName}"
                                     prompt="${ObjectField.moExpObjTypeName}" title="${ObjectField.moExpObjTypeName}"
                                     required="${ObjectField.requiredFlag}">
                                <a:mapping>
                                    <a:map from="id" to="${ObjectField.returnField}"/>
                                    <a:map from="name" to="${ObjectField.displayField}"/>
                                </a:mapping>
                            </a:field>
                        </#if>
                        <#if ObjectField.expenseObjectMethod == "SQL_TEXT">
                            <a:field name="${ObjectField.displayField}"
                                     lovCode="LOV_OBJECT_SQL_VALUE?sqlQuery=${ObjectField.sqlQuery}"
                                     defaultValue="${ObjectField.defaultMoObjectName}"
                                     prompt="${ObjectField.moExpObjTypeName}" title="${ObjectField.moExpObjTypeName}"
                                     required="${ObjectField.requiredFlag}">
                                <a:mapping>
                                    <a:map from="id" to="${ObjectField.returnField}"/>
                                    <a:map from="name" to="${ObjectField.displayField}"/>
                                </a:mapping>
                            </a:field>
                        </#if>
                    </#list>
                </a:fields>
                <a:events>
                    <a:event name="update" handler="EXP5110_onHeaderUpdateFun"/>
                    <!-- 报销单保存完毕后，进入提交逻辑，通过判断submitFlag标志，确定是否进行提交 -->
                    <a:event name="submitsuccess" handler="EXP5110_onHeaderSubmitSuccess"/>
                </a:events>
            </a:dataSet>
        </a:dataSets>
        <!--add by chao.dai 2019-01-17 将报销单的按钮上移，防止滑动滚动条时按钮跟随滑动-->
        <a:screenTopToolbar
                style="position:fixed;left:1px;right:1px;height:34px;z-index:100;border-bottom:1px solid #C7C7C7;background-color:#FFFFFF;padding-left: 10px;">
            <div class="clearfix" style="width:100%;height:100%;">
                <a:button click="EXP5110_return" style="margin-top:8px;margin-right:10px;float:right;" text="hap_back"/>
                <div id="saveExpDiv">
                    <a:button id="EXP5110_wflPreviewBtn" click="EXP5110_wflPreview"
                              style="margin-top:8px;margin-right:10px;float:right;" text="wfl.preview"/>
                    <a:button id="EXP5110_submitBtn" click="EXP5110_manualSubmitReport"
                              style="margin-top:8px;margin-right:10px;float:right;" text="hap_submit_approval"/>
                    <a:button id="EXP5110_trackBtn" click="EXP5110_trackReport"
                              style="margin-top:8px;margin-right:10px;float:right;" text="hap_tracking_documents"/>
                    <a:button id="EXP5110_attachmentBtn" click="EXP5110_attachment"
                              style="margin-top:8px;margin-right:10px;float:right;"
                              text="hap_attachment_documentation"/>
                    <a:button id="EXP5110_deleteBtn" click="EXP5110_deleteReport"
                              style="margin-top:8px;margin-right:10px;float:right;" text="hap_delete_draft"/>
                </div>
                <a:button id="EXP5110_saveBtn" click="EXP5110_manualSaveReport"
                          style="margin-top:8px;margin-right:10px;float:right;" text="hap_save_draft"/>
                <a:button id="EXP5110_createFromExpensePoolsBtn" click="EXP5110_standardCreateFromExpensePools"
                          style="margin-top:8px;margin-right:10px;float:right;"
                          text="exp_report_create.from_expense_pools"/>
                <span style="margin-top:15px;margin-left：5px;font-size:1.05em;display:block;font-weight: 600;"><![CDATA[$[/model/header_info/records/record/@moExpReportTypeName] ]]></span>
            </div>
        </a:screenTopToolbar>
        <div id="report_placeholder"/>
        <a:screenBody>
            <a:form column="1" title=" ">
                <a:box column="4" style="width:100%;">
                    <a:textField name="expReportNumber" bindTarget="EXP5110_exp_report_header_ds"/>
                    <a:textField name="moExpReportTypeName" bindTarget="EXP5110_exp_report_header_ds"/>
                    <a:textField name="employeeName" bindTarget="EXP5110_exp_report_header_ds"/>
                    <a:datePicker name="reportDate" bindTarget="EXP5110_exp_report_header_ds" renderer="Aurora.formatDate"/>
                    <a:lov name="unitName" bindTarget="EXP5110_exp_report_header_ds"/>
                    <a:textField name="accEntityName" bindTarget="EXP5110_exp_report_header_ds"/>
                    <a:textField name="createdByName" bindTarget="EXP5110_exp_report_header_ds"/>
                    <a:textField name="reportStatusName" bindTarget="EXP5110_exp_report_header_ds"/>
                    <a:numberField name="attachmentNum" allowDecimals="1" allowNegative="false"
                                   bindTarget="EXP5110_exp_report_header_ds"/>
                    <a:textField name="paymentCurrencyName" bindTarget="EXP5110_exp_report_header_ds"/>
                    <a:numberField name="repTotalAmount" allowDecimals="true" allowFormat="true"
                                   bindTarget="EXP5110_exp_report_header_ds"/>
                    <!-- <a:comboBox name="payeeCategoryName" bindTarget="EXP5110_exp_report_header_ds"/>-->
                    <a:lov name="payeeName" bindTarget="EXP5110_exp_report_header_ds">
                        <a:events>
                            <a:event name="focus" handler="EXP5100_onFocus"/>
                        </a:events>
                    </a:lov>
                    <#list header_info as header>
                        <#if header.taxpayerType == "GENERAL_TAXPAYER">
                            <a:checkBox name="vatInvoiceFlag" bindTarget="EXP5110_exp_report_header_ds"
                                        prompt="exp_report_header.vat_invoice_flag"/>
                        </#if>
                        <#if header.relationMethodCode == 'HEAD'>
                            <a:lov name="expRequisitionNumber" id="EXP5110_expReqNumberLov" bindTarget="EXP5110_exp_report_header_ds" className="reqnumberlov">
                                <a:events>
                                    <a:event name="focus" handler="EXP5110_onReqNumberFocusFun"/>
                                </a:events>
                            </a:lov>
                        </#if>
                    </#list>
                    <#list defaultObjectFields as ObjectField>
                        <#if ObjectField.expenseObjectMethod == "DESC_TEXT">
                            <a:textField name="${ObjectField.displayField}" bindTarget="EXP5110_exp_report_header_ds"/>
                        <#else>
                            <a:lov name="${ObjectField.displayField}" bindTarget="EXP5110_exp_report_header_ds"/>
                        </#if>
                    </#list>
                    <#list headerDimensionFields as DimensionField>
                        <#if DimensionField ??>
                            <a:lov name="${DimensionField.displayField}" bindTarget="EXP5110_exp_report_header_ds">
                                <a:events>
                                    <a:event name="focus" handler="EXP5110_headerDimensionFunc"/>
                                </a:events>
                            </a:lov>
                        </#if>
                    </#list>
                </a:box>
                <a:box style="width:100%;margin-top:-5px;">
                    <a:textArea name="description" bindTarget="EXP5110_exp_report_header_ds"
                                calcWidth="calc((100%  + 90px) / 4 * 3 + 150px)" width="800"/>
                </a:box>
            </a:form>
            <a:vBox id="EXP5110_view_place" padding="0" showMargin="false" style="">
                <#list elements as element>
                    <#if element!=null && element.serviceName??>
                        <a:screen-include screen="/WEB-INF/view/${element.serviceName}"/>
                    </#if>
                </#list>
            </a:vBox>
        </a:screenBody>
    </a:view>

</a:screen>
