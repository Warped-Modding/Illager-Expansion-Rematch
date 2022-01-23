package me.sandbox.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import me.sandbox.Sandbox;
import me.sandbox.entity.custom.LostMinerEntity;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LostMinerRenderer extends SkeletonRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Sandbox.MOD_ID, "textures/entity/lost_miner.png");

    public LostMinerRenderer(EntityRendererProvider.Context p_174380_) {
        super(p_174380_, ModelLayers.SKELETON, ModelLayers.SKELETON_INNER_ARMOR, ModelLayers.SKELETON_OUTER_ARMOR);
    }

    public ResourceLocation getTextureLocation(AbstractSkeleton p_116458_) {
        return TEXTURE;
    }
    protected void scale(AbstractSkeleton p_21640, PoseStack p_21641, float p_21642) {
    p_21641.scale(0.9F, 0.9F, 0.9F);}
}