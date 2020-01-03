package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-13-CSH1016-init-table-migration.groovy') {


    changeSet(author: "xiuxian.wu@hand-china.com", id: "2019-03-13-CSH_BANK_BRANCHE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_BANK_BRANCHE_S', startValue:"10001")
        }

        createTable(tableName: "CSH_BANK_BRANCHE", remarks: "公司银行定义表") {

            column(name: "ADDRESS", type: "VARCHAR(2000)", remarks: "地址")   
            column(name: "CONTACT_NAME", type: "VARCHAR(30)", remarks: "联系人")   
            column(name: "E_MAIL_ADDRESS", type: "VARCHAR(100)", remarks: "E-Mail")   
            column(name: "PHONE", type: "VARCHAR(30)", remarks: "电话")   
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "BANK_BRANCH_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_BANK_BRANCHE_PK")} 
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "BANK_ID", type: "BIGINT", remarks: "银行ID")  {constraints(nullable:"false")}  
            column(name: "CNAP_ID", type: "VARCHAR(30)", remarks: "分行ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"CNAP_ID,ACC_ENTITY_ID,BANK_ID",tableName:"CSH_BANK_BRANCHE",constraintName: "CSH_BANK_BRANCHES_U1")
    }


    changeSet(author: "xiuxian.wu@hand-china.com", id: "2019-03-13-CSH_BANK_CONTACT_PERSON") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_BANK_CONTACT_PERSON_S', startValue:"10001")
        }

        createTable(tableName: "CSH_BANK_CONTACT_PERSON", remarks: "银行联系人表") {

            column(name: "CONTACT_PERSONS_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "联系人ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_BANK_CONTACT_PERSON_PK")} 
            column(name: "BANK_BRANCH_ID", type: "BIGINT", remarks: "分行ID")  {constraints(nullable:"false")}  
            column(name: "CONTACT_PERSON_TYPE", type: "VARCHAR(30)", remarks: "联系人类型（CONTACT_PERSON联系人，SIGNATURE_PERSON印签人）")  {constraints(nullable:"false")}  
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")  {constraints(nullable:"false")}  
            column(name: "START_DATE_ACTIVE", type: "DATETIME", remarks: "开始日期")  {constraints(nullable:"false")}  
            column(name: "END_DATE_ACTIVE", type: "DATETIME", remarks: "结束日期")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"EMPLOYEE_ID,CONTACT_PERSON_TYPE,BANK_BRANCH_ID",tableName:"CSH_BANK_CONTACT_PERSON",constraintName: "CSH_BANK_CONTACT_PERSONS_U1")
    }


    changeSet(author: "xiuxian.wu@hand-china.com", id: "2019-03-13-CSH_BANK_ACCOUNT_ASGN_ACC") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_BANK_ACCOUNT_ASGN_ACC_S', startValue:"10001")
        }

        createTable(tableName: "CSH_BANK_ACCOUNT_ASGN_ACC", remarks: "银行账户分配银行科目") {

            column(name: "ASSIGN_ACC_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_BANK_ACCOUNT_ASGN_ACC_PK")} 
            column(name: "BANK_ACCOUNT_ID", type: "BIGINT", remarks: "银行账户ID")  {constraints(nullable:"false")}  
            column(name: "SET_OF_BOOKS_ID", type: "BIGINT", remarks: "账套ID")  {constraints(nullable:"false")}  
            column(name: "CASH_ACCOUNT_ID", type: "BIGINT", remarks: "银行科目ID")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"SET_OF_BOOKS_ID,CASH_ACCOUNT_ID,BANK_ACCOUNT_ID",tableName:"CSH_BANK_ACCOUNT_ASGN_ACC",constraintName: "CSH_BANK_ACCOUNT_ASGN_ACC_U1")
    }


    changeSet(author: "xiuxian.wu@hand-china.com", id: "2019-03-13-CSH_BANK_REF_OFFER_FORMAT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_BANK_REF_OFFER_FORMAT_S', startValue:"10001")
        }

        createTable(tableName: "CSH_BANK_REF_OFFER_FORMAT", remarks: "银行关联报盘格式") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_BANK_REF_OFFER_FORMAT_PK")} 
            column(name: "REF_TYPE", type: "VARCHAR(30)", remarks: "关联类型（BANKS/银行，BRANCH/分行，ACCOUNT/账户）")  {constraints(nullable:"false")}  
            column(name: "REF_LINE_ID", type: "BIGINT", remarks: "关联ID（银行ID/分行ID/账户ID）")  {constraints(nullable:"false")}  
            column(name: "FORMAT_HDS_ID", type: "BIGINT", remarks: "报盘格式头ID")  {constraints(nullable:"false")}  
            column(name: "START_DATE", type: "DATETIME", remarks: "有效期从")  {constraints(nullable:"false")}  
            column(name: "END_DATE", type: "DATETIME", remarks: "有效期至")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"FORMAT_HDS_ID,REF_TYPE,REF_LINE_ID",tableName:"CSH_BANK_REF_OFFER_FORMAT",constraintName: "CSH_BANK_REF_OFFER_FORMAT_U1")
    }


}