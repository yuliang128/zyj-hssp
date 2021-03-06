package com.hand.hap.generator.service.impl;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.generator.dto.DBColumn;
import com.hand.hap.generator.dto.DBTable;
import com.hand.hap.generator.dto.FtlInfo;
import com.hand.hap.generator.dto.GeneratorInfo;
import com.hand.hap.mybatis.util.StringUtil;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.system.service.impl.BaseServiceImpl;

/**
 * @author jialong.zuo@hand-china.com jialin.xing@hand-china.com
 * @date 2016/10/25.
 */
public class FileUtil {

    private static final Pattern LINE_PATTERN = Pattern.compile("_(\\w)");

    private FileUtil() {

    }

    public enum pType {
        Controller, MapperXml, Mapper, Service, Impl, Html
    }

    private static List<String> allClassFiles = new ArrayList<>();
    private static List<String> allXmlFiles = new ArrayList<>();
    private static List<String> allHtmlFiles = new ArrayList<>();


    public static String columnToCamel(String str) {

        str = str.toLowerCase();
        Matcher matcher = LINE_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        String s = sb.toString();
        return s;
    }

    public static String camelToColumn(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    public static void createDto(DBTable table, GeneratorInfo generatorInfo) throws IOException {
        // key 是否需要引入相对包
        boolean needUtil = false;
        boolean needNotNull = false;
        boolean needNotEmpty = false;
        boolean needMath = false;

        String name = generatorInfo.getDtoName().substring(0, generatorInfo.getDtoName().indexOf("."));
        String projectPath = generatorInfo.getProjectPath();
        String parentPackagePath = generatorInfo.getParentPackagePath();
        String packagePath = generatorInfo.getPackagePath();
        String directory = projectPath + "/src/main/java/" + parentPackagePath + "/" + packagePath + "/dto/"
                + generatorInfo.getDtoName();
        String remark = "";
        File file = new File(directory);
        createFileDir(file);
        file.createNewFile();
        List<DBColumn> columns = table.getColumns();
        // 判断是否需要引入包
        for (DBColumn s : columns) {
            switch (s.getJdbcType().toUpperCase()) {
                case "DATE":
                case "TIMESTAMP":
                case "DATETIME":
                case "TIME":
                    s.setJavaType("Date");
                    s.setJdbcType("DATE");
                    needUtil = true;
                    break;
                case "BIGINT":
                case "INT":
                case "INTEGER":
                case "TINYINT":
                    s.setJavaType("Long");
                    s.setJdbcType("DECIMAL");
                    break;
                case "DOUBLE":
                    s.setJavaType("Double");
                    s.setJdbcType("DECIMAL");
                    break;
                case "DECIMAL":
                    s.setJavaType("BigDecimal");
                    s.setJdbcType("DECIMAL");
                    needMath = true;
                    break;
                case "NUMBER":
                case "FLOAT":
                    s.setJavaType("Float");
                    s.setJdbcType("DECIMAL");
                    break;
                default:
                    s.setJavaType("String");
                    s.setJdbcType("VARCHAR");
                    break;
            }
            if (s.isNotNull()) {
                needNotNull = true;
            } else if (s.isNotEmpty()) {
                needNotEmpty = true;
            }
        }
        // 生成属性
        StringBuilder sb = new StringBuilder();
        String dir1 = parentPackagePath + "/" + packagePath + "/dto";
        dir1 = dir1.replaceAll("/", ".");
        sb.append("package " + dir1 + ";\r\n\r\n");
        sb.append("import javax.persistence.GeneratedValue;\r\n");
        sb.append("import javax.persistence.Id;\r\n");
        sb.append("import javax.persistence.Table;\r\n\r\n");

        sb.append("import lombok.Getter;\r\n");
        sb.append("import lombok.NoArgsConstructor;\r\n");
        sb.append("import lombok.Setter;\r\n");
        sb.append("import lombok.ToString;\r\n");
        sb.append("import org.hibernate.validator.constraints.Length;\r\n");
        sb.append("import com.hand.hap.mybatis.annotation.ExtensionAttribute;\r\n");
        sb.append("import com.hand.hap.system.dto.BaseDTO;\r\n");
        sb.append("import javax.persistence.Table;\r\n");
        if (needMath) {
            sb.append("import java.math.BigDecimal;\r\n");
        }
        if (needUtil) {
            sb.append("import java.util.Date;\r\n");
        }
        if (needNotNull) {
            sb.append("import javax.validation.constraints.NotNull;\r\n");
        }
        if (needNotEmpty) {
            sb.append("import org.hibernate.validator.constraints.NotEmpty;\r\n");
        }
        if (table.isMultiLanguage()) {
            String s1 = MultiLanguageField.class.getName();
            String s2 = MultiLanguage.class.getName();
            sb.append("import " + s1 + ";\r\n");
            sb.append("import " + s2 + ";\r\n");
            sb.append("\r\n\r\n@MultiLanguage\r\n");
        }
        sb.append("@Getter\r\n");
        sb.append("@Setter\r\n");
        sb.append("@ToString\r\n");
        sb.append("@NoArgsConstructor\r\n");
        sb.append("@ExtensionAttribute(disable=true)\r\n");
        sb.append("@Table(name = " + "\"" + table.getName() + "\")\r\n");
        sb.append("public class " + name + " extends BaseDTO {\r\n\r\n");

        //生成filed
        for (DBColumn co : columns) {
            String filed1 = "     public static final String FIELD_" + co.getName().toUpperCase() + " = \"" + columnToCamel(co.getName()) + "\";\r\n";
            sb.append(filed1);
        }
        sb.append("\r\n\r\n");
        // 生成属性
        for (DBColumn cl : columns) {
            if (!StringUtil.isEmpty(cl.getRemarks())) {
                remark = "    /**\r\n     * " + cl.getRemarks() + "\r\n     */\r\n";
                sb.append(remark);
            }
            if (cl.isId()) {
                sb.append("     @Id\r\n     @GeneratedValue\r\n");
            } else {
                if (cl.isNotEmpty()) {
                    sb.append("     @NotEmpty\r\n");
                } else if (cl.isNotNull()) {
                    sb.append("     @NotNull\r\n");
                }
                if (cl.getJavaType().equalsIgnoreCase("String")) {
                    sb.append("     @Length(max = ");
                    sb.append(cl.getColumnLength() + ")\r\n");
                }
            }
            if (cl.isMultiLanguage()) {
                sb.append("     @MultiLanguageField\r\n");
            }
            String str = "     private " + cl.getJavaType() + " " + columnToCamel(cl.getName()) + ";";
            str += "\r\n\r\n";
            sb.append(str);

        }
        /*// 生成get 和 set方法
        sb.append("\r\n");
        for (DBColumn cl : columns) {
            String name1 = columnToCamel(cl.getName());
            String name2 = name1.substring(0, 1).toUpperCase() + name1.substring(1);
            sb.append("     public void set" + name2 + "(" + cl.getJavaType() + " " + name1 + "){\r\n");
            sb.append("         this." + name1 + " = " + name1 + ";\r\n");
            sb.append("     }\r\n\r\n");
            sb.append("     public " + cl.getJavaType() + " get" + name2 + "(){\r\n");
            sb.append("         return " + name1 + ";\r\n");
            sb.append("     }\r\n\r\n");
        }*/
        sb.append("     }\r\n");

        PrintWriter p = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
        p.write(sb.toString());
        p.close();

    }

    public static void createFtlInfoByType(pType type, DBTable table, GeneratorInfo generatorInfo) throws IOException, TemplateException {
        String projectPath = generatorInfo.getProjectPath();
        String parentPackagePath = generatorInfo.getParentPackagePath();
        String packagePath = generatorInfo.getPackagePath();
        String htmlModelName = generatorInfo.getHtmlModelName();
        String pac = parentPackagePath + "/" + packagePath;
        FtlInfo info = new FtlInfo();
        String directory = null;
        List<String> importPackages = new ArrayList<>();
        if (type == pType.Controller) {
            directory = projectPath + "/src/main/java/" + pac + "/controllers/" + generatorInfo.getControllerName();
            importPackages.add("org.springframework.stereotype.Controller");
            importPackages.add(BaseController.class.getName());
            importPackages.add(IRequest.class.getName());
            importPackages.add(ResponseData.class.getName());
        } else if (type == pType.Mapper) {
            directory = projectPath + "/src/main/java/" + pac + "/mapper/" + generatorInfo.getMapperName();
            importPackages.add("com.hand.hap.mybatis.common.Mapper");
        } else if (type == pType.MapperXml) {
            directory = projectPath + "/src/main/resources/" + pac + "/mapper/" + generatorInfo.getMapperXmlName();
        } else if (type == pType.Service) {
            directory = projectPath + "/src/main/java/" + pac + "/service/" + generatorInfo.getServiceName();
            importPackages.add(ProxySelf.class.getName());
            importPackages.add(IBaseService.class.getName());
        } else if (type == pType.Impl) {
            directory = projectPath + "/src/main/java/" + pac + "/service/impl/" + generatorInfo.getImplName();
            importPackages.add(BaseServiceImpl.class.getName());
            importPackages.add("org.springframework.stereotype.Service");
        } else if (type == pType.Html) {
            directory = projectPath + "/src/main/webapp/WEB-INF/view/" + packagePath + "/"
                    + generatorInfo.getHtmlName();
        }
        pac = pac.replaceAll("/", ".");
        info.setPackageName(pac);
        info.setDir(directory);
        info.setProjectPath(projectPath);
        info.setImportName(importPackages);
        info.setHtmlModelName(htmlModelName);
        List<DBColumn> columns = table.getColumns();

        List<XmlColumnsInfo> columnsInfos = new ArrayList<>();

        for (DBColumn column : columns) {
            XmlColumnsInfo columnsInfo = new XmlColumnsInfo();
            columnsInfo.setTableColumnsName(columnToCamel(column.getName()));
            columnsInfo.setdBColumnsName(column.getName());
            columnsInfo.setJdbcType(column.getJdbcType());
            columnsInfos.add(columnsInfo);
        }
        info.setColumnsInfo(columnsInfos);
        createFtl(info, type, generatorInfo);
    }

    public static void createFtl(FtlInfo ftlInfo, pType type, GeneratorInfo generatorInfo) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        Template template = null;
        Map<String, Object> map = new HashMap<>();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        cfg.setServletContextForTemplateLoading(request.getServletContext(), "WEB-INF/view/generator/ftl");

        if (type == pType.Controller) {
            template = cfg.getTemplate("controllers.ftl");
        } else if (type == pType.MapperXml) {
            template = cfg.getTemplate("mapperxml.ftl");
        } else if (type == pType.Mapper) {
            template = cfg.getTemplate("mapper.ftl");
        } else if (type == pType.Service) {
            template = cfg.getTemplate("service.ftl");
        } else if (type == pType.Impl) {
            template = cfg.getTemplate("impl.ftl");
        } else if (type == pType.Html) {
            template = cfg.getTemplate(ftlInfo.getHtmlModelName());
        }
        if (null != template) {
            template.setEncoding("UTF-8");
            File file = new File(ftlInfo.getDir());
            createFileDir(file);
            OutputStream out = new FileOutputStream(file);
            map.put("package", ftlInfo.getPackageName());
            map.put("import", ftlInfo.getImportName());
            map.put("name", ftlInfo.getFileName());
            map.put("dtoName", generatorInfo.getDtoName().substring(0, generatorInfo.getDtoName().indexOf('.')));
            map.put("controllerName",
                    generatorInfo.getControllerName().substring(0, generatorInfo.getControllerName().indexOf('.')));
            map.put("implName", generatorInfo.getImplName().substring(0, generatorInfo.getImplName().indexOf('.')));
            map.put("serviceName",
                    generatorInfo.getServiceName().substring(0, generatorInfo.getServiceName().indexOf('.')));
            map.put("mapperName", generatorInfo.getMapperName().substring(0, generatorInfo.getMapperName().indexOf('.')));
            map.put("xmlName",
                    generatorInfo.getMapperXmlName().substring(0, generatorInfo.getMapperXmlName().indexOf('.')));
            map.put("columnsInfo", ftlInfo.getColumnsInfo());
            String url = generatorInfo.getTargetName().toLowerCase();
            if (url.endsWith("_b")) {
                url = url.substring(0, url.length() - 2);
            }
            int functionIndex = url.indexOf("_");
            url = url.substring(0, functionIndex) + "/" + url.replaceAll("_", "-").substring(functionIndex + 1, url.length());
            map.put("baseUrl", "/" + url);
            template.process(map, new OutputStreamWriter(out));
            out.flush();
            out.close();
        }
    }

