package me.sandbox.world.features.structurefeatures;

import com.mojang.serialization.Codec;
import me.sandbox.config.IllagerExpansionConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.structure.*;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.random.AtomicSimpleRandom;
import net.minecraft.world.gen.random.ChunkRandom;

import java.util.Optional;


public class IllagerFortFeature
        extends JigsawFeature {
    public IllagerFortFeature(Codec<StructurePoolFeatureConfig> configCodec) {
        super(configCodec, 0, true, true, IllagerFortFeature::canGenerate);
    }

    private static boolean canGenerate(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
        ChunkPos chunkPos = context.chunkPos();
        int i = chunkPos.x >> 4;
        int j = chunkPos.z >> 4;
        ChunkRandom chunkRandom = new ChunkRandom(new AtomicSimpleRandom(0L));
        chunkRandom.setSeed((long)(i ^ j << 4) ^ context.seed());
        chunkRandom.nextInt();
        if (chunkRandom.nextInt(5) != 0) {
            return false;
        }
        return !context.chunkGenerator().method_41053(StructureSetKeys.VILLAGES, context.seed(), chunkPos.x, chunkPos.z, 10);
    }
}

