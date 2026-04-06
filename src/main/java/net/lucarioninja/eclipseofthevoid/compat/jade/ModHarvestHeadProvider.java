package net.lucarioninja.eclipseofthevoid.compat.jade;

import net.lucarioninja.eclipseofthevoid.item.ModItems;
import net.lucarioninja.eclipseofthevoid.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

public class ModHarvestHeadProvider implements IBlockComponentProvider {

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        BlockState state = blockAccessor.getBlockState();

        if (state.is(ModTags.Blocks.NEEDS_INFERNAL_TOOL)) {
            ItemStack toolToShow = new ItemStack(ModItems.INFERNAL_PICKAXE.get());
            iTooltip.add(IElementHelper.get().item(toolToShow, 0.75f).message(null));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation("eclipseofthevoid", "harvest_tool");
    }

    @Override
    public @Nullable IElement getIcon(BlockAccessor accessor, IPluginConfig config, IElement currentIcon) {
        return IBlockComponentProvider.super.getIcon(accessor, config, currentIcon);
    }
}