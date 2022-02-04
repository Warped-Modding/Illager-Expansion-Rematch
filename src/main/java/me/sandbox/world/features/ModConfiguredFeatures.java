package me.sandbox.world.features;

import me.sandbox.Sandbox;
import me.sandbox.block.BlockRegistry;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.*;

public class ModConfiguredFeatures {

    //Endergon ore
    private static ConfiguredFeature<?, ?> ENDERGON_ORE_CONFIGURED_FEATURE = Feature.ORE
            .configure(new OreFeatureConfig(
                    new BlockMatchRuleTest(Blocks.END_STONE),
                    BlockRegistry.endergon_ore.getDefaultState(),
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
    }

}
