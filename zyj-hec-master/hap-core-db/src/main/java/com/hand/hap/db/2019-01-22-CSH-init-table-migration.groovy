package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-22-CSH-init-table-migration.groovy') {


    changeSet(author: "jingjing.tang@hand-china.com", id: "2019-01-22-CSH_WRITE_OFF") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_WRITE_OFF_S', startValue:"10001")
        }

        createTable(tableName: "CSH_WRITE_OFF", remarks: "现金事务核销表") {

            column(name: "WRITE_OFF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "核销ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_WRITE_OFF_PK")} 
            column(name: "WRITE_OFF_TYPE", type: "VARCHAR(30)", remarks: "核销类型")  {constraints(nullable:"false")}  
            column(name: "CSH_TRANSACTION_LINE_ID", type: "BIGINT", remarks: "现金事务行ID")   
            column(name: "CSH_WRITE_OFF_AMOUNT", type: "NUMBER", remarks: "核销金额")   
            column(name: "DOCUMENT_SOURCE", type: "VARCHAR(30)", remarks: "单据来源")   
            column(name: "DOCUMENT_HEADER_ID", type: "BIGINT", remarks: "单据头ID")   
            column(name: "DOCUMENT_LINE_ID", type: "BIGINT", remarks: "单据行ID")   
            column(name: "DOCUMENT_WRITE_OFF_AMOUNT", type: "NUMBER", remarks: "单据核销金额")   
            column(name: "WRITE_OFF_DATE", type: "DATETIME", remarks: "核销日期")  {constraints(nullable:"false")}  
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")  {constraints(nullable:"false")}  
            column(name: "SOURCE_CSH_TRX_LINE_ID", type: "BIGINT", remarks: "来源现金事务行ID")   
            column(name: "SOURCE_WRITE_OFF_AMOUNT", type: "NUMBER", remarks: "来源核销金额")   
            column(name: "GLD_INTERFACE_FLAG", type: "VARCHAR(1)", remarks: "总账接口标志（P已过，N未过）")   
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_WRITE_OFF", indexName: "CSH_WRITE_OFF_N1") {
                    column(name: "DOCUMENT_HEADER_ID")
                    column(name: "DOCUMENT_SOURCE")
                }
                createIndex(tableName: "CSH_WRITE_OFF", indexName: "CSH_WRITE_OFF_N2") {
                    column(name: "WRITE_OFF_DATE")
                }
                createIndex(tableName: "CSH_WRITE_OFF", indexName: "CSH_WRITE_OFF_N3") {
                    column(name: "CSH_TRANSACTION_LINE_ID")
                }
                createIndex(tableName: "CSH_WRITE_OFF", indexName: "CSH_WRITE_OFF_N4") {
                    column(name: "SOURCE_CSH_TRX_LINE_ID")
                }
        
    }


    changeSet(author: "jingjing.tang@hand-china.com", id: "2019-01-22-CSH_WRITE_OFF_ACCOUNT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_WRITE_OFF_ACCOUNT_S', startValue:"10001")
        }

        createTable(tableName: "CSH_WRITE_OFF_ACCOUNT", remarks: "核销凭证表") {

            column(name: "WRITE_OFF_JE_LINE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_WRITE_OFF_ACCOUNT_PK")} 
            column(name: "WRITE_OFF_ID", type: "BIGINT", remarks: "核销ID")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "描述")   
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")  {constraints(nullable:"false")}  
            column(name: "SOURCE_CODE", type: "VARCHAR(30)", remarks: "来源")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体")  {constraints(nullable:"false")}  
            column(name: "RESP_CENTER_ID", type: "BIGINT", remarks: "责任中心")  {constraints(nullable:"false")}  
            column(name: "ACCOUNT_ID", type: "BIGINT", remarks: "科目")  {constraints(nullable:"false")}  
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种")  {constraints(nullable:"false")}  
            column(name: "EXCHANGE_RATE_TYPE", type: "VARCHAR(30)", remarks: "汇率类型")   
            column(name: "EXCHANGE_RATE", type: "NUMBER", remarks: "汇率类型")  {constraints(nullable:"false")}  
            column(name: "ENTERED_AMOUNT_DR", type: "NUMBER", remarks: "原币借方金额")   
            column(name: "ENTERED_AMOUNT_CR", type: "NUMBER", remarks: "原币贷方金额")   
            column(name: "FUNCTIONAL_AMOUNT_DR", type: "NUMBER", remarks: "本币借方金额")   
            column(name: "FUNCTIONAL_AMOUNT_CR", type: "NUMBER", remarks: "本币贷方金额")   
            column(name: "GLD_INTERFACE_FLAG", type: "VARCHAR(1)", remarks: "总账接口标志")  {constraints(nullable:"false")}  
            column(name: "CASH_CLEARING_FLAG", type: "VARCHAR(1)", remarks: "现金清算标志")  {constraints(nullable:"false")}  
            column(name: "JOURNAL_DATE", type: "DATETIME", remarks: "制证日期")  {constraints(nullable:"false")}  
            column(name: "USAGE_CODE", type: "VARCHAR(30)", remarks: "用途代码")  {constraints(nullable:"false")}  
            column(name: "ACCOUNT_SEGMENT1", type: "VARCHAR(200)", remarks: "科目段1")   
            column(name: "ACCOUNT_SEGMENT2", type: "VARCHAR(200)", remarks: "科目段2")   
            column(name: "ACCOUNT_SEGMENT3", type: "VARCHAR(200)", remarks: "科目段3")   
            column(name: "ACCOUNT_SEGMENT4", type: "VARCHAR(200)", remarks: "科目段4")   
            column(name: "ACCOUNT_SEGMENT5", type: "VARCHAR(200)", remarks: "科目段5")   
            column(name: "ACCOUNT_SEGMENT6", type: "VARCHAR(200)", remarks: "科目段6")   
            column(name: "ACCOUNT_SEGMENT7", type: "VARCHAR(200)", remarks: "科目段7")   
            column(name: "ACCOUNT_SEGMENT8", type: "VARCHAR(200)", remarks: "科目段8")   
            column(name: "ACCOUNT_SEGMENT9", type: "VARCHAR(200)", remarks: "科目段9")   
            column(name: "ACCOUNT_SEGMENT10", type: "VARCHAR(200)", remarks: "科目段10")   
            column(name: "ACCOUNT_SEGMENT11", type: "VARCHAR(200)", remarks: "科目段11")   
            column(name: "ACCOUNT_SEGMENT12", type: "VARCHAR(200)", remarks: "科目段12")   
            column(name: "ACCOUNT_SEGMENT13", type: "VARCHAR(200)", remarks: "科目段13")   
            column(name: "ACCOUNT_SEGMENT14", type: "VARCHAR(200)", remarks: "科目段14")   
            column(name: "ACCOUNT_SEGMENT15", type: "VARCHAR(200)", remarks: "科目段15")   
            column(name: "ACCOUNT_SEGMENT16", type: "VARCHAR(200)", remarks: "科目段16")   
            column(name: "ACCOUNT_SEGMENT17", type: "VARCHAR(200)", remarks: "科目段17")   
            column(name: "ACCOUNT_SEGMENT18", type: "VARCHAR(200)", remarks: "科目段18")   
            column(name: "ACCOUNT_SEGMENT19", type: "VARCHAR(200)", remarks: "科目段19")   
            column(name: "ACCOUNT_SEGMENT20", type: "VARCHAR(200)", remarks: "科目段20")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_WRITE_OFF_ACCOUNT", indexName: "CSH_WRITE_OFF_ACCOUNT_N1") {
                    column(name: "WRITE_OFF_ID")
                }
        
    }


    changeSet(author: "jingjing.tang@hand-china.com", id: "2019-01-22-CSH_TRANSACTION_LINE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_TRANSACTION_LINE_S', startValue:"10001")
        }

        createTable(tableName: "CSH_TRANSACTION_LINE", remarks: "现金事物行表") {

            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种")  {constraints(nullable:"false")}  
            column(name: "EXCHANGE_RATE_TYPE", type: "VARCHAR(30)", remarks: "汇率类型")   
            column(name: "EXCHANGE_RATE", type: "NUMBER", remarks: "汇率")  {constraints(nullable:"false")}  
            column(name: "BANK_ACCOUNT_ID", type: "BIGINT", remarks: "付款方银行账户")   
            column(name: "DOCUMENT_NUM", type: "VARCHAR(30)", remarks: "单据编号")   
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "收款方类别")   
            column(name: "PAYEE_ID", type: "BIGINT", remarks: "收款方ID")   
            column(name: "PAYEE_BANK_ACCOUNT_ID", type: "BIGINT", remarks: "收款方银行账户")   
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "描述")   
            column(name: "HANDLING_CHARGE", type: "NUMBER", remarks: "手续费")   
            column(name: "INTEREST", type: "NUMBER", remarks: "利息")   
            column(name: "AGENT_EMPLOYEE_ID", type: "BIGINT", remarks: "经办人")  {constraints(nullable:"false")}  
            column(name: "TRANS_IN_OUT_TYPE", type: "VARCHAR(30)", remarks: "转入转出标志")   
            column(name: "DOCUMENT_CATEGORY", type: "VARCHAR(30)", remarks: "单据类别")   
            column(name: "DOCUMENT_TYPE_ID", type: "BIGINT", remarks: "单据类型ID")   
            column(name: "PAYMENT_USEDE_ID", type: "BIGINT", remarks: "付款用途ID")   
            column(name: "PAYMENT_METHOD_ID", type: "BIGINT", remarks: "付款方式ID")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "TRANSACTION_LINE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_TRANSACTION_LINE_PK")} 
            column(name: "TRANSACTION_HEADER_ID", type: "BIGINT", remarks: "现金事务头ID")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体")  {constraints(nullable:"false")}  
            column(name: "TRANSACTION_AMOUNT", type: "NUMBER", remarks: "交易金额")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_TRANSACTION_LINE", indexName: "CSH_TRANSACTION_LINE_N1") {
                    column(name: "TRANSACTION_HEADER_ID")
                }
                createIndex(tableName: "CSH_TRANSACTION_LINE", indexName: "CSH_TRANSACTION_LINE_N2") {
                    column(name: "PAYEE_CATEGORY")
                    column(name: "PAYEE_ID")
                }
                createIndex(tableName: "CSH_TRANSACTION_LINE", indexName: "CSH_TRANSACTION_LINE_N3") {
                    column(name: "DOCUMENT_CATEGORY")
                    column(name: "DOCUMENT_TYPE_ID")
                }
                createIndex(tableName: "CSH_TRANSACTION_LINE", indexName: "CSH_TRANSACTION_LINE_N4") {
                    column(name: "PAYMENT_USEDE_ID")
                }
                createIndex(tableName: "CSH_TRANSACTION_LINE", indexName: "CSH_TRANSACTION_LINE_N5") {
                    column(name: "PAYMENT_METHOD_ID")
                }
        
    }


    changeSet(author: "jingjing.tang@hand-china.com", id: "2019-01-22-CSH_TRANSACTION_HEADER") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_TRANSACTION_HEADER_S', startValue:"10001")
        }

        createTable(tableName: "CSH_TRANSACTION_HEADER", remarks: "现金事物头表") {

            column(name: "TRANSACTION_HEADER_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_TRANSACTION_HEADER_PK")} 
            column(name: "TRANSACTION_NUM", type: "VARCHAR(30)", remarks: "现金事物编号")  {constraints(nullable:"false")}  
            column(name: "TRANSACTION_TYPE", type: "VARCHAR(30)", remarks: "现金事物类型(PAYMENT,PREPAYMENT)")  {constraints(nullable:"false")}  
            column(name: "MO_CSH_TRX_CLASS_ID", type: "BIGINT", remarks: "现金事物分类ID")   
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "经办人，源单据头上员工")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "描述")   
            column(name: "ENABLED_WRITE_OFF_FLAG", type: "VARCHAR(1)", remarks: "是否启用核销")  {constraints(nullable:"false")}  
            column(name: "TRANSACTION_DATE", type: "DATETIME", remarks: "交易日期")  {constraints(nullable:"false")}  
            column(name: "TRANSACTION_DATE_TZ", type: "TIMESTAMP", defaultValueComputed:"CURRENT_TIMESTAMP", remarks: "交易日期_当地时区")  {constraints(nullable:"false")}
            column(name: "TRANSACTION_DATE_LTZ", type: "TIMESTAMP", defaultValueComputed:"CURRENT_TIMESTAMP", remarks: "交易日期_查询时区")  {constraints(nullable:"false")}
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_METHOD_ID", type: "BIGINT", remarks: "付款方式ID")  {constraints(nullable:"false")}  
            column(name: "TRANSACTION_CATEGORY", type: "VARCHAR(30)", remarks: "现金事物类别(BUSINESS,MISCELLANEOUS)")  {constraints(nullable:"false")}  
            column(name: "POSTED_FLAG", type: "VARCHAR(1)", remarks: "过账标志")  {constraints(nullable:"false")}  
            column(name: "REVERSED_FLAG", type: "VARCHAR(1)", remarks: "反冲标志")  {constraints(nullable:"false")}  
            column(name: "REVERSED_DATE", type: "DATETIME", remarks: "反冲日期")   
            column(name: "REVERSED_DATE_TZ", type: "TIMESTAMP", defaultValueComputed:"CURRENT_TIMESTAMP", remarks: "反冲日期_当地时区")
            column(name: "REVERSED_DATE_LTZ", type: "TIMESTAMP", defaultValueComputed:"CURRENT_TIMESTAMP", remarks: "反冲日期_查询时区")
            column(name: "RETURNED_FLAG", type: "VARCHAR(1)", remarks: "退款标志(R退款事物\\Y部分退款\\C完全退款\\N未退款)")
            column(name: "WRITE_OFF_FLAG", type: "VARCHAR(1)", remarks: "核销标志（C完全核销\\Y部分核销\\N未核销）")  {constraints(nullable:"false")}
            column(name: "WRITE_OFF_COMPLETED_DATE", type: "DATETIME", remarks: "核销日期")   
            column(name: "WRITE_OFF_COMPLETED_DATE_TZ", type: "TIMESTAMP", defaultValueComputed:"CURRENT_TIMESTAMP", remarks: "核销日期_当地时区")
            column(name: "WRITE_OFF_COMPLETED_DATE_LTZ", type: "TIMESTAMP", defaultValueComputed:"CURRENT_TIMESTAMP", remarks: "核销日期_查询时区")
            column(name: "SOURCE_HEADER_ID", type: "BIGINT", remarks: "反冲、退款的来源现金事物头ID")   
            column(name: "GLD_INTERFACE_FLAG", type: "VARCHAR(1)", remarks: "总账标志（N未入总账\\P可入总账）")  {constraints(nullable:"false")}
            column(name: "SOURCE_PAYMENT_HEADER_ID", type: "BIGINT", remarks: "来源付款现金事物ID")   
            column(name: "EBANKING_FLAG", type: "VARCHAR(1)", remarks: "网银标志")  {constraints(nullable:"false")}  
            column(name: "DOCUMENT_CATEGORY", type: "VARCHAR(30)", remarks: "单据类别")   
            column(name: "DOCUMENT_TYPE_ID", type: "BIGINT", remarks: "单据类型ID")   
            column(name: "PAYMENT_USEDE_ID", type: "BIGINT", remarks: "付款用途ID")   
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "收款方类型")   
            column(name: "PAYEE_ID", type: "BIGINT", remarks: "收款方")   
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种")   
            column(name: "BANK_ACCOUNT_ID", type: "BIGINT", remarks: "付款方银行账户")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_TRANSACTION_HEADER", indexName: "CSH_TRANSACTION_HEADER_N1") {
                    column(name: "ACC_ENTITY_ID")
                    column(name: "TRANSACTION_DATE")
                }
                    createIndex(tableName: "CSH_TRANSACTION_HEADER", indexName: "CSH_TRANSACTION_HEADER_N2") {
                    column(name: "ACC_ENTITY_ID")
                    column(name: "REVERSED_DATE")
                }
                createIndex(tableName: "CSH_TRANSACTION_HEADER", indexName: "CSH_TRANSACTION_HEADER_N3") {
                    column(name: "ACC_ENTITY_ID")
                    column(name: "WRITE_OFF_COMPLETED_DATE")
                }
                createIndex(tableName: "CSH_TRANSACTION_HEADER", indexName: "CSH_TRANSACTION_HEADER_N4") {
                    column(name: "MO_CSH_TRX_CLASS_ID")
                }
                createIndex(tableName: "CSH_TRANSACTION_HEADER", indexName: "CSH_TRANSACTION_HEADER_N5") {
                    column(name: "EMPLOYEE_ID")
                }
                createIndex(tableName: "CSH_TRANSACTION_HEADER", indexName: "CSH_TRANSACTION_HEADER_N6") {
                    column(name: "PERIOD_NAME")
                }
                createIndex(tableName: "CSH_TRANSACTION_HEADER", indexName: "CSH_TRANSACTION_HEADER_N7") {
                    column(name: "SOURCE_HEADER_ID")
                }
                createIndex(tableName: "CSH_TRANSACTION_HEADER", indexName: "CSH_TRANSACTION_HEADER_N8") {
                    column(name: "SOURCE_PAYMENT_HEADER_ID")
                }
                createIndex(tableName: "CSH_TRANSACTION_HEADER", indexName: "CSH_TRANSACTION_HEADER_N9") {
                    column(name: "DOCUMENT_CATEGORY")
                    column(name: "DOCUMENT_TYPE_ID")
                }
            
        addUniqueConstraint(columnNames:"TRANSACTION_NUM",tableName:"CSH_TRANSACTION_HEADER",constraintName: "CSH_TRANSACTION_HEADER_U1")
    }


    changeSet(author: "jingjing.tang@hand-china.com", id: "2019-01-22-CSH_TRANSACTION_DIST") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_TRANSACTION_DIST_S', startValue:"10001")
        }

        createTable(tableName: "CSH_TRANSACTION_DIST", remarks: "现金事务分配行表") {

            column(name: "TRANSACTION_HEADER_ID", type: "BIGINT", remarks: "交易事务头ID")  {constraints(nullable:"false")}  
            column(name: "DISTRIBUTION_LINE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "单据分配行ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_TRANSACTION_DIST_PK")} 
            column(name: "SOURCE_DISTRIBUTION_LINE_ID", type: "BIGINT", remarks: "来源单据分配行ID")   
            column(name: "DEBIT_FLAG", type: "VARCHAR(2)", remarks: "借方")   
            column(name: "AMOUNT", type: "NUMBER", remarks: "金额")  {constraints(nullable:"false")}  
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT", remarks: "成本中心ID")  {constraints(nullable:"false")}  
            column(name: "ACCOUNT_ID", type: "BIGINT", remarks: "科目ID")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "描述")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_TRANSACTION_DIST", indexName: "CSH_TRANSACTION_DIST_N1") {
                    column(name: "TRANSACTION_HEADER_ID")
                    column(name: "DISTRIBUTION_LINE_ID")
                }
                createIndex(tableName: "CSH_TRANSACTION_DIST", indexName: "CSH_TRANSACTION_DIST_N2") {
                    column(name: "SOURCE_DISTRIBUTION_LINE_ID")
                }
        
    }


    changeSet(author: "jingjing.tang@hand-china.com", id: "2019-01-22-CSH_TRANSACTION_ACCOUNT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_TRANSACTION_ACCOUNT_S', startValue:"10001")
        }

        createTable(tableName: "CSH_TRANSACTION_ACCOUNT", remarks: "现金事物凭证行表") {

            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "TRANSACTION_JE_LINE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "现金事物凭证行ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_TRANSACTION_ACCOUNT_PK")} 
            column(name: "TRANSACTION_LINE_ID", type: "BIGINT", remarks: "现金事物行ID")  {constraints(nullable:"false")}  
            column(name: "DISTRIBUTION_LINE_ID", type: "BIGINT", remarks: "现金事物分配行ID")   
            column(name: "WRITE_OFF_ID", type: "BIGINT", remarks: "核销ID")   
            column(name: "SOURCE_CODE", type: "VARCHAR(30)", remarks: "来源代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "描述")   
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体")  {constraints(nullable:"false")}  
            column(name: "RESP_CENTER_ID", type: "BIGINT", remarks: "责任中心")  {constraints(nullable:"false")}  
            column(name: "ACCOUNT_ID", type: "BIGINT", remarks: "科目")  {constraints(nullable:"false")}  
            column(name: "ENTERED_AMOUNT_DR", type: "NUMBER", remarks: "原币借方金额")   
            column(name: "ENTERED_AMOUNT_CR", type: "NUMBER", remarks: "原币贷方金额")   
            column(name: "FUNCTIONAL_AMOUNT_DR", type: "NUMBER", remarks: "本币借方金额")   
            column(name: "FUNCTIONAL_AMOUNT_CR", type: "NUMBER", remarks: "本币贷方金额")   
            column(name: "EXCHANGE_RATE_TYPE", type: "VARCHAR(30)", remarks: "汇率类型")   
            column(name: "EXCHANGE_RATE", type: "NUMBER", remarks: "汇率")  {constraints(nullable:"false")}  
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种")  {constraints(nullable:"false")}  
            column(name: "JOURNAL_DATE", type: "DATETIME", remarks: "交易日期")  {constraints(nullable:"false")}  
            column(name: "JOURNAL_DATE_TZ", type: "TIMESTAMP", defaultValueComputed:"CURRENT_TIMESTAMP", remarks: "交易日期_当地时区")  {constraints(nullable:"false")}
            column(name: "JOURNAL_DATE_LTZ", type: "TIMESTAMP", defaultValueComputed:"CURRENT_TIMESTAMP", remarks: "交易日期_查询时区")  {constraints(nullable:"false")}
            column(name: "CASH_CLEARING_FLAG", type: "VARCHAR(1)", remarks: "现金清算标志")   
            column(name: "BANK_ACCOUNT_FLAG", type: "VARCHAR(1)", remarks: "银行账户标志")   
            column(name: "GLD_INTERFACE_FLAG", type: "VARCHAR(1)", remarks: "总账标志")  {constraints(nullable:"false")}  
            column(name: "USAGE_CODE", type: "VARCHAR(30)", remarks: "用途代码")  {constraints(nullable:"false")}  
            column(name: "BATCH_JE_LINE_ID", type: "BIGINT", remarks: "付款批凭证ID")   
            column(name: "ACCOUNT_SEGMENT1", type: "VARCHAR(200)", remarks: "科目段1")   
            column(name: "ACCOUNT_SEGMENT2", type: "VARCHAR(200)", remarks: "科目段2")   
            column(name: "ACCOUNT_SEGMENT3", type: "VARCHAR(200)", remarks: "科目段3")   
            column(name: "ACCOUNT_SEGMENT4", type: "VARCHAR(200)", remarks: "科目段4")   
            column(name: "ACCOUNT_SEGMENT5", type: "VARCHAR(200)", remarks: "科目段5")   
            column(name: "ACCOUNT_SEGMENT6", type: "VARCHAR(200)", remarks: "科目段6")   
            column(name: "ACCOUNT_SEGMENT7", type: "VARCHAR(200)", remarks: "科目段7")   
            column(name: "ACCOUNT_SEGMENT8", type: "VARCHAR(200)", remarks: "科目段8")   
            column(name: "ACCOUNT_SEGMENT9", type: "VARCHAR(200)", remarks: "科目段9")   
            column(name: "ACCOUNT_SEGMENT10", type: "VARCHAR(200)", remarks: "科目段10")   
            column(name: "ACCOUNT_SEGMENT11", type: "VARCHAR(200)", remarks: "科目段11")   
            column(name: "ACCOUNT_SEGMENT12", type: "VARCHAR(200)", remarks: "科目段12")   
            column(name: "ACCOUNT_SEGMENT13", type: "VARCHAR(200)", remarks: "科目段13")   
            column(name: "ACCOUNT_SEGMENT14", type: "VARCHAR(200)", remarks: "科目段14")   
            column(name: "ACCOUNT_SEGMENT15", type: "VARCHAR(200)", remarks: "科目段15")   
            column(name: "ACCOUNT_SEGMENT16", type: "VARCHAR(200)", remarks: "科目段16")   
            column(name: "ACCOUNT_SEGMENT17", type: "VARCHAR(200)", remarks: "科目段17")   
            column(name: "ACCOUNT_SEGMENT18", type: "VARCHAR(200)", remarks: "科目段18")   
            column(name: "ACCOUNT_SEGMENT19", type: "VARCHAR(200)", remarks: "科目段19")   
            column(name: "ACCOUNT_SEGMENT20", type: "VARCHAR(200)", remarks: "科目段20")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_TRANSACTION_ACCOUNT", indexName: "CSH_TRANSACTION_ACCOUNT_N1") {
                    column(name: "TRANSACTION_LINE_ID")
                }
                createIndex(tableName: "CSH_TRANSACTION_ACCOUNT", indexName: "CSH_TRANSACTION_ACCOUNT_N2") {
                    column(name: "WRITE_OFF_ID")
                }
        
    }


}