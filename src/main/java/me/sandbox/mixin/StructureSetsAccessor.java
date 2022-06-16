package me.sandbox.mixin;

import net.minecraft.structure.StructureSet;
import net.minecraft.structure.StructureSets;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin (StructureSets.class)
public interface StructureSetsAccessor {
    @Invoker
    static RegistryEntry<StructureSet> callRegister(RegistryKey<StructureSet> key, StructureSet structureSet) {
        throw new UnsupportedOperationException();
    }
}
