package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-30-GL1010-init-table-migration.groovy') {


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-01-30-GLD_SEGMENT_VALUE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_SEGMENT_VALUE_S', startValue:"10001")
        }

        createTable(tableName: "GLD_SEGMENT_VALUE", remarks: "科目段取值列表") {

            column(name: "SEGMENT_ID", type: "BIGINT", remarks: "科目段ID")  {constraints(nullable:"false")}  
            column(name: "VALUE_ID", type: "BIGINT", remarks: "值ID，主键")  {constraints(nullable:"false")}  
            column(name: "VALUE_CODE", type: "VARCHAR(30)", remarks: "值代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(200)", remarks: "值描述")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"VALUE_CODE,SEGMENT_ID",tableName:"GLD_SEGMENT_VALUE",constraintName: "GLD_SEGMENT_VALUE_U1")
    }


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-01-30-GLD_SEGMENT_SOURCE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_SEGMENT_SOURCE_S', startValue:"10001")
        }

        createTable(tableName: "GLD_SEGMENT_SOURCE", remarks: "科目段取值来源") {

            column(name: "SEGMENT_SOURCE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_SEGMENT_SOURCE_PK")} 
            column(name: "SEGMENT_ID", type: "BIGINT", remarks: "科目段ID")  {constraints(nullable:"false")}  
            column(name: "DOCUMENT_CATEGORY", type: "VARCHAR(30)", remarks: "单据类别")  {constraints(nullable:"false")}  
            column(name: "VALUE_TABLE", type: "VARCHAR(30)", remarks: "取值方式")  {constraints(nullable:"false")}  
            column(name: "VALUE_ITEM", type: "VARCHAR(30)", remarks: "取值项")  {constraints(nullable:"false")}  
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
            createIndex(tableName: "GLD_SEGMENT_SOURCE", indexName: "GLD_SEGMENT_SOURCE_N1") {
                    column(name: "SEGMENT_ID")
                }
        
    }


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-01-30-GLD_SOB_SEGMENT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_SOB_SEGMENT_S', startValue:"10001")
        }

        createTable(tableName: "GLD_SOB_SEGMENT", remarks: "帐套级科目段表") {

            column(name: "SEGMENT_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_SOB_SEGMENT_PK")} 
            column(name: "SET_OF_BOOKS_ID", type: "BIGINT", remarks: "账套ID")  {constraints(nullable:"false")}  
            column(name: "SEGMENT_CODE", type: "VARCHAR(150)", remarks: "科目段代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "科目段名称ID")  {constraints(nullable:"false")}  
            column(name: "SEGMENT_FIELD", type: "VARCHAR(30)", remarks: "科目段字段代码（SYSCODE：GLD_SEGMENT_FIELD）")  {constraints(nullable:"false")}  
            column(name: "DR_CR_FLAG", type: "VARCHAR(2)", remarks: "借贷方标志（DR：借方、CR：贷方）")   
            column(name: "SEGMENT_TYPE", type: "VARCHAR(30)", remarks: "科目段取值类型（SYSCODE：GLD_SEGMENT_TYPE）")  {constraints(nullable:"false")}  
            column(name: "SQL_QUERY", type: "VARCHAR(2000)", remarks: "SQL语句")   
            column(name: "SQL_VALIDATE", type: "VARCHAR(2000)", remarks: "SQL验证")   
            column(name: "DEFAULT_TEXT", type: "VARCHAR(200)", remarks: "默认值")   
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
            
        addUniqueConstraint(columnNames:"SEGMENT_FIELD,SET_OF_BOOKS_ID,DR_CR_FLAG",tableName:"GLD_SOB_SEGMENT",constraintName: "GLD_SOB_SEGMENT_U1")
        addUniqueConstraint(columnNames:"DR_CR_FLAG,SEGMENT_CODE,SET_OF_BOOKS_ID",tableName:"GLD_SOB_SEGMENT",constraintName: "GLD_SOB_SEGMENT_U2")
    }

    changeSet(author:"hui.zhao01@hand-china.com", id: "2019-01-30-GLD_SOB_SEGMENT_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_SOB_SEGMENT_TL_S', startValue: "10001")
        }

        createTable(tableName: "GLD_SOB_SEGMENT_TL", remarks: "帐套级科目段表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "SEGMENT_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "科目段名称ID")   
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

    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-02-20-GLD_SOB_RULE_SEGMENT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_SOB_RULE_SEGMENT_S', startValue:"10001")
        }

        createTable(tableName: "GLD_SOB_RULE_SEGMENT", remarks: "帐套级会计规则明细-分配科目段") {

            column(name: "RULE_SEGMENT_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_SOB_RULE_SEGMENT_PK")}
            column(name: "RULE_ID", type: "BIGINT", remarks: "账套级会计规则明细")  {constraints(nullable:"false")}
            column(name: "SEGMENT_ID", type: "BIGINT", remarks: "账套级科目段")  {constraints(nullable:"false")}
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}
            column(name: "REQUIRED_FLAG", type: "VARCHAR(1)", remarks: "必输标志")  {constraints(nullable:"false")}
            column(name: "SOURCE_CODE", type: "VARCHAR(30)", remarks: "来源（SYSCODE:GLD_SOB_RULE_SEGMENT_SOURCE）")  {constraints(nullable:"false")}
            column(name: "DEFAULT_VALUE", type: "VARCHAR(255)", remarks: "默认值")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"RULE_ID,SEGMENT_ID",tableName:"GLD_SOB_RULE_SEGMENT",constraintName: "GLD_SOB_RULE_SEGMENT_U1")
    }

    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-02-21-GLD_SOB_RULE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_SOB_RULE_S', startValue:"10001")
        }

        createTable(tableName: "GLD_SOB_RULE", remarks: "账套级会计规则明细") {

            column(name: "RULE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_SOB_RULE_PK")}
            column(name: "RULE_GROUP_ID", type: "BIGINT", remarks: "账套级会计规则组")  {constraints(nullable:"false")}
            column(name: "RULE_CODE", type: "VARCHAR(30)", remarks: "账套级会计规则代码")  {constraints(nullable:"false")}
            column(name: "RULE_NAME", type: "VARCHAR(500)", remarks: "账套级会计规则名称")
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
        createIndex(tableName: "GLD_SOB_RULE", indexName: "GLD_SOB_RULE_N1") {
            column(name: "RULE_NAME")
        }

        addUniqueConstraint(columnNames:"RULE_CODE,RULE_GROUP_ID",tableName:"GLD_SOB_RULE",constraintName: "GLD_SOB_RULE_U1")
    }

    changeSet(author:"hui.zhao01@hand-china.com", id: "2019-02-21-GLD_SOB_RULE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_SOB_RULE_TL_S', startValue: "10001")
        }

        createTable(tableName: "GLD_SOB_RULE_TL", remarks: "账套级会计规则明细多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "RULE_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")}
            column(name: "RULE_NAME", type: "VARCHAR(500)",  remarks: "账套级会计规则名称")
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

    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-02-21-GLD_SOB_RULE_ACCOUNT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_SOB_RULE_ACCOUNT_S', startValue:"10001")
        }

        createTable(tableName: "GLD_SOB_RULE_ACCOUNT", remarks: "账套级会计规则明细-分配科目") {

            column(name: "RULE_ACCOUNT_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_SOB_RULE_ACCOUNT_PK")}
            column(name: "RULE_ID", type: "BIGINT", remarks: "帐套级会计规则明细")  {constraints(nullable:"false")}
            column(name: "ACCOUNT_ID", type: "BIGINT", remarks: "科目ID")  {constraints(nullable:"false")}
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

        addUniqueConstraint(columnNames:"RULE_ID,ACCOUNT_ID",tableName:"GLD_SOB_RULE_ACCOUNT",constraintName: "GLD_SOB_RULE_ACCOUNT_U1")
    }


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-02-21-GLD_SOB_RULE_GP_REF_AE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_SOB_RULE_GP_REF_AE_S', startValue:"10001")
        }

        createTable(tableName: "GLD_SOB_RULE_GP_REF_AE", remarks: "账套级会计规则组-分配核算主体") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_SOB_RULE_GP_REF_AE_PK")}
            column(name: "RULE_GROUP_ID", type: "BIGINT", remarks: "帐套级会计规则组")  {constraints(nullable:"false")}
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体")  {constraints(nullable:"false")}
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

        addUniqueConstraint(columnNames:"RULE_GROUP_ID,ACC_ENTITY_ID",tableName:"GLD_SOB_RULE_GP_REF_AE",constraintName: "GLD_SOB_RULE_GP_REF_AE_U1")
    }

}