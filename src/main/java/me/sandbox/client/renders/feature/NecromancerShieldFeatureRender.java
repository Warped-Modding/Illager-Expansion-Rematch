package me.sandbox.client.renders.feature;

import net.minecraft.util.math.MathHelper;
import me.sandbox.client.ModModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import me.sandbox.entity.NecromancerEntity;
import net.minecraft.client.render.entity.feature.EnergySwirlOverlayFeatureRenderer;

@Environment(EnvType.CLIENT)
public class NecromancerShieldFeatureRender extends EnergySwirlOverlayFeatureRenderer<NecromancerEntity, IllagerEntityModel<NecromancerEntity>>
{
    private static final Identifier SKIN;
    private final IllagerEntityModel<NecromancerEntity> model;

    public NecromancerShieldFeatureRender(final FeatureRendererContext<NecromancerEntity, IllagerEntityModel<NecromancerEntity>> context, final EntityModelLoader loader) {
        super(context);
        this.model = new IllagerEntityModel<NecromancerEntity>(loader.getModelPart(ModModelLayers.NECROMANCER_SHIELD));
    }

    protected float getEnergySwirlX(final float partialAge) {
        return MathHelper.cos(partialAge * 0.2f) * 0.2f;
    }

    protected Identifier getEnergySwirlTexture() {
        return NecromancerShieldFeatureRender.SKIN;
    }

    protected IllagerEntityModel<NecromancerEntity> getEnergySwirlModel() {
        return this.model;
    }

    static {
        SKIN = new Identifier("illagerexp:textures/entity/necromancer_armor1.png");
    }
}