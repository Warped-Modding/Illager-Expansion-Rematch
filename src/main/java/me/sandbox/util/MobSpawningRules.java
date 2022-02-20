package me.sandbox.util;

import me.sandbox.entity.EntityRegistry;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;

public class MobSpawningRules {

    public static void addSpawnEntries() {
        BiomeModifications.addSpawn(BiomeSelectors.all(),
                SpawnGroup.MONSTER,
                EntityRegistry.LOST_MINER,
                55,
                1,
                2);
    }
}
