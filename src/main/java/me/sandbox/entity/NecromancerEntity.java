package me.sandbox.entity;

import com.chocohead.mm.api.ClassTinkerers;
import me.sandbox.client.particle.ParticleRegistry;
import me.sandbox.entity.projectile.SkullboltEntity;
import me.sandbox.sounds.SoundRegistry;
import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NecromancerEntity extends SpellcastingIllagerEntity implements SkinOverlayOwner
{
    @Nullable
    private SheepEntity wololoTarget;
    private int cooldown;
    private int undeadtick;
    private int particletick;
    private int summoncooldown;
    private static final TrackedData<Boolean> SHIELDED = DataTracker.registerData(NecromancerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private AttributeContainer attributeContainer;

    public NecromancerEntity(final EntityType<? extends NecromancerEntity> entityType, final World world) {
        super(entityType, world);
        this.experiencePoints = 15;
    }

    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal((MobEntity)this));
        this.goalSelector.add(1, new LookAtTargetOrWololoTarget());
        this.goalSelector.add(4, new ConjureSkullGoal());
        this.goalSelector.add(3, new NecromancerEntity.SummonUndeadGoal());
        this.goalSelector.add(5, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0f, 0.6, 1.0));
        this.goalSelector.add(8, new WanderAroundGoal((PathAwareEntity)this, 0.6));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0f, 1.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0f));
        this.targetSelector.add(1, new RevengeGoal(this, RaiderEntity.class).setGroupRevenge(new Class[0]));
        this.targetSelector.add(2, new ActiveTargetGoal<PlayerEntity>((MobEntity)this, PlayerEntity.class, true).setMaxTimeWithoutVisibility(300));
        this.targetSelector.add(3, new ActiveTargetGoal<MerchantEntity>((MobEntity)this, MerchantEntity.class, false).setMaxTimeWithoutVisibility(300));
        this.targetSelector.add(3, new ActiveTargetGoal<IronGolemEntity>((MobEntity)this, IronGolemEntity.class, false));

    }

    public AttributeContainer getAttributes() {
        if (this.attributeContainer == null) {
            this.attributeContainer = new AttributeContainer(HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 32.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.38).build());
        }
        return this.attributeContainer;
    }

    protected void initDataTracker() {
        this.dataTracker.startTracking(SHIELDED,false);
        super.initDataTracker();
    }

    public void setShieldedState(final boolean isShielded) {
        this.dataTracker.set(SHIELDED, isShielded);
    }

    public boolean getShieldedState() {
        return (boolean)this.dataTracker.get(SHIELDED);
    }

    public void readCustomDataFromNbt(final NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }

    public SoundEvent getCelebratingSound() {
        return SoundEvents.ENTITY_ILLUSIONER_AMBIENT;
    }

    public void writeCustomDataToNbt(final NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    protected void mobTick() {
        super.mobTick();
        --this.summoncooldown;
        --this.cooldown;
        if (!this.getNearUndeadForLink().isEmpty()) {
            ++this.particletick;
            this.setShieldedState(true);
            this.getNearUndeadForLink().forEach(this::doUndeadLinkLogic);
            if (this.particletick == 10) {
                this.getNearUndeadForLink().forEach(this::doUndeadLinkParticle);
                this.particletick = 0;
            }
        }
        else {
            this.setShieldedState(false);
        }
        if (this.getTarget() instanceof SkeletonEntity) {
            this.setTarget((LivingEntity)null);
        }
        if (!this.getNearUndeadForTarget().isEmpty()) {
            this.getNearUndeadForTarget().forEach(this::setUndeadTarget);
        }
    }

    public List<LivingEntity> getNearUndeadForLink() {
        return (this.world.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(10.0), entity -> entity.getGroup() == EntityGroup.UNDEAD));
    }

    public List<LivingEntity> getNearUndeadForTarget() {
        return (this.world.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(30.0), entity -> entity.getGroup() == EntityGroup.UNDEAD));
    }

    public void doUndeadLinkLogic(final LivingEntity entity) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 5, 0));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 5, 0));
        if (entity instanceof MobEntity) {
            ((MobEntity)entity).setTarget(this.getTarget());
        }
        if (entity instanceof SkeletonEntity) {
            entity.setFireTicks(0);
        }
    }

    public void setUndeadTarget(final LivingEntity entity) {
        if (this.getTarget() != null && entity instanceof MobEntity) {
            ((MobEntity)entity).setTarget(this.getTarget());
        }
    }

    public void doUndeadLinkParticle(final LivingEntity entity) {
        final double x = entity.getX();
        final double y = entity.getY();
        final double z = entity.getZ();
        if (this.world instanceof ServerWorld && this.particletick == 10) {
            ((ServerWorld)this.world).spawnParticles(ParticleRegistry.NECROMANCER_BUFF, x, y + 1.0, z, 1, 0.4, 0.5, 0.4, 0.015);
            ((ServerWorld)this.world).spawnParticles(ParticleRegistry.NECROMANCER_BUFF, this.getX(), this.getY() + 1.0, this.getZ(), 1, 0.4, 0.5, 0.4, 0.015);
        }
    }

    public boolean damage(final DamageSource source, final float amount) {
        if (this.getShieldedState()) {
            final float halfamount = amount / 2.0f;
            return super.damage(source, halfamount);
        }
        final boolean bl2 = super.damage(source, amount);
        return bl2;
    }

    public boolean isTeammate(final Entity other) {
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
            return this.isTeammate((Entity)((VexEntity)other).getOwner());
        }
        return other instanceof LivingEntity && ((LivingEntity)other).getGroup() == EntityGroup.ILLAGER && this.getScoreboardTeam() == null && other.getScoreboardTeam() == null;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ILLUSIONER_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ILLUSIONER_DEATH;
    }

    protected SoundEvent getHurtSound(final DamageSource source) {
        return SoundEvents.ENTITY_ILLUSIONER_HURT;
    }

    @Nullable
    SheepEntity getWololoTarget() {
        return this.wololoTarget;
    }

    protected SoundEvent getCastSpellSound() {
        return SoundEvents.ENTITY_EVOKER_CAST_SPELL;
    }

    public void addBonusForWave(final int wave, final boolean unused) {
    }

    public IllagerEntity.State getState() {
        if (this.isSpellcasting()) {
            return IllagerEntity.State.SPELLCASTING;
        }
        return IllagerEntity.State.CROSSED;
    }

    public boolean shouldRenderOverlay() {
        return this.getShieldedState();
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
            if (spellcount >= 4) {
                return false;
            }
            int i = NecromancerEntity.this.world.getTargets(ZombieEntity.class, this.closeVexPredicate, NecromancerEntity.this, NecromancerEntity.this.getBoundingBox().expand(16.0)).size();
            return NecromancerEntity.this.random.nextInt(5) + 1 > i;
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
                BlockPos blockPos = NecromancerEntity.this.getBlockPos().add(-2 + NecromancerEntity.this.random.nextInt(5), 1, -2 + NecromancerEntity.this.random.nextInt(5));
                SkeletonEntity skeletonEntity = EntityType.SKELETON.create(NecromancerEntity.this.world);
                skeletonEntity.refreshPositionAndAngles(blockPos, 0.0f, 0.0f);
                skeletonEntity.initialize(serverWorld, NecromancerEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, null, null);
                serverWorld.spawnEntityAndPassengers(skeletonEntity);
            }
            if(NecromancerEntity.this.world.isDay()) {
                ServerWorld serverWorld = (ServerWorld) NecromancerEntity.this.world;
                for (int i = 0; i < 2; ++i) {
                    BlockPos blockPos = NecromancerEntity.this.getBlockPos().add(-2 + NecromancerEntity.this.random.nextInt(5), 1, -2 + NecromancerEntity.this.random.nextInt(5));
                    HuskEntity zombieEntity = EntityType.HUSK.create(NecromancerEntity.this.world);
                    zombieEntity.refreshPositionAndAngles(blockPos, 0.0f, 0.0f);
                    zombieEntity.initialize(serverWorld, NecromancerEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, null, null);
                    serverWorld.spawnEntityAndPassengers(zombieEntity);
                }
                BlockPos blockPos = NecromancerEntity.this.getBlockPos().add(-2 + NecromancerEntity.this.random.nextInt(5), 1, -2 + NecromancerEntity.this.random.nextInt(5));
                SkeletonEntity skeletonEntity = EntityType.SKELETON.create(NecromancerEntity.this.world);
                skeletonEntity.refreshPositionAndAngles(blockPos, 0.0f, 0.0f);
                skeletonEntity.initialize(serverWorld, NecromancerEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, null, null);
                serverWorld.spawnEntityAndPassengers(skeletonEntity);
            }
            ++spellcount;
        }

        @Override
        protected SoundEvent getSoundPrepare() {
            return SoundRegistry.NECROMANCER_SUMMON;
        }

        @Override
        protected SpellcastingIllagerEntity.Spell getSpell() {
            return ClassTinkerers.getEnum(Spell.class, "NECRORAISE");
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
            return ClassTinkerers.getEnum(Spell.class, "CONJURE_SKULLBOLT");
        }
    }
}
