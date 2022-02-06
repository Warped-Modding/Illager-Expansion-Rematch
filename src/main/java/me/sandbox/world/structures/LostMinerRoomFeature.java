package me.sandbox.world.structures;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.structure.PostPlacementProcessor;
import net.minecraft.structure.StructureGeneratorFactory;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.minecraft.world.gen.random.AtomicSimpleRandom;
import net.minecraft.world.gen.random.ChunkRandom;

public class LostMinerRoomFeature extends StructureFeature<DefaultFeatureConfig> {

    public LostMinerRoomFeature(Codec<DefaultFeatureConfig> configCodec, StructureGeneratorFactory<DefaultFeatureConfig> piecesGenerator) {
        super(configCodec, piecesGenerator);
    }

    public static boolean canGenerate(StructureGeneratorFactory.Context<DefaultFeatureConfig> context) {
        ChunkRandom chunkRandom = new ChunkRandom(new AtomicSimpleRandom(0L));
        return false;
    }

}
