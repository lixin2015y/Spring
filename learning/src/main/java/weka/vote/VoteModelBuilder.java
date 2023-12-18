package weka.vote;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.Vote;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;
import weka.core.converters.ConverterUtils.DataSource;

public class VoteModelBuilder {

    private static String modelPath = "/Users/lixin08_dxm/IdeaProjects/Spring/ARDS预后预警.model";
    private static String arffFilePath = "/Users/lixin08_dxm/IdeaProjects/Spring/ARDS预后预警.arff";

    private static String arffFilePathTest = "/Users/lixin08_dxm/IdeaProjects/Spring/ARDS预后预警-test.arff";

    @Test
    public void buildModel() throws Exception {
        DataSource source = new DataSource(arffFilePath);
        Instances data = source.getDataSet();

        // 设置类别索引，这是Weka分类器的要求
        data.setClassIndex(0);

        // 创建投票分类器
        Vote vote = new Vote();

        vote.setClassifiers(
                new Classifier[] {new weka.classifiers.trees.RandomForest(), new weka.classifiers.bayes.NaiveBayes()
                        //                        , new weka.classifiers.functions.SMO()
                });

        // 训练模型
        vote.buildClassifier(data);
        SerializationHelper.write(modelPath, vote);
    }

    @Test
    public void test() throws Exception {
        Vote classifier8 = (Vote) weka.core.SerializationHelper.read(modelPath);
        File inputFile = new File(arffFilePath);
        ArffLoader atf = new ArffLoader();
        atf.setFile(inputFile);
        // 读入训练文件
        Instances instances = atf.getDataSet();
        instances.setClassIndex(0);
        int successCount = 0;
        for (int i = 0; i < instances.size(); i++) {
            double d = classifier8.classifyInstance(instances.instance(i));
            if (d == instances.get(i).classValue()) {
                successCount++;
            }
            System.out.println(d + "===" + instances.get(i).classValue());
        }
        System.out.println(successCount + "====" + instances.size());

    }

