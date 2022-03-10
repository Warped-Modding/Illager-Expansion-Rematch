package me.sandbox.world.features.structurefeatures;



import com.mojang.serialization.Codec;
import me.sandbox.Sandbox;
import me.sandbox.entity.EntityRegistry;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureGeneratorFactory;
import net.minecraft.structure.StructurePiecesGenerator;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.random.AtomicSimpleRandom;
import net.minecraft.world.gen.random.ChunkRandom;

import java.util.Optional;


public class IllusionerTowerFeature extends StructureFeature<StructurePoolFeatureConfig> {

    public IllusionerTowerFeature(Codec<StructurePoolFeatureConfig> codec) {
        super(codec, IllusionerTowerFeature::createPiecesGenerator);
    }

    private static boolean canGenerate(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
        if (!isSuitableChunk(context)) {
            return false;
        }
        return true;
    }
    private static boolean isSuitableChunk(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
        int i = context.chunkPos().x >> 4;
        int j = context.chunkPos().z >> 4;

        ChunkRandom chunkRandom = new ChunkRandom(new AtomicSimpleRandom(0L));
        chunkRandom.setSeed((long) (i ^ j << 4) ^ context.seed());
        chunkRandom.nextInt();

        if (chunkRandom.nextInt(5) != 0) {
            return false;
        }

        return true;
    }
    public static Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> createPiecesGenerator(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
        if (!IllusionerTowerFeature.canGenerate(context)) {
            return Optional.empty();
        }
        StructurePoolFeatureConfig newConfig = new StructurePoolFeatureConfig(() -> context.registryManager().get(Registry.STRUCTURE_POOL_KEY)
                .get(new Identifier(Sandbox.MOD_ID, "illusioner_tower/illusioner_tower")), 1);
        StructureGeneratorFactory.Context<StructurePoolFeatureConfig> newContext = new StructureGeneratorFactory.Context<>(
                context.chunkGenerator(),
                context.biomeSource(),
                context.seed(),
                context.chunkPos(),
                newConfig,
                context.world(),
                context.validBiome(),
                context.structureManager(),
                context.registryManager());
        BlockPos blockPos = context.chunkPos().getCenterAtY(0);

        Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> structurePiecesGenerator =
                StructurePoolBasedGenerator.generate(
                        newContext,
                        PoolStructurePiece::new,
                        blockPos,
                        false,
                        true
                );
        return structurePiecesGenerator;
    }

}

