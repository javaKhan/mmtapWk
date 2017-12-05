package com.mmtap.wk.modular.order.utils;

import org.apache.poi.ss.usermodel.*;

public class DefaultCellStyleImpl implements DefaultCellStyle {
    @Override
    public CellStyle setCellStyle(Workbook workbook) {
        // create style for header cells
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("宋体");
        cellStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        cellStyle.setFillPattern(FillPatternType.forInt(1));
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        font.setBold(true);
        font.setFontHeightInPoints((short)20);
        font.setColor(IndexedColors.WHITE.getIndex());
        cellStyle.setFont(font);
        return cellStyle;
    }
}
