package me.sandbox.item;

import me.sandbox.Sandbox;
import me.sandbox.entity.ModEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModItems {
    
    //Item registry

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Sandbox.MOD_ID);

    //Register Items here

    public static final RegistryObject<Item> ENDERGON_INGOT = ITEMS.register("endergon_ingot",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> ENDERGON_REFUSE = ITEMS.register("endergon_refuse",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<Item> ENDERGON_HELMET = ITEMS.register("endergon_helmet", () ->
            new ArmorItem(ModArmorMaterial.ENDERGON, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> ENDERGON_CHESTPLATE = ITEMS.register("endergon_chestplate", () ->
            new ArmorItem(ModArmorMaterial.ENDERGON, EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> ENDERGON_LEGGINGS = ITEMS.register("endergon_leggings", () ->
            new ArmorItem(ModArmorMaterial.ENDERGON, EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> ENDERGON_BOOTS = ITEMS.register("endergon_boots", () ->
            new ArmorItem(ModArmorMaterial.ENDERGON, EquipmentSlot.FEET, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));






    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


}
