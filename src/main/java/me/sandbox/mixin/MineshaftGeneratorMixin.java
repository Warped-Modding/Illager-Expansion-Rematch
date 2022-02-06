package me.sandbox.mixin;


import me.sandbox.entity.EntityRegistry;
import me.sandbox.entity.LostMinerEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import net.minecraft.structure.MineshaftGenerator;
import net.minecraft.structure.StructurePiecesHolder;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(MineshaftGenerator.MineshaftCorridor.class)
public abstract class MineshaftGeneratorMixin {

    public void addMiner(ServerWorldAccess world, BlockPos pos) {
        LostMinerEntity lostMinerEntity;
        lostMinerEntity = EntityRegistry.LOST_MINER.create(world.toServerWorld());
        lostMinerEntity.setPersistent();
        world.spawnEntityAndPassengers(lostMinerEntity);
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
    }


    @Inject(at = @At("HEAD"), method = "generate", cancellable = true)
        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pos, CallbackInfo ci) {


        }
    }
