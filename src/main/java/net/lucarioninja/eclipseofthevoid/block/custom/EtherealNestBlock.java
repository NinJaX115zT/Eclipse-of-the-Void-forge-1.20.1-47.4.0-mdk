package net.lucarioninja.eclipseofthevoid.block.custom;

import net.lucarioninja.eclipseofthevoid.block.entity.EtherealNestBlockEntity;
import net.lucarioninja.eclipseofthevoid.block.entity.ModBlockEntities;
import net.lucarioninja.eclipseofthevoid.entity.ModEntities;
import net.lucarioninja.eclipseofthevoid.entity.custom.EtherealBeeEntity;
import net.lucarioninja.eclipseofthevoid.item.ModItems;
import net.lucarioninja.eclipseofthevoid.particles.ModParticles;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EtherealNestBlock extends BeehiveBlock implements EntityBlock {

    public static final IntegerProperty HONEY_LEVEL = BeehiveBlock.HONEY_LEVEL;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty GENERATED = BooleanProperty.create("generated");

    public EtherealNestBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HONEY_LEVEL, 0)
                .setValue(FACING, Direction.NORTH)
                .setValue(GENERATED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HONEY_LEVEL, FACING, GENERATED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection())
                .setValue(HONEY_LEVEL, 0)
                .setValue(GENERATED, false);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(hand);

        if (!level.isClientSide) {
            int honeyLevel = state.getValue(HONEY_LEVEL);

            // Bottle collection
            if (honeyLevel >= 5 && heldItem.is(Items.GLASS_BOTTLE)) {
                if (!player.isCreative()) {
                    heldItem.shrink(1);
                    ItemStack honeyBottle = new ItemStack(ModItems.ETHEREAL_HONEY_BOTTLE.get());
                    if (!player.getInventory().add(honeyBottle)) {
                        player.drop(honeyBottle, false);
                    }
                }

                level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlock(pos, state.setValue(HONEY_LEVEL, 0), 3);
                angerNearbyBees(level, pos, player);
                return InteractionResult.SUCCESS;
            }

            // Shearing
            if (honeyLevel >= 5 && heldItem.is(Items.SHEARS)) {
                popResource(level, pos, new ItemStack(ModItems.ETHEREAL_HONEYCOMB_CELL.get(), 1 + level.random.nextInt(2)));
                heldItem.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
                level.setBlock(pos, state.setValue(HONEY_LEVEL, 0), 3);
                angerNearbyBees(level, pos, player);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, net.minecraft.world.level.block.entity.BlockEntity be, ItemStack tool) {
        if (!level.isClientSide) {
            boolean hasSilkTouch = !tool.isEmpty() && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool) > 0;

            if (hasSilkTouch) {
                popResource(level, pos, new ItemStack(this));
            } else {
                if (be instanceof EtherealNestBlockEntity nest) {
                    List<CompoundTag> bees = nest.releaseAllBees();
                    for (CompoundTag tag : bees) {
                        EtherealBeeEntity angryBee = ModEntities.ETHEREAL_BEE.get().create(level);
                        if (angryBee != null) {
                            angryBee.load(tag);
                            angryBee.moveTo(pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5);
                            angryBee.setTarget(player);
                            level.addFreshEntity(angryBee);
                        }
                    }
                }
                spawnAngryBees(level, pos, player, 3 + level.random.nextInt(3));
                angerNearbyBees(level, pos, player);
            }
        }

        super.playerDestroy(level, player, pos, state, be, tool);
    }


    private void spawnAngryBees(Level level, BlockPos pos, Player player, int amount) {
        for (int i = 0; i < amount; i++) {
            EtherealBeeEntity bee = ModEntities.ETHEREAL_BEE.get().create(level);
            if (bee != null) {
                bee.moveTo(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, level.random.nextFloat() * 360F, 0);
                bee.setTarget(player);
                level.addFreshEntity(bee);
            }
        }
    }

    private void angerNearbyBees(Level level, BlockPos pos, Player player) {
        List<EtherealBeeEntity> bees = level.getEntitiesOfClass(EtherealBeeEntity.class, new net.minecraft.world.phys.AABB(pos).inflate(12));
        for (EtherealBeeEntity bee : bees) {
            bee.setTarget(player);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(4) != 0) {
            return;
        }

        double[][] dripPoints = {
                {0.22D, 0.02D, 0.22D},
                {0.50D, 0.02D, 0.18D},
                {0.78D, 0.02D, 0.22D},
                {0.18D, 0.02D, 0.50D},
                {0.82D, 0.02D, 0.50D},
                {0.22D, 0.02D, 0.78D},
                {0.50D, 0.02D, 0.82D},
                {0.78D, 0.02D, 0.78D}
        };

        int dripCount = 1;

        if (random.nextInt(3) == 0) {
            dripCount++;
        }

        if (random.nextInt(5) == 0) {
            dripCount++;
        }

        boolean[] used = new boolean[dripPoints.length];

        for (int i = 0; i < dripCount; i++) {
            int index;
            do {
                index = random.nextInt(dripPoints.length);
            } while (used[index]);

            used[index] = true;
            spawnDrip(level, pos, dripPoints[index]);
        }
    }

    private void spawnDrip(Level level, BlockPos pos, double[] point) {
        double x = pos.getX() + point[0];
        double y = pos.getY() + point[1];
        double z = pos.getZ() + point[2];

        level.addParticle(ModParticles.ETHEREAL_HONEY_HANG.get(),
                x, y, z,
                0.0D, 0.0D, 0.0D);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EtherealNestBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.ETHEREAL_NEST.get(), EtherealNestBlockEntity::tick);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return state.getValue(HONEY_LEVEL);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        if (Screen.hasShiftDown()) {
            pTooltip.add(Component.translatable("tooltip.eclipseofthevoid.ethereal_nest_block.tooltip.shift")
                    .withStyle(ChatFormatting.GRAY));
        } else {
            pTooltip.add(Component.translatable("tooltip.eclipseofthevoid.ethereal_nest_block.tooltip")
                    .withStyle(ChatFormatting.LIGHT_PURPLE));
        }
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @Override
    public void onRemove(BlockState oldState, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!oldState.is(newState.getBlock())) {
            if (!level.isClientSide) {
                Player nearest = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 8, false);

                if (nearest != null && !nearest.isCreative()) {
                    BlockEntity be = level.getBlockEntity(pos);
                    if (be instanceof EtherealNestBlockEntity nest) {
                        List<CompoundTag> bees = nest.releaseAllBees();
                        for (CompoundTag tag : bees) {
                            EtherealBeeEntity bee = ModEntities.ETHEREAL_BEE.get().create(level);
                            if (bee != null) {
                                bee.load(tag);
                                bee.moveTo(pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5);
                                bee.setTarget(nearest);
                                level.addFreshEntity(bee);
                            }
                        }
                    }
                }
            }

            super.onRemove(oldState, level, pos, newState, isMoving);
        }
    }
}
