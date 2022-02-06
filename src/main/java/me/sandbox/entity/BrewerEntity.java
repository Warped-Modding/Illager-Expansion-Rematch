package me.sandbox.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BrewerEntity extends IllagerEntity implements RangedAttackMob {

    public BrewerEntity(EntityType<? extends IllagerEntity> entityType, World world) {
        super(entityType, world);
    }

    private AttributeContainer attributeContainer;
    @Override
    public AttributeContainer getAttributes() {
        if (attributeContainer == null) {
            attributeContainer = new AttributeContainer(HostileEntity.createHostileAttributes()
                    .add(EntityAttributes.GENERIC_MAX_HEALTH, 26.0D)
                    .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0D)
                    .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5D)
                    .build());
        }
        return attributeContainer;
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        Vec3d vec3d = target.getVelocity();
        double d = target.getX() + vec3d.x - this.getX();
        double e = target.getEyeY() - (double)1.1f - this.getY();
        double f = target.getZ() + vec3d.z - this.getZ();
        double g = Math.sqrt(d * d + f * f);
        Potion potion = Potions.SWIFTNESS;
        if (target instanceof IllagerEntity) {
            potion = target.getHealth() <= 4.0f ? Potions.HEALING : Potions.REGENERATION;
            this.setTarget(null);
        } else if (target instanceof IllagerEntity && target.isOnFire()) {
            potion = Potions.FIRE_RESISTANCE;
        }
        PotionEntity potionEntity = new PotionEntity(this.world, this);
        potionEntity.setItem(PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), potion));
        potionEntity.setPitch(potionEntity.getPitch() - -20.0f);
        potionEntity.setVelocity(d, e + g * 0.2, f, 0.75f, 8.0f);
        if (!this.isSilent()) {
            this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0f, 0.8f + this.random.nextFloat() * 0.4f);
        }
        this.world.spawnEntity(potionEntity);
    }

    @Override
    public void addBonusForWave(int wave, boolean unused) {

    }

    @Override
    public SoundEvent getCelebratingSound() {
        return null;
    }
}
