package me.sandbox.mixin;

import me.sandbox.config.IllagerExpansionConfig;
import me.sandbox.entity.*;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.raid.Raid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Raid.class)
public class RaidMixin {
    @Inject(at = @At("TAIL"), cancellable = true, method = "addRaider(ILnet/minecraft/entity/raid/RaiderEntity;Lnet/minecraft/util/math/BlockPos;Z)V")
    public void discardRaider(int wave, RaiderEntity raider, BlockPos pos, boolean existing, CallbackInfo ci) {
        IllagerExpansionConfig config = AutoConfig.getConfigHolder(IllagerExpansionConfig.class).getConfig();
        if (!config.addIllagersToRaids) {
            if (raider instanceof ArchivistEntity || raider instanceof BasherEntity || raider instanceof AlchemistEntity || raider instanceof InquisitorEntity || raider instanceof MarauderEntity || raider instanceof NecromancerEntity ||raider instanceof ProvokerEntity || raider instanceof SorcererEntity) {
                raider.discard();
            }
        }
    }
}
