package top.whitecola.promodule.modules;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.events.impls.event.PacketReceivedEvent;
import top.whitecola.promodule.events.impls.event.WorldRenderEvent;
import top.whitecola.promodule.gui.components.clickables.buttons.ClickGUISubEntry;
import top.whitecola.promodule.gui.components.clickables.wrapper.Setting;
import top.whitecola.promodule.gui.widgets.AbstractWidget;
import top.whitecola.promodule.utils.ClientUtils;

import java.lang.reflect.Field;
import java.util.Vector;

public class AbstractModule implements IModule{
    Vector<ModuleOption> options = new Vector<ModuleOption>();
    protected boolean enabled;
    protected AbstractWidget widget;
    private AbstractModule module;


    @Override
    public void onTick() {

    }

    @Override
    public void onRender2D(RenderWorldLastEvent e) {

    }

    @Override
    public void onRender(TickEvent.RenderTickEvent e) {

    }

    @Override
    public void onRenderOverLay(RenderGameOverlayEvent event) {

    }

    @Override
    public void onEntityJoinWorld(EntityJoinWorldEvent e) {

    }


    @Override
    public void onEnable() {
        ClientUtils.sendAClientMessage(this.getModuleName(),"");
    }

    @Override
    public void onDisable() {
        ClientUtils.sendAClientMessage(this.getModuleName(),"");
    }

    @Override
    public void enable() {
        this.enabled = true;
        onEnable();
    }

    @Override
    public void disable() {
        this.enabled = false;
        onDisable();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void renderGameOverlayRETURN() {

    }

    @Override
    public void addOption(ModuleOption option) {
        options.add(option);
    }

    @Override
    public void removeOption(ModuleOption option) {
        options.remove(option);
    }

    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.COMBAT;
    }

    @Override
    public String getModuleName() {
        return "Null";
    }

    @Override
    public void optionEnable(String optionName) {
//        getOptionByName(optionName).
    }

    @Override
    public void optionDisable(String optionName) {

    }





    public ModuleOption getOptionByName(String optionName) {

        return null;
    }

    @Override
    public void onAttackEntity(AttackEntityEvent e) {

    }

    @Override
    public void onWordRender(RenderWorldEvent e) {

    }

    @Override
    public void onLoginIn(FMLNetworkEvent.ClientConnectedToServerEvent e) {

    }

    @Override
    public void onLoginOut(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {

    }

    @Override
    public void onChatReceive(ClientChatReceivedEvent e) {

    }

    @Override
    public void removeWidget(AbstractWidget widget) {
        ProModule.getProModule().getWidgetManager().removeWidget(widget);
    }

    @Override
    public void addWidget(AbstractWidget widget) {
        ProModule.getProModule().getWidgetManager().addWidget(widget);

    }


    @Deprecated
    @Override
    public void onLivingHurt(LivingHurtEvent e) {

    }

    public void setWidget(AbstractWidget widget) {
        this.widget = widget;
    }

    public AbstractWidget getWidget() {
        return widget;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public void onLivingAttack(LivingAttackEvent e) {

    }



    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {

    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent e) {

    }

    @Override
    public void onPlayerClickBlock(BlockPos p_clickBlock_1_, EnumFacing p_clickBlock_2_) {

    }

    @Override
    public void onRenderPlayer(RenderPlayerEvent.Post e) {

    }

    @Override
    public void onRenderOverLayPre(RenderGameOverlayEvent.Pre event) {

    }

    @Override
    public void onRender3D(int pass, float partialTicks, long finishTimeNano) {

    }

    @Override
    public void onModuleSettingChanged(String name) {

    }

    @Override
    public void packetReceivedEvent(PacketReceivedEvent e) {

    }

    @Override
    public void worldRenderEvent(WorldRenderEvent e) {

    }


    public Vector<ModuleOption> getOptions() {
        return options;
    }

    public void setOptions(Vector<ModuleOption> options) {
        this.options = options;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if(enabled){
            onEnable();
        }else{
            onDisable();
        }
    }


    public Object getSetting(String name){
        Field[] fields = this.getClass().getFields();
        for(Field field : fields){
            if(field.isAnnotationPresent(ModuleSetting.class)){
                if(field.getAnnotation(ModuleSetting.class).name().equalsIgnoreCase(name)){
                    try {
                        return field.get(this);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    public String getDisplayName(){
        return this.getModuleName();
    }


    public boolean toogleBooleanSetting(ClickGUISubEntry entry){
        Setting setting = getSettingField(entry.getEntryName());
        if(setting==null){
            return false;
        }
        if(setting.getModuleSetting().type().equalsIgnoreCase("select")){

            try {
                setting.getField().set(this,!entry.isEnabled());
                entry.setEnabled(!entry.isEnabled());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
            return true;

        }
        return false;
    }

    public void toogleBooleanSetting(String settingname){
        Boolean value = getBooleanSetting(settingname);
        if(value==null){
            return;
        }

        setBooleanSetting(settingname,!value);
    }


    public Boolean getBooleanSetting(String settingname){
        Setting setting = getSettingField(settingname);
        if(setting==null){
            return null;
        }
        if(setting.getModuleSetting().type().equalsIgnoreCase("select")){
            try {
                return (Boolean) setting.getField().get(this);
            } catch (IllegalAccessException e) {
                return null;
            }
        }
        return null;
    }



    public void setBooleanSetting(String settingname,Boolean value){
        Setting setting = getSettingField(settingname);
        if(setting==null){
            return;
        }
        if(setting.getModuleSetting().type().equalsIgnoreCase("select")){
            try {
                setting.getField().set(this,value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return;
    }

    public Float getFloatSetting(String settingname){
        Setting setting = getSettingField(settingname);
        if(setting==null){
            return null;
        }

        try {
            return (Float) setting.getField().get(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setFloatSetting(String settingname,Float value){
        Setting setting = getSettingField(settingname);
        if(setting==null){
            return;
        }

        if(value==null){
            return;
        }

        try {
            setting.getField().set(this,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Setting getSettingField(String settingName) {
        for (Field field : this.getClass().getFields()) {
            if (field.isAnnotationPresent(ModuleSetting.class)) {
                ModuleSetting moduleSetting = field.getAnnotation(ModuleSetting.class);
                if (moduleSetting.name().equalsIgnoreCase(settingName)) {
                    return new Setting(field,moduleSetting);
                }
            }
        }
        return null;
    }

    public Vector<Setting> getSettings() {
        Vector<Setting> settings = new Vector<Setting>();

        for (Field field : this.getClass().getFields()) {
            if (field.isAnnotationPresent(ModuleSetting.class)) {
                ModuleSetting moduleSetting = field.getAnnotation(ModuleSetting.class);
                settings.add(new Setting(field,moduleSetting));
            }
        }
        return settings;
    }

}
