package me.sandbox.client;


import me.sandbox.Sandbox;
import me.sandbox.client.particle.ParticleRegistry;
import me.sandbox.client.renders.*;
import me.sandbox.entity.EntityRegistry;
import me.sandbox.item.ItemRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.item.Item;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;


@Environment(EnvType.CLIENT)
public class ClientRegistry implements ClientModInitializer {
    @Override
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

        //pull registry
        registerPullPredicates(ItemRegistry.HORN_OF_SIGHT);

        }
        public void registerPullPredicates(Item item) {
            FabricModelPredicateProviderRegistry.register(item, new Identifier("pull"), (itemStack, world, livingEntity, i) -> {
                if (livingEntity == null) {
                    return 0.0F;
                } else {
                    return livingEntity.getActiveItem() != itemStack ? 0.0F : (float) (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 20.0F;
                }
            });
            FabricModelPredicateProviderRegistry.register(item, new Identifier("pulling"),
                    (itemStack, clientWorld, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F);
        }

}
