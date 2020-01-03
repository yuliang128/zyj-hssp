package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-16-FND2910-init-table-migration.groovy') {


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-01-18-GLD_USAGE_CODE_USER_FUNCTION") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_USAGE_CODE_USER_FUNCTION_S', startValue:"10001")
        }

        createTable(tableName: "GLD_USAGE_CODE_USER_FUNCTION", remarks: "用途代码用户自定义函数表") {

            column(name: "USAGE_CODE_UF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_USAGE_CODE_USER_FUNC_PK")}
            column(name: "USAGE_CODE", type: "VARCHAR(30)", remarks: "用户代码")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")   
            column(name: "USER_DEFINE_FUNCTION", type: "VARCHAR(61)", remarks: "用户自定义函数名")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
    
        addUniqueConstraint(columnNames:"USAGE_CODE",tableName:"GLD_USAGE_CODE_USER_FUNCTION",constraintName: "GLD_USAGE_CODE_USER_FUNC_U1")
    }


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-01-18-GLD_USAGE_CODE_TMP") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_USAGE_CODE_TMP_S', startValue:"10001")
        }

        createTable(tableName: "GLD_USAGE_CODE_TMP", remarks: "") {

            column(name: "USAGE_CODE_TMP_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_USAGE_CODE_TMP_PK")}
            column(name: "USAGE_CODE", type: "VARCHAR(500)", remarks: "")   
            column(name: "COPY_COMPANY_ID", type: "BIGINT", remarks: "")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

    }


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-01-18-GLD_USAGE_CODE_COPY_LOG") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_USAGE_CODE_COPY_LOG_S', startValue:"10001")
        }

        createTable(tableName: "GLD_USAGE_CODE_COPY_LOG", remarks: "用途代码复制日志表") {

            column(name: "LOG_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_USAGE_CODE_COPY_LOG_PK")} 
            column(name: "USER_ID", type: "BIGINT", remarks: "操作用户")   
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司")   
            column(name: "USAGE_CODE", type: "VARCHAR(200)", remarks: "用途代码")   
            column(name: "GROUP_NAME", type: "VARCHAR(200)", remarks: "匹配组")   
            column(name: "MESSAGE", type: "VARCHAR(200)", remarks: "操作消息")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")   
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户")   
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")   
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
    
    }


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-01-18-GLD_USAGE_CODE_CATALOG") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_USAGE_CODE_CATALOG_S', startValue:"10001")
        }

        createTable(tableName: "GLD_USAGE_CODE_CATALOG", remarks: "用途代码目录表") {

            column(name: "USAGE_CODE_CATALOG_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_USAGE_CODE_CATALOG_PK")}
            column(name: "USAGE_CODE", type: "VARCHAR(30)", remarks: "用途代码")  {constraints(nullable:"false")}  
            column(name: "MAPPING_CONDITION_CODE", type: "VARCHAR(30)", remarks: "目录编码")  {constraints(nullable:"false")}  
            column(name: "LAYOUT_PRIORITY", type: "BIGINT", remarks: "布局位置")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"USAGE_CODE,LAYOUT_PRIORITY",tableName:"GLD_USAGE_CODE_CATALOG",constraintName: "GLD_USAGE_CODE_CATALOG_U1")
        addUniqueConstraint(columnNames:"USAGE_CODE,MAPPING_CONDITION_CODE",tableName:"GLD_USAGE_CODE_CATALOG",constraintName: "GLD_USAGE_CODE_CATALOG_U2")
    }


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-01-18-GLD_USAGE_CODE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_USAGE_CODE_S', startValue:"10001")
        }

        createTable(tableName: "GLD_USAGE_CODE", remarks: "用途代码表") {

            column(name: "USAGE_CODE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_USAGE_CODE_PK")} 
            column(name: "USAGE_CODE", type: "VARCHAR(30)", remarks: "用途代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"USAGE_CODE",tableName:"GLD_USAGE_CODE",constraintName: "GLD_USAGE_CODE_U1")
    }

    changeSet(author:"hui.zhao01@hand-china.com", id: "2019-01-18-GLD_USAGE_CODE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_USAGE_CODE_TL_S', startValue: "10001")
        }

        createTable(tableName: "GLD_USAGE_CODE_TL", remarks: "用途代码表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "USAGE_CODE_ID", type: "BIGINT",  remarks: "")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "描述ID")   
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

    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-01-18-GLD_MAPPING_CONDITION_SQL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_MAPPING_CONDITION_SQL_S', startValue:"10001")
        }

        createTable(tableName: "GLD_MAPPING_CONDITION_SQL", remarks: "用途代码配置-匹配项取值sql") {

            column(name: "MAPPING_CONDITION_SQL_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_MAPPING_CONDITION_SQL_PK")} 
            column(name: "MAPPING_CONDITION_CODE", type: "VARCHAR(30)", remarks: "匹配项代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述")  {constraints(nullable:"false")}  
            column(name: "LOV_STATEMENT", type: "VARCHAR(2000)", remarks: "取值SQL")  {constraints(nullable:"false")}  
            column(name: "REF_TABLE", type: "VARCHAR(30)", remarks: "关联表名称")  {constraints(nullable:"false")}  
            column(name: "REF_FIELD", type: "VARCHAR(30)", remarks: "关联字段")  {constraints(nullable:"false")}  
            column(name: "REF_ID_FIELD", type: "VARCHAR(30)", remarks: "关联匹配项")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"MAPPING_CONDITION_CODE",tableName:"GLD_MAPPING_CONDITION_SQL",constraintName: "GLD_MAPPING_CONDITION_SQL_U1")
    }

    changeSet(author:"hui.zhao01@hand-china.com", id: "2019-01-18-GLD_MAPPING_CONDITION_SQL_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_MAPPING_CONDITION_SQL_TL_S', startValue: "10001")
        }

        createTable(tableName: "GLD_MAPPING_CONDITION_SQL_TL", remarks: "用途代码配置-匹配项取值sql多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "MAPPING_CONDITION_SQL_ID", type: "BIGINT",  remarks: "")  {constraints(nullable: "false", primaryKey: "true")} 
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

    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-01-18-GLD_MAPPING_COND_GRP_HD") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_MAPPING_COND_GRP_HD_S', startValue:"10001")
        }

        createTable(tableName: "GLD_MAPPING_COND_GRP_HD", remarks: "用途代码匹配组头") {

            column(name: "COND_GRP_HD_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_MAPPING_COND_GRP_HD_PK")} 
            column(name: "USAGE_CODE", type: "VARCHAR(30)", remarks: "用途代码")  {constraints(nullable:"false")}  
            column(name: "GROUP_NAME", type: "VARCHAR(240)", remarks: "匹配组")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述")  {constraints(nullable:"false")}  
            column(name: "PRIORITY", type: "BIGINT", remarks: "优先级")  {constraints(nullable:"false")}  
            column(name: "TABLE_NAME", type: "VARCHAR(30)", remarks: "表名")  {constraints(nullable:"false")}  
            column(name: "SYS_FLAG", type: "VARCHAR(1)", remarks: "系统标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            
        addUniqueConstraint(columnNames:"USAGE_CODE,PRIORITY",tableName:"GLD_MAPPING_COND_GRP_HD",constraintName: "GLD_MAPPING_COND_GRP_HD_U1")
        addUniqueConstraint(columnNames:"GROUP_NAME",tableName:"GLD_MAPPING_COND_GRP_HD",constraintName: "GLD_MAPPING_COND_GRP_HD_U2")
    }

    changeSet(author:"hui.zhao01@hand-china.com", id: "2019-01-18-GLD_MAPPING_COND_GRP_HD_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_MAPPING_COND_GRP_HD_TL_S', startValue: "10001")
        }

        createTable(tableName: "GLD_MAPPING_COND_GRP_HD_TL", remarks: "用途代码匹配组头多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "COND_GRP_HD_ID", type: "BIGINT",  remarks: "")  {constraints(nullable: "false", primaryKey: "true")} 
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

    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-01-18-GLD_MAPPING_COND_GRP_LN") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_MAPPING_COND_GRP_LN_S', startValue:"10001")
        }

        createTable(tableName: "GLD_MAPPING_COND_GRP_LN", remarks: "用途代码匹配组行") {

            column(name: "COND_GRP_LN_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_MAPPING_COND_GRP_LN_PK")}
            column(name: "GROUP_NAME", type: "VARCHAR(240)", remarks: "匹配组")  {constraints(nullable:"false")}  
            column(name: "MAPPING_CONDITION_CODE", type: "VARCHAR(30)", remarks: "匹配项代码")  {constraints(nullable:"false")}  
            column(name: "LAYOUT_PRIORITY", type: "BIGINT", remarks: "优先级")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"MAPPING_CONDITION_CODE,GROUP_NAME",tableName:"GLD_MAPPING_COND_GRP_LN",constraintName: "GLD_MAPPING_COND_GRP_LN_U1")
    }


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-01-18-GLD_MAPPING_CONDITION") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_MAPPING_CONDITION_S', startValue:"10001")
        }

        createTable(tableName: "GLD_MAPPING_CONDITION", remarks: "用途代码匹配条件表") {

            column(name: "MAPPING_CONDITION_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_MAPPING_CONDITION")}
            column(name: "PROCESS_ID", type: "BIGINT", remarks: "处理")  {constraints(nullable:"false")}  
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织")  {constraints(nullable:"false")}  
            column(name: "SET_OF_BOOKS_ID", type: "BIGINT", remarks: "账套")  {constraints(nullable:"false")}  
            column(name: "MAPPING_CONDITION_CODE", type: "VARCHAR(30)", remarks: "匹配条件代码")  {constraints(nullable:"false")}  
            column(name: "MAPPING_CONDITION_VALUE", type: "VARCHAR(30)", remarks: "匹配条件值")   
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建人")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"MAPPING_CONDITION_CODE,PROCESS_ID",tableName:"GLD_MAPPING_CONDITION",constraintName: "GLD_MAPPING_CONDITION_U1")
    }


}