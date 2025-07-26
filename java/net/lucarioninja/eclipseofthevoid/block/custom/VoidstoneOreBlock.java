package net.lucarioninja.eclipseofthevoid.block.custom;

import net.lucarioninja.eclipseofthevoid.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class VoidstoneOreBlock extends DropExperienceBlock {
    public VoidstoneOreBlock(Properties pProperties, UniformInt uniformInt) {
        super(pProperties);
    }

    @Override
    public void attack(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player) {
        if (!isProperTool(player.getMainHandItem())) {
            if (!level.isClientSide) {
                player.displayClientMessage(Component.literal("This tool is unworthy."), true);
            }
        }
        super.attack(state, level, pos, player);
    }

    private boolean isProperTool(ItemStack stack) {
        Item tool = stack.getItem();
        return tool == Items.DIAMOND_PICKAXE
                || tool == Items.NETHERITE_PICKAXE
                || tool == ModItems.VOID_PICKAXE.get()
                || tool == ModItems.INFERNAL_PICKAXE.get()
                || tool == ModItems.COSMIC_PICKAXE.get();
    }
}
