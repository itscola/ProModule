package top.whitecola.promodule.config;

import java.io.IOException;
import java.util.ArrayList;

public interface IData<T> {
    void saveConfig();

    void loadConfig() throws IOException;

    void reloadConfig();

    T getConfig();

    void checkConfigs();

    ArrayList<String> checkConfig();

    T getDefConfig();
}
