package Util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfToWord{
    /**
     * Pdf 转 word
     * @param originPath
     * @param targetPath
     */
    public static void pdfToWord(String originPath,String targetPath){
        PdfDocument pdf = new PdfDocument();
        pdf.loadFromFile(originPath);
        pdf.saveToFile(targetPath);
    }

}
