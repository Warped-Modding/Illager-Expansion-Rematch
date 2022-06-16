package me.sandbox.client.model;

import me.sandbox.entity.InquisitorEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class ArmoredIllagerEntityModel<T extends IllagerEntity> extends SinglePartEntityModel<T> implements ModelWithArms, ModelWithHead
{
    private static final String LEFT_SHOULDERPAD = "left_shoulderpad";
    private static final String RIGHT_SHOULDERPAD = "right_shoulderpad";
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart hat;
    private final ModelPart arms;
    private final ModelPart body;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart leftShoulderpad;
    private final ModelPart rightShoulderpad;

    public ArmoredIllagerEntityModel(final ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.hat = this.head.getChild("hat");
        this.body = root.getChild("body");
        this.hat.visible = false;
        this.arms = root.getChild("arms");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");
        this.rightShoulderpad = root.getChild("right_shoulderpad");
        this.leftShoulderpad = root.getChild("left_shoulderpad");
    }

    public static TexturedModelData getTexturedModelData() {
        final ModelData modelData = new ModelData();
        final ModelPartData modelPartData = modelData.getRoot();
        final ModelPartData modelPartData2 = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f), ModelTransform.pivot(0.0f, 0.0f, 0.0f));
        modelPartData2.addChild("hat", ModelPartBuilder.create().uv(32, 0).cuboid(-4.0f, -10.0f, -4.0f, 8.0f, 12.0f, 8.0f, new Dilation(0.45f)), ModelTransform.NONE);
        modelPartData2.addChild("nose", ModelPartBuilder.create().uv(24, 0).cuboid(-1.0f, -1.0f, -6.0f, 2.0f, 4.0f, 2.0f), ModelTransform.pivot(0.0f, -2.0f, 0.0f));
        modelPartData.addChild("body", ModelPartBuilder.create().uv(16, 20).cuboid(-4.0f, 0.0f, -3.0f, 8.0f, 12.0f, 6.0f).uv(0, 38).cuboid(-4.0f, 0.0f, -3.0f, 8.0f, 18.0f, 6.0f, new Dilation(0.5f)), ModelTransform.pivot(0.0f, 0.0f, 0.0f));
        final ModelPartData modelPartData3 = modelPartData.addChild("arms", ModelPartBuilder.create().uv(44, 22).cuboid(-8.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f).uv(40, 38).cuboid(-4.0f, 2.0f, -2.0f, 8.0f, 4.0f, 4.0f), ModelTransform.of(0.0f, 3.0f, -1.0f, -0.75f, 0.0f, 0.0f));
        modelPartData3.addChild("left_shoulder", ModelPartBuilder.create().uv(44, 22).mirrored().cuboid(4.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f), ModelTransform.NONE);
        modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(0, 22).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f), ModelTransform.pivot(-2.0f, 12.0f, 0.0f));
        modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(0, 22).mirrored().cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f), ModelTransform.pivot(2.0f, 12.0f, 0.0f));
        modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(40, 46).cuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f), ModelTransform.pivot(-5.0f, 2.0f, 0.0f));
        modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(40, 46).mirrored().cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f), ModelTransform.pivot(5.0f, 2.0f, 0.0f));
        modelPartData.addChild("left_shoulderpad", ModelPartBuilder.create().uv(0, 64).mirrored().cuboid(-0.7f, -2.0f, -2.0f, 4.0f, 4.0f, 4.0f, new Dilation(1.1f)), ModelTransform.pivot(5.0f, 2.0f, 0.0f));
        modelPartData.addChild("right_shoulderpad", ModelPartBuilder.create().uv(0, 64).cuboid(-3.3f, -2.0f, -2.0f, 4.0f, 4.0f, 4.0f, new Dilation(1.1f)), ModelTransform.pivot(-5.0f, 2.0f, 0.0f));
        return TexturedModelData.of(modelData, 64, 128);
    }

    public ModelPart getPart() {
        return this.root;
    }

    public void setAngles(final T illagerEntity, final float f, final float g, final float h, final float i, final float j) {
        this.head.yaw = i * 0.017453292f;
        this.head.pitch = j * 0.017453292f;
        if (this.riding) {
            this.rightArm.pitch = -0.62831855f;
            this.rightArm.yaw = 0.0f;
            this.rightArm.roll = 0.0f;
            this.leftArm.pitch = -0.62831855f;
            this.leftArm.yaw = 0.0f;
            this.leftArm.roll = 0.0f;
            this.rightLeg.pitch = -1.4137167f;
            this.rightLeg.yaw = 0.31415927f;
            this.rightLeg.roll = 0.07853982f;
            this.leftLeg.pitch = -1.4137167f;
            this.leftLeg.yaw = -0.31415927f;
            this.leftLeg.roll = -0.07853982f;
            this.rightShoulderpad.pitch = -0.62831855f;
            this.leftShoulderpad.pitch = -0.62831855f;
        }
        else {
            this.rightArm.pitch = MathHelper.cos(f * 0.6662f + 3.1415927f) * 2.0f * g * 0.5f;
            this.rightShoulderpad.pitch = MathHelper.cos(f * 0.6662f + 3.1415927f) * 2.0f * g * 0.5f;
            this.rightShoulderpad.yaw = 0.0f;
            this.rightArm.yaw = 0.0f;
            this.rightArm.roll = 0.0f;
            this.leftArm.pitch = MathHelper.cos(f * 0.6662f) * 2.0f * g * 0.5f;
            this.leftShoulderpad.pitch = MathHelper.cos(f * 0.6662f) * 2.0f * g * 0.5f;
            this.leftShoulderpad.yaw = 0.0f;
            this.leftArm.yaw = 0.0f;
            this.leftArm.roll = 0.0f;
            this.rightLeg.pitch = MathHelper.cos(f * 0.6662f) * 1.4f * g * 0.5f;
            this.rightLeg.yaw = 0.0f;
            this.rightLeg.roll = 0.0f;
            this.leftLeg.pitch = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * g * 0.5f;
            this.leftLeg.yaw = 0.0f;
            this.leftLeg.roll = 0.0f;
        }
        final IllagerEntity.State state = illagerEntity.getState();
        final Arm mainArm = illagerEntity.getMainArm();
        final boolean hasShield = illagerEntity.getOffHandStack().isOf(Items.SHIELD);
        if (state == IllagerEntity.State.ATTACKING) {
            if (((LivingEntity)illagerEntity).getMainHandStack().isEmpty()) {
                CrossbowPosing.meleeAttack(this.leftArm, this.rightArm, true, this.handSwingProgress, h);
                CrossbowPosing.meleeAttack(this.leftShoulderpad, this.rightShoulderpad, true, this.handSwingProgress, h);
            }
            else {
                CrossbowPosing.meleeAttack(this.rightArm, this.leftArm, (MobEntity)illagerEntity, this.handSwingProgress, h);
                CrossbowPosing.meleeAttack(this.rightShoulderpad, this.leftShoulderpad, (MobEntity)illagerEntity, this.handSwingProgress, h);
            }
            if (hasShield && mainArm == Arm.RIGHT) {
                this.leftArm.pitch = -1.05f;
                this.leftArm.yaw = 0.5235988f;
                this.leftShoulderpad.pitch = -1.05f;
                this.leftShoulderpad.yaw = 0.5235988f;
            }
            if (hasShield && mainArm == Arm.LEFT) {
                this.rightArm.pitch = 200.0f;
                this.rightArm.yaw = -0.5235988f;
                this.rightShoulderpad.pitch = 200.0f;
                this.rightShoulderpad.yaw = -0.5235988f;
            }
            if (((InquisitorEntity)illagerEntity).getStunnedState()) {
                this.head.pitch = 18.5f;
                this.head.yaw = MathHelper.cos(h * 0.8f) * 0.3f;
                this.rightArm.pitch = 200.0f;
                this.rightArm.yaw = 0.5235988f;
                this.rightShoulderpad.pitch = 200.0f;
                this.rightShoulderpad.yaw = 0.5235988f;
                this.leftArm.pitch = 200.0f;
                this.leftArm.yaw = -0.5235988f;
                this.leftShoulderpad.pitch = 200.0f;
                this.leftShoulderpad.yaw = -0.5235988f;
            }
        }
        else if (state == IllagerEntity.State.SPELLCASTING) {
            this.rightArm.pivotZ = 0.0f;
            this.rightArm.pivotX = -5.0f;
            this.leftArm.pivotZ = 0.0f;
            this.leftArm.pivotX = 5.0f;
            this.rightArm.pitch = MathHelper.cos(h * 0.6662f) * 0.25f;
            this.leftArm.pitch = MathHelper.cos(h * 0.6662f) * 0.25f;
            this.rightArm.roll = 2.3561945f;
            this.leftArm.roll = -2.3561945f;
            this.rightArm.yaw = 0.0f;
            this.leftArm.yaw = 0.0f;
        }
        else if (state == IllagerEntity.State.BOW_AND_ARROW) {
            this.rightArm.yaw = -0.1f + this.head.yaw;
            this.rightArm.pitch = -1.5707964f + this.head.pitch;
            this.leftArm.pitch = -0.9424779f + this.head.pitch;
            this.leftArm.yaw = this.head.yaw - 0.4f;
            this.leftArm.roll = 1.5707964f;
        }
        else if (state == IllagerEntity.State.CROSSBOW_HOLD) {
            CrossbowPosing.hold(this.rightArm, this.leftArm, this.head, true);
        }
        else if (state == IllagerEntity.State.CROSSBOW_CHARGE) {
            CrossbowPosing.charge(this.rightArm, this.leftArm, (LivingEntity)illagerEntity, true);
        }
        else if (state == IllagerEntity.State.CELEBRATING) {
            this.rightArm.pivotZ = 0.0f;
            this.rightArm.pivotX = -5.0f;
            this.rightArm.pitch = MathHelper.cos(h * 0.6662f) * 0.05f;
            this.rightArm.roll = 2.670354f;
            this.rightArm.yaw = 0.0f;
            this.rightShoulderpad.pivotZ = 0.0f;
            this.rightShoulderpad.pivotX = -5.0f;
            this.rightShoulderpad.pitch = MathHelper.cos(h * 0.6662f) * 0.05f;
            this.rightShoulderpad.roll = 2.670354f;
            this.rightShoulderpad.yaw = 0.0f;
            this.leftArm.pivotZ = 0.0f;
            this.leftArm.pivotX = 5.0f;
            this.leftArm.pitch = MathHelper.cos(h * 0.6662f) * 0.05f;
            this.leftArm.roll = -2.3561945f;
            this.leftArm.yaw = 0.0f;
            this.leftShoulderpad.pivotZ = 0.0f;
            this.leftShoulderpad.pivotX = 5.0f;
            this.leftShoulderpad.pitch = MathHelper.cos(h * 0.6662f) * 0.05f;
            this.leftShoulderpad.roll = -2.3561945f;
            this.leftShoulderpad.yaw = 0.0f;
        }
        final boolean bl = this.arms.visible = (state == IllagerEntity.State.CROSSED);
        this.leftArm.visible = !bl;
        this.rightArm.visible = !bl;
    }

    private ModelPart getAttackingArm(final Arm arm) {
        if (arm == Arm.LEFT) {
            return this.leftArm;
        }
        return this.rightArm;
    }

    public ModelPart getHat() {
        return this.hat;
    }

    public ModelPart getHead() {
        return this.head;
    }

    public void setArmAngle(final Arm arm, final MatrixStack matrices) {
        this.getAttackingArm(arm).rotate(matrices);
    }
}
