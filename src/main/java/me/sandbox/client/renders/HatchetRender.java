package me.sandbox.client.renders;

import me.sandbox.entity.projectile.HatchetEntity;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;

public class HatchetRender extends FlyingItemEntityRenderer<HatchetEntity> {
    private final ItemRenderer itemRenderer;

    public HatchetRender(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();

    }
    @Override
    public void render (HatchetEntity hatchetEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
            matrixStack.scale(1.45f, 1.45f, 1.45f);
            float age = hatchetEntity.getAgeException();
            matrixStack.push();
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(g, hatchetEntity.prevYaw, hatchetEntity.getYaw())-270.0f));
            matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(g, hatchetEntity.prevPitch, hatchetEntity.getPitch()) + 90.0f * age));
            matrixStack.translate(0.1f, -0.2f, 0.0f);
            BakedModel hatchetmodel = MinecraftClient.getInstance().getItemRenderer().getModels().getModel(hatchetEntity.getStack());
            this.itemRenderer.renderItem(hatchetEntity.getStack(), ModelTransformation.Mode.GROUND, false, matrixStack, vertexConsumerProvider, light, OverlayTexture.DEFAULT_UV, hatchetmodel);
            matrixStack.pop();
    }
}
