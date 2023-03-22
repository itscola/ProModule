package top.whitecola.promodule.modules.impls.other;

import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.modules.AbstractModule;

public class GUIAnim extends AbstractModule {
    @ModuleSetting(name = "ClickGUIAnim" ,type = "select")
    public Boolean ClickGUIAnim = true;
}
