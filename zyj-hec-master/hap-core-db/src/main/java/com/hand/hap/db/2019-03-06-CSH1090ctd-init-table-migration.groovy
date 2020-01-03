package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-06-CSH1090ctd-init-table-migration.groovy') {


    changeSet(author: "bo.zhang05@hand-china.com", id: "2019-03-06-CSH_TRANSACTION_DETAIL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_TRANSACTION_DETAIL_S', startValue:"10001")
        }

        createTable(tableName: "CSH_TRANSACTION_DETAIL", remarks: "费控资金交易接口表") {

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
            column(name: "PAYEE_ACCOUNT_TYPE", type: "VARCHAR(30)", remarks: "收款方账户类型，PERSONAL个人账户BUSINESS对公账户")   
            column(name: "SEGMENT1", type: "VARCHAR(200)", remarks: "备用字段1")   
            column(name: "SEGMENT2", type: "VARCHAR(200)", remarks: "备用字段2")   
            column(name: "SEGMENT3", type: "VARCHAR(200)", remarks: "备用字段3")   
            column(name: "SEGMENT4", type: "VARCHAR(200)", remarks: "备用字段4")   
            column(name: "SEGMENT5", type: "VARCHAR(200)", remarks: "备用字段5")   
            column(name: "SEGMENT6", type: "VARCHAR(200)", remarks: "备用字段6")   
            column(name: "SEGMENT7", type: "VARCHAR(200)", remarks: "备用字段7")   
            column(name: "SEGMENT8", type: "VARCHAR(200)", remarks: "备用字段8")   
            column(name: "SEGMENT9", type: "VARCHAR(200)", remarks: "备用字段9")   
            column(name: "SEGMENT10", type: "VARCHAR(200)", remarks: "备用字段10")   
            column(name: "SEGMENT11", type: "VARCHAR(200)", remarks: "备用字段11")   
            column(name: "SEGMENT12", type: "VARCHAR(200)", remarks: "备用字段12")   
            column(name: "SEGMENT13", type: "VARCHAR(200)", remarks: "备用字段13")   
            column(name: "SEGMENT14", type: "VARCHAR(200)", remarks: "备用字段14")   
            column(name: "SEGMENT15", type: "VARCHAR(200)", remarks: "备用字段15")   
            column(name: "SEGMENT16", type: "VARCHAR(200)", remarks: "备用字段16")   
            column(name: "SEGMENT17", type: "VARCHAR(200)", remarks: "备用字段17")   
            column(name: "SEGMENT18", type: "VARCHAR(200)", remarks: "备用字段18")   
            column(name: "SEGMENT19", type: "VARCHAR(200)", remarks: "备用字段19")   
            column(name: "SEGMENT20", type: "VARCHAR(200)", remarks: "备用字段20")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "DETAIL_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其它表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_TRANSACTION_DETAIL_PK")} 
            column(name: "CSH_TRANSACTION_LINE_ID", type: "BIGINT", remarks: "现金事物行ID")  {constraints(nullable:"false")}  
            column(name: "HEC_BATCH_NUM", type: "VARCHAR(200)", remarks: "费控提请接口批次号")   
            column(name: "HEC_DETAIL_NUM", type: "VARCHAR(200)", remarks: "费控提请接口明细批次号")   
            column(name: "ITF_BATCH_NUM", type: "VARCHAR(200)", remarks: "接口返回批次号")   
            column(name: "ITF_DETAIL_NUM", type: "VARCHAR(200)", remarks: "接口返回明细批次号")   
            column(name: "PAYMENT_STATUS", type: "VARCHAR(30)", remarks: "付款状态[PENDING暂挂],[WAITING_SEND等待发送],[PAY_SENT支付传送],[WAITING_CONFIRM_SUCCESS待付确认成功],[PAY_SUCCESS支付成功],[PAY_FAILED支付失败],[REFUNDING退票中],[REFUND_CONFIRM退票确认]")   
            column(name: "INTERFACE_STATUS", type: "VARCHAR(30)", remarks: "接口状态[UNSENT未发送，可以提交接口],[DATA_FORMAT_ERROR数据整理错误,可以提交接口],[SENT已发送],[UNKNOWN传送状态未知],[FAILED传送失败]")   
            column(name: "STATUS_DESC", type: "VARCHAR(2000)", remarks: "接口状态描述")   
            column(name: "SOURCE_CODE", type: "VARCHAR(30)", remarks: "业务来源")   
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")  {constraints(nullable:"false")}  
            column(name: "COMPANY_CODE", type: "VARCHAR(30)", remarks: "公司代码")  {constraints(nullable:"false")}  
            column(name: "DOCUMENT_TYPE", type: "VARCHAR(30)", remarks: "业务类型")   
            column(name: "DOCUMENT_ID", type: "BIGINT", remarks: "单据ID")   
            column(name: "DOCUMENT_NUMBER", type: "VARCHAR(30)", remarks: "单据编号")   
            column(name: "DOCUMENT_LINE_ID", type: "BIGINT", remarks: "单据明细ID")   
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "备注字段")   
            column(name: "PAYMENT_METHOD", type: "VARCHAR(30)", remarks: "付款方式")   
            column(name: "PAYMENT_CHANNEL", type: "VARCHAR(30)", remarks: "付款渠道，ENANK(资金系统)、OFFER(报盘)、OTHER(其他)")   
            column(name: "EBANK_CHANNEL", type: "VARCHAR(30)", remarks: "资金系统渠道，CN_PUBLIC(国内对公)，CN_PRIVATE(国内对私)")   
            column(name: "PAY_DATE", type: "DATETIME", remarks: "费控内支付日期")  {constraints(nullable:"false")}  
            column(name: "PAY_DATE_TZ", type: "TIMESTAMP", remarks: "费控内支付日期_TZ")   
            column(name: "PAY_DATE_LTZ", type: "TIMESTAMP", remarks: "费控内支付日期_LTZ")   
            column(name: "REQUEST_TIME", type: "DATETIME", remarks: "费控内支付请求时间")   
            column(name: "REQUEST_TIME_TZ", type: "TIMESTAMP", remarks: "费控内支付请求时间_TZ")   
            column(name: "REQUEST_TIME_LTZ", type: "TIMESTAMP", remarks: "费控内支付请求时间_LTZ")   
            column(name: "ACTUAL_PAY_DATE", type: "DATETIME", remarks: "实际支付成功日期")   
            column(name: "ACTUAL_PAY_DATE_TZ", type: "TIMESTAMP", remarks: "实际支付成功日期_TZ")   
            column(name: "ACTUAL_PAY_DATE_LTZ", type: "TIMESTAMP", remarks: "实际支付成功日期_LTZ")   
            column(name: "ITF_PAY_DATE", type: "DATETIME", remarks: "传递接口日期")   
            column(name: "ITF_PAY_DATE_TZ", type: "TIMESTAMP", remarks: "传递接口日期_TZ")   
            column(name: "ITF_PAY_DATE_LTZ", type: "TIMESTAMP", remarks: "传递接口日期_LTZ")   
            column(name: "REFUND_TIME", type: "DATETIME", remarks: "退票时间")   
            column(name: "REFUND_TIME_TZ", type: "TIMESTAMP", remarks: "退票时间_TZ")   
            column(name: "REFUND_TIME_LTZ", type: "TIMESTAMP", remarks: "退票时间_LTZ")   
            column(name: "CASH_FLOW_CODE", type: "VARCHAR(200)", remarks: "现金流量项")   
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "收款方类型")  {constraints(nullable:"false")}  
            column(name: "PAYEE_ID", type: "BIGINT", remarks: "收款方ID")  {constraints(nullable:"false")}  
            column(name: "PAYEE_CODE", type: "VARCHAR(30)", remarks: "收款方代码")  {constraints(nullable:"false")}  
            column(name: "PAYEE_NAME", type: "VARCHAR(2000)", remarks: "收款方名称")  {constraints(nullable:"false")}  
            column(name: "USEDES", type: "VARCHAR(30)", remarks: "付款用途")   
            column(name: "CURRENCY", type: "VARCHAR(30)", remarks: "币种")  {constraints(nullable:"false")}  
            column(name: "AMOUNT", type: "NUMBER", remarks: "金额")  {constraints(nullable:"false")}  
            column(name: "DRAWEE_ID", type: "BIGINT", remarks: "付款出纳")  {constraints(nullable:"false")}  
            column(name: "ENCRYPTION_AMOUNT", type: "VARCHAR(200)", remarks: "加密金额")   
            column(name: "ENCRYPTION_VERSION", type: "VARCHAR(50)", remarks: "加密版本")   
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
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_TRANSACTION_DETAIL", indexName: "CSH_TRANSACTION_DETAIL_N1") {
                    column(name: "CSH_TRANSACTION_LINE_ID")
                }
                createIndex(tableName: "CSH_TRANSACTION_DETAIL", indexName: "CSH_TRANSACTION_DETAIL_N2") {
                    column(name: "PAYMENT_STATUS")
                }
                createIndex(tableName: "CSH_TRANSACTION_DETAIL", indexName: "CSH_TRANSACTION_DETAIL_N3") {
                    column(name: "HEC_BATCH_NUM")
                    column(name: "HEC_DETAIL_NUM")
                }
                createIndex(tableName: "CSH_TRANSACTION_DETAIL", indexName: "CSH_TRANSACTION_DETAIL_N4") {
                    column(name: "ITF_BATCH_NUM")
                    column(name: "ITF_DETAIL_NUM")
                }
        
    }


}