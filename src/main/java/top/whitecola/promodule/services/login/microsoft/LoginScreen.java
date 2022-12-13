package top.whitecola.promodule.services.login.microsoft;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.Sys;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class LoginScreen extends GuiScreen {
    public static final Executor EXECUTOR = Executors.newSingleThreadExecutor(r -> new Thread(r,"mslogin"));
    private String title;
    private Consumer<Account> handler;
    private MicrosoftAuthCallback callback = new MicrosoftAuthCallback();
    private GuiTextField username;
    private GuiButton offline;
    private GuiButton microsoft;
    private String state;
    private GuiScreen prev;


    public LoginScreen(GuiScreen prev, String title, Consumer<Account> handler){
        this.prev = prev;
        this.title = title;
        this.handler = handler;
    }


    @Override
    public void initGui() {

        super.initGui();
    }

    @Override
    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {


        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
    }

    @Override
    protected void actionPerformed(GuiButton p_actionPerformed_1_) throws IOException {

        super.actionPerformed(p_actionPerformed_1_);
    }

    protected void loginMS(){
        this.state = "";
        EXECUTOR.execute(() -> {
            this.state = "Check Browser...";
            this.openURI("https://login.live.com/oauth20_authorize.srf?client_id=54fd49e4-2103-4044-9603-2b028c814ec3&response_type=code&scope=XboxLive.signin%20XboxLive.offline_access&redirect_uri=http://localhost:59125&prompt=select_account");
            this.callback.start((s, o) -> {
                this.state = I18n.format(s, o);
            }, I18n.format("ias.loginGui.microsoft.canClose", new Object[0])).whenComplete((acc, t) -> {
                if (mc.currentScreen != this) {
                    return;
                }
                if (t != null) {
//                    mc.addScheduledTask(() -> mc.displayGuiScreen(new IASAlertScreen(() -> mc.displayGuiScreen(this.prev), EnumChatFormatting.RED + I18n.format("ias.error", new Object[0]), String.valueOf(t))));
                    return;
                }
                if (acc == null) {
                    mc.addScheduledTask(() -> mc.displayGuiScreen(this.prev));
                    return;
                }
                mc.addScheduledTask(() -> {
                    this.handler.accept((Account)acc);
//                    mc.displayGuiScreen(this.prev);
                });
            });
        });
    }

    protected void openURI(String uri){
        try {
            Desktop.getDesktop().browse(new URI(uri));
        } catch (Throwable t) {
            Sys.openURL(uri);
        }
    }
}
