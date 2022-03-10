package me.sandbox.gui;

import me.sandbox.Sandbox;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModdedScreenHandler {
    public static ScreenHandlerType<ImbuingTableScreenHandler> IMBUING_TABLE_SCREEN_HANDLER =
            ScreenHandlerRegistry.registerSimple(new Identifier(Sandbox.MOD_ID, "imbuing_table"),
                    (int syncId, PlayerInventory inventory) -> new ImbuingTableScreenHandler(syncId, inventory));
}
