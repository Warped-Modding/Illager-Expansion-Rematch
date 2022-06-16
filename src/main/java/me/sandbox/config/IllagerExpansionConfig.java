package me.sandbox.config;


import blue.endless.jankson.Comment;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;


@Config(name = "illager_expansion_config")
public class IllagerExpansionConfig implements ConfigData {
    @ConfigEntry.Gui.RequiresRestart
    public boolean addIllagersToRaids = true;

    public static void registerConfig() {
        AutoConfig.register(IllagerExpansionConfig.class, GsonConfigSerializer::new);
    }
}


