package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2018-01-04-GLD2020-init-table-migration.groovy') {
	
	changeSet(author: "hui.lu@hand-china.com", id: "2019-01-04-GLD_ACC_ENTITY_HIERARCHY") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_ACC_ENTITY_HIERARCHY_S', startValue:"10001")
        }

        createTable(tableName: "GLD_ACC_ENTITY_HIERARCHY", remarks: "核算主体层次表") {

            column(name: "HIERARCHY_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_ACC_ENTITY_HIERARCHY_PK")} 
            column(name: "PARENT_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "ENTITY_ID", type: "BIGINT", remarks: "子核算主体ID")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"ENTITY_ID,PARENT_ENTITY_ID",tableName:"GLD_ACC_ENTITY_HIERARCHY",constraintName: "GLD_ACC_ENTITY_HIERARCHY_U1")
    }


    changeSet(author: "hui.lu@hand-china.com", id: "2019-01-04-GLD_ACCOUNTING_ENTITY") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_ACCOUNTING_ENTITY_S', startValue:"10001")
        }

        createTable(tableName: "GLD_ACCOUNTING_ENTITY", remarks: "核算主体表") {

            column(name: "ACC_ENTITY_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "核算主体ID，主键供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_ACCOUNTING_ENTITY_PK")} 
            column(name: "ACC_ENTITY_CODE", type: "VARCHAR(30)", remarks: "核算主体代码")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_NAME", type: "VARCHAR(500)", remarks: "核算主体名称")  {constraints(nullable:"false")}  
            column(name: "FUNCTIONAL_CURRENCY_CODE", type: "VARCHAR(10)", remarks: "本位币")   
            column(name: "PAY_CURRENCY_CODE", type: "VARCHAR(10)", remarks: "支付币种")  {constraints(nullable:"false")}  
            column(name: "COMPANY_TYPE", type: "VARCHAR(30)", remarks: "公司类型")  {constraints(nullable:"false")}  
            column(name: "START_DATE_ACTIVE", type: "DATETIME", remarks: "有效日期从")  {constraints(nullable:"false")}  
            column(name: "END_DATE_ACTIVE", type: "DATETIME", remarks: "有效日期至")   
            column(name: "DEFAULT_SET_OF_BOOKS_ID", type: "BIGINT", remarks: "默认账套")   
            column(name: "DEFAULT_TIMEZONE_ID", type: "BIGINT", remarks: "默认时区")   
            column(name: "DEFAULT_LANGUAGE", type: "VARCHAR(30)", remarks: "默认语言")   
            column(name: "DEFAULT_COUNTRY_CODE", type: "VARCHAR(30)", remarks: "默认国家")   
            column(name: "TAXPAYER_TYPE", type: "VARCHAR(30)", remarks: "纳税人类型")   
            column(name: "TAXPAYER_NUMBER", type: "VARCHAR(30)", remarks: "纳税人识别号")   
            column(name: "PHONE", type: "VARCHAR(30)", remarks: "联系电话")   
            column(name: "ADDRESS", type: "VARCHAR(255)", remarks: "地址")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "BANK_ACCOUNT", type: "VARCHAR(50)", remarks: "开户行及账号")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "GLD_ACCOUNTING_ENTITY", indexName: "GLD_ACCOUNTING_ENTITIES_N1") {
                    column(name: "ACC_ENTITY_NAME")
                }
            
        addUniqueConstraint(columnNames:"ACC_ENTITY_CODE",tableName:"GLD_ACCOUNTING_ENTITY",constraintName: "GLD_ACCOUNTING_ENTITIES_U1")
    }

    changeSet(author:"hui.lu@hand-china.com", id: "2019-01-04-GLD_ACCOUNTING_ENTITY_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_ACCOUNTING_ENTITY_TL_S', startValue: "10001")
        }

        createTable(tableName: "GLD_ACCOUNTING_ENTITY_TL", remarks: "核算主体表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "ACC_ENTITY_ID", type: "BIGINT",remarks: "核算主体ID，主键供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")}
            column(name: "ACC_ENTITY_NAME", type: "VARCHAR(500)",  remarks: "核算主体名称")   
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

    changeSet(author: "hui.lu@hand-china.com", id: "2019-01-09-SYS_STANDARD_TIMEZONE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SYS_STANDARD_TIMEZONE_S', startValue:"10001")
        }

        createTable(tableName: "SYS_STANDARD_TIMEZONE", remarks: "标准时区定义") {

            column(name: "STANDARD_TIMEZONE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "标准时区ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SYS_STANDARD_TIMEZONE_PK")}
            column(name: "STANDARD_TIMEZONE_CODE", type: "VARCHAR(30)", remarks: "标准时区代码")
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "NUMBER", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "NUMBER", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

    }

    changeSet(author:"hui.lu@hand-china.com", id: "2019-01-09-SYS_STANDARD_TIMEZONE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SYS_STANDARD_TIMEZONE_TL_S', startValue: "10001")
        }

        createTable(tableName: "SYS_STANDARD_TIMEZONE_TL", remarks: "标准时区定义多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "STANDARD_TIMEZONE_ID", type: "BIGINT",  remarks: "标准时区ID")  {constraints(nullable: "false", primaryKey: "true")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "描述")
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

    changeSet(author: "hui.lu@hand-china.com", id: "2019-01-11-FND_COUNTRY_CODE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_COUNTRY_CODE_S', startValue:"10001")
        }

        createTable(tableName: "FND_COUNTRY_CODE", remarks: "国家代码") {

            column(name: "COUNTRY_CODE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_COUNTRY_CODE_PK")}
            column(name: "COUNTRY_CODE", type: "VARCHAR(30)", remarks: "国家代码") {constraints(nullable:"false")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述")
            column(name: "CREATED_BY", type: "NUMBER", remarks: "创建用户ID")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "NUMBER", remarks: "最后更新用户ID")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"DESCRIPTION",tableName:"FND_COUNTRY_CODE",constraintName: "FND_COUNTRY_CODE_U1")
        addUniqueConstraint(columnNames:"COUNTRY_CODE",tableName:"FND_COUNTRY_CODE",constraintName: "FND_COUNTRY_CODE_U2")
    }

    changeSet(author:"hui.lu@hand-china.com", id: "2019-01-11-FND_COUNTRY_CODE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_COUNTRY_CODE_TL_S', startValue: "10001")
        }

        createTable(tableName: "FND_COUNTRY_CODE_TL", remarks: "国家代码多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "COUNTRY_CODE_ID", type: "BIGINT",  remarks: "国家代码ID")  {constraints(nullable: "false", primaryKey: "true")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "描述")
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

    changeSet(author: "hui.lu@hand-china.com", id: "2019-01-21-GLD_ACC_ENTITY_TAX") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_ACC_ENTITY_TAX_S', startValue:"10001")
        }

        createTable(tableName: "GLD_ACC_ENTITY_TAX", remarks: "核算主体纳税人信息表") {

            column(name: "TAX_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_ACC_ENTITY_TAX_PK")}
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}
            column(name: "TAXPAYER_TYPE", type: "VARCHAR(200)", remarks: "纳税人类型（SYSCODE：TYPE_OF_TAXPAYER）")  {constraints(nullable:"false")}
            column(name: "TAXPAYER_NUMBER", type: "VARCHAR(200)", remarks: "纳税人识别号")
            column(name: "ADDRESS", type: "VARCHAR(2000)", remarks: "地址电话")
            column(name: "TAXPAYER_BANK", type: "VARCHAR(200)", remarks: "开户行及帐号")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "NUMBER", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "NUMBER", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "GLD_ACC_ENTITY_TAX", indexName: "GLD_ACC_ENTITY_TAX_N1") {
            column(name: "ACC_ENTITY_ID")
        }

    }
}