package com.lee.easyExcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import org.junit.Test;

public class SimpleRead {

    /**
     * 读取方式一
     */
    @Test
    public void test() {
        // 写法1：
        String fileName = "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();

    }

    /**
     * 读取方式二,读取多个sheet
     */
    @Test
    public void test2() {
        String fileName = "demo.xlsx";
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            ReadSheet readSheet2 = EasyExcel.readSheet(1).build();
            excelReader.read(readSheet, readSheet2);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }

    /**
     * 读取多个sheet
     */
    @Test
    public void test3() {
    }

}
