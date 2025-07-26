package net.lucarioninja.eclipseofthevoid.entity;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.entity.custom.EtherealBeeEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, EclipseOfTheVoid.MOD_ID);

    public static final RegistryObject<EntityType<EtherealBeeEntity>> ETHEREAL_BEE =
            ENTITY_TYPES.register("ethereal_bee", () -> EntityType.Builder
                    .of(EtherealBeeEntity::new, MobCategory.CREATURE)
                    .sized(1.0f, 1.1f) // your preferred size
                    .build(new ResourceLocation(EclipseOfTheVoid.MOD_ID, "ethereal_bee").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
