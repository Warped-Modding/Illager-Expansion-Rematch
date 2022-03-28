package me.sandbox.world.features;

import me.sandbox.IllagerExpansion;
import me.sandbox.mixin.ConfiguredStructureFeaturesAccessor;
import me.sandbox.mixin.StructureFeatureAccessor;
import me.sandbox.world.features.structurefeatures.*;
import net.minecraft.structure.StructureSet;
import net.minecraft.structure.StructureSetKeys;
import net.minecraft.structure.StructureSets;
import net.minecraft.tag.BiomeTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.gen.chunk.placement.SpreadType;
import net.minecraft.world.gen.feature.*;

public class StructureRegistry<C extends FeatureConfig> {
    public static RegistryEntry<ConfiguredStructureFeature<?,?>> CONFIGURED_LABYRINTH_TEST;
    public static RegistryEntry<StructureSet> LABYRINTH_TESTS;
    public static StructureFeature<?> ILLUSIONER_TOWER = new IllusionerTowerFeature();
    public static StructureFeature<?> SORCERER_HUT = new SorcererHutFeature();
    public static StructureFeature<?> ILLAGER_FORT = new IllagerFortFeature();
    public static StructureFeature<?> LABYRINTH = new LabyrinthFeature();
    public static StructureFeature<StructurePoolFeatureConfig> LABYRINTH_TEST = new LabyrinthTestFeature(StructurePoolFeatureConfig.CODEC);

    public static final RegistryKey<ConfiguredStructureFeature<?, ?>> LABYRINTH_TEST_KEY = StructureRegistry.of("labyrinth_test");
    public static final RegistryKey<StructureSet> LABYRINTH_TEST_STRUCTURE_SET_KEY = StructureRegistry.setof("labyrinth_test");

    public static void registerStructureFeatures() {
        StructureFeatureAccessor.callRegister(IllagerExpansion.MOD_ID + ":illusioner_tower", ILLUSIONER_TOWER, GenerationStep.Feature.SURFACE_STRUCTURES);
        StructureFeatureAccessor.callRegister(IllagerExpansion.MOD_ID + ":illager_fort", ILLAGER_FORT, GenerationStep.Feature.SURFACE_STRUCTURES);
        StructureFeatureAccessor.callRegister(IllagerExpansion.MOD_ID + ":sorcerer_hut", SORCERER_HUT, GenerationStep.Feature.SURFACE_STRUCTURES);
        StructureFeatureAccessor.callRegister(IllagerExpansion.MOD_ID + ":labyrinth", LABYRINTH, GenerationStep.Feature.SURFACE_STRUCTURES);
        StructureFeatureAccessor.callRegister(IllagerExpansion.MOD_ID + ":labyrinth_test", LABYRINTH_TEST, GenerationStep.Feature.SURFACE_STRUCTURES);
    }
    public static void registerConfiguredStructureFeatures() {
        CONFIGURED_LABYRINTH_TEST = ConfiguredStructureFeaturesAccessor.callRegister(StructureRegistry.LABYRINTH_TEST_KEY, StructureRegistry.LABYRINTH_TEST.configure(new StructurePoolFeatureConfig(LabyrinthTestGenerator.STRUCTURE_POOLS, 7), BiomeTags.PILLAGER_OUTPOST_HAS_STRUCTURE));
    }
    public static void registerStructureSets() {
        LABYRINTH_TESTS = StructureSets.register(StructureRegistry.LABYRINTH_TEST_STRUCTURE_SET_KEY, StructureRegistry.CONFIGURED_LABYRINTH_TEST, new RandomSpreadStructurePlacement(32, 8, SpreadType.LINEAR, 1687452161));
    }
    private static RegistryKey<ConfiguredStructureFeature<?, ?>> of(String id) {
        return RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, new Identifier(id));
    }
    private static RegistryKey<StructureSet> setof(String id) {
        return RegistryKey.of(Registry.STRUCTURE_SET_KEY, new Identifier(id));
    }
}
