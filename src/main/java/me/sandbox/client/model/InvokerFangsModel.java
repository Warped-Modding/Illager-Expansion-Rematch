package me.sandbox.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class InvokerFangsModel<T extends Entity>
        extends SinglePartEntityModel<T> {
    private static final String BASE = "base";
    private static final String UPPER_JAW = "upper_jaw";
    private static final String LOWER_JAW = "lower_jaw";
    private static final String SPIKE1 = "spike1";
    private static final String SPIKE2 = "spike2";
    private static final String SPIKE3 = "spike3";
    private static final String SPIKE4 = "spike4";
    private final ModelPart root;
    private final ModelPart base;
    private final ModelPart upperJaw;
    private final ModelPart lowerJaw;
    private final ModelPart spike1;
    private final ModelPart spike2;
    private final ModelPart spike3;
    private final ModelPart spike4;


    public InvokerFangsModel(ModelPart root) {
        this.root = root;
        this.base = root.getChild(BASE);
        this.upperJaw = root.getChild(UPPER_JAW);
        this.lowerJaw = root.getChild(LOWER_JAW);
        this.spike1 = root.getChild(SPIKE1);
        this.spike2= root.getChild(SPIKE2);
        this.spike3= root.getChild(SPIKE3);
        this.spike4= root.getChild(SPIKE4);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartBuilder modelPartBuilder2 = ModelPartBuilder.create().uv(100, 0).cuboid(-0.2F, 0.0F, -0.1F, 6.0f, 25.0f, 0.0f);
        modelPartData.addChild(BASE, ModelPartBuilder.create().uv(0, 0).cuboid(0.0f, 0.0f, 0.0f, 10.0f, 12.0f, 10.0f), ModelTransform.pivot(-5.0f, 24.0f, -5.0f));
        modelPartData.addChild(SPIKE1, modelPartBuilder2, ModelTransform.pivot(-0.2f, 0.0f, -0.1f));
        modelPartData.addChild(SPIKE2, modelPartBuilder2, ModelTransform.pivot(-0.2f, 0.0f, -0.1f));
        modelPartData.addChild(SPIKE3, modelPartBuilder2, ModelTransform.pivot(-0.2f, 0.0f, -0.1f));
        modelPartData.addChild(SPIKE4, modelPartBuilder2, ModelTransform.pivot(-0.2f, 0.0f, -0.1f));
        ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(40, 0).cuboid(0.0f, 0.0f, 0.0f, 4.0f, 14.0f, 8.0f);
        modelPartData.addChild(UPPER_JAW, modelPartBuilder, ModelTransform.pivot(1.5f, 24.0f, -4.0f));
        modelPartData.addChild(LOWER_JAW, modelPartBuilder, ModelTransform.of(-1.5f, 24.0f, 4.0f, 0.0f, (float)Math.PI, 0.0f));
        return TexturedModelData.of(modelData, 128, 64);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float f = limbAngle * 2.0f;
        if (f > 1.0f) {
            f = 1.0f;
        }
        f = 1.0f - f * f * f;
        this.upperJaw.roll = -MathHelper.cos(f * 1.8f) - 8.95f * 1.0688f;
        this.lowerJaw.roll = MathHelper.cos(f * 1.8f) + 8.95f * 1.0688f;
        float g = (limbAngle + MathHelper.sin(limbAngle * 2.7f)) * 0.6f * 12.0f;
        this.spike1.yaw = 1f;
        this.spike2.yaw = -1f;
        this.spike3.yaw = -2f;
        this.spike4.yaw = 2f;
        this.lowerJaw.pivotY = this.upperJaw.pivotY = 24.0f - g;
        this.base.pivotY = this.upperJaw.pivotY;
        this.spike1.pivotY = this.upperJaw.pivotY*MathHelper.cos(g/4.0f) + 1.6f;
        this.spike2.pivotY = this.upperJaw.pivotY*MathHelper.cos(g/4.0f) + 1.6f;
        this.spike3.pivotY = this.upperJaw.pivotY*MathHelper.cos(g/4.0f) + 1.6f;
        this.spike4.pivotY = this.upperJaw.pivotY*MathHelper.cos(g/4.0f) + 1.6f;
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}

