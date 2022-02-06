package me.sandbox.client;


import me.sandbox.entity.BrewerEntity;
import me.sandbox.entity.EntityRegistry;
import me.sandbox.entity.renders.BrewerRender;
import me.sandbox.entity.renders.LostMinerRender;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class ClientRegistry implements ClientModInitializer {
    public void onInitializeClient() {
        //entities
        EntityRendererRegistry.register(EntityRegistry.LOST_MINER, LostMinerRender::new);
    }
}