    @Test
    public void test2() throws Exception {
        Vote classifier8 = (Vote) weka.core.SerializationHelper.read(modelPath);
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("Cluster", Arrays.asList("0", "1")));
        attributes.add(new Attribute("Age", Arrays.asList("<57", ">=57")));
        attributes.add(new Attribute("WBC", Arrays.asList("<2.5", "2.5-18", ">18")));
        attributes.add(new Attribute("Neu", Arrays.asList("<92", ">=92")));
        attributes.add(new Attribute("LYM", Arrays.asList("<=9", ">9")));
        attributes.add(new Attribute("BUN", Arrays.asList("<12", ">=12")));
        attributes.add(new Attribute("PT", Arrays.asList("<16", ">=16")));
        attributes.add(new Attribute("PCO2", Arrays.asList("<27", "27-46", ">46")));
        attributes.add(new Attribute("PFR", Arrays.asList("<=150", "＞150")));
        attributes.add(new Attribute("Lac", Arrays.asList("<2.8", ">=2.8")));
        attributes.add(new Attribute("Fluid_balance", Arrays.asList("<2000", ">=2000")));
        Instances instances = new Instances("repo_popular", attributes, 0);
        instances.setClassIndex(0);
        DenseInstance instance = new DenseInstance(attributes.size());
        instance.setDataset(instances);
        instance.setValue(1, "<57");
        instance.setValue(2, "2.5-18");
        instance.setValue(3, "<92");
        instance.setValue(4, ">9");
        instance.setValue(5, "<12");
        instance.setValue(6, ">=16");
        instance.setValue(7, "27-46");
        instance.setValue(8, "＞150");
        instance.setValue(9, "<2.8");
        instance.setValue(10, ">=2000");
        double v = classifier8.classifyInstance(instance);
        System.out.println(v);

    }

    @Test
    public void buildData() {
        List<List<String>> list = new ArrayList<>();
        extracted(list);
        for (int i = 0; i < list.size(); i++) {
            List<String> ele = list.get(i);
            if (ele.size() != 11) {
                System.out.print(ele);
                throw new IllegalArgumentException("-----");
            }
            Integer Cluster = Integer.parseInt(ele.get(0));
            Double Age = Double.parseDouble(ele.get(1));
            Double WBC = Double.parseDouble(ele.get(2));
            Double Neu = Double.parseDouble(ele.get(3));
            Double LYM = Double.parseDouble(ele.get(4));
            Double BUN = Double.parseDouble(ele.get(5));
            Double PT = Double.parseDouble(ele.get(6));
            Double PCO2 = Double.parseDouble(ele.get(7));
            Double PFR = Double.parseDouble(ele.get(8));
            Double Lac = Double.parseDouble(ele.get(9));
            Double Fluid_balance = Double.parseDouble(ele.get(10));

            System.out.print(Cluster + ",");
            if (Age < 57) {
                System.out.print("<57,");
            } else {
                System.out.print(">=57,");
            }

            if (WBC < 2.5) {
                System.out.print("<2.5,");
            } else if (WBC >= 2.5 && WBC <= 18) {
                System.out.print("2.5-18,");
            } else {
                System.out.print(">18,");
            }

            if (Neu < 92) {
                System.out.print("<92,");
            } else {
                System.out.print(">=92,");
            }

            if (LYM <= 9) {
                System.out.print("<=9,");
            } else {
                System.out.print(">9,");
            }

            if (BUN < 12) {
                System.out.print("<12,");
            } else {
                System.out.print(">=12,");
            }

            if (PT < 16) {
                System.out.print("<16,");
            } else {
                System.out.print(">=16,");
            }

            if (PCO2 < 27) {
                System.out.print("<27,");
            } else if (PCO2 >= 27 && PCO2 <= 46) {
                System.out.print("27-46,");
            } else {
                System.out.print(">46,");
            }

            if (PFR <= 150) {
                System.out.print("<=150,");
            } else {
                System.out.print("＞150,");
            }

            if (Lac < 2.8d) {
                System.out.print("<2.8,");
            } else {
                System.out.print(">=2.8,");
            }

            if (Fluid_balance < 2000) {
                System.out.print("<2000");
            } else {
                System.out.print(">=2000");
            }
            System.out.print("\n");
        }
    }

    private static void extracted(List<List<String>> list) {
        list.add(Arrays.asList("1,57,14.9,94.1,2,4.4,14.2,37.6,145.7,1.5,5976".split(",")));
        list.add(Arrays.asList("1,71,13.5,88.2,5,6.4,13.9,46,127.2,2.6,4602".split(",")));
        list.add(Arrays.asList("1,67,9.4,87.3,3.2,10.6,14.5,55.8,157.2,2.1,28617".split(",")));
        list.add(Arrays.asList("0,34,6.1,81.3,10.7,10.3,17.8,37.5,176.7,1.6,5672".split(",")));
        list.add(Arrays.asList("0,31,16.4,87,8.3,3.4,15.1,31.9,203.7,1,7716".split(",")));
        list.add(Arrays.asList("1,80,7.6,94.3,3.5,12.1,17.9,23.6,85.5,5.1,3913".split(",")));
        list.add(Arrays.asList("0,22,22.8,83.7,8.5,10.6,13.6,47.3,213,1.3,1511".split(",")));
        list.add(Arrays.asList("1,52,20.2,88.6,4.4,14,14,102.1,69.8,2.3,3851".split(",")));
        list.add(Arrays.asList("0,45,11,79,12.2,5.6,15.8,33.9,234.6,1.1,-1463".split(",")));
        list.add(Arrays.asList("0,45,20.5,87.4,7.7,8.8,13.4,45.7,280.3,2.6,3151".split(",")));
        list.add(Arrays.asList("0,48,10.3,85.2,6.6,5.1,15.2,31.1,161.9,1.1,8455".split(",")));
        list.add(Arrays.asList("0,57,7.2,93.8,4.6,8.9,17.3,39.1,87.8,1.8,-347".split(",")));
        list.add(Arrays.asList("1,56,34.4,94.2,2,8.9,15.8,64.5,105.7,2.2,14148".split(",")));
        list.add(Arrays.asList("1,68,9.1,89.7,6,5.4,13.7,41,97,1.1,6611".split(",")));
        list.add(Arrays.asList("0,47,6.6,90.8,5.9,4.7,13.1,47,71.4,1.7,649".split(",")));
        list.add(Arrays.asList("1,85,14.9,85.3,8.7,32.3,29.9,35.1,299.6,4,6076".split(",")));
        list.add(Arrays.asList("0,91,10.5,82.9,11.3,20.4,15.1,37.9,128.9,1.2,-1098".split(",")));
        list.add(Arrays.asList("1,64,9.1,88.5,6.7,3.2,16.6,33.2,119.7,1.8,3034".split(",")));
        list.add(Arrays.asList("1,65,4.2,91.8,5.9,24.4,14,33.5,53.2,2.1,7498".split(",")));
        list.add(Arrays.asList("1,51,19.5,92.3,5.1,4.2,12.8,34.2,82.6,1.4,3204".split(",")));
        list.add(Arrays.asList("1,80,10.2,88.3,9.5,17.2,14.8,44.6,84.7,2.5,11824".split(",")));
        list.add(Arrays.asList("0,81,15.1,92.7,2.8,12.6,19.7,31.4,258,3.1,-1700".split(",")));
        list.add(Arrays.asList("1,73,92.3,60.3,8.7,9.4,19.5,35.7,122.2,1.8,-61".split(",")));
        list.add(Arrays.asList("0,48,13.5,88.2,2.8,17.8,16,23.8,113.3,0.7,-11127".split(",")));
        list.add(Arrays.asList("0,52,14.5,92.8,4.5,5.8,14.1,36.7,168.5,1.3,2002".split(",")));
        list.add(Arrays.asList("0,47,12.5,94.4,4.6,10.8,16.8,32,146,1.2,-350".split(",")));
        list.add(Arrays.asList("0,52,10.6,77.1,15.8,8.8,15.3,30.4,136,2.1,1170".split(",")));
        list.add(Arrays.asList("1,45,8,93.9,3,18.4,17.6,31.6,369.3,1.2,651".split(",")));
        list.add(Arrays.asList("1,67,78,68.5,15.1,15.7,33.7,50.6,106.5,6.1,5061".split(",")));
        list.add(Arrays.asList("0,79,16.1,92.3,4.8,6.9,23.3,38.2,219.3,1.9,-3464".split(",")));
        list.add(Arrays.asList("1,79,11.9,96.4,2.4,34.9,13.3,36.5,99.2,2,2861".split(",")));
        list.add(Arrays.asList("0,31,4.8,93.7,5.3,10.2,15.1,36.3,99,1.4,-5665".split(",")));
        list.add(Arrays.asList("0,74,9.3,89.9,6.7,8.5,13,32.6,133.3,1.5,-4435".split(",")));
        list.add(Arrays.asList("0,88,9.4,88.3,6.8,11.2,17.7,50.6,223.7,2.2,1042".split(",")));
        list.add(Arrays.asList("1,46,5.9,84.1,12.6,5.2,13.2,30.2,148.7,1.8,6202".split(",")));
        list.add(Arrays.asList("1,75,37.5,91.7,4.4,11.2,15.6,57.9,104.8,1.6,3107".split(",")));
        list.add(Arrays.asList("1,81,33,87.1,5.2,20.8,25.8,37.8,169.7,2.7,-22762".split(",")));
        list.add(Arrays.asList("0,62,7.9,78.5,15.1,8.4,14.4,40.6,93.9,1.6,-743".split(",")));
        list.add(Arrays.asList("1,75,1.8,55.9,40,9.2,20.4,55.3,64.9,16.3,13777".split(",")));
        list.add(Arrays.asList("0,40,8.4,86.1,4.4,5.4,16,29.1,217.7,2,-5517".split(",")));
        list.add(Arrays.asList("0,44,16.5,86.4,9.2,9.5,19.9,41.6,123.3,3,-10778".split(",")));
        list.add(Arrays.asList("0,62,10.8,78.1,13.6,9.2,13.8,29.7,186.7,0.6,-8483".split(",")));
        list.add(Arrays.asList("0,81,9,92.8,5.6,2.6,15.2,30.2,78.4,1.9,1402".split(",")));
        list.add(Arrays.asList("1,81,9.5,90.1,4.9,22.5,12.7,43.6,126.7,1.8,10029".split(",")));
        list.add(Arrays.asList("0,76,8.8,93.3,3.9,8.8,14.2,29.7,139.9,1.5,-5246".split(",")));
        list.add(Arrays.asList("0,28,7.7,93.2,4.5,3.5,13.6,34.3,204.7,1.8,-1553".split(",")));
        list.add(Arrays.asList("1,48,11.6,87.4,7.6,6.2,16.4,41.3,137.2,2.7,1947".split(",")));
        list.add(Arrays.asList("0,63,8.4,83.1,13.6,3.7,13.2,36.7,110.8,2.9,1739".split(",")));
        list.add(Arrays.asList("1,72,18.2,80.8,13.5,1.7,19.9,64.8,88.8,1.9,-10091".split(",")));
        list.add(Arrays.asList("0,45,18.9,88.3,4.7,7.3,15.2,40.5,91,1.6,753".split(",")));
        list.add(Arrays.asList("1,63,15.3,84.7,6,5.7,14.8,50.4,131.7,1.6,497".split(",")));
        list.add(Arrays.asList("0,26,15.5,86.8,9.7,5.7,14.9,49.1,121.5,1.6,-302".split(",")));
        list.add(Arrays.asList("1,42,20.5,94.8,2.6,12.5,14.3,34.5,167,1.4,1253".split(",")));
        list.add(Arrays.asList("0,47,5.9,79.6,15.9,4.5,64.5,27.9,130.3,1.1,-649".split(",")));
        list.add(Arrays.asList("1,53,7.7,84.5,8.3,9.4,13.7,51.5,77.4,1.1,1451".split(",")));
        list.add(Arrays.asList("1,55,18.6,93.4,2.7,3.8,19,49.6,132.9,10.2,486".split(",")));
        list.add(Arrays.asList("1,49,9.9,86.2,10.8,5,15.5,48.5,60.2,1.4,2870".split(",")));
        list.add(Arrays.asList("1,38,9.1,92.7,5.4,21.8,14.4,34.7,116.7,2,-1667".split(",")));
        list.add(Arrays.asList("1,62,23.8,95.3,3.1,10.6,14.9,40.7,90.8,3.4,1638".split(",")));
        list.add(Arrays.asList("1,58,26.9,92.1,3.8,6.4,13.5,43,84.3,2.2,15478".split(",")));
        list.add(Arrays.asList("1,64,7.4,93.6,4.9,22.4,22.4,52,104,9.9,14470".split(",")));
        list.add(Arrays.asList("0,58,4.1,79.6,13.9,4,14.1,25.8,85.6,1.1,1746".split(",")));
        list.add(Arrays.asList("1,34,22.3,58.8,3.6,12.6,13.6,36.4,111.3,1.6,4696".split(",")));
        list.add(Arrays.asList("0,56,7,83.7,8.8,8.9,15.8,40,119.3,1.2,-1361".split(",")));
        list.add(Arrays.asList("1,63,7.7,87,6.1,4.8,15,42.8,60.7,1.9,2268".split(",")));
        list.add(Arrays.asList("0,27,9.7,84.8,10.4,6.7,16.2,55.3,228.3,0.8,1310".split(",")));
        list.add(Arrays.asList("0,80,16.3,89,6.4,8.2,14.7,52.7,75.4,1.9,7816".split(",")));
        list.add(Arrays.asList("1,63,24.6,92.8,3.2,7.7,15.1,62.7,60,1.5,1135".split(",")));
        list.add(Arrays.asList("0,56,8.1,80.1,15.3,9.9,15.6,43.6,327.3,0.9,-7503".split(",")));
        list.add(Arrays.asList("0,60,15.6,91.1,4.2,3.5,17.1,32.6,122,1.3,-1560".split(",")));
        list.add(Arrays.asList("0,49,6.6,92.6,3.5,28.7,16.2,32.2,115.7,1,56".split(",")));
        list.add(Arrays.asList("1,66,16.5,95,4.2,8.5,16,51,82.8,1.2,-5216".split(",")));
        list.add(Arrays.asList("0,17,3.1,87.4,11.3,5.7,14.5,30.5,154,1,-2465".split(",")));
        list.add(Arrays.asList("0,31,5.7,85.3,4.5,4.7,14.5,49.2,56,1.4,-7457".split(",")));
        list.add(Arrays.asList("0,21,17,90.8,3.9,14.4,19.8,29.2,218.3,1.8,-6868".split(",")));
        list.add(Arrays.asList("0,55,25.1,92.7,3.1,12.3,51.2,27.1,228.3,1.1,3473".split(",")));
        list.add(Arrays.asList("1,77,7.6,94.3,4.9,5.2,15.9,36.3,56.8,2.7,5765".split(",")));
        list.add(Arrays.asList("0,64,21.2,91,4.2,4.1,17.9,31.2,147,1.3,-4202".split(",")));
        list.add(Arrays.asList("0,28,7.7,91.1,7.9,2,15.4,30.4,110.3,0.9,-6134".split(",")));
        list.add(Arrays.asList("0,32,27.9,88.3,5.5,2.7,14.5,34.6,90.4,1.6,5824".split(",")));
        list.add(Arrays.asList("1,37,6.4,95.8,2.5,6.6,15.8,43.5,93.3,2,1884".split(",")));
        list.add(Arrays.asList("1,75,17.3,97.2,1.8,28.3,17,24.9,86.7,1.6,3046".split(",")));
        list.add(Arrays.asList("0,76,11,82.8,13.3,4.1,14.2,29.2,138.7,1.7,-3878".split(",")));
        list.add(Arrays.asList("0,48,6,91.6,4.8,3.8,14.2,29.5,205,0.9,-4506".split(",")));
        list.add(Arrays.asList("0,51,9.6,88.1,3.7,28.4,13.7,43.1,160.7,1.7,-2953".split(",")));
        list.add(Arrays.asList("0,84,11.5,88.7,8.2,5.3,14.1,37.3,138.3,1.7,-731".split(",")));
        list.add(Arrays.asList("0,63,4.7,91.1,8,5,15.5,27.5,155,1.5,-4514".split(",")));
        list.add(Arrays.asList("1,79,8.1,94.8,3,32.5,14.6,21.6,142,5.3,8776".split(",")));
        list.add(Arrays.asList("1,26,0.6,63.5,26.6,19.5,18.1,45.6,62.2,3,2969".split(",")));
        list.add(Arrays.asList("0,69,8.1,95.5,4.3,10,16.1,33,212,1,935".split(",")));
        list.add(Arrays.asList("0,73,15.2,91.7,4,5.9,15.5,34.8,96,1.6,-3111".split(",")));
        list.add(Arrays.asList("0,56,6.7,91.6,4,9.8,15.3,38.2,231.7,1.4,3129".split(",")));
        list.add(Arrays.asList("1,54,12.6,71.9,16.5,7.5,14.5,65.3,127,1,15206".split(",")));
        list.add(Arrays.asList("0,59,10.4,95.1,3.8,3.8,15.9,40.2,152.3,1.1,-3646".split(",")));
        list.add(Arrays.asList("0,41,6.9,83.9,11.6,11,14.3,42.9,267,2,-5182".split(",")));
        list.add(Arrays.asList("0,52,14.8,77.1,11.1,4.7,15.3,43.8,153.3,0.9,880".split(",")));
        list.add(Arrays.asList("1,18,3.6,80.5,17.7,7.9,15.4,40.4,111.4,2.3,10763".split(",")));
        list.add(Arrays.asList("1,62,0.5,53.7,38.3,7.8,22.3,49.2,78.8,3.1,2155".split(",")));
        list.add(Arrays.asList("0,19,20.4,90.6,4.5,6.5,18.9,54.5,83.6,1.6,-3396".split(",")));
        list.add(Arrays.asList("1,82,18.3,95,2.8,11.2,15.6,37.3,70.3,2.5,6476".split(",")));
        list.add(Arrays.asList("1,35,14.1,96,2.6,2.1,16.2,39,93.5,3.4,1903".split(",")));
        list.add(Arrays.asList("0,37,6.1,90,7.7,7.8,17.1,31.8,127.9,1.5,1187".split(",")));
        list.add(Arrays.asList("1,49,11.2,94.3,2.4,6.6,16.8,27.5,88.7,1.8,1000".split(",")));
        list.add(Arrays.asList("0,60,14.3,86.6,8.6,10.6,13.9,43.1,101.7,1.8,-4056".split(",")));
        list.add(Arrays.asList("0,46,3.8,82.3,14.6,8.6,14.8,35.2,188.3,1.3,-773".split(",")));
        list.add(Arrays.asList("0,36,3.4,51,35.4,2.6,12.1,37.7,265.3,1,-101".split(",")));
        list.add(Arrays.asList("1,15,10.5,90.6,5.7,6.8,16,43.5,261,2.8,7371".split(",")));
        list.add(Arrays.asList("0,51,16.4,87.2,9.1,19.8,14.4,38.5,108.3,1.3,3932".split(",")));
        list.add(Arrays.asList("1,50,16.9,89.7,7.7,18.3,15.3,32.6,156,2.5,-199".split(",")));
        list.add(Arrays.asList("1,31,7.9,87,8.4,6.5,14.4,41,90.7,2.1,5469".split(",")));
        list.add(Arrays.asList("0,38,9.3,83.3,10.5,4.9,12.4,39.8,97,2,33".split(",")));
        list.add(Arrays.asList("0,42,2.3,87.5,10.3,4.2,16.7,32.6,111.3,5.7,-1563".split(",")));
        list.add(Arrays.asList("0,51,10.5,90,7.9,7.1,14.3,40,114.7,1.7,9032".split(",")));
        list.add(Arrays.asList("0,54,6.1,77.2,15.2,10.3,15.1,28,169,1.6,3910".split(",")));
        list.add(Arrays.asList("1,48,9.2,80.1,14,4.7,13.1,39.5,112,1.9,4741".split(",")));
        list.add(Arrays.asList("1,44,7.2,84.6,11.2,9.6,13.1,31.3,235.3,2.1,1470".split(",")));
        list.add(Arrays.asList("0,62,5.1,76,18.4,5,14.3,36,113,1.3,-1658".split(",")));
        list.add(Arrays.asList("1,66,5.5,81.8,11.5,31.7,13.9,33.1,147.7,1,946".split(",")));
        list.add(Arrays.asList("1,46,26.3,95,3.5,6.5,16.5,46.8,98,2,-1023".split(",")));
        list.add(Arrays.asList("0,35,13.6,86.3,9.7,5.4,14.4,46.3,228.7,0.9,2011".split(",")));
        list.add(Arrays.asList("1,47,9.7,95.4,3.9,7.6,15.8,52,91,1.8,1474".split(",")));
        list.add(Arrays.asList("1,49,14,84.8,9.9,16.6,15,39.3,87.8,1.7,4922".split(",")));
        list.add(Arrays.asList("1,16,33.7,92.2,5,10.3,15.1,47.7,118.3,1.8,11629".split(",")));
        list.add(Arrays.asList("1,45,18.4,94.4,2.5,5.2,16.1,46.1,100,2.1,3898".split(",")));
        list.add(Arrays.asList("0,60,4.6,73.8,13.2,7.5,15.2,36,143,1.9,-6930".split(",")));
        list.add(Arrays.asList("1,65,10.3,83.9,10.1,7.1,15.7,43.7,140,1.2,5108".split(",")));
        list.add(Arrays.asList("0,46,18.8,91.1,6.3,5.1,17.1,42.8,180.3,1.2,2068".split(",")));
        list.add(Arrays.asList("0,63,6.6,76.5,15.1,4.5,14.6,39.8,102.3,0.8,-4434".split(",")));
        list.add(Arrays.asList("0,56.2,4.5,86.2,11.6,9,14.9,28.3,105.3,1,-8921".split(",")));
        list.add(Arrays.asList("0,41,7.7,88.8,6.1,12.3,14.7,33.1,143,1.4,-11985".split(",")));
        list.add(Arrays.asList("1,81,12.5,89.4,7.1,7.3,14.8,41.1,58.3,2,-4496".split(",")));
        list.add(Arrays.asList("0,52,9.8,84.7,11.4,8.7,17.1,43.3,185.7,1.6,-337".split(",")));
        list.add(Arrays.asList("1,75,55.3,99,1,6.3,22.2,36.6,150.3,1.4,6801".split(",")));
        list.add(Arrays.asList("0,51,16.6,91.3,4.1,23.3,17.3,41.6,204,1.7,-10654".split(",")));
        list.add(Arrays.asList("0,41,10.9,85.3,6.6,2.3,15.5,28.2,269,1.5,-641".split(",")));
        list.add(Arrays.asList("0,35,5.5,73.6,19.9,2.4,13.4,34.9,203.7,0.9,3035".split(",")));
        list.add(Arrays.asList("1,55,10.3,90.4,7.6,4.9,16,40.8,106.7,1.7,3402".split(",")));
        list.add(Arrays.asList("0,56,6.5,90.6,6.6,17.1,14,29.9,264.3,1.7,-127".split(",")));
        list.add(Arrays.asList("1,44,13,90.9,5.4,7.7,15.9,34.5,102.7,1.4,7625".split(",")));
        list.add(Arrays.asList("1,61,10.9,94.6,2.9,5.7,15.4,44.6,74.9,2.2,5092".split(",")));
        list.add(Arrays.asList("1,50,15.1,93.3,4,13.8,14.8,42.2,68.3,1.7,990".split(",")));
        list.add(Arrays.asList("1,47,6.7,88.3,8.4,9,13,39.9,73.3,1.6,-6165".split(",")));
        list.add(Arrays.asList("1,66,26,93.2,2.8,47.1,14.5,32.9,246.3,2.1,1072".split(",")));
        list.add(Arrays.asList("0,66,19,94.8,2.9,10.7,16.6,43.5,252.3,2.4,-5788".split(",")));
        list.add(Arrays.asList("0,65,12.5,93.7,4.3,8.5,15,40.9,183.7,1.9,-9639".split(",")));
        list.add(Arrays.asList("1,87,8,85.9,11.7,14.6,15.7,57,210.3,2.9,4995".split(",")));
        list.add(Arrays.asList("1,30,11,83.5,12.3,19.6,13.1,69.4,85.3,1.9,-1231".split(",")));
        list.add(Arrays.asList("0,70,12.8,93.6,4.7,20.8,17.7,53.8,179,2.1,-4540".split(",")));
        list.add(Arrays.asList("1,36,12.8,87.6,10.5,6.7,16.9,46.6,214.7,1.3,11657".split(",")));
        list.add(Arrays.asList("0,53,4.8,71.5,21,5.6,11.8,35.2,193,1.1,-985".split(",")));
        list.add(Arrays.asList("1,83,8.4,82.7,12.2,6.4,12.4,29.8,185,1.7,2837".split(",")));
        list.add(Arrays.asList("1,60,8.1,96.2,2.4,14.6,13.9,45.1,126.7,1.5,286".split(",")));
        list.add(Arrays.asList("0,40,24.2,88.6,10,10.3,14,59.2,63.3,2.7,-19519".split(",")));
        list.add(Arrays.asList("0,35,7,79.3,12.8,6.9,13.7,38.2,141,2.1,2113".split(",")));
        list.add(Arrays.asList("1,53,16.5,93.3,3,6,21.9,42.4,268,2.4,4936".split(",")));
        list.add(Arrays.asList("1,47,10.3,95.1,3.9,10.1,15.2,39.3,122.5,4,2235".split(",")));
        list.add(Arrays.asList("1,47,15.1,92.6,4.5,25,29.1,37.3,207,2.5,25458".split(",")));
        list.add(Arrays.asList("0,46,7.4,84.5,11.2,8.2,12.6,36.6,135.7,1.4,3594".split(",")));
        list.add(Arrays.asList("1,52,15.1,96.1,1.7,17.9,16.6,39.1,233.3,1.8,11932".split(",")));
        list.add(Arrays.asList("0,76,4.8,90.1,8.4,8.5,13.3,35.5,69,2.3,6417".split(",")));
        list.add(Arrays.asList("1,61,18.3,82.9,3.8,28,18.3,38.4,98,1.1,8962".split(",")));
        list.add(Arrays.asList("1,42,6.4,96,2.3,7,14.6,43.4,107.1,2.1,-1746".split(",")));
        list.add(Arrays.asList("0,53,4.6,93.1,5.3,10.6,14.9,38.2,112.4,3.2,6570".split(",")));
        list.add(Arrays.asList("0,32,5.7,92.9,5.7,8.6,16.9,38,138.4,1.7,11425".split(",")));
        list.add(Arrays.asList("0,29,8.9,81.7,13.3,3.5,14.5,42.2,187.7,1.5,754".split(",")));
        list.add(Arrays.asList("1,66,12,97.1,1.4,6.9,17.6,37.7,168,4.1,17252".split(",")));
        list.add(Arrays.asList("1,62,10.7,94.9,2.7,7.1,14.6,40.6,135.3,2.4,14849".split(",")));
        list.add(Arrays.asList("0,47,13.1,90.2,5.7,8.7,16,35.8,91.6,1.7,14054".split(",")));
        list.add(Arrays.asList("1,79,9.7,92.2,5.7,24.5,14.4,61.7,107.7,2.1,11409".split(",")));
        list.add(Arrays.asList("0,71,7,91.2,6.7,55.9,12.1,30,98.3,2.1,-9795".split(",")));
        list.add(Arrays.asList("0,60,13.1,82.5,13.1,8.8,14.6,33,144,1.9,1862".split(",")));
        list.add(Arrays.asList("0,65,7.4,93.7,4.4,10.7,14.4,22,117,8.1,-60".split(",")));
        list.add(Arrays.asList("1,65,7.7,95.2,3.5,16.6,14.8,35,154,0.8,1288".split(",")));
        list.add(Arrays.asList("1,56,10.2,87.5,5.2,5.7,13,42.7,48.3,2.3,5860".split(",")));
        list.add(Arrays.asList("1,75,8.9,94.6,3.5,13.2,11.9,49.7,61.8,2.1,10857".split(",")));
        list.add(Arrays.asList("0,58,16.2,93.1,3.9,14,15.7,46.7,135.5,2.4,825.8".split(",")));
        list.add(Arrays.asList("1,71,19.5,67,2,11.7,15.4,77.7,66.3,1.9,961.5".split(",")));
        list.add(Arrays.asList("1,22,5.4,70.3,25.9,8.8,16.9,42.3,75,3.2,7254".split(",")));
        list.add(Arrays.asList("1,61,12.8,86.9,7.8,7.6,16.1,60.7,51,1.3,6882.5".split(",")));
        list.add(Arrays.asList("1,57,13.3,88.5,6.2,12.3,14.1,42.7,58.3,1.9,5935".split(",")));
        list.add(Arrays.asList("1,74,8.8,91.7,5.9,8.7,15,39.7,94.4,4.1,2756".split(",")));
        list.add(Arrays.asList("1,60,12.6,92.2,5.2,6.6,13.6,38.3,61.9,2,7668.2".split(",")));
        list.add(Arrays.asList("1,67,12.6,95,3.6,11.4,14.5,38.3,89.6,0.9,-2491.3".split(",")));
        list.add(Arrays.asList("1,78,8.5,88.5,6.4,19.5,18.7,43.1,67.4,4.3,-50".split(",")));
        list.add(Arrays.asList("0,59,8.4,87.6,9.6,5.4,13,44.4,114.7,2.5,4171".split(",")));
        list.add(Arrays.asList("0,87,10,89.9,7.9,6.1,15.4,48.8,178.5,2.1,-76".split(",")));
        list.add(Arrays.asList("0,81,9.1,88,8.4,3.3,16.1,28.9,134.9,2,-7129".split(",")));
        list.add(Arrays.asList("0,55,12.8,78.4,17,5.2,13.7,45.4,137.3,4,-1342".split(",")));
        list.add(Arrays.asList("0,68,7.5,84.3,13.3,5.5,15.9,29.9,144.2,2.9,-238".split(",")));
        list.add(Arrays.asList("0,43,9.2,75.5,19.8,5.6,17.5,34.9,161.6,1.4,-885.2".split(",")));
        list.add(Arrays.asList("0,36,9.9,78,15.9,5.1,13.5,34.3,44.6,2.1,4152".split(",")));
        list.add(Arrays.asList("1,75,16.5,91.4,6.8,20.9,16.6,25.7,162.5,5.1,13607".split(",")));
        list.add(Arrays.asList("1,25,6.2,80.8,18,10,14.3,36.1,106.3,1.9,5220".split(",")));
        list.add(Arrays.asList("0,84,21.7,82.3,11.6,10.3,15.1,45.6,190,4.9,-180".split(",")));
        list.add(Arrays.asList("0,71,8.3,84.5,10.8,7.4,11.7,34.7,124.9,3.1,-1618".split(",")));
        list.add(Arrays.asList("0,35,26.6,76.4,20.6,6.7,11,43,65.3,3.3,351".split(",")));
        list.add(Arrays.asList("0,54,11.8,98.9,3.8,7.7,12.2,37.2,93.7,2.7,609".split(",")));
        list.add(Arrays.asList("0,74,7.5,55.9,36.2,5.2,12,42.8,115,1.9,-5904".split(",")));
        list.add(Arrays.asList("1,49,1.6,72.8,22,6.4,16.8,34.1,167.5,5.4,-8900".split(",")));
        list.add(Arrays.asList("0,65,5.3,82.3,12.5,12.5,18.2,35.9,96.5,3.3,9257".split(",")));
        list.add(Arrays.asList("0,78,13.5,85.3,8,6.2,12.6,30.4,131,2.4,-344".split(",")));
        list.add(Arrays.asList("0,59,6.2,79.3,13.4,2.8,15.1,36.4,241.7,1.6,-441".split(",")));
        list.add(Arrays.asList("1,81,8.2,86.2,11.8,13.3,11.8,32.1,77.7,3.5,2619".split(",")));
        list.add(Arrays.asList("1,69,15.3,59.1,7.9,5,12.2,34.5,250.7,2.6,5243".split(",")));
        list.add(Arrays.asList("0,44,9.7,80.5,12.9,9.3,9.6,31.4,149,2.3,1375".split(",")));
        list.add(Arrays.asList("0,61,7.9,94,3.2,4.5,12.4,27,133.2,1.6,-2135".split(",")));
        list.add(Arrays.asList("1,18,9.3,92.5,4.6,4.4,10.4,28.6,128.2,1.1,8930".split(",")));
        list.add(Arrays.asList("0,27,12,97.4,1.6,4.5,11,29.8,137.9,1.5,-7240".split(",")));
        list.add(Arrays.asList("0,59,2.3,71.9,13.7,6.4,11.8,28.8,118.2,1,3440".split(",")));
        list.add(Arrays.asList("1,56,2.6,93.6,4.1,11.7,12.8,25.2,223.1,1.5,4410".split(",")));
        list.add(Arrays.asList("1,42,14.5,94.6,10.4,9.1,22.7,20.8,130.8,4.8,11475".split(",")));
        list.add(Arrays.asList("0,28,14.9,81.3,8.4,4.7,12.7,33.6,97.3,0.8,-1454".split(",")));
        list.add(Arrays.asList("0,71,5.8,90.8,6.2,13.9,13.8,34,225,4.6,-435".split(",")));
        list.add(Arrays.asList("0,31,23.5,88.8,7.8,7,11.4,38.1,50.9,7,10096".split(",")));
        list.add(Arrays.asList("0,50,15.1,92.2,3.7,10.3,13.4,41.2,149.5,2.8,2620".split(",")));
        list.add(Arrays.asList("1,28,18.8,74.9,15.2,3.2,16.3,34.9,171.3,2.3,5180".split(",")));
        list.add(Arrays.asList("0,70,10.3,90.9,5.2,11.1,13.7,30.4,137.8,2.5,3340".split(",")));
        list.add(Arrays.asList("0,25,19.4,84.5,9.3,6.5,12.2,30.8,168.1,2.3,-1790".split(",")));
        list.add(Arrays.asList("1,63,16,89.9,5,19.2,12,28.9,73.7,4.3,-7360".split(",")));
        list.add(Arrays.asList("0,68,10.7,85.5,8,9.6,16.2,28.1,95.9,1.6,-1920".split(",")));
        list.add(Arrays.asList("1,70,11.8,94.4,4.8,9.2,13,36.6,121.2,1.8,-3755".split(",")));
        list.add(Arrays.asList("0,62,3.9,80.8,7.9,5.9,11.7,32.7,66.7,2,-345".split(",")));
        list.add(Arrays.asList("1,84,27.7,92,4.3,7.6,14.1,41.2,58.4,2.5,5830".split(",")));
        list.add(Arrays.asList("1,41,8.6,87.7,5.7,73.3,13.1,26.2,96.1,4,5730".split(",")));
        list.add(Arrays.asList("0,57,6.7,90.7,5.3,5.5,10.3,62.3,77.4,1.9,-2280".split(",")));
        list.add(Arrays.asList("1,44,16.4,89.7,5.3,4,11.8,47.1,142.5,1.2,-390".split(",")));
        list.add(Arrays.asList("0,17,5.8,87.3,9.4,4.9,15.6,28,193.6,2.9,-6290".split(",")));
        list.add(Arrays.asList("0,52,8.2,89.7,4.9,6.5,11.5,37.6,68.3,2,-5350".split(",")));
        list.add(Arrays.asList("0,63,6.8,80.2,12.3,11.9,11.6,31.5,97.3,1.1,6730".split(",")));
        list.add(Arrays.asList("1,89,13.5,90.7,5.6,13.9,13.1,36.4,167.9,2.6,4003".split(",")));
        list.add(Arrays.asList("1,53,15.6,89.8,6.6,9.9,23.3,25.7,227.3,9.6,6205".split(",")));
        list.add(Arrays.asList("0,60,10,79.6,10.4,7,12.3,46.5,167,0.9,551".split(",")));
        list.add(Arrays.asList("0,53,11.1,88.8,6.5,7.7,15.3,36.6,236.7,1,1590".split(",")));
        list.add(Arrays.asList("1,28,15.2,89.3,6.1,8.3,14.6,29.7,299.9,1.4,645".split(",")));
        list.add(Arrays.asList("0,64,5.2,79.6,14.2,24.7,12,30.4,184.2,2.9,5276".split(",")));
        list.add(Arrays.asList("0,69,8.1,81.9,5.6,28,16.9,56.1,167.3,3.1,-3977".split(",")));
        list.add(Arrays.asList("0,33,10.7,68.6,38.5,7.7,11.2,41.5,180.2,1.7,4520".split(",")));
        list.add(Arrays.asList("0,67,7.9,84,10.1,5.8,12,35.2,172.9,0.7,1204".split(",")));
        list.add(Arrays.asList("0,63,7,82.6,11.9,2.9,37.4,30.3,267.4,1.3,-7404".split(",")));
        list.add(Arrays.asList("0,52,14.6,79.4,13.4,11.5,12.3,40,145.6,2.3,0".split(",")));
        list.add(Arrays.asList("0,60,8.7,90.1,4,7.1,12.6,37.9,252.7,1.6,8197".split(",")));
        list.add(Arrays.asList("0,33,6.4,71.7,22.8,7.4,12.5,35.1,281,1.9,-1281".split(",")));
        list.add(Arrays.asList("0,35,15.4,91.8,2.9,1.1,10.9,45.1,268,0.8,4416".split(",")));
        list.add(Arrays.asList("0,27,9,72.2,19.6,6,12.7,43.1,184,0.6,2582".split(",")));
        list.add(Arrays.asList("0,67,11.8,87.8,5.4,5.7,13.8,44.8,140.1,1.2,4300".split(",")));
        list.add(Arrays.asList("1,50,5.3,96.1,2.7,20,12.1,46.9,52.8,1.4,4272".split(",")));
        list.add(Arrays.asList("0,30,10,87.1,8.6,1.5,13.3,35.2,203.3,35.8,5887".split(",")));
        list.add(Arrays.asList("0,54,15.8,82.3,8.5,4.4,13.1,50.8,89.8,35.2,1800".split(",")));
        list.add(Arrays.asList("0,42,9.1,78.7,15.9,6.6,10.9,47.6,219.3,1,-346".split(",")));
        list.add(Arrays.asList("0,50,7.2,81.9,9.7,10.3,11.2,47.3,156.4,0.9,-1195".split(",")));
        list.add(Arrays.asList("0,61,9,85.7,9.9,7.5,14.6,38.1,191.8,2.5,-4765".split(",")));
        list.add(Arrays.asList("0,31,9.7,83.9,8.7,4.1,11.4,37.8,184.6,1,-1175".split(",")));
        list.add(Arrays.asList("1,79,9.2,91.3,5.9,11.9,14.3,42.1,143,2.3,7692".split(",")));
        list.add(Arrays.asList("0,62,9.2,89.9,4.8,13.9,11.4,162.7,145.6,0.8,4240".split(",")));
        list.add(Arrays.asList("0,63,17.6,86.2,8.3,8.4,16.2,37.8,156.7,2.4,145".split(",")));
        list.add(Arrays.asList("0,39,13.1,72,17.9,2.3,12.4,37.1,274,1.4,3245".split(",")));
        list.add(Arrays.asList("0,48,16.3,86.4,7.2,4.5,10.8,45.3,145.6,1.9,-2130".split(",")));
        list.add(Arrays.asList("0,55,11,72.3,14.5,7.9,12.9,39.5,145.6,0.9,2025".split(",")));
        list.add(Arrays.asList("0,20,12.2,82,8.7,5.9,12.4,38.6,145.6,1.1,-1213".split(",")));
        list.add(Arrays.asList("0,50,8.9,71.7,19.7,25.7,11.9,34.7,234.6,1.1,-5302".split(",")));
        list.add(Arrays.asList("0,55,11.7,82.2,11.4,5.6,13.3,44.7,145.6,2.3,5585".split(",")));
        list.add(Arrays.asList("0,70,7.5,63.6,32.9,21.1,13.2,41.1,204.8,1,110".split(",")));
        list.add(Arrays.asList("0,85,10.7,92.4,5.2,2.1,10.7,40.5,80.9,1.7,-780".split(",")));
        list.add(Arrays.asList("0,86,9.7,87.3,7.3,6.8,12.2,44.4,151.7,0.8,1060".split(",")));
        list.add(Arrays.asList("0,75,9.5,75.8,10.7,36.2,12.1,33.6,221.3,1.8,-280".split(",")));
        list.add(Arrays.asList("0,68,7.1,87.8,7,8.7,10.5,31.6,231.2,0.6,3046".split(",")));
        list.add(Arrays.asList("1,58,74.2,7.5,8.5,7.6,17.4,48.4,125.7,1.9,5810".split(",")));
        list.add(Arrays.asList("1,92,8.9,90.3,4.4,14.3,11.9,42,123.7,2,-974".split(",")));
        list.add(Arrays.asList("0,81,15.4,87.8,4.1,22.5,16.1,43.9,209,1.3,-1260".split(",")));
        list.add(Arrays.asList("0,75,16.1,90.2,4.7,6,12.9,36.4,114.2,1,3400".split(",")));
        list.add(Arrays.asList("1,84,8.2,60.3,24.3,8.2,21.1,134.3,124.7,12.9,4585".split(",")));
        list.add(Arrays.asList("0,61,7.4,85.9,9.5,4.7,11.3,45.4,161.3,2.1,1530".split(",")));
        list.add(Arrays.asList("0,62,8,37.1,30.6,7.3,13.9,39.8,166.3,1.8,3125".split(",")));
        list.add(Arrays.asList("0,84,15.9,82.6,12.4,5.8,16.6,39.2,147.5,1.8,-3545".split(",")));
        list.add(Arrays.asList("1,86,12.9,88.9,5.8,4.9,10.7,41.1,133.3,1.4,-250".split(",")));
        list.add(Arrays.asList("0,41,9.3,76.3,13.6,14.3,12.5,43.3,154,0.9,-5922".split(",")));
        list.add(Arrays.asList("0,63,5.5,60.5,30.9,2.9,11.9,40.7,191.1,0.9,7915".split(",")));
        list.add(Arrays.asList("0,50,7,56.8,33.8,3,11.9,35.9,131,0.9,7075".split(",")));
        list.add(Arrays.asList("1,55,18.7,55.9,25,18.9,21,66,94.6,14.3,7450".split(",")));
        list.add(Arrays.asList("0,52,23.6,86.3,5.2,4.9,13.6,45.5,215.5,1,3355".split(",")));
        list.add(Arrays.asList("0,73,13.4,94.1,2.1,8.4,15.8,41.2,234.1,1.2,3820".split(",")));
        list.add(Arrays.asList("1,75,12.9,89.2,3.8,5.3,11.4,41.5,94.4,1,11317".split(",")));
        list.add(Arrays.asList("1,71,4.1,76.9,14.3,9.6,10.5,42.9,115.5,0.9,6980".split(",")));
        list.add(Arrays.asList("0,76,3.9,90.7,7.3,14.9,11.9,33.4,210,1.1,-3260".split(",")));
        list.add(Arrays.asList("0,48,4.9,64.8,22,1.8,11.9,50.5,152.5,0.9,4583".split(",")));
        list.add(Arrays.asList("0,72,6.4,85.2,6.8,6.9,11.5,44.5,152,1.1,4705".split(",")));
        list.add(Arrays.asList("0,24,10.1,80.5,9.1,4.4,14.1,46.9,192.7,1.7,2850".split(",")));
        list.add(Arrays.asList("1,64,9.9,79,15.7,10,15.3,33.3,75.3,1.3,1435".split(",")));
        list.add(Arrays.asList("1,36,6.2,76.8,17.6,3.3,17.7,33.7,94,3.5,526".split(",")));
        list.add(Arrays.asList("0,56,5.8,81.5,14.4,6.9,16.7,52.1,129.3,1,-4010".split(",")));
        list.add(Arrays.asList("0,63,6.9,87.8,4.5,4.8,14.7,54.2,171,1.8,3005".split(",")));
        list.add(Arrays.asList("0,36,4.2,93.1,4.9,8.1,13,62.6,146.3,1.1,-2915".split(",")));
        list.add(Arrays.asList("1,31,28.9,86.8,3.6,5.6,15.1,58.6,223.6,1.1,-7430".split(",")));
        list.add(Arrays.asList("1,43,9.6,96.3,1.5,5.7,19.4,33.4,402.2,5.9,269".split(",")));
        list.add(Arrays.asList("1,65,12.7,91.8,6.7,13.7,15.4,36.5,107.3,1.7,10967".split(",")));
        list.add(Arrays.asList("1,65,57.1,93.1,1.9,16.8,16,58.2,93.6,1.8,8186".split(",")));
        list.add(Arrays.asList("1,47,10.7,82.7,10.6,29.2,17.2,39.8,50.3,1.1,-3692".split(",")));
        list.add(Arrays.asList("1,47,18,85.4,7.8,10.1,12.5,53.1,203.7,0.9,12678".split(",")));
        list.add(Arrays.asList("1,73,12.8,89.6,5.1,19.9,15.8,42.5,195.8,1.1,12829".split(",")));
        list.add(Arrays.asList("1,78,21.6,92.7,2.4,12.4,15.1,41.4,104.2,1.9,2196".split(",")));
        list.add(Arrays.asList("1,25,9.5,89.8,5.6,7.8,15.3,29.8,96.9,1.3,16997".split(",")));
        list.add(Arrays.asList("1,58,15,96.4,2.4,29.1,19.4,30.9,65.8,2.1,13371".split(",")));
        list.add(Arrays.asList("0,30,10.9,94,3.8,29.6,15.8,35.1,221.9,2.1,10623".split(",")));
        list.add(Arrays.asList("0,73,20.2,90.7,6.1,17.4,14.6,46.1,145.2,1.8,10542".split(",")));
        list.add(Arrays.asList("1,75,14,87.7,7.9,36.2,15.1,39.4,86.7,1.6,7436".split(",")));
        list.add(Arrays.asList("1,80,26.6,93.5,2.9,37.6,28.8,56,59.7,3.1,5110".split(",")));
        list.add(Arrays.asList("0,55,8.3,86.3,5.8,21.5,12.6,35.3,78,1.4,5356".split(",")));
        list.add(Arrays.asList("1,47,5.1,89.6,8.3,13.6,14.3,30.7,78.4,1.5,9014".split(",")));
        list.add(Arrays.asList("1,65,18.1,89.5,7.6,19.9,17.8,52.8,218.7,1.6,3631".split(",")));
        list.add(Arrays.asList("1,62,10.5,86.2,10,5.4,18,28.2,73.6,5.2,9512".split(",")));
        list.add(Arrays.asList("1,68,9.9,91,6.4,5.7,14.1,24.2,176.1,3.2,9469".split(",")));
        list.add(Arrays.asList("0,51,15.5,88,11,9.7,15.3,44.1,133.7,2.6,-1268".split(",")));
        list.add(Arrays.asList("1,83,9.1,95.9,2.9,32.2,11.2,29.8,103,2.3,-11895".split(",")));
        list.add(Arrays.asList("0,62,10.4,91,7.1,8.5,13.4,33.9,150.7,3.6,13438".split(",")));
        list.add(Arrays.asList("1,75,18.1,95.3,3,7.3,16.1,40.2,129.5,4.1,11325".split(",")));
        list.add(Arrays.asList("1,78,17.1,87.7,7.8,12.3,48.8,45.9,122.7,3.6,3107".split(",")));
        list.add(Arrays.asList("0,36,15.5,86.4,10.1,6.7,20.8,56.6,163.3,3.6,-12640".split(",")));
        list.add(Arrays.asList("1,62,9.4,88.1,9,6.6,15.6,40.2,150.3,2.9,5755".split(",")));
        list.add(Arrays.asList("0,58,10.3,92.2,6,6.3,17.1,38.6,287.3,4.5,-10291".split(",")));
        list.add(Arrays.asList("0,54,15.1,91.5,4.5,8.1,11,35.8,136.8,2.7,-3260".split(",")));
        list.add(Arrays.asList("1,79,2.1,77,20.9,21.3,22,37.4,135.7,9.2,3315".split(",")));
        list.add(Arrays.asList("0,88,34.5,95.2,3.7,7.3,14,41.3,177.7,3.4,6090".split(",")));
        list.add(Arrays.asList("1,72,13.7,93.4,4.4,7.9,14.8,59.6,100.7,3.9,-5306".split(",")));
        list.add(Arrays.asList("1,70,17.2,94.8,3,8.5,13.7,42.3,136.7,3.2,310".split(",")));
        list.add(Arrays.asList("1,69,13.9,92,3.1,11.5,12.4,50.5,128,4.7,10786".split(",")));
        list.add(Arrays.asList("1,34,19.2,95.4,3.3,5.3,14.3,55,113.7,4.5,2017".split(",")));
        list.add(Arrays.asList("0,58,16,87.5,9.4,14.1,14.4,54.8,131.7,2.6,4525".split(",")));
        list.add(Arrays.asList("0,59,12.3,95.2,2.8,9,11.6,29.4,195.7,2.1,-204".split(",")));
        list.add(Arrays.asList("0,54,34,95.3,3.6,19,20,43.2,170,6.3,1815".split(",")));
        list.add(Arrays.asList("1,60,9.6,89,7.7,5.3,15.6,46,187,2.4,-852".split(",")));
        list.add(Arrays.asList("0,29,10.6,87.1,8.2,5.7,25.6,43.4,107.7,3.7,-903".split(",")));
        list.add(Arrays.asList("0,62,17.2,87,7.4,11.7,13.2,43,198,2.6,-8095".split(",")));
        list.add(Arrays.asList("1,27,6.8,89.1,9.6,6.7,13.8,28,161.3,2.1,4868".split(",")));
        list.add(Arrays.asList("0,50,10,93.7,4.5,7.6,10.6,31.5,131.7,3.6,-1481".split(",")));
        list.add(Arrays.asList("1,49,9.5,95.3,2.9,21.3,14.1,38.9,94,2.2,711".split(",")));
        list.add(Arrays.asList("0,66,8.5,76.3,17.5,6.5,12.1,43.2,165.3,1.8,7080".split(",")));
        list.add(Arrays.asList("0,73,12.5,92.6,3.7,10.3,12.7,42.9,77.7,1.5,-8902".split(",")));
        list.add(Arrays.asList("1,78,19,94.1,2.2,10.1,15.3,41.1,111.3,2.4,-2776".split(",")));
        list.add(Arrays.asList("0,41,24.5,80.5,14.8,23.2,12.9,41.1,135.7,0.8,2797".split(",")));
        list.add(Arrays.asList("1,63,19.1,89.4,5.5,13,13.7,51,115.2,2.4,4687".split(",")));
        list.add(Arrays.asList("1,49,7.6,97.2,1.1,15.5,15.6,42.7,142.4,1.1,6517".split(",")));
        list.add(Arrays.asList("1,61,21.3,91.7,2.9,11.1,15.2,50.7,61,3.5,9728".split(",")));
        list.add(Arrays.asList("0,31,8,91.1,5.6,9.9,17.2,49.7,170.3,3.1,-4436".split(",")));
        list.add(Arrays.asList("1,53,13.9,85,5.8,6.3,15,35.3,154,1.5,14685".split(",")));
        list.add(Arrays.asList("1,43,15.2,91.3,4,8.5,14.5,48.7,121.3,1.5,2091".split(",")));
        list.add(Arrays.asList("0,56.2,24.4,30.6,0.4,8.7,18.2,53.7,199.5,1.7,4683".split(",")));
        list.add(Arrays.asList("1,60,18.6,91.1,2.3,3.8,16.2,47.3,71.3,2.4,4796".split(",")));
        list.add(Arrays.asList("1,55,33.3,48.3,7.4,30.7,25.9,49.3,174,2.9,8633".split(",")));
        list.add(Arrays.asList("0,33,8.8,85.8,10.6,10.3,15.1,46.7,117.3,1.2,4225".split(",")));
        list.add(Arrays.asList("0,56.2,17.7,91.2,1.4,6.8,17.3,66.3,120.7,1,2002".split(",")));
        list.add(Arrays.asList("1,60,9.2,92.1,6.2,8.2,1.1,42,66.7,1,11280".split(",")));
        list.add(Arrays.asList("1,58,5.5,95.1,3.6,9.6,13.8,41,61.3,3,4757".split(",")));
        list.add(Arrays.asList("0,22,10.1,91.8,6.4,25.5,14.6,33.3,73,1.4,8132".split(",")));
        list.add(Arrays.asList("0,36,11.8,85.3,8.7,10.3,15.1,38.3,104,1.5,5793".split(",")));
        list.add(Arrays.asList("1,43,9.8,96.9,2,13.5,15.2,33.3,78,2.5,211".split(",")));
        list.add(Arrays.asList("0,47,8.4,90.8,5.2,31.2,16,44,113.7,1.1,4791".split(",")));
        list.add(Arrays.asList("0,65,8.1,82.3,13.2,4.6,13.9,34.6,134.3,1,5227".split(",")));
        list.add(Arrays.asList("1,0,7.5,96.4,3.3,10.9,15.1,28.7,87.3,1.6,5981".split(",")));
        list.add(Arrays.asList("1,66,19.1,86,5.7,5.5,15.3,33.1,193,1.1,1920".split(",")));
        list.add(Arrays.asList("1,67,7.8,94.6,4,5.5,12.6,34.3,51,1.9,6642".split(",")));
        list.add(Arrays.asList("1,34,1.4,88.1,12.4,15.2,16.3,40,107.3,3.6,-163".split(",")));
        list.add(Arrays.asList("1,41,11.3,89.8,8,16.8,7.9,33.3,81.3,2,5822".split(",")));
        list.add(Arrays.asList("0,37,9.1,80.1,10.5,8,13.4,38.7,158,2.9,3872".split(",")));
        list.add(Arrays.asList("1,42,4.6,91.7,6.4,20.8,12.1,47,71.7,3.7,3748".split(",")));
        list.add(Arrays.asList("1,64,5.4,71.5,17.9,25,16.3,25.3,125,1,-4010".split(",")));
        list.add(Arrays.asList("1,81,28.1,98.4,1.2,24.8,18.7,41.7,167,3.6,7242".split(",")));
        list.add(Arrays.asList("1,68,8.7,84,3.6,9.8,15.1,36.7,103.7,1.4,7431".split(",")));
        list.add(Arrays.asList("1,66,11.2,91.1,7.4,8.8,24.2,34.7,98.3,2.7,4502".split(",")));
        list.add(Arrays.asList("1,73,22.8,89,3.1,8.2,15.1,45.3,114.2,2.4,3258".split(",")));
        list.add(Arrays.asList("1,30,15.5,91.4,3.7,21.7,15.1,32.3,147.7,2.1,4331".split(",")));
        list.add(Arrays.asList("1,60,6.2,93,3.8,13,1.3,38,153,2.2,2223".split(",")));
        list.add(Arrays.asList("0,43,4.1,61.6,24.7,4.3,15.1,39.3,128.7,1.5,2626".split(",")));
        list.add(Arrays.asList("0,68,6.8,86,12.5,5.9,26.3,40.7,116.3,1.3,542".split(",")));
        list.add(Arrays.asList("1,74,15.2,94.4,3.4,7.6,13.9,39,94.7,1.6,2593".split(",")));
        list.add(Arrays.asList("0,78,3.2,90,8.3,25.5,15.1,36.7,164,1.2,1594".split(",")));
        list.add(Arrays.asList("1,66,15.7,96.6,1,35.4,14.7,51,53.7,1.7,1209".split(",")));
        list.add(Arrays.asList("0,63,10.8,85.9,9.5,16.1,14.4,27,125.7,1.5,-6410".split(",")));
        list.add(Arrays.asList("0,47,10.6,86.9,10.8,8.1,12.5,29.4,97,2,-4974".split(",")));
        list.add(Arrays.asList("1,71,16.7,97.4,2.3,14.5,15.1,55,61,5.9,6623".split(",")));
        list.add(Arrays.asList("1,79,9.4,92.8,5.4,11,17.3,36.3,89.7,1.8,4517".split(",")));
        list.add(Arrays.asList("0,47,6.1,80.8,8.7,6,14.9,45.7,52.3,1.4,6035".split(",")));
        list.add(Arrays.asList("1,50,12.2,93.7,2.7,5.1,14.4,82.3,42.3,3.5,3433".split(",")));
        list.add(Arrays.asList("0,19,11.8,82.8,14.6,4.8,13.6,29,154,2,9522".split(",")));
        list.add(Arrays.asList("1,25,3.6,78.2,20,33.7,12.5,30.7,70,1.1,3684".split(",")));
        list.add(Arrays.asList("0,56,9.8,84.4,11.2,11.9,15.1,45.7,60.6,2,4687".split(",")));
        list.add(Arrays.asList("0,64,14.8,88.9,5.3,6,14.7,31.7,186.7,2.9,3400".split(",")));
        list.add(Arrays.asList("1,77,3.5,89,8.1,4.9,14.3,28.3,155.3,2.2,6471".split(",")));
        list.add(Arrays.asList("1,81,8.3,93.8,3.8,7.5,15.7,38.3,169.7,1.7,12175".split(",")));
        list.add(Arrays.asList("0,71,12.8,85.4,8.4,4.6,46.3,38.7,215,0.7,-8691".split(",")));
        list.add(Arrays.asList("1,77,14.7,90.5,4.9,7.7,15.5,29.7,223.3,1.5,-103".split(",")));
        list.add(Arrays.asList("1,95,5.5,82.1,12.1,13.1,17.5,30.7,145.3,2.6,10089".split(",")));
        list.add(Arrays.asList("1,74,37.3,96.1,2.4,10.1,15.5,35,171,1.8,-1990".split(",")));
        list.add(Arrays.asList("0,72,9.6,92.3,2.5,5.6,14.2,36,88.7,1.5,-2725".split(",")));
        list.add(Arrays.asList("0,78,11.2,79.4,14.2,6.3,15.1,38,130,1.1,8518".split(",")));
        list.add(Arrays.asList("1,79,14.7,96,2.9,8.4,12.9,53.3,125.3,1.3,-370".split(",")));
        list.add(Arrays.asList("0,70,11.4,81.9,12.5,3.2,34.8,29,254.3,1.8,-1180".split(",")));
        list.add(Arrays.asList("0,21,9.7,81.6,11.8,7.2,14.4,27,223.2,0.4,-1120".split(",")));
        list.add(Arrays.asList("0,62,5.8,74.9,8.7,3.7,32.3,33,222.7,1.6,-620".split(",")));
        list.add(Arrays.asList("1,65,9.7,91,5.1,12.9,14.4,28,150.8,1.7,7952".split(",")));
        list.add(Arrays.asList("0,77,8.1,80.5,15,123,15.7,35.7,100.7,0.9,8158".split(",")));
        list.add(Arrays.asList("0,52,7.4,90.7,5.9,3.8,14,30.7,170.3,1.3,-3223".split(",")));
        list.add(Arrays.asList("1,77,16,79.6,10.9,4.8,14.7,36,181.3,1.7,0".split(",")));
        list.add(Arrays.asList("0,29,6.6,67.5,24.7,1.6,14.5,32,282.5,0.8,-5090".split(",")));
        list.add(Arrays.asList("1,74,8.3,95.2,3.9,24.1,13.8,39.3,70,3.7,4205".split(",")));
        list.add(Arrays.asList("1,63,10.9,90.6,6.2,14.7,16.7,45.3,86,1.4,2988".split(",")));
        list.add(Arrays.asList("1,76,9.4,95.2,2.6,8.9,14.6,51,98,0.7,6643".split(",")));
        list.add(Arrays.asList("1,73,28.8,86.1,9.3,7.1,14.4,56.3,129,2.3,2649".split(",")));
        list.add(Arrays.asList("1,69,11.4,86.6,10,3.4,19.5,38.7,104.4,4.6,856".split(",")));
        list.add(Arrays.asList("1,23,16.7,91.9,4.7,5.7,15.2,35,224,2.1,-8357".split(",")));
        list.add(Arrays.asList("1,74,25.7,91.2,4.6,10.3,18.2,78.7,57.7,4.2,0".split(",")));
        list.add(Arrays.asList("1,54,11.2,89.9,7.3,5.6,14.7,34.7,110.7,1.8,3064".split(",")));
        list.add(Arrays.asList("1,51,4.4,44.2,30.9,11,14.2,57,122.7,1.2,-2825".split(",")));
        list.add(Arrays.asList("1,66,9.1,83.7,11,10.5,17,36,89.5,1.1,1226".split(",")));
        list.add(Arrays.asList("0,52,6.6,90,8.9,5.4,23,40.3,221.3,1.4,-3594".split(",")));
        list.add(Arrays.asList("1,62,3.5,93.4,4.3,10.3,15.1,43,79.3,1.3,9566".split(",")));
        list.add(Arrays.asList("1,55,21.1,92.2,5.1,10.2,15.5,51.7,143.3,6.4,4590".split(",")));
        list.add(Arrays.asList("0,65,16.5,88,3.9,7.9,13.8,33.3,259.3,1.3,2233".split(",")));
        list.add(Arrays.asList("0,51,4.7,71.7,21.3,5.6,13.9,39.3,222,0.9,3920".split(",")));
        list.add(Arrays.asList("0,54,12.7,85.1,9,2,13.5,33,133.7,1.8,2540".split(",")));
        list.add(Arrays.asList("0,68,7,84.4,10.9,4.4,13.9,37.7,169.7,1.5,7235".split(",")));
        list.add(Arrays.asList("0,58,5.2,80.7,16.8,5.3,13.1,31.3,123.2,2.2,6485".split(",")));
        list.add(Arrays.asList("0,45,9,86.3,7.6,5.7,12.2,35.3,226,2.8,7580".split(",")));
        list.add(Arrays.asList("1,77,11.2,93.9,3,5.4,14,40,145.6,2.3,6923".split(",")));
        list.add(Arrays.asList("0,62,8.5,89.6,7.2,7.9,15.1,36.7,99.3,1.5,5975".split(",")));
        list.add(Arrays.asList("1,76,10.3,94.9,3.9,12.7,13.5,40.7,101.7,2.9,4805".split(",")));
        list.add(Arrays.asList("0,21,14.4,84.8,6.4,3.1,15.4,42.4,175.3,1.8,4445".split(",")));
        list.add(Arrays.asList("0,57,8,86.1,8.8,5,12.7,37.6,152.7,1.5,4691".split(",")));
        list.add(Arrays.asList("0,57,10.4,59.7,7,7.8,15.9,45.7,142.6,3.2,-1692".split(",")));
        list.add(Arrays.asList("1,70,12.7,85.5,5.3,18,12.3,50.2,55.8,2.5,2363".split(",")));
        list.add(Arrays.asList("0,60,4.9,92.2,5.7,7.9,13.3,34,173,1,1168".split(",")));
        list.add(Arrays.asList("0,28,17,92.1,5.4,3.3,18.9,41.3,367.7,1.7,0".split(",")));
        list.add(Arrays.asList("0,39,4.7,73,20.2,5.4,12.6,33.1,171.8,1.8,3136".split(",")));
        list.add(Arrays.asList("1,69,14.3,87.2,9.4,9.7,13.9,38.8,104.2,1.5,3822".split(",")));
        list.add(Arrays.asList("0,38,8,84.1,8.6,5.1,12.6,39.3,180.7,0.9,-1330".split(",")));
        list.add(Arrays.asList("1,43,17.4,83.6,8.8,5.2,15.2,36.4,115.1,3.3,-2755".split(",")));
        list.add(Arrays.asList("1,60,3.1,75.6,19.1,18.3,18.9,30.7,105.5,3.1,4410".split(",")));
        list.add(Arrays.asList("0,71,6.9,83.9,8.9,20.5,17.1,33.8,181.5,2.5,2956".split(",")));
        list.add(Arrays.asList("1,45,17.9,90.4,5.8,6.7,15.4,26.6,110.4,12.5,4890".split(",")));
        list.add(Arrays.asList("1,79,11.1,84.3,3,6.4,13.7,35.4,138.1,3.7,2658".split(",")));
        list.add(Arrays.asList("0,45,10.7,87.4,7,13.4,13.6,24.2,184.1,3,9937".split(",")));
        list.add(Arrays.asList("0,45,14.4,85.3,6.4,6.9,12.6,28.6,219.6,4.1,-1985".split(",")));
        list.add(Arrays.asList("1,53,9.3,87.7,11.2,11.4,12.2,30.9,130.3,9.4,5965".split(",")));
        list.add(Arrays.asList("0,78,12.7,93.1,4.4,13.3,14.3,42,128.7,2.9,8605".split(",")));
        list.add(Arrays.asList("1,77,27.8,92.7,2.3,13.3,21.5,31.6,245.7,4,2355".split(",")));
        list.add(Arrays.asList("0,52,8.2,88.4,8.3,14.6,13.1,29.6,75.2,2.8,1457".split(",")));
        list.add(Arrays.asList("0,60,21.8,92.1,2.6,31.6,29.2,37.4,211.2,3,930".split(",")));
        list.add(Arrays.asList("0,52,15.9,84.3,10.8,2.7,18.5,33.5,150.9,1.5,-3852".split(",")));
        list.add(Arrays.asList("0,56,10.7,83.6,10.9,6.9,11.9,40.6,122.3,2.6,4498".split(",")));
        list.add(Arrays.asList("1,80,11,92,5.5,20.4,11,37.3,85.7,1.8,10905".split(",")));
        list.add(Arrays.asList("1,63,18.6,87.9,5.8,17.9,57.9,40.8,87.4,2.4,3869".split(",")));
        list.add(Arrays.asList("0,57,12.5,88.2,4,5.4,13.6,42.3,143.6,2.6,222".split(",")));
        list.add(Arrays.asList("0,26,7.8,84.2,9.4,4.2,12.7,37.6,144,0.8,0".split(",")));
        list.add(Arrays.asList("1,76,12.2,87.3,8.8,5,15.1,47,137,1.6,6899.5".split(",")));
        list.add(Arrays.asList("1,79,7.6,88.9,7.1,8.7,12.3,32.7,128,2.5,5527.5".split(",")));
        list.add(Arrays.asList("1,42,6.2,85,8.9,9.8,11.1,31.8,89.9,2.9,8667".split(",")));
        list.add(Arrays.asList("1,75,14.2,95.6,1.1,9.8,13.7,34.3,140,2.2,28623".split(",")));
        list.add(Arrays.asList("0,58,19.4,96.1,2.5,3.1,13.1,29.3,79.5,1.3,948".split(",")));
        list.add(Arrays.asList("1,47,7.6,30.5,31.6,13.7,11.2,38.7,65.7,1.4,10345".split(",")));
        list.add(Arrays.asList("0,28,26.2,92,3.7,3.2,11.5,37,176.7,0.9,2792".split(",")));
        list.add(Arrays.asList("1,45,14.4,88.5,5.7,10.1,18.9,52,103.3,2.5,4907".split(",")));
        list.add(Arrays.asList("0,53,7,89.1,5.5,3.2,12.1,34.7,343.7,1.8,-1214".split(",")));
        list.add(Arrays.asList("0,34,5,71.1,22.3,4,11.6,35.3,259.2,1.1,154".split(",")));
        list.add(Arrays.asList("0,25,6.3,84.1,8.8,3.7,14,43,151.7,2.3,3424".split(",")));
        list.add(Arrays.asList("0,49,14.6,54,18.9,8.1,11.8,39.7,109.1,4.1,-1041".split(",")));
        list.add(Arrays.asList("0,52,3.2,89,8,3.7,12.2,28.7,176.9,1.2,4514".split(",")));
        list.add(Arrays.asList("1,50,13.3,94.7,3.9,5.7,12.3,40,77.8,2.1,19517".split(",")));
        list.add(Arrays.asList("1,50,9.6,89.6,4.8,8.9,11.8,32.7,130.7,0.9,-897".split(",")));
        list.add(Arrays.asList("0,53,8.7,81.1,13.8,3.5,12,37.7,273.3,0.5,-1861".split(",")));
        list.add(Arrays.asList("0,27,8.1,82.4,6.8,6.7,14.5,32.7,186.7,1.8,7827".split(",")));
        list.add(Arrays.asList("0,50,14.1,96.7,2.3,18.7,11.6,25.3,246.7,1.1,-4536".split(",")));
        list.add(Arrays.asList("0,81,14.1,90.2,4.1,16.3,14.3,30.7,125.7,1.2,2042".split(",")));
        list.add(Arrays.asList("0,42,13,87,7.2,10.3,12,33.5,250,0.9,205".split(",")));
        list.add(Arrays.asList("1,70,23,88.2,3.4,13.9,20.3,44.3,70.7,2.2,3602".split(",")));
        list.add(Arrays.asList("1,82,11.5,89.5,6.8,7.2,11.5,50.7,191.7,1.3,12247".split(",")));
        list.add(Arrays.asList("0,56,15,78.5,9.9,15.4,13.8,35.7,307.3,1.5,10718".split(",")));
        list.add(Arrays.asList("1,34,14.5,98,0.8,18.9,15,31,209.7,3,9603".split(",")));
        list.add(Arrays.asList("1,53,4.4,83.6,10.2,11,11,38.7,204,2,12523".split(",")));
        list.add(Arrays.asList("1,48,6,89.2,8.5,16.2,19.6,39.3,175,8.3,1211".split(",")));
        list.add(Arrays.asList("0,25,8.1,88,10.1,3.6,15.4,35,240.7,1.9,11100".split(",")));
        list.add(Arrays.asList("0,49,4.6,82.7,8.2,4.9,12.5,43.3,117.3,1.2,6947".split(",")));
        list.add(Arrays.asList("0,21,37.9,96.1,3.4,10.5,14.8,39,211.7,2.3,986".split(",")));
        list.add(Arrays.asList("0,61,16.5,90.2,3.9,9.6,11.2,34,170,0.9,4080".split(",")));
        list.add(Arrays.asList("0,66,8.6,88,6.7,7.1,15.1,33.3,113.7,0.9,10675".split(",")));
        list.add(Arrays.asList("0,62,13.6,97.6,2.1,9.1,11.4,29.6,81.3,1.1,-1124".split(",")));
        list.add(Arrays.asList("0,50,12.2,87.9,11.5,3.9,9.2,31,158.3,1.8,1777".split(",")));
        list.add(Arrays.asList("0,21,12,90.3,5.5,17,13.3,43.5,101.1,1.7,5868".split(",")));
        list.add(Arrays.asList("0,68,4.4,58.5,24.4,5.8,12.1,40,66.5,2,10461".split(",")));
        list.add(Arrays.asList("0,17,20.2,89,6.3,5,13,38.3,111.8,3.3,11929".split(",")));
        list.add(Arrays.asList("1,24,6.5,88.5,8,7.9,10.1,31.6,111,1.6,7861".split(",")));
        list.add(Arrays.asList("0,33,12.5,92.3,3.6,7.9,10.5,46,69.5,1.7,8554".split(",")));
        list.add(Arrays.asList("1,59,9.4,89.4,7.2,15.4,17.8,29.7,87.7,4.6,-258".split(",")));
        list.add(Arrays.asList("1,30,13.1,94.8,2.2,17.9,14.8,30,179.5,2,17855".split(",")));
        list.add(Arrays.asList("1,53,19.5,93.6,3.6,9.7,14.8,33,120.5,5.4,10087".split(",")));
        list.add(Arrays.asList("0,28,11.1,83.2,8.7,7.6,11,53.9,33.4,2.2,11599".split(",")));
        list.add(Arrays.asList("1,76,11.8,89.4,6,5.6,10.4,40.3,113.7,1.3,-3340".split(",")));
        list.add(Arrays.asList("0,69,11.5,97.1,1.4,14.9,12.7,27.4,88,1.9,-2300".split(",")));
        list.add(Arrays.asList("0,48,14,91.7,4.3,6.2,11.5,41.2,47.1,2.1,-3265".split(",")));
        list.add(Arrays.asList("0,35,19,96.8,1.8,8.5,9.3,45.7,96.7,2.1,8950".split(",")));
        list.add(Arrays.asList("0,48,5.6,80.8,11.3,5.3,9.7,33.8,166.3,1.1,6740".split(",")));
        list.add(Arrays.asList("0,37,13.8,90.2,4.9,8.2,13.2,41.3,290.7,1.7,-5516".split(",")));
        list.add(Arrays.asList("0,56,12.7,92.9,2.9,31,12.6,30.6,114.7,1.6,-7904".split(",")));
        list.add(Arrays.asList("0,40,3.9,87.3,8.2,2.2,10.6,41.7,68.3,1.4,6891".split(",")));
        list.add(Arrays.asList("1,48,15.2,91.1,6,10.1,16.6,35.2,67.9,2.7,7340".split(",")));
        list.add(Arrays.asList("0,30,8.1,87.6,6.9,5.7,10.9,34.6,150.3,1.1,4770".split(",")));
        list.add(Arrays.asList("0,65,14,86.3,8.7,9.1,10.8,34.2,174.3,2,-1707".split(",")));
        list.add(Arrays.asList("1,27,4.4,82.7,13.1,10.1,10.8,37.9,175.9,1.8,13525".split(",")));
        list.add(Arrays.asList("1,35,5.2,82.5,8.9,8,9.1,49,69.7,1.7,5341".split(",")));
        list.add(Arrays.asList("0,30,8.3,71.6,16.9,5.3,11.2,36,134.7,0.9,668".split(",")));
        list.add(Arrays.asList("1,68,12.3,93.3,3.3,11.6,12.3,40.9,73.7,2.1,2334".split(",")));
        list.add(Arrays.asList("1,21,9.6,86.3,9.3,7.2,12.2,36.8,123.7,1.4,4620".split(",")));
        list.add(Arrays.asList("1,46,1.2,80.6,16.3,3.1,11.7,31.4,87.2,1.3,9749".split(",")));
        list.add(Arrays.asList("1,48,13.7,84.8,8.8,5,10.2,41.3,55.5,1.4,12626".split(",")));
        list.add(Arrays.asList("1,39,8,90.4,6,14.3,14.7,47.9,72,2,7989".split(",")));
        list.add(Arrays.asList("1,59,7.2,91.2,8,3.6,18,38.1,99.7,1.8,19991".split(",")));
        list.add(Arrays.asList("1,46,7.5,91,4.4,6.5,10.2,42.6,158.3,1.8,4135".split(",")));
        list.add(Arrays.asList("0,36,5.7,80.7,11.3,7.6,9.2,37.9,105.7,1.5,4960".split(",")));
    }
}
