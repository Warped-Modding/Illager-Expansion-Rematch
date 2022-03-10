package me.sandbox.world.features;

import me.sandbox.Sandbox;
import me.sandbox.block.BlockRegistry;
import me.sandbox.world.features.structurefeatures.IllagerFortFeature;
import me.sandbox.world.features.structurefeatures.IllusionerTowerFeature;
import me.sandbox.world.features.structurefeatures.SorcererHutFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.*;

public class FeatureRegistry {

    public static StructureFeature<StructurePoolFeatureConfig> SORCERER_HUT = new SorcererHutFeature(StructurePoolFeatureConfig.CODEC);
    public static StructureFeature<StructurePoolFeatureConfig> ILLAGER_FORT = new IllagerFortFeature(StructurePoolFeatureConfig.CODEC);
    public static StructureFeature<StructurePoolFeatureConfig> ILLUSIONER_TOWER = new IllusionerTowerFeature(StructurePoolFeatureConfig.CODEC);

    //Endergon ore
    private static ConfiguredFeature<?, ?> ENDERGON_ORE_CONFIGURED_FEATURE = Feature.ORE
            .configure(new OreFeatureConfig(
                    new BlockMatchRuleTest(Blocks.END_STONE),
                    BlockRegistry.ENDERGON_ORE.getDefaultState(),
                    3));
    public static PlacedFeature ENDERGON_ORE_PLACED_FEATURE = ENDERGON_ORE_CONFIGURED_FEATURE.withPlacement(
            CountPlacementModifier.of(9),
            SquarePlacementModifier.of(),
            HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(28)));


    public static void registerFeatures() {
        //Endergon Ore Register
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,
                new Identifier(Sandbox.MOD_ID, "endergon_ore"), ENDERGON_ORE_CONFIGURED_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Sandbox.MOD_ID, "endergon_ore"),
                ENDERGON_ORE_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.foundInTheEnd(), GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                        new Identifier(Sandbox.MOD_ID, "endergon_ore")));
        //Structure Registry
        FabricStructureBuilder.create(new Identifier(Sandbox.MOD_ID, "sorcerer_hut"), SORCERER_HUT).step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(new StructureConfig(30, 25, 530103)).adjustsSurface().register();
        FabricStructureBuilder.create(new Identifier(Sandbox.MOD_ID, "illager_fort"), ILLAGER_FORT).step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(new StructureConfig(45, 40, 530104)).adjustsSurface().register();
        FabricStructureBuilder.create(new Identifier(Sandbox.MOD_ID, "illusioner_tower"), ILLUSIONER_TOWER).step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(new StructureConfig(45, 40, 530105)).adjustsSurface().register();

    }

    public static void addStructureSpawning() {
        BiomeModifications.addStructure(BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST), RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
                BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(ModConfiguredFeatures.CONFIGURED_SORCERER_HUT)));
        BiomeModifications.addStructure(BiomeSelectors.includeByKey(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA, BiomeKeys.OLD_GROWTH_PINE_TAIGA), RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
                BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(ModConfiguredFeatures.CONFIGURED_ILLAGER_FORT)));
        BiomeModifications.addStructure(BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST), RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
                BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(ModConfiguredFeatures.CONFIGURED_ILLUSIONER_TOWER)));
    }
}
