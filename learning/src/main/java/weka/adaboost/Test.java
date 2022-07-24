package weka.adaboost;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.lang.instrument.ClassFileTransformer;
import java.text.DecimalFormat;

public class Test {
    public static void main(String[] args) throws Exception {
        Classifier classifier = (Classifier) weka.core.SerializationHelper.read("D:/Sepsis_预测-ada.model");
        File inputFile = new File("output/Sepsis_预测-ada-test==.arff");
        ArffLoader atf = new ArffLoader();
        atf.setFile(inputFile);
        Instances instancesTest = atf.getDataSet();
        instancesTest.setClassIndex(0);
        DecimalFormat dFormat = new DecimalFormat("#.##");
        for (int i = 0; i < instancesTest.size(); i++) {
            double target = instancesTest.instance(i).classValue();
            double predicate = classifier.classifyInstance(instancesTest.instance(i));
            System.out.println(instancesTest.instance(i));
            System.out.println(dFormat.format(target) + "----" + dFormat.format(predicate));
        }

    }
}
