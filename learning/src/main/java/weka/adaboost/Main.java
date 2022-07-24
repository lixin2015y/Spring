package weka.adaboost;

import com.sun.deploy.util.StringUtils;
import weka.classifiers.Classifier;
import weka.classifiers.meta.AdaBoostM1;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) {

        try {
            Classifier classifier = AdaBoostM1.forName("weka.classifiers.trees.J48", args);
            ArffLoader atf = new ArffLoader();
            // 读入训练文件
            File inputFile = new File("output/Sepsis_预测-ada.arff");
            atf.setFile(inputFile);
            Instances instancesTrain = atf.getDataSet();
            instancesTrain.setClassIndex(0);
            // 训练
            classifier.buildClassifier(instancesTrain);

            // 读取测试文件
            inputFile = new File("output/Sepsis_预测-ada-test.arff");
            atf.setFile(inputFile);
            Instances instancesTest = atf.getDataSet();
            instancesTest.setClassIndex(0);

            System.out.println("classifier = " + classifier);

            SerializationHelper.write("D:/Sepsis_预测-ada.model", classifier);

            int sum = instancesTest.size();
            System.out.println("测试数据共:" + sum);
            int right = 0;
            DecimalFormat dFormat = new DecimalFormat("#.##");
            for (int i = 0; i < sum; i++) {
                double target = instancesTest.instance(i).classValue();
                double predicate = classifier.classifyInstance(instancesTest.instance(i));
                System.out.println(dFormat.format(target) + "----" + dFormat.format(predicate));

                if (dFormat.format(target).equals(dFormat.format(predicate))) {
                    right++;
                }
            }
            System.out.println(right * 100 / sum + "%");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
