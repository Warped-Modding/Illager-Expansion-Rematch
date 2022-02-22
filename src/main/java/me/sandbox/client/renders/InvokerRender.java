package me.sandbox.client.renders;

import me.sandbox.entity.InvokerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

    public class InvokerRender extends IllagerEntityRenderer<InvokerEntity> {
        private static final Identifier TEXTURE = new Identifier("sandbox:textures/entity/invoker.png");

        public InvokerRender(EntityRendererFactory.Context context) {

            super(context, new IllagerEntityModel(context.getPart(EntityModelLayers.ILLUSIONER)), 0.5f);
            this.addFeature(new HeldItemFeatureRenderer<InvokerEntity, IllagerEntityModel<InvokerEntity>>((FeatureRendererContext)this){

                @Override
                public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, InvokerEntity invokerEntity, float f, float g, float h, float j, float k, float l) {
                    if (invokerEntity.isSpellcasting()) {
                        super.render(matrixStack, vertexConsumerProvider, i, invokerEntity, f, g, h, j, k, l);
                    }
                    if (invokerEntity.isAttacking()) {
                        super.render(matrixStack, vertexConsumerProvider, i, invokerEntity, f, g, h, j, k, l);
                    }
                }
            });
            ((IllagerEntityModel)this.model).getHat().visible = true;
        }


        @Override
        public Identifier getTexture(InvokerEntity entity) {
            return TEXTURE;
        }
    }
