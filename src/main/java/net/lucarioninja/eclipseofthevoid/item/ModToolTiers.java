package net.lucarioninja.eclipseofthevoid.item;

import net.lucarioninja.eclipseofthevoid.EclipseOfTheVoid;
import net.lucarioninja.eclipseofthevoid.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {
    public static final Tier VOID = TierSortingRegistry.registerTier(
            new ForgeTier(4, 1800, 8, 3.5F, 12,
                    ModTags.Blocks.NEEDS_VOID_TOOL, () -> Ingredient.of(ModItems.VOID_INGOT.get())),
            new ResourceLocation(EclipseOfTheVoid.MOD_ID, "void"), List.of(Tiers.DIAMOND, Tiers.NETHERITE), List.of());
    public static final Tier INFERNAL = TierSortingRegistry.registerTier(
            new ForgeTier(6, 2700, 13,9.0F, 25,
                    ModTags.Blocks.NEEDS_INFERNAL_TOOL, () -> Ingredient.of(ModItems.INFERNAL_INGOT.get())),
            new ResourceLocation(EclipseOfTheVoid.MOD_ID, "infernal"), List.of(Tiers.NETHERITE), List.of());
    public static final Tier COSMIC = TierSortingRegistry.registerTier(
            new ForgeTier(8, 4000, 18,14, 35,
                    ModTags.Blocks.NEEDS_COSMIC_TOOL, () -> Ingredient.of(ModItems.COSMIC_INGOT.get())),
            new ResourceLocation(EclipseOfTheVoid.MOD_ID, "cosmic"), List.of(Tiers.NETHERITE), List.of());
}
