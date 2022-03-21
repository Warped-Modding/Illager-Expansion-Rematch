package me.sandbox.client.renders;

import me.sandbox.entity.InquisitorEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class IllagerBruteRender
        extends IllagerEntityRenderer<InquisitorEntity> {
    private static final Identifier TEXTURE = new Identifier("sandbox:textures/entity/illager_brute.png");

    public IllagerBruteRender(EntityRendererFactory.Context context) {
        super(context, new IllagerEntityModel(context.getPart(EntityModelLayers.VINDICATOR)), 0.5f);
        this.addFeature(new HeldItemFeatureRenderer<InquisitorEntity, IllagerEntityModel<InquisitorEntity>>((FeatureRendererContext)this){

            @Override
            public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, InquisitorEntity vindicatorEntity, float f, float g, float h, float j, float k, float l) {
                if (vindicatorEntity.isAttacking()) {
                    super.render(matrixStack, vertexConsumerProvider, i, vindicatorEntity, f, g, h, j, k, l);
                }
            }
        });
    }
    @Override
    protected void scale(InquisitorEntity illagerBruteEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(1.1f, 1.1f, 1.1f);
    }

    @Override
    public Identifier getTexture(InquisitorEntity vindicatorEntity) {
        return TEXTURE;
    }
}
