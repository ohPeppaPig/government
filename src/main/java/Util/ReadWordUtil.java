package Util;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.*;
import pojo.notice;
import pojo.textObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * 读取word文档中表格数据，支持doc、docx
 * @author Fise19
 *
 */
public class ReadWordUtil {

    private static List<String> oneTitle = Arrays.asList("一、", "二、", "三、","四、","五、","六、","七、");

    public static void main(String[] args) throws IOException {

    }


    /**
     * 将文档内容提取到数据结构
     * 方法未完整！！！
     * @param path
     * @return
     * @throws IOException
     */
    public static textObject getParamStruct(String path) throws IOException {
        InputStream is = new FileInputStream(path);
        XWPFDocument doc = new XWPFDocument(is);
        List<XWPFParagraph> paras = doc.getParagraphs();
        textObject textObject = new textObject();
        int count = 0;

        // 去除文章中的回车部分
        List<XWPFParagraph> parasWithNoBlank = new ArrayList<>(paras.size()/2);

        for (XWPFParagraph graph:paras) {
            String paragraphText = graph.getParagraphText();
            if("".equals(paragraphText)){
                continue;
            }
            parasWithNoBlank.add(graph);
        }
        // 处理头标题
        XWPFParagraph xwp0 = parasWithNoBlank.get(0);
        textObject.setHeadTitle(xwp0.getParagraphText());

        // 处理id
        XWPFParagraph xwp1 = parasWithNoBlank.get(1);
        textObject.setIssueId(xwp1.getParagraphText());

        // 处理标题
        int id = 2;
        StringBuilder sb = new StringBuilder();
        while ("CENTER".equals(parasWithNoBlank.get(id).getAlignment().name())){
            sb.append(parasWithNoBlank.get(id).getParagraphText());
            id++;
        }
        textObject.setTitle(sb.toString());
        XWPFParagraph paragraph1 = parasWithNoBlank.get(id++);
        textObject.setTargetAgency(paragraph1.getParagraphText());
        XWPFParagraph paragraph2 = parasWithNoBlank.get(id++);
        textObject.setTargetText(paragraph2.getParagraphText());


        List<Integer> titles = new ArrayList<>();
        for (int i = id; i < parasWithNoBlank.size(); i++) {
            XWPFParagraph xwpfParagraph = parasWithNoBlank.get(id);
            String paragraphText = xwpfParagraph.getParagraphText();
            String serialNumber = paragraphText.substring(0, 2);
            if(oneTitle.contains(serialNumber)){
                titles.add(id);
            }
        }

        List<notice> notices = new ArrayList<>();
        for (int i = 0; i < titles.size()-1;i++) {
            int j = i+1;
            XWPFParagraph xwpfParagraph = parasWithNoBlank.get(i);
            String paragraphText = xwpfParagraph.getParagraphText();

            notice notice = new notice();
            notice.setDescription(paragraphText);
            StringBuilder s = new StringBuilder();
            for (int k = titles.get(i); k < titles.get(j); k++) {
                XWPFParagraph xwp = parasWithNoBlank.get(k);
                String para = xwp.getParagraphText();
                s.append(para);
            }
            notice.setText(s.toString());
            notices.add(notice);
        }

        // 匹配最后一个标题

        int last = titles.get(titles.size()-1);
        XWPFParagraph xwpfParagraph = parasWithNoBlank.get(last);
        String paragraphText = xwpfParagraph.getParagraphText();

        notice notice = new notice();
        notice.setDescription(paragraphText);
        StringBuilder s = new StringBuilder();
        int index = last + 1;
        while (index < parasWithNoBlank.size()){
            // 有附件 && 遇到落款
            XWPFParagraph xwpfParah = parasWithNoBlank.get(index);
            if("RIGHT".equals(xwpfParah.getAlignment().name())){
                break;
            }
            String p = xwpfParagraph.getParagraphText();
            s.append(p);
            index++;
//            if("附".equals(paragraph.substring(0,1))){
//
//            }

        }
        notice.setText(s.toString());
        notices.add(notice);





        return null;

    }


    /**
     * <b> 获得段落内容及表格内容
     * </b><br><br><i>Description</i> :  待优化
     * @return void
     * <br><br>Date: 2019/11/16 18:01     <br>Author : dxl
     */
    public static void getWordText(String path) throws IOException {
        XWPFDocument document = new XWPFDocument(new FileInputStream(path));
        try {
            // 获取word中的所有段落与表格
            List<IBodyElement> elements = document.getBodyElements();
            for (IBodyElement element : elements) {
                // 段落
                if (element instanceof XWPFParagraph) {
                    getParagraphText((XWPFParagraph) element);
                }
                // 表格
                else if (element instanceof XWPFTable) {
                    getTabelText((XWPFTable) element);
                }
            }
        } finally {
            document.close();
        }
    }
    /**
     * 获取段落内容
     *
     * @param paragraph
     */
    private static void getParagraphText(XWPFParagraph paragraph) {
        // 获取段落中所有内容
        List<XWPFRun> runs = paragraph.getRuns();
        if (runs.size() == 0) {
            System.out.println("按了回车（新段落）");
            return;
        }
        StringBuffer runText = new StringBuffer();
        for (XWPFRun run : runs) {
            runText.append(run.text());
        }
        if (runText.length() > 0) {
            runText.append("，对齐方式：").append(paragraph.getAlignment().name());
            System.out.println(runText);
        }
    }

    /**
     * 获取表格内容
     *
     * @param table
     */
    private static void getTabelText(XWPFTable table) {
        List<XWPFTableRow> rows = table.getRows();

        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                // 简单获取内容（简单方式是不能获取字体对齐方式的）
                // System.out.println(cell.getText());
                // 一个单元格可以理解为一个word文档，单元格里也可以加段落与表格
                List<XWPFParagraph> paragraphs = cell.getParagraphs();
                for (XWPFParagraph paragraph : paragraphs) {
                    getParagraphText(paragraph);
                }
            }
        }
    }
}
