package me.sandbox.client.renders;

import me.sandbox.entity.ProvokerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ProvokerRender extends IllagerEntityRenderer<ProvokerEntity> {
    private static final Identifier TEXTURE = new Identifier("sandbox:textures/entity/provoker.png");

    public ProvokerRender(EntityRendererFactory.Context context) {

        super(context, new IllagerEntityModel(context.getPart(EntityModelLayers.ILLUSIONER)), 0.5f);
        this.addFeature(new HeldItemFeatureRenderer<ProvokerEntity, IllagerEntityModel<ProvokerEntity>>((FeatureRendererContext)this){

            @Override
            public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, ProvokerEntity provokerEntity, float f, float g, float h, float j, float k, float l) {
                if (provokerEntity.isAttacking() || provokerEntity.isSpellcasting()) {
                    super.render(matrixStack, vertexConsumerProvider, i, provokerEntity, f, g, h, j, k, l);
                }
            }
        });
        ((IllagerEntityModel)this.model).getHat().visible = true;
    }


    @Override
    public Identifier getTexture(ProvokerEntity entity) {
        return TEXTURE;
    }
}