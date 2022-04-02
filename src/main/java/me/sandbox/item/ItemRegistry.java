package me.sandbox.item;

import me.sandbox.IllagerExpansion;
import me.sandbox.entity.EntityRegistry;
import me.sandbox.item.custom.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegistry {

    //GENERAL ITEMS
    public static final Item UNUSUAL_DUST = registerItem("unusual_dust",
            new Item(new FabricItemSettings().group(ModItemGroup.SandBoxMisc)));
    public static final Item ILLUSIONARY_DUST = registerItem("illusionary_dust",
            new IllusionaryDustItem(new FabricItemSettings().group(ModItemGroup.SandBoxMisc)));
    public static final Item RAVAGER_HORN = registerItem("ravager_horn",
            new Item(new FabricItemSettings().group(ModItemGroup.SandBoxMisc)));
    public static final Item GILDED_HORN = registerItem("gilded_horn",
            new Item(new FabricItemSettings().group(ModItemGroup.SandBoxMisc)));
    public static final Item HORN_OF_SIGHT = registerItem("horn_of_sight",
            new HornOfSightItem(new FabricItemSettings().group(ModItemGroup.SandBoxMisc).maxCount(1)));
    public static final Item HALLOWED_GEM = registerItem("hallowed_gem",
            new Item(new FabricItemSettings().group(ModItemGroup.SandBoxMisc)));
    public static final Item PRIMAL_ESSENCE = registerItem("primal_essence",
            new Item(new FabricItemSettings().group(ModItemGroup.SandBoxMisc)));


    //TOOLS
    public static final Item HATCHET = registerItem("hatchet",
            new HatchetItem(new FabricItemSettings().maxDamage(250).group(ModItemGroup.SandBoxMisc)));


    //SPAWN EGGS
    public static final Item PROVOKER_SPAWN_EGG = registerItem("provoker_spawn_egg",
            new SpawnEggItem(EntityRegistry.PROVOKER,9541270,9399876, new Item.Settings().group(ModItemGroup.SandBoxMobs)));
    public static final Item SURRENDERED_SPAWN_EGG = registerItem("surrendered_spawn_egg",
            new SpawnEggItem(EntityRegistry.SURRENDERED,11260369,11858160, new Item.Settings().group(ModItemGroup.SandBoxMobs)));
    public static final Item NECROMANCER_SPAWN_EGG = registerItem("necromancer_spawn_egg",
            new SpawnEggItem(EntityRegistry.NECROMANCER,9541270,9585210, new Item.Settings().group(ModItemGroup.SandBoxMobs)));
    public static final Item BASHER_SPAWN_EGG = registerItem("basher_spawn_egg",
            new SpawnEggItem(EntityRegistry.BASHER,9541270,5985087, new Item.Settings().group(ModItemGroup.SandBoxMobs)));
    public static final Item SORCERER_SPAWN_EGG = registerItem("sorcerer_spawn_egg",
            new SpawnEggItem(EntityRegistry.SORCERER,9541270,10899592, new Item.Settings().group(ModItemGroup.SandBoxMobs)));
    public static final Item ARCHIVIST_SPAWN_EGG = registerItem("archivist_spawn_egg",
            new SpawnEggItem(EntityRegistry.ARCHIVIST,9541270,13251893, new Item.Settings().group(ModItemGroup.SandBoxMobs)));
    public static final Item ILLAGER_BRUTE_SPAWN_EGG = registerItem("inquisitor_spawn_egg",
            new SpawnEggItem(EntityRegistry.INQUISITOR,9541270,4934471, new Item.Settings().group(ModItemGroup.SandBoxMobs)));
    public static final Item MARAUDER_SPAWN_EGG = registerItem("marauder_spawn_egg",
            new SpawnEggItem(EntityRegistry.MARAUDER,5441030,9541270, new Item.Settings().group(ModItemGroup.SandBoxMobs)));
    public static final Item ALCHEMIST_SPAWN_EGG = registerItem("alchemist_spawn_egg",
            new SpawnEggItem(EntityRegistry.ALCHEMIST,9541270,7550099, new Item.Settings().group(ModItemGroup.SandBoxMobs)));
    public static final Item FIRECALLER_SPAWN_EGG = registerItem("firecaller_spawn_egg",
            new SpawnEggItem(EntityRegistry.FIRECALLER,9541270,14185784, new Item.Settings().group(ModItemGroup.SandBoxMobs)));



    public static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(IllagerExpansion.MOD_ID, name), item);
    }


    public static void registerModItems() {
    }
}
