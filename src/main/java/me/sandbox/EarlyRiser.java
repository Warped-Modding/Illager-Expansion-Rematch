package me.sandbox;

import com.chocohead.mm.api.ClassTinkerers;
import me.sandbox.config.IllagerExpansionConfig;
import me.sandbox.entity.EntityRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.village.raid.Raid;

import java.util.Random;

public class EarlyRiser implements Runnable
{
    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();
        if (FabricLoader.getInstance().isModLoaded("friendsandfoes")) {
            String Raid = remapper.mapClassName("intermediary", "net.minecraft.class_3765$class_3766");
            String EntityType = 'L' + remapper.mapClassName("intermediary", "net.minecraft.class_1299") + ';';
            ClassTinkerers
                    .enumBuilder(Raid, EntityType, int[].class)
                    .addEnum("BASHER", () -> new Object[]{EntityRegistry.BASHER, new int[]{1, 1, 2, 1, 2, 2, 3, 3}})
                    .addEnum("PROVOKER", () -> new Object[]{EntityRegistry.PROVOKER, new int[]{0, 1, 1, 0, 1, 1, 2, 2}})
                    .addEnum("NECROMANCER", () -> new Object[]{EntityRegistry.NECROMANCER, new int[]{0, 0, 0, 0, 1, 1, 1, 1}})
                    .addEnum("SORCERER", () -> new Object[]{EntityRegistry.SORCERER, new int[]{0, 0, 0, 0, 0, 1, 1, 1}})
                    .addEnum("ARCHIVIST", () -> new Object[]{EntityRegistry.ARCHIVIST, new int[]{0, 1, 0, 1, 1, 1, 2, 2}})
                    .addEnum("MARAUDER", () -> new Object[]{EntityRegistry.MARAUDER, new int[]{0, 1, 1, 1, 2, 2, 3, 2}})
                    .addEnum("INQUISITOR", () -> new Object[]{EntityRegistry.INQUISITOR, new int[]{0, 0, 0, 0, 0, 0, 0, 1}})
                    .addEnum("ALCHEMIST", () -> new Object[]{EntityRegistry.ALCHEMIST, new int[]{0, 0, 0, 0, 1, 1, 2, 2}})
                    .build();
        } else {
            String Raid = remapper.mapClassName("intermediary", "net.minecraft.class_3765$class_3766");
            String EntityType = 'L' + remapper.mapClassName("intermediary", "net.minecraft.class_1299") + ';';
            ClassTinkerers
                    .enumBuilder(Raid, EntityType, int[].class)
                    .addEnum("BASHER", () -> new Object[]{EntityRegistry.BASHER, new int[]{1, 1, 2, 1, 2, 2, 3, 3}})
                    .addEnum("PROVOKER", () -> new Object[]{EntityRegistry.PROVOKER, new int[]{0, 1, 1, 0, 1, 1, 2, 2}})
                    .addEnum("NECROMANCER", () -> new Object[]{EntityRegistry.NECROMANCER, new int[]{0, 0, 0, 0, 1, 1, 1, 1}})
                    .addEnum("SORCERER", () -> new Object[]{EntityRegistry.SORCERER, new int[]{0, 0, 0, 0, 0, 1, 1, 1}})
                    .addEnum("ILLUSIONER", () -> new Object[]{net.minecraft.entity.EntityType.ILLUSIONER, new int[]{0, 0, 0, 0, 0, 1, 1, 1}})
                    .addEnum("ARCHIVIST", () -> new Object[]{EntityRegistry.ARCHIVIST, new int[]{0, 1, 0, 1, 1, 1, 2, 2}})
                    .addEnum("MARAUDER", () -> new Object[]{EntityRegistry.MARAUDER, new int[]{0, 1, 1, 1, 2, 2, 3, 2}})
                    .addEnum("INQUISITOR", () -> new Object[]{EntityRegistry.INQUISITOR, new int[]{0, 0, 0, 0, 0, 0, 0, 1}})
                    .addEnum("ALCHEMIST", () -> new Object[]{EntityRegistry.ALCHEMIST, new int[]{0, 0, 0, 0, 1, 1, 2, 2}})
                    .build();
        }

        final String SpellcastingIllagerEntity = remapper.mapClassName("intermediary", "net.minecraft.class_1617$class_1618");
        ClassTinkerers.enumBuilder(SpellcastingIllagerEntity, int.class, double.class, double.class, double.class)
                .addEnum("ENCHANT",6, 0.8, 0.8, 0.2 )
                .addEnum("CONJURE_FLAMES", 7, 1.8, 0.0, 1.8 )
                .addEnum("CONJURE_TELEPORT", 8, 1.5, 1.5, 0.8)
                .addEnum("NECRORAISE", 9, 0.3, 0.8, 0.05)
                .addEnum("CONJURE_SKULLBOLT",10, 0.5, 0.05, 0.05)
                .addEnum("PROVOKE", 11, 1.0, 0.8, 0.75)
                .build();
    }
}
