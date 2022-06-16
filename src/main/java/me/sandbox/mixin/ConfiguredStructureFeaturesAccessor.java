package me.sandbox.mixin;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ConfiguredStructureFeatures.class)
public interface ConfiguredStructureFeaturesAccessor {

    @Invoker
    static <FC extends FeatureConfig, F extends StructureFeature<FC>> RegistryEntry<ConfiguredStructureFeature<?, ?>> callRegister(RegistryKey<ConfiguredStructureFeature<?, ?>> key, ConfiguredStructureFeature<FC, F> configuredStructureFeature) {
        throw new UnsupportedOperationException();
    }
}
