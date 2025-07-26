package net.lucarioninja.eclipseofthevoid.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.core.particles.ParticleTypes;
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
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level level, @NotNull net.minecraft.world.entity.Entity entity, int slot, boolean selected) {
        if (!level.isClientSide && entity instanceof Player player) {
            if (player.getInventory().contains(stack)) {
                long time = level.getGameTime();

                // 🟣 Dragon Breath-style area cloud every 15 seconds
                if (level instanceof ServerLevel serverLevel && time % 500 == 0) {
                    if (level.random.nextFloat() < 0.50f) {
                        AreaEffectCloud cloud = new AreaEffectCloud(player.level(), player.getX(), player.getY(), player.getZ());
                        cloud.setRadius(2.5F);               // Size of the damage zone
                        cloud.setDuration(100);              // 5 seconds duration
                        cloud.setRadiusOnUse(-0.5F);         // Shrinks as it ticks
                        cloud.setWaitTime(0);                // Starts instantly
                        cloud.setParticle(ParticleTypes.DRAGON_BREATH);
                        cloud.setFixedColor(0x6E00A8);       // Optional: deep cosmic purple
                        cloud.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 1)); // Instant damage II

                        serverLevel.addFreshEntity(cloud);
                    }
                }

                if (time % 200 == 0) {
                    player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 100, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0));
                }

                if (time % 400 == 0) {
                    player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0));
                }
            }
        }

        super.inventoryTick(stack, level, entity, slot, selected);
    }
}