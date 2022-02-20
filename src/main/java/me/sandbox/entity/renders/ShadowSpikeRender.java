package me.sandbox.entity.renders;

import me.sandbox.client.model.ShadowSpikeModel;
import me.sandbox.entity.ShadowSpikeEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ShadowSpikeRender extends GeoEntityRenderer<ShadowSpikeEntity> {

    public ShadowSpikeRender(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ShadowSpikeModel());
    }
    protected void scale(ShadowSpikeEntity entity, MatrixStack matrixStack, float f) {
        matrixStack.scale(2.0f, 2.0f, 2.0f);
    }
}
