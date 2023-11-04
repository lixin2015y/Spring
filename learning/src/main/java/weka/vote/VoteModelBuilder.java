package weka.vote;

import java.util.Arrays;

import org.junit.Test;

import weka.classifiers.Classifier;
import weka.classifiers.meta.Vote;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;

public class VoteModelBuilder {

    private static String modelPath = "/Users/lixin08_dxm/Desktop/machine-learning/models/ARDS预后预警.model";

    private static String arffFilePath = "/Users/lixin08_dxm/Desktop/machine-learning/arff/ARDS预后预警.arff";


    @Test
    public void buildModel() throws Exception {
        DataSource source = new DataSource(arffFilePath);
        Instances data = source.getDataSet();

        // 设置类别索引，这是Weka分类器的要求
        data.setClassIndex(0);

        // 创建投票分类器
        Vote vote = new Vote();

        vote.setClassifiers(
                new Classifier[] {new weka.classifiers.trees.RandomForest(),
                        new weka.classifiers.bayes.NaiveBayes(),
                        new weka.classifiers.functions.SMO()
                });

        // 训练模型
        vote.buildClassifier(data);
        SerializationHelper.write(modelPath, vote);
    }
}
