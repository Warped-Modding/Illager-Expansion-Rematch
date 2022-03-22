package me.sandbox.client.renders;

import me.sandbox.client.ModModelLayers;
import me.sandbox.client.model.ArmoredIllagerEntityModel;
import me.sandbox.entity.InquisitorEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class InquisitorRender extends MobEntityRenderer<InquisitorEntity, ArmoredIllagerEntityModel<InquisitorEntity>>
{
    private static final Identifier TEXTURE;

    public InquisitorRender(final EntityRendererFactory.Context context) {
        super(context, new ArmoredIllagerEntityModel(context.getPart(ModModelLayers.ARMORED_ILLAGER)), 0.5f);
        this.addFeature(new HeldItemFeatureRenderer(this));
        this.model.getHat().visible = false;
    }

    protected void scale(final InquisitorEntity inquisitorEntity, final MatrixStack matrixStack, final float f) {
        matrixStack.scale(1.1f, 1.1f, 1.1f);
    }

    public Identifier getTexture(final InquisitorEntity entity) {
        return InquisitorRender.TEXTURE;
    }

    static {
        TEXTURE = new Identifier("illagerexp:textures/entity/inquisitor.png");
    }
}
