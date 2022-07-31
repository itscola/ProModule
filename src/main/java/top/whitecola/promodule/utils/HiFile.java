package top.whitecola.promodule.utils;

import java.io.*;
import java.nio.charset.Charset;

public class HiFile {
    public static byte[] readFile(File file){
        if(!file.isFile()){
           return null;
        }
        try{
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[fis.available()];
            fis.read(data);
            fis.close();
            return data;
        }catch (IOException e){
            return new byte[0];
        }
    }


    public static void writeFile(byte[] data,File file,boolean append) throws IOException{
        //appendΪfalseʱ �����ļ�

        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if(!file.isFile()){
            file.createNewFile();
        }
            OutputStream ops = new FileOutputStream(file,append);
            ops.write(data);
            ops.flush();
            ops.close();
    }

    public static void writeTextToFile(String content,File file,boolean append,Charset charset) throws IOException{
        writeFile(content.getBytes(charset),file,append);
    }


    public static String readTextFromFile(File file, Charset charset){
        return new String(readFile(file),charset);
    }

    


}
