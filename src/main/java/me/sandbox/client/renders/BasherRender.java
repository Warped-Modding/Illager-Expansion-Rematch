package me.sandbox.client.renders;

import me.sandbox.entity.BasherEntity;
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
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class BasherRender
        extends IllagerEntityRenderer<BasherEntity> {
    private static final Identifier TEXTURE = new Identifier("illagerexp:textures/entity/basher.png");

    public BasherRender(EntityRendererFactory.Context context) {
        super(context, new IllagerEntityModel(context.getPart(EntityModelLayers.VINDICATOR)), 0.5f);
        this.addFeature(new HeldItemFeatureRenderer<BasherEntity, IllagerEntityModel<BasherEntity>>((FeatureRendererContext)this, context.getHeldItemRenderer()){

            @Override
            public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, BasherEntity vindicatorEntity, float f, float g, float h, float j, float k, float l) {
                if (vindicatorEntity.isAttacking()) {
                    super.render(matrixStack, vertexConsumerProvider, i, vindicatorEntity, f, g, h, j, k, l);
                }
            }
        });
    }

    @Override
    public Identifier getTexture(BasherEntity vindicatorEntity) {
        return TEXTURE;
    }
}
