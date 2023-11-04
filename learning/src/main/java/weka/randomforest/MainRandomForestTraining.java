package weka.randomforest;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;

public class MainRandomForestTraining {

    private static String modelPath = "/Users/lixin08_dxm/Desktop/machine-learning/models/ARDS临床分型.model";

    private static String arffFilePath = "/Users/lixin08_dxm/Desktop/machine-learning/arff/ARDS临床分型.arff";

    public static void main(String[] args) throws Exception {

        // 训练语料文件
        File inputFile = new File(arffFilePath);
        Classifier randomForest = new RandomForest();
        ArffLoader atf = new ArffLoader();
        atf.setFile(inputFile);
        // 读入训练文件
        Instances instancesTrain = atf.getDataSet();
        instancesTrain.setClassIndex(0);
        // 训练
        randomForest.buildClassifier(instancesTrain);

        // 参数一为模型保存文件，classifier4为要保存的模型

        SerializationHelper.write(modelPath, randomForest);

    }

    @Test
    public void test2() throws Exception {
        Classifier classifier8 = (Classifier) weka.core.SerializationHelper.read(modelPath);
        Instances instances = generatePopularInstance();
        for (int i = 0; i < instances.size(); i++) {
            System.out.println(
                    "instancesTest.instance(i) = " + instances.instance(i) + "====" + instances.instance(i).classValue()
                            + "===" + classifier8.classifyInstance(instances.instance(i)));
        }
    }

    private Instances generatePopularInstance() {
        //set attributes
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("Cluster"));
        attributes.add(new Attribute("WBC"));
        attributes.add(new Attribute("Renal_insufficiency"));
        attributes.add(new Attribute("Solid_malignant_tumor"));
        attributes.add(new Attribute("Immunosuppressed_population"));

        // 1,28.85,0,0,0
        Instance instance = new DenseInstance(attributes.size());
        instance.setValue(1, 28.85);
        instance.setValue(2, 0);
        instance.setValue(3, 0);
        instance.setValue(4, 0);
        //set instances
        Instances instances = new Instances("repo_popular", attributes, 0);
        instances.setClassIndex(0);
        instances.add(instance);
        return instances;
    }
}

