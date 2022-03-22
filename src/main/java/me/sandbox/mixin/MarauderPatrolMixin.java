package me.sandbox.mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.entity.EntityData;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import me.sandbox.entity.EntityRegistry;
import net.minecraft.world.WorldAccess;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.world.BlockView;
import net.minecraft.world.SpawnHelper;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.gen.PillagerSpawner;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ PillagerSpawner.class })
public abstract class MarauderPatrolMixin
{
    @Inject(at = { @At("HEAD") }, cancellable = true, method = { "spawnPillager(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;Z)Z" })
    public void spawnMarauder(final ServerWorld world, final BlockPos pos, final Random random, final boolean captain, final CallbackInfoReturnable<Boolean> cir) {
        final BlockState bs = world.getBlockState(pos);
        if (!SpawnHelper.isClearForSpawn(world, pos, bs, bs.getFluidState(), EntityType.PILLAGER)) {
            cir.cancel();
        }
        if (!PatrolEntity.canSpawn(EntityType.PILLAGER, world, SpawnReason.PATROL, pos, random)) {
            cir.cancel();
        }
        final int randvalue = random.nextInt(2);
        if (randvalue == 0) {
            final PatrolEntity marauder = EntityRegistry.MARAUDER.create(world);
            if (marauder != null) {
                if (captain) {
                    marauder.setPatrolLeader(true);
                    marauder.setRandomPatrolTarget();
                }
                marauder.setPosition(pos.getX(), pos.getY(), pos.getZ());
                marauder.initialize(world, world.getLocalDifficulty(pos), SpawnReason.PATROL, null, null);
                world.spawnEntityAndPassengers(marauder);
            }
        }
    }
}
