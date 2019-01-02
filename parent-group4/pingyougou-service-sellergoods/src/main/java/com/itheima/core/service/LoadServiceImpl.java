package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;

import com.itheima.core.LoadService;
import com.itheima.core.dao.good.GoodsDao;
import com.itheima.core.dao.order.OrderDao;
import com.itheima.core.dao.order.OrderItemDao;
import com.itheima.core.pojo.good.Brand;
import com.itheima.core.pojo.good.Goods;
import com.itheima.core.pojo.order.Order;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class LoadServiceImpl implements LoadService {

    @Autowired
    private OrderDao orderdao;
    @Autowired
    private OrderItemDao orderItemdao;
    @Autowired
    private GoodsDao goodsDao;


    @Override
    public void readExcelFile() throws IOException {
        //创建工作簿
        HSSFWorkbook workBook = new HSSFWorkbook();

        //创建工作表
        HSSFSheet ordersheet = workBook.createSheet("order");
        HSSFSheet goodssheet = workBook.createSheet("goods");

        goodssheet.setDefaultColumnWidth(20);
        goodssheet.setDefaultRowHeightInPoints(12);



        ordersheet.setDefaultColumnWidth(20);
        ordersheet.setDefaultRowHeightInPoints(12);
        HSSFFont font = workBook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("微软雅黑");

        HSSFCellStyle style = workBook.createCellStyle();
        //   style.setAlignment(HorizontalAlignment.CENTER); // 居中
        style.setFont(font);
        HSSFCell cell;

        //2.创建首行
        HSSFRow headerRow = ordersheet.createRow(0);
        HSSFRow headerRow1 = goodssheet.createRow(0);

        //3.创建行内的每一个单元格 总共5列
        headerRow.createCell(0).setCellValue("order_id");
        headerRow.createCell(1).setCellValue("payment");
        headerRow.createCell(2).setCellValue("payment_type");
        headerRow.createCell(3).setCellValue("status");
        headerRow.createCell(4).setCellValue("create_time");
        headerRow.createCell(5).setCellValue("update_time");
        headerRow.createCell(6).setCellValue("user_id");
        headerRow.createCell(7).setCellValue("receiver_area_name");
        headerRow.createCell(8).setCellValue("receiver_mobile");
        headerRow.createCell(9).setCellValue("receiver");
        headerRow.createCell(10).setCellValue("seller_id");

        headerRow1.createCell(0).setCellValue("id");
        headerRow1.createCell(1).setCellValue("seller_id");
        headerRow1.createCell(2).setCellValue("goods_name");
        headerRow1.createCell(3).setCellValue("audit_status");
        headerRow1.createCell(4).setCellValue("brand_id");
        headerRow1.createCell(5).setCellValue("caption");
        headerRow1.createCell(6).setCellValue("category1_id");
        headerRow1.createCell(7).setCellValue("category2_id");
        headerRow1.createCell(8).setCellValue("category3_id");
        headerRow1.createCell(9).setCellValue("price");

        List<Order> orders = orderdao.selectByExample(null);
        List<Goods> good = goodsDao.selectByExample(null);
        for (int j=1;j<good.size();j++){
            Goods goods = good.get(j);
            headerRow1 = goodssheet.createRow(j);
            cell = headerRow1.createCell(0);
            cell.setCellStyle(style);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(goods.getId().toString());

            cell = headerRow1.createCell(1);
            cell.setCellStyle(style);
            cell.setCellValue(goods.getSellerId());

            cell = headerRow1.createCell(2);
            cell.setCellStyle(style);
            cell.setCellValue(goods.getGoodsName());

            cell = headerRow1.createCell(3);
            cell.setCellStyle(style);
            cell.setCellValue(goods.getAuditStatus());

            cell = headerRow1.createCell(4);
            cell.setCellStyle(style);
            cell.setCellValue(goods.getBrandId());

            cell = headerRow1.createCell(5);
            cell.setCellStyle(style);
            cell.setCellValue(goods.getCaption());

            cell = headerRow1.createCell(6);
            cell.setCellStyle(style);
            cell.setCellValue(goods.getCategory1Id());

            cell = headerRow1.createCell(7);
            cell.setCellStyle(style);
            cell.setCellValue(goods.getCategory2Id());

            cell = headerRow1.createCell(8);
            cell.setCellStyle(style);
            cell.setCellValue(goods.getCategory3Id());

            cell = headerRow1.createCell(9);
            cell.setCellStyle(style);
            cell.setCellValue(goods.getPrice().toString());
        }

        //循环插入数据
        for (int i = 1; i <= orders.size(); i++) {
//            //每遍历一次,在末尾行动态添加一行
//            HSSFRow data = ordersheet.createRow(ordersheet.getLastRowNum()+1);
            Order order = orders.get(i);

            //动态添加数据
            headerRow = ordersheet.createRow(i);
            cell = headerRow.createCell(0);
            cell.setCellStyle(style);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(order.getOrderId().toString());

            cell = headerRow.createCell(1);
            cell.setCellStyle(style);

            cell.setCellValue(order.getPayment().toString());

            cell = headerRow.createCell(2);
            cell.setCellStyle(style);
            cell.setCellValue(order.getPaymentType());

            cell = headerRow.createCell(3);
            cell.setCellStyle(style);
            cell.setCellValue(order.getStatus());

            cell = headerRow.createCell(4);
            cell.setCellStyle(style);
            cell.setCellValue(order.getCreateTime().toString());

            cell = headerRow.createCell(5);
            cell.setCellStyle(style);
            cell.setCellValue(order.getUpdateTime().toString());

            cell = headerRow.createCell(6);
            cell.setCellStyle(style);
            cell.setCellValue(order.getUserId().toString());

            cell = headerRow.createCell(7);
            cell.setCellStyle(style);
            cell.setCellValue(order.getReceiverAreaName());

            cell = headerRow.createCell(8);
            cell.setCellStyle(style);
            cell.setCellValue(order.getReceiverMobile());

            cell = headerRow.createCell(9);
            cell.setCellStyle(style);
            cell.setCellValue(order.getReceiver());

            cell = headerRow.createCell(10);
            cell.setCellStyle(style);
            cell.setCellValue(order.getSellerId().toString());

            FileOutputStream outputStream = new FileOutputStream(new File("D:\\poi\\用户(订单,商品).xlsx"));
            workBook.write(outputStream);
            workBook.close();//记得关闭工作簿

        }
    }
}