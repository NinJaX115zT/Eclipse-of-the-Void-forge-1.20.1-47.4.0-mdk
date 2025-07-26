package net.lucarioninja.eclipseofthevoid.item.custom;

import net.lucarioninja.eclipseofthevoid.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class InfernalPickaxeItem extends PickaxeItem {
    public InfernalPickaxeItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Override
    public boolean isCorrectToolForDrops(@NotNull BlockState state) {
        boolean correctTag = state.is(ModTags.Blocks.NEEDS_INFERNAL_TOOL);
        System.out.println("[DEBUG] isCorrectToolForDrops for INFERNAL: " + correctTag);
        return correctTag;
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level level, @NotNull BlockState state,
                             @NotNull BlockPos pos, @NotNull LivingEntity miner) {

        System.out.println("[DEBUG] Void Pickaxe used on: " + state.getBlock().getDescriptionId());
        System.out.println("[DEBUG] Block hardness: " + state.getDestroySpeed(level, pos));
        System.out.println("[DEBUG] Is block tagged NEEDS_COSMIC_TOOL: " + state.is(net.lucarioninja.eclipseofthevoid.util.ModTags.Blocks.NEEDS_COSMIC_TOOL));
        System.out.println("[DEBUG] Is block tagged NEEDS_INFERNAL_TOOL: " + state.is(net.lucarioninja.eclipseofthevoid.util.ModTags.Blocks.NEEDS_INFERNAL_TOOL));
        System.out.println("[DEBUG] Is block tagged NEEDS_VOID_TOOL: " + state.is(net.lucarioninja.eclipseofthevoid.util.ModTags.Blocks.NEEDS_VOID_TOOL));
        System.out.println("[DEBUG] Is block tagged NEEDS_DIAMOND_TOOL: " + state.is(net.minecraft.tags.BlockTags.NEEDS_DIAMOND_TOOL));
        System.out.println("[DEBUG] Is block tagged NEEDS_NETHERITE_TOOL: " + state.is(net.minecraftforge.common.Tags.Blocks.NEEDS_NETHERITE_TOOL));

        return super.mineBlock(stack, level, state, pos, miner);
    }
}
