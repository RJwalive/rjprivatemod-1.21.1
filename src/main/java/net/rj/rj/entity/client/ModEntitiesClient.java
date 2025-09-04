package net.rj.rj.entity.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.rj.rj.entity.ModEntities;

public class ModEntitiesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.METEOR, MeteorRenderer::new);
    }
}