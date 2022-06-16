package me.sandbox.client.renders;

import me.sandbox.entity.InvokerEntity;
import me.sandbox.entity.SurrenderedEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.VexEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;


public class SurrenderedRender extends SkeletonEntityRenderer {
    private static final Identifier TEXTURE = new Identifier("illagerexp:textures/entity/surrendered.png");
    private static final Identifier CHARGE_TEXTURE = new Identifier("illagerexp:textures/entity/surrendered_charge.png");


    public SurrenderedRender(EntityRendererFactory.Context context) {
        super(context, EntityModelLayers.SKELETON, EntityModelLayers.SKELETON_INNER_ARMOR, EntityModelLayers.SKELETON_OUTER_ARMOR);
    }
    @Override
    protected void scale(AbstractSkeletonEntity abstractSkeletonEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.85f, 0.85f, 0.85f);
    }

    @Override
    public Identifier getTexture(AbstractSkeletonEntity abstractSkeletonEntity) {
        if (((SurrenderedEntity)abstractSkeletonEntity).isCharging()) {
            return CHARGE_TEXTURE;
        }
        return TEXTURE;
    }

}

