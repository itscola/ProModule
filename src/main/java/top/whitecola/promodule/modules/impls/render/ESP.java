package top.whitecola.promodule.modules.impls.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import top.whitecola.promodule.ProModule;
import top.whitecola.promodule.annotations.ModuleSetting;
import top.whitecola.promodule.injection.wrappers.IMixinMinecraft;
import top.whitecola.promodule.injection.wrappers.IMixinRenderManager;
import top.whitecola.promodule.injection.wrappers.IMxinEntityRenderer;
import top.whitecola.promodule.modules.AbstractModule;
import top.whitecola.promodule.modules.ModuleCategory;
import top.whitecola.promodule.modules.impls.combat.AimAssist;
import top.whitecola.promodule.modules.impls.combat.AntiBot;
import top.whitecola.promodule.modules.impls.combat.Killaura;
import top.whitecola.promodule.utils.Render2DUtils;
import top.whitecola.promodule.utils.RotationUtils;
import top.whitecola.promodule.utils.Wrapper;

import java.awt.*;
import java.awt.List;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;

import static top.whitecola.promodule.utils.MCWrapper.*;

public class ESP extends AbstractModule {

    @ModuleSetting(name = "AimTarget" ,type="select")
    Boolean aimTarget = true;

    @ModuleSetting(name = "InvPlayer" ,type="select")
    Boolean invPlayer = false;

    @ModuleSetting(name = "AuraESP" ,type="select")
    Boolean auraESP = true;

    @ModuleSetting(name = "NameTags" ,type="select")
    public Boolean nametags = false;

    @ModuleSetting(name = "BrightPlayer" ,type="select")
    public Boolean brightPlayer = false;

    public static Map<EntityLivingBase, double[]> entityPositions = new HashMap<>();

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
    public void onRender(TickEvent.RenderTickEvent e) {

        if(!nametags) return;

        if(Minecraft.getMinecraft()==null||Minecraft.getMinecraft().theWorld==null || mc.thePlayer==null){
            return;
        }

        updatePositions();
        super.onRender(e);
    }


