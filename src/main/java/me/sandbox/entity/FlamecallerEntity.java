package me.sandbox.entity;


import com.google.common.collect.Maps;
import me.sandbox.item.custom.HornOfSightItem;
import me.sandbox.sounds.SoundRegistry;
import me.sandbox.util.spellutil.SpellParticleUtil;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
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
import net.minecraft.entity.projectile.FireballEntity;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.*;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.CallbackI;

import java.util.HashMap;
import java.util.List;

public class FlamecallerEntity
        extends SpellcastingIllagerEntity {
    @Nullable
    private SheepEntity wololoTarget;
    private int buffcooldown;
    private int flamecooldown;
    public boolean offenseSpell;

    public FlamecallerEntity(EntityType<? extends FlamecallerEntity> entityType, World world) {
        super((EntityType<? extends SpellcastingIllagerEntity>)entityType, world);
        this.experiencePoints = 10;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new FlamecallerEntity.LookAtTargetOrWololoTarget());
        this.goalSelector.add(5, new FireResistanceGoal());
        this.goalSelector.add(4, new ConjureFlamesGoal());
        this.goalSelector.add(8, new WanderAroundGoal(this, 0.6));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0f, 1.0f));
        this.goalSelector.add(6, new MeleeAttackGoal(this,1.2, true));
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
                    .add(EntityAttributes.GENERIC_MAX_HEALTH, 26.0D)
                    .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35D)
                    .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D)
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


    @Override
    protected void mobTick() {
        SpellParticleUtil spellParticleUtil = new SpellParticleUtil<>();
        --buffcooldown;
        --flamecooldown;
        if (this.isSpellcasting() && offenseSpell) {
            spellParticleUtil.setSpellParticles(this,world,ParticleTypes.SMOKE,1,0.08D);
            spellParticleUtil.setSpellParticles(this,world,ParticleTypes.FLAME,1,0.08D);
            ((ServerWorld)world).spawnParticles(ParticleTypes.SMOKE,this.getX(),this.getY()+1,this.getZ(),10,3.0D, 3.0D,3.0D,0.003);
            ((ServerWorld)world).spawnParticles(ParticleTypes.FLAME,this.getX(),this.getY()+1,this.getZ(),10,3.0D, 3.0D,3.0D,0.003);
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
    protected void initEquipment(LocalDifficulty difficulty) {
        if (this.getRaid() == null) {
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
            HashMap<Enchantment, Integer> map = Maps.newHashMap();
            map.put(Enchantments.FIRE_ASPECT, 1);
        }
    }
    @Override
    public void addBonusForWave(int wave, boolean unused) {
        boolean bl;
        ItemStack itemStack = new ItemStack(Items.GOLDEN_SWORD);
        Raid raid = this.getRaid();
        int i = 1;
        if (wave > raid.getMaxWaves(Difficulty.NORMAL)) {
            i = 2;
        }
        boolean bl2 = bl = this.random.nextFloat() <= raid.getEnchantmentChance();
        if (bl) {
            HashMap<Enchantment, Integer> map = Maps.newHashMap();
            map.put(Enchantments.SHARPNESS, i);
            map.put(Enchantments.FIRE_ASPECT, 1);
            EnchantmentHelper.set(map, itemStack);
        }
        this.equipStack(EquipmentSlot.MAINHAND, itemStack);
    }
    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        EntityData entityData2 = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        ((MobNavigation) this.getNavigation()).setCanPathThroughDoors(true);
        this.initEquipment(difficulty);
        this.updateEnchantments(difficulty);
        return entityData2;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_EVOKER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_EVOKER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_EVOKER_HURT;
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
        return SoundEvents.ITEM_FIRECHARGE_USE;
    }
    @Override
    public IllagerEntity.State getState() {
        if (this.isSpellcasting()) {
            return IllagerEntity.State.SPELLCASTING;
        }
        if (this.isAttacking()) {
            return State.ATTACKING;
        }
        return IllagerEntity.State.CROSSED;
    }

    class LookAtTargetOrWololoTarget
            extends SpellcastingIllagerEntity.LookAtTargetGoal {
        FlamecallerEntity flamecaller = FlamecallerEntity.this;

        @Override
        public void tick() {
            if (flamecaller.getTarget() != null) {
                flamecaller.getLookControl().lookAt(flamecaller.getTarget(), flamecaller.getMaxHeadRotation(), flamecaller.getMaxLookPitchChange());
            } else if (flamecaller.getWololoTarget() != null) {
                flamecaller.getLookControl().lookAt(flamecaller.getWololoTarget(), flamecaller.getMaxHeadRotation(), flamecaller.getMaxLookPitchChange());
            }
        }
    }

    public class FireResistanceGoal
            extends SpellcastingIllagerEntity.CastSpellGoal {
        FlamecallerEntity flamecaller = FlamecallerEntity.this;

        @Override
        public boolean canStart() {
            if (flamecaller.getTarget() == null) {
                return false;
            }
            if (flamecaller.isSpellcasting()) {
                return false;
            }
            if (flamecaller.buffcooldown < 0 && !(getTargets().stream().anyMatch(entity -> (entity.hasStatusEffect(StatusEffects.FIRE_RESISTANCE))))) {
                return true;
            }
            return false;
        }
        private List<LivingEntity> getTargets() {
            return world.getEntitiesByClass(LivingEntity.class, getBoundingBox().expand(12), entity -> (entity instanceof  IllagerEntity));
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

        }

        @Override
        protected void castSpell() {
            flamecaller.buffcooldown = 400;
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
    public class ConjureFlamesGoal
            extends SpellcastingIllagerEntity.CastSpellGoal {
        FlamecallerEntity flamecaller = FlamecallerEntity.this;

        @Override
        public boolean canStart() {
            if (flamecaller.getTarget() == null) {
                return false;
            }
            if (getTargets().isEmpty()) {
                return false;
            }
            if (flamecaller.isSpellcasting()) {
                return false;
            }
            if (flamecaller.flamecooldown < 0) {
                offenseSpell = true;
                return true;
            }
            return false;
        }
        private List<LivingEntity> getTargets() {
            return world.getEntitiesByClass(LivingEntity.class, getBoundingBox().expand(8), entity -> !(entity instanceof HostileEntity));
        }

        private void placeFire(double targetx, double targety, double targetz) {
            BlockPos blockpos = new BlockPos(targetx ,targety, targetz);
            world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
        }

        private void igniteTargets(LivingEntity entity) {
            entity.damage(DamageSource.MAGIC, 4.0f);
            placeFire(entity.getX(), entity.getY(), entity.getZ());
            if (world instanceof ServerWorld) {
                ((ServerWorld)world).spawnParticles(ParticleTypes.FLAME,entity.getX(), entity.getY()+1.5, entity.getZ(),20,0.4D, 0.4D,0.4D,0.15D);
            }
        }

        @Override
        public void stop() {
            super.stop();
        }

        @Override
        protected void castSpell() {
            getTargets().forEach(this::igniteTargets);
            flamecaller.flamecooldown = 460;
            ((ServerWorld)world).spawnParticles(ParticleTypes.FLAME,flamecaller.getX(), flamecaller.getY()+1, flamecaller.getZ(),100,1.0D, 2.0D,1.0D,0.3D);
            offenseSpell = false;
        }

        @Override
        protected int getInitialCooldown() {
            return 60;
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
            return SoundRegistry.FLAMECALLER_CAST;
        }

        @Override
        protected SpellcastingIllagerEntity.Spell getSpell() {
            return SpellcastingIllagerEntity.Spell.WOLOLO;
        }
    }
}

