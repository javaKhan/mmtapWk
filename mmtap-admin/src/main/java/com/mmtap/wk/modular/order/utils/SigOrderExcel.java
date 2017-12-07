package com.mmtap.wk.modular.order.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;

public class SigOrderExcel extends ExcelView {
    @Override
    protected void setStyle(Workbook workbook) {
        DefaultCellStyle defaultCellStyle = new DefaultCellStyleImpl();
        super.cellStyle = defaultCellStyle.setCellStyle(workbook);
    }

    @Override
    protected void setRow(Sheet sheet, Map<String, Object> map) {
        // create header row
        Row header = sheet.createRow(0);
        header.setHeight((short)500);



        Row body = sheet.createRow(1);
        body.setHeight((short)300);
        int col = 0;


        //订单信息
        header.createCell(col).setCellValue("订单号");
        header.getCell(col).setCellStyle(super.cellStyle);
        body.createCell(col).setCellValue(MapUtils.getString(map,"oid"));
        col++;


        header.createCell(col).setCellValue("接单时间");
        header.getCell(col).setCellStyle(super.cellStyle);
        body.createCell(col).setCellValue(MapUtils.getString(map,"createtime"));
        col++;

        header.createCell(col).setCellValue("接单人");
        header.getCell(col).setCellStyle(super.cellStyle);
        body.createCell(col).setCellValue(MapUtils.getString(map,"name"));
        col++;

        header.createCell(col).setCellValue("订单备注");
        header.getCell(col).setCellStyle(super.cellStyle);
        body.createCell(col).setCellValue(MapUtils.getString(map,"comments"));
        col++;

        //客户信息
        header.createCell(col).setCellValue("客户名称");
        header.getCell(col).setCellStyle(super.cellStyle);
        body.createCell(col).setCellValue(MapUtils.getString(map,"customname"));
        col++;

        header.createCell(col).setCellValue("电话");
        header.getCell(col).setCellStyle(super.cellStyle);
        body.createCell(col).setCellValue(MapUtils.getString(map,"mobile"));
        col++;

        header.createCell(col).setCellValue("QQ/微信");
        header.getCell(col).setCellStyle(super.cellStyle);
        body.createCell(col).setCellValue(MapUtils.getString(map,"netid"));
        col++;

        header.createCell(col).setCellValue("旺旺");
        header.getCell(col).setCellStyle(super.cellStyle);
        body.createCell(col).setCellValue(MapUtils.getString(map,"wwid"));
        col++;

        header.createCell(col).setCellValue("所在地");
        header.getCell(col).setCellStyle(super.cellStyle);
        body.createCell(col).setCellValue(MapUtils.getString(map,"place"));
        col++;

        header.createCell(col).setCellValue("来源");
        header.getCell(col).setCellStyle(super.cellStyle);
        body.createCell(col).setCellValue(MapUtils.getString(map,"come"));
        col++;

        header.createCell(col).setCellValue("地址");
        header.getCell(col).setCellStyle(super.cellStyle);
        body.createCell(col).setCellValue(MapUtils.getString(map,"address"));
        col++;

        header.createCell(col).setCellValue("客户备注");
        header.getCell(col).setCellStyle(super.cellStyle);
        body.createCell(col).setCellValue(MapUtils.getString(map,"cuscom"));
        col++;

        List<Map> workList = (List) map.get("works");
        int x = 1;
        for (Map work : workList){
//            super.cellStyle.setFillForegroundColor((short)x);
            if(null!=work){
                header.createCell(col).setCellValue("业务类型"+x);
                header.getCell(col).setCellStyle(super.cellStyle);
                body.createCell(col).setCellValue(MapUtils.getString(work,"businessname"));
                col++;
                header.createCell(col).setCellValue("业务状态"+x);
                header.getCell(col).setCellStyle(super.cellStyle);
                body.createCell(col).setCellValue(MapUtils.getString(work,"flowname"));
                col++;
                header.createCell(col).setCellValue("业务价格"+x);
                header.getCell(col).setCellStyle(super.cellStyle);
                body.createCell(col).setCellValue(MapUtils.getString(work,"price"));
                col++;
                header.createCell(col).setCellValue("业务备注"+x);
                header.getCell(col).setCellStyle(super.cellStyle);
                body.createCell(col).setCellValue(MapUtils.getString(work,"workcom"));
                col++;

                //处理业务信息的JSON
                String infoStr = MapUtils.getString(work,"info");
                if(null!=infoStr && !"".equals(infoStr)){
                    Map info = JSON.parseObject(infoStr);
                    for (Object entry : info.entrySet()){
                        Map.Entry e = (Map.Entry)entry;
                        header.createCell(col).setCellValue(""+e.getKey()+x);
                        header.getCell(col).setCellStyle(super.cellStyle);
                        body.createCell(col).setCellValue(""+e.getValue());
                        col++;
                    }
                }

                x++;//下一业务标示
            }
        }




    }
}
