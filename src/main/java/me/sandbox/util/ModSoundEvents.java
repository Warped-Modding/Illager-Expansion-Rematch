package me.sandbox.util;

import me.sandbox.Sandbox;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Sandbox.MOD_ID);

    public static final RegistryObject<SoundEvent> LOST_MINER_AMBIENT =
            registerSoundEvent("lost_miner_ambient");
    public static final RegistryObject<SoundEvent> LOST_MINER_HURT =
            registerSoundEvent("lost_miner_hurt");
    public static final RegistryObject<SoundEvent> LOST_MINER_DEATH =
            registerSoundEvent("lost_miner_death");

    public static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(Sandbox.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
