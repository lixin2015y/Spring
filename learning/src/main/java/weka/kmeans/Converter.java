package weka.kmeans;


import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.File;

public class Converter {

    public static void main(String[] args) throws Exception {
        Instances allData = DataSource.read("input/ARDS_Kmeans(1).csv");
        ArffSaver saver = new ArffSaver();
        saver.setInstances(allData);
        saver.setFile(new File("C:/Users/lixin/Desktop/project/Spring/learning/src/main/resources/output/ARDS_Kmeans(1).arff"));
        saver.writeBatch();
        System.out.println("已经转化为arrf文件");
    }
}


