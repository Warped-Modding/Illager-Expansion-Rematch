package me.sandbox.client;


import me.sandbox.Sandbox;
import me.sandbox.client.particle.ParticleRegistry;
import me.sandbox.entity.EntityRegistry;
import me.sandbox.entity.renders.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.particle.CrackParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.item.Items;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ClientRegistry implements ClientModInitializer {
    public void onInitializeClient() {
        //entities
        EntityRendererRegistry.register(EntityRegistry.LOST_MINER, LostMinerRender::new);
        EntityRendererRegistry.register(EntityRegistry.PROVOKER, ProvokerRender::new);
        EntityRendererRegistry.register(EntityRegistry.INVOKER, InvokerRender::new);
        EntityRendererRegistry.register(EntityRegistry.SURRENDERED, SurrenderedRender::new);
        EntityRendererRegistry.register(EntityRegistry.SHADOW_SPIKE, ShadowSpikeRender::new);
        EntityRendererRegistry.register(EntityRegistry.MUSHROOMLING, MushroomlingRender::new);
        EntityRendererRegistry.register(EntityRegistry.NECROMANCER, NecromancerRender::new);
        EntityRendererRegistry.register(EntityRegistry.SKULLBOLT, SkullboltRender::new);
        EntityRendererRegistry.register(EntityRegistry.BASHER, BasherRender::new);
        EntityRendererRegistry.register(EntityRegistry.SORCERER, SorcererRender::new);

        //particle texture (only needed if custom texture)!!
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(Sandbox.MOD_ID, "particle/poison_spore"));
        }));

        //particle factory
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.POISON_SPORE, FlameParticle.Factory::new);
    }
}
