package top.whitecola.promodule.modules.impls.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.injection.wrappers.IMixinMinecraft;
import top.whitecola.promodule.injection.wrappers.IMixinRenderManager;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.modules.impls.combat.AimAssist;
import top.whitecola.promodule.modules.impls.combat.AntiBot;
import top.whitecola.promodule.modules.impls.combat.Killaura;
import top.whitecola.promodule.utils.Render2DUtils;

import java.awt.*;
import java.util.Vector;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class ESP extends AbstractModule {

    @ModuleSetting(name = "AimTarget" ,type="select")
    Boolean aimTarget = true;

    @ModuleSetting(name = "InvPlayer" ,type="select")
    Boolean invPlayer = true;

    @ModuleSetting(name = "AuraESP" ,type="select")
    Boolean auraESP = true;

//    @ModuleSetting(name = "ESP3D" ,type="select")
//    Boolean eSP3D = false;

//    @ModuleSetting(name = "AllPlaer" ,type="select")
//    Boolean allPlayer = true;

    private Color color = new Color(255, 255, 255);
    private Color hurtColor = new Color(156, 60, 60);
    private Color invColor = new Color(66, 150, 210);

//    private Vector<EntityLivingBase> inv = new Vector<EntityLivingBase>();

    private boolean direction = true;
    private float yPos;
    private long last;

    @Override
    public void onRender3D(int pass, float partialTicks, long finishTimeNano) {
        if(Minecraft.getMinecraft()==null||Minecraft.getMinecraft().theWorld==null || mc.thePlayer==null){
            return;
        }


        if(auraESP){
            Killaura killaura = ProModule.getProModule().getModuleManager().getModuleByClass(Killaura.class);
            if(killaura!=null&&killaura.isEnabled()){
                EntityLivingBase target = killaura.target;
                if(target!=null&&!target.isDead){
                    do3DESP(target,partialTicks);
                }
            }
        }

        if(aimTarget){
            AimAssist aimAssist = (AimAssist) ProModule.getProModule().getModuleManager().getModuleByName("AimAssist");
            if(!(aimAssist==null || !aimAssist.isEnabled())){
                EntityLivingBase entity = aimAssist.getTheTarget();

                if(!(entity==null || entity.isDead)){
//                    if(eSP3D){
//                        do3DESP(entity,partialTicks);
//                    }else{
                        doRenderESP(entity);
//                    }
                }
            }




        }

        if(invPlayer){
            for (final EntityPlayer player : mc.theWorld.playerEntities) {
                if (player != mc.thePlayer) {
                    AntiBot antiBot = (AntiBot) ProModule.getProModule().getModuleManager().getModuleByName("AntiBot");
//                    System.out.println(11111);

                    if(antiBot!=null&&antiBot.isEnabled()){
                        if(antiBot.isBot(player)){
                            return;
                        }
                    }

                    if(!player.isInvisible()){
                        return;
                    }
                    doRenderESP(player);
                }
            }
        }
//        if(invPlayer){
//            for(EntityPlayer player : mc.theWorld.playerEntities){
//                if(){
//
//                }
//            }
//        }



//        if(invPlayer){
//            for (Entity entity : mc.theWorld.playerEntities) {
//                if (!(entity instanceof EntityLivingBase)) {
//                    continue;
//                }
//
//
//                EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
//
//
//            }
//        }

        super.onRender3D(pass, partialTicks, finishTimeNano);
    }


    @Override
    public ModuleCategory getModuleType() {
        return ModuleCategory.RENDERS;
    }

    @Override
    public String getModuleName() {
        return "ESP";
    }






    private void do3DESP(EntityLivingBase target, float partialTicks){
        drawShadow(target, partialTicks, yPos, direction);
        drawCircle(target, partialTicks, yPos);
    }


    @Override
    public void onRender2D(RenderWorldLastEvent e) {
        if(System.currentTimeMillis()-last>=10){
            last = System.currentTimeMillis();

            if (direction) {
                yPos += 0.03;
                if (2 - yPos < 0.02) {
                    direction = false;
                }
            } else {
                yPos -= 0.03;
                if (yPos < 0.02) {
                    direction = true;
                }
            }
        }
        super.onRender2D(e);
    }

    private void drawShadow(Entity entity, float partialTicks, float pos, boolean direction) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel((int) 7425);
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks - mc.getRenderManager().viewerPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks - mc.getRenderManager().viewerPosY + pos;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks - mc.getRenderManager().viewerPosZ;
        GL11.glBegin(GL11.GL_QUAD_STRIP);
        for (int i = 0; i <= 180; i++) {
            double c1 = i * Math.PI * 2 / 180;
            double c2 = (i + 1) * Math.PI * 2 / 180;
            GlStateManager.color(1, 1, 1, 0.3f);
            GL11.glVertex3d(x + 0.5 * Math.cos(c1), y, z + 0.5 * Math.sin(c1));
            GL11.glVertex3d(x + 0.5 * Math.cos(c2), y, z + 0.5 * Math.sin(c2));
            GlStateManager.color(1, 1, 1, 0f);

            GL11.glVertex3d(x + 0.5 * Math.cos(c1), y + (direction ? -0.2 : 0.2), z + 0.5 * Math.sin(c1));
            GL11.glVertex3d(x + 0.5 * Math.cos(c2), y + (direction ? -0.2 : 0.2), z + 0.5 * Math.sin(c2));


        }
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel((int) 7424);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private void drawCircle(Entity entity, float partialTicks, float pos) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel((int) 7425);
        GL11.glLineWidth(1);
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks - mc.getRenderManager().viewerPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks - mc.getRenderManager().viewerPosY + pos;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks - mc.getRenderManager().viewerPosZ;
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (int i = 0; i <= 180; i++) {
            double c1 = i * Math.PI * 2 / 180;
            GlStateManager.color(2, 1, 1, 1);
            GL11.glVertex3d(x + 0.5 * Math.cos(c1), y, z + 0.5 * Math.sin(c1));


        }

        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel((int) 7424);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }





    private void doRenderESP(EntityLivingBase entity){
        if(entity==null){
            return;
        }


        float partialTicks = ((IMixinMinecraft)mc).getTimer().renderPartialTicks;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(2929);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.enableBlend();
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        double x2 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks - ((IMixinRenderManager)mc.getRenderManager()).getRenderPosX();
        double y2 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks - ((IMixinRenderManager)mc.getRenderManager()).getRenderPosY();
        double z2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks - ((IMixinRenderManager)mc.getRenderManager()).getRenderPosZ();
        float DISTANCE = mc.thePlayer.getDistanceToEntity(entity);
        float SCALE = 0.035f;
        GlStateManager.translate((float) x2, (float) y2 + entity.height + 0.5f - (entity.isChild() ? entity.height / 2.0f : 0.0f), (float) z2);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        mc.getRenderManager();
        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glScalef(-SCALE, -SCALE, -(SCALE /= 2.0f));
        double thickness = 1 + DISTANCE * 0.01f;
        double xLeft = -15.0;
        double xRight = 15.0;
        double yUp = 10.0;
        double yDown = 70.0;
        Color color = this.color;
        if (entity.hurtTime > 0) {
            color = this.hurtColor;
        } else if (entity.isInvisible()) {
            color = this.invColor;
        }

        float left = -2;

        //top
        drawBorderedRect((float) xLeft, (float) yUp, (float) xRight , (float) yUp+2, 0.15f, color.getRGB(), color.getRGB());


        //bottom
        drawBorderedRect((float) xLeft, (float) yDown-2, (float) xRight , (float) yDown, 0.15f, color.getRGB(), color.getRGB());


        //left

        drawBorderedRect((float) xLeft - 2.0f - DISTANCE * 0.1f +2, (float) yDown - (float) (yDown - yUp), (float) xLeft - 2.0f + 2 +1 , (float) yDown /2, 0.15f, color.getRGB(), color.getRGB());
        drawBorderedRect((float) xLeft - 2.0f - DISTANCE * 0.1f +2, (float) yDown - (float) (yDown - yUp)/2, (float) xLeft - 2.0f + 2 +1 , (float) yDown, 0.15f, color.getRGB(), color.getRGB());


        //right

        drawBorderedRect((float) xRight - 2.0f - DISTANCE * 0.1f +2, (float) yDown - (float) (yDown - yUp), (float) xRight - 2.0f + 2 +1 , (float) yDown /2, 0.15f, color.getRGB(), color.getRGB());
        drawBorderedRect((float) xRight - 2.0f - DISTANCE * 0.1f +2, (float) yDown - (float) (yDown - yUp)/2, (float) xRight - 2.0f + 2 +1 , (float) yDown, 0.15f, color.getRGB(), color.getRGB());


        drawBorderedRect((float) xLeft - 2.0f - DISTANCE * 0.1f +left, (float) yDown - (float) (yDown - yUp), (float) xLeft - 2.0f + 2 + left, (float) yDown+left, 0.15f, new Color(100, 100, 100).getRGB(), new Color(100, 100, 100).getRGB());
        Color c1 = new Color(Color.HSBtoRGB(mc.thePlayer.ticksExisted / 25f, 0.7f, 1));
        Color c2 = new Color(Color.HSBtoRGB(mc.thePlayer.ticksExisted / 50f, 0.7f, 1));
        Render2DUtils.drawGradientRect((float) xLeft - 2.0f - DISTANCE * 0.1f+left, (float) yDown - (float) (yDown - yUp) * Math.min(1.0f, entity.getHealth() / 20.0f), (float) xLeft - 2.0f +2+left, (float) yDown, c1.getRGB(), c2.getRGB());
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GlStateManager.disableBlend();
        GL11.glDisable(3042);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glNormal3f(1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();


    }




    public static void drawBorderedRect(float x2, float y2, float x22, float y22, float l1, int col1, int col2) {
        ESP.drawRect(x2, y2, x22, y22, col2);
        float f2 = (float) (col1 >> 24 & 255) / 255.0f;
        float f1 = (float) (col1 >> 16 & 255) / 255.0f;
        float f22 = (float) (col1 >> 8 & 255) / 255.0f;
        float f3 = (float) (col1 & 255) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f1, f22, f3, f2);
        GL11.glLineWidth(l1);
        GL11.glBegin(1);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y22);
        GL11.glVertex2d(x22, y22);
        GL11.glVertex2d(x22, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x22, y2);
        GL11.glVertex2d(x2, y22);
        GL11.glVertex2d(x22, y22);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    public static void drawRect(float g2, float h2, float i2, float j2, int col1) {
        float f2 = (float) (col1 >> 24 & 255) / 255.0f;
        float f1 = (float) (col1 >> 16 & 255) / 255.0f;
        float f22 = (float) (col1 >> 8 & 255) / 255.0f;
        float f3 = (float) (col1 & 255) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f1, f22, f3, f2);
        GL11.glBegin(7);
        GL11.glVertex2d(i2, h2);
        GL11.glVertex2d(g2, h2);
        GL11.glVertex2d(g2, j2);
        GL11.glVertex2d(i2, j2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }



}
