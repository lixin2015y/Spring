package weka.kmeans;



import com.google.common.io.Resources;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @date: 27/11/2017 6:58 PM
 * @author: qinjiangbo@github.io
 * @description:
 *      使用KMeans算法将Iris数据集进行聚类
 */
public class KmeansCluster {

    /**
     * 训练数据集文件地址
     */
    private static final String TRAINING_DATASET_FILENAME = "output/ARDS_Kmeans(1).arff";

    public static void main(String[] args) {
        try {
            // 进行聚类
            process();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载数据集
     * @param fileName 文件路径
     * @return
     */
    public static Instances loadDataSet(String fileName) {
        Instances instances = null;
        URL url = Resources.getResource(fileName);
        File file = new File(url.getPath());
        ArffLoader loader = new ArffLoader();
        try {
            loader.setFile(file);
            instances = loader.getDataSet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instances;
    }

    /**
     * 操作数据集
     * @throws Exception
     */
    public static void process() throws Exception {
        SimpleKMeans kMeans = generateClassifier();
        // 打印聚类结果
        System.out.println(kMeans.preserveInstancesOrderTipText());
        System.out.println(kMeans.toString());
    }

    /**
     * 训练生成分类器
     * @return
     * @throws Exception
     */
    public static SimpleKMeans generateClassifier() throws Exception {
        Instances instances = loadDataSet(TRAINING_DATASET_FILENAME);
        // 初始化聚类器
        SimpleKMeans kMeans = new SimpleKMeans();
        // 设置聚类要得到的簇数
        kMeans.setNumClusters(3);
        // 开始进行聚类
        kMeans.buildClusterer(instances);
        return kMeans;
    }

}
