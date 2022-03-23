package me.sandbox.entity.projectile;

import me.sandbox.entity.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class SkullboltEntity
        extends ExplosiveProjectileEntity {

    public SkullboltEntity(EntityType<? extends SkullboltEntity> entityType, World world) {
        super((EntityType<? extends ExplosiveProjectileEntity>)entityType, world);
    }

    public SkullboltEntity(World world, LivingEntity owner, double directionX, double directionY, double directionZ) {
        super(EntityRegistry.SKULLBOLT, owner, directionX, directionY, directionZ, world);
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        boolean bl;
        super.onEntityHit(entityHitResult);
        if (this.world.isClient) {
            return;
        }
        Entity entity = entityHitResult.getEntity();
        Entity entity2 = this.getOwner();
        if (entity2 instanceof LivingEntity) {
            if (((LivingEntity) entity).getGroup() == EntityGroup.UNDEAD) {
                ((LivingEntity) entity).heal(5.0f);
                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 100, 2));
            } else {
                entity.damage(DamageSource.MAGIC, 7.0f);
                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 1));
            }
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (world instanceof ServerWorld) {
            double x = this.getX();
            double y = this.getY()+0.2;
            double z = this.getZ();
            ((ServerWorld)world).spawnParticles(ParticleTypes.SMOKE,x, y,z,25,0.25D, 0.25D,0.25D,0.05D);
        }
        this.discard();

    }

    @Override
    public boolean collides() {
        return false;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    @Override
    protected boolean isBurning() {
        return false;
    }
}

