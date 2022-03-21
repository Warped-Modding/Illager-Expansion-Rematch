package me.sandbox.entity.goal;

import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.BowItem;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import java.util.EnumSet;
import org.jetbrains.annotations.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.HostileEntity;

public class PotionBowAttackGoal<T extends HostileEntity> extends Goal
{
    private final MobEntity mob;
    private final RangedAttackMob owner;
    @Nullable
    private LivingEntity target;
    private int updateCountdownTicks;
    private final double mobSpeed;
    private int seenTargetTicks;
    private final int minIntervalTicks;
    private final int maxIntervalTicks;
    private final float maxShootRange;
    private final float squaredMaxShootRange;
    private final T actor;
    private final double speed;
    private int attackInterval;
    private final float squaredRange;
    private int cooldown;
    private int targetSeeingTicker;
    private boolean movingToLeft;
    private boolean backward;
    private int combatTicks;

    public PotionBowAttackGoal(final T actor, final double speed, final int attackInterval, final float range) {
        this.updateCountdownTicks = -1;
        this.cooldown = -1;
        this.combatTicks = -1;
        this.actor = actor;
        this.speed = speed;
        this.attackInterval = attackInterval;
        this.squaredRange = range * range;
        this.owner = (RangedAttackMob)actor;
        this.mob = (MobEntity)actor;
        this.mobSpeed = speed;
        this.minIntervalTicks = attackInterval;
        this.maxIntervalTicks = attackInterval;
        this.maxShootRange = range;
        this.squaredMaxShootRange = this.maxShootRange * this.maxShootRange;
        this.setControls((EnumSet)EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    public void setAttackInterval(final int attackInterval) {
        this.attackInterval = attackInterval;
    }

    public boolean canStart() {
        return ((MobEntity)this.actor).getTarget() != null && this.isHoldingBow();
    }

    protected boolean isHoldingBow() {
        return ((LivingEntity)this.actor).isHolding(Items.BOW) || ((LivingEntity)this.actor).isHolding(Items.LINGERING_POTION);
    }

    public boolean shouldContinue() {
        return (this.canStart() || !((MobEntity)this.actor).getNavigation().isIdle()) && this.isHoldingBow();
    }

    public void start() {
        super.start();
        ((MobEntity)this.actor).setAttacking(true);
    }

    public void stop() {
        super.stop();
        ((MobEntity)this.actor).setAttacking(false);
        this.targetSeeingTicker = 0;
        this.cooldown = -1;
        ((LivingEntity)this.actor).clearActiveItem();
    }

    public boolean shouldRunEveryTick() {
        return true;
    }

    public void tick() {
        final LivingEntity livingEntity = this.actor.getTarget();
        if (livingEntity == null) {
            return;
        }
        if (this.actor.getEquippedStack(EquipmentSlot.MAINHAND).isOf(Items.LINGERING_POTION)) {
            final double d = this.mob.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            final boolean bl = this.mob.getVisibilityCache().canSee((Entity)livingEntity);
            this.seenTargetTicks = (bl ? (++this.seenTargetTicks) : 0);
            if (d > this.squaredMaxShootRange || this.seenTargetTicks < 5) {
                this.mob.getNavigation().startMovingTo((Entity)livingEntity, this.mobSpeed);
            }
            else {
                this.mob.getNavigation().stop();
            }
            this.mob.getLookControl().lookAt((Entity)livingEntity, 30.0f, 30.0f);
            final int updateCountdownTicks = this.updateCountdownTicks - 1;
            this.updateCountdownTicks = updateCountdownTicks;
            if (updateCountdownTicks == 0) {
                if (!bl) {
                    return;
                }
                final float f = (float)Math.sqrt(d) / this.maxShootRange;
                final float g = MathHelper.clamp(f, 0.1f, 1.0f);
                this.owner.attack(livingEntity, g);
                this.updateCountdownTicks = MathHelper.floor(f * (this.maxIntervalTicks - this.minIntervalTicks) + this.minIntervalTicks);
            }
            else if (this.updateCountdownTicks < 0) {
                this.updateCountdownTicks = MathHelper.floor(MathHelper.lerp(Math.sqrt(d) / this.maxShootRange, (double)this.minIntervalTicks, (double)this.maxIntervalTicks));
            }
        }
        if (this.actor.getEquippedStack(EquipmentSlot.MAINHAND).isOf(Items.BOW)) {
            final double d = ((Entity)this.actor).squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            final boolean bl = ((MobEntity)this.actor).getVisibilityCache().canSee((Entity)livingEntity);
            final boolean bl3;
            final boolean bl2 = bl3 = (this.targetSeeingTicker > 0);
            if (bl != bl2) {
                this.targetSeeingTicker = 0;
            }
            this.targetSeeingTicker = (bl ? (++this.targetSeeingTicker) : (--this.targetSeeingTicker));
            if (d > this.squaredRange || this.targetSeeingTicker < 20) {
                ((MobEntity)this.actor).getNavigation().startMovingTo((Entity)livingEntity, this.speed);
                this.combatTicks = -1;
            }
            else {
                ((MobEntity)this.actor).getNavigation().stop();
                ++this.combatTicks;
            }
            if (this.combatTicks >= 20) {
                if (((LivingEntity)this.actor).getRandom().nextFloat() < 0.3) {
                    boolean movingToLeft = false;
                    if (this.movingToLeft) {
                        movingToLeft = false;
                    }
                    this.movingToLeft = movingToLeft;
                }
                if (((LivingEntity)this.actor).getRandom().nextFloat() < 0.3) {
                    this.backward = !this.backward;
                }
                this.combatTicks = 0;
            }
            if (this.combatTicks > -1) {
                if (d > this.squaredRange * 0.75f) {
                    this.backward = false;
                }
                else if (d < this.squaredRange * 0.25f) {
                    this.backward = true;
                }
                ((MobEntity)this.actor).getMoveControl().strafeTo(this.backward ? -0.5f : 0.5f, this.movingToLeft ? 0.5f : -0.5f);
                ((MobEntity)this.actor).lookAtEntity((Entity)livingEntity, 30.0f, 30.0f);
            }
            else {
                ((MobEntity)this.actor).getLookControl().lookAt((Entity)livingEntity, 30.0f, 30.0f);
            }
            if (((LivingEntity)this.actor).isUsingItem()) {
                if (!bl && this.targetSeeingTicker < -60) {
                    ((LivingEntity)this.actor).clearActiveItem();
                }
                else {
                    final int i;
                    if (bl && (i = ((LivingEntity)this.actor).getItemUseTime()) >= 20) {
                        ((LivingEntity)this.actor).clearActiveItem();
                        ((RangedAttackMob)this.actor).attack(livingEntity, BowItem.getPullProgress(i));
                        this.cooldown = this.attackInterval;
                    }
                }
            }
            else {
                final int cooldown = this.cooldown - 1;
                this.cooldown = cooldown;
                if (cooldown <= 0 && this.targetSeeingTicker >= -60 && this.actor.getEquippedStack(EquipmentSlot.MAINHAND).isOf(Items.BOW)) {
                    ((LivingEntity)this.actor).setCurrentHand(ProjectileUtil.getHandPossiblyHolding((LivingEntity)this.actor, Items.BOW));
                }
            }
        }
    }
}
