package me.sandbox.world.features.structurefeatures;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;

public class LabyrinthTestGenerator {
    public static final RegistryEntry<StructurePool> STRUCTURE_POOLS = StructurePools.register(new StructurePool(new Identifier("illagerexp:labyrinth_entrance_pool"), new Identifier("empty"), ImmutableList.of(Pair.of(StructurePoolElement.ofSingle("illagerexp:labyrinth/labyrinth_entrance"), 1)), StructurePool.Projection.RIGID));

    public static void init() {

    }
}
