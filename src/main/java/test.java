import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.wp.usermodel.CharacterRun;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import pojo.textObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) throws IOException {
            String path = "E:\\gitt\\comnjugovernment\\src\\main\\resources\\政策\\关于组织开展2020年度首批省星级上云企业认定工作的通知.docx";
            InputStream is = new FileInputStream(path);
            List<String> list = new ArrayList<String>();
            XWPFDocument doc = new XWPFDocument(is);
            List<XWPFParagraph> paras = doc.getParagraphs();
            textObject text = new textObject();
            for (XWPFParagraph graph : paras) {

                // 获取段落文字的字体与大小
                String paragraphText = graph.getParagraphText();
                if("".equals(paragraphText)){
                    continue;
                }

                List<XWPFRun> runs = graph.getRuns();
                if(runs.size() != 0){
                    int fontSize = runs.get(0).getFontSize();
                    int characterSpacing = runs.get(0).getCharacterSpacing();

                    int kerning = runs.get(0).getKerning();
                    System.out.println(fontSize+":"+characterSpacing+":"+kerning);
                }



            }
        }


}
