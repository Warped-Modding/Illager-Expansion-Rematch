package me.sandbox.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.UUID;

public class ShadowSpikeEntity extends PathAwareEntity implements IAnimatable {
    private boolean startedAttack;
    private int ticksLeft = 8;
    private boolean playingAnimation;
    private int warmup;
    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerUuid;


    public ShadowSpikeEntity(EntityType<? extends ShadowSpikeEntity> entityType, World world) {
        super(entityType, world);
    }

    public ShadowSpikeEntity(World world, int warmup, double x, double y, double z, LivingEntity owner) {
        this((EntityType<? extends ShadowSpikeEntity>) EntityRegistry.SHADOW_SPIKE, world);
        this.warmup = warmup;
        this.setOwner(owner);
        this.setPosition(x, y, z);
    }
    private AttributeContainer attributeContainer;
    @Override
    public AttributeContainer getAttributes() {
        if (attributeContainer == null) {
            attributeContainer = new AttributeContainer(HostileEntity.createHostileAttributes()
                    .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                    .build());
        }
        return attributeContainer;
    }


    public void setOwner(@Nullable LivingEntity owner) {
        this.owner = owner;
        this.ownerUuid = owner == null ? null : owner.getUuid();
    }

    @Nullable
    public LivingEntity getOwner() {
        Entity entity;
        if (this.owner == null && this.ownerUuid != null && this.world instanceof ServerWorld && (entity = ((ServerWorld) this.world).getEntity(this.ownerUuid)) instanceof LivingEntity) {
            this.owner = (LivingEntity) entity;
        }
        return this.owner;
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        this.warmup = nbt.getInt("Warmup");
        if (nbt.containsUuid("Owner")) {
            this.ownerUuid = nbt.getUuid("Owner");
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("Warmup", this.warmup);
        if (this.ownerUuid != null) {
            nbt.putUuid("Owner", this.ownerUuid);
        }
    }

    @Override
    public void tick() {
        super.tick();
        --this.ticksLeft;
        if (ticksLeft == 6) {
            List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(0.1, 0.2, 0.1));
            for (LivingEntity livingEntity : list) {
                if (livingEntity != null && !(livingEntity instanceof ShadowSpikeEntity)) {
                    this.damage(livingEntity);
                }
            }
        }
        if (ticksLeft <= 0) {
            this.discard();
        }
    }
    private void damage(LivingEntity target) {
        LivingEntity livingEntity = this.getOwner();
        if (!target.isAlive() || target.isInvulnerable() || target == livingEntity) {
            return;
        }
        if (livingEntity == null) {
            target.damage(DamageSource.MAGIC, 8.0f);
            target.addVelocity(0.0, 0.5, 0.0);
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 300, 0));
        } else {
            if (livingEntity.isTeammate(target)) {
                return;
            }
            target.damage(DamageSource.magic(this, livingEntity), 8.0f);
            target.addVelocity(0.0, 0.5, 0.0);
        }
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status == 4) {
            if (!this.isSilent()) {
                this.world.playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_EVOKER_FANGS_ATTACK, this.getSoundCategory(), 1.0f, this.random.nextFloat() * 0.2f + 0.85f, false);
            }
        }
    }
    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    private <E extends IAnimatable> PlayState mainpredicate(AnimationEvent<E> event) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shadow_spike.rise", false));
            return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<ShadowSpikeEntity>(this, "main", 0, this::mainpredicate));

    }
    private AnimationFactory factory = new AnimationFactory(this);
    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
