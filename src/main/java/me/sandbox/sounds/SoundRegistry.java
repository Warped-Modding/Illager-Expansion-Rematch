package me.sandbox.sounds;

import me.sandbox.Sandbox;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SoundRegistry {
    public static SoundEvent ENDERGON_ORE_BREAK = registerSoundEvent("endergon_ore_break");
    public static SoundEvent LOST_MINER_AMBIENT = registerSoundEvent("lost_miner_ambient");
    public static SoundEvent LOST_MINER_HURT = registerSoundEvent("lost_miner_hurt");
    public static SoundEvent LOST_MINER_DEATH = registerSoundEvent("lost_miner_death");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(Sandbox.MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }
    public static void registerSounds() {
        Sandbox.LOGGER.info("Registering sounds...");
    }
}
