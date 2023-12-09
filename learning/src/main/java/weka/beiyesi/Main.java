package weka.beiyesi;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;

public class Main {

    private static String arffPath = "/Users/lixin08_dxm/IdeaProjects/Spring/ARDS生理分型.arff";
    private static String arffPathTest = "/Users/lixin08_dxm/IdeaProjects/Spring/ARDS生理分型-test.arff";

    private static String modelPath = "/Users/lixin08_dxm/IdeaProjects/Spring/ARDS生理分型.model";

    public static void main(String[] args) throws Exception {
        Classifier classifier = AdaBoostM1.forName("weka.classifiers.bayes.NaiveBayes", args);
        ArffLoader atf = new ArffLoader();
        // 读入训练文件
        File inputFile = new File(arffPath);
        atf.setFile(inputFile);
        Instances instancesTrain = atf.getDataSet();
        instancesTrain.setClassIndex(0);

        // 训练
        classifier.buildClassifier(instancesTrain);

        SerializationHelper.write(modelPath, classifier);
    }

    @Test
    public void test() throws Exception {
        NaiveBayes classifier8 = (NaiveBayes) weka.core.SerializationHelper.read(modelPath);
        File inputFile = new File(arffPathTest);
        ArffLoader atf = new ArffLoader();
        atf.setFile(inputFile);
        // 读入训练文件
        Instances instances = atf.getDataSet();
        instances.setClassIndex(0);
        int successCount = 0;
        for (int i = 0; i < instances.size(); i++) {
            double d = classifier8.classifyInstance(instances.instance(i));
            if (d == instances.get(i).classValue() + 1) {
                successCount++;
            }
            System.out.println(d + "===" + instances.get(i).classValue());
        }
        System.out.println(successCount + "====" + instances.size());

    }

    @Test
    public void test2() throws IOException {
        File inputFile = new File(arffPathTest);
        ArffLoader atf = new ArffLoader();
        atf.setFile(inputFile);
        // 读入训练文件
        Instances instances = atf.getDataSet();
        instances.setClassIndex(0);
        for (Instance instance : instances) {
            System.out.println("instance.classValue() = " + instance.classValue());
            System.out.println(instance);
        }
    }
}
