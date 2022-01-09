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

    public static final RegistryObject<Item> ENDERITE_INGOT = ITEMS.register("enderite_ingot",
            () -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item> ENDERITE_HELMET = ITEMS.register("enderite_helmet", () ->
            new ArmorItem(ModArmorMaterial.ENDERITE, EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<Item> ENDERITE_CHESTPLATE = ITEMS.register("enderite_chestplate", () ->
            new ArmorItem(ModArmorMaterial.ENDERITE, EquipmentSlotType.CHEST, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<Item> ENDERITE_LEGGINGS = ITEMS.register("enderite_leggings", () ->
            new ArmorItem(ModArmorMaterial.ENDERITE, EquipmentSlotType.LEGS, new Item.Properties().group(ItemGroup.COMBAT)));
    public static final RegistryObject<Item> ENDERITE_BOOTS = ITEMS.register("enderite_boots", () ->
            new ArmorItem(ModArmorMaterial.ENDERITE, EquipmentSlotType.FEET, new Item.Properties().group(ItemGroup.COMBAT)));




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


}
