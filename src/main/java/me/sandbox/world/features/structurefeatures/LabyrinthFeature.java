package me.sandbox.world.features.structurefeatures;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import me.sandbox.world.ProcessorRegistry;
import net.minecraft.structure.StructureSets;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.HeightContext;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.heightprovider.ConstantHeightProvider;

import java.util.Optional;

public class LabyrinthFeature extends BaseStructure {
    public static final RegistryEntry<StructurePool> STRUCTURE_POOLS = StructurePools.register(new StructurePool(new Identifier("illagerexp:labyrinth_entrance_pool"), new Identifier("empty"), ImmutableList.of(Pair.of(StructurePoolElement.ofProcessedSingle("illagerexp:labyrinth/labyrinth_entrance", ProcessorRegistry.WATERLOGGED_LIST), 1)), StructurePool.Projection.RIGID));

    public LabyrinthFeature(Config config) {
        super(config, STRUCTURE_POOLS, 7, ConstantHeightProvider.create(YOffset.fixed(-40)), Heightmap.Type.WORLD_SURFACE_WG);
    }

    @Override
    public Optional<StructurePosition> getStructurePosition(Context context) {
        if (!canGenerateSimple(context)) {
            return Optional.empty();
        }
        ChunkPos chunkPos = context.chunkPos();
        int i = this.startHeight.get(context.random(), new HeightContext(context.chunkGenerator(), context.world()));
        BlockPos blockPos = new BlockPos(chunkPos.getStartX(), i, chunkPos.getStartZ());
        int topLandY = context.chunkGenerator().getHeightOnGround(blockPos.getX(), blockPos.getZ(), Heightmap.Type.WORLD_SURFACE_WG, context.world(), context.noiseConfig());
        blockPos = blockPos.up(topLandY - 40);

        StructurePools.method_44111();
        return StructurePoolBasedGenerator.generate(context, this.startPool, this.startJigsawName, this.size, blockPos, false, this.projectStartToHeightmap, this.maxDistanceFromCenter);
    }

    public static boolean canGenerateSimple(Context context) {
        ChunkPos chunkPos = context.chunkPos();
        return !context.chunkGenerator().shouldStructureGenerateInRange(StructureSets.VILLAGES, context.noiseConfig(), context.seed(), chunkPos.x, chunkPos.z, 10);
    }
}
