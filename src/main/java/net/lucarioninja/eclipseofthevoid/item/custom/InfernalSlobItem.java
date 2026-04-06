package net.lucarioninja.eclipseofthevoid.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.core.particles.ParticleTypes;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class InfernalSlobItem extends Item {
    public InfernalSlobItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("You swear it breathed. Then burned your hand.")
                .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
        tooltip.add(Component.literal("It grows angrier the longer you carry it.")
                .withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int slot, boolean selected) {
        if (level.isClientSide || !(entity instanceof Player player)) {
            super.inventoryTick(stack, level, entity, slot, selected);
            return;
        }

        long time = level.getGameTime();
        boolean inHotbar = slot >= 0 && slot < 9;

        // Base effects just for having it in inventory, as if it's radiating heat and malice
        if (time % 120 == 0 && level.random.nextFloat() < 0.18f) {
            player.setSecondsOnFire(4);
        }

        if (time % 180 == 0 && level.random.nextFloat() < 0.12f) {
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 160, 0));
        }

        if (time % 180 == 0 && level.random.nextFloat() < 0.22f) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1));
        }

        // Worse if in hotbar, as if the mass is reacting to being held and searing your hands
        if (inHotbar && time % 70 == 0 && level.random.nextFloat() < 0.22f) {
            player.setSecondsOnFire(6);
        }

        if (inHotbar && time % 180 == 0 && level.random.nextFloat() < 0.13f) {
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 180, 0));
        }

        if (inHotbar && time % 180 == 0 && level.random.nextFloat() < 0.23f) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 2));
        }

        if (inHotbar && time % 120 == 0 && level.random.nextFloat() < 0.20f) {
            player.addEffect(new MobEffectInstance(MobEffects.WITHER, 140, 0));
        }

        // Worst if selected, as if the mass is directly affecting your body and soul, burning you from the inside out
        if (selected && time % 70 == 0 && level.random.nextFloat() < 0.26f) {
            player.setSecondsOnFire(8);
        }

        if (selected && time % 180 == 0 && level.random.nextFloat() < 0.14f) {
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, 0));
        }

        if (selected && time % 180 == 0 && level.random.nextFloat() < 0.24f) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 800, 3));
        }

        if (selected && time % 110 == 0 && level.random.nextFloat() < 0.21f) {
            player.addEffect(new MobEffectInstance(MobEffects.WITHER, 180, 0));
        }

        if (selected && time % 170 == 0 && level.random.nextFloat() < 0.18f) {
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 140, 0));
        }

        // Visual and audio cues for the mass reacting to being held, like it's searing your hands and radiating heat and malice around you
        if (time % 60 == 0 && level.random.nextFloat() < (selected ? 0.45f : inHotbar ? 0.26f : 0.12f)) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.LAVA_POP, SoundSource.PLAYERS, 0.45f, 0.9f + level.random.nextFloat() * 0.2f);
        }

        if (time % 40 == 0 && level.random.nextFloat() < (selected ? 0.55f : inHotbar ? 0.30f : 0.12f)) {
            if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.SMOKE,
                        player.getX(), player.getY() + 1.0D, player.getZ(),
                        selected ? 8 : 4,
                        0.3D, 0.5D, 0.3D, 0.01D);

                serverLevel.sendParticles(ParticleTypes.FLAME,
                        player.getX(), player.getY() + 1.0D, player.getZ(),
                        selected ? 5 : 2,
                        0.2D, 0.4D, 0.2D, 0.01D);
            }
        }

        if (selected && time % 180 == 0 && level.random.nextFloat() < 0.18f && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.displayClientMessage(
                    Component.literal("The infernal mass sears your flesh.")
                            .withStyle(ChatFormatting.RED, ChatFormatting.ITALIC),
                    true
            );
        }

        super.inventoryTick(stack, level, entity, slot, selected);
    }
}