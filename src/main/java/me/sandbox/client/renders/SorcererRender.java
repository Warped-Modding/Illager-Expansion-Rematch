package me.sandbox.client.renders;

import me.sandbox.entity.SorcererEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

    public class SorcererRender extends IllagerEntityRenderer<SorcererEntity> {
        private static final Identifier TEXTURE = new Identifier("sandbox:textures/entity/sorcerer.png");

        public SorcererRender(EntityRendererFactory.Context context) {

            super(context, new IllagerEntityModel(context.getPart(EntityModelLayers.EVOKER)), 0.5f);
            this.addFeature(new HeldItemFeatureRenderer<SorcererEntity, IllagerEntityModel<SorcererEntity>>((FeatureRendererContext) this) {

                @Override
                public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, SorcererEntity SorcererEntity, float f, float g, float h, float j, float k, float l) {
                    if (SorcererEntity.isSpellcasting()) {
                        super.render(matrixStack, vertexConsumerProvider, i, SorcererEntity, f, g, h, j, k, l);
                    }
                }
            });
            ((IllagerEntityModel) this.model).getHat().visible = true;
        }


        @Override
        public Identifier getTexture(SorcererEntity entity) {
            return TEXTURE;
        }
    }
