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

public class VoidSlobItem extends Item {
    public VoidSlobItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("It clings to your soul like tar. You feel slower... sicker...")
                .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
        tooltip.add(Component.literal("Storing it in a chest is strongly advised.")
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

        // Base effects just for having it in inventory, like it's seeping into you
        if (time % 100 == 0 && level.random.nextFloat() < 0.18f) {
            player.addEffect(new MobEffectInstance(MobEffects.POISON, 140, 0));
        }

        if (time % 160 == 0 && level.random.nextFloat() < 0.14f) {
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 160, 0));
        }

        if (time % 120 == 0 && level.random.nextFloat() < 0.22f) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1));
        }

        if (time % 200 == 0 && level.random.nextFloat() < 0.15f) {
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 140, 0));
        }

        // Worse if in hotbar, as if the tar is seeping into your hands
        if (inHotbar && time % 100 == 0 && level.random.nextFloat() < 0.19f) {
            player.addEffect(new MobEffectInstance(MobEffects.POISON, 160, 0));
        }

        if (inHotbar && time % 160 == 0 && level.random.nextFloat() < 0.15f) {
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 180, 0));
        }

        if (inHotbar && time % 120 == 0 && level.random.nextFloat() < 0.21f) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 2));
        }

        if (inHotbar && time % 200 == 0 && level.random.nextFloat() < 0.16f) {
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 160, 0));
        }

        if (inHotbar && time % 80 == 0 && level.random.nextFloat() < 0.19f) {
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 80, 0));
        }

        // Worst if actively held, as if the tar is directly affecting your body
        if (selected && time % 100 == 0 && level.random.nextFloat() < 0.20f) {
            player.addEffect(new MobEffectInstance(MobEffects.POISON, 180, 0));
        }

        if (selected && time % 160 == 0 && level.random.nextFloat() < 0.16f) {
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 0));
        }

        if (selected && time % 120 == 0 && level.random.nextFloat() < 0.22f) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 800, 2));
        }

        if (selected && time % 200 == 0 && level.random.nextFloat() < 0.17f) {
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 180, 0));
        }

        if (selected && time % 80 == 0 && level.random.nextFloat() < 0.20f) {
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0));
        }

        if (selected && time % 140 == 0 && level.random.nextFloat() < 0.18f) {
            player.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 0));
        }

        // Visual and audio effects to show the mass reacting to being held, like it's trying to escape or lash out
        if (time % 90 == 0 && level.random.nextFloat() < (selected ? 0.35f : inHotbar ? 0.22f : 0.10f)) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.SOUL_ESCAPE, SoundSource.PLAYERS, 0.5f, 0.8f + level.random.nextFloat() * 0.3f);
        }

        if (time % 40 == 0 && level.random.nextFloat() < (selected ? 0.45f : inHotbar ? 0.25f : 0.10f)) {
            if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.SOUL,
                        player.getX(), player.getY() + 1.0D, player.getZ(),
                        selected ? 6 : 3,
                        0.3D, 0.5D, 0.3D, 0.01D);
            }
        }

        // Occasional ominous messages, more likely if actively held, as if the tar is whispering to you and seeping deeper into your body
        if (selected && time % 200 == 0 && level.random.nextFloat() < 0.18f && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.displayClientMessage(
                    Component.literal("The void tar creeps deeper into your body...")
                            .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC),
                    true
            );
        }

        super.inventoryTick(stack, level, entity, slot, selected);
    }
}
