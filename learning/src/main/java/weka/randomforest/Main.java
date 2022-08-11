package weka.randomforest;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import sun.misc.Unsafe;
import weka.classifiers.Classifier;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.AdditiveRegression;
import weka.classifiers.trees.RandomForest;
import weka.core.*;
import weka.core.converters.ArffLoader;

public class Main {

    public static void main(String[] args) throws Exception {

        // 训练语料文件
        File inputFile = new File("output/MIMIC_cluster(1).arff");

        Classifier randomForest = new RandomForest();
        ArffLoader atf = new ArffLoader();
        atf.setFile(inputFile);
        // 读入训练文件
        Instances instancesTrain = atf.getDataSet();
        // 测试语料文件
        inputFile = new File("output/MIMIC_cluster(1).arff");
        atf.setFile(inputFile);
        // 读入测试文件
        Instances instancesTest = atf.getDataSet();
        // 设置分类属性所在行号（第一行为0号），instancesTest.numAttributes()可以取得属性总数
        instancesTest.setClassIndex(0);
        // 测试语料实例数
        double sum = instancesTest.numInstances(), right = 0.0f;
        instancesTrain.setClassIndex(0);
        // 训练
        randomForest.buildClassifier(instancesTrain);
        System.out.println("===" + randomForest);

        // 保存模型
        // 参数一为模型保存文件，classifier4为要保存的模型
        SerializationHelper.write("D:/MIMIC_cluster.model", randomForest);

        // 测试分类结果  1
        for (int i = 0; i < sum; i++) {
            // 如果预测值和答案值相等（测试语料中的分类列提供的须为正确答案，结果才有意义）
            System.out.println("instancesTest.instance(i) = " + instancesTest.instance(i) + "====" + instancesTest.instance(i).classValue()+ "===" + randomForest.classifyInstance(instancesTest.instance(i)));

            if ((randomForest.classifyInstance(instancesTest.instance(i)) > 0.5f ? 1.0f : 0.0f )== instancesTest.instance(i).classValue()) {
                // 正确值加1
                right++;
            }
        }

        // 获取上面保存的模型
        Classifier classifier8 = (Classifier) weka.core.SerializationHelper.read("D:/MIMIC_cluster.model");
        double right2 = 0.0f;
        // 测试分类结果  2 (通过)
        for (int i = 0; i < sum; i++) {
            // 如果预测值和答案值相等（测试语料中的分类列提供的须为正确答案，结果才有意义）
            if ((classifier8.classifyInstance(instancesTest.instance(i)) > 0.5f ? 1.0f : 0.0f )== instancesTest.instance(i).classValue()) {
                // 正确值加1
                right2++;
                System.out.println("=======" + classifier8.classifyInstance(instancesTest.instance(i)));
            }
        }
        System.out.println(right);
        System.out.println(right2);
        System.out.println(sum);
        System.out.println("RandomForest classification precision:" + (right / sum));
    }
    @Test
    public void test2() throws Exception {
        // @relation 气压伤预测
        //
        //@attribute �Ƿ���ѹ�� numeric
        //@attribute ���θ�Ⱦ numeric
        //@attribute �ǵ��Ͳ�ԭ�� numeric
        //@attribute ECMO��θ��� numeric
        //@attribute ���� numeric
        //@attribute fio2b6h numeric
        //@attribute rrD1 numeric
        //@attribute TD1 numeric
        Classifier classifier8 = (Classifier) weka.core.SerializationHelper.read("D:/LibSVM.model");
        Instances instances = generatePopularInstance(null);
        for (int i = 0; i < instances.size(); i++) {
            System.out.println("instancesTest.instance(i) = " + instances.instance(i) + "====" + instances.instance(i).classValue()+ "===" + classifier8.classifyInstance(instances.instance(i)));
        }
    }


    private Instances generatePopularInstance(List<Map> entities) {
        //set attributes
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("�Ƿ���ѹ��"));
        attributes.add(new Attribute("���θ�Ⱦ"));
        attributes.add(new Attribute("�ǵ��Ͳ�ԭ��"));
        attributes.add(new Attribute("ECMO��θ���"));
        attributes.add(new Attribute("����"));
        attributes.add(new Attribute("fio2b6h"));
        attributes.add(new Attribute("rrD1"));
        attributes.add(new Attribute("TD1"));

        Instance instance = new DenseInstance(attributes.size());
        instance.setValue(0, 1.1);
        instance.setValue(1, 2.1);
        instance.setValue(2, 4.1);
        instance.setValue(3, 5.1);
        instance.setValue(4, 6.1);
        instance.setValue(5, 2.1);
        instance.setValue(6, 4.1);
        instance.setValue(7, 5.1);
        //set instances
        Instances instances = new Instances("repo_popular",attributes,0);
        instances.setClassIndex(0);
        instances.add(instance);
        //add instance
//        for (SecRepoEntity secRepoEntity: entities) {
//            Instance instance = new DenseInstance(attributes.size());
//            instance.setValue(0,secRepoEntity.getForkCount());
//            instance.setValue(1,secRepoEntity.getSize());
//            instance.setValue(2,secRepoEntity.getSumFollower());
//            instance.setValue(3,secRepoEntity.getAvgFollower());
//            instance.setValue(4,secRepoEntity.getWeightFollower());
//            instances.add(instance);
//        }

        return instances;
    }


    @Test
    public void test() throws Exception {
        Classifier classifier8 = (Classifier) weka.core.SerializationHelper.read("D:/MIMIC_cluster.model");
        ArrayList<Attribute> attributes = new ArrayList<>();
        Attribute attribute = new Attribute("label");
        attributes.add(attribute);
        attributes.add(new Attribute("apsiii"));
        attributes.add(new Attribute("hemoglobin_min"));
        attributes.add(new Attribute("aniongap_max"));
        attributes.add(new Attribute("bicarbonate_max"));
        attributes.add(new Attribute("bun_max"));
        attributes.add(new Attribute("glucose_max"));
        attributes.add(new Attribute("sodium_max"));
        attributes.add(new Attribute("potassium_max"));
        attributes.add(new Attribute("fio2"));
        Instance instance = new DenseInstance(attributes.size());
        instance.setValue(1, 41);
        instance.setValue(2, 9.8);
        instance.setValue(3, 15);
        instance.setValue(4, 26);
        instance.setValue(5, 19);
        instance.setValue(6, 118);
        instance.setValue(7, 134);
        instance.setValue(8, 3.4);
        instance.setValue(9, 0.5);
        Instances instances = new Instances("repo_popular2", attributes, 0);
        instances.setClassIndex(0);
        instances.add(instance);

        System.out.println("classifier8.classifyInstance(instances.instance(0)) = " + classifier8.classifyInstance(instances.instance(0)));
    }
}

