package me.sandbox.client.renders;

import me.sandbox.entity.FlamecallerEntity;
import me.sandbox.entity.FlamecallerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;


public class FlamecallerRender extends IllagerEntityRenderer<FlamecallerEntity> {
    private static final Identifier TEXTURE = new Identifier("sandbox:textures/entity/flamecaller.png");


    public FlamecallerRender(EntityRendererFactory.Context context) {

        super(context, new IllagerEntityModel(context.getPart(EntityModelLayers.EVOKER)), 0.5f);
        this.addFeature(new HeldItemFeatureRenderer<FlamecallerEntity, IllagerEntityModel<FlamecallerEntity>>((FeatureRendererContext)this){


            @Override
            public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, FlamecallerEntity FlamecallerEntity, float f, float g, float h, float j, float k, float l) {

                if (FlamecallerEntity.isSpellcasting()) {
                    super.render(matrixStack, vertexConsumerProvider, i, FlamecallerEntity, f, g, h, j, k, l);
                }
                if (FlamecallerEntity.isAttacking()) {
                    super.render(matrixStack, vertexConsumerProvider, i, FlamecallerEntity, f, g, h, j, k, l);
                }
            }
        });
        ((IllagerEntityModel)this.model).getHat().visible = true;
    }


    @Override
    public Identifier getTexture(FlamecallerEntity entity) {
        return TEXTURE;
    }
}
