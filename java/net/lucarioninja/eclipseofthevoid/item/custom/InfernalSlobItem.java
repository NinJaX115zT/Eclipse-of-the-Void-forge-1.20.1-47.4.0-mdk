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

public class InfernalSlobItem extends Item {
    public InfernalSlobItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("You swear it breathed. Then burned your hand.")
                .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level level, net.minecraft.world.entity.@NotNull Entity entity, int slot, boolean selected) {
        if (!level.isClientSide && entity instanceof Player player) {
            if (player.getInventory().contains(stack)) {
                long time = level.getGameTime();

                if (time % 150 == 0) {
                    player.setSecondsOnFire(3); // brief fire sizzle
                }

                if (time % 300 == 0) {
                    player.addEffect(new MobEffectInstance(MobEffects.WITHER, 120, 0)); // like you're burning inside
                }

                if (time % 450 == 0) {
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2)); // heavy feeling
                }
            }
        }
        super.inventoryTick(stack, level, entity, slot, selected);
    }
}
