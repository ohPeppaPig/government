package Service;

import Util.ClusterUtil;
import com.hankcs.hanlp.mining.cluster.ClusterAnalyzer;

import java.io.File;
import java.util.List;
import java.util.Set;

public class policyCluster {

    public static String policy_preprocess = "E:\\gitt\\comnjugovernment\\src\\main\\java\\preprocess";
    public static void cluster(){
        File file = new File(policy_preprocess);

        ClusterAnalyzer<String> analyzer = ClusterUtil.addFileToCluster(file.getPath());
        List<Set<String>> sets = analyzer.repeatedBisection(0.7);
        System.out.println("聚类结果：");
        for (Set<String> set: sets) {
            System.out.println(set);
            System.out.println("_____________________");
        }


    }

    public static void main(String[] args) {
        cluster();
    }
}
