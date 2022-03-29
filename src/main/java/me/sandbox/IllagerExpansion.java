package me.sandbox;

import me.sandbox.block.BlockRegistry;
import me.sandbox.client.particle.ParticleRegistry;
import me.sandbox.entity.EntityRegistry;
import me.sandbox.item.ItemRegistry;
import me.sandbox.sounds.SoundRegistry;
import me.sandbox.world.ProcessorRegistry;
import me.sandbox.world.features.StructureRegistry;
import me.sandbox.world.features.structurefeatures.LabyrinthGenerator;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IllagerExpansion implements ModInitializer {
    public static final String MOD_ID = "illagerexp";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        ItemRegistry.registerModItems();
        BlockRegistry.registerModBlocks();
        SoundRegistry.registerSounds();
        EntityRegistry.registerEntities();
        ParticleRegistry.registerParticles();
        StructureRegistry.registerStructureFeatures();
        LabyrinthGenerator.init();
        StructureRegistry.registerConfiguredStructureFeatures();
        StructureRegistry.registerStructureSets();
        ProcessorRegistry.registerProcessors();
    }
}
