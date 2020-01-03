package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-09-exp-init-table-migration.groovy') {


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_EMPLOYEE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_EMPLOYEE_S', startValue: "10001")
        }

        createTable(tableName: "EXP_EMPLOYEE", remarks: "员工基础定义") {

            column(name: "EMPLOYEE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "员工ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_EMPLOYEE_PK")
            }
            column(name: "EMPLOYEE_CODE", type: "VARCHAR(30)", remarks: "员工代码") { constraints(nullable: "false") }
            column(name: "NAME", type: "VARCHAR(50)", remarks: "姓名") { constraints(nullable: "false") }
            column(name: "EMAIL", type: "VARCHAR(50)", remarks: "E-mail")
            column(name: "MOBIL", type: "VARCHAR(50)", remarks: "移动电话")
            column(name: "PHONE", type: "VARCHAR(50)", remarks: "固定电话")
            column(name: "BANK_OF_DEPOSIT", type: "VARCHAR(200)", remarks: "开户行")
            column(name: "BANK_ACCOUNT", type: "VARCHAR(200)", remarks: "银行帐户")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "EMPLOYEE_TYPE_ID", type: "BIGINT", remarks: "员工类型ID")
            column(name: "ID_TYPE", type: "VARCHAR(50)", remarks: "证件类型")
            column(name: "ID_CODE", type: "VARCHAR(200)", remarks: "证件编码")
            column(name: "NOTES", type: "VARCHAR(200)", remarks: "备注")
            column(name: "NATIONAL_IDENTIFIER", type: "VARCHAR(200)", remarks: "证件编号")
            column(name: "HMAP_SYNC_FLAG", type: "VARCHAR(1)", remarks: "HMAP同步标志，Y/N")
            column(name: "HMAP_SYNC_DATE", type: "DATETIME", remarks: "HMAP同步日期")
            column(name: "PLACE_ID", type: "BIGINT", remarks: "费用政策地点id")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "EMPLOYEE_CODE", tableName: "EXP_EMPLOYEE", constraintName: "EXP_EMPLOYEES_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_EMPLOYEE_ACCOUNT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_EMPLOYEE_ACCOUNT_S', startValue: "10001")
        }

        createTable(tableName: "EXP_EMPLOYEE_ACCOUNT", remarks: "员工银行账号信息表") {

            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID") { constraints(nullable: "false") }
            column(name: "LINE_NUMBER", type: "BIGINT", remarks: "行号") { constraints(nullable: "false") }
            column(name: "BANK_CODE", type: "VARCHAR(30)", remarks: "银行代码")
            column(name: "BANK_NAME", type: "VARCHAR(200)", remarks: "银行名称")
            column(name: "BANK_LOCATION_CODE", type: "VARCHAR(30)", remarks: "分行代码")
            column(name: "BANK_LOCATION", type: "VARCHAR(200)", remarks: "分行名称")
            column(name: "PROVINCE_CODE", type: "VARCHAR(30)", remarks: "分行所在省")
            column(name: "PROVINCE_NAME", type: "VARCHAR(200)", remarks: "省名称")
            column(name: "CITY_CODE", type: "VARCHAR(30)", remarks: "分行所在城市")
            column(name: "CITY_NAME", type: "VARCHAR(200)", remarks: "市名称")
            column(name: "ACCOUNT_NUMBER", type: "VARCHAR(200)", remarks: "银行帐号")
            column(name: "ACCOUNT_NAME", type: "VARCHAR(200)", remarks: "银行户名")
            column(name: "NOTES", type: "VARCHAR(2000)", remarks: "备注")
            column(name: "PRIMARY_FLAG", type: "VARCHAR(1)", remarks: "主帐号标志")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addPrimaryKey(columnNames: "EMPLOYEE_ID,LINE_NUMBER", tableName: "EXP_EMPLOYEE_ACCOUNT", constraintName: "EXP_EMPLOYEE_ACCOUNTS_PK")
        addUniqueConstraint(columnNames: "BANK_CODE,ACCOUNT_NUMBER", tableName: "EXP_EMPLOYEE_ACCOUNT", constraintName: "EXP_EMPLOYEE_ACCOUNT_NUMBER_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_EMPLOYEE_ASSIGN") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_EMPLOYEE_ASSIGN_S', startValue: "10001")
        }

        createTable(tableName: "EXP_EMPLOYEE_ASSIGN", remarks: "员工分配表") {

            column(name: "EMPLOYEES_ASSIGN_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "员工分配ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_EMPLOYEE_ASSIGN_PK")
            }
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID") { constraints(nullable: "false") }
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID") { constraints(nullable: "false") }
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID")
            column(name: "EMPLOYEE_JOB_ID", type: "BIGINT", remarks: "员工职务ID")
            column(name: "EMPLOYEE_LEVELS_ID", type: "BIGINT", remarks: "员工级别ID")
            column(name: "DATE_FROM", type: "DATETIME", remarks: "员工分配开始日期")
            column(name: "DATE_TO", type: "DATETIME", remarks: "员工分配结束日期")
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT", remarks: "责任中心ID")
            column(name: "ENTITY_ID", type: "BIGINT", remarks: "预算实体ID")
            column(name: "CENTER_ID", type: "BIGINT", remarks: "预算中心ID")
            column(name: "PRIMARY_POSITION_FLAG", type: "VARCHAR(1)", remarks: "主岗")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "EXP_EMPLOYEE_ASSIGN", indexName: "EXP_EMPLOYEE_ASSIGNS_N1") {
            column(name: "EMPLOYEE_LEVELS_ID")
        }
        createIndex(tableName: "EXP_EMPLOYEE_ASSIGN", indexName: "EXP_EMPLOYEE_ASSIGNS_N2") {
            column(name: "EMPLOYEE_JOB_ID")
        }
        createIndex(tableName: "EXP_EMPLOYEE_ASSIGN", indexName: "EXP_EMPLOYEE_ASSIGNS_N3") {
            column(name: "ENABLED_FLAG")
            column(name: "COMPANY_ID")
            column(name: "PRIMARY_POSITION_FLAG")
        }
        createIndex(tableName: "EXP_EMPLOYEE_ASSIGN", indexName: "EXP_EMPLOYEE_ASSIGNS_N4") {
            column(name: "EMPLOYEE_ID")
            column(name: "ENABLED_FLAG")
            column(name: "COMPANY_ID")
            column(name: "POSITION_ID")
        }

        addUniqueConstraint(columnNames: "EMPLOYEE_ID,COMPANY_ID,POSITION_ID,EMPLOYEE_JOB_ID,EMPLOYEE_LEVELS_ID", tableName: "EXP_EMPLOYEE_ASSIGN", constraintName: "EXP_EMPLOYEE_ASSIGNS_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_EMPLOYEE_ATTACHMENT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_EMPLOYEE_ATTACHMENT_S', startValue: "10001")
        }

        createTable(tableName: "EXP_EMPLOYEE_ATTACHMENT", remarks: "员工附件") {

            column(name: "ATTACHMENT_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "pk，员工附件ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_EMPLOYEE_ATTACHMENT_PK")
            }
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID") { constraints(nullable: "false") }
            column(name: "FND_ATTACHMENT_ID", type: "BIGINT", remarks: "系统附件ID")
            column(name: "FILE_NAME", type: "VARCHAR(30)", remarks: "文件名")
            column(name: "USED_FLAG", type: "VARCHAR(1)", remarks: "使用标志")
            column(name: "DOC_CATEGORY", type: "VARCHAR(30)", remarks: "关联单据类别")
            column(name: "DOC_ID", type: "BIGINT", remarks: "关联单据ID")
            column(name: "IMAGE_WIDTH", type: "BIGINT", remarks: "图片宽度")
            column(name: "IMAGE_HEIGHT", type: "BIGINT", remarks: "图片高度")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "EXP_EMPLOYEE_ATTACHMENT", indexName: "EXP_EMPLOYEE_ATTACHMENTS_N1") {
            column(name: "DOC_CATEGORY")
            column(name: "DOC_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_LEVEL_SERIES") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_LEVEL_SERIES_S', startValue: "10001")
        }

        createTable(tableName: "EXP_LEVEL_SERIES", remarks: "员工级别系列") {

            column(name: "LEVEL_SERIES_CODE", type: "VARCHAR(30)", remarks: "级别系列代码") { constraints(nullable: "false") }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LEVEL_SERIES_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "级别系列ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_LEVEL_SERIES_PK")
            }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "LEVEL_SERIES_CODE", tableName: "EXP_LEVEL_SERIES", constraintName: "EXP_LEVEL_SERIES_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_LEVEL_SERIES_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_LEVEL_SERIES_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_LEVEL_SERIES_TL", remarks: "员工级别系列多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID")
            column(name: "LEVEL_SERIES_ID", type: "BIGINT", remarks: "级别系列ID") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_MO_EMPLOYEE_TYPE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_MO_EMPLOYEE_TYPE_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_EMPLOYEE_TYPE", remarks: "员工类型") {

            column(name: "EMPLOYEE_TYPE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_MO_EMPLOYEE_TYPE_PK")
            }
            column(name: "EMPLOYEE_TYPE_CODE", type: "VARCHAR(30)", remarks: "员工类型代码") {
                constraints(nullable: "false")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "员工类型描述ID")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "EMPLOYEE_TYPE_CODE", tableName: "EXP_MO_EMPLOYEE_TYPE", constraintName: "EXP_MO_EMPLOYEE_TYPES_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_MO_EMPLOYEE_TYPE_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_MO_EMPLOYEE_TYPE_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_EMPLOYEE_TYPE_TL", remarks: "员工类型多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "EMPLOYEE_TYPE_ID", type: "BIGINT", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "员工类型描述ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_MO_EMP_TYPE_ASGN_COM") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_MO_EMP_TYPE_ASGN_COM_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_EMP_TYPE_ASGN_COM", remarks: "管理组织级员工类型分配，取代EXP_SOB_EMP_ASGN_CM") {

            column(name: "EMP_MO_ASGN_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_MO_EMP_TYPE_ASGN_COM_PK")
            }
            column(name: "EMPLOYEE_TYPE_ID", type: "BIGINT", remarks: "员工类型ID") { constraints(nullable: "false") }
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "COMPANY_ID,EMPLOYEE_TYPE_ID", tableName: "EXP_MO_EMP_TYPE_ASGN_COM", constraintName: "EXP_MO_EMP_TYPE_ASGN_COM_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_EMPLOYEE_JOB") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_EMPLOYEE_JOB_S', startValue: "10001")
        }

        createTable(tableName: "EXP_EMPLOYEE_JOB", remarks: "员工职务定义") {

            column(name: "EMPLOYEE_JOB_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_EMPLOYEE_JOB_PK")
            }
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID") { constraints(nullable: "false") }
            column(name: "EMPLOYEE_JOB_CODE", type: "VARCHAR(30)", remarks: "职务代码") { constraints(nullable: "false") }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "职务名称ID") { constraints(nullable: "false") }
            column(name: "LEVEL_SERIES_ID", type: "BIGINT", remarks: "级别系列ID")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "MAG_ORG_ID,EMPLOYEE_JOB_CODE", tableName: "EXP_EMPLOYEE_JOB", constraintName: "EXP_EMPLOYEE_JOBS_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_EMPLOYEE_JOB_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_EMPLOYEE_JOB_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_EMPLOYEE_JOB_TL", remarks: "员工职务定义多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "EMPLOYEE_JOB_ID", type: "BIGINT", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "职务名称ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_MO_EMP_GROUP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_MO_EMP_GROUP_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_EMP_GROUP", remarks: "管理组织级员工组定义，取代EXP_SOB_USER_GROUPS") {

            column(name: "MO_EMP_GROUP_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "员工组ID，pk") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_MO_EMP_GROUP_PK")
            }
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID") { constraints(nullable: "false") }
            column(name: "MO_EMP_GROUP_CODE", type: "VARCHAR(30)", remarks: "员工组代码") { constraints(nullable: "false") }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "MO_EMP_GROUP_CODE,MAG_ORG_ID", tableName: "EXP_MO_EMP_GROUP", constraintName: "EXP_MO_EMP_GROUPS_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_MO_EMP_GROUP_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_MO_EMP_GROUP_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_EMP_GROUP_TL", remarks: "管理组织级员工组定义，取代EXP_SOB_USER_GROUPS多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "MO_EMP_GROUP_ID", type: "BIGINT", remarks: "员工组ID，pk") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_EMPLOYEE_LEVEL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_EMPLOYEE_LEVEL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_EMPLOYEE_LEVEL", remarks: "员工级别") {

            column(name: "EMPLOYEE_LEVELS_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "员工级别ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_EMPLOYEE_LEVEL_PK")
            }
            column(name: "LEVEL_SERIES_ID", type: "BIGINT", remarks: "级别系列ID") { constraints(nullable: "false") }
            column(name: "EMPLOYEE_LEVELS_CODE", type: "VARCHAR(30)", remarks: "级别代码") {
                constraints(nullable: "false")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "LEVEL_SERIES_ID,EMPLOYEE_LEVELS_CODE", tableName: "EXP_EMPLOYEE_LEVEL", constraintName: "EXP_EMPLOYEE_LEVELS_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_EMPLOYEE_LEVEL_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_EMPLOYEE_LEVEL_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_EMPLOYEE_LEVEL_TL", remarks: "员工级别多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "EMPLOYEE_LEVELS_ID", type: "BIGINT", remarks: "员工级别ID") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_ORG_POSITION") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_ORG_POSITION_S', startValue: "10001")
        }

        createTable(tableName: "EXP_ORG_POSITION", remarks: "员工岗位定义") {

            column(name: "POSITION_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "岗位ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_ORG_POSITION_PK")
            }
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")
            column(name: "POSITION_CODE", type: "VARCHAR(30)", remarks: "岗位")
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID")
            column(name: "PARENT_POSITION_ID", type: "BIGINT", remarks: "上级岗位ID")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")
            column(name: "EMPLOYEE_JOB_ID", type: "BIGINT", remarks: "员工职务ID")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "EXP_ORG_POSITION", indexName: "EXP_ORG_POSITION_N1") {
            column(name: "PARENT_POSITION_ID")
        }

        addUniqueConstraint(columnNames: "POSITION_CODE,COMPANY_ID", tableName: "EXP_ORG_POSITION", constraintName: "EXP_ORG_POSITION_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_ORG_POSITION_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_ORG_POSITION_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_ORG_POSITION_TL", remarks: "员工岗位定义多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_POSITION_GROUP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_POSITION_GROUP_S', startValue: "10001")
        }

        createTable(tableName: "EXP_POSITION_GROUP", remarks: "岗位组定义") {

            column(name: "POSITION_GROUP_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "岗位组ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_POSITION_GROUP_PK")
            }
            column(name: "POSITION_GROUP_CODE", type: "VARCHAR(30)", remarks: "岗位组") { constraints(nullable: "false") }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID") { constraints(nullable: "false") }
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "COMPANY_ID,POSITION_GROUP_CODE", tableName: "EXP_POSITION_GROUP", constraintName: "EXP_ORG_POSITION_GROUPS_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_POSITION_GROUP_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_POSITION_GROUP_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_POSITION_GROUP_TL", remarks: "岗位组定义多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "POSITION_GROUP_ID", type: "BIGINT", remarks: "岗位组ID") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_POSITION_GROUP_RELATION") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_POSITION_GROUP_RELATION_S', startValue: "10001")
        }

        createTable(tableName: "EXP_POSITION_GROUP_RELATION", remarks: "岗位分配岗位组") {

            column(name: "POSITION_GROUP_ID", type: "BIGINT", remarks: "岗位组ID") { constraints(nullable: "false") }
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "EXP_POSITION_GROUP_RELATION", indexName: "EXP_POSITION_GRP_RELATIONS_N1") {
            column(name: "POSITION_ID")
        }

        addPrimaryKey(columnNames: "POSITION_ID,POSITION_GROUP_ID", tableName: "EXP_POSITION_GROUP_RELATION", constraintName: "EXP_POSITION_GRP_RELATIONS_PK")
    }

    changeSet(author: "hongliu.ye@hand-china.com", id: "2019-01-21-EXP_POSITION_GROUP_RELATION") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_POSITION_GROUP_RELATION_S', startValue: "10001")
        }

        createTable(tableName: "EXP_POSITION_GROUP_RELATION", remarks: "岗位分配岗位组") {

            column(name: "RELATION_ID", type: "BIGINT", autoIncrement: "true", startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_POSITION_GROUP_RELATION_PK")
            }
            column(name: "POSITION_GROUP_ID", type: "BIGINT", remarks: "岗位组ID") { constraints(nullable: "false") }
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "EXP_POSITION_GROUP_RELATION", indexName: "EXP_POSITION_GRP_RELATION_N1") {
            column(name: "POSITION_ID")
        }

        addUniqueConstraint(columnNames: "POSITION_GROUP_ID,POSITION_ID", tableName: "EXP_POSITION_GROUP_RELATION", constraintName: "EXP_POSITION_GRP_RELATION_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_MO_UNIT_GROUP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_MO_UNIT_GROUP_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_UNIT_GROUP", remarks: "管理组织级部门组") {

            column(name: "MO_UNIT_GROUP_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "部门组ID，pk") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_MO_UNIT_GROUP_PK")
            }
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID") { constraints(nullable: "false") }
            column(name: "MO_UNIT_GROUP_CODE", type: "VARCHAR(30)", remarks: "部门组代码") { constraints(nullable: "false") }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "MAG_ORG_ID,MO_UNIT_GROUP_CODE", tableName: "EXP_MO_UNIT_GROUP", constraintName: "EXP_MO_UNIT_GROUPS_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_MO_UNIT_GROUP_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_MO_UNIT_GROUP_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_UNIT_GROUP_TL", remarks: "管理组织级部门组多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "MO_UNIT_GROUP_ID", type: "BIGINT", remarks: "部门组ID，pk") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-EXP_MO_UNIT_GROUP_ASGN_COM") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_MO_UNIT_GROUP_ASGN_COM_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_UNIT_GROUP_ASGN_COM", remarks: "管理组织级部门组分配公司") {

            column(name: "ASSIGN_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "pk") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_MO_UNIT_GROUP_ASGN_COM_PK")
            }
            column(name: "MO_UNIT_GROUP_ID", type: "BIGINT", remarks: "管理组织级部门组ID")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "COMPANY_ID,MO_UNIT_GROUP_ID", tableName: "EXP_MO_UNIT_GROUP_ASGN_COM", constraintName: "EXP_MO_UNIT_GROUP_ASGN_COM_U1")
    }


}