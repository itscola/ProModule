package top.whitecola.promodule.config;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.whitecola.promodule.utils.HiFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class HiConfig<T> implements IData<T>{
    public File file;
    public T config;
    public Class<T> configClass;
    public Charset charset;
    public Constructor<T> cc;
    public Gson g = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();

    public HiConfig(String path, Class<T> configClass, Charset charset){
        this.file = new File(path);
        this.configClass = configClass;
        try {
            this.cc = configClass.getConstructor(new Class<?>[0]);
            this.cc.setAccessible(true);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        this.charset = charset;
        try {
            loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void saveConfig() {
        if(!this.file.getParentFile().exists()){
            this.file.getParentFile().mkdirs();
        }
        try {
            HiFile.writeTextToFile(g.toJson(this.config,this.configClass),this.file,false,this.charset);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    @Override
    public void loadConfig() throws IOException {
        if(this.file.getParentFile().exists()&&file.isFile()){
            this.config =  g.fromJson(HiFile.readTextFromFile(file,charset), this.configClass);
            if(this.config ==null){
                config = getDefConfig();
            }
        }else{
            config = getDefConfig();
            saveConfig();
        }
    }

    @Override
    public T getDefConfig(){
        try {
            return this.cc.newInstance(new Object[0]);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public ArrayList<String> checkConfig(){
        ArrayList<String> al = new ArrayList<String>();
        for(Field f : this.config.getClass().getFields()){
            try {
                Object obj = f.get(this.config);
                if(obj==null){
                    al.add(f.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return al;
    }

    @Override
    public void reloadConfig() {
        try {
            this.loadConfig();
            this.saveConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkConfigs(){
        ArrayList<String> al = checkConfig();
    }

    @Override
    public T getConfig(){
        checkConfigs();
        return this.config;
    }
}
