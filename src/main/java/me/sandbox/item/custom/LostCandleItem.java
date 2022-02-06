package me.sandbox.item.custom;

import me.sandbox.sounds.SoundRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.BaseText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.w3c.dom.ranges.Range;

import java.util.Arrays;
import java.util.List;

public class LostCandleItem extends Item {
    public LostCandleItem(Settings settings) {
        super(settings);
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient) {
            World world = context.getWorld();
            PlayerEntity player = context.getPlayer();
            BlockPos playerlocation = player.getBlockPos();
            boolean foundOres = false;
            player.getItemCooldownManager().set(this, 60);

            for (BlockPos blockRadius : BlockPos.iterateOutwards(playerlocation, 10, 10, 10)) {
                Block blockInRadius = world.getBlockState(blockRadius).getBlock();
                if (isOre(blockInRadius)) {
                    foundOres = true;
                    if (blockInRadius == Blocks.DIAMOND_ORE || blockInRadius == Blocks.DEEPSLATE_DIAMOND_ORE) {
                        world.playSound(player, playerlocation, SoundRegistry.LOST_CANDLE_DIAMOND, SoundCategory.AMBIENT, 1.0F, 1.0F);
                    }
                    if (blockInRadius == Blocks.IRON_ORE || blockInRadius == Blocks.DEEPSLATE_IRON_ORE) {
                        world.playSound(player,playerlocation, SoundRegistry.LOST_CANDLE_IRON, SoundCategory.AMBIENT, 1.0F, 1.0F);
                    }
                    if (blockInRadius == Blocks.GOLD_ORE || blockInRadius == Blocks.DEEPSLATE_GOLD_ORE) {
                        world.playSound(player, playerlocation, SoundRegistry.LOST_CANDLE_GOLD, SoundCategory.AMBIENT, 1.0F, 1.0F);
                    }
                    if (blockInRadius == Blocks.COPPER_ORE || blockInRadius == Blocks.DEEPSLATE_COPPER_ORE) {
                        world.playSound(player, playerlocation, SoundRegistry.LOST_CANDLE_COPPER, SoundCategory.AMBIENT, 1.0F, 1.0F);
                    }
                    if (blockInRadius == Blocks.COAL_ORE || blockInRadius == Blocks.DEEPSLATE_COAL_ORE) {
                        world.playSound(player, playerlocation, SoundRegistry.LOST_CANDLE_COAL, SoundCategory.AMBIENT, 1.0F, 1.0F);
                    }
                    break;
                }
            }
            if (!foundOres) {
                world.playSound(player, playerlocation, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.AMBIENT, 0.6F, 1.0F);
            }
        }
        return super.useOnBlock(context);
    }

    private boolean isOre(Block block) {
        return block == Blocks.COAL_ORE || block == Blocks.COPPER_ORE || block == Blocks.IRON_ORE || block == Blocks.DIAMOND_ORE || block == Blocks.GOLD_ORE
                || block == Blocks.DEEPSLATE_COAL_ORE || block == Blocks.DEEPSLATE_COPPER_ORE || block == Blocks.DEEPSLATE_IRON_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE || block == Blocks.DEEPSLATE_GOLD_ORE;
    }
}
