package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-04-FND2120-init-table-migration.groovy') {


    changeSet(author: "junkai.lu@hand-china.com", id: "2019-01-04-GLD_PERIOD_SET") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_PERIOD_SET_S', startValue:"10001")
        }

        createTable(tableName: "GLD_PERIOD_SET", remarks: "会计期表") {

            column(name: "PERIOD_SET_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_PERIOD_SET_PK")} 
            column(name: "PERIOD_SET_CODE", type: "VARCHAR(30)", remarks: "会计期代码")  {constraints(nullable:"false")}  
            column(name: "PERIOD_SET_NAME", type: "VARCHAR(500)", remarks: "会计期名称")   
            column(name: "TOTAL_PERIOD_NUM", type: "BIGINT", remarks: "会计期总数")  {constraints(nullable:"false")}  
            column(name: "PERIOD_ADDITIONAL_FLAG", type: "VARCHAR(1)", remarks: "名称附加（SYSCODE：PERIOD_ADDITIONAL_FLAG）")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
                createIndex(tableName: "GLD_PERIOD_SET", indexName: "GLD_PERIOD_SET_N1") {
                    column(name: "PERIOD_SET_NAME")
                }
        
        addUniqueConstraint(columnNames:"PERIOD_SET_CODE",tableName:"GLD_PERIOD_SET",constraintName: "GLD_PERIOD_SET_U1")
    }

    changeSet(author:"junkai.lu@hand-china.com", id: "2019-01-04-GLD_PERIOD_SET_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_PERIOD_SET_TL_S', startValue: "10001")
        }

        createTable(tableName: "GLD_PERIOD_SET_TL", remarks: "会计期表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "PERIOD_SET_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "PERIOD_SET_NAME", type: "VARCHAR(500)",  remarks: "会计期名称")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
    }

    changeSet(author: "junkai.lu@hand-china.com", id: "2019-01-11-GLD_PERIOD_RULE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_PERIOD_RULE_S', startValue:"10001")
        }

        createTable(tableName: "GLD_PERIOD_RULE", remarks: "会计期间规则") {

            column(name: "PERIOD_RULE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_PERIOD_RULE_PK")}
            column(name: "PERIOD_SET_ID", type: "BIGINT", remarks: "会计期ID")  {constraints(nullable:"false")}
            column(name: "PERIOD_ADDITIONAL_NAME", type: "VARCHAR(30)", remarks: "期间名称附加")
            column(name: "PERIOD_NUM", type: "BIGINT", remarks: "月份")  {constraints(nullable:"false")}
            column(name: "MONTH_FROM", type: "BIGINT", remarks: "月份从")  {constraints(nullable:"false")}
            column(name: "MONTH_TO", type: "BIGINT", remarks: "月份到")  {constraints(nullable:"false")}
            column(name: "DATE_FROM", type: "BIGINT", remarks: "日期从")  {constraints(nullable:"false")}
            column(name: "DATE_TO", type: "BIGINT", remarks: "日期到")  {constraints(nullable:"false")}
            column(name: "QUARTER_NUM", type: "BIGINT", remarks: "季度")  {constraints(nullable:"false")}
            column(name: "ADJUSTMENT_FLAG", type: "VARCHAR(1)", remarks: "调整标识")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"PERIOD_SET_ID,PERIOD_NUM",tableName:"GLD_PERIOD_RULE",constraintName: "GLD_PERIOD_RULE_U1")
    }

    changeSet(author: "junkai.lu@hand-china.com", id: "2019-01-09-GLD_PERIOD") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_PERIOD_S', startValue:"10001")
        }

        createTable(tableName: "GLD_PERIOD", remarks: "会计期间表") {

            column(name: "PERIOD_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_PERIOD_PK")}
            column(name: "PERIOD_SET_ID", type: "BIGINT", remarks: "会计期ID")  {constraints(nullable:"false")}
            column(name: "PERIOD_YEAR", type: "BIGINT", remarks: "年")  {constraints(nullable:"false")}
            column(name: "PERIOD_NUM", type: "BIGINT", remarks: "月份")  {constraints(nullable:"false")}
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")  {constraints(nullable:"false")}
            column(name: "ADJUSTMENT_FLAG", type: "VARCHAR(1)", remarks: "调整")  {constraints(nullable:"false")}
            column(name: "INTERNAL_PERIOD_NUM", type: "BIGINT", remarks: "期间序号")  {constraints(nullable:"false")}
            column(name: "START_DATE", type: "DATE", remarks: "日期从")  {constraints(nullable:"false")}
            column(name: "END_DATE", type: "DATE", remarks: "日期到")  {constraints(nullable:"false")}
            column(name: "QUARTER_NUM", type: "BIGINT", remarks: "季度")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"INTERNAL_PERIOD_NUM,PERIOD_SET_ID",tableName:"GLD_PERIOD",constraintName: "GLD_PERIOD_U1")
        addUniqueConstraint(columnNames:"PERIOD_NAME,PERIOD_SET_ID",tableName:"GLD_PERIOD",constraintName: "GLD_PERIOD_U2")
    }

}