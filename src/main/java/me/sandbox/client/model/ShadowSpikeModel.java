package me.sandbox.client.model;

import me.sandbox.Sandbox;
import me.sandbox.entity.ShadowSpikeEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShadowSpikeModel extends AnimatedGeoModel<ShadowSpikeEntity>
{
    @Override
    public Identifier getModelLocation(ShadowSpikeEntity object)
    {
        return new Identifier(Sandbox.MOD_ID, "geo/shadow_spike.geo.json");
    }

    @Override
    public Identifier getTextureLocation(ShadowSpikeEntity object)
    {
        return new Identifier(Sandbox.MOD_ID, "textures/entity/shadow_spike.png");
    }

    @Override
    public Identifier getAnimationFileLocation(ShadowSpikeEntity object)
    {
        return new Identifier(Sandbox.MOD_ID, "animations/shadow_spike.animations.json");
    }
}
