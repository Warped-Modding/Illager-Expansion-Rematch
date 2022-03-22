package me.sandbox.client.particle;

import me.sandbox.Sandbox;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.Particle;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ParticleRegistry {
    public static final DefaultParticleType MAGIC_FLAME = FabricParticleTypes.simple();
    public static final DefaultParticleType NECROMANCER_BUFF = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Sandbox.MOD_ID, "magic_flame"), MAGIC_FLAME);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Sandbox.MOD_ID, "necromancer_buff"), NECROMANCER_BUFF);
    }
}
