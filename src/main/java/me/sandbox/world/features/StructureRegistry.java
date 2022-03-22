package me.sandbox.world.features;

import me.sandbox.IllagerExpansion;
import me.sandbox.mixin.StructureFeatureAccessor;
import me.sandbox.world.features.structurefeatures.IllagerFortFeature;
import me.sandbox.world.features.structurefeatures.IllusionerTowerFeature;
import me.sandbox.world.features.structurefeatures.SorcererHutFeature;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.StructureFeature;

public class StructureRegistry {
    public static StructureFeature<?> ILLUSIONER_TOWER = new IllusionerTowerFeature();
    public static StructureFeature<?> SORCERER_HUT = new SorcererHutFeature();
    public static StructureFeature<?> ILLAGER_FORT = new IllagerFortFeature();

    public static void registerStructureFeatures() {
        StructureFeatureAccessor.callRegister(IllagerExpansion.MOD_ID + ":illusioner_tower", ILLUSIONER_TOWER, GenerationStep.Feature.SURFACE_STRUCTURES);
        StructureFeatureAccessor.callRegister(IllagerExpansion.MOD_ID + ":illager_fort", ILLAGER_FORT, GenerationStep.Feature.SURFACE_STRUCTURES);
        StructureFeatureAccessor.callRegister(IllagerExpansion.MOD_ID + ":sorcerer_hut", SORCERER_HUT, GenerationStep.Feature.SURFACE_STRUCTURES);

    }
}
