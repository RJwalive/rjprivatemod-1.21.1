package net.rj.rj.entity.client;

import net.rj.rj.entity.custom.MeteorEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MeteorRenderer extends GeoEntityRenderer<MeteorEntity> {
    public MeteorRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MeteorModel());
        this.shadowRadius = 0.7f;
    }

    @Override
    public Identifier getTexture(MeteorEntity entity) {
        return this.model.getTextureResource(entity);
    }
}