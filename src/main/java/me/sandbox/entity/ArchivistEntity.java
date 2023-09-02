package me.sandbox.entity;

import com.chocohead.mm.api.ClassTinkerers;
import me.sandbox.sounds.SoundRegistry;
import me.sandbox.util.spellutil.EnchantToolUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ArchivistEntity
        extends SpellcastingIllagerEntity {
    @Nullable
    private SheepEntity wololoTarget;
    private IllagerEntity enchantTarget;
    private int cooldown;
    private int damagedelay = 60;
    private boolean isLevitating;
    public float bookanimation;
    public float bookanimation1;
    private int buffcooldown;

    public ArchivistEntity(EntityType<? extends ArchivistEntity> entityType, World world) {
        super((EntityType<? extends SpellcastingIllagerEntity>)entityType, world);
        this.experiencePoints = 10;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new ArchivistEntity.LookAtTargetOrWololoTarget());
        this.goalSelector.add(4, new LevitateTargetsGoal());
        this.goalSelector.add(3, new EnchantAllyGoal());
        this.goalSelector.add(8, new WanderAroundGoal(this, 0.6));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0f, 1.0f));
        this.goalSelector.add(5, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0f, 0.6, 1.0));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0f));
        this.targetSelector.add(1, new RevengeGoal(this, RaiderEntity.class).setGroupRevenge(new Class[0]));
        this.targetSelector.add(2, new ActiveTargetGoal<PlayerEntity>((MobEntity)this, PlayerEntity.class, true).setMaxTimeWithoutVisibility(300));
        this.targetSelector.add(3, new ActiveTargetGoal<MerchantEntity>((MobEntity)this, MerchantEntity.class, false).setMaxTimeWithoutVisibility(300));
        this.targetSelector.add(3, new ActiveTargetGoal<IronGolemEntity>((MobEntity)this, IronGolemEntity.class, false));
    }
    public static DefaultAttributeContainer.Builder createArchivistAttributes() {
		return HostileEntity.createHostileAttributes()
                    .add(EntityAttributes.GENERIC_MAX_HEALTH, 22.0D)
                    .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.36D);
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
        return SoundRegistry.ARCHIVIST_AMBIENT;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }


    @Override
    protected void mobTick() {
        --cooldown;
        --buffcooldown;
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
        return SoundRegistry.ARCHIVIST_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.ARCHIVIST_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundRegistry.ARCHIVIST_HURT;
    }

    void setWololoTarget(@Nullable SheepEntity sheep) {
        this.wololoTarget = sheep;
    }
    void setEnchantTarget(@Nullable IllagerEntity entity) {
        this.enchantTarget = entity;
    }

    @Nullable
    SheepEntity getWololoTarget() {
        return this.wololoTarget;
    }
    @Nullable
    IllagerEntity getEnchantTarget() {
        return this.enchantTarget;
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
            if (ArchivistEntity.this.getTarget() != null) {
                ArchivistEntity.this.getLookControl().lookAt(ArchivistEntity.this.getTarget(), ArchivistEntity.this.getMaxHeadRotation(), ArchivistEntity.this.getMaxLookPitchChange());
            } else if (ArchivistEntity.this.getWololoTarget() != null) {
                ArchivistEntity.this.getLookControl().lookAt(ArchivistEntity.this.getWololoTarget(), ArchivistEntity.this.getMaxHeadRotation(), ArchivistEntity.this.getMaxLookPitchChange());
            }
        }
    }

    public class LevitateTargetsGoal
            extends SpellcastingIllagerEntity.CastSpellGoal {

        @Override
        public boolean canStart() {
            if (ArchivistEntity.this.getTarget() == null) {
                return false;
            }
            if (ArchivistEntity.this.cooldown < 0 && getTargets().stream().anyMatch(entity -> !(entity instanceof HostileEntity))) {
                return true;
            }
            return false;
        }
        private void knockBack(Entity entity) {
            double d = entity.getX() - ArchivistEntity.this.getX();
            double e = entity.getZ() - ArchivistEntity.this.getZ();
            double f = Math.max(d * d + e * e, 0.001);
            entity.addVelocity(d / f * 5.0, 0.6, e / f * 5.0);
        }
        protected void knockback(LivingEntity target) {
                    this.knockBack(target);
                    target.velocityModified = true;
            }

        private List<LivingEntity> getTargets() {
            return world.getEntitiesByClass(LivingEntity.class, getBoundingBox().expand(6), entity -> !(entity instanceof HostileEntity));
        }
        @Override
        public void stop() {
            super.stop();
        }
        private void buff(LivingEntity entity) {
            knockback(entity);
            entity.damage(DamageSource.MAGIC, 4.0f);
        }

        @Override
        protected void castSpell() {
            ArchivistEntity.this.cooldown = 220;
            getTargets().forEach(this::buff);
            double x = ArchivistEntity.this.getX();
            double y = ArchivistEntity.this.getY()+1;
            double z = ArchivistEntity.this.getZ();
            ((ServerWorld)world).spawnParticles(ParticleTypes.ENCHANT,x, y,z,150,3.0D, 3.0D,3.0D,0.1D);
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
            return SoundEvents.ENTITY_ILLUSIONER_PREPARE_BLINDNESS;
        }

        @Override
        protected SpellcastingIllagerEntity.Spell getSpell() {
            return Spell.FANGS;
        }
    }

    public class EnchantAllyGoal
            extends SpellcastingIllagerEntity.CastSpellGoal {
        EnchantToolUtil enchantToolUtil = new EnchantToolUtil();
        private final TargetPredicate closeEnchantableMobPredicate = TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0).setPredicate(livingEntity -> !(livingEntity instanceof ArchivistEntity));
        private int targetId;

        public boolean canEnchant() {
            IllagerEntity hostileEntity = ArchivistEntity.this.getEnchantTarget();
            if (hostileEntity == null) {
                return false;
            }
            return enchantToolUtil.eligibleForEnchant(hostileEntity);
        }
        @Override
        public boolean canStart() {
            if (ArchivistEntity.this.getTarget() == null) {
                return false;
            }
            if (ArchivistEntity.this.buffcooldown >= 0) {
                return false;
            }
            if (ArchivistEntity.this.isSpellcasting()) {
                return false;
            }
            List<IllagerEntity> list = ArchivistEntity.this.world.getTargets(IllagerEntity.class, this.closeEnchantableMobPredicate, ArchivistEntity.this, ArchivistEntity.this.getBoundingBox().expand(16.0, 4.0, 16.0));
            if (list.isEmpty()) {
                return false;
            }
            ArchivistEntity.this.setEnchantTarget(list.get(ArchivistEntity.this.random.nextInt(list.size())));
            IllagerEntity hostileEntity = ArchivistEntity.this.getEnchantTarget();
            if (hostileEntity.getId() == this.targetId) {
                return false;
            }
            return this.canEnchant();
        }
        @Override
        public void stop() {
            super.stop();
            ArchivistEntity.this.setEnchantTarget(null);
        }

        @Override
        protected void castSpell() {
            HostileEntity hostileEntity = ArchivistEntity.this.getEnchantTarget();
            if (hostileEntity != null) {
                this.targetId = hostileEntity.getId();
            }
            enchantToolUtil.enchant(hostileEntity);
            double x = hostileEntity.getX();
            double y = hostileEntity.getY() + 1.5;
            double z = hostileEntity.getZ();
            if (world instanceof ServerWorld) {
                ((ServerWorld)world).spawnParticles(ParticleTypes.ENCHANT, x, y, z, 50, 1.0D, 2.0D, 1.0D, 0.1D);
            }
            ArchivistEntity.this.buffcooldown = 300;
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
            return ClassTinkerers.getEnum(Spell.class, "ENCHANT");
        }
    }
}

