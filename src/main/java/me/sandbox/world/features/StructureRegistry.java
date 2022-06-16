package me.sandbox.world.features;

import me.sandbox.IllagerExpansion;
import me.sandbox.config.IllagerExpansionConfig;
import me.sandbox.mixin.ConfiguredStructureFeaturesAccessor;
import me.sandbox.mixin.StructureFeatureAccessor;
import me.sandbox.world.features.structurefeatures.*;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.structure.StructureSet;
import net.minecraft.structure.StructureSets;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.gen.chunk.placement.SpreadType;
import net.minecraft.world.gen.feature.*;

public class StructureRegistry<C extends FeatureConfig> {
    public static RegistryEntry<ConfiguredStructureFeature<?,?>> CONFIGURED_LABYRINTH;
    public static RegistryEntry<StructureSet> LABYRINTHS;
    public static StructureFeature<?> ILLUSIONER_TOWER = new IllusionerTowerFeature();
    public static StructureFeature<?> SORCERER_HUT = new SorcererHutFeature();
    public static StructureFeature<?> ILLAGER_FORT = new IllagerFortFeature(StructurePoolFeatureConfig.CODEC);
    public static StructureFeature<StructurePoolFeatureConfig> LABYRINTH = new LabyrinthFeature(StructurePoolFeatureConfig.CODEC);
    static {

    }

    public static final RegistryKey<ConfiguredStructureFeature<?, ?>> LABYRINTH_KEY = StructureRegistry.of("labyrinth");

    public static final RegistryKey<StructureSet> LABYRINTH_STRUCTURE_SET_KEY = StructureRegistry.setof("labyrinth");

    public static void registerStructureFeatures() {
        StructureFeatureAccessor.callRegister(IllagerExpansion.MOD_ID + ":illusioner_tower", ILLUSIONER_TOWER, GenerationStep.Feature.SURFACE_STRUCTURES);
        StructureFeatureAccessor.callRegister(IllagerExpansion.MOD_ID + ":illager_fort", ILLAGER_FORT, GenerationStep.Feature.SURFACE_STRUCTURES);
        StructureFeatureAccessor.callRegister(IllagerExpansion.MOD_ID + ":sorcerer_hut", SORCERER_HUT, GenerationStep.Feature.SURFACE_STRUCTURES);
        StructureFeatureAccessor.callRegister(IllagerExpansion.MOD_ID + ":labyrinth", LABYRINTH, GenerationStep.Feature.SURFACE_STRUCTURES);

        LabyrinthGenerator.init();
    }
    public static void registerConfiguredStructureFeatures() {
        CONFIGURED_LABYRINTH = ConfiguredStructureFeaturesAccessor.callRegister(StructureRegistry.LABYRINTH_KEY, StructureRegistry.LABYRINTH.configure(new StructurePoolFeatureConfig(LabyrinthGenerator.STRUCTURE_POOLS, 7), BiomeTags.WOODLAND_MANSION_HAS_STRUCTURE));
    }
    public static void registerStructureSets() {
        LABYRINTHS = StructureSets.register(StructureRegistry.LABYRINTH_STRUCTURE_SET_KEY, StructureRegistry.CONFIGURED_LABYRINTH, new RandomSpreadStructurePlacement(48, 40, SpreadType.LINEAR, 1687452161));
    }
    private static RegistryKey<ConfiguredStructureFeature<?, ?>> of(String id) {
        return RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, new Identifier(IllagerExpansion.MOD_ID, id));
    }
    private static RegistryKey<StructureSet> setof(String id) {
        return RegistryKey.of(Registry.STRUCTURE_SET_KEY, new Identifier(IllagerExpansion.MOD_ID, id));
    }
}
