package me.sandbox.entity;

import me.sandbox.Sandbox;
import me.sandbox.entity.projectile.HatchetEntity;
import me.sandbox.entity.projectile.SkullboltEntity;
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
    public static final EntityType<FlamecallerEntity> FLAMECALLER = Registry.register(Registry.ENTITY_TYPE, new Identifier(Sandbox.MOD_ID,"flamecaller"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, FlamecallerEntity::new).dimensions(EntityDimensions.fixed(0.5f, 1.92f)).build()
    );
    public static final EntityType<ArchivistEntity> ARCHIVIST = Registry.register(Registry.ENTITY_TYPE, new Identifier(Sandbox.MOD_ID,"archivist"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ArchivistEntity::new).dimensions(EntityDimensions.fixed(0.74f, 1.92f)).build()
    );
    public static final EntityType<InquisitorEntity> INQUISITOR = Registry.register(Registry.ENTITY_TYPE, new Identifier(Sandbox.MOD_ID,"inquisitor"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, InquisitorEntity::new).dimensions(EntityDimensions.fixed(0.5f, 2.48f)).build()
    );
    public static final EntityType<MarauderEntity> MARAUDER = Registry.register(Registry.ENTITY_TYPE, new Identifier(Sandbox.MOD_ID,"marauder"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, MarauderEntity::new).dimensions(EntityDimensions.fixed(0.5f, 1.92f)).build()
    );
    public static final EntityType<AlchemistEntity> ALCHEMIST = Registry.register(Registry.ENTITY_TYPE, new Identifier(Sandbox.MOD_ID,"alchemist"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, AlchemistEntity::new).dimensions(EntityDimensions.fixed(0.5f, 1.92f)).build()
    );
    public static final EntityType<SurrenderedEntity> SURRENDERED = Registry.register(Registry.ENTITY_TYPE, new Identifier(Sandbox.MOD_ID,"surrendered"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SurrenderedEntity::new).fireImmune().dimensions(EntityDimensions.fixed(0.5f, 1.42f)).build()
    );
    public static final EntityType<SkullboltEntity> SKULLBOLT = Registry.register(Registry.ENTITY_TYPE, new Identifier(Sandbox.MOD_ID, "skullbolt"),
            FabricEntityTypeBuilder.<SkullboltEntity>create(SpawnGroup.MISC, ((entityType, world) -> (new SkullboltEntity(entityType, world)))).dimensions(EntityDimensions.fixed(0.3f,0.3f)).trackRangeBlocks(4).trackedUpdateRate(10).build()
    );
    public static final EntityType<HatchetEntity> HATCHET = Registry.register(Registry.ENTITY_TYPE, new Identifier(Sandbox.MOD_ID, "hatchet"),
            FabricEntityTypeBuilder.<HatchetEntity>create(SpawnGroup.MISC, ((entityType, world) -> (new HatchetEntity(entityType, world)))).dimensions(EntityDimensions.fixed(0.35f,0.35f)).trackRangeBlocks(4).trackedUpdateRate(10).build()
    );
    public static final EntityType<InvokerFangsEntity> INVOKER_FANGS = Registry.register(Registry.ENTITY_TYPE, new Identifier(Sandbox.MOD_ID,"invoker_fangs"),
            FabricEntityTypeBuilder.<InvokerFangsEntity>create(SpawnGroup.MISC, (entityType, world) -> (new InvokerFangsEntity(entityType, world))).dimensions(EntityDimensions.fixed(0.65f, 1.05f)).build()
    );

    public static void registerEntities() {
        Sandbox.LOGGER.info("Registering mobs...");
    }

}
