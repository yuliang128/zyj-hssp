package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-04-08-CSH1280-init-table-migration.groovy') {


    changeSet(author: "jingjing.tang@hand-china.com", id: "2019-04-08-CSH_PAYMENT_BATCH_HEADER") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_BATCH_HEADER_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_BATCH_HEADER", remarks: "付款批头") {

            column(name: "BATCH_HEADER_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_BATCH_HEADER_PK")} 
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID")  {constraints(nullable:"false")}  
            column(name: "BATCH_TYPE_ID", type: "BIGINT", remarks: "付款批类型ID")  {constraints(nullable:"false")}  
            column(name: "BATCH_NUMBER", type: "VARCHAR(30)", remarks: "付款批号")  {constraints(nullable:"false")}  
            column(name: "BATCH_DATE", type: "DATETIME", remarks: "付款批时间")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_METHOD_ID", type: "BIGINT", remarks: "付款方式")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_CURRENCY_CODE", type: "VARCHAR(30)", remarks: "支付币种")  {constraints(nullable:"false")}  
            column(name: "BANK_ACCOUNT_ID", type: "BIGINT", remarks: "银行账户ID")  {constraints(nullable:"false")}  
            column(name: "DRAWEE_BANK_CODE", type: "VARCHAR(30)", remarks: "付款方银行代码")   
            column(name: "DRAWEE_BANK_NAME", type: "VARCHAR(200)", remarks: "付款方银行名称")   
            column(name: "DRAWEE_BANK_LOCATION_CODE", type: "VARCHAR(30)", remarks: "付款方分行代码")   
            column(name: "DRAWEE_BANK_LOCATION", type: "VARCHAR(200)", remarks: "付款方分行名称")   
            column(name: "DRAWEE_PROVINCE_CODE", type: "VARCHAR(30)", remarks: "付款方分行所在省")   
            column(name: "DRAWEE_PROVINCE_NAME", type: "VARCHAR(200)", remarks: "付款方省名称")   
            column(name: "DRAWEE_CITY_CODE", type: "VARCHAR(30)", remarks: "付款方分行所在市")   
            column(name: "DRAWEE_CITY_NAME", type: "VARCHAR(200)", remarks: "付款方市名称")   
            column(name: "DRAWEE_ACCOUNT_NUMBER", type: "VARCHAR(200)", remarks: "付款方银行账号")   
            column(name: "DRAWEE_ACCOUNT_NAME", type: "VARCHAR(200)", remarks: "付款方银行户名")   
            column(name: "TOTAL_NUMBER", type: "BIGINT", remarks: "付款批总笔数")  {constraints(nullable:"false")}  
            column(name: "AMOUNT", type: "NUMBER", remarks: "付款批总金额")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_TOTAL_NUMBER", type: "BIGINT", remarks: "支付总笔数")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_AMOUNT", type: "NUMBER", remarks: "支付总金额")  {constraints(nullable:"false")}  
            column(name: "EXPORT_NUMBER", type: "BIGINT", remarks: "导出次数")  {constraints(nullable:"false")}  
            column(name: "EXPORT_USER_ID", type: "BIGINT", remarks: "导出用户")   
            column(name: "STATUS", type: "VARCHAR(30)", remarks: "状态（SYSCODE：CSH_PAYMENT_BATCH_STATUS）")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_COMPLETED_DATE", type: "DATETIME", remarks: "付款完成日期")   
            column(name: "PAYMENT_COMPLETED_DATE_TZ", type: "TIMESTAMP", remarks: "付款完成日期_业务时区")   
            column(name: "PAYMENT_COMPLETED_DATE_LTZ", type: "TIMESTAMP", remarks: "付款完成日期_查询时区")   
            column(name: "REVERSED_FLAG", type: "VARCHAR(1)", remarks: "反冲标志（N未被反冲/Y部分反冲/C完全反冲）")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_PAYMENT_BATCH_HEADER", indexName: "CSH_PAYMENT_BATCH_HEADER_N1") {
                    column(name: "ACC_ENTITY_ID")
                    column(name: "COMPANY_ID")
                }
                createIndex(tableName: "CSH_PAYMENT_BATCH_HEADER", indexName: "CSH_PAYMENT_BATCH_HEADER_N2") {
                    column(name: "BATCH_TYPE_ID")
                }
                createIndex(tableName: "CSH_PAYMENT_BATCH_HEADER", indexName: "CSH_PAYMENT_BATCH_HEADER_N3") {
                    column(name: "BANK_ACCOUNT_ID")
                }
            
        addUniqueConstraint(columnNames:"BATCH_NUMBER",tableName:"CSH_PAYMENT_BATCH_HEADER",constraintName: "CSH_PAYMENT_BATCH_HEADER_U1")
    }


    changeSet(author: "jingjing.tang@hand-china.com", id: "2019-04-08-CSH_PAYMENT_BATCH_LINE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_BATCH_LINE_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_BATCH_LINE", remarks: "付款批行") {

            column(name: "BATCH_LINE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_BATCH_LINE_PK")} 
            column(name: "BATCH_HEADER_ID", type: "BIGINT", remarks: "付款批头ID")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID")  {constraints(nullable:"false")}  
            column(name: "TRANSACTION_LINE_ID", type: "BIGINT", remarks: "现金事物行表ID")  {constraints(nullable:"false")}  
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "收款对象")  {constraints(nullable:"false")}  
            column(name: "PAYEE_ID", type: "BIGINT", remarks: "收款方")  {constraints(nullable:"false")}  
            column(name: "PAYEE_BANK_CODE", type: "VARCHAR(30)", remarks: "收款方银行代码")   
            column(name: "PAYEE_BANK_NAME", type: "VARCHAR(200)", remarks: "收款方银行名称")   
            column(name: "PAYEE_BANK_LOCATION_CODE", type: "VARCHAR(30)", remarks: "收款方分行代码")   
            column(name: "PAYEE_BANK_LOCATION", type: "VARCHAR(200)", remarks: "收款方分行名称")   
            column(name: "PAYEE_PROVINCE_CODE", type: "VARCHAR(30)", remarks: "收款方分行所在省")   
            column(name: "PAYEE_PROVINCE_NAME", type: "VARCHAR(200)", remarks: "收款方省名称")   
            column(name: "PAYEE_CITY_CODE", type: "VARCHAR(30)", remarks: "收款方分行所在市")   
            column(name: "PAYEE_CITY_NAME", type: "VARCHAR(200)", remarks: "收款方市名称")   
            column(name: "PAYEE_ACCOUNT_NUMBER", type: "VARCHAR(200)", remarks: "收款方银行账号")   
            column(name: "PAYEE_ACCOUNT_NAME", type: "VARCHAR(200)", remarks: "收款方银行户名")   
            column(name: "AMOUNT", type: "NUMBER", remarks: "金额")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_STATUS", type: "VARCHAR(30)", remarks: "付款状态")  {constraints(nullable:"false")}  
            column(name: "REVERSED_FLAG", type: "VARCHAR(1)", remarks: "反冲标志（N未被反冲\\R反冲\\W被反冲）")  {constraints(nullable:"false")}
            column(name: "REVERSED_DATE", type: "DATETIME", remarks: "反冲日期")   
            column(name: "REVERSED_DATE_TZ", type: "TIMESTAMP", remarks: "反冲日期_当地时区")   
            column(name: "REVERSED_DATE_LTZ", type: "TIMESTAMP", remarks: "反冲日期_查询时区")   
            column(name: "RETURNED_FLAG", type: "VARCHAR(1)", remarks: "退款标志（R退款事物\\Y部分退款\\C完全退款\\N未退款）")  {constraints(nullable:"false")}
            column(name: "SOURCE_LINE_ID", type: "BIGINT", remarks: "反冲、退款的来源付款批头ID")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_PAYMENT_BATCH_LINE", indexName: "CSH_PAYMENT_BATCH_LINE_N1") {
                    column(name: "BATCH_HEADER_ID")
                }
                createIndex(tableName: "CSH_PAYMENT_BATCH_LINE", indexName: "CSH_PAYMENT_BATCH_LINE_N2") {
                    column(name: "TRANSACTION_LINE_ID")
                }
                createIndex(tableName: "CSH_PAYMENT_BATCH_LINE", indexName: "CSH_PAYMENT_BATCH_LINE_N3") {
                    column(name: "ACC_ENTITY_ID")
                    column(name: "COMPANY_ID")
                }
                createIndex(tableName: "CSH_PAYMENT_BATCH_LINE", indexName: "CSH_PAYMENT_BATCH_LINE_N4") {
                    column(name: "SOURCE_LINE_ID")
                }
        
    }


}