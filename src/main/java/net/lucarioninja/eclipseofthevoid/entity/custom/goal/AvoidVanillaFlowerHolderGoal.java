package net.lucarioninja.eclipseofthevoid.entity.custom.goal;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AvoidVanillaFlowerHolderGoal extends AvoidEntityGoal<Player> {
    public AvoidVanillaFlowerHolderGoal(PathfinderMob mob, double walkSpeedModifier, double sprintSpeedModifier) {
        super(mob, Player.class, 6.0F, walkSpeedModifier, sprintSpeedModifier,
                entity -> {
                    if (entity instanceof Player player) {
                        return isHoldingVanillaFlower(player); // ← no gamemode check
                    }
                    return false;
                });
    }
    private static boolean isHoldingVanillaFlower(Player player) {
        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();
        return main.is(ItemTags.FLOWERS) || off.is(ItemTags.FLOWERS);
    }
}
