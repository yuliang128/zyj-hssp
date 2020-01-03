package com.hand.hap.excel.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.hand.hap.excel.ExcelException;

/**
 * Created by jialong.zuo@hand-china.com on 2016/11/30.
 */
public interface IHapExcelExportService {

    /**
     * @param responseData        导出数据
     * @param httpServletRequest  httpServletRequest
     * @param httpServletResponse httpServletResponse
     * @param columns             选中所需导出的列
     * @param way                 导出方式
     * @throws IOException    io异常
     * @throws ExcelException excel异常
     * @throws SQLException   sql异常
     */
    void exportAndDownloadExcel(Object responseData, HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse, List<String> columns, String way) throws IOException, SQLException, ExcelException;


}
