package net.lucarioninja.eclipseofthevoid.particles;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.particles.custom.EtherealHoneyParticles;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EclipseOfTheVoid.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModParticleFactories {

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(
                ModParticles.ETHEREAL_HONEY_HANG.get(),
                EtherealHoneyParticles.HangProvider::new
        );

        Minecraft.getInstance().particleEngine.register(
                ModParticles.ETHEREAL_HONEY_FALL.get(),
                EtherealHoneyParticles.FallProvider::new
        );

        Minecraft.getInstance().particleEngine.register(
                ModParticles.ETHEREAL_HONEY_LAND.get(),
                EtherealHoneyParticles.LandProvider::new
        );
    }
}