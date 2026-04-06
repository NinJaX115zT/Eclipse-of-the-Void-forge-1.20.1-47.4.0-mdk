package net.lucarioninja.eclipseofthevoid.block.entity;

import net.lucarioninja.eclipseofthevoid.block.ModBlocks;
import net.lucarioninja.eclipseofthevoid.block.custom.EtherealHoneyCauldronBlock;
import net.lucarioninja.eclipseofthevoid.block.custom.EtherealNestBlock;
import net.lucarioninja.eclipseofthevoid.entity.ModEntities;
import net.lucarioninja.eclipseofthevoid.entity.custom.EtherealBeeEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static net.lucarioninja.eclipseofthevoid.block.custom.EtherealNestBlock.HONEY_LEVEL;

public class EtherealNestBlockEntity extends BlockEntity {

    private static final int MAX_BEES = 5;
    private static final int TICKS_IN_NEST = 3000;
    private static final int DRIP_COOLDOWN_TICKS = 1200;
    private static final int CAULDRON_COOLDOWN_TICKS = 300;

    private float combProgress = 0;
    private int nestDripCooldown = 0;
    private int cauldronFillCooldown = 0;
    private boolean starterBeesInitialized = false;

    private final List<StoredBee> storedBees = new ArrayList<>();

    public EtherealNestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ETHEREAL_NEST.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, EtherealNestBlockEntity nest) {
        if (level.isClientSide) return;

        nest.tryInitializeStarterBees(level, pos, state);
        nest.tickBeeLifecycle(level, pos, state);
        nest.tickDrippingMechanic(level, pos, state);
    }

    private void tryInitializeStarterBees(Level level, BlockPos pos, BlockState state) {
        if (starterBeesInitialized) return;
        if (!state.hasProperty(EtherealNestBlock.GENERATED)) return;
        if (!state.getValue(EtherealNestBlock.GENERATED)) return;
        if (!storedBees.isEmpty()) {
            starterBeesInitialized = true;
            setChanged();
            return;
        }

        int roll = level.random.nextInt(100);
        int beeCount;

        if (roll < 75) {
            beeCount = 1;
        } else if (roll < 95) {
            beeCount = 2;
        } else {
            beeCount = 3;
        }

        for (int i = 0; i < beeCount; i++) {
            EtherealBeeEntity bee = new EtherealBeeEntity(ModEntities.ETHEREAL_BEE.get(), level);
            bee.setHasNectarFlag(false);

            CompoundTag tag = new CompoundTag();
            bee.save(tag);

            StoredBee storedBee = new StoredBee(tag);
            storedBee.ticks = 0;
            storedBees.add(storedBee);
        }

        level.playSound(null, pos, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1F, 1F);
        starterBeesInitialized = true;
        setChanged();
    }

    private void tickBeeLifecycle(Level level, BlockPos pos, BlockState state) {
        Iterator<StoredBee> iterator = storedBees.iterator();
        boolean changed = false;

        if (level.getGameTime() % 80 == 0 && getBeeCount() > 0) {
            level.playSound(null, pos, SoundEvents.BEEHIVE_WORK, SoundSource.BLOCKS, 1F, 1.1F);
        }

        while (iterator.hasNext()) {
            StoredBee bee = iterator.next();
            bee.ticks++;

            if (bee.ticks % 200 == 0 && bee.nbt.contains("Health")) {
                float currentHealth = bee.nbt.getFloat("Health");
                float maxHealth = 50.0F;

                if (currentHealth < maxHealth) {
                    float newHealth = Math.min(currentHealth + 1.0F, maxHealth);
                    bee.nbt.putFloat("Health", newHealth);
                    changed = true;

                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                                pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D,
                                2, 0.2D, 0.2D, 0.2D, 0.01D);
                    }
                }
            }

            if (bee.ticks >= TICKS_IN_NEST) {
                Vec3 safeExit = findSafeNestExit(level, pos);
                if (safeExit == null) {
                    continue;
                }

                EtherealBeeEntity entity = new EtherealBeeEntity(ModEntities.ETHEREAL_BEE.get(), level);
                entity.load(bee.nbt);
                entity.setHasNectarFlag(false);
                entity.setPreferredNest(pos);
                entity.setPos(safeExit.x, safeExit.y, safeExit.z);
                level.addFreshEntity(entity);
                level.playSound(null, pos, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1F, 1F);

                iterator.remove();
                changed = true;
            }
        }

        if (changed) this.setChanged();
    }

    private void tickDrippingMechanic(Level level, BlockPos pos, BlockState state) {
        if (nestDripCooldown > 0) nestDripCooldown--;
        if (cauldronFillCooldown > 0) cauldronFillCooldown--;

        BlockPos cauldronPos = null;
        for (int i = 1; i <= 7; i++) {
            BlockPos check = pos.below(i);
            if (level.getBlockState(check).is(Blocks.CAULDRON) ||
                    level.getBlockState(check).is(ModBlocks.ETHEREAL_HONEY_CAULDRON.get())) {
                cauldronPos = check;
                break;
            }
        }

        if (cauldronPos != null && state.getValue(HONEY_LEVEL) >= 5 && nestDripCooldown <= 0 && cauldronFillCooldown <= 0) {
            BlockState cauldronState = level.getBlockState(cauldronPos);

            boolean filled = false;
            if (cauldronState.is(ModBlocks.ETHEREAL_HONEY_CAULDRON.get())) {
                int currentLevel = cauldronState.getValue(EtherealHoneyCauldronBlock.LEVEL);
                if (currentLevel < 3) {
                    level.setBlock(cauldronPos, cauldronState.setValue(EtherealHoneyCauldronBlock.LEVEL, currentLevel + 1), 3);
                    filled = true;
                }
            } else if (cauldronState.is(Blocks.CAULDRON)) {
                level.setBlock(cauldronPos, ModBlocks.ETHEREAL_HONEY_CAULDRON.get().defaultBlockState().setValue(EtherealHoneyCauldronBlock.LEVEL, 1), 3);
                filled = true;
            }

            if (filled) {
                nestDripCooldown = DRIP_COOLDOWN_TICKS;
                cauldronFillCooldown = CAULDRON_COOLDOWN_TICKS;
                level.playSound(null, cauldronPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1F, 1.3F);

                if (level instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < 3; i++) {
                        double x = pos.getX() + 0.3 + level.random.nextDouble() * 0.4;
                        double y = pos.getY() - 0.05;
                        double z = pos.getZ() + 0.3 + level.random.nextDouble() * 0.4;
                        serverLevel.sendParticles(ParticleTypes.DRIPPING_HONEY, x, y, z, 1, 0, 0, 0, 0.0);
                    }
                }
            }
        }
    }

    public boolean tryAddBee(EtherealBeeEntity bee) {
        if (storedBees.size() >= MAX_BEES || !bee.hasNectar()) return false;

        CompoundTag tag = new CompoundTag();
        bee.setPreferredNest(worldPosition);
        bee.save(tag);
        storedBees.add(new StoredBee(tag));
        level.playSound(null, worldPosition, SoundEvents.BEEHIVE_ENTER, SoundSource.BLOCKS, 1F, 1F);

        combProgress += 25F;
        if (combProgress >= 100F) {
            BlockState state = level.getBlockState(worldPosition);
            int current = state.getValue(BeehiveBlock.HONEY_LEVEL);
            if (current < 5) {
                level.setBlock(worldPosition, state.setValue(BeehiveBlock.HONEY_LEVEL, current + 1), 3);
            }
            combProgress = 0F;
        }
        setChanged();
        return true;
    }

    public List<CompoundTag> releaseAllBees() {
        List<CompoundTag> list = new ArrayList<>();
        for (StoredBee bee : storedBees) list.add(bee.nbt);
        storedBees.clear();
        setChanged();
        return list;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        storedBees.clear();
        ListTag list = tag.getList("stored_bees", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag beeTag = (CompoundTag) t;
            StoredBee bee = new StoredBee(beeTag.getCompound("data"));
            bee.ticks = beeTag.getInt("ticks");
            storedBees.add(bee);
        }
        starterBeesInitialized = tag.getBoolean("starter_bees_initialized");
        combProgress = tag.getFloat("comb_progress");
        nestDripCooldown = tag.getInt("nest_drip_cooldown");
        cauldronFillCooldown = tag.getInt("cauldron_fill_cooldown");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ListTag list = new ListTag();
        for (StoredBee bee : storedBees) {
            CompoundTag beeTag = new CompoundTag();
            beeTag.put("data", bee.nbt);
            beeTag.putInt("ticks", bee.ticks);
            list.add(beeTag);
        }
        tag.putBoolean("starter_bees_initialized", starterBeesInitialized);
        tag.put("stored_bees", list);
        tag.putFloat("comb_progress", combProgress);
        tag.putInt("nest_drip_cooldown", nestDripCooldown);
        tag.putInt("cauldron_fill_cooldown", cauldronFillCooldown);
    }

    private static class StoredBee {
        final CompoundTag nbt;
        int ticks = 0;

        StoredBee(CompoundTag tag) {
            this.nbt = tag;
        }
    }

    @Nullable
    private static Vec3 findSafeNestExit(Level level, BlockPos pos) {
        BlockPos[] candidates = new BlockPos[] {
                pos.north(),
                pos.south(),
                pos.east(),
                pos.west(),

                pos.north().above(),
                pos.south().above(),
                pos.east().above(),
                pos.west().above(),

                pos.above(),
                pos.above(2),

                pos.north(2),
                pos.south(2),
                pos.east(2),
                pos.west(2)
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

    public int getBeeCount() {
        return this.storedBees.size();
    }
}
