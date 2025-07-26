package net.lucarioninja.eclipseofthevoid.particles;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, EclipseOfTheVoid.MOD_ID);

    public static final RegistryObject<SimpleParticleType> ETHEREAL_HONEY_PARTICLE =
            PARTICLES.register("ethereal_honey", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }
}
