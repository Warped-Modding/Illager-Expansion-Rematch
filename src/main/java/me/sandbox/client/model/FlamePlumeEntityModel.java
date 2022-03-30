package me.sandbox.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class FlamePlumeEntityModel<T extends Entity>
        extends SinglePartEntityModel<T> {
    private static final String BASE = "base";
    private final ModelPart root;
    private final ModelPart base;


    public FlamePlumeEntityModel(ModelPart root) {
        this.root = root;
        this.base = root.getChild(BASE);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(BASE, ModelPartBuilder.create().uv(0, 0).cuboid(0.0f, -20.0f, 0.0f, 7.0f, 7.0f, 7.0f), ModelTransform.pivot(-5.0f, 24.0f, -5.0f));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.base.pivotY = 1.0f;
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}

