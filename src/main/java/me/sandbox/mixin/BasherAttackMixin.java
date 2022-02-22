package me.sandbox.mixin;

import me.sandbox.entity.BasherEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IllagerEntityModel.class)
public class BasherAttackMixin<T extends IllagerEntity>{


    @Shadow @Final private ModelPart rightArm;

    @Inject(at = @At("TAIL"), cancellable = true, method = "setAngles(Lnet/minecraft/entity/mob/IllagerEntity;FFFFF)V")
    public void chargingAnimation(T illagerEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        ItemStack item = illagerEntity.getMainHandStack();
        if (illagerEntity instanceof BasherEntity) {
            IllagerEntity.State state = ((IllagerEntity)illagerEntity).getState();
            if (state == IllagerEntity.State.ATTACKING && item.isOf(Items.SHIELD)) {
                this.rightArm.pitch = 200.0f;
                this.rightArm.yaw = -0.5235988f;
            }
        }
    }
}

