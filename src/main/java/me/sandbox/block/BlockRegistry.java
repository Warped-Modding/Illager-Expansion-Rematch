package me.sandbox.block;

import me.sandbox.block.custom.ImbuingTableBlock;
import me.sandbox.block.custom.MagicFireBlock;
import me.sandbox.item.ModItemGroup;
import me.sandbox.sounds.ModBlockSoundGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import me.sandbox.Sandbox;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nullable;

public class BlockRegistry {

    // Ores
    public static final Block ENDERGON_ORE = registerBlock("endergon_ore",
            new Block(FabricBlockSettings.of(Material.STONE).sounds(ModBlockSoundGroup.ENDERGON_ORE).strength(6f).requiresTool()), ModItemGroup.SandBoxDecorations);

    //Decoration Blocks
    public static final Block IMBUING_TABLE = registerBlock("imbuing_table",
            new ImbuingTableBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.COPPER).strength(4f).requiresTool()), ModItemGroup.SandBoxDecorations);


    public static final Block MAGIC_FIRE = registerBlock("magic_fire",
            new MagicFireBlock(FabricBlockSettings.of(Material.FIRE, MapColor.PURPLE).noCollision().luminance(state -> 10), 0.0f), null);

    private static Block registerBlock(String name, Block block, @Nullable ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(Sandbox.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(Sandbox.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }

    public static void registerModBlocks() {
        Sandbox.LOGGER.info("Registering blocks...");
    }
}