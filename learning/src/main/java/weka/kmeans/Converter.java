package weka.kmeans;


import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.File;

public class Converter {

    public static void main(String[] args) throws Exception {
        Instances allData = DataSource.read("/Users/lixin08_dxm/Desktop/气压伤_原始数据.csv");
        ArffSaver saver = new ArffSaver();
        saver.setInstances(allData);
        saver.setFile(new File("/Users/lixin08_dxm/Desktop/气压伤_原始数据.arff"));
        saver.writeBatch();
        System.out.println("已经转化为arrf文件");
    }
}


