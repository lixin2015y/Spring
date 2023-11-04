package weka.beiyesi;

import java.io.File;

import weka.classifiers.Classifier;
import weka.classifiers.meta.AdaBoostM1;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;

public class Main {
    public static void main(String[] args) throws Exception {
        Classifier classifier = AdaBoostM1.forName("weka.classifiers.bayes.NaiveBayes", args);
        ArffLoader atf = new ArffLoader();
        // 读入训练文件
        File inputFile = new File("/Users/lixin08_dxm/Desktop/machine-learning/arff/ARDS生理分型.arff");
        System.out.println(inputFile.exists());
        atf.setFile(inputFile);
        Instances instancesTrain = atf.getDataSet();
        instancesTrain.setClassIndex(0);
        // 训练
        classifier.buildClassifier(instancesTrain);
        SerializationHelper.write("/Users/lixin08_dxm/Desktop/machine-learning/models/ARDS生理分型.model", classifier);
    }
}
