package me.sandbox;

import com.ibm.icu.impl.TimeZoneGenericNames;
import me.sandbox.block.BlockRegistry;
import me.sandbox.client.particle.ParticleRegistry;
import me.sandbox.entity.EntityRegistry;
import me.sandbox.item.ItemRegistry;
import me.sandbox.sounds.SoundRegistry;
import me.sandbox.util.MobSpawningRules;
import me.sandbox.world.features.ModConfiguredFeatures;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import org.lwjgl.system.CallbackI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class Sandbox implements ModInitializer {
    public static final String MOD_ID = "sandbox";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        ItemRegistry.registerModItems();
        BlockRegistry.registerModBlocks();
        SoundRegistry.registerSounds();
        EntityRegistry.registerEntities();
        ModConfiguredFeatures.registerFeatures();
        MobSpawningRules.addSpawnEntries();
        ParticleRegistry.registerParticles();
    }
}
