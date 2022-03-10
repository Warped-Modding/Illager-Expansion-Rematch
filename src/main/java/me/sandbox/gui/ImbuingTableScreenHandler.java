package me.sandbox.gui;

import me.sandbox.gui.ModdedScreenHandler;
import me.sandbox.item.ItemRegistry;
import me.sandbox.sounds.SoundRegistry;
import me.sandbox.util.ImbueUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class ImbuingTableScreenHandler
        extends ForgingScreenHandler {
    public boolean bigBook;
    public boolean atMax;
    public boolean tooLow;
    public boolean badItems;
    public boolean badEnchant;
    private String newItemName;
    private final Property levelCost = Property.create();

    public ImbuingTableScreenHandler(int syncId, PlayerInventory inventory) {
        this(syncId, inventory, ScreenHandlerContext.EMPTY);
    }

    public ImbuingTableScreenHandler(int syncId, PlayerInventory inventory, ScreenHandlerContext context) {
        super(ModdedScreenHandler.IMBUING_TABLE_SCREEN_HANDLER, syncId, inventory, context);
    }

    @Override
    protected boolean canUse(BlockState state) {
        return state.isIn(BlockTags.ANVIL);
    }

    @Override
    protected boolean canTakeOutput(PlayerEntity player, boolean present) {
        return true;
    }

    @Override
    protected void onTakeOutput(PlayerEntity player, ItemStack stack) {
        this.input.setStack(0, ItemStack.EMPTY);
        this.input.getStack(1).decrement(1);
        World world = player.getWorld();
        world.playSound(player.getX(), player.getY(), player.getZ(), SoundRegistry.SORCERER_COMPLETE_CAST, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
    }
    @Override
    public void updateResult() {
        ItemStack itemslot1 = this.input.getStack(0);
        ItemStack itemslot2 = this.input.getStack(1);
        ItemStack result = this.input.getStack(0).copy();
        if (itemslot1.isOf(Items.ENCHANTED_BOOK) && itemslot2.isOf(ItemRegistry.HALLOWED_GEM)) {
            Map<Enchantment, Integer> map = EnchantmentHelper.get(itemslot1);
            bigBook = map.size() > 1;
            for (Enchantment enchantment : map.keySet()) {
                if (ImbueUtil.getBadEnchants().contains(enchantment.getTranslationKey())) {
                    this.badEnchant = true;
                    continue;
                }
                int level = map.get(enchantment).intValue();
                tooLow = level < enchantment.getMaxLevel();
                atMax = level > enchantment.getMaxLevel();
                if (bigBook) continue;
                if (enchantment == null) continue;
                if (!(level == enchantment.getMaxLevel())) continue;
                map.put(enchantment, level + 1);
                EnchantmentHelper.set(map, result);
                this.output.setStack(0, result);
                setAllBooleans(false);
            }
        }
        if (!(itemslot1.isOf(Items.ENCHANTED_BOOK)) || !(itemslot2.isOf(ItemRegistry.HALLOWED_GEM))) {
            badItems = true;
        }
        if (itemslot1.isEmpty()) {
            setAllBooleans(false);
            this.output.setStack(0, ItemStack.EMPTY);
        }
        if (itemslot2.isEmpty()) {
            this.output.setStack(0, ItemStack.EMPTY);
            setAllBooleans(false);
        }
        this.sendContentUpdates();
    }
    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.dropInventory(player, this.input);
    }
    public void setAllBooleans(boolean value) {
        bigBook = value;
        atMax = value;
        tooLow = value;
        badEnchant = value;
        badItems = value;
    }
}