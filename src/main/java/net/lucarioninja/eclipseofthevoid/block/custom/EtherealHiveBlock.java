package net.lucarioninja.eclipseofthevoid.block.custom;

import net.lucarioninja.eclipseofthevoid.block.entity.EtherealHiveBlockEntity;
import net.lucarioninja.eclipseofthevoid.block.entity.ModBlockEntities;
import net.lucarioninja.eclipseofthevoid.entity.custom.EtherealBeeEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class EtherealHiveBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty NECTAR = BooleanProperty.create("nectar");

    public EtherealHiveBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(NECTAR, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, NECTAR);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection())
                .setValue(NECTAR, false);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EtherealHiveBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof EtherealHiveBlockEntity hive) {
                NetworkHooks.openScreen((ServerPlayer) player, hive, pos);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModBlockEntities.ETHEREAL_HIVE.get()
                ? (lvl, pos, st, be) -> EtherealHiveBlockEntity.tick(lvl, pos, st, (EtherealHiveBlockEntity) be)
                : null;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof EtherealHiveBlockEntity hive) {
            ItemStack stack = hive.getItemHandler().getStackInSlot(0);
            return stack.isEmpty() ? 0 : 15;
        }
        return 0;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        if (Screen.hasShiftDown()) {
            pTooltip.add(Component.translatable("tooltip.eclipseofthevoid.ethereal_hive_block.tooltip.shift")
                    .withStyle(ChatFormatting.GRAY));
        } else {
            pTooltip.add(Component.translatable("tooltip.eclipseofthevoid.ethereal_hive_block.tooltip")
                    .withStyle(ChatFormatting.LIGHT_PURPLE));
        }
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, net.minecraft.world.level.block.entity.BlockEntity be, ItemStack tool) {
        if (!level.isClientSide) {
            if (!tool.isEmpty() && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool) > 0) {
                popResource(level, pos, new ItemStack(this));
            } else {
                if (be instanceof EtherealHiveBlockEntity hive) {
                    hive.angerBees(player);
                    hive.emptyAllContents(player);
                }
                angerNearbyBees(level, pos, player);
            }
        }
        super.playerDestroy(level, player, pos, state, be, tool);
    }

    private void angerNearbyBees(Level level, BlockPos pos, Player player) {
        List<EtherealBeeEntity> bees = level.getEntitiesOfClass(EtherealBeeEntity.class, new net.minecraft.world.phys.AABB(pos).inflate(12));
        for (EtherealBeeEntity bee : bees) {
            bee.setTarget(player);
        }
    }
}
