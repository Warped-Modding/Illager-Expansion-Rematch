package me.sandbox.client.renders.feature;

import me.sandbox.client.ModModelLayers;
import me.sandbox.client.model.InvokerEntityModel;
import me.sandbox.entity.InvokerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.feature.EnergySwirlOverlayFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(value= EnvType.CLIENT)
public class InvokerShieldFeatureRender
        extends EnergySwirlOverlayFeatureRenderer<InvokerEntity, InvokerEntityModel<InvokerEntity>> {
    private static final Identifier SKIN = new Identifier("textures/entity/wither/wither_armor.png");
    private final InvokerEntityModel<InvokerEntity> model;

    public InvokerShieldFeatureRender(FeatureRendererContext<InvokerEntity, InvokerEntityModel<InvokerEntity>> context, EntityModelLoader loader) {
        super(context);
        this.model = new InvokerEntityModel<InvokerEntity>(loader.getModelPart(ModModelLayers.INVOKER_SHIELD));
    }

    @Override
    protected float getEnergySwirlX(float partialAge) {
        return MathHelper.cos(partialAge * 0.02f) * 3.0f;
    }

    @Override
    protected Identifier getEnergySwirlTexture() {
        return SKIN;
    }

    @Override
    protected InvokerEntityModel<InvokerEntity> getEnergySwirlModel() {
        return this.model;
    }
}
