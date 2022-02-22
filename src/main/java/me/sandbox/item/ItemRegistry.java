package me.sandbox.item;

import me.sandbox.Sandbox;
import me.sandbox.entity.EntityRegistry;
import me.sandbox.item.custom.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegistry {

    //GENERAL ITEMS
    public static final Item ENDERGON_REFUSE = registerItem("endergon_refuse",
            new Item(new FabricItemSettings().group(ModItemGroup.SandBoxMisc).fireproof()));
    public static final Item ENDERGON_INGOT = registerItem("endergon_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.SandBoxMisc).fireproof()));
    public static final Item ENDERGON_CONDUIT = registerItem("endergon_conduit",
            new Item(new FabricItemSettings().group(ModItemGroup.SandBoxMisc).fireproof()));
    public static final Item UNUSUAL_DUST = registerItem("unusual_dust",
            new Item(new FabricItemSettings().group(ModItemGroup.SandBoxMisc)));
    public static final Item ILLUSIONARY_DUST = registerItem("illusionary_dust",
            new IllusionaryDustItem(new FabricItemSettings().group(ModItemGroup.SandBoxMisc)));
    public static final Item RAVAGER_HORN = registerItem("ravager_horn",
            new Item(new FabricItemSettings().group(ModItemGroup.SandBoxMisc)));

    //TOOLS
    public static final Item LOST_CANDLE = registerItem("lost_candle",
            new LostCandleItem(new FabricItemSettings().group(ModItemGroup.SandBoxMisc)));
    public static final Item ENDERGON_SWORD = registerItem("endergon_sword",
            new SwordItem(ModToolMaterials.ENDERGON, 4, -2.4f,
                    new FabricItemSettings().group(ModItemGroup.SandBoxMisc).fireproof()));
    public static final Item ENDERGON_AXE = registerItem("endergon_axe",
            new ModAxeItem(ModToolMaterials.ENDERGON, 7, -3f,
                    new FabricItemSettings().group(ModItemGroup.SandBoxMisc).fireproof()));
    public static final Item ENDERGON_HOE = registerItem("endergon_hoe",
            new ModHoeItem(ModToolMaterials.ENDERGON, -4, 0f,
                    new FabricItemSettings().group(ModItemGroup.SandBoxMisc).fireproof()));
    public static final Item ENDERGON_SHOVEL = registerItem("endergon_shovel",
            new ShovelItem(ModToolMaterials.ENDERGON, 2, -3f,
                    new FabricItemSettings().group(ModItemGroup.SandBoxMisc).fireproof()));
    public static final Item ENDERGON_PICKAXE = registerItem("endergon_pickaxe",
            new ModPickaxeItem(ModToolMaterials.ENDERGON, 1, -2.8f,
                    new FabricItemSettings().group(ModItemGroup.SandBoxMisc).fireproof()));


    //ARMOR
    public static final Item ENDERGON_HELMET = registerItem("endergon_helmet",
            new ArmorItem(ModArmorMaterial.ENDERGON, EquipmentSlot.HEAD, new FabricItemSettings().group(ModItemGroup.SandBoxMisc).fireproof()));
    public static final Item ENDERGON_CHESTPLATE = registerItem("endergon_chestplate",
            new ArmorItem(ModArmorMaterial.ENDERGON, EquipmentSlot.CHEST, new FabricItemSettings().group(ModItemGroup.SandBoxMisc).fireproof()));
    public static final Item ENDERGON_LEGGINGS = registerItem("endergon_leggings",
            new ArmorItem(ModArmorMaterial.ENDERGON, EquipmentSlot.LEGS, new FabricItemSettings().group(ModItemGroup.SandBoxMisc).fireproof()));
    public static final Item ENDERGON_BOOTS = registerItem("endergon_boots",
            new ArmorItem(ModArmorMaterial.ENDERGON, EquipmentSlot.FEET, new FabricItemSettings().group(ModItemGroup.SandBoxMisc).fireproof()));

    //SPAWN EGGS
    public static final Item PROVOKER_SPAWN_EGG = registerItem("provoker_spawn_egg",
            new SpawnEggItem(EntityRegistry.PROVOKER,9541270,9399876, new Item.Settings().group(ModItemGroup.SandBoxMobs)));
    public static final Item LOST_MINER_SPAWN_EGG = registerItem("lost_miner_spawn_egg",
            new SpawnEggItem(EntityRegistry.LOST_MINER,12895428,6704946, new Item.Settings().group(ModItemGroup.SandBoxMobs)));
    public static final Item SURRENDERED_SPAWN_EGG = registerItem("surrendered_spawn_egg",
            new SpawnEggItem(EntityRegistry.SURRENDERED,11260369,11858160, new Item.Settings().group(ModItemGroup.SandBoxMobs)));
    public static final Item NECROMANCER_SPAWN_EGG = registerItem("necromancer_spawn_egg",
            new SpawnEggItem(EntityRegistry.NECROMANCER,9541270,9585210, new Item.Settings().group(ModItemGroup.SandBoxMobs)));
    public static final Item BASHER_SPAWN_EGG = registerItem("basher_spawn_egg",
            new SpawnEggItem(EntityRegistry.BASHER,9541270,5985087, new Item.Settings().group(ModItemGroup.SandBoxMobs)));
    public static final Item SORCERER_SPAWN_EGG = registerItem("sorcerer_spawn_egg",
            new SpawnEggItem(EntityRegistry.SORCERER,9541270,10899592, new Item.Settings().group(ModItemGroup.SandBoxMobs)));




    public static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Sandbox.MOD_ID, name), item);
    }


    public static void registerModItems() {
        Sandbox.LOGGER.info("Registering items...");
    }
}
