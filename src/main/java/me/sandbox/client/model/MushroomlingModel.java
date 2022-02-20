package me.sandbox.client.model;

import me.sandbox.Sandbox;
import me.sandbox.entity.MushroomlingEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MushroomlingModel extends AnimatedGeoModel<MushroomlingEntity>
{
    @Override
    public Identifier getModelLocation(MushroomlingEntity object)
    {
        return new Identifier(Sandbox.MOD_ID, "geo/mushroomling.geo.json");
    }

    @Override
    public Identifier getTextureLocation(MushroomlingEntity object)
    {
        return new Identifier(Sandbox.MOD_ID, "textures/entity/mushroomling.png");
    }

    @Override
    public Identifier getAnimationFileLocation(MushroomlingEntity object)
    {
        return new Identifier(Sandbox.MOD_ID, "animations/mushroomling.animations.json");
    }
}
