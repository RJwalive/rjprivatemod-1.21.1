package net.rj.rj.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.rj.rj.Rjprivatemod;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;

public class ScreenMod {
//    public static final ScreenHandlerType<WarturtleScreenHandler> WARTURTLE_SCREEN_HANDLER =
//            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Rjprivatemod.MOD_ID, "warturtle_screen_handler"),
//                    new ExtendedScreenHandlerType<>(WarturtleScreenHandler::create, Uuids.PACKET_CODEC));


    public static void registerScreenHandler() {
        Rjprivatemod.LOGGER.info("Registering Screen Handlers for " + Rjprivatemod.MOD_ID);
    }
}