    /**
     * 判断文件目录是否存在不存在则创建
     *
     * @param file
     * @throws IOException
     */
    private static void createFileDir(File file) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }

    /**
     * 判断文件是否已经存在
     *
     * @param generatorInfo
     * @return
     */
    public static int isFileExist(GeneratorInfo generatorInfo) {
        int rs = 0;
        String classDir = generatorInfo.getProjectPath() + "/src/main/java/" + generatorInfo.getParentPackagePath();
        String xmlDir = generatorInfo.getProjectPath() + "/src/main/resources/" + generatorInfo.getParentPackagePath();
        String htmlDir = generatorInfo.getProjectPath() + "/src/main/webapp/WEB-INF/view";
        getFileList(classDir, classDir, generatorInfo);
        // 判断有没有重复的java文件

        for (String name : allClassFiles) {
            if (name.equals(generatorInfo.getDtoName())) {
                if ("Create".equalsIgnoreCase(generatorInfo.getDtoStatus())) {
                    rs = 1;
                    break;
                } else if ("Cover".equalsIgnoreCase(generatorInfo.getDtoStatus())) {
                    File file1 = new File(
                            classDir + "/" + generatorInfo.getPackagePath() + "/dto/" + generatorInfo.getDtoName());
                    if (!file1.exists()) {
                        rs = 1;
                        break;
                    }
                }
            }
        }

        if (rs == 0) {
            for (String name : allClassFiles) {
                if (name.equals(generatorInfo.getServiceName())) {
                    if ("Create".equalsIgnoreCase(generatorInfo.getServiceStatus())) {
                        rs = 2;
                        break;
                    } else if ("Cover".equalsIgnoreCase(generatorInfo.getServiceStatus())) {
                        File file1 = new File(classDir + "/" + generatorInfo.getPackagePath() + "/service/"
                                + generatorInfo.getServiceName());
                        if (!file1.exists()) {
                            rs = 2;
                            break;
                        }
                    }
                }
            }
        }

        if (rs == 0) {
            for (String name : allClassFiles) {
                if (name.equals(generatorInfo.getImplName())) {
                    if ("Create".equalsIgnoreCase(generatorInfo.getImplStatus())) {
                        rs = 3;
                        break;
                    } else if ("Cover".equalsIgnoreCase(generatorInfo.getImplStatus())) {
                        File file1 = new File(classDir + "/" + generatorInfo.getPackagePath() + "/service/impl/"
                                + generatorInfo.getImplName());
                        if (!file1.exists()) {
                            rs = 3;
                            break;
                        }
                    }
                }
            }
        }

        if (rs == 0) {
            for (String name : allClassFiles) {
                if (name.equals(generatorInfo.getControllerName())) {
                    if ("Create".equalsIgnoreCase(generatorInfo.getControllerStatus())) {
                        rs = 4;
                        break;
                    } else if ("Cover".equalsIgnoreCase(generatorInfo.getControllerStatus())) {
                        File file1 = new File(classDir + "/" + generatorInfo.getPackagePath() + "/controllers/"
                                + generatorInfo.getControllerName());
                        if (!file1.exists()) {
                            rs = 4;
                            break;
                        }
                    }
                }
            }
        }
        if (rs == 0) {
            for (String name : allClassFiles) {
                if (name.equals(generatorInfo.getMapperName())) {
                    if ("Create".equalsIgnoreCase(generatorInfo.getMapperStatus())) {
                        rs = 5;
                        break;
                    } else if ("Cover".equalsIgnoreCase(generatorInfo.getMapperStatus())) {
                        File file1 = new File(classDir + "/" + generatorInfo.getPackagePath() + "/mapper/"
                                + generatorInfo.getMapperName());
                        if (!file1.exists()) {
                            rs = 5;
                            break;
                        }
                    }
                }
            }
        }

        // 判断有没有重复的xml文件
        if (rs == 0) {
            getFileList(xmlDir, xmlDir, generatorInfo);
            for (String name : allXmlFiles) {
                if (name.equals(generatorInfo.getMapperXmlName())) {
                    if ("Create".equalsIgnoreCase(generatorInfo.getMapperXmlStatus())) {
                        rs = 6;
                        break;
                    } else if ("Cover".equalsIgnoreCase(generatorInfo.getMapperXmlStatus())) {
                        File file1 = new File(xmlDir + "/" + generatorInfo.getPackagePath() + "/mapper/"
                                + generatorInfo.getMapperXmlName());
                        if (!file1.exists()) {
                            rs = 6;
                            break;
                        }
                    }
                }
            }
        }
        // 判断有没有重复的html文件
        if (rs == 0) {
            getFileList(htmlDir, htmlDir, generatorInfo);
            for (String name : allHtmlFiles) {
                if (name.equals(generatorInfo.getHtmlName())) {
                    if ("Create".equalsIgnoreCase(generatorInfo.getHtmlStatus())) {
                        rs = 7;
                        break;
                    } else if ("Cover".equalsIgnoreCase(generatorInfo.getHtmlStatus())) {
                        File file1 = new File(
                                htmlDir + "/" + generatorInfo.getPackagePath() + "/" + generatorInfo.getHtmlName());
                        if (!file1.exists()) {
                            rs = 7;
                            break;
                        }
                    }
                }
            }
        }
        return rs;
    }

    // 获取文件夹下所有文件列表
    private static void getFileList(String basePath, String directory, GeneratorInfo generatorInfo) {
        File dir = new File(basePath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) {
                    getFileList(files[i].getAbsolutePath(), directory, generatorInfo);
                } else {
                    if (directory.equals(generatorInfo.getProjectPath() + "/src/main/java/"
                            + generatorInfo.getParentPackagePath())) {
                        allClassFiles.add(fileName);
                    } else if (directory.equals(generatorInfo.getProjectPath() + "/src/main/resources/"
                            + generatorInfo.getParentPackagePath())) {
                        allXmlFiles.add(fileName);
                    } else if (directory.equals(generatorInfo.getProjectPath() + "/src/main/webapp/WEB-INF/view")) {
                        allHtmlFiles.add(fileName);
                    }
                }
            }
        }

    }

}
