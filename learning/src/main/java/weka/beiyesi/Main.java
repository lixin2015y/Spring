package weka.beiyesi;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.AdaBoostM1;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;

public class Main {

    private static String arffPath = "/Users/lixin08_dxm/IdeaProjects/Spring/ARDS生理分型.arff";
    private static String arffPathTest = "/Users/lixin08_dxm/IdeaProjects/Spring/ARDS生理分型.arff";

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

//        ArrayList<Attribute> attributes = new ArrayList<>();
//        attributes.add(new Attribute("Cluster", Arrays.asList("1", "2", "3")));
//        attributes.add(new Attribute("D0_PO2_FiO2_Ratio", Arrays.asList("99", "150", "250", "301")));
//        attributes.add(new Attribute("D0_Lac", Arrays.asList("1", "2", "3", "4")));
//        attributes.add(new Attribute("D0_pH", Arrays.asList("7.2", "7.4", "7.5", "7.6")));
//        attributes.add(new Attribute("D0_PO2", Arrays.asList("49", "51", "81", "111")));
//        attributes.add(new Attribute("D0_PCO2", Arrays.asList("29", "31", "41", "51")));
//        attributes.add(new Attribute("D0_HCO3", Arrays.asList("17", "19", "23", "27")));
//        attributes.add(new Attribute("Respiratory_support", Arrays.asList("1", "2", "3")));
//        Instances instances = new Instances("repo_popular", attributes, 0);
//        instances.setClassIndex(0);
//        Instance instance = new DenseInstance(8);
//        instance.setDataset(instances);
//        instance.setValue(1, "250");
//        instance.setValue(2, "3");
//        instance.setValue(3, "7.4");
//        instance.setValue(4, "81");
//        instance.setValue(5, "31");
//        instance.setValue(6, "19");
//        instance.setValue(7, "2");
//        double v = classifier8.classifyInstance(instance);
//        System.out.println(v);

        File inputFile = new File(arffPathTest);
        ArffLoader atf = new ArffLoader();
        atf.setFile(inputFile);
        // 读入训练文件
        Instances instances = atf.getDataSet();
        instances.setClassIndex(0);
        int successCount = 0;
        for (Instance instance : instances) {
            double v = classifier8.classifyInstance(instance);
            if (v == instance.classValue()) {
                successCount++;
            }
        }
        System.out.println(successCount + "=" + instances.size());
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
