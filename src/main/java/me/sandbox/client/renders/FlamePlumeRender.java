package me.sandbox.client.renders;

import me.sandbox.client.ModModelLayers;
import me.sandbox.client.model.FlamePlumeEntityModel;
import me.sandbox.entity.FlamePlumeEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import me.sandbox.client.ModModelLayers;
import me.sandbox.entity.FlamePlumeEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;

public class FlamePlumeRender
        extends EntityRenderer<FlamePlumeEntity> {
    private static final Identifier TEXTURE = new Identifier("illagerexp:textures/entity/flame_plume.png");
    private final FlamePlumeEntityModel<FlamePlumeEntity> model;

    public FlamePlumeRender(EntityRendererFactory.Context context) {
        super(context);
        this.model = new FlamePlumeEntityModel<>(context.getPart(ModModelLayers.FLAME_PLUME));
    }

    @Override
    public void render(FlamePlumeEntity FlamePlume, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(TEXTURE));
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
        super.render(FlamePlume, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(FlamePlumeEntity FlamePlume) {
        return TEXTURE;
    }
}

