package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-2-18-FND2410-init-table-migration.groovy') {


    changeSet(author: "jialin.xing@hand-china.com", id: "2019-02-18-PUR_SYSTEM_VENDER") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'PUR_SYSTEM_VENDER_S', startValue:"10001")
        }

        createTable(tableName: "PUR_SYSTEM_VENDER", remarks: "系统级供应商主数据") {

            column(name: "VENDER_ID", type: "BIGINT",autoIncrement: true, startWith: "10001", remarks: "供应商ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "PUR_SYSTEM_VENDER_PK")}
            column(name: "VENDER_CODE", type: "VARCHAR(30)", remarks: "供应商代码")  {constraints(nullable:"false")}  
            column(name: "VENDER_TYPE_ID", type: "BIGINT", remarks: "供应商类型ID")
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述")
            column(name: "ADDRESS", type: "VARCHAR(2000)", remarks: "地址")
            column(name: "ARTIFICIAL_PERSON", type: "VARCHAR(30)", remarks: "法人代表")   
            column(name: "TAX_ID_NUMBER", type: "VARCHAR(1000)", remarks: "税务登记号")   
            column(name: "BANK_BRANCH_CODE", type: "VARCHAR(2000)", remarks: "开户银行")   
            column(name: "BANK_ACCOUNT_CODE", type: "VARCHAR(2000)", remarks: "银行账号")   
            column(name: "PAYMENT_TERM_ID", type: "BIGINT", remarks: "付款条款ID")
            column(name: "PAYMENT_METHOD", type: "VARCHAR(30)", remarks: "付款方式")   
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种")   
            column(name: "TAX_TYPE_ID", type: "BIGINT", remarks: "税种")
            column(name: "ENABLED_ITEM_FLAG", type: "VARCHAR(1)", remarks: "启用物料管理标志")  {constraints(nullable:"false")}  
            column(name: "APPROVED_VENDER_FLAG", type: "VARCHAR(1)", remarks: "合格供应商标志")   
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"VENDER_CODE",tableName:"PUR_SYSTEM_VENDER",constraintName: "PUR_SYSTEM_VENDERS_U1")
    }

    changeSet(author:"jialin.xing@hand-china.com", id: "2019-02-18-PUR_SYSTEM_VENDER_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'PUR_SYSTEM_VENDER_TL_S', startValue: "10001")
        }

        createTable(tableName: "PUR_SYSTEM_VENDER_TL", remarks: "系统级供应商主数据多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "VENDER_ID", type: "BIGINT",  remarks: "供应商ID")  {constraints(nullable: "false", primaryKey: "true")}
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


    changeSet(author: "jialin.xing@hand-china.com", id: "2019-02-18-FND_TAX_TYPE_CODE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_TAX_TYPE_CODE_S', startValue:"10001")
        }

        createTable(tableName: "FND_TAX_TYPE_CODE", remarks: "税率定义") {

            column(name: "TAX_TYPE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_TAX_TYPE_CODE_PK")}
            column(name: "COUNTRY_CODE", type: "VARCHAR(30)", remarks: "国家代码")  {constraints(nullable:"false")}
            column(name: "TAX_TYPE_CODE", type: "VARCHAR(30)", remarks: "税种代码")  {constraints(nullable:"false")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "税种名称")
            column(name: "TAX_CATEGORY", type: "VARCHAR(30)", remarks: "税率类别（SYSCODE：FND_TAX_CATEGORY  价内/价外税）")  {constraints(nullable:"false")}
            column(name: "TAX_TYPE_RATE", type: "NUMBER", remarks: "税率")  {constraints(nullable:"false")}
            column(name: "AUTO_CALCULATION_FLAG", type: "VARCHAR(1)", remarks: "自动计算标志")  {constraints(nullable:"false")}
            column(name: "WITHHOLDING_FLAG", type: "VARCHAR(1)", remarks: "代扣缴标志")  {constraints(nullable:"false")}
            column(name: "SALE_TAX_FLAG", type: "VARCHAR(1)", remarks: "销项税率标志")  {constraints(nullable:"false")}
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"TAX_TYPE_CODE",tableName:"FND_TAX_TYPE_CODE",constraintName: "FND_TAX_TYPE_CODE_U1")
    }

    changeSet(author:"jialin.xing@hand-china.com", id: "2019-02-18-FND_TAX_TYPE_CODE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_TAX_TYPE_CODE_TL_S', startValue: "10001")
        }

        createTable(tableName: "FND_TAX_TYPE_CODE_TL", remarks: "税率定义多语言表") {
            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "TAX_TYPE_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "税种名称")
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

    changeSet(author: "jialin.xing@gmail.com", id: "2019-02-19-CSH_PAYMENT_TERM") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_TERM_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_TERM", remarks: "付款条款表") {

            column(name: "PAYMENT_TERM_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "条款代码ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_TERM_PK")}
            column(name: "PAYMENT_TERM_CODE", type: "VARCHAR(30)", remarks: "条款代码")  {constraints(nullable:"false")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述")  {constraints(nullable:"false")}
            column(name: "DUE_DATES", type: "BIGINT", remarks: "付款期限(天)")  {constraints(nullable:"false")}
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志(Y启用，N不启用)")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"PAYMENT_TERM_CODE",tableName:"CSH_PAYMENT_TERM",constraintName: "CSH_PAYMENT_TERM_U1")
    }

    changeSet(author:"jialin.xing@gmail.com", id: "2019-02-19-CSH_PAYMENT_TERM_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_TERM_TL_S', startValue: "10001")
        }

        createTable(tableName: "CSH_PAYMENT_TERM_TL", remarks: "付款条款表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "PAYMENT_TERM_ID", type: "BIGINT",  remarks: "条款代码ID")  {constraints(nullable: "false", primaryKey: "true")}
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

    changeSet(author: "jialin.xing@hand-china.com", id: "2019-02-20-PUR_SYSTEM_VENDER_REF_AE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'PUR_SYSTEM_VENDER_REF_AE_S', startValue:"10001")
        }

        createTable(tableName: "PUR_SYSTEM_VENDER_REF_AE", remarks: "供应商分配核算主体表") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "PUR_SYSTEM_VENDER_REF_AE_PK")}
            column(name: "VENDER_ID", type: "BIGINT", remarks: "供应商ID")  {constraints(nullable:"false")}
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"ACC_ENTITY_ID,VENDER_ID",tableName:"PUR_SYSTEM_VENDER_REF_AE",constraintName: "PUR_SYSTEM_VENDER_REF_AE_U1")
    }

    changeSet(author: "jialin.xing@hand-china.com", id: "2019-02-21-PUR_VENDER_ACCOUNT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'PUR_VENDER_ACCOUNT_S', startValue:"10001")
        }

        createTable(tableName: "PUR_VENDER_ACCOUNT", remarks: "供应商银行账户表") {

            column(name: "ACCOUNT_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "PUR_VENDER_ACCOUNT_PK")}
            column(name: "VENDER_ID", type: "BIGINT", remarks: "供应商ID")  {constraints(nullable:"false")}
            column(name: "BANK_ID", type: "BIGINT", remarks: "银行ID")  {constraints(nullable:"false")}
            column(name: "CNAPS_CODE", type: "VARCHAR(50)", remarks: "联行号/SWIFT")  {constraints(nullable:"false")}
            column(name: "PROVINCE_CODE", type: "VARCHAR(30)", remarks: "省代码")
            column(name: "PROVINCE_NAME", type: "VARCHAR(200)", remarks: "省名称")
            column(name: "CITY_CODE", type: "VARCHAR(30)", remarks: "市代码")
            column(name: "CITY_NAME", type: "VARCHAR(200)", remarks: "市名称")
            column(name: "ACCOUNT_NAME", type: "VARCHAR(200)", remarks: "银行户名")
            column(name: "ACCOUNT_NUMBER", type: "VARCHAR(200)", remarks: "银行帐号")
            column(name: "NOTES", type: "VARCHAR(4000)", remarks: "备注")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "PUR_VENDER_ACCOUNT", indexName: "PUR_VENDER_ACCOUNT_N1") {
            column(name: "VENDER_ID")
        }

    }

    changeSet(author: "jialin.xing@hand-china.com", id: "2019-02-21-PUR_VENDER_ACCOUNT_REF_AE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'PUR_VENDER_ACCOUNT_REF_AE_S', startValue:"10001")
        }

        createTable(tableName: "PUR_VENDER_ACCOUNT_REF_AE", remarks: "供应商银行账户分配核算主体表") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "PUR_VENDER_ACCOUNT_REF_AE_PK")}
            column(name: "ACCOUNT_ID", type: "BIGINT", remarks: "供应商银行账户ID")  {constraints(nullable:"false")}
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}
            column(name: "PRIMARY_FLAG", type: "VARCHAR(1)", remarks: "主帐号标志")  {constraints(nullable:"false")}
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"ACCOUNT_ID,ACC_ENTITY_ID",tableName:"PUR_VENDER_ACCOUNT_REF_AE",constraintName: "PUR_VENDER_ACCOUNT_REF_AE_U1")
    }

    changeSet(author: "jialin.xing@hand-china.com", id: "2019-02-25-PUR_VENDER_TAX") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'PUR_VENDER_TAX_S', startValue:"10001")
        }

        createTable(tableName: "PUR_VENDER_TAX", remarks: "供应商税务信息表") {

            column(name: "TAXPAYER_NUMBER", type: "VARCHAR(200)", remarks: "纳税人识别号")
            column(name: "ADDRESS", type: "VARCHAR(2000)", remarks: "地址电话")
            column(name: "TAXPAYER_BANK", type: "VARCHAR(200)", remarks: "开户行及帐号")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "TAX_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "PUR_VENDER_TAX_PK")}
            column(name: "VENDER_ID", type: "BIGINT", remarks: "供应商ID")  {constraints(nullable:"false")}
            column(name: "TAXPAYER_TYPE", type: "VARCHAR(200)", remarks: "纳税人类型（SYSCODE：TYPE_OF_TAXPAYER）")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "PUR_VENDER_TAX", indexName: "PUR_VENDER_TAX_N1") {
            column(name: "VENDER_ID")
        }

    }


    changeSet(author: "jialin.xing@hand-china.com", id: "2019-02-25-PUR_VENDER_TAX_REF_AE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'PUR_VENDER_TAX_REF_AE_S', startValue:"10001")
        }

        createTable(tableName: "PUR_VENDER_TAX_REF_AE", remarks: "供应商税务信息分配核算主体表") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "PUR_VENDER_TAX_REF_AE_PK")}
            column(name: "TAX_ID", type: "BIGINT", remarks: "供应商纳税信息ID")  {constraints(nullable:"false")}
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"TAX_ID,ACC_ENTITY_ID",tableName:"PUR_VENDER_TAX_REF_AE",constraintName: "PUR_VENDER_TAX_REF_AE_U1")
    }

}