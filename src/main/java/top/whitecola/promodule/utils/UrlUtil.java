package top.whitecola.promodule.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class UrlUtil {
    public static String readURL(String Iurl) throws IOException {
        URL url = new URL(Iurl);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setConnectTimeout(3000);
        con.setDoOutput(true);
        con.setUseCaches(false);
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type","application/json");
        InputStream is = con.getInputStream();
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        while(true)
        {
            int temp = is.read();
            if(temp!=-1)
            {
                bao.write(temp);
            }else{
                break;
            }
        }
        is.close();
        bao.close();
        return new String(bao.toByteArray());


    }
}