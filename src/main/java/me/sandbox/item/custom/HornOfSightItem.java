package me.sandbox.item.custom;

import me.sandbox.sounds.SoundRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.List;

public class HornOfSightItem extends Item {

    public HornOfSightItem(Settings settings) {
        super(settings);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int i;
        float f;
        if (!(user instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity playerEntity = (PlayerEntity)user;
        ItemStack itemStack = playerEntity.getArrowType(stack);
        if (itemStack.isEmpty()) {
            return;
        }
        if ((double)(f = BowItem.getPullProgress(i = this.getMaxUseTime(stack) - remainingUseTicks)) < 0.1) {
            return;
        }
        world.playSound(user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_BELL_RESONATE, SoundCategory.PLAYERS, 1.0f, 1.0f, true);
        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        playerEntity.getItemCooldownManager().set(this, 30);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 60;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    private List<LivingEntity> getTargets(PlayerEntity user) {
        return user.world.getEntitiesByClass(LivingEntity.class, user.getBoundingBox().expand(30), entity -> (entity instanceof LivingEntity) && !(entity instanceof PlayerEntity));
    }
    private void glow(LivingEntity entity) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 400, 0));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        world.playSound(user.getX(), user.getY(), user.getZ(), SoundRegistry.HORN_OF_SIGHT, SoundCategory.PLAYERS, 1.0f, 1.0f, true);
            ItemStack itemStack = user.getStackInHand(hand);
                getTargets(user).forEach(this::glow);
                user.setCurrentHand(hand);
                user.getItemCooldownManager().set(this, 80);
                return TypedActionResult.consume(itemStack);
            }
    }

