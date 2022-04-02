package me.sandbox.config;


import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "illager_expansion_config")
public class IllagerExpansionConfig implements ConfigData {
    public boolean addIllagersToRaids = true;
}


