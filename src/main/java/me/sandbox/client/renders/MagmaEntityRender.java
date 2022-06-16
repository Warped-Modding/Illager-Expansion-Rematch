package me.sandbox.client.renders;

import me.sandbox.entity.projectile.MagmaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

import java.util.Random;

@Environment(value= EnvType.CLIENT)
public class MagmaEntityRender
        extends EntityRenderer<MagmaEntity> {
    public MagmaEntityRender(EntityRendererFactory.Context context) {
        super(context);
        this.shadowRadius = 0.5f;
    }

    @Override
    public void render(MagmaEntity MagmaEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(g, MagmaEntity.prevYaw, MagmaEntity.getYaw())));
        matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(g, MagmaEntity.prevPitch, MagmaEntity.getPitch())));
        BlockRenderManager blockRenderManager = MinecraftClient.getInstance().getBlockRenderManager();
        blockRenderManager.renderBlockAsEntity(Blocks.MAGMA_BLOCK.getDefaultState(), matrixStack, vertexConsumerProvider, i, OverlayTexture.DEFAULT_UV);
        matrixStack.pop();
        super.render(MagmaEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(MagmaEntity MagmaEntity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
