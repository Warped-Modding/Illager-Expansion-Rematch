package me.sandbox.client.renders;

import me.sandbox.client.model.MushroomlingModel;
import me.sandbox.entity.MushroomlingEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.example.client.model.entity.ExampleEntityModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MushroomlingRender extends GeoEntityRenderer<MushroomlingEntity> {

    public MushroomlingRender(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MushroomlingModel());
    }
}