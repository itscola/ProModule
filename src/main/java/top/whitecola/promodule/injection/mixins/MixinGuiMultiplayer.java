package top.whitecola.promodule.injection.mixins;

import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.whitecola.promodule.gui.components.clickables.buttons.LongRectButton;
import top.whitecola.promodule.services.login.microsoft.LoginScreen;
import top.whitecola.promodule.utils.AuthUtils;

import java.io.IOException;

@Mixin(GuiMultiplayer.class)
public abstract class MixinGuiMultiplayer extends GuiScreen {
    @Shadow private boolean deletingServer;

    @Shadow public abstract void connectToSelected();

    @Shadow private boolean directConnect;

    @Shadow private boolean addingServer;

    @Shadow private boolean editingServer;

    @Shadow private ServerData selectedServer;

    @Shadow private GuiScreen parentScreen;

    @Shadow protected abstract void refreshServerList();

    @Shadow private ServerSelectionList serverListSelector;

    @Inject(method = "createButtons", at = { @At("HEAD") }, cancellable = true)
    public void createButtons(CallbackInfo ci){
        this.buttonList.add(new LongRectButton(17,4,4,98, 20,"Alt Login"));
    }

    /**
     * @author White_cola
     * @reason microsoft login
     */
    @Overwrite
    protected void actionPerformed(GuiButton p_actionPerformed_1_) throws IOException {
        if (p_actionPerformed_1_.enabled) {
            GuiListExtended.IGuiListEntry guilistextended$iguilistentry = this.serverListSelector.func_148193_k() < 0 ? null : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());
            if (p_actionPerformed_1_.id == 2 && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
                String s4 = ((ServerListEntryNormal)guilistextended$iguilistentry).getServerData().serverName;
                if (s4 != null) {
                    this.deletingServer = true;
                    String s = I18n.format("selectServer.deleteQuestion", new Object[0]);
                    String s1 = "'" + s4 + "' " + I18n.format("selectServer.deleteWarning", new Object[0]);
                    String s2 = I18n.format("selectServer.deleteButton", new Object[0]);
                    String s3 = I18n.format("gui.cancel", new Object[0]);
                    GuiYesNo guiyesno = new GuiYesNo(this, s, s1, s2, s3, this.serverListSelector.func_148193_k());
                    this.mc.displayGuiScreen(guiyesno);
                }
            } else if (p_actionPerformed_1_.id == 1) {
                this.connectToSelected();
            } else if (p_actionPerformed_1_.id == 4) {
                this.directConnect = true;
                this.mc.displayGuiScreen(new GuiScreenServerList(this, this.selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "", false)));
            } else if (p_actionPerformed_1_.id == 3) {
                this.addingServer = true;
                this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "", false)));
            } else if (p_actionPerformed_1_.id == 7 && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
                this.editingServer = true;
                ServerData serverdata = ((ServerListEntryNormal)guilistextended$iguilistentry).getServerData();
                this.selectedServer = new ServerData(serverdata.serverName, serverdata.serverIP, false);
                this.selectedServer.copyFrom(serverdata);
                this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.selectedServer));
            } else if (p_actionPerformed_1_.id == 0) {
                this.mc.displayGuiScreen(this.parentScreen);
            } else if (p_actionPerformed_1_.id == 8) {
                this.refreshServerList();
            }else if(p_actionPerformed_1_.id == 17){
//                this.mc.displayGuiScreen(new LoginScreen(this,"Alt Manager",""));
            }
        }

    }
}
