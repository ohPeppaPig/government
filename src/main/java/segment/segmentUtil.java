package segment;

import com.huaban.analysis.jieba.JiebaSegmenter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 中文分词工具
 */
public class segmentUtil {
    private static String chineseStopWords = "segment/stopWords.txt";

    public static String segmenter(String str){
        JiebaSegmenter segmenter = new JiebaSegmenter();
        str = chineseClean(str);
        List<String> strings = segmenter.sentenceProcess(str);
        str = String.join(" ", strings);
        str = Stopwords.remover(str,chineseStopWords);
        return str;

    }

    public static String chineseClean(String input) {

        StringBuilder sb = new StringBuilder();

        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+");
        Matcher m = p.matcher(input);

        while (m.find()) {
            sb.append(m.group().trim());
//            sb.append(" ");
        }
        return sb.toString();
    }
}
