package top.whitecola.promodule.config.struct;

import java.util.Vector;

public class NowConfig {

    public Vector<ConfigStruct> structs = new Vector<ConfigStruct>();
    public class ConfigStruct{
        public String fileName;
    }
}
