package com.mcorg.maccaronne.client.resources.model;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

public class ModelResource {

    public static ModelResource CLOUD = new ModelResource("cloud");

    private static List<String> modelNames = new ArrayList<String>();

    static {
        OBJLoader.INSTANCE.addDomain("eodmod");
    }


    IBakedModel model;

    public ModelResource(String resourceLocation) {
        try {
            CustomTextureItem item = new CustomTextureItem();
            ModelResourceLocation obj = new ModelResourceLocation("eodmod:" + resourceLocation, "inventory");
            ItemModelMesher renderItem = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
            renderItem.register(item, 0, obj);
            model = renderItem.getItemModel(item.getDefaultInstance());

            System.out.println("Class: " + model.getClass().getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void renderModel(Entity entity, double offsetX, double offsetY, double offsetZ) {
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        GL11.glPushMatrix();
        GL11.glColor4f(1f, 1f, 1f, 1f);
        GL11.glTranslatef(((float) offsetX), ((float) offsetY), (float) offsetZ);
        GL11.glRotatef(-entity.rotationYaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entity.rotationPitch, 1.0F, 0.0F, 0.0F);

        BlockRendererDispatcher renderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.BLOCK);
        BlockPos pos = new BlockPos(0, 0, 0);
        IBlockState state = entity.world.getBlockState(entity.getPosition());

        renderer.getBlockModelRenderer().renderModel(entity.world, model, state, pos, buffer, false);
        tessellator.draw();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

}
