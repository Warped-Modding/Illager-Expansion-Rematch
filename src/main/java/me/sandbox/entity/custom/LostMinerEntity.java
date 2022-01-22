package me.sandbox.entity.custom;

import me.sandbox.entity.ModEntityTypes;
import me.sandbox.util.ModSoundEvents;
import net.minecraft.client.audio.Sound;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;


public class LostMinerEntity extends AbstractSkeletonEntity {


    public LostMinerEntity(EntityType<? extends LostMinerEntity> entityType, World world) {
        super(entityType, world);
    }
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return AbstractSkeletonEntity.func_234295_eP_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 26.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 6.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.STONE_PICKAXE));
    }

    @Override
    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_SKELETON_STEP;
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
