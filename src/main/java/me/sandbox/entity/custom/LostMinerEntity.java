package me.sandbox.entity.custom;

import me.sandbox.entity.ModEntityTypes;
import me.sandbox.util.ModSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;


public class LostMinerEntity extends AbstractSkeleton {


    public LostMinerEntity(EntityType<? extends LostMinerEntity> entityType, Level world) {
        super(entityType, world);
    }
    public static AttributeSupplier.Builder CreateAttributes() {
        return Skeleton.createAttributes()
                .add(Attributes.MAX_HEALTH, 26F)
                .add(Attributes.ATTACK_DAMAGE, 6.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    protected void populateDefaultEquipmentSlots(DifficultyInstance p_34172_) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_PICKAXE));
    }

    @Override
    protected SoundEvent getStepSound() {
        return SoundEvents.SKELETON_STEP;
    }

    protected SoundEvent getDeathSound() {
    return ModSoundEvents.LOST_MINER_DEATH.get();
    }
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return ModSoundEvents.LOST_MINER_HURT.get();
    }
    protected SoundEvent getAmbientSound() {
    return ModSoundEvents.LOST_MINER_AMBIENT.get();
    }
}
