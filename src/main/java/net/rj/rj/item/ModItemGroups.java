package net.rj.rj.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.rj.rj.Rjprivatemod;
import net.rj.rj.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup CORRUPTED_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Rjprivatemod.MOD_ID, "corrupted_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.CORRUPTED_IRON_DEPOSIT))
                    .displayName(Text.translatable("itemgroup.rj.corrupted_item_group"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.CORRUPTED_IRON_DEPOSIT);
                        entries.add(ModItems.RAW_CORRUPTED_IRON_DEPOSIT);

                    }).build());

    public static final ItemGroup CORRUPTED_BLOCK_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Rjprivatemod.MOD_ID, "corrupted_blocks"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModBlocks.RAW_CORRUPTED_IRON_DEPOSIT_BLOCK))
                    .displayName(Text.translatable("itemgroup.rj.corrupted_block_group"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.RAW_CORRUPTED_IRON_DEPOSIT_BLOCK);
                    }).build());

    public static final ItemGroup RJS_SHENANIGANS = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Rjprivatemod.MOD_ID, "rjs_shenanigans"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.CHISEL))
                    .displayName(Text.translatable("itemgroup.rj.rjs_shenanigans"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.CHISEL);
                        entries.add(ModItems.CAMERA_SHAKE_WAND);
                        entries.add(ModBlocks.RING_OF_FIRE);
                        entries.add(ModItems.APPLE_PIE);
                    }).build());




    public static void registerItemGroups() {
        Rjprivatemod.LOGGER.info("Registering Item Groups for " + Rjprivatemod.MOD_ID);
    }
}