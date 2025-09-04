package net.rj.rj.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.rj.rj.Rjprivatemod;
import net.rj.rj.entity.ModEntities;
import net.rj.rj.item.custom.ChiselItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.rj.rj.item.custom.CameraShakeWand;
import net.rj.rj.item.custom.ChainsawItem;


import java.util.List;

public class ModItems {
    public static final Item CORRUPTED_IRON_DEPOSIT = registerItem("corrupted_iron_deposit", new Item(new Item.Settings()));

    public static final Item APPLE_PIE = registerItem("apple_pie", new Item(new Item.Settings().food(ModFoodComponents.APPLE_PIE)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.rj.apple_pie.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);

        }
    });










    public static final Item RAW_CORRUPTED_IRON_DEPOSIT = registerItem("raw_corrupted_iron_deposit", new Item(new Item.Settings()));
    public static final Item CHAINSAW = registerItem("chainsaw", new ChainsawItem(new Item.Settings().maxDamage(32)));

    public static final Item CHISEL = registerItem("chisel", new ChiselItem(new Item.Settings().maxDamage(32)));



    public static final Item CAMERA_SHAKE_WAND = registerItem("camera_shake_wand", new CameraShakeWand(new Item.Settings().maxDamage(32)));

    public static final Item RING_OF_FIRE = registerItem("ring_of_fire", new CameraShakeWand(new Item.Settings().maxDamage(32)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Rjprivatemod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Rjprivatemod.LOGGER.info("Registering Mod Items for " + Rjprivatemod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(CORRUPTED_IRON_DEPOSIT);
            entries.add(RAW_CORRUPTED_IRON_DEPOSIT);

        });
    }
}