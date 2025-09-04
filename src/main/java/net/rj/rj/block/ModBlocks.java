package net.rj.rj.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.rj.rj.Rjprivatemod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.rj.rj.block.custom.RingOfFire;

public class ModBlocks {

    public static final Block RAW_CORRUPTED_IRON_DEPOSIT_BLOCK = registerBlock("raw_corrupted_iron_deposit_block",
            new Block(AbstractBlock.Settings.create().strength(3f)
                    .requiresTool().sounds(BlockSoundGroup.NETHERITE)));

    public static final Block RING_OF_FIRE = registerBlock("ring_of_fire",
            new RingOfFire(AbstractBlock.Settings.create()
                    .nonOpaque()
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.WOOD)
                    ));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Rjprivatemod.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Rjprivatemod.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        Rjprivatemod.LOGGER.info("Registering Mod Blocks for " + Rjprivatemod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.add(ModBlocks.RAW_CORRUPTED_IRON_DEPOSIT_BLOCK);
            entries.add(ModBlocks.RING_OF_FIRE);
        });
    }
}