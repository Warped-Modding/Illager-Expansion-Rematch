package me.sandbox;

import com.chocohead.mm.api.ClassTinkerers;
import me.sandbox.entity.EntityRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.entity.EntityType;

import java.util.Random;

public class EarlyRiser implements Runnable
{
    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

        String Raid = remapper.mapClassName("intermediary", "net.minecraft.class_3765$class_3766");
        String EntityType = 'L' + remapper.mapClassName("intermediary", "net.minecraft.class_1299") + ';';
        ClassTinkerers
                .enumBuilder(Raid, EntityType, int[].class)
                .addEnum("BASHER", () -> new Object[]{EntityRegistry.BASHER, new int[]{1, 1, 2, 1, 2, 3, 4, 5}})
                .addEnum("PROVOKER", () -> new Object[]{EntityRegistry.PROVOKER, new int[]{0, 0, 1, 0, 1, 2, 2, 3}})
                .addEnum("NECROMANCER", () -> new Object[]{EntityRegistry.NECROMANCER, new int[]{0, 0, 0, 0, 0, 1, 2, 3}})
                .addEnum("SORCERER", () -> new Object[]{EntityRegistry.SORCERER, new int[]{0, 0, 0, 0, 0, 1, 1, 2}})
                .addEnum("ILLUSIONER", () -> new Object[]{net.minecraft.entity.EntityType.ILLUSIONER, new int[]{0, 0, 0, 0, 1, 1, 2, 2}})
                .build();
        Random random = new Random();
        int canInvokerSpawn = random.nextInt(8);
        if (canInvokerSpawn == 0) {
            ClassTinkerers
                    .enumBuilder(Raid, EntityType, int[].class)
                    .addEnum("INVOKER", () -> new Object[]{EntityRegistry.INVOKER, new int[]{0, 0, 0, 0, 0, 0, 0, 1}})
                    .build();
        }
    }
}
