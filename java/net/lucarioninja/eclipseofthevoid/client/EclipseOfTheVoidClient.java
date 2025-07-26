package net.lucarioninja.eclipseofthevoid.client;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.client.renderer.VoidCapeRenderer;
import net.lucarioninja.eclipseofthevoid.particles.ModParticles;
import net.lucarioninja.eclipseofthevoid.particles.custom.EtherealHoneyParticleProvider;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = EclipseOfTheVoid.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EclipseOfTheVoidClient {
    @SubscribeEvent
    public static void onParticleFactoryRegister(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.ETHEREAL_HONEY_PARTICLE.get(), EtherealHoneyParticleProvider::new);
    }
    @SubscribeEvent
    public static void onLayerAdded(EntityRenderersEvent.AddLayers event) {
        event.getSkins().forEach(skin -> {
            LivingEntityRenderer<?, ?> renderer = event.getSkin(skin);
            if (renderer instanceof PlayerRenderer playerRenderer) {

                playerRenderer.addLayer(new VoidCapeRenderer(playerRenderer));
            }
        });
    }
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // Client setup stuff here
        });
    }
}
