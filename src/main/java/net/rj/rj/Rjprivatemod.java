package net.rj.rj;

import net.fabricmc.api.ModInitializer;


import net.rj.rj.entity.ModEntities;
import net.rj.rj.item.ModItemGroups;
import net.rj.rj.block.ModBlocks;
import net.rj.rj.item.ModItems;
import net.rj.rj.screen.ScreenMod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.rj.rj.entity.custom.MeteorEntity;


public class Rjprivatemod implements ModInitializer {
    public static final String MOD_ID = "rj";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);



    @Override
    public void onInitialize() {
        ModItemGroups.registerItemGroups();
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ScreenMod.registerScreenHandler();
        ModEntities.registerModEntities();
    }
}