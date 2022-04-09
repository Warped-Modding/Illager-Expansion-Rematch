package me.sandbox.mixin;

import me.sandbox.entity.BasherEntity;
import me.sandbox.entity.MarauderEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IllagerEntityModel.class)
public class IllagerModelMixin<T extends IllagerEntity>{


    @Shadow @Final private ModelPart rightArm;

    @Shadow @Final private ModelPart head;

    @Shadow @Final private ModelPart leftArm;

    @Inject(at = @At("TAIL"), cancellable = true, method = "setAngles(Lnet/minecraft/entity/mob/IllagerEntity;FFFFF)V")
    public void chargingAnimation(T illagerEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        ItemStack item = illagerEntity.getMainHandStack();
        Arm mainArm= illagerEntity.getMainArm();
        if (illagerEntity instanceof BasherEntity) {
            IllagerEntity.State state = illagerEntity.getState();
            if (state == IllagerEntity.State.ATTACKING && item.isOf(Items.SHIELD) && !(((BasherEntity) illagerEntity).isStunned) && mainArm == Arm.RIGHT) {
                this.rightArm.pitch = this.rightArm.pitch * 0.5f + 0.05424779f;
                this.rightArm.yaw = -0.5235988f;

            }
            if (state == IllagerEntity.State.ATTACKING && item.isOf(Items.SHIELD) && !(((BasherEntity) illagerEntity).isStunned) && mainArm == Arm.LEFT) {
                this.leftArm.pitch = this.leftArm.pitch * 0.5f - 0.9424779f;
                this.leftArm.yaw = 0.5235988f;
            }
            if (((BasherEntity)illagerEntity).getStunnedState()) {
                this.head.pitch = 20.35f;
                this.head.yaw = MathHelper.cos(h * 0.8f) * 0.3f;
                this.rightArm.pitch = -0.25f;
                this.leftArm.pitch = -0.25f;
            }
        }
        if (illagerEntity instanceof MarauderEntity) {
            if (mainArm == Arm.RIGHT) {
                if (((MarauderEntity) illagerEntity).isCharging()) {
                    this.rightArm.pitch = 3.7699115f;
                }
                if (!illagerEntity.isAttacking()) {
                    this.rightArm.pitch = MathHelper.cos(f * 0.6662f + (float) Math.PI) * 2.0f * g * 0.5f;
                }
            } else {
                if (((MarauderEntity) illagerEntity).isCharging()) {
                    this.leftArm.pitch = 3.7699115f;
                }
                if (!illagerEntity.isAttacking()) {
                    this.leftArm.pitch = MathHelper.cos(f * 0.6662f) * 2.0f * g * 0.5f;
                }
            }
        }
    }
}

