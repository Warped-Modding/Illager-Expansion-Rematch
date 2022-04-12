package me.sandbox.gui;

import me.sandbox.item.ItemRegistry;
import me.sandbox.sounds.SoundRegistry;
import me.sandbox.util.ImbueUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;

import java.util.Map;

public class ImbuingTableScreenHandler extends ScreenHandler {
    public static boolean bigBook;
    public static boolean badEnchant;
    public static boolean lowEnchant;
    protected final CraftingResultInventory output = new CraftingResultInventory();
    final Inventory input = new SimpleInventory(3){
        @Override
        public void markDirty() {
            super.markDirty();
            ImbuingTableScreenHandler.this.onContentChanged(this);
        }};
    private final ScreenHandlerContext context;

    public ImbuingTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public ImbuingTableScreenHandler(int syncId, PlayerInventory playerInventory, final ScreenHandlerContext context) {
        super(ModdedScreenHandler.IMBUING_TABLE_SCREEN_HANDLER, syncId);
        this.context = context;
        int i;
        this.addSlot(new Slot(this.input, 0, 26, 54){
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(Items.ENCHANTED_BOOK);
            }
                @Override
                public void onTakeItem(PlayerEntity playerEntity, ItemStack itemStack) {
                    updateBooleans(false);
                }
            });
        this.addSlot(new Slot(this.input, 1, 80, 54){
            @Override
            public void onTakeItem(PlayerEntity playerEntity, ItemStack itemStack) {
                updateBooleans(false);
            }
        });
        this.addSlot(new Slot(this.input, 2, 134, 54){
            @Override
            public void onTakeItem(PlayerEntity playerEntity, ItemStack itemStack) {
                updateBooleans(false);
            }
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(ItemRegistry.HALLOWED_GEM);
            }});
        this.addSlot(new Slot(this.output, 3, 80, 14) {
            @Override
            public boolean canInsert(ItemStack stack) {return false;}
            @Override
            public boolean canTakeItems(PlayerEntity playerEntity) {return true;}
            @Override
            public void onTakeItem(PlayerEntity playerEntity, ItemStack itemStack) {
                input.setStack(0, ItemStack.EMPTY);
                input.setStack(1, ItemStack.EMPTY);
                input.getStack(2).increment(-1);
                updateBooleans(false);
                playerEntity.playSound(SoundRegistry.SORCERER_COMPLETE_CAST, 1.0f, 1.0f);
            }
        });
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
        if (inventory == this.input) {
            this.updateResult();
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
    protected boolean isUsableAsAddition(ItemStack stack) {
        return false;
    }
    public void updateBooleans(boolean value) {
        bigBook = value;
        badEnchant = value;
        lowEnchant = value;
    }
    public void updateResult() {
        ItemStack imbuingItem = this.input.getStack(1);
        ItemStack book = this.input.getStack(0);
        ItemStack gem = this.input.getStack(2);
        ItemStack imbuingResult = imbuingItem.copy();
        Map<Enchantment, Integer> bookmap = EnchantmentHelper.get(book);
        if (!book.isEmpty() && !gem.isEmpty()) {
            for (Enchantment bookEnchantment : bookmap.keySet()) {
                if (bookmap.size() > 1) {
                    bigBook = true;
                } else if (ImbueUtil.getBadEnchants().contains(bookEnchantment.getTranslationKey())) {
                    badEnchant = true;
                } else if (bookmap.getOrDefault(bookEnchantment, 0) != bookEnchantment.getMaxLevel()) {
                    lowEnchant = true;
                } else {
                    int imbueLevel = bookmap.get(bookEnchantment) + 1;
                    Map<Enchantment, Integer> imbueMap = EnchantmentHelper.get(imbuingItem);
                    for (Enchantment imbueEnchant : imbueMap.keySet()) {
                        int level = imbueMap.getOrDefault(imbueEnchant, 0);
                        bookmap.put(imbueEnchant, level);
                    }
                    bookmap.put(bookEnchantment, imbueLevel);
                    EnchantmentHelper.set(bookmap, imbuingResult);
                    this.output.setStack(0, imbuingResult);
                    updateBooleans(false);
                    this.sendContentUpdates();
                }
            }
            } else {
            output.setStack(0, ItemStack.EMPTY);
        }
    }
    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index == 3) {
                if (!this.insertItem(itemStack2, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (index == 0 || index == 1 || index == 2) {
                if (!this.insertItem(itemStack2, 3, 39, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 3 && index < 39) {
                int i;
                int n = i = this.isUsableAsAddition(itemStack) ? 1 : 0;
                if (!this.insertItem(itemStack2, i, 3, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTakeItem(player, itemStack2);
        }
        return itemStack;
    }
    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.dropInventory(player, input);
        this.updateBooleans(false);
    }
}