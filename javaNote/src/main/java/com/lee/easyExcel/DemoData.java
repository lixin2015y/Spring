package com.lee.easyExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DemoData {
    // 指定列的下标
    @ExcelProperty(value = "string2", converter = CustomStringStringConverter.class)
    private String string;

    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    private Date date;

    private Double doubleData;

    @ExcelProperty("撒旦法")
    private String string1;
}

