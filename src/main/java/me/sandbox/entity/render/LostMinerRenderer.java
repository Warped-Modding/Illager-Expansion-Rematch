package me.sandbox.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.sandbox.Sandbox;
import me.sandbox.entity.custom.LostMinerEntity;
import me.sandbox.entity.model.LostMinerModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.util.ResourceLocation;

public class LostMinerRenderer extends SkeletonRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Sandbox.MOD_ID, "textures/entity/lost_miner.png");

    public LostMinerRenderer(EntityRendererManager rendererManager) {
        super(rendererManager);
    }

    @Override
    protected void preRenderCallback(AbstractSkeletonEntity abstractSkeletonEntity, MatrixStack matrixStack, float v) {
        if (abstractSkeletonEntity instanceof LostMinerEntity) {
            float scaleFactor = 0.9F;
            matrixStack.scale(scaleFactor, scaleFactor, scaleFactor);
        }
        super.preRenderCallback(abstractSkeletonEntity, matrixStack, v);
    }

    public ResourceLocation getEntityTexture(AbstractSkeletonEntity abstractSkeletonEntity) {
        if (abstractSkeletonEntity instanceof LostMinerEntity) {
            return TEXTURE;
        } else {
            return super.getEntityTexture(abstractSkeletonEntity);
        }
    }
}