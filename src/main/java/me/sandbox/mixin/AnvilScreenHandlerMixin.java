package me.sandbox.mixin;

import me.sandbox.util.ImbueUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {
    @Redirect(method = "updateResult()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I"))

    private int setMaxLevel(Enchantment enchantment) {
        if(!ImbueUtil.getBadEnchants().contains(enchantment.getTranslationKey())) {
            return enchantment.getMaxLevel() +1;
        }
        return enchantment.getMaxLevel();
    }
}
