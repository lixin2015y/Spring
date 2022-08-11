package weka.stacking;

import org.junit.Test;
import weka.classifiers.Classifier;
import weka.classifiers.meta.Stacking;
import weka.core.*;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class StackingMain {
    @Test
    public void test() throws Exception {
        File inputFile = new File("output/stacking.arff");
        ArffLoader atf = new ArffLoader();
        atf.setFile(inputFile);
        // 读入训练文件
        Instances instancesTrain = atf.getDataSet();
        instancesTrain.setClassIndex(0);

        Classifier classifierJ48 = Stacking.forName("weka.classifiers.trees.J48", null);
        Classifier classifierLogistic = Stacking.forName("weka.classifiers.functions.Logistic", null);
        Classifier classifierRandomForest = Stacking.forName("weka.classifiers.trees.RandomForest", null);
        Classifier classifierNaiveBayes = Stacking.forName("weka.classifiers.bayes.NaiveBayes", null);

        Classifier[] classifiers = new Classifier[3];
        classifiers[0] = classifierLogistic;
        classifiers[1] = classifierRandomForest;
        classifiers[2] = classifierNaiveBayes;

        Stacking stacking = new Stacking();
        stacking.setClassifiers(classifiers);
        stacking.setMetaClassifier(classifierJ48);
        stacking.buildClassifier(instancesTrain);
        SerializationHelper.write("D:/stacking.model", stacking);
    }


    @Test
    public void test2() throws Exception {
        Classifier classifier8 = (Classifier) weka.core.SerializationHelper.read("D:/stacking.model");
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("class", Arrays.asList("0", "1")));
        attributes.add(new Attribute("apsiii"));
        attributes.add(new Attribute("aniongap_max"));
        attributes.add(new Attribute("bicarbonate_max"));
        attributes.add(new Attribute("bun_max"));
        attributes.add(new Attribute("bun_min"));
        attributes.add(new Attribute("fio2"));
        attributes.add(new Attribute("glucose_max"));
        attributes.add(new Attribute("hemoglobin_max"));
        attributes.add(new Attribute("pao2fio2ratio_max"));
        attributes.add(new Attribute("pao2fio2ratio_min"));
        attributes.add(new Attribute("ph_max"));
        attributes.add(new Attribute("ph_min"));
        attributes.add(new Attribute("po2_max"));
        attributes.add(new Attribute("po2_min"));
        attributes.add(new Attribute("pco2_max"));
        attributes.add(new Attribute("pco2_min"));
        attributes.add(new Attribute("potassium_max"));
        attributes.add(new Attribute("sodium_max"));
        Instance instance = new DenseInstance(attributes.size());
        instance.setValue(1,45);
        instance.setValue(2,21);
        instance.setValue(3,24);
        instance.setValue(4,37);
        instance.setValue(5,35);
        instance.setValue(6,0.4);
        instance.setValue(7,215);
        instance.setValue(8,10.1);
        instance.setValue(9,300.7);
        instance.setValue(10,190.3);
        instance.setValue(11,7.4);
        instance.setValue(12,7.3);
        instance.setValue(13,181.6);
        instance.setValue(14,91.4);
        instance.setValue(15,49.6);
        instance.setValue(16,39.4);
        instance.setValue(17,6.2);
        instance.setValue(18,145);

        Instances instances = new Instances("repo_popular2", attributes, 0);
        instances.setClassIndex(0);
        instances.add(instance);
        System.out.println("instances.instance(0) = " + instances.instance(0));
        System.out.println("classifier8.classifyInstance(instances.instance(0)) = " + classifier8.classifyInstance(instances.instance(0)));


    }
}
