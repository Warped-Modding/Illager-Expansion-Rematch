package me.sandbox.mixin;


import me.sandbox.entity.EntityRegistry;
import me.sandbox.entity.ProvokerEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnReason;
import net.minecraft.structure.WoodlandMansionGenerator;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.swing.text.AbstractDocument;
import java.util.Random;

@Mixin(WoodlandMansionGenerator.Piece.class)
public class WoodlandMansionPiecesMixin {
    @Inject(at=@At("HEAD"), cancellable = true, method= "handleMetadata(Ljava/lang/String;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/ServerWorldAccess;Ljava/util/Random;Lnet/minecraft/util/math/BlockBox;)V")
    public void handleDataMarker(String metadata, BlockPos pos, ServerWorldAccess world, Random random, BlockBox boundingBox, CallbackInfo ci) {
        Random randomValue = new Random();
        int value = randomValue.nextInt(8);
        if(metadata.equals("Warrior") && value == 1) {
            ProvokerEntity provoker;
            provoker = EntityRegistry.PROVOKER.create(world.toServerWorld());
            provoker.setPersistent();
            provoker.refreshPositionAndAngles(pos, 0.0f, 0.0f);
            provoker.initialize(world, world.getLocalDifficulty(provoker.getBlockPos()), SpawnReason.STRUCTURE, null, null);
            world.spawnEntityAndPassengers(provoker);
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
        }
    }
}