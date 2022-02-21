package me.sandbox.entity;

import me.sandbox.sounds.SoundRegistry;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SorcererEntity
        extends SpellcastingIllagerEntity {
    @Nullable
    private SheepEntity wololoTarget;
    private int cooldown;
    private int buffcooldown;
    private int damagedelay = 60;
    private boolean isLevitating;

    public SorcererEntity(EntityType<? extends SorcererEntity> entityType, World world) {
        super((EntityType<? extends SpellcastingIllagerEntity>)entityType, world);
        this.experiencePoints = 10;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SorcererEntity.LookAtTargetOrWololoTarget());
        this.goalSelector.add(3, new LevitateTargetsGoal());
        this.goalSelector.add(4, new FireResistanceGoal());
        this.goalSelector.add(8, new WanderAroundGoal(this, 0.6));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0f, 1.0f));
        this.goalSelector.add(5, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0f, 0.6, 1.0));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0f));
        this.targetSelector.add(1, new RevengeGoal(this, RaiderEntity.class).setGroupRevenge(new Class[0]));
        this.targetSelector.add(2, new ActiveTargetGoal<PlayerEntity>((MobEntity)this, PlayerEntity.class, true).setMaxTimeWithoutVisibility(300));
        this.targetSelector.add(3, new ActiveTargetGoal<MerchantEntity>((MobEntity)this, MerchantEntity.class, false).setMaxTimeWithoutVisibility(300));
        this.targetSelector.add(3, new ActiveTargetGoal<IronGolemEntity>((MobEntity)this, IronGolemEntity.class, false));
    }
    private AttributeContainer attributeContainer;
    @Override
    public AttributeContainer getAttributes() {
        if (attributeContainer == null) {
            attributeContainer = new AttributeContainer(HostileEntity.createHostileAttributes()
                    .add(EntityAttributes.GENERIC_MAX_HEALTH, 28.0D)
                    .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.38D)
                    .build());
        }
        return attributeContainer;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }

    @Override
    public SoundEvent getCelebratingSound() {
        return SoundRegistry.SORCERER_AMBIENT;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    private List<LivingEntity> getTargets() {
        return world.getEntitiesByClass(LivingEntity.class, getBoundingBox().expand(50), entity -> !(entity instanceof IllagerEntity) && !(entity instanceof SurrenderedEntity) && !(entity instanceof RavagerEntity) && entity.hasStatusEffect(StatusEffects.LEVITATION));
    }
    private void doDamage(LivingEntity entity) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1, 0));
        double x = entity.getX();
        double y = entity.getY()+1;
        double z = entity.getZ();
        entity.pushAwayFrom(SorcererEntity.this);
        ((ServerWorld)world).spawnParticles(ParticleTypes.WITCH,x, y,z,45,0.4D, 0.4D,0.4D,0.03D);
    }

    @Override
    protected void mobTick() {
        --cooldown;
        --buffcooldown;
        if (isLevitating) {
            --SorcererEntity.this.damagedelay;
            if (damagedelay <= 0) {
                getTargets().forEach(this::doDamage);
                ((ServerWorld)world).spawnParticles(ParticleTypes.WITCH,this.getX(), this.getY()+1.5,this.getZ(),45,0.4D, 0.4D,0.4D,0.03D);
                isLevitating = false;
                damagedelay = 60;
            }
        }
        super.mobTick();
    }

    @Override
    public boolean isTeammate(Entity other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (super.isTeammate(other)) {
            return true;
        }
        if (other instanceof VexEntity) {
            return this.isTeammate(((VexEntity)other).getOwner());
        }
        if (other instanceof LivingEntity && ((LivingEntity)other).getGroup() == EntityGroup.ILLAGER) {
            return this.getScoreboardTeam() == null && other.getScoreboardTeam() == null;
        }
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.SORCERER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.SORCERER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundRegistry.SORCERER_HURT;
    }

    void setWololoTarget(@Nullable SheepEntity sheep) {
        this.wololoTarget = sheep;
    }

    @Nullable
    SheepEntity getWololoTarget() {
        return this.wololoTarget;
    }

    @Override
    protected SoundEvent getCastSpellSound() {
        return SoundRegistry.SORCERER_COMPLETE_CAST;
    }

    @Override
    public void addBonusForWave(int wave, boolean unused) {
    }
    @Override
    public IllagerEntity.State getState() {
        if (this.isSpellcasting()) {
            return IllagerEntity.State.SPELLCASTING;
        }
        return IllagerEntity.State.CROSSED;
    }

    class LookAtTargetOrWololoTarget
            extends SpellcastingIllagerEntity.LookAtTargetGoal {

        @Override
        public void tick() {
            if (SorcererEntity.this.getTarget() != null) {
                SorcererEntity.this.getLookControl().lookAt(SorcererEntity.this.getTarget(), SorcererEntity.this.getMaxHeadRotation(), SorcererEntity.this.getMaxLookPitchChange());
            } else if (SorcererEntity.this.getWololoTarget() != null) {
                SorcererEntity.this.getLookControl().lookAt(SorcererEntity.this.getWololoTarget(), SorcererEntity.this.getMaxHeadRotation(), SorcererEntity.this.getMaxLookPitchChange());
            }
        }
    }

    public class LevitateTargetsGoal
            extends SpellcastingIllagerEntity.CastSpellGoal {

        @Override
        public boolean canStart() {
            if (SorcererEntity.this.getTarget() == null) {
                return false;
            }
            if (SorcererEntity.this.cooldown < 0) {
                return true;
            }
            return false;
        }
        private List<LivingEntity> getTargets() {
            return world.getEntitiesByClass(LivingEntity.class, getBoundingBox().expand(14), entity -> !(entity instanceof IllagerEntity) && !(entity instanceof SurrenderedEntity));
        }
        @Override
        public void stop() {
            super.stop();
        }
        private void buff(LivingEntity entity) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 60, 1));
            double x = entity.getX();
            double y = entity.getY()+1;
            double z = entity.getZ();
            entity.pushAwayFrom(SorcererEntity.this);
            ((ServerWorld)world).spawnParticles(ParticleTypes.SMOKE,x, y,z,10,0.2D, 0.2D,0.2D,0.015D);
        }

        @Override
        protected void castSpell() {
            SorcererEntity.this.cooldown = 220;
            getTargets().forEach(this::buff);
            isLevitating = true;


        }

        @Override
        protected int getInitialCooldown() {
            return 50;
        }

        @Override
        protected int getSpellTicks() {
            return 50;
        }

        @Override
        protected int startTimeDelay() {
            return 400;
        }

        @Override
        protected SoundEvent getSoundPrepare() {
            return SoundRegistry.SORCERER_CAST;
        }

        @Override
        protected SpellcastingIllagerEntity.Spell getSpell() {
            return Spell.FANGS;
        }
    }
    public class FireResistanceGoal
            extends SpellcastingIllagerEntity.CastSpellGoal {

        @Override
        public boolean canStart() {
            if (SorcererEntity.this.getTarget() == null) {
                return false;
            }
            if (SorcererEntity.this.buffcooldown < 0) {
                return true;
            }
            return false;
        }
        private List<LivingEntity> getTargets() {
            return world.getEntitiesByClass(LivingEntity.class, getBoundingBox().expand(12), entity -> (entity instanceof IllagerEntity));
        }

        @Override
        public void stop() {
            super.stop();
        }
        private void buff(LivingEntity entity) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 450, 0));
            double x = entity.getX();
            double y = entity.getY()+1;
            double z = entity.getZ();
            ((ServerWorld)world).spawnParticles(ParticleTypes.FLAME,x, y,z,20,0.4D, 0.4D,0.4D,0.15D);

        }

        @Override
        protected void castSpell() {
            SorcererEntity.this.buffcooldown = 400;
            getTargets().forEach(this::buff);
        }

        @Override
        protected int getInitialCooldown() {
            return 40;
        }

        @Override
        protected int getSpellTicks() {
            return 40;
        }

        @Override
        protected int startTimeDelay() {
            return 140;
        }

        @Override
        protected SoundEvent getSoundPrepare() {
            return SoundRegistry.SORCERER_CAST;
        }

        @Override
        protected SpellcastingIllagerEntity.Spell getSpell() {
            return SpellcastingIllagerEntity.Spell.WOLOLO;
        }
    }
}
