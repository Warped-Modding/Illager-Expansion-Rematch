package me.sandbox.entity;

import me.sandbox.Sandbox;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

public class EntityRegistry {


    //Register Entities
    public static final EntityType<ProvokerEntity> PROVOKER = Registry.register(Registry.ENTITY_TYPE, new Identifier(Sandbox.MOD_ID,"provoker"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ProvokerEntity::new).dimensions(EntityDimensions.fixed(0.5f, 1.92f)).build()
    );
    public static final EntityType<InvokerEntity> INVOKER = Registry.register(Registry.ENTITY_TYPE, new Identifier(Sandbox.MOD_ID,"invoker"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, InvokerEntity::new).dimensions(EntityDimensions.fixed(0.5f, 1.92f)).build()
    );
    public static final EntityType<NecromancerEntity> NECROMANCER = Registry.register(Registry.ENTITY_TYPE, new Identifier(Sandbox.MOD_ID,"necromancer"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, NecromancerEntity::new).dimensions(EntityDimensions.fixed(0.5f, 1.92f)).build()
    );
    public static final EntityType<BasherEntity> BASHER = Registry.register(Registry.ENTITY_TYPE, new Identifier(Sandbox.MOD_ID,"basher"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BasherEntity::new).dimensions(EntityDimensions.fixed(0.5f, 1.92f)).build()
    );
    public static final EntityType<SorcererEntity> SORCERER = Registry.register(Registry.ENTITY_TYPE, new Identifier(Sandbox.MOD_ID,"sorcerer"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SorcererEntity::new).dimensions(EntityDimensions.fixed(0.5f, 1.92f)).build()
    );
    public static final EntityType<SurrenderedEntity> SURRENDERED = Registry.register(Registry.ENTITY_TYPE, new Identifier(Sandbox.MOD_ID,"surrendered"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SurrenderedEntity::new).fireImmune().dimensions(EntityDimensions.fixed(0.5f, 1.42f)).build()
    );
    public static final EntityType<ShadowSpikeEntity> SHADOW_SPIKE = Registry.register(Registry.ENTITY_TYPE, new Identifier(Sandbox.MOD_ID,"shadow_spike"),
            FabricEntityTypeBuilder.<ShadowSpikeEntity>create(SpawnGroup.MISC, (entityType, world) -> (new ShadowSpikeEntity(entityType, world))).dimensions(EntityDimensions.fixed(0.3f, 0.5f)).build()
    );
    public static final EntityType<MushroomlingEntity> MUSHROOMLING = Registry.register(Registry.ENTITY_TYPE, new Identifier(Sandbox.MOD_ID,"mushroomling"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MushroomlingEntity::new).dimensions(EntityDimensions.fixed(0.5f, 1.92f)).build()
    );
    public static final EntityType<SkullboltEntity> SKULLBOLT = Registry.register(Registry.ENTITY_TYPE, new Identifier(Sandbox.MOD_ID, "skullbolt"),
            FabricEntityTypeBuilder.<SkullboltEntity>create(SpawnGroup.MISC, ((entityType, world) -> (new SkullboltEntity(entityType, world)))).dimensions(EntityDimensions.fixed(0.3f,0.3f)).trackRangeBlocks(4).trackedUpdateRate(10).build()
    );

    public static final EntityType<LostMinerEntity> LOST_MINER = Registry.register(Registry.ENTITY_TYPE, new Identifier(Sandbox.MOD_ID,"lost_miner"),
            FabricEntityTypeBuilder.createMob()
            .spawnGroup(SpawnGroup.MONSTER)
            .entityFactory(LostMinerEntity::new)
            .dimensions(EntityDimensions.changing(0.6F, 1.9F))
            .spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LostMinerEntity::canSpawn)
            .build()
    );

    public static void registerEntities() {
        Sandbox.LOGGER.info("Registering mobs...");
    }

}
