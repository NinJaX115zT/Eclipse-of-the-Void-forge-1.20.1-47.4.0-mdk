package net.lucarioninja.eclipseofthevoid.block.entity;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, EclipseOfTheVoid.MOD_ID);

    public static final RegistryObject<BlockEntityType<EtherealNestBlockEntity>> ETHEREAL_NEST =
            BLOCK_ENTITIES.register("ethereal_nest", () ->
                    BlockEntityType.Builder.of(EtherealNestBlockEntity::new,
                            ModBlocks.ETHEREAL_NEST.get()).build(null));
    public static final RegistryObject<BlockEntityType<EtherealHiveBlockEntity>> ETHEREAL_HIVE =
            BLOCK_ENTITIES.register("ethereal_hive", () ->
                    BlockEntityType.Builder.of(EtherealHiveBlockEntity::new,
                            ModBlocks.ETHEREAL_HIVE.get()).build(null));

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
    }
}
