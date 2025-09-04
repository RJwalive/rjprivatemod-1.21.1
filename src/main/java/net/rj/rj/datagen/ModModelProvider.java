package net.rj.rj.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.rj.rj.block.ModBlocks;
import net.rj.rj.item.ModItems;
import net.minecraft.data.client.*;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;
import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_CORRUPTED_IRON_DEPOSIT_BLOCK);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.RING_OF_FIRE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.CORRUPTED_IRON_DEPOSIT, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_CORRUPTED_IRON_DEPOSIT, Models.GENERATED);


         itemModelGenerator.register(ModItems.CHISEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.APPLE_PIE, Models.GENERATED);



    }
}