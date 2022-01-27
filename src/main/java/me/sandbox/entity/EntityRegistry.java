package me.sandbox.entity;

import me.sandbox.Sandbox;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityRegistry {

    public static final EntityType<LostMinerEntity> LOST_MINER = Registry.register(Registry.ENTITY_TYPE, new Identifier(Sandbox.MOD_ID, "lost_miner"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, LostMinerEntity::new).dimensions(EntityDimensions.fixed(0.5f, 1.92f)).build()
    );
    public static void registerEntities() {
        Sandbox.LOGGER.info("Registering mobs...");
    }

}
