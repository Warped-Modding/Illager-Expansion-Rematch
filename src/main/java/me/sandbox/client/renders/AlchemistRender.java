package me.sandbox.client.renders;

import me.sandbox.client.ModModelLayers;
import me.sandbox.client.model.ArmoredIllagerEntityModel;
import me.sandbox.client.model.BrimmedHatIllagerEntityModel;
import me.sandbox.entity.AlchemistEntity;
import me.sandbox.entity.AlchemistEntity;
import me.sandbox.entity.ProvokerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class AlchemistRender extends MobEntityRenderer<AlchemistEntity, BrimmedHatIllagerEntityModel<AlchemistEntity>>
{
    private static final Identifier TEXTURE;

    public AlchemistRender(final EntityRendererFactory.Context context) {
        super(context, new BrimmedHatIllagerEntityModel<>(context.getPart(ModModelLayers.BRIM_HAT_ILLAGER)), 0.5f);
        this.addFeature(new HeldItemFeatureRenderer<AlchemistEntity, BrimmedHatIllagerEntityModel<AlchemistEntity>>((FeatureRendererContext)this){

            @Override
            public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AlchemistEntity provokerEntity, float f, float g, float h, float j, float k, float l) {
                if (provokerEntity.isAttacking()) {
                    super.render(matrixStack, vertexConsumerProvider, i, provokerEntity, f, g, h, j, k, l);
                }
            }
        });
        this.model.getHat().visible = true;
    }

    protected void scale(final AlchemistEntity AlchemistEntity, final MatrixStack matrixStack, final float f) {
        matrixStack.scale(0.95f, 0.95f, 0.95f);
    }

    public Identifier getTexture(final AlchemistEntity entity) {
        return AlchemistRender.TEXTURE;
    }

    static {
        TEXTURE = new Identifier("illagerexp:textures/entity/alchemist.png");
    }
}
