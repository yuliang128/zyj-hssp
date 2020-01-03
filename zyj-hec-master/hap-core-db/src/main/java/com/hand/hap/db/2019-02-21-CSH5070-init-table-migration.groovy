package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-21-CSH5070-init-table-migration.groovy') {


    changeSet(author: "jingjing.tang@hand-china.com", id: "2019-02-21-CSH_PAYMENT_REQ_ACCOUNT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_REQ_ACCOUNT_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_REQ_ACCOUNT", remarks: "借款申请凭证表") {

            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_REQ_JE_LINE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_REQ_ACCOUNT_PK")} 
            column(name: "PAYMENT_REQUISITION_LINE_ID", type: "BIGINT", remarks: "借款申请单行ID")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "描述")   
            column(name: "JOURNAL_DATE", type: "DATETIME", remarks: "凭证日期")  {constraints(nullable:"false")}  
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "RESP_CENTER_ID", type: "BIGINT", remarks: "责任中心ID")  {constraints(nullable:"false")}  
            column(name: "ACCOUNT_ID", type: "BIGINT", remarks: "科目ID")  {constraints(nullable:"false")}  
            column(name: "CURRENCY_CODE", type: "VARCHAR(10)", remarks: "币种")  {constraints(nullable:"false")}  
            column(name: "EXCHANGE_RATE_TYPE", type: "VARCHAR(30)", remarks: "汇率类型")   
            column(name: "EXCHANGE_RATE", type: "NUMBER", remarks: "汇率")  {constraints(nullable:"false")}  
            column(name: "ENTERED_AMOUNT_DR", type: "NUMBER", remarks: "原币借方金额")   
            column(name: "ENTERED_AMOUNT_CR", type: "NUMBER", remarks: "原币贷方金额")   
            column(name: "FUNCTIONAL_AMOUNT_DR", type: "NUMBER", remarks: "本币借方金额")   
            column(name: "FUNCTIONAL_AMOUNT_CR", type: "NUMBER", remarks: "本币贷方金额")   
            column(name: "GLD_INTERFACE_FLAG", type: "VARCHAR(1)", remarks: "总账标志")  {constraints(nullable:"false")}  
            column(name: "USAGE_CODE", type: "VARCHAR(30)", remarks: "用途代码")  {constraints(nullable:"false")}  
            column(name: "ACCOUNT_SEGMENT1", type: "VARCHAR(200)", remarks: "核算段1")   
            column(name: "ACCOUNT_SEGMENT2", type: "VARCHAR(200)", remarks: "核算段2")   
            column(name: "ACCOUNT_SEGMENT3", type: "VARCHAR(200)", remarks: "核算段3")   
            column(name: "ACCOUNT_SEGMENT4", type: "VARCHAR(200)", remarks: "核算段4")   
            column(name: "ACCOUNT_SEGMENT5", type: "VARCHAR(200)", remarks: "核算段5")   
            column(name: "ACCOUNT_SEGMENT6", type: "VARCHAR(200)", remarks: "核算段6")   
            column(name: "ACCOUNT_SEGMENT7", type: "VARCHAR(200)", remarks: "核算段7")   
            column(name: "ACCOUNT_SEGMENT8", type: "VARCHAR(200)", remarks: "核算段8")   
            column(name: "ACCOUNT_SEGMENT9", type: "VARCHAR(200)", remarks: "核算段9")   
            column(name: "ACCOUNT_SEGMENT10", type: "VARCHAR(200)", remarks: "核算段10")   
            column(name: "ACCOUNT_SEGMENT11", type: "VARCHAR(200)", remarks: "核算段11")   
            column(name: "ACCOUNT_SEGMENT12", type: "VARCHAR(200)", remarks: "核算段12")   
            column(name: "ACCOUNT_SEGMENT13", type: "VARCHAR(200)", remarks: "核算段13")   
            column(name: "ACCOUNT_SEGMENT14", type: "VARCHAR(200)", remarks: "核算段14")   
            column(name: "ACCOUNT_SEGMENT15", type: "VARCHAR(200)", remarks: "核算段15")   
            column(name: "ACCOUNT_SEGMENT16", type: "VARCHAR(200)", remarks: "核算段16")   
            column(name: "ACCOUNT_SEGMENT17", type: "VARCHAR(200)", remarks: "核算段17")   
            column(name: "ACCOUNT_SEGMENT18", type: "VARCHAR(200)", remarks: "核算段18")   
            column(name: "ACCOUNT_SEGMENT19", type: "VARCHAR(200)", remarks: "核算段19")   
            column(name: "ACCOUNT_SEGMENT20", type: "VARCHAR(200)", remarks: "核算段20")   
            column(name: "ACCOUNT_SEGMENT21", type: "VARCHAR(200)", remarks: "核算段21")   
            column(name: "ACCOUNT_SEGMENT22", type: "VARCHAR(200)", remarks: "核算段22")   
            column(name: "ACCOUNT_SEGMENT23", type: "VARCHAR(200)", remarks: "核算段23")   
            column(name: "ACCOUNT_SEGMENT24", type: "VARCHAR(200)", remarks: "核算段24")   
            column(name: "ACCOUNT_SEGMENT25", type: "VARCHAR(200)", remarks: "核算段25")   
            column(name: "ACCOUNT_SEGMENT26", type: "VARCHAR(200)", remarks: "核算段26")   
            column(name: "ACCOUNT_SEGMENT27", type: "VARCHAR(200)", remarks: "核算段27")   
            column(name: "ACCOUNT_SEGMENT28", type: "VARCHAR(200)", remarks: "核算段28")   
            column(name: "ACCOUNT_SEGMENT29", type: "VARCHAR(200)", remarks: "核算段29")   
            column(name: "ACCOUNT_SEGMENT30", type: "VARCHAR(200)", remarks: "核算段30")   
            column(name: "ACCOUNT_SEGMENT31", type: "VARCHAR(200)", remarks: "核算段31")   
            column(name: "ACCOUNT_SEGMENT32", type: "VARCHAR(200)", remarks: "核算段32")   
            column(name: "ACCOUNT_SEGMENT33", type: "VARCHAR(200)", remarks: "核算段33")   
            column(name: "ACCOUNT_SEGMENT34", type: "VARCHAR(200)", remarks: "核算段34")   
            column(name: "ACCOUNT_SEGMENT35", type: "VARCHAR(200)", remarks: "核算段35")   
            column(name: "ACCOUNT_SEGMENT36", type: "VARCHAR(200)", remarks: "核算段36")   
            column(name: "ACCOUNT_SEGMENT37", type: "VARCHAR(200)", remarks: "核算段37")   
            column(name: "ACCOUNT_SEGMENT38", type: "VARCHAR(200)", remarks: "核算段38")   
            column(name: "ACCOUNT_SEGMENT39", type: "VARCHAR(200)", remarks: "核算段39")   
            column(name: "ACCOUNT_SEGMENT40", type: "VARCHAR(200)", remarks: "核算段40")   
            column(name: "ACCOUNT_SEGMENT41", type: "VARCHAR(200)", remarks: "核算段41")   
            column(name: "ACCOUNT_SEGMENT42", type: "VARCHAR(200)", remarks: "核算段42")   
            column(name: "ACCOUNT_SEGMENT43", type: "VARCHAR(200)", remarks: "核算段43")   
            column(name: "ACCOUNT_SEGMENT44", type: "VARCHAR(200)", remarks: "核算段44")   
            column(name: "ACCOUNT_SEGMENT45", type: "VARCHAR(200)", remarks: "核算段45")   
            column(name: "ACCOUNT_SEGMENT46", type: "VARCHAR(200)", remarks: "核算段46")   
            column(name: "ACCOUNT_SEGMENT47", type: "VARCHAR(200)", remarks: "核算段47")   
            column(name: "ACCOUNT_SEGMENT48", type: "VARCHAR(200)", remarks: "核算段48")   
            column(name: "ACCOUNT_SEGMENT49", type: "VARCHAR(200)", remarks: "核算段49")   
            column(name: "ACCOUNT_SEGMENT50", type: "VARCHAR(200)", remarks: "核算段50")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_PAYMENT_REQ_ACCOUNT", indexName: "CSH_PAYMENT_REQ_ACCOUNT_N1") {
                    column(name: "PAYMENT_REQUISITION_LINE_ID")
                }
        
    }


}