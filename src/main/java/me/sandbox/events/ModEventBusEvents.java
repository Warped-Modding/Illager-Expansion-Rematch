package me.sandbox.events;

import me.sandbox.Sandbox;
import me.sandbox.entity.ModEntityTypes;
import me.sandbox.entity.custom.LostMinerEntity;
import me.sandbox.entity.render.LostMinerRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Sandbox.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.LOST_MINER.get(), LostMinerEntity.createAttributes().build());
    }
    @SubscribeEvent
    public static void RendererSetup(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.LOST_MINER.get(), LostMinerRenderer::new);
    }
}
