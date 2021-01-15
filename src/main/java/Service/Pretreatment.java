package Service;

import Util.FileIOUtil;
import Util.spireReadUtil;

import java.io.File;

// 预处理过程
public class Pretreatment {

    public static String policy_origin = "E:\\gitt\\comnjugovernment\\src\\main\\resources\\政策";

    public static String policy_preprocess = "E:\\gitt\\comnjugovernment\\src\\main\\java\\preprocess";


    public static void preProcess(){
        File file = new File(policy_origin);
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                for (File file1 : f.listFiles()) {
                    String content = null;
                    String name = null;
                    if (file1.getName().endsWith(".pdf")) {
                        content = spireReadUtil.getContentFromPdf(file1.getPath());
                        name = file1.getName().split(".pdf")[0].replace(' ','_');
                    }else if(file1.getName().endsWith(".doc")||file1.getName().endsWith(".docx")){
                        content = spireReadUtil.getContentFromDoc(file1.getPath());
                        name = file1.getName().split(".doc")[0].replace(' ','_');
                    }
                    if(content == null){
                        System.out.println("出错！！！");
                        continue;
                    }
                    FileIOUtil.writeFile(content, policy_preprocess+"/"+name);
                }
            }
        }
    }



    public static void main(String[] args) {
        preProcess();

    }
}
