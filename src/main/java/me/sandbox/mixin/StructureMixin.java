package me.sandbox.mixin;

import me.sandbox.world.ProcessorRegistry;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Structure.class)
public class StructureMixin {

    @Inject(
            method = "place(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/StructurePlacementData;Ljava/util/Random;I)Z",
            at = @At(value = "HEAD")
    )
    private void preventAutoWaterlogging(ServerWorldAccess world, BlockPos pos, BlockPos pivot, StructurePlacementData placementData, Random random, int flags, CallbackInfoReturnable<Boolean> cir) {

        if(placementData.getProcessors().stream().anyMatch(processor ->
                ((StructureProcessorAccessor)processor).callGetType() == ProcessorRegistry.NO_WATERLOG_PROCESSOR)) {
            placementData.setPlaceFluids(false);
        }
    }
}