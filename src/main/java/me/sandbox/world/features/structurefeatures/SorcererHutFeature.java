package me.sandbox.world.features.structurefeatures;



import net.minecraft.structure.*;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.*;

import java.util.Optional;


public class SorcererHutFeature extends StructureFeature<StructurePoolFeatureConfig> {

    public SorcererHutFeature() {
        super(StructurePoolFeatureConfig.CODEC, SorcererHutFeature::createPiecesGenerator, PostPlacementProcessor.EMPTY);
    }

    public static boolean isFeatureChunk(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
        ChunkPos chunkPos = context.chunkPos();

        return !context.chunkGenerator().method_41053(StructureSetKeys.VILLAGES, context.seed(), chunkPos.x, chunkPos.z, 10);
    }
    public static Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> createPiecesGenerator(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
        if (!SorcererHutFeature.isFeatureChunk(context)) {
            return Optional.empty();
        }
        BlockPos blockpos = context.chunkPos().getCenterAtY(0);
        int topLandY = context.chunkGenerator().getHeightOnGround(blockpos.getX(), blockpos.getZ(), Heightmap.Type.WORLD_SURFACE_WG, context.world());

        Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> structurePiecesCollector =
                StructurePoolBasedGenerator.generate(
                        context,
                        PoolStructurePiece::new,
                        blockpos,
                        false,
                        false
                );
        return structurePiecesCollector;
    }

}

