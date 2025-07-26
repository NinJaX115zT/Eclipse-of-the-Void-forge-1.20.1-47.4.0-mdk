package net.lucarioninja.eclipseofthevoid.sound;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, EclipseOfTheVoid.MOD_ID);

    public static final RegistryObject<SoundEvent> VOID_ESSENCE_PICKUP = registerSoundEvents("void_essence_pickup");
    public static final RegistryObject<SoundEvent> INFERNAL_ESSENCE_PICKUP = registerSoundEvents("infernal_essence_pickup");
    public static final RegistryObject<SoundEvent> COSMIC_ESSENCE_PICKUP = registerSoundEvents("cosmic_essence_pickup");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUNDS_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(EclipseOfTheVoid.MOD_ID , name)));
    }

    public static void register(IEventBus eventBus){
        SOUNDS_EVENTS.register(eventBus);
    }
}
