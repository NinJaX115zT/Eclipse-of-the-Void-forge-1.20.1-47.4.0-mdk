package net.lucarioninja.eclipseofthevoid.event;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.entity.ModEntities;
import net.lucarioninja.eclipseofthevoid.entity.custom.EtherealBeeEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EclipseOfTheVoid.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.ETHEREAL_BEE.get(), EtherealBeeEntity.createAttributes().build());
    }
}
