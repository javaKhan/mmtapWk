package com.mmtap.wk.modular.order.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.util.List;
import java.util.Map;

public class AllOrderExcel extends ExcelView {
    @Override
    protected void setStyle(Workbook workbook) {
        DefaultCellStyle defaultCellStyle = new DefaultCellStyleImpl();
        super.cellStyle = defaultCellStyle.setCellStyle(workbook);
    }

    @Override
    protected void setRow(Sheet sheet, Map<String, Object> map) {
        /**
         * 生成头行
         */
        Row header = sheet.createRow(0);
        header.setHeight((short)500);
        int col = 0;
        header.createCell(col).setCellValue("订单号");
        header.getCell(col).setCellStyle(super.cellStyle);
        col++;
        header.createCell(col).setCellValue("接单时间");
        header.getCell(col).setCellStyle(super.cellStyle);
        col++;
        header.createCell(col).setCellValue("接单人");
        header.getCell(col).setCellStyle(super.cellStyle);
        col++;
        header.createCell(col).setCellValue("订单备注");
        header.getCell(col).setCellStyle(super.cellStyle);
        col++;
        header.createCell(col).setCellValue("客户名称");
        header.getCell(col).setCellStyle(super.cellStyle);
        col++;
        header.createCell(col).setCellValue("电话");
        header.getCell(col).setCellStyle(super.cellStyle);
        col++;
        header.createCell(col).setCellValue("QQ/微信");
        header.getCell(col).setCellStyle(super.cellStyle);
        col++;
        header.createCell(col).setCellValue("旺旺");
        header.getCell(col).setCellStyle(super.cellStyle);
        col++;
        header.createCell(col).setCellValue("所在地");
        header.getCell(col).setCellStyle(super.cellStyle);
        col++;
        header.createCell(col).setCellValue("来源");
        header.getCell(col).setCellStyle(super.cellStyle);
        col++;
        header.createCell(col).setCellValue("地址");
        header.getCell(col).setCellStyle(super.cellStyle);
        col++;
        header.createCell(col).setCellValue("客户备注");
        header.getCell(col).setCellStyle(super.cellStyle);
        col=0;

        /**
         * 内容行生成
         */
        List<Map> orders = (List)map.get("orders");
        if(null==orders||orders.size()<1){
            return;
        }
        for (int x = 1; x <= orders.size(); x++) {
            Map order = orders.get(x-1);
            int pointer = 0;
            Row row = sheet.createRow(x);
            row.setHeight((short) 300);
            //订单信息
            row.createCell(pointer++).setCellValue(MapUtils.getString(order, "oid"));
            row.createCell(pointer++).setCellValue(MapUtils.getString(order, "createtime"));
            row.createCell(pointer++).setCellValue(MapUtils.getString(order, "name"));
            row.createCell(pointer++).setCellValue(MapUtils.getString(order, "comments"));
            //订单的客户信息
            row.createCell(pointer++).setCellValue(MapUtils.getString(order, "customname"));
            row.createCell(pointer++).setCellValue(MapUtils.getString(order, "mobile"));
            row.createCell(pointer++).setCellValue(MapUtils.getString(order, "netid"));
            row.createCell(pointer++).setCellValue(MapUtils.getString(order, "wwid"));
            row.createCell(pointer++).setCellValue(MapUtils.getString(order, "place"));
            row.createCell(pointer++).setCellValue(MapUtils.getString(order, "come"));
            row.createCell(pointer++).setCellValue(MapUtils.getString(order, "address"));
            row.createCell(pointer++).setCellValue(MapUtils.getString(order, "cuscom"));


            //订单的业务信息
            List<Map> workList = (List) order.get("works");
            int m = 1;
            for (Map work : workList) {
                if (null != work) {
                    row.createCell(pointer++).setCellValue("业务类型:" + m);
                    row.createCell(pointer++).setCellValue(MapUtils.getString(work, "businessname"));
                    row.createCell(pointer++).setCellValue("业务状态:" + m);
                    row.createCell(pointer++).setCellValue(MapUtils.getString(work, "flowname"));
                    row.createCell(pointer++).setCellValue("业务价格:" + m);
                    row.createCell(pointer++).setCellValue(MapUtils.getString(work, "price"));
                    row.createCell(pointer++).setCellValue("业务备注:" + m);
                    row.createCell(pointer++).setCellValue(MapUtils.getString(work, "workcom"));

                    //处理业务信息的JSON
                    String infoStr = MapUtils.getString(work, "info");
                    if (null != infoStr && !"".equals(infoStr)) {
                        Map info = JSON.parseObject(infoStr);
                        for (Object entry : info.entrySet()) {
                            Map.Entry e = (Map.Entry)entry;

                            Cell cellKey  = row.createCell(pointer++);

                            CellStyle infoKeyStyle = sheet.getWorkbook().createCellStyle();
                            infoKeyStyle.setFillForegroundColor((short)59);

                            infoKeyStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
                            infoKeyStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                            infoKeyStyle.setAlignment(HorizontalAlignment.RIGHT);
                            cellKey.setCellStyle(infoKeyStyle);

                            cellKey.setCellValue((e.getKey()+":"));


                            row.createCell(pointer++).setCellValue(""+e.getValue());
                        }
                    }

                    m++;//下一业务标示
                }
            }



        }

    }//方法结束
}
