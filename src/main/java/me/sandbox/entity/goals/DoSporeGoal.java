package me.sandbox.entity.goals;

import me.sandbox.entity.MushroomlingEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.EntityTypeTags;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class DoSporeGoal extends Goal {

    private final MushroomlingEntity mushroomling;
    private LivingEntity target;
    public int duration;
    private static final Predicate<Entity> IS_NOT_MUSHROOMLING = entity -> entity.isAlive() && !(entity instanceof MushroomlingEntity);

    @Nullable
    public DoSporeGoal(MushroomlingEntity mushroomling) {
        this.mushroomling = mushroomling;

    }

    @Override
    public boolean canStart() {
        Random random = new Random();
        boolean canSpore = random.nextBoolean();
        if (mushroomling.getAttacker() instanceof LivingEntity && canSpore && mushroomling.sporeCooldown >= 100) {
            return true;
        }
        if (mushroomling.getTarget() instanceof LivingEntity && mushroomling.sporeCooldown >= 100) {
            return true;
        }
        return false;
    }

    private List<LivingEntity> getTargets() {
        return mushroomling.world.getEntitiesByClass(LivingEntity.class, mushroomling.getBoundingBox().expand(12), entity -> entity.isAlive() && !(entity instanceof MushroomlingEntity));
    }

    private void debuff(LivingEntity entity) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 500, 1));
        getTargets().stream().forEach(this::debuff);

    }

    @Override
    public void start() {
    this.duration = 0;
    }

    @Override
    public void tick() {
        ++this.duration;
        System.out.println(this.duration);
    }
    @Override
    public boolean shouldContinue() {
        if (this.duration <= 40) {
            return true;
        }
        if (mushroomling.getAttacker()==null) {
            return false;
        }
        return false;
    }

    @Override
    public void stop() {
        System.out.println("STOPPED");
        mushroomling.sporeCooldown = 0;
    }
}

