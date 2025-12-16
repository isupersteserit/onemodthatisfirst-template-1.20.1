package net.noah.onemodthatisfirst;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.impl.object.builder.FabricEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModEntities {
    // Register the entity using the new helper method pattern
    public static final EntityType<Cmk2Rocket> CMK_2_ROCKET = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(Onemodthatisfirst.MOD_ID, "celebration_mk_2_rocket"),
            FabricEntityTypeBuilder.create(
                            SpawnGroup.MISC,
                            // Explicitly declare the types in the lambda signature
                            (EntityType<Cmk2Rocket> type, World world) -> new Cmk2Rocket(type, world)
                    )
                    .dimensions(EntityDimensions.fixed(0.75f, 0.75f))
                    .build()
    );


    // This helper method is generally not needed if you register directly as above
    public static <T extends Entity> EntityType<T> register(EntityType<T> entityType, String id) {
        Identifier entityID = Identifier.of(Onemodthatisfirst.MOD_ID, id);
        return Registry.register(Registries.ENTITY_TYPE, entityID, entityType);
    }

    public static void initialize() {
        // This method can be called in your main mod class to ensure the static fields are initialized
    }
}
