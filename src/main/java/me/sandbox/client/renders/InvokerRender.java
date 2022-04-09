package me.sandbox.client.renders;

import me.sandbox.client.ModModelLayers;
import me.sandbox.client.model.InvokerEntityModel;
import me.sandbox.client.renders.feature.InvokerShieldFeatureRender;
import me.sandbox.entity.InvokerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class InvokerRender extends MobEntityRenderer<InvokerEntity, InvokerEntityModel<InvokerEntity>> {
        private static final Identifier TEXTURE = new Identifier("illagerexp:textures/entity/caped_invoker.png");

        public InvokerRender(EntityRendererFactory.Context context) {
            super(context, new InvokerEntityModel<>(context.getPart(ModModelLayers.CAPED_ILLAGER)), 0.0f);
            this.addFeature(new InvokerShieldFeatureRender(this, context.getModelLoader()));
            this.model.getHat().visible = true;
        }
        @Override
        public void render(InvokerEntity invokerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
            float z = 0.02f * 3.8f % 10;
            float q = MathHelper.sin((float)(invokerEntity).age * z) * 1.5f * ((float)Math.PI / 180);
            matrixStack.translate(0.0f, 0.3+q, 0.0f);
            matrixStack.scale(0.95f, 0.95f, 0.95f);
            super.render(invokerEntity, f, g, matrixStack, vertexConsumerProvider, i);
        }

        @Override
        public Identifier getTexture(InvokerEntity entity) {
                return TEXTURE;
            }
        }
