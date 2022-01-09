package me.sandbox.item;

import me.sandbox.Sandbox;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    
    //Item registry

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Sandbox.MOD_ID);

    //Register Items here

    public static final RegistryObject<Item> ENDERGON_INGOT = ITEMS.register("endergon_ingot",
            () -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item> ENDERGON_HELMET = ITEMS.register("endergon_helmet", () ->
            new ArmorItem(ModArmorMaterial.ENDERGON, EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<Item> ENDERGON_CHESTPLATE = ITEMS.register("endergon_chestplate", () ->
            new ArmorItem(ModArmorMaterial.ENDERGON, EquipmentSlotType.CHEST, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<Item> ENDERGON_LEGGINGS = ITEMS.register("endergon_leggings", () ->
            new ArmorItem(ModArmorMaterial.ENDERGON, EquipmentSlotType.LEGS, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<Item> ENDERGON_BOOTS = ITEMS.register("endergon_boots", () ->
            new ArmorItem(ModArmorMaterial.ENDERGON, EquipmentSlotType.FEET, new Item.Properties().group(ItemGroup.COMBAT)));




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


}
