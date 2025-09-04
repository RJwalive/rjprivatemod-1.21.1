package net.rj.rj.entity;

import net.rj.rj.Rjprivatemod;
import net.rj.rj.entity.custom.MeteorEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<MeteorEntity> METEOR = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(Rjprivatemod.MOD_ID, "meteor"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, MeteorEntity::new)
                    .dimensions(EntityDimensions.fixed(1.5f, 1.5f))
                    .build()
    );

    public static void registerModEntities() {
        Rjprivatemod.LOGGER.info("Registering Mod Entities for " + Rjprivatemod.MOD_ID);
        FabricDefaultAttributeRegistry.register(METEOR, MeteorEntity.setAttributes());
    }
}