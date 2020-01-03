package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-21-CSH5040-init-table-migration.groovy') {


    changeSet(author: "jingjing.tang@hand-china.com", id: "2019-03-21-CSH_PAYMENT_MESSAGE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_MESSAGE_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_MESSAGE", remarks: "现金支付信息表") {

            column(name: "MESSAGE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其它表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_MESSAGE_PK")} 
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")   
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")   
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种")   
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "收款对象")   
            column(name: "PAYEE_ID", type: "BIGINT", remarks: "收款方")   
            column(name: "PAYEE_DATE", type: "DATETIME", remarks: "费控提请付款时间")   
            column(name: "AMOUNT", type: "BIGINT", remarks: "金额")   
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "描述")   
            column(name: "PAYMENT_METHOD_ID", type: "BIGINT", remarks: "付款方式ID")   
            column(name: "ACCOUNT_NAME", type: "VARCHAR(100)", remarks: "银行户名")   
            column(name: "ACCOUNT_NUMBER", type: "VARCHAR(100)", remarks: "银行账号")   
            column(name: "DOCUMENT_TYPE", type: "VARCHAR(100)", remarks: "单据类型（EXP_REPORT/费用报销单，PAYMENT_REQUISITION/借款申请单，ACP_REQUISITION/付款申请单）")   
            column(name: "DOCUMENT_ID", type: "BIGINT", remarks: "单据ID")   
            column(name: "DOCUMENT_NUMBER", type: "VARCHAR(30)", remarks: "单据编号")   
            column(name: "DOCUMENT_LINE_ID", type: "BIGINT", remarks: "单据明细ID（费用报销单为计划付款行ID，借款申请单/付款申请单为行ID）")   
            column(name: "EBANKING_FLAG", type: "VARCHAR(1)", remarks: "网银标志")  {constraints(nullable:"false")}  
            column(name: "OFFER_FLAG", type: "VARCHAR(1)", remarks: "报盘标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
    
    }


}