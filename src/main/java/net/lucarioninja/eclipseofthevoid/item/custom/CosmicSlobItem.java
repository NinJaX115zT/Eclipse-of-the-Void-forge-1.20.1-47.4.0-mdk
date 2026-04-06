package net.lucarioninja.eclipseofthevoid.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class CosmicSlobItem extends Item {
    public CosmicSlobItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Held too long, it invites death by flame and shadow. You will not see it coming.")
                .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
        tooltip.add(Component.literal("Reality frays around those who carry it.")
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

        // Base effects just for having it in inventory, as if it's warping reality around you
        if (time % 140 == 0 && level.random.nextFloat() < 0.18f) {
            player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 140, 0));
        }

        if (time % 140 == 0 && level.random.nextFloat() < 0.19f) {
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 160, 0));
        }

        if (time % 220 == 0 && level.random.nextFloat() < 0.17f) {
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80, 0));
        }

        if (time % 180 == 0 && level.random.nextFloat() < 0.21f) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1));
        }

        // Worse if in hotbar, as if the mass is reacting to being held and distorting reality around your hands
        if (inHotbar && time % 140 == 0 && level.random.nextFloat() < 0.19f) {
            player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 160, 0));
        }

        if (inHotbar && time % 140 == 0 && level.random.nextFloat() < 0.20f) {
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 180, 1));
        }

        if (inHotbar && time % 220 == 0 && level.random.nextFloat() < 0.18f) {
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0));
        }

        if (inHotbar && time % 90 == 0 && level.random.nextFloat() < 0.21f) {
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 120, 0));
        }

        if (inHotbar && time % 180 == 0 && level.random.nextFloat() < 0.22f) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 2));
        }

        // Even worse if selected, as if the mass is reacting to being held and searing your hands and mind
        if (selected && time % 140 == 0 && level.random.nextFloat() < 0.20f) {
            player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 180, 0));
        }

        if (selected && time % 140 == 0 && level.random.nextFloat() < 0.21f) {
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 2));
        }

        if (selected && time % 220 == 0 && level.random.nextFloat() < 0.19f) {
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 120, 0));
        }

        if (selected && time % 90 == 0 && level.random.nextFloat() < 0.22f) {
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 140, 0));
        }

        if (selected && time % 180 == 0 && level.random.nextFloat() < 0.23f) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 800, 1));
        }

        if (selected && time % 150 == 0 && level.random.nextFloat() < 0.18f) {
            player.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 0));
        }

        // Worst if actively held, as if the mass is directly affecting your body and warping reality around you, causing damage and a burst of chaotic energy
        if (selected && level instanceof ServerLevel serverLevel && time % 200 == 0 && level.random.nextFloat() < 0.14f) {
            AreaEffectCloud cloud = new AreaEffectCloud(serverLevel, player.getX(), player.getY(), player.getZ());
            cloud.setRadius(3.5F);
            cloud.setDuration(120); // Lasts for 6 seconds, but the damage is instant so it's more about the visual effect and lingering presence than ongoing damage
            cloud.setWaitTime(0);
            cloud.setRadiusOnUse(-0.1F);
            cloud.setParticle(ParticleTypes.DRAGON_BREATH);
            cloud.setFixedColor(0x6E00A8);
            cloud.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 0));
            serverLevel.addFreshEntity(cloud);
        }

        // Ambient sounds and particles, more likely if actively held, as if the mass is searing your hands and warping reality around you
        if (time % 70 == 0 && level.random.nextFloat() < (selected ? 0.38f : inHotbar ? 0.22f : 0.10f)) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENDERMAN_AMBIENT, SoundSource.PLAYERS, 0.25f, 0.6f + level.random.nextFloat() * 0.4f);
        }

        if (time % 40 == 0 && level.random.nextFloat() < (selected ? 0.55f : inHotbar ? 0.30f : 0.12f)) {
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.DRAGON_BREATH,
                        player.getX(), player.getY() + 1.0D, player.getZ(),
                        selected ? 8 : 4,
                        0.3D, 0.5D, 0.3D, 0.01D);

                serverLevel.sendParticles(ParticleTypes.PORTAL,
                        player.getX(), player.getY() + 1.0D, player.getZ(),
                        selected ? 10 : 5,
                        0.4D, 0.6D, 0.4D, 0.05D);
            }
        }

        if (selected && time % 200 == 0 && level.random.nextFloat() < 0.16f && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.displayClientMessage(
                    Component.literal("The cosmic mass bends light and thought around you.")
                            .withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC),
                    true
            );
        }

        super.inventoryTick(stack, level, entity, slot, selected);
    }
}