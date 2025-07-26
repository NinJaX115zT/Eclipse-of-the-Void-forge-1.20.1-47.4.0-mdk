package net.lucarioninja.eclipseofthevoid.villager;

import com.google.common.collect.ImmutableSet;
import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.block.ModBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, EclipseOfTheVoid.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, EclipseOfTheVoid.MOD_ID);

    public static final RegistryObject<PoiType> VOID_MULCHER_POI = POI_TYPES.register("void_mulcher_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.VOID_MULCHER.get().getStateDefinition().getPossibleStates()), 1, 1));

    public static final RegistryObject<VillagerProfession> VOID_CULTIVATOR =
            VILLAGER_PROFESSIONS.register("void_cultivator", () -> new VillagerProfession("void_cultivator",
                            holder -> holder.get() == VOID_MULCHER_POI.get(), // workstation check
                    holder -> holder.get() == VOID_MULCHER_POI.get(), // secondary check
                    ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_FARMER // work sound
            ));

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
