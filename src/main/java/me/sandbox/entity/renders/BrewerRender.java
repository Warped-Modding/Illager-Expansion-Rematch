package me.sandbox.entity.renders;

import me.sandbox.entity.BrewerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.PillagerEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.util.Identifier;

public class BrewerRender extends IllagerEntityRenderer<BrewerEntity> {
    private static final Identifier TEXTURE = new Identifier("sandbox:textures/entity/brewer.png");

    public BrewerRender(EntityRendererFactory.Context ctx, IllagerEntityModel<BrewerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Override
    public Identifier getTexture(BrewerEntity entity) {
        return TEXTURE;
    }
}