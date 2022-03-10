package me.sandbox.util.spellutil;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.MendingEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.LocalDifficulty;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class EnchantToolUtil {
    public static final Set<Item> MELEE_ITEM;
    static {MELEE_ITEM = Sets.newHashSet(Items.GOLDEN_SWORD, Items.IRON_AXE, Items.STONE_AXE, Items.DIAMOND_AXE);}

    public void doEnchant(Enchantment enchantment, int enchantLevel, LivingEntity entity) {
        ItemStack mainhanditem = entity.getEquippedStack(EquipmentSlot.MAINHAND);
        ItemStack offhanditem = entity.getEquippedStack(EquipmentSlot.OFFHAND);
        HashMap<Enchantment, Integer> map = Maps.newHashMap();
        map.put(enchantment, enchantLevel);
        EnchantmentHelper.set(map, mainhanditem);
        EnchantmentHelper.set(map, offhanditem);
    }

    public void enchant(LivingEntity entity) {
        ItemStack mainhanditem = entity.getEquippedStack(EquipmentSlot.MAINHAND);
        ItemStack offhanditem = entity.getEquippedStack(EquipmentSlot.OFFHAND);
        if (mainhanditem.isOf(Items.BOW) || offhanditem.isOf(Items.BOW)) {
            doEnchant(Enchantments.POWER, 3, entity);
            }
        if (MELEE_ITEM.contains(mainhanditem.getItem()) || MELEE_ITEM.contains(offhanditem.getItem())) {
            doEnchant(Enchantments.SHARPNESS, 3, entity);
        }
        if (mainhanditem.isOf(Items.CROSSBOW) || mainhanditem.isOf(Items.CROSSBOW)) {
            Random random = new Random();
            int randvalue = random.nextInt(2);
            if (randvalue == 1) {
                doEnchant(Enchantments.PIERCING, 4, entity);
            }
            if (randvalue == 0) {
                doEnchant(Enchantments.MULTISHOT, 1, entity);
            }
        }
        entity.equipStack(EquipmentSlot.MAINHAND, mainhanditem);
        entity.equipStack(EquipmentSlot.OFFHAND, offhanditem);


    }
}
