package net.lucarioninja.eclipseofthevoid.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
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
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level level, net.minecraft.world.entity.@NotNull Entity entity, int slot, boolean selected) {
        if (!level.isClientSide && entity instanceof Player player) {
            if (player.getInventory().contains(stack)) {
                long time = level.getGameTime();

                if (time % 200 == 0) {
                    player.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0));// Slow corruption
                }
                if (time % 300 == 0) {
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 0));// Bad luck
                }
                if (time % 600 == 0) {
                    player.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 0));// Rare instant pain
                }

            }
        }
        super.inventoryTick(stack, level, entity, slot, selected);
    }
}
