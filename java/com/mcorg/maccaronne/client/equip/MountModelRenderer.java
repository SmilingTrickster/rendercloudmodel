package com.mcorg.maccaronne.client.equip;

import com.mcorg.maccaronne.client.resources.model.ModelResource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class MountModelRenderer {

    private static Map<String, String> mountModels = new HashMap<String, String>();

    static {
        mountModels.put("铁马冰河", "cloud");
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderLivingEvent.Pre<EntityCreature> event) {
        EntityLivingBase entity = event.getEntity();
        String name = entity.getName();
        String mountModelName = getMountModelName(name);
        if (mountModelName != null) {
            event.setCanceled(true);
            renderingMounts.put((EntityHorse) entity, mountModelName);
        }
    }

    String getMountModelName(String horseName) {
        for (Map.Entry<String, String> entry : mountModels.entrySet()) {
            if (horseName.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    private Map<EntityHorse, String> renderingMounts = new HashMap<EntityHorse, String>();

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        float partialTicks = event.getPartialTicks();
        for (Map.Entry<EntityHorse, String> renderingPlayer : renderingMounts.entrySet()) {
            renderHorseMount(renderingPlayer.getKey(), renderingPlayer.getValue(), partialTicks);
        }
        renderingMounts.clear();
    }

    private void renderHorseMount(EntityHorse entity, String model, float partialTicks) {
        Minecraft minecraft = Minecraft.getMinecraft();
        RenderManager renderManager = minecraft.getRenderManager();
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks + entity.height;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;

        float xpos = (float) (x - renderManager.viewerPosX);
        float ypos = (float) (y - renderManager.viewerPosY + 1);
        float zpos = (float) (z - renderManager.viewerPosZ);
        ModelResource.CLOUD.renderModel(entity, xpos, ypos, zpos);

    }

}
