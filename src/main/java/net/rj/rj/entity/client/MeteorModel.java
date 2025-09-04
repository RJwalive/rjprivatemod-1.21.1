package net.rj.rj.entity.client;

import net.rj.rj.Rjprivatemod;
import net.rj.rj.entity.custom.MeteorEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MeteorModel extends GeoModel<MeteorEntity> {
    @Override
    public Identifier getModelResource(MeteorEntity meteor) {
        return Identifier.of(Rjprivatemod.MOD_ID, "geo/meteor.geo.json");
    }

    @Override
    public Identifier getTextureResource(MeteorEntity meteor) {
        return Identifier.of(Rjprivatemod.MOD_ID, "textures/entity/meteor.png");
    }

    @Override
    public Identifier getAnimationResource(MeteorEntity meteor) {
        return Identifier.of(Rjprivatemod.MOD_ID, "animations/meteor.animation.json");
    }
}