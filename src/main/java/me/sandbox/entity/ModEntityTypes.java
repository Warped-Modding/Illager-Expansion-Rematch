package me.sandbox.entity;

import me.sandbox.Sandbox;
import me.sandbox.entity.custom.LostMinerEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class ModEntityTypes {

    public static DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, Sandbox.MOD_ID);

    public static final RegistryObject<EntityType<LostMinerEntity>> LOST_MINER = ENTITY_TYPES.register("lost_miner", () ->
            EntityType.Builder.of(LostMinerEntity::new, MobCategory.MONSTER)
                    .build(new ResourceLocation(Sandbox.MOD_ID, "lost_miner").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }


}
