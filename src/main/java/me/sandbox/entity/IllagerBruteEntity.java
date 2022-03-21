package me.sandbox.entity;

import com.google.common.collect.Maps;
import me.sandbox.item.ItemRegistry;
import me.sandbox.sounds.SoundRegistry;
import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.NavigationConditions;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Random;
import java.util.function.Predicate;

public class IllagerBruteEntity
        extends IllagerEntity{
    public boolean isUsingHatchet;

    public IllagerBruteEntity(EntityType<? extends IllagerBruteEntity> entityType, World world) {
        super((EntityType<? extends IllagerEntity>) entityType, world);
        this.experiencePoints = 25;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new IllagerEntity.LongDoorInteractGoal(this));
        this.goalSelector.add(4, new IllagerBruteEntity.AttackGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this, RaiderEntity.class).setGroupRevenge(new Class[0]));
        this.targetSelector.add(2, new ActiveTargetGoal<PlayerEntity>((MobEntity) this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<MerchantEntity>((MobEntity) this, MerchantEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<IronGolemEntity>((MobEntity) this, IronGolemEntity.class, true));
        this.goalSelector.add(8, new WanderAroundGoal(this, 0.6));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0f, 1.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0f));
    }

    @Override
    protected void mobTick() {
        if (!this.isAiDisabled() && NavigationConditions.hasMobNavigation(this)) {
            boolean bl = ((ServerWorld) this.world).hasRaidAt(this.getBlockPos());
            ((MobNavigation) this.getNavigation()).setCanPathThroughDoors(bl);
        }
        super.mobTick();
    }

    private AttributeContainer attributeContainer;

    @Override
    public AttributeContainer getAttributes() {
        if (attributeContainer == null) {
            attributeContainer = new AttributeContainer(HostileEntity.createHostileAttributes()
                    .add(EntityAttributes.GENERIC_MAX_HEALTH, 80.0D)
                    .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.33D)
                    .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D)
                    .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK,1.6D)
                    .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.8D)
                    .build());
        }
        return attributeContainer;
    }
    @Override
    public void tickMovement() {
        if (this.horizontalCollision && this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            boolean bl = false;
            Box box = this.getBoundingBox().expand(0.3);
            for (BlockPos blockPos : BlockPos.iterate(MathHelper.floor(box.minX), MathHelper.floor(box.minY), MathHelper.floor(box.minZ), MathHelper.floor(box.maxX), MathHelper.floor(box.maxY), MathHelper.floor(box.maxZ))) {
                BlockState blockState = this.world.getBlockState(blockPos);
                Block block = blockState.getBlock();
                if (!(block instanceof LeavesBlock || block instanceof DoorBlock || block instanceof GlassBlock)) continue;
                bl = this.world.breakBlock(blockPos, true, this) || bl;
            }
        }
        super.tickMovement();
    }


    @Override
    public IllagerEntity.State getState() {
        if (this.isCelebrating()) {
            return IllagerEntity.State.CELEBRATING;
        }
        if (this.isAttacking()) {
            return State.ATTACKING;
        }
        return State.NEUTRAL;
    }

    @Override
    public SoundEvent getCelebratingSound() {
        return SoundEvents.ENTITY_VINDICATOR_CELEBRATE;
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
    protected void initEquipment(LocalDifficulty difficulty) {
        if (this.getRaid() == null) {
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_AXE));
        }
    }

    @Override
    public boolean isTeammate(Entity other) {
        if (super.isTeammate(other)) {
            return true;
        }
        if (other instanceof LivingEntity && ((LivingEntity) other).getGroup() == EntityGroup.ILLAGER) {
            return this.getScoreboardTeam() == null && other.getScoreboardTeam() == null;
        }
        return false;
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.ILLAGER_BRUTE_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.ILLAGER_BRUTE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundRegistry.ILLAGER_BRUTE_HURT;
    }

    @Override
    public void addBonusForWave(int wave, boolean unused) {
        boolean bl;
        ItemStack itemStack = new ItemStack(Items.DIAMOND_AXE);
        Raid raid = this.getRaid();
        int i = 1;
        if (wave > raid.getMaxWaves(Difficulty.NORMAL)) {
            i = 2;
        }
        boolean bl2 = bl = this.random.nextFloat() <= raid.getEnchantmentChance();
        if (bl) {
            HashMap<Enchantment, Integer> map = Maps.newHashMap();
            map.put(Enchantments.SHARPNESS, i);
            EnchantmentHelper.set(map, itemStack);
        }
        this.equipStack(EquipmentSlot.MAINHAND, itemStack);
    }

    class AttackGoal
            extends MeleeAttackGoal {
        public AttackGoal(IllagerBruteEntity vindicator) {
            super(vindicator, 1.0, false);

        }

        @Override
        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            if (this.mob.getVehicle() instanceof RavagerEntity) {
                float f = this.mob.getVehicle().getWidth() - 0.1f;
                return f * 2.0f * (f * 2.0f) + entity.getWidth();
            }
            return super.getSquaredMaxAttackDistance(entity);
        }
    }
}
