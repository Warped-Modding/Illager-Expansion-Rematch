package me.sandbox.client;


import me.sandbox.block.BlockRegistry;
import me.sandbox.client.model.*;
import me.sandbox.client.particle.ParticleRegistry;
import me.sandbox.client.renders.*;
import me.sandbox.entity.EntityRegistry;
import me.sandbox.gui.ImbuingTableScreen;
import me.sandbox.gui.ModdedScreenHandler;
import me.sandbox.item.ItemRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.particle.EmotionParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;


@Environment(EnvType.CLIENT)
public class ClientRegistry implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        //entities
        EntityRendererRegistry.register(EntityRegistry.PROVOKER, ProvokerRender::new);
        EntityRendererRegistry.register(EntityRegistry.INVOKER, InvokerRender::new);
        EntityRendererRegistry.register(EntityRegistry.SURRENDERED, SurrenderedRender::new);
        EntityRendererRegistry.register(EntityRegistry.NECROMANCER, NecromancerRender::new);
        EntityRendererRegistry.register(EntityRegistry.SKULLBOLT, SkullboltRender::new);
        EntityRendererRegistry.register(EntityRegistry.BASHER, BasherRender::new);
        EntityRendererRegistry.register(EntityRegistry.SORCERER, SorcererRender::new);
        EntityRendererRegistry.register(EntityRegistry.ARCHIVIST, ArchivistRender::new);
        EntityRendererRegistry.register(EntityRegistry.INQUISITOR, InquisitorRender::new);
        EntityRendererRegistry.register(EntityRegistry.MARAUDER, MarauderRender::new);
        EntityRendererRegistry.register(EntityRegistry.ALCHEMIST, AlchemistRender::new);
        EntityRendererRegistry.register(EntityRegistry.FIRECALLER, FirecallerRender::new);
        EntityRendererRegistry.register(EntityRegistry.INVOKER_FANGS, InvokerFangsRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.HATCHET, HatchetRender::new);
        EntityRendererRegistry.register(EntityRegistry.MAGMA, MagmaEntityRender::new);




        //particle factory
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.MAGIC_FLAME, FlameParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.NECROMANCER_BUFF, EmotionParticle.HeartFactory::new);

        //pull registry
        registerPullPredicates(ItemRegistry.HORN_OF_SIGHT);

        //Block client stuff
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.MAGIC_FIRE, RenderLayer.getCutout());

        //Mob Layers
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CAPED_ILLAGER, CapedIllagerEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.INVOKER_SHIELD, CapedIllagerEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.NECROMANCER_SHIELD, IllagerEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.INVOKER_FANGS, InvokerFangsModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.ARMORED_ILLAGER, ArmoredIllagerEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.HAT_ILLAGER, HatIllagerEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.STAFF_ILLAGER, IllagerWithStaffEntityModel::getTexturedModelData);

        //Screen Renders
        ScreenRegistry.register(ModdedScreenHandler.IMBUING_TABLE_SCREEN_HANDLER, ImbuingTableScreen::new);

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
