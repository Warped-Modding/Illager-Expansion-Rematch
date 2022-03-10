package me.sandbox.client.renders;

import me.sandbox.client.ModModelLayers;
import me.sandbox.client.model.InvokerFangsModel;
import me.sandbox.entity.InvokerFangsEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

public class InvokerFangsRenderer
        extends EntityRenderer<InvokerFangsEntity> {
    private static final Identifier TEXTURE = new Identifier("sandbox:textures/entity/invoker_fangs.png");
    private final InvokerFangsModel<InvokerFangsEntity> model;

    public InvokerFangsRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new InvokerFangsModel(context.getPart(ModModelLayers.INVOKER_FANGS));
    }

    @Override
    public void render(InvokerFangsEntity invokerFangs, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        float h = invokerFangs.getAnimationProgress(g);
        if (h == 0.0f) {
            return;
        }
        float j = 2.0f;
        if (h > 0.9f) {
            j = (float)((double)j * ((1.0 - (double)h) / (double)0.1f));
        }
        matrixStack.push();
        matrixStack.scale(1.15f, 1.15f, 1.15f);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0f - invokerFangs.getYaw()));
        matrixStack.scale(-j, -j, j);
        float k = 0.03125f;
        matrixStack.translate(0.0, -0.626f, 0.0);
        matrixStack.scale(0.5f, 0.5f, 0.5f);
        this.model.setAngles(invokerFangs, h, 0.0f, 0.0f, invokerFangs.getYaw(), invokerFangs.getPitch());
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(TEXTURE));
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
        super.render(invokerFangs, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(InvokerFangsEntity InvokerFangs) {
        return TEXTURE;
    }
}