    @Override
    public void onRender2D(float partialTicks) {
        if(!nametags) return;

        if(Minecraft.getMinecraft()==null||Minecraft.getMinecraft().theWorld==null || mc.thePlayer==null){
            return;
        }

        ScaledResolution scaledRes = new ScaledResolution(mc);

        try {
            for (EntityLivingBase ent : entityPositions.keySet()) {

                if (ent != mc.thePlayer && (invPlayer || !ent.isInvisible())) {
                    GlStateManager.pushMatrix();
                    if ((ent instanceof EntityPlayer)) {
                        double[] renderPositions = entityPositions.get(ent);
                        if ((renderPositions[3] < 0.0D) || (renderPositions[3] >= 1.0D)) {
                            GlStateManager.popMatrix();
                            continue;
                        }

                        FontRenderer font = mc.fontRendererObj;

                        GlStateManager.translate(renderPositions[0] / scaledRes.getScaleFactor()+3, renderPositions[1] / scaledRes.getScaleFactor()+3, 0.0D);

                        GlStateManager.scale(0.5, 0.5, 0.5);
                        GlStateManager.translate(0.0D, -2.5D, 0.0D);

                        String str = ent.getName();


                        float allWidth = font.getStringWidth(str.replaceAll("\247.", "")) + 14;

                        Render2DUtils.drawRoundedRect2(-allWidth / 2, -14.0f, allWidth / 2, 0, 1,Render2DUtils.getColor(0, 150));

                        font.drawString(str.replaceAll("\247.", ""), (int)(-allWidth / 2 + 5.5f), -13, color.getRGB());

                        float nowhealth = (float) Math.ceil(ent.getHealth() + ent.getAbsorptionAmount());
                        float maxHealth = ent.getMaxHealth() + ent.getAbsorptionAmount();
                        float healthP = nowhealth / maxHealth;

                        int color = hurtColor.getRGB();
                        String text = ent.getDisplayName().getFormattedText();

                        //Megawalls
                        text = text.replaceAll((text.contains("[") && text.contains("]")) ? "\2477":  "", "");
                        for (int i = 0; i < text.length(); i++) {
                            if ((text.charAt(i) == '\247') && (i + 1 < text.length())) {
                                char oneMore = Character.toLowerCase(text.charAt(i + 1));
                                int colorCode = "0123456789abcdefklmnorg".indexOf(oneMore);
                                if (colorCode < 16) {
                                    try {
                                        color = Render2DUtils.reAlpha(mc.fontRendererObj.getColorCode(oneMore), 1f);
                                    } catch (ArrayIndexOutOfBoundsException ignored) {}
                                }
                            }
                        }

                        Render2DUtils.drawRoundedRect2(-allWidth / 2, -2f, allWidth / 2 - ((allWidth / 2) * (1 - healthP)) * 2, 0, 0.2f,Render2DUtils.reAlpha(color, 0.8f));

                        boolean armors = true;

                        if (armors) {
                            ArrayList<ItemStack> itemsToRender = new ArrayList<ItemStack>();

                            for (int i = 0; i < 5; i++) {
                                ItemStack stack = ent.getEquipmentInSlot(i);
                                if (stack != null) {
                                    itemsToRender.add(stack);
                                }
                            }

                            int x = -(itemsToRender.size() * 9) - 3;

                            for (ItemStack stack : itemsToRender) {

                                GlStateManager.pushMatrix();
                                RenderHelper.enableGUIStandardItemLighting();
                                GlStateManager.disableAlpha();
                                GlStateManager.clear(256);
                                mc.getRenderItem().zLevel = -150.0F;
                                this.fixGlintShit();
                                mc.getRenderItem().renderItemIntoGUI(stack, x + 6, -32);
                                mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, stack, x + 6, -32);
                                mc.getRenderItem().zLevel = 0.0F;
                                x += 6;
                                GlStateManager.enableAlpha();
                                RenderHelper.disableStandardItemLighting();
                                GlStateManager.popMatrix();

                                if (stack != null) {
                                    int y = 0;
                                    int sLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId,
                                            stack);
                                    int fLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId,
                                            stack);
                                    int kLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId,
                                            stack);
                                    if (sLevel > 0) {
                                        drawEnchantTag("Sh" + getColor(sLevel) + sLevel, x, y);
                                        y += font.FONT_HEIGHT - 2;
                                    }
                                    if (fLevel > 0) {
                                        drawEnchantTag("Fir" + getColor(fLevel) + fLevel, x, y);
                                        y += font.FONT_HEIGHT - 2;
                                    }
                                    if (kLevel > 0) {
                                        drawEnchantTag("Kb" + getColor(kLevel) + kLevel, x, y);
                                    } else if ((stack.getItem() instanceof ItemArmor)) {
                                        int pLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId,
                                                stack);
                                        int tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId,
                                                stack);
                                        int uLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId,
                                                stack);
                                        if (pLevel > 0) {
                                            drawEnchantTag("P" + getColor(pLevel) + pLevel, x, y);
                                            y += font.FONT_HEIGHT - 2;
                                        }
                                        if (tLevel > 0) {
                                            drawEnchantTag("Th" + getColor(tLevel) + tLevel, x, y);
                                            y += font.FONT_HEIGHT - 2;
                                        }
                                        if (uLevel > 0) {
                                            drawEnchantTag("Unb" + getColor(uLevel) + uLevel, x, y);
                                        }
                                    } else if ((stack.getItem() instanceof ItemBow)) {
                                        int powLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
                                        int punLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
                                        int fireLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack);

                                        if (powLevel > 0) {
                                            drawEnchantTag("Pow" + getColor(powLevel) + powLevel, x, y);
                                            y += font.FONT_HEIGHT - 2;
                                        }

                                        if (punLevel > 0) {
                                            drawEnchantTag("Pun" + getColor(punLevel) + punLevel, x, y);
                                            y += font.FONT_HEIGHT - 2;
                                        }

                                        if (fireLevel > 0) {
                                            drawEnchantTag("Fir" + getColor(fireLevel) + fireLevel, x, y);
                                        }
                                    } else if (stack.getRarity() == EnumRarity.EPIC) {
                                        drawEnchantTag("\2476\247lGod", x - 0.5f, y + 12);
                                    }
                                    x += 12;
                                }
                            }
                        }
                    }
                    GlStateManager.popMatrix();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        super.onRender2D(partialTicks);
    }



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

    private void fixGlintShit() {
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
    }

    private String getColor(int level) {
        if (level == 2) {
            return "\247a";
        } else if (level == 3) {
            return "\2473";
        } else if (level == 4) {
            return "\2474";
        } else if (level >= 5) {
            return "\2476";
        }
        return "\247f";
    }

    private void drawEnchantTag(String text, float x, float y) {
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        x = (int) (x * 1.05D);
        y -= 6;
        mc.fontRendererObj.drawStringWithShadow(text, x, -44 - y, color.getRGB());
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }

    private void updatePositions() {
        entityPositions.clear();
        float pTicks = Wrapper.getTimer().renderPartialTicks;
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity != mc.thePlayer && entity instanceof EntityPlayer && (!entity.isInvisible() || invPlayer)) {
                double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * pTicks - ((IMixinRenderManager)mc.getRenderManager()).getRenderPosX();
                double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * pTicks - ((IMixinRenderManager)mc.getRenderManager()).getRenderPosY();
                double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * pTicks - ((IMixinRenderManager)mc.getRenderManager()).getRenderPosZ();
                y += entity.height + 0.25d;
                if ((Objects.requireNonNull(convertTo2D(x, y, z))[2] >= 0.0D) && (Objects.requireNonNull(convertTo2D(x, y, z))[2] < 1.0D)) {
                    entityPositions.put((EntityPlayer) entity, new double[]{Objects.requireNonNull(convertTo2D(x, y, z))[0], Objects.requireNonNull(convertTo2D(x, y, z))[1], Math.abs(convertTo2D(x, y + 1.0D, z, entity)[1] - convertTo2D(x, y, z, entity)[1]), Objects.requireNonNull(convertTo2D(x, y, z))[2]});
                }
            }
        }
    }

    private double[] convertTo2D(double x, double y, double z, Entity ent) {
        float pTicks = Wrapper.getTimer().renderPartialTicks;
        float prevYaw = mc.thePlayer.rotationYaw;
        float prevPrevYaw = mc.thePlayer.prevRotationYaw;
        float[] rotations = RotationUtils.getRotationFromPosition(
                ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * pTicks,
                ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * pTicks,
                ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * pTicks - 1.6D);
        mc.getRenderViewEntity().rotationYaw = (mc.getRenderViewEntity().prevRotationYaw = rotations[0]);
        ((IMxinEntityRenderer) Minecraft.getMinecraft().entityRenderer).runSetupCameraTransform(pTicks, 0);
        double[] convertedPoints = convertTo2D(x, y, z);
        mc.getRenderViewEntity().rotationYaw = prevYaw;
        mc.getRenderViewEntity().prevRotationYaw = prevPrevYaw;
        ((IMxinEntityRenderer) Minecraft.getMinecraft().entityRenderer).runSetupCameraTransform(pTicks, 0);
        return convertedPoints;
    }

    private double[] convertTo2D(double x, double y, double z) {
        FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
        IntBuffer viewport = BufferUtils.createIntBuffer(16);
        FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
        FloatBuffer projection = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(2982, modelView);
        GL11.glGetFloat(2983, projection);
        GL11.glGetInteger(2978, viewport);
        boolean result = GLU.gluProject((float) x, (float) y, (float) z, modelView, projection, viewport, screenCoords);
        if (result) {
            return new double[]{screenCoords.get(0), Display.getHeight() - screenCoords.get(1), screenCoords.get(2)};
        }
        return null;
    }

}
