package me.sandbox.world.features;

import me.sandbox.Sandbox;
import net.minecraft.structure.PlainsVillageData;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;


public class ModConfiguredFeatures {
    public static ConfiguredStructureFeature<?,?> CONFIGURED_SORCERER_HUT = FeatureRegistry.SORCERER_HUT.configure(new StructurePoolFeatureConfig(() -> PlainsVillageData.STRUCTURE_POOLS,0));
    public static ConfiguredStructureFeature<?,?> CONFIGURED_ILLAGER_FORT = FeatureRegistry.ILLAGER_FORT.configure(new StructurePoolFeatureConfig(() -> PlainsVillageData.STRUCTURE_POOLS,0));
    public static ConfiguredStructureFeature<?,?> CONFIGURED_ILLUSIONER_TOWER = FeatureRegistry.ILLUSIONER_TOWER.configure(new StructurePoolFeatureConfig(() -> PlainsVillageData.STRUCTURE_POOLS,0));

    public static void registerConfiguredFeatures() {
        Registry<ConfiguredStructureFeature<?,?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new Identifier(Sandbox.MOD_ID, "sorcerer_hut"), CONFIGURED_SORCERER_HUT);
        Registry.register(registry, new Identifier(Sandbox.MOD_ID, "illager_fort"), CONFIGURED_ILLAGER_FORT);
        Registry.register(registry, new Identifier(Sandbox.MOD_ID, "illusioner_tower"), CONFIGURED_ILLUSIONER_TOWER);
    }
}
