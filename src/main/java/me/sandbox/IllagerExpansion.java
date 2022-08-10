package me.sandbox;

import me.sandbox.block.BlockRegistry;
import me.sandbox.client.particle.ParticleRegistry;
import me.sandbox.config.IllagerExpansionConfig;
import me.sandbox.config.ModMenu;
import me.sandbox.entity.EntityRegistry;
import me.sandbox.item.ItemRegistry;
import me.sandbox.item.potion.PotionRegistry;
import me.sandbox.sounds.SoundRegistry;
import me.sandbox.world.ProcessorRegistry;
import me.sandbox.world.features.StructureRegistry;
import me.sandbox.world.features.structurefeatures.LabyrinthGenerator;
import me.sandbox.gui.ImbuingTableScreenHandler;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.screen.ScreenHandlerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IllagerExpansion implements ModInitializer {
    public static final String MOD_ID = "illagerexp";
    public static final Logger LOGGER = LoggerFactory.getLogger("IllagerExpansion");
	
	public static final Identifier IMBUING_TABLE_HANDLER_ID = new Identifier(MOD_ID, "imbuing_table");
	public static final ScreenHandlerType<ImbuingTableScreenHandler> IMBUING_TABLE_SCREEN_HANDLER;
	
	static {
        IMBUING_TABLE_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(IMBUING_TABLE_HANDLER_ID, ImbuingTableScreenHandler::new);
    }


    @Override
    public void onInitialize() {
        IllagerExpansionConfig.registerConfig();
        ItemRegistry.registerModItems();
        BlockRegistry.registerModBlocks();
        SoundRegistry.registerSounds();
        EntityRegistry.registerEntities();
        ParticleRegistry.registerParticles();
        StructureRegistry.registerStructureFeatures();
        StructureRegistry.registerConfiguredStructureFeatures();
        StructureRegistry.registerStructureSets();
        ProcessorRegistry.registerProcessors();
        PotionRegistry.registerPotions();

        LOGGER.info("Why are there so many illagers?");
    }
}
