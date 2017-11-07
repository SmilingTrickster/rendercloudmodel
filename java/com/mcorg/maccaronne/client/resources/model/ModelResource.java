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

//        for (EnumFacing enumfacing : EnumFacing.values()) {
//            List<BakedQuad> list = model.getQuads(state, enumfacing, 0);
//            if (!list.isEmpty()) {
//                this.renderQuadsSmooth(worldIn, stateIn, posIn, buffer, list, afloat, bitset, blockmodelrenderer$ambientocclusionface);
//            }
//        }

        renderer.getBlockModelRenderer().renderModel(entity.world, model, state, pos, buffer, false);
        tessellator.draw();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }
//
//
//    private void renderQuadsSmooth(IBlockAccess blockAccessIn, IBlockState stateIn, BlockPos posIn, VertexBuffer buffer, List<BakedQuad> list, float[] quadBounds, BitSet bitSet, BlockModelRenderer.AmbientOcclusionFace aoFace) {
//        double d0 = (double) posIn.getX() ;
//        double d1 = (double) posIn.getY() ;
//        double d2 = (double) posIn.getZ();
//        int i = 0;
//
//        for (int j = list.size(); i < j; ++i) {
//            BakedQuad bakedquad = (BakedQuad) list.get(i);
//            this.fillQuadBounds(stateIn, bakedquad.getVertexData(), bakedquad.getFace(), quadBounds, bitSet);
//            aoFace.updateVertexBrightness(blockAccessIn, stateIn, posIn, bakedquad.getFace(), quadBounds, bitSet);
//            buffer.addVertexData(bakedquad.getVertexData());
//            buffer.putBrightness4(aoFace.vertexBrightness[0], aoFace.vertexBrightness[1], aoFace.vertexBrightness[2], aoFace.vertexBrightness[3]);
//            if (bakedquad.shouldApplyDiffuseLighting()) {
//                float diffuse = net.minecraftforge.client.model.pipeline.LightUtil.diffuseLight(bakedquad.getFace());
//                aoFace.vertexColorMultiplier[0] *= diffuse;
//                aoFace.vertexColorMultiplier[1] *= diffuse;
//                aoFace.vertexColorMultiplier[2] *= diffuse;
//                aoFace.vertexColorMultiplier[3] *= diffuse;
//            }
//            if (bakedquad.hasTintIndex()) {
//                int k = this.blockColors.colorMultiplier(stateIn, blockAccessIn, posIn, bakedquad.getTintIndex());
//
//                if (EntityRenderer.anaglyphEnable) {
//                    k = TextureUtil.anaglyphColor(k);
//                }
//
//                float f = (float) (k >> 16 & 255) / 255.0F;
//                float f1 = (float) (k >> 8 & 255) / 255.0F;
//                float f2 = (float) (k & 255) / 255.0F;
//                buffer.putColorMultiplier(aoFace.vertexColorMultiplier[0] * f, aoFace.vertexColorMultiplier[0] * f1, aoFace.vertexColorMultiplier[0] * f2, 4);
//                buffer.putColorMultiplier(aoFace.vertexColorMultiplier[1] * f, aoFace.vertexColorMultiplier[1] * f1, aoFace.vertexColorMultiplier[1] * f2, 3);
//                buffer.putColorMultiplier(aoFace.vertexColorMultiplier[2] * f, aoFace.vertexColorMultiplier[2] * f1, aoFace.vertexColorMultiplier[2] * f2, 2);
//                buffer.putColorMultiplier(aoFace.vertexColorMultiplier[3] * f, aoFace.vertexColorMultiplier[3] * f1, aoFace.vertexColorMultiplier[3] * f2, 1);
//            } else {
//                buffer.putColorMultiplier(aoFace.vertexColorMultiplier[0], aoFace.vertexColorMultiplier[0], aoFace.vertexColorMultiplier[0], 4);
//                buffer.putColorMultiplier(aoFace.vertexColorMultiplier[1], aoFace.vertexColorMultiplier[1], aoFace.vertexColorMultiplier[1], 3);
//                buffer.putColorMultiplier(aoFace.vertexColorMultiplier[2], aoFace.vertexColorMultiplier[2], aoFace.vertexColorMultiplier[2], 2);
//                buffer.putColorMultiplier(aoFace.vertexColorMultiplier[3], aoFace.vertexColorMultiplier[3], aoFace.vertexColorMultiplier[3], 1);
//            }
//
//            buffer.putPosition(d0, d1, d2);
//        }
//    }
//
//    private void fillQuadBounds(IBlockState stateIn, int[] vertexData, EnumFacing face, @Nullable float[] quadBounds, BitSet boundsFlags)
//    {
//        float f = 32.0F;
//        float f1 = 32.0F;
//        float f2 = 32.0F;
//        float f3 = -32.0F;
//        float f4 = -32.0F;
//        float f5 = -32.0F;
//
//        for (int i = 0; i < 4; ++i)
//        {
//            float f6 = Float.intBitsToFloat(vertexData[i * 7]);
//            float f7 = Float.intBitsToFloat(vertexData[i * 7 + 1]);
//            float f8 = Float.intBitsToFloat(vertexData[i * 7 + 2]);
//            f = Math.min(f, f6);
//            f1 = Math.min(f1, f7);
//            f2 = Math.min(f2, f8);
//            f3 = Math.max(f3, f6);
//            f4 = Math.max(f4, f7);
//            f5 = Math.max(f5, f8);
//        }
//
//        if (quadBounds != null)
//        {
//            quadBounds[EnumFacing.WEST.getIndex()] = f;
//            quadBounds[EnumFacing.EAST.getIndex()] = f3;
//            quadBounds[EnumFacing.DOWN.getIndex()] = f1;
//            quadBounds[EnumFacing.UP.getIndex()] = f4;
//            quadBounds[EnumFacing.NORTH.getIndex()] = f2;
//            quadBounds[EnumFacing.SOUTH.getIndex()] = f5;
//            int j = EnumFacing.values().length;
//            quadBounds[EnumFacing.WEST.getIndex() + j] = 1.0F - f;
//            quadBounds[EnumFacing.EAST.getIndex() + j] = 1.0F - f3;
//            quadBounds[EnumFacing.DOWN.getIndex() + j] = 1.0F - f1;
//            quadBounds[EnumFacing.UP.getIndex() + j] = 1.0F - f4;
//            quadBounds[EnumFacing.NORTH.getIndex() + j] = 1.0F - f2;
//            quadBounds[EnumFacing.SOUTH.getIndex() + j] = 1.0F - f5;
//        }
//
//        float f9 = 1.0E-4F;
//        float f10 = 0.9999F;
//
//        switch (face)
//        {
//            case DOWN:
//                boundsFlags.set(1, f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F);
//                boundsFlags.set(0, (f1 < 1.0E-4F || stateIn.isFullCube()) && f1 == f4);
//                break;
//            case UP:
//                boundsFlags.set(1, f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F);
//                boundsFlags.set(0, (f4 > 0.9999F || stateIn.isFullCube()) && f1 == f4);
//                break;
//            case NORTH:
//                boundsFlags.set(1, f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F);
//                boundsFlags.set(0, (f2 < 1.0E-4F || stateIn.isFullCube()) && f2 == f5);
//                break;
//            case SOUTH:
//                boundsFlags.set(1, f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F);
//                boundsFlags.set(0, (f5 > 0.9999F || stateIn.isFullCube()) && f2 == f5);
//                break;
//            case WEST:
//                boundsFlags.set(1, f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F);
//                boundsFlags.set(0, (f < 1.0E-4F || stateIn.isFullCube()) && f == f3);
//                break;
//            case EAST:
//                boundsFlags.set(1, f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F);
//                boundsFlags.set(0, (f3 > 0.9999F || stateIn.isFullCube()) && f == f3);
//        }
//    }
}
