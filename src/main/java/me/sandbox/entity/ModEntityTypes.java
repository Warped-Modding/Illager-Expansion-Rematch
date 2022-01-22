package me.sandbox.entity;

import me.sandbox.Sandbox;
import me.sandbox.entity.custom.LostMinerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class ModEntityTypes {

    public static DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, Sandbox.MOD_ID);

    public static final RegistryObject<EntityType<LostMinerEntity>> LOST_MINER = ENTITY_TYPES.register("lost_miner", () ->
            EntityType.Builder.<LostMinerEntity>create(LostMinerEntity::new, EntityClassification.MONSTER)
                    .size(0.6F, 1.99F)
                    .build(new ResourceLocation(Sandbox.MOD_ID, "lost_miner").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }


}
