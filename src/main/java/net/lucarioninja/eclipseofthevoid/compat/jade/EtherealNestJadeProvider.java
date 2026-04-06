package net.lucarioninja.eclipseofthevoid.compat.jade;

import net.lucarioninja.eclipseofthevoid.block.entity.EtherealNestBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum EtherealNestJadeProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    private static final String BEE_COUNT = "EtherealBeeCount";
    private static final String MAX_BEES = "EtherealMaxBees";

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        if (accessor.getBlockEntity() instanceof EtherealNestBlockEntity nest) {
            data.putInt(BEE_COUNT, nest.getBeeCount());
            data.putInt(MAX_BEES, 5); // or use a getter if you make one
        }
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();

        if (data.contains(BEE_COUNT) && data.contains(MAX_BEES)) {
            int bees = data.getInt(BEE_COUNT);
            int max = data.getInt(MAX_BEES);

            tooltip.add(Component.translatable("tooltip.eclipseofthevoid.ethereal_nest_bees", bees, max));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ModJadePlugin.ETHEREAL_NEST_UID;
    }
}