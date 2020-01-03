package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-22-EXP1010-init-table-migration.groovy') {


    changeSet(author: "yuting.gui@hand-china.com", id: "2019-01-22-CSH_BANK_ACCOUNT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_BANK_ACCOUNT_S', startValue:"10001")
        }

        createTable(tableName: "CSH_BANK_ACCOUNT", remarks: "银行账户表") {

            column(name: "BANK_ACCOUNT_ID", type: "BIGINT",autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_BANK_ACCOUNT_PK")}
            column(name: "BANK_BRANCH_ID", type: "BIGINT", remarks: "银行分行ID")  {constraints(nullable:"false")}
            column(name: "ACCOUNT_TYPE", type: "VARCHAR(30)", remarks: "账户类型（COMPANY：公司的银行帐户、CUSTOMER：客户的银行帐户、VENDER：供应商的银行帐户）")   
            column(name: "BANK_ACCOUNT_NAME", type: "VARCHAR(500)", remarks: "账户名称ID")  {constraints(nullable:"false")}  
            column(name: "BANK_ACCOUNT_NUM", type: "VARCHAR(50)", remarks: "银行帐号")  {constraints(nullable:"false")}  
            column(name: "CURRENCY_CODE", type: "VARCHAR(15)", remarks: "币种")  {constraints(nullable:"false")}  
            column(name: "COUNTRY_CODE", type: "VARCHAR(30)", remarks: "国家")   
            column(name: "TIMEZONE_ID", type: "BIGINT", remarks: "时区")
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT", remarks: "责任中心")
            column(name: "CUSTOMER_ID", type: "BIGINT", remarks: "客户ID")
            column(name: "VENDER_ID", type: "BIGINT", remarks: "供应商ID")
            column(name: "RECEIPT_FLAG", type: "VARCHAR(1)", remarks: "回单凭证记录")  {constraints(nullable:"false")}  
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
            createIndex(tableName: "CSH_BANK_ACCOUNT", indexName: "CSH_BANK_ACCOUNT_N1") {
                    column(name: "BANK_BRANCH_ID")
                }
                createIndex(tableName: "CSH_BANK_ACCOUNT", indexName: "CSH_BANK_ACCOUNT_N2") {
                    column(name: "BANK_ACCOUNT_NAME")
                }
        
    }

    changeSet(author:"yuting.gui@hand-china.com", id: "2019-01-22-CSH_BANK_ACCOUNT_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_BANK_ACCOUNT_TL_S', startValue: "10001")
        }

        createTable(tableName: "CSH_BANK_ACCOUNT_TL", remarks: "银行账户表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "BANK_ACCOUNT_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")}
            column(name: "BANK_ACCOUNT_NAME", type: "VARCHAR(500)",  remarks: "账户名称ID")   
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

    changeSet(author: "yuting.gui@hand-china.com", id: "2019-01-22-CSH_BANK_BRANCH") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_BANK_BRANCH_S', startValue:"10001")
        }

        createTable(tableName: "CSH_BANK_BRANCH", remarks: "公司银行定义表") {

            column(name: "ADDRESS", type: "VARCHAR(2000)", remarks: "地址")   
            column(name: "CONTACT_NAME", type: "VARCHAR(30)", remarks: "联系人")   
            column(name: "E_MAIL_ADDRESS", type: "VARCHAR(100)", remarks: "E-Mail")   
            column(name: "PHONE", type: "VARCHAR(30)", remarks: "电话")   
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "BANK_BRANCH_ID", type: "BIGINT", remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_BANK_BRANCH_PK")}
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}
            column(name: "BANK_ID", type: "BIGINT", remarks: "银行ID")  {constraints(nullable:"false")}
            column(name: "CNAPS_ID", type: "BIGINT", remarks: "分行ID")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"CNAPS_ID,ACC_ENTITY_ID,BANK_ID",tableName:"CSH_BANK_BRANCH",constraintName: "CSH_BANK_BRANCH_U1")
    }

    changeSet(author: "yuting.gui@hand-china.com", id: "2019-01-22-CSH_CNAP") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_CNAP_S', startValue:"10001")
        }

        createTable(tableName: "CSH_CNAPS", remarks: "银行分行定义表") {

            column(name: "CNAPS_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_CNAPS_PK")}
            column(name: "BANK_ID", type: "BIGINT", remarks: "银行ID")  {constraints(nullable:"false")}
            column(name: "BANK_LOCATION_CODE", type: "VARCHAR(30)", remarks: "CNAPS号")  {constraints(nullable:"false")}
            column(name: "BANK_LOCATION_NAME", type: "VARCHAR(2000)", remarks: "支行名称")
            column(name: "PROVINCE_CODE", type: "VARCHAR(30)", remarks: "支行省份代码")
            column(name: "PROVINCE_NAME", type: "VARCHAR(2000)", remarks: "支行省份名称")
            column(name: "CITY_CODE", type: "VARCHAR(30)", remarks: "支行城市代码")
            column(name: "CITY_NAME", type: "VARCHAR(2000)", remarks: "支行城市名称")
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
        createIndex(tableName: "CSH_CNAPS", indexName: "CSH_CNAPS_N1") {
            column(name: "BANK_ID")
        }

        addUniqueConstraint(columnNames:"BANK_LOCATION_CODE",tableName:"CSH_CNAPS",constraintName: "CSH_CNAPS_U1")
    }

    changeSet(author: "yuting.gui@hand-china.com", id: "2019-01-24-FND_UNIT_DIM_ASSIGN") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_UNIT_DIM_ASSIGN_S', startValue:"10001")
        }

        createTable(tableName: "FND_UNIT_DIM_ASSIGN", remarks: "部门维度分配表") {

            column(name: "ASSIGN_ID", type: "BIGINT",autoIncrement: true, startWith: "10001", remarks: "分配ID(pk)")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_UNIT_DIM_ASSIGN_PK")}
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")  {constraints(nullable:"false")}
            column(name: "DIMENSION_ID", type: "BIGINT", remarks: "维度ID")  {constraints(nullable:"false")}
            column(name: "DEFAULT_DIMENSION_VALUE_ID", type: "BIGINT", remarks: "默认维值ID")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"UNIT_ID,DIMENSION_ID",tableName:"FND_UNIT_DIM_ASSIGN",constraintName: "FND_UNIT_DIM_ASSIGN_U1")
    }

    changeSet(author: "yuting.gui@hand-china.com", id: "2019-03-27-FND_UNIT_DIM_VALUE_ASSIGN") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_UNIT_DIM_VALUE_ASSIGN_S', startValue:"10001")
        }

        createTable(tableName: "FND_UNIT_DIM_VALUE_ASSIGN", remarks: "部门维值分配表") {

            column(name: "ASSIGN_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "维度分配ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_UNIT_DIM_VALUE_ASSIGN_PK")}
            column(name: "DIM_ASSIGN_ID", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")  {constraints(nullable:"false")}
            column(name: "DIMENSION_ID", type: "BIGINT", remarks: "维度ID")  {constraints(nullable:"false")}
            column(name: "DIMENSION_VALUE_ID", type: "BIGINT", remarks: "维值ID")  {constraints(nullable:"false")}
            column(name: "DEFAULT_FLAG", type: "VARCHAR(1)", remarks: "默认标志")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"DIMENSION_VALUE_ID,UNIT_ID",tableName:"FND_UNIT_DIM_VALUE_ASSIGN",constraintName: "FND_UNIT_DIM_VALUE_ASSIGN_U1")
    }


}