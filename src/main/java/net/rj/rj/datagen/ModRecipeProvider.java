package net.rj.rj.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.rj.rj.Rjprivatemod;
import net.rj.rj.block.ModBlocks;
import net.rj.rj.item.ModItems;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        List<ItemConvertible> CORRUPTED_SMELTABLES = List.of(ModItems.RAW_CORRUPTED_IRON_DEPOSIT, ModBlocks.RAW_CORRUPTED_IRON_DEPOSIT_BLOCK);

        offerSmelting(exporter, CORRUPTED_SMELTABLES, RecipeCategory.MISC, ModItems.CORRUPTED_IRON_DEPOSIT, 0.25f, 200, "corrupted_iron_deposit");
        offerBlasting(exporter, CORRUPTED_SMELTABLES, RecipeCategory.MISC, ModItems.CORRUPTED_IRON_DEPOSIT, 0.25f, 100, "corrupted_iron_deposit");


        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RAW_CORRUPTED_IRON_DEPOSIT, 9)
                .input(ModBlocks.RAW_CORRUPTED_IRON_DEPOSIT_BLOCK)
                .criterion(hasItem(ModBlocks.RAW_CORRUPTED_IRON_DEPOSIT_BLOCK), conditionsFromItem(ModBlocks.RAW_CORRUPTED_IRON_DEPOSIT_BLOCK))
                .offerTo(exporter);

    }
}