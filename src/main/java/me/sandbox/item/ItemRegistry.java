package me.sandbox.item;

import me.sandbox.Sandbox;
import me.sandbox.item.custom.LostCandleItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegistry {

    //GENERAL ITEMS
    public static final Item ENDERGON_REFUSE = registerItem("endergon_refuse",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item ENDERGON_INGOT = registerItem("endergon_ingot",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item ENDERGON_CONDUIT = registerItem("endergon_conduit",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item LOST_CANDLE = registerItem("lost_candle",
            new LostCandleItem(new FabricItemSettings().group(ItemGroup.TOOLS)));

    //ARMOR
    public static final Item ENDERGON_HELMET = registerItem("endergon_helmet",
            new ArmorItem(ModArmorMaterial.ENDERGON, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT)));
    public static final Item ENDERGON_CHESTPLATE = registerItem("endergon_chestplate",
            new ArmorItem(ModArmorMaterial.ENDERGON, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT)));
    public static final Item ENDERGON_LEGGINGS = registerItem("endergon_leggings",
            new ArmorItem(ModArmorMaterial.ENDERGON, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT)));
    public static final Item ENDERGON_BOOTS = registerItem("endergon_boots",
            new ArmorItem(ModArmorMaterial.ENDERGON, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT)));



    public static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Sandbox.MOD_ID, name), item);
    }


    public static void registerModItems() {
        Sandbox.LOGGER.info("Registering items...");
    }
}
