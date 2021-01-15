package Util;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIOUtil {
    public static void abort(String m) {
        System.err.println(m);
        System.err.flush();
        Thread.dumpStack();
        System.exit(1);
    }

    @Nullable
    public static String readFile(@NotNull String path) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return Charset.forName("UTF-8").decode(ByteBuffer.wrap(encoded)).toString();
        } catch (IOException e) {
            return null;
        }
    }

    public static void writeFile(@NotNull String input, String path) {
        Path outPath = Paths.get(path);
        Charset charset = Charset.forName("UTF-8");

        try (BufferedWriter writer = Files.newBufferedWriter(outPath, charset)) {
            writer.write(input, 0, input.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
    private static void writeFile(File javaFile, String targetPath) throws IOException {
        String name = javaFile.getName();
        BufferedReader br = new BufferedReader(new FileReader(javaFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(targetPath+File.separator+name));

        String line;
        while((line=br.readLine())!=null) {
            bw.write(line);
            bw.newLine();
        }
        br.close();
        bw.close();
    }

    public static void moveFiles(String originPath,String targetPath){
        File dir = new File(originPath);
        if(dir.isDirectory()) {
            System.out.println("direct name: "+dir.getName());
            File[] childs = dir.listFiles();
            System.out.println("num of child files: "+childs.length);
            for(File child:childs) {
                moveFiles(child.getAbsolutePath(), targetPath);
            }

        }
        else {
            String fileName = dir.getName();
            if(fileName.endsWith(".txt")) {
                String className = fileName.substring(0,fileName.lastIndexOf("."));
//                writeFile(dir,targetPath);
            }
        }
    }



}
