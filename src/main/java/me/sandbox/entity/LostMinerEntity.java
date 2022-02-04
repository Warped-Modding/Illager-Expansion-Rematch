package me.sandbox.entity;

import me.sandbox.sounds.SoundRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;

public class LostMinerEntity extends SkeletonEntity {
    private AttributeContainer attributeContainer;
    @Override
    public AttributeContainer getAttributes() {
        if (attributeContainer == null) {
            attributeContainer = new AttributeContainer(HostileEntity.createHostileAttributes()
                    .add(EntityAttributes.GENERIC_MAX_HEALTH, 26.0D)
                    .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0D)
                    .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.32D)
                    .build());
        }
        return attributeContainer;
    }

    public LostMinerEntity(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.LOST_MINER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundRegistry.LOST_MINER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.LOST_MINER_DEATH;
    }


    @Override
    protected void initEquipment(LocalDifficulty difficulty) {
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_PICKAXE));
    }


}