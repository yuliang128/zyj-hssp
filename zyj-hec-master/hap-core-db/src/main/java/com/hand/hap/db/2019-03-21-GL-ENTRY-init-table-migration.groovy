package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-21-GL-ENTRY-init-table-migration.groovy') {


    changeSet(author: "jingjing.tang@hand-china.com", id: "2019-03-21-EBS_GL_ACCOUNT_ENTRY") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EBS_GL_ACCOUNT_ENTRY_S', startValue:"10001")
        }

        createTable(tableName: "EBS_GL_ACCOUNT_ENTRY", remarks: "EBS凭证接口表") {

            column(name: "CURRENCY_CONVERSION_RATE", type: "NUMBER", remarks: "汇率")   
            column(name: "CURRENCY_CONVERSION_TYPE", type: "VARCHAR(30)", remarks: "汇率类型")   
            column(name: "SEGMENT1", type: "VARCHAR(150)", remarks: "属性1")   
            column(name: "SEGMENT2", type: "VARCHAR(150)", remarks: "属性2")   
            column(name: "SEGMENT3", type: "VARCHAR(150)", remarks: "属性3")   
            column(name: "SEGMENT4", type: "VARCHAR(150)", remarks: "属性4")   
            column(name: "SEGMENT5", type: "VARCHAR(150)", remarks: "属性5")   
            column(name: "SEGMENT6", type: "VARCHAR(150)", remarks: "属性6")   
            column(name: "SEGMENT7", type: "VARCHAR(150)", remarks: "属性7")   
            column(name: "SEGMENT8", type: "VARCHAR(150)", remarks: "属性8")   
            column(name: "SEGMENT9", type: "VARCHAR(150)", remarks: "属性9")   
            column(name: "SEGMENT10", type: "VARCHAR(150)", remarks: "属性10")   
            column(name: "SEGMENT11", type: "VARCHAR(150)", remarks: "属性11")   
            column(name: "SEGMENT12", type: "VARCHAR(150)", remarks: "属性12描述")   
            column(name: "SEGMENT13", type: "VARCHAR(150)", remarks: "属性13")   
            column(name: "SEGMENT14", type: "VARCHAR(150)", remarks: "属性14")   
            column(name: "SEGMENT15", type: "VARCHAR(150)", remarks: "属性15")   
            column(name: "SEGMENT16", type: "VARCHAR(150)", remarks: "属性16")   
            column(name: "SEGMENT17", type: "VARCHAR(150)", remarks: "属性17")   
            column(name: "SEGMENT18", type: "VARCHAR(150)", remarks: "属性18")   
            column(name: "SEGMENT19", type: "VARCHAR(150)", remarks: "属性19")   
            column(name: "SEGMENT20", type: "VARCHAR(150)", remarks: "属性20")   
            column(name: "EBS_JE_CATEGORY_CODE", type: "VARCHAR(150)", remarks: "凭证类别代码")   
            column(name: "SEGMENT_DESC1", type: "VARCHAR(200)", remarks: "属性1描述")
            column(name: "SEGMENT_DESC2", type: "VARCHAR(200)", remarks: "属性描述2")
            column(name: "SEGMENT_DESC3", type: "VARCHAR(200)", remarks: "属性描述3")
            column(name: "SEGMENT_DESC4", type: "VARCHAR(200)", remarks: "属性4描述")
            column(name: "SEGMENT_DESC5", type: "VARCHAR(200)", remarks: "属性5描述")
            column(name: "SEGMENT_DESC6", type: "VARCHAR(200)", remarks: "属性6描述")
            column(name: "SEGMENT_DESC7", type: "VARCHAR(200)", remarks: "属性描述7")
            column(name: "SEGMENT_DESC8", type: "VARCHAR(200)", remarks: "属性8描述")
            column(name: "SEGMENT_DESC9", type: "VARCHAR(200)", remarks: "属性9描述")
            column(name: "SEGMENT_DESC10", type: "VARCHAR(200)", remarks: "属性10描述")
            column(name: "SEGMENT_DESC11", type: "VARCHAR(200)", remarks: "属性描述11")
            column(name: "SEGMENT_DESC12", type: "VARCHAR(200)", remarks: "属性12描述")
            column(name: "SEGMENT_DESC13", type: "VARCHAR(200)", remarks: "属性13描述")
            column(name: "SEGMENT_DESC14", type: "VARCHAR(200)", remarks: "属性14描述")
            column(name: "SEGMENT_DESC15", type: "VARCHAR(200)", remarks: "属性描述15")
            column(name: "SEGMENT_DESC16", type: "VARCHAR(200)", remarks: "属性描述16")
            column(name: "SEGMENT_DESC17", type: "VARCHAR(200)", remarks: "属性17描述")
            column(name: "SEGMENT_DESC18", type: "VARCHAR(200)", remarks: "属性18描述")
            column(name: "SEGMENT_DESC19", type: "VARCHAR(200)", remarks: "属性19描述")
            column(name: "SEGMENT_DESC20", type: "VARCHAR(200)", remarks: "属性描述20")
            column(name: "IMPORTED_FLAG", type: "VARCHAR(15)", remarks: "重要标志")  {constraints(nullable:"false")}  
            column(name: "ATTRIBUTE1", type: "TEXT", remarks: "字段1")
            column(name: "ATTRIBUTE2", type: "TEXT", remarks: "字段2")
            column(name: "ATTRIBUTE3", type: "TEXT", remarks: "字段3")
            column(name: "ATTRIBUTE4", type: "TEXT", remarks: "字段4")
            column(name: "ATTRIBUTE5", type: "TEXT", remarks: "字段5")
            column(name: "ATTRIBUTE6", type: "TEXT", remarks: "字段6")
            column(name: "ATTRIBUTE7", type: "TEXT", remarks: "字段7")
            column(name: "ATTRIBUTE8", type: "TEXT", remarks: "字段8")
            column(name: "ATTRIBUTE9", type: "TEXT", remarks: "字段9")
            column(name: "ATTRIBUTE10", type: "TEXT", remarks: "字段10")
            column(name: "ATTRIBUTE11", type: "TEXT", remarks: "字段11")
            column(name: "ATTRIBUTE12", type: "TEXT", remarks: "字段12")
            column(name: "ATTRIBUTE13", type: "TEXT", remarks: "字段13")
            column(name: "ATTRIBUTE14", type: "TEXT", remarks: "字段14")
            column(name: "ATTRIBUTE15", type: "TEXT", remarks: "字段15")
            column(name: "ATTRIBUTE16", type: "TEXT", remarks: "字段16")
            column(name: "ATTRIBUTE17", type: "TEXT", remarks: "字段17")
            column(name: "ATTRIBUTE18", type: "TEXT", remarks: "字段18")
            column(name: "ATTRIBUTE19", type: "TEXT", remarks: "字段19")
            column(name: "ATTRIBUTE20", type: "TEXT", remarks: "字段20")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "NUMBER", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "NUMBER", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "GL_INTERFACE_ID", type: "NUMBER", remarks: "总账标识")   
            column(name: "ACCOUNT_ENTRY_ID", type: "NUMBER", remarks: "凭证接口表id")  {constraints(nullable:"false")}  
            column(name: "HEC_SET_OF_BOOKS_ID", type: "NUMBER", remarks: "HEC帐套ID")  {constraints(nullable:"false")}  
            column(name: "EBS_SOB_ID", type: "NUMBER", remarks: "EBS帐套id")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "NUMBER", remarks: "公司ID")  {constraints(nullable:"false")}  
            column(name: "TRANSACTION_TYPE", type: "VARCHAR(30)", remarks: "付款类型")  {constraints(nullable:"false")}  
            column(name: "TRANSACTION_HEADER_ID", type: "NUMBER", remarks: "付款事务头id")   
            column(name: "TRANSACTION_LINE_ID", type: "NUMBER", remarks: "付款事务行id")   
            column(name: "TRANSACTION_DIST_ID", type: "NUMBER", remarks: "付款分配行id")   
            column(name: "TRANSACTION_JE_LINE_ID", type: "NUMBER", remarks: "付款事务行id")   
            column(name: "TRANSACTION_NUMBER", type: "VARCHAR(30)", remarks: "现金事务编码")   
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")   
            column(name: "DESCRIPTION", type: "VARCHAR(1000)", remarks: "描述")
            column(name: "TRANSACTION_DATE", type: "DATETIME", remarks: "传送日期")   
            column(name: "ACCOUNTING_DATE", type: "DATETIME", remarks: "凭证日期")   
            column(name: "ENTERED_AMOUNT_DR", type: "NUMBER", remarks: "原币借方金额")   
            column(name: "ENTERED_AMOUNT_CR", type: "NUMBER", remarks: "原币贷方金额")   
            column(name: "FUNCTIONAL_AMOUNT_DR", type: "NUMBER", remarks: "借方金额")   
            column(name: "FUNCTIONAL_AMOUNT_CR", type: "NUMBER", remarks: "本币贷方金额")   
            column(name: "CURRENCY_CODE", type: "VARCHAR(10)", remarks: "币种")   
            column(name: "CURRENCY_CONVERSION_DATE", type: "DATETIME", remarks: "汇率日期")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "EBS_GL_ACCOUNT_ENTRY", indexName: "EBS_GL_ACCOUNT_ENTRY_N1") {
                    column(name: "TRANSACTION_TYPE")
                    column(name: "TRANSACTION_HEADER_ID")
                    column(name: "TRANSACTION_LINE_ID")
                    column(name: "COMPANY_ID")
                    column(name: "TRANSACTION_DIST_ID")
                }
                createIndex(tableName: "EBS_GL_ACCOUNT_ENTRY", indexName: "EBS_GL_ACCOUNT_ENTRY_N2") {
                    column(name: "GL_INTERFACE_ID")
                }
                createIndex(tableName: "EBS_GL_ACCOUNT_ENTRY", indexName: "EBS_GL_ACCOUNT_ENTRY_N3") {
                    column(name: "IMPORTED_FLAG")
                    column(name: "COMPANY_ID")
                }
            
        addUniqueConstraint(columnNames:"COMPANY_ID,TRANSACTION_TYPE,TRANSACTION_JE_LINE_ID",tableName:"EBS_GL_ACCOUNT_ENTRY",constraintName: "EBS_GL_ACCOUNT_ENTRY_U1")
    }


    changeSet(author: "jingjing.tang@hand-china.com", id: "2019-03-21-GL_ACCOUNT_ENTRY") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GL_ACCOUNT_ENTRY_S', startValue:"10001")
        }

        createTable(tableName: "GL_ACCOUNT_ENTRY", remarks: "总账分录表") {

            column(name: "QUERY_BATCH_CODE", type: "VARCHAR(50)", remarks: "查询批次代码")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "SEGMENT33", type: "VARCHAR(150)", remarks: "属性33")   
            column(name: "SEGMENT34", type: "VARCHAR(150)", remarks: "属性34")   
            column(name: "SEGMENT35", type: "VARCHAR(150)", remarks: "属性35")   
            column(name: "SEGMENT36", type: "VARCHAR(150)", remarks: "属性36")   
            column(name: "SEGMENT37", type: "VARCHAR(150)", remarks: "属性37")   
            column(name: "SEGMENT38", type: "VARCHAR(150)", remarks: "属性38")   
            column(name: "SEGMENT39", type: "VARCHAR(150)", remarks: "属性39")   
            column(name: "SEGMENT40", type: "VARCHAR(150)", remarks: "属性40")   
            column(name: "SEGMENT41", type: "VARCHAR(150)", remarks: "属性41")   
            column(name: "SEGMENT42", type: "VARCHAR(150)", remarks: "属性42")   
            column(name: "SEGMENT43", type: "VARCHAR(150)", remarks: "属性43")   
            column(name: "SEGMENT44", type: "VARCHAR(150)", remarks: "属性44")   
            column(name: "SEGMENT45", type: "VARCHAR(150)", remarks: "属性45")   
            column(name: "SEGMENT46", type: "VARCHAR(150)", remarks: "属性46")   
            column(name: "SEGMENT47", type: "VARCHAR(150)", remarks: "属性47")   
            column(name: "SEGMENT48", type: "VARCHAR(150)", remarks: "属性48")   
            column(name: "SEGMENT49", type: "VARCHAR(150)", remarks: "属性49")   
            column(name: "SEGMENT50", type: "VARCHAR(150)", remarks: "属性50")   
            column(name: "SEGMENT_DESC1", type: "VARCHAR(200)", remarks: "属性描述1")
            column(name: "SEGMENT_DESC2", type: "VARCHAR(200)", remarks: "属性描述2")
            column(name: "SEGMENT_DESC3", type: "VARCHAR(200)", remarks: "属性描述3")
            column(name: "SEGMENT_DESC4", type: "VARCHAR(200)", remarks: "属性描述4")
            column(name: "SEGMENT_DESC5", type: "VARCHAR(200)", remarks: "属性描述5")
            column(name: "SEGMENT_DESC6", type: "VARCHAR(200)", remarks: "属性描述6")
            column(name: "SEGMENT_DESC7", type: "VARCHAR(200)", remarks: "属性描述7")
            column(name: "SEGMENT_DESC8", type: "VARCHAR(200)", remarks: "属性描述8")
            column(name: "SEGMENT_DESC9", type: "VARCHAR(200)", remarks: "属性描述9")
            column(name: "SEGMENT_DESC10", type: "VARCHAR(200)", remarks: "属性描述10")
            column(name: "SEGMENT_DESC11", type: "VARCHAR(200)", remarks: "属性描述11")
            column(name: "SEGMENT_DESC12", type: "VARCHAR(200)", remarks: "属性描述12")
            column(name: "SEGMENT_DESC13", type: "VARCHAR(200)", remarks: "属性描述13")
            column(name: "SEGMENT_DESC14", type: "VARCHAR(200)", remarks: "属性描述14")
            column(name: "SEGMENT_DESC15", type: "VARCHAR(200)", remarks: "属性描述15")
            column(name: "SEGMENT_DESC16", type: "VARCHAR(200)", remarks: "属性描述16")
            column(name: "SEGMENT_DESC17", type: "VARCHAR(200)", remarks: "属性描述17")
            column(name: "SEGMENT_DESC18", type: "VARCHAR(200)", remarks: "属性描述18")
            column(name: "SEGMENT_DESC19", type: "VARCHAR(200)", remarks: "属性描述19")
            column(name: "SEGMENT_DESC20", type: "VARCHAR(200)", remarks: "属性描述20")
            column(name: "SEGMENT_DESC21", type: "VARCHAR(200)", remarks: "属性描述21")
            column(name: "SEGMENT_DESC22", type: "VARCHAR(200)", remarks: "属性描述22")
            column(name: "SEGMENT_DESC23", type: "VARCHAR(200)", remarks: "属性描述23")
            column(name: "SEGMENT_DESC24", type: "VARCHAR(200)", remarks: "属性描述24")
            column(name: "SEGMENT_DESC25", type: "VARCHAR(200)", remarks: "属性描述25")
            column(name: "SEGMENT_DESC26", type: "VARCHAR(200)", remarks: "属性描述26")
            column(name: "SEGMENT_DESC27", type: "VARCHAR(200)", remarks: "属性描述27")
            column(name: "SEGMENT_DESC28", type: "VARCHAR(200)", remarks: "属性描述28")
            column(name: "SEGMENT_DESC29", type: "VARCHAR(200)", remarks: "属性描述29")
            column(name: "SEGMENT_DESC30", type: "VARCHAR(200)", remarks: "属性描述30")
            column(name: "SEGMENT_DESC31", type: "VARCHAR(200)", remarks: "属性描述31")
            column(name: "SEGMENT_DESC32", type: "VARCHAR(200)", remarks: "属性描述32")
            column(name: "SEGMENT_DESC33", type: "VARCHAR(200)", remarks: "属性描述33")
            column(name: "SEGMENT_DESC34", type: "VARCHAR(200)", remarks: "属性描述34")
            column(name: "SEGMENT_DESC35", type: "VARCHAR(200)", remarks: "属性描述35")
            column(name: "SEGMENT_DESC36", type: "VARCHAR(200)", remarks: "属性描述36")
            column(name: "SEGMENT_DESC37", type: "VARCHAR(200)", remarks: "属性描述37")
            column(name: "SEGMENT_DESC38", type: "VARCHAR(200)", remarks: "属性描述38")
            column(name: "SEGMENT_DESC39", type: "VARCHAR(200)", remarks: "属性描述39")
            column(name: "SEGMENT_DESC40", type: "VARCHAR(200)", remarks: "属性描述40")
            column(name: "SEGMENT_DESC41", type: "VARCHAR(200)", remarks: "属性描述41")
            column(name: "SEGMENT_DESC42", type: "VARCHAR(200)", remarks: "属性描述42")
            column(name: "SEGMENT_DESC43", type: "VARCHAR(200)", remarks: "属性描述43")
            column(name: "SEGMENT_DESC44", type: "VARCHAR(200)", remarks: "属性描述44")
            column(name: "SEGMENT_DESC45", type: "VARCHAR(200)", remarks: "属性描述45")
            column(name: "SEGMENT_DESC46", type: "VARCHAR(200)", remarks: "属性描述46")
            column(name: "SEGMENT_DESC47", type: "VARCHAR(200)", remarks: "属性描述47")
            column(name: "SEGMENT_DESC48", type: "VARCHAR(200)", remarks: "属性描述48")
            column(name: "SEGMENT_DESC49", type: "VARCHAR(200)", remarks: "属性描述49")
            column(name: "SEGMENT_DESC50", type: "VARCHAR(200)", remarks: "属性描述50")
            column(name: "ATTRIBUTE1", type: "TEXT", remarks: "字段1")
            column(name: "ATTRIBUTE2", type: "TEXT", remarks: "字段2")
            column(name: "ATTRIBUTE3", type: "TEXT", remarks: "字段3")
            column(name: "ATTRIBUTE4", type: "TEXT", remarks: "字段4")
            column(name: "ATTRIBUTE5", type: "TEXT", remarks: "字段5")
            column(name: "ATTRIBUTE6", type: "TEXT", remarks: "字段6")
            column(name: "ATTRIBUTE7", type: "TEXT", remarks: "字段7")
            column(name: "ATTRIBUTE8", type: "TEXT", remarks: "字段8")
            column(name: "ATTRIBUTE9", type: "TEXT", remarks: "字段9")
            column(name: "ATTRIBUTE10", type: "TEXT", remarks: "字段10")
            column(name: "ATTRIBUTE11", type: "TEXT", remarks: "字段11")
            column(name: "ATTRIBUTE12", type: "TEXT", remarks: "字段12")
            column(name: "ATTRIBUTE13", type: "TEXT", remarks: "字段13")
            column(name: "ATTRIBUTE14", type: "TEXT", remarks: "字段14")
            column(name: "ATTRIBUTE15", type: "TEXT", remarks: "字段15")
            column(name: "ATTRIBUTE16", type: "TEXT", remarks: "字段16")
            column(name: "ATTRIBUTE17", type: "TEXT", remarks: "字段17")
            column(name: "ATTRIBUTE18", type: "TEXT", remarks: "字段18")
            column(name: "ATTRIBUTE19", type: "TEXT", remarks: "字段19")
            column(name: "ATTRIBUTE20", type: "TEXT", remarks: "字段20")
            column(name: "BATCH_CODE", type: "VARCHAR(50)", remarks: "发送批号")   
            column(name: "GL_INTERFACE_ID", type: "BIGINT", remarks: "返回生成的接口ID")   
            column(name: "IMPORTED_FLAG", type: "VARCHAR(15)", remarks: "导入标志")   
            column(name: "IMPORT_DATE", type: "DATETIME", remarks: "导入日期")   
            column(name: "JOURNAL_NUMBER", type: "VARCHAR(50)", remarks: "凭证编号")   
            column(name: "ERROR_CODE", type: "VARCHAR(150)", remarks: "错误代码")   
            column(name: "ERROR_MESSAGE", type: "VARCHAR(1000)", remarks: "错误信息")
            column(name: "ACCOUNT_ENTRY_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GL_ACCOUNT_ENTRY_PK")} 
            column(name: "HEC_SOB_CODE", type: "VARCHAR(30)", remarks: "HEC帐套代码")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_CODE", type: "VARCHAR(30)", remarks: "核算主体代码")  {constraints(nullable:"false")}  
            column(name: "TRANSACTION_TYPE", type: "VARCHAR(30)", remarks: "事务类型")  {constraints(nullable:"false")}  
            column(name: "TRANSACTION_HEADER_ID", type: "BIGINT", remarks: "事务头ID")   
            column(name: "TRANSACTION_LINE_ID", type: "BIGINT", remarks: "事务行ID")   
            column(name: "TRANSACTION_DIST_ID", type: "BIGINT", remarks: "事务分配行ID")   
            column(name: "TRANSACTION_JE_LINE_ID", type: "BIGINT", remarks: "事务凭证行ID")   
            column(name: "TRANSACTION_NUMBER", type: "VARCHAR(30)", remarks: "事务编码")   
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间名称")   
            column(name: "PERIOD_YEAR", type: "BIGINT", remarks: "期间年")   
            column(name: "PERIOD_NUM", type: "BIGINT", remarks: "期间号")   
            column(name: "DESCRIPTION", type: "VARCHAR(1000)", remarks: "描述")
            column(name: "TRANSACTION_DATE", type: "DATETIME", remarks: "事务日期")   
            column(name: "ACCOUNTING_DATE", type: "DATETIME", remarks: "凭证日期")   
            column(name: "ATTACHMENT_NUM", type: "BIGINT", remarks: "附件数")   
            column(name: "LINE_DESCRIPTION", type: "VARCHAR(1000)", remarks: "行描述")
            column(name: "ACCOUNT_ID", type: "NUMBER", remarks: "科目ID")   
            column(name: "ACCOUNT_CODE", type: "VARCHAR(30)", remarks: "科目代码")   
            column(name: "ENTERED_AMOUNT_DR", type: "BIGINT", remarks: "原币借方金额")   
            column(name: "ENTERED_AMOUNT_CR", type: "BIGINT", remarks: "原币贷方金额")   
            column(name: "FUNCTIONAL_AMOUNT_DR", type: "BIGINT", remarks: "本币借方金额")   
            column(name: "FUNCTIONAL_AMOUNT_CR", type: "BIGINT", remarks: "本币贷方金额")   
            column(name: "CURRENCY_CODE", type: "VARCHAR(10)", remarks: "币种代码")   
            column(name: "CURRENCY_CONVERSION_DATE", type: "DATETIME", remarks: "币种汇率日期")   
            column(name: "CURRENCY_CONVERSION_RATE", type: "BIGINT", remarks: "币种汇率")   
            column(name: "CURRENCY_CONVERSION_TYPE", type: "VARCHAR(30)", remarks: "币种汇率类型")   
            column(name: "REVERSE_FLAG", type: "VARCHAR(1)", remarks: "反冲标志")   
            column(name: "REVERSE_TRANSACTION_ID", type: "BIGINT", remarks: "反冲事务ID")   
            column(name: "SEGMENT1", type: "VARCHAR(150)", remarks: "属性1")   
            column(name: "SEGMENT2", type: "VARCHAR(150)", remarks: "属性2")   
            column(name: "SEGMENT3", type: "VARCHAR(150)", remarks: "属性3")   
            column(name: "SEGMENT4", type: "VARCHAR(150)", remarks: "属性4")   
            column(name: "SEGMENT5", type: "VARCHAR(150)", remarks: "属性5")   
            column(name: "SEGMENT6", type: "VARCHAR(150)", remarks: "属性6")   
            column(name: "SEGMENT7", type: "VARCHAR(150)", remarks: "属性7")   
            column(name: "SEGMENT8", type: "VARCHAR(150)", remarks: "属性8")   
            column(name: "SEGMENT9", type: "VARCHAR(150)", remarks: "属性9")   
            column(name: "SEGMENT10", type: "VARCHAR(150)", remarks: "属性10")   
            column(name: "SEGMENT11", type: "VARCHAR(150)", remarks: "属性11")   
            column(name: "SEGMENT12", type: "VARCHAR(150)", remarks: "属性12")   
            column(name: "SEGMENT13", type: "VARCHAR(150)", remarks: "属性13")   
            column(name: "SEGMENT14", type: "VARCHAR(150)", remarks: "属性14")   
            column(name: "SEGMENT15", type: "VARCHAR(150)", remarks: "属性15")   
            column(name: "SEGMENT16", type: "VARCHAR(150)", remarks: "属性16")   
            column(name: "SEGMENT17", type: "VARCHAR(150)", remarks: "属性17")   
            column(name: "SEGMENT18", type: "VARCHAR(150)", remarks: "属性18")   
            column(name: "SEGMENT19", type: "VARCHAR(150)", remarks: "属性19")   
            column(name: "SEGMENT20", type: "VARCHAR(150)", remarks: "属性20")   
            column(name: "SEGMENT21", type: "VARCHAR(150)", remarks: "属性21")   
            column(name: "SEGMENT22", type: "VARCHAR(150)", remarks: "属性22")   
            column(name: "SEGMENT23", type: "VARCHAR(150)", remarks: "属性23")   
            column(name: "SEGMENT24", type: "VARCHAR(150)", remarks: "属性24")   
            column(name: "SEGMENT25", type: "VARCHAR(150)", remarks: "属性25")   
            column(name: "SEGMENT26", type: "VARCHAR(150)", remarks: "属性26")   
            column(name: "SEGMENT27", type: "VARCHAR(150)", remarks: "属性27")   
            column(name: "SEGMENT28", type: "VARCHAR(150)", remarks: "属性28")   
            column(name: "SEGMENT29", type: "VARCHAR(150)", remarks: "属性29")   
            column(name: "SEGMENT30", type: "VARCHAR(150)", remarks: "属性30")   
            column(name: "SEGMENT31", type: "VARCHAR(150)", remarks: "属性31")   
            column(name: "SEGMENT32", type: "VARCHAR(150)", remarks: "属性32")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "GL_ACCOUNT_ENTRY", indexName: "GL_ACCOUNT_ENTRY_N1") {
                    column(name: "ACC_ENTITY_ID")
                    column(name: "TRANSACTION_HEADER_ID")
                    column(name: "TRANSACTION_TYPE")
                }
                createIndex(tableName: "GL_ACCOUNT_ENTRY", indexName: "GL_ACCOUNT_ENTRY_N2") {
                    column(name: "ACC_ENTITY_ID")
                    column(name: "IMPORTED_FLAG")
                    column(name: "TRANSACTION_TYPE")
                }
                createIndex(tableName: "GL_ACCOUNT_ENTRY", indexName: "GL_ACCOUNT_ENTRY_N3") {
                    column(name: "GL_INTERFACE_ID")
                }
                createIndex(tableName: "GL_ACCOUNT_ENTRY", indexName: "GL_ACCOUNT_ENTRY_N4") {
                    column(name: "IMPORTED_FLAG")
                }
                createIndex(tableName: "GL_ACCOUNT_ENTRY", indexName: "GL_ACCOUNT_ENTRY_N5") {
                    column(name: "BATCH_CODE")
                }
        
    }


}