package me.sandbox.entity;

import com.google.common.collect.Sets;
import me.sandbox.client.particle.ParticleRegistry;
import me.sandbox.entity.goals.DoSporeGoal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
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
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

public class MushroomlingEntity extends TameableEntity implements IAnimatable {
    public static final Set<Item> TAMING_ITEMS;
    private static final Predicate<Entity> IS_NOT_MUSHROOMLING = entity -> entity.isAlive() && !(entity instanceof MushroomlingEntity);
    public boolean setSpore;
    public int sporeCooldown;

    static {
        TAMING_ITEMS = Sets.newHashSet(Items.BROWN_MUSHROOM, Items.RED_MUSHROOM);
    }

    private AnimationFactory factory = new AnimationFactory(this);
    public static final TrackedData<Integer> STATE = DataTracker.registerData(MushroomlingEntity.class,
            TrackedDataHandlerRegistry.INTEGER);

    public MushroomlingEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(STATE, 0);
    }

    private <E extends IAnimatable> PlayState mainpredicate(AnimationEvent<E> event) {
        if (this.dataTracker.get(STATE) == 0 && event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("mushroomling.animation.walk", true));
            return PlayState.CONTINUE;
        }
        if (this.dataTracker.get(STATE) == 0 && this.isSitting() && !event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("mushroomling.animation.sit", true));
            return PlayState.CONTINUE;
        }
        if (this.dataTracker.get(STATE) == 0 && !event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("mushroomling.animation.idle", true));
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<MushroomlingEntity>(this, "main", 4, this::mainpredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(8, new WanderAroundGoal(this, 0.6));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0f, 1.0f));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0f));

    }

    private AttributeContainer attributeContainer;

    @Override
    public AttributeContainer getAttributes() {
        if (attributeContainer == null) {
            attributeContainer = new AttributeContainer(HostileEntity.createHostileAttributes()
                    .add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
                    .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
                    .build());
        }
        return attributeContainer;
    }

    public void doSpore() {
        if (world instanceof ServerWorld) {
            ((ServerWorld) world).spawnParticles(ParticleRegistry.POISON_SPORE, this.prevX, this.prevY + 1, this.prevZ, 75, 0.5D, 0.5D, 0.5D, 0.003);
        }
        LivingEntity attacker = this.getAttacker();
        if (attacker != null) {
            attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100));
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean bl2 = super.damage(source, amount);
        Random random = new Random();
        boolean canSpore = random.nextBoolean();
        if (canSpore) {
            this.doSpore();
        }
        return bl2;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!this.isTamed() && TAMING_ITEMS.contains(itemStack.getItem())) {
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            if (!this.world.isClient) {
                if (this.random.nextInt(10) == 0) {
                    this.setOwner(player);
                    this.world.sendEntityStatus(this, (byte) 7);
                } else {
                    this.world.sendEntityStatus(this, (byte) 6);
                }
            }

        return ActionResult.success(this.world.isClient);
    } else if (this.isTamed() && this.isOwner(player)) {
            if (!this.world.isClient) {
                this.setSitting(!this.isSitting());
            }
            return ActionResult.success(this.world.isClient);
        } else {
            return super.interactMob(player, hand);
        }
    }
}
