package net.lucarioninja.eclipseofthevoid.compat.jade;

import net.lucarioninja.eclipseofthevoid.block.custom.EtherealNestBlock;
import net.lucarioninja.eclipseofthevoid.block.entity.EtherealNestBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class EOTVJadePlugin implements IWailaPlugin {

    public static final ResourceLocation ETHEREAL_NEST_UID =
            new ResourceLocation(EclipseOfTheVoid.MOD_ID, "ethereal_nest_info");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(EtherealNestJadeProvider.INSTANCE, EtherealNestBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(EtherealNestJadeProvider.INSTANCE, EtherealNestBlock.class);
    }
}