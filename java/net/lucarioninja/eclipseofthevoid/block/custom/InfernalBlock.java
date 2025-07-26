package net.lucarioninja.eclipseofthevoid.block.custom;


import net.lucarioninja.eclipseofthevoid.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class InfernalBlock extends Block {
    public InfernalBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.literal("It holds Infernal energy within it. It seems to be alive...")
                .withStyle(net.minecraft.ChatFormatting.DARK_PURPLE, net.minecraft.ChatFormatting.ITALIC));
    }
    @Override
    public void stepOn(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, Entity pEntity) {
        if (!pEntity.isSteppingCarefully() && pEntity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)pEntity)) {
            pEntity.hurt(pLevel.damageSources().hotFloor(), 2.0F);
        }

        super.stepOn(pLevel, pPos, pState, pEntity);
    }
    @Override
    public void tick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, BlockPos pPos, @NotNull RandomSource pRandom) {
        BubbleColumnBlock.updateColumn(pLevel, pPos.above(), pState);
    }
    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState pState, @NotNull Direction pFacing, @NotNull BlockState pFacingState, @NotNull LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pFacing == Direction.UP && pFacingState.is(Blocks.WATER)) {
            pLevel.scheduleTick(pCurrentPos, this, 20);
        }

        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }
    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        pLevel.scheduleTick(pPos, this, 20);
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
        return tool == Items.NETHERITE_PICKAXE
                || tool == ModItems.VOID_PICKAXE.get()
                || tool == ModItems.INFERNAL_PICKAXE.get()
                || tool == ModItems.COSMIC_PICKAXE.get();
    }
}
