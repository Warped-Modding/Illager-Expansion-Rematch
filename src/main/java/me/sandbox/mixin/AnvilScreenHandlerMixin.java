package me.sandbox.mixin;

import me.sandbox.util.ImbueUtil;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.CallbackI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin{


    @Redirect(method = "updateResult()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I"))
    public int setMaxLevel(Enchantment enchantment) {
        if(!ImbueUtil.getBadEnchants().contains(enchantment.getTranslationKey())) {
            return enchantment.getMaxLevel() +1;
        }
        return enchantment.getMaxLevel();
    }
    @Inject(method = "updateResult", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void changeUpdateResult(CallbackInfo ci, ItemStack itemStack, int i, int j, int k, ItemStack itemStack3) {
    }


}
