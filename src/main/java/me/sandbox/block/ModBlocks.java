package me.sandbox.block;

import me.sandbox.Sandbox;
import me.sandbox.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.PickaxeItem;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks {

public static final DeferredRegister<Block> BLOCKS
        = DeferredRegister.create(ForgeRegistries.BLOCKS, Sandbox.MOD_ID);

public static final RegistryObject<Block> ENDERGON_ORE =registerBlock("endergon_ore",
        () -> new Block(AbstractBlock.Properties.create(Material.ROCK).harvestLevel(4).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(8f)));


private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
    RegistryObject<T> toReturn = BLOCKS.register(name, block);
    return toReturn;

}

private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
    ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
            new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
}
public static void register(IEventBus eventBus) {
    BLOCKS.register(eventBus);
}





}
