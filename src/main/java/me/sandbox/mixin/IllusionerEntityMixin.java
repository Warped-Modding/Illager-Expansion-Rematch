
package me.sandbox.mixin;

import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundEvent;
import net.minecraft.entity.player.PlayerEntity;
import java.util.List;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.IllusionerEntity;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.mob.IllagerEntity;

@Mixin(IllusionerEntity.class)
public class IllusionerEntityMixin extends IllagerEntity
{
    protected IllusionerEntityMixin(final EntityType<? extends IllagerEntity> entityType, final World world) {
        super(entityType, world);
    }

    @Inject(at = { @At("HEAD") }, cancellable = true, method = { "attack(Lnet/minecraft/entity/LivingEntity;F)V" })
    public void shootFirework(final LivingEntity target, final float pullProgress, final CallbackInfo callbackInfo) {
        final ItemStack firework = new ItemStack(Items.FIREWORK_ROCKET);
        try {
            firework.setSubNbt("Fireworks", StringNbtReader.parse("{Flight:3,Explosions:[{Type:1,Flicker:0,Trail:0,Colors:[I;2437522],FadeColors:[I;2437522]},{Type:1,Flicker:0,Trail:0,Colors:[I;8073150],FadeColors:[I;8073150]},{Type:1,Flicker:0,Trail:0,Colors:[I;3887386],FadeColors:[I;3887386]}]}"));
        }
        catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        final int randvalue = this.random.nextInt(3);
        if (randvalue == 0 && this.getNearbyPlayers().isEmpty()) {
            final ProjectileEntity projectileEntity = new FireworkRocketEntity(this.world, firework,this, this.getX(), this.getEyeY() - 0.15000000596046448, this.getZ(), true);
            final double d = target.getX() - this.getX();
            final double e2 = target.getBodyY(0.3333333333333333) - projectileEntity.getY();
            final double f = target.getZ() - this.getZ();
            final double g = Math.sqrt(d * d + f * f);
            projectileEntity.setVelocity(d, e2 + 0.4000000059604645, f - 1.0, 1.6f, (float)(14 - this.world.getDifficulty().getId() * 4));
            this.world.spawnEntity(projectileEntity);
            callbackInfo.cancel();
        }
    }

    private List<LivingEntity> getNearbyPlayers() {
        return this.world.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(4.0), entity -> entity instanceof PlayerEntity);
    }

    public void addBonusForWave(final int wave, final boolean unused) {
    }

    public SoundEvent getCelebratingSound() {
        return SoundEvents.ENTITY_ILLUSIONER_AMBIENT;
    }
}
