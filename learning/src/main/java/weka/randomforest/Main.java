package weka.randomforest;


import java.io.File;

import weka.classifiers.Classifier;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.AdditiveRegression;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;

public class Main {

    public static void main(String[] args) throws Exception {

        // 训练语料文件
        File inputFile = new File("output/气压伤预测1.arff");

        Classifier randomForest = new RandomForest();
        ArffLoader atf = new ArffLoader();
        atf.setFile(inputFile);
        // 读入训练文件
        Instances instancesTrain = atf.getDataSet();
        // 测试语料文件
        inputFile = new File("output/气压伤预测2.arff");
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
        SerializationHelper.write("D:/LibSVM.model", randomForest);

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
        Classifier classifier8 = (Classifier) weka.core.SerializationHelper.read("D:/LibSVM.model");
        double right2 = 0.0f;
        // 测试分类结果  2 (通过)
        for (int i = 0; i < sum; i++) {
            // 如果预测值和答案值相等（测试语料中的分类列提供的须为正确答案，结果才有意义）
            if ((classifier8.classifyInstance(instancesTest.instance(i)) > 0.5f ? 1.0f : 0.0f )== instancesTest.instance(i).classValue()) {
                // 正确值加1
                right2++;
            }
        }
        System.out.println(right);
        System.out.println(right2);
        System.out.println(sum);
        System.out.println("RandomForest classification precision:" + (right / sum));
    }
}

