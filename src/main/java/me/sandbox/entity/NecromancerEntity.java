package me.sandbox.entity;

import me.sandbox.sounds.SoundRegistry;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NecromancerEntity
        extends SpellcastingIllagerEntity {
    @Nullable
    private SheepEntity wololoTarget;
    private int cooldown;

    public NecromancerEntity(EntityType<? extends NecromancerEntity> entityType, World world) {
        super((EntityType<? extends SpellcastingIllagerEntity>)entityType, world);
        this.experiencePoints = 15;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new NecromancerEntity.LookAtTargetOrWololoTarget());
        this.goalSelector.add(4, new ConjureSkullGoal());
        this.goalSelector.add(3, new SummonUndeadGoal());
        this.goalSelector.add(2, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0f, 0.6, 1.0));
        this.goalSelector.add(8, new WanderAroundGoal(this, 0.6));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0f, 1.0f));
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
                    .add(EntityAttributes.GENERIC_MAX_HEALTH, 34.0D)
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
        return SoundEvents.ENTITY_ILLUSIONER_AMBIENT;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    @Override
    protected void mobTick() {
        super.mobTick();
        --cooldown;
        System.out.println(cooldown);
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
        return SoundEvents.ENTITY_ILLUSIONER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ILLUSIONER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_ILLUSIONER_HURT;
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
        return SoundEvents.ENTITY_EVOKER_CAST_SPELL;
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
            if (NecromancerEntity.this.getTarget() != null) {
                NecromancerEntity.this.getLookControl().lookAt(NecromancerEntity.this.getTarget(), NecromancerEntity.this.getMaxHeadRotation(), NecromancerEntity.this.getMaxLookPitchChange());
            } else if (NecromancerEntity.this.getWololoTarget() != null) {
                NecromancerEntity.this.getLookControl().lookAt(NecromancerEntity.this.getWololoTarget(), NecromancerEntity.this.getMaxHeadRotation(), NecromancerEntity.this.getMaxLookPitchChange());
            }
        }
    }
    class SummonUndeadGoal
            extends SpellcastingIllagerEntity.CastSpellGoal {
        private final TargetPredicate closeVexPredicate = TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0).ignoreVisibility().ignoreDistanceScalingFactor();

        SummonUndeadGoal() {
        }
        private int spellcount;

        @Override
        public boolean canStart() {
            if (!super.canStart()) {
                return false;
            }
            if (spellcount >= 5) {
                return false;
            }
            int i = NecromancerEntity.this.world.getTargets(ZombieEntity.class, this.closeVexPredicate, NecromancerEntity.this, NecromancerEntity.this.getBoundingBox().expand(16.0)).size();
            return NecromancerEntity.this.random.nextInt(8) + 1 > i;
        }

        @Override
        protected int getSpellTicks() {
            return 100;
        }

        @Override
        protected int startTimeDelay() {
            return 340;
        }

        @Override
        protected void castSpell() {
            if(NecromancerEntity.this.world.isNight()) {
                ServerWorld serverWorld = (ServerWorld) NecromancerEntity.this.world;
                for (int i = 0; i < 3; ++i) {
                    BlockPos blockPos = NecromancerEntity.this.getBlockPos().add(-2 + NecromancerEntity.this.random.nextInt(5), 1, -2 + NecromancerEntity.this.random.nextInt(5));
                    ZombieEntity zombieEntity = EntityType.ZOMBIE.create(NecromancerEntity.this.world);
                    zombieEntity.refreshPositionAndAngles(blockPos, 0.0f, 0.0f);
                    zombieEntity.initialize(serverWorld, NecromancerEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, null, null);
                    serverWorld.spawnEntityAndPassengers(zombieEntity);
                }
            }
            if(NecromancerEntity.this.world.isDay()) {
                ServerWorld serverWorld = (ServerWorld) NecromancerEntity.this.world;
                for (int i = 0; i < 3; ++i) {
                    BlockPos blockPos = NecromancerEntity.this.getBlockPos().add(-2 + NecromancerEntity.this.random.nextInt(5), 1, -2 + NecromancerEntity.this.random.nextInt(5));
                    HuskEntity zombieEntity = EntityType.HUSK.create(NecromancerEntity.this.world);
                    zombieEntity.refreshPositionAndAngles(blockPos, 0.0f, 0.0f);
                    zombieEntity.initialize(serverWorld, NecromancerEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, null, null);
                    serverWorld.spawnEntityAndPassengers(zombieEntity);
                }
            }
            ++spellcount;
        }

        @Override
        protected SoundEvent getSoundPrepare() {
            return SoundRegistry.NECROMANCER_SUMMON;
        }

        @Override
        protected SpellcastingIllagerEntity.Spell getSpell() {
            return SpellcastingIllagerEntity.Spell.SUMMON_VEX;
        }
    }


    public class ConjureSkullGoal
            extends SpellcastingIllagerEntity.CastSpellGoal {

        @Override
        public boolean canStart() {
            if (NecromancerEntity.this.getTarget() == null) {
                return false;
            }
            if (NecromancerEntity.this.cooldown > 0) {
                return false;
            }
            if (NecromancerEntity.this.cooldown <= 0) {
                return true;
            }
            return true;
        }

        @Override
        public void stop() {
            super.stop();
        }

        private void shootSkullAt(LivingEntity target) {
            this.shootSkullAt(target.getX(), target.getY() + (double)target.getStandingEyeHeight() * 0.5, target.getZ());
        }

        private void shootSkullAt(double targetX, double targetY, double targetZ) {
            double d = NecromancerEntity.this.getX();
            double e = NecromancerEntity.this.getY()+2.5;
            double f = NecromancerEntity.this.getZ();
            double g = targetX - d;
            double h = targetY - e;
            double i = targetZ - f;
            SkullboltEntity skullbolt = new SkullboltEntity(NecromancerEntity.this.world, NecromancerEntity.this, g, h, i);
            skullbolt.setOwner(NecromancerEntity.this);
            skullbolt.setPos(d, e, f);
            NecromancerEntity.this.world.spawnEntity(skullbolt);
        }

        @Override
        protected void castSpell() {
            this.shootSkullAt(NecromancerEntity.this.getTarget());
            if (world instanceof ServerWorld) {
                double x = NecromancerEntity.this.getX();
                double y = NecromancerEntity.this.getY()+2.5;
                double z = NecromancerEntity.this.getZ();
                ((ServerWorld) world).spawnParticles(ParticleTypes.SMOKE, x, y, z, 40, 0.4D, 0.4D, 0.4D, 0.15D);
            }
            NecromancerEntity.this.cooldown = 100;
        }

        @Override
        protected int getInitialCooldown() {
            return 40;
        }

        @Override
        protected int getSpellTicks() {
            return 60;
        }

        @Override
        protected int startTimeDelay() {
            return 140;
        }

        @Override
        protected SoundEvent getSoundPrepare() {
            return SoundEvents.ENTITY_EVOKER_PREPARE_ATTACK;
        }

        @Override
        protected SpellcastingIllagerEntity.Spell getSpell() {
            return Spell.FANGS;
        }
    }
}
