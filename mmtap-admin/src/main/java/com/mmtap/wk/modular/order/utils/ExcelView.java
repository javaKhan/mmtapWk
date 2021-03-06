package com.mmtap.wk.modular.order.utils;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;

public abstract class ExcelView extends AbstractXlsView {

    public CellStyle cellStyle;

    protected abstract void setStyle(Workbook workbook);
    protected abstract void setRow(Sheet sheet, Map<String, Object> map);


    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String excelName = map.get("filename").toString() + ".xls";
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(excelName,"utf-8"));
        String Agent = request.getHeader("User-Agent");
        if (null != Agent) {
            Agent = Agent.toLowerCase();
            if (Agent.indexOf("firefox") != -1) {
                response.setHeader("content-disposition", String.format("attachment;filename*=utf-8'zh_cn'%s", URLEncoder.encode(excelName, "utf-8")));

            } else {
                response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(excelName, "utf-8"));
            }
        }
        response.setContentType("application/ms-excel; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");



        response.setContentType("application/ms-excel; charset=UTF-8");
        Sheet sheet = workbook.createSheet(System.currentTimeMillis()+"");
        sheet.setDefaultColumnWidth(30);
        this.setStyle(workbook);
        this.setRow(sheet, map);
    }
}
