package net.lucarioninja.eclipseofthevoid.block.entity;

import net.lucarioninja.eclipseofthevoid.block.custom.EtherealHiveBlock;
import net.lucarioninja.eclipseofthevoid.entity.ModEntities;
import net.lucarioninja.eclipseofthevoid.entity.custom.EtherealBeeEntity;
import net.lucarioninja.eclipseofthevoid.item.ModItems;
import net.lucarioninja.eclipseofthevoid.screen.EtherealHiveMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EtherealHiveBlockEntity extends BlockEntity implements MenuProvider {

    private static class StoredBee {
        public static final int MAX_PRODUCTIVE_BEES = 32;
        public final CompoundTag tag;
        public int ticksInHive = 0;

        public StoredBee(CompoundTag tag) {
            this.tag = tag;
        }
    }

    private final List<StoredBee> storedBees = new ArrayList<>();
    private float combProgress = 0;
    private boolean showNectarFront = false;

    private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private final LazyOptional<ItemStackHandler> lazyItemHandler = LazyOptional.of(() -> itemHandler);

    public EtherealHiveBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ETHEREAL_HIVE.get(), pos, state);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Ethereal Hive");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new EtherealHiveMenu(id, inv, this, getData());
    }

    public static void tick(Level level, BlockPos pos, BlockState state, EtherealHiveBlockEntity hive) {
        if (level.isClientSide) return;

        boolean changed = false;

        // Bee processing loop
        Iterator<StoredBee> iterator = hive.storedBees.iterator();
        while (iterator.hasNext()) {
            StoredBee stored = iterator.next();
            stored.ticksInHive++;

            if (stored.ticksInHive % 200 == 0 && stored.tag.contains("Health")) {
                float currentHealth = stored.tag.getFloat("Health");
                float maxHealth = 10.0F;

                if (currentHealth < maxHealth) {
                    float newHealth = Math.min(currentHealth + 1.0F, maxHealth);
                    stored.tag.putFloat("Health", newHealth);
                    changed = true;

                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                                pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D,
                                2, 0.2D, 0.2D, 0.2D, 0.01D);
                    }
                }
            }

            // Bee leaves after 1:30 min cap
            if (stored.ticksInHive >= 3000) {
                Vec3 safeExit = findSafeHiveExit(level, pos, state);
                if (safeExit == null) {
                    continue; // blocked, try again next tick cycle
                }

                EtherealBeeEntity newBee = new EtherealBeeEntity(ModEntities.ETHEREAL_BEE.get(), level);
                newBee.load(stored.tag);
                newBee.setHasNectarFlag(false);
                newBee.setHivePos(pos);              // sets both hivePos and preferred home now
                newBee.setPreferredHome(pos, true);  // explicit, just to be safe
                newBee.setPos(safeExit.x, safeExit.y, safeExit.z);
                newBee.onExitHive();
                level.addFreshEntity(newBee);

                level.playSound(null, pos, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1.0f, 1.0f);
                iterator.remove();
                changed = true;
            }
        }

        // Comb progress based on bees inside
        if (!hive.storedBees.isEmpty()) {
            int effectiveBees = Math.min(hive.storedBees.size(), StoredBee.MAX_PRODUCTIVE_BEES);
            hive.combProgress += effectiveBees * 0.0083F;
            changed = true;
        }

        // Handle honeycomb production
        if (hive.combProgress >= 100) {
            ItemStack slotStack = hive.itemHandler.getStackInSlot(0);
            if (slotStack.isEmpty()) {
                hive.itemHandler.setStackInSlot(0, new ItemStack(ModItems.ETHEREAL_HONEYCOMB_CELL.get()));
            } else if (slotStack.getItem() == ModItems.ETHEREAL_HONEYCOMB_CELL.get() && slotStack.getCount() < slotStack.getMaxStackSize()) {
                slotStack.grow(1);
                hive.itemHandler.setStackInSlot(0, slotStack);
            }

            // Always play sound/particles when a Cell is produced
            level.playSound(null, pos, SoundEvents.BEEHIVE_SHEAR, SoundSource.BLOCKS, 1f, 1f);
            level.playSound(null, pos, SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.BLOCKS, 1f, 1.4f);

            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.END_ROD,
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        8, 0.3, 0.3, 0.3, 0.01
                );
            }

            hive.combProgress -= 100;

            // Update nectar visual
            if (!state.getValue(EtherealHiveBlock.NECTAR)) {
                level.setBlock(pos, state.setValue(EtherealHiveBlock.NECTAR, true), 3);
            }
            changed = true;
        }

        // Auto-disable front visual if comb taken out
        ItemStack current = hive.itemHandler.getStackInSlot(0);
        if (current.isEmpty() && state.getValue(EtherealHiveBlock.NECTAR)) {
            level.setBlock(pos, state.setValue(EtherealHiveBlock.NECTAR, false), 3);
            changed = true;
        }

        // Play work sound every 2s if bees are inside
        if (!hive.storedBees.isEmpty() && level.getGameTime() % 40 == 0) {
            level.playSound(null, pos, SoundEvents.BEEHIVE_WORK, SoundSource.BLOCKS, 1f, 1.0f);
        }

        if (changed) hive.setChanged();
    }

    public void addBee(EtherealBeeEntity bee) {
        if (bee.hasNectar()) {
            CompoundTag tag = new CompoundTag();
            bee.setHivePos(worldPosition);
            bee.setPreferredHome(worldPosition, true);
            bee.save(tag);
            storedBees.add(new StoredBee(tag));
            combProgress += 10;

            // 🐝 Enter sound
            if (level != null) {
                level.playSound(null, worldPosition, SoundEvents.BEEHIVE_ENTER, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }
    }

    private final SimpleContainerData data = new SimpleContainerData(2) {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> getCombProgressPercent();
                case 1 -> storedBees.size();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {}

        @Override
        public int getCount() {
            return 2;
        }
    };

    public SimpleContainerData getData() {
        return data;
    }

    public int getCombProgressPercent() {
        return Math.min(100, (int) this.combProgress);
    }

    public boolean shouldShowNectarFront() {
        return this.showNectarFront;
    }

    public void clearNectarFront() {
        this.showNectarFront = false;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inventory", itemHandler.serializeNBT());

        ListTag beeList = new ListTag();
        for (StoredBee bee : storedBees) {
            CompoundTag beeTag = new CompoundTag();
            beeTag.put("data", bee.tag);
            beeTag.putInt("ticks", bee.ticksInHive);
            beeList.add(beeTag);
        }
        tag.put("stored_bees", beeList);
        tag.putFloat("comb_progress", combProgress);
        tag.putBoolean("show_nectar_front", showNectarFront);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));

        storedBees.clear();
        if (tag.contains("stored_bees", Tag.TAG_LIST)) {
            ListTag beeList = tag.getList("stored_bees", Tag.TAG_COMPOUND);
            for (Tag t : beeList) {
                CompoundTag beeTag = (CompoundTag) t;
                StoredBee bee = new StoredBee(beeTag.getCompound("data"));
                bee.ticksInHive = beeTag.getInt("ticks");
                storedBees.add(bee);
            }
        }
        combProgress = tag.getFloat("comb_progress");
        showNectarFront = tag.getBoolean("show_nectar_front");
    }

    @Nullable
    private static Vec3 findSafeHiveExit(Level level, BlockPos pos, BlockState state) {
        Direction facing = state.getOptionalValue(EtherealHiveBlock.FACING).orElse(Direction.NORTH);

        BlockPos front = pos.relative(facing);
        BlockPos left = pos.relative(facing.getClockWise());
        BlockPos right = pos.relative(facing.getCounterClockWise());
        BlockPos back = pos.relative(facing.getOpposite());

        BlockPos[] candidates = new BlockPos[] {
                front,
                left,
                right,
                back,

                front.above(),
                left.above(),
                right.above(),
                back.above(),

                pos.above(),
                pos.above(2),

                front.relative(facing),
                left.relative(facing.getClockWise()),
                right.relative(facing.getCounterClockWise())
        };

        for (BlockPos candidate : candidates) {
            BlockState stateHere = level.getBlockState(candidate);
            BlockState stateAbove = level.getBlockState(candidate.above());

            if (stateHere.isAir() && stateAbove.isAir()) {
                return new Vec3(candidate.getX() + 0.5D, candidate.getY() + 0.75D, candidate.getZ() + 0.5D);
            }
        }

        return null;
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public void angerBees(@Nullable Player player) {
        if (level == null || level.isClientSide) return;

        for (StoredBee stored : storedBees) {
            EtherealBeeEntity bee = new EtherealBeeEntity(ModEntities.ETHEREAL_BEE.get(), level);
            bee.load(stored.tag);
            bee.setHasNectarFlag(false);

            Vec3 safeExit = findSafeHiveExit(level, worldPosition, getBlockState());
            if (safeExit == null) {
                safeExit = new Vec3(worldPosition.getX() + 0.5D, worldPosition.getY() + 1.5D, worldPosition.getZ() + 0.5D);
            }

            bee.setHivePos(worldPosition);
            bee.setPreferredHome(worldPosition, true);
            bee.setPos(safeExit.x, safeExit.y, safeExit.z);
            bee.onExitHive();
            bee.setTarget(player);
            level.addFreshEntity(bee);
        }
    }

    public void emptyAllContents(@Nullable Player player) {
        storedBees.clear();
        combProgress = 0;
        if (level != null && !level.isClientSide) {
            level.setBlock(worldPosition, getBlockState().setValue(EtherealHiveBlock.NECTAR, false), 3);
        }
    }
}